<template>
  <v-container fluid>
    <v-row class="d-flex justify-center">
      <v-col cols="12" sm="8" md="10">
        <v-card>
          <v-card-title>
            <h3>Proyectos</h3>
          </v-card-title>
          <v-card-text>
            <v-data-table :headers="headers" :items="items" sort-by="name" class="elevation-1">
              <template v-slot:top>
                <v-toolbar flat>
                  <v-text-field v-model="search" append-icon="mdi-magnify" label="Buscar empleado" single-line
                    hide-details class="mb-4"></v-text-field>
                  <v-divider class="mx-4" inset vertical></v-divider>
                  <!-- <v-spacer></v-spacer> -->

                  <v-dialog v-model="dialog" max-width="1000px">
                    <template v-slot:activator="{ on, attrs }">
                      <v-btn color="primary" dark class="mb-2" v-bind="attrs" v-on="on">
                        Nuevo Proyecto
                      </v-btn>
                    </template>


                    <v-card>
                      <v-card-title>
                        <span class="text-h5">{{ formTitle }}</span>
                      </v-card-title>
                      <v-card-text>
                        <v-container>
                          <v-form ref="form" v-model="valid" lazy-validation>
                            <v-row>
                              <v-col cols="12" sm="12" md="12">
                                <v-text-field v-model="editedItem.description" label="Descripcion"
                                  :rules="[rules.required]" required></v-text-field>
                              </v-col>
                              <v-col cols="12" sm="6" md="4">
                                <v-menu ref="menuStartDate" v-model="menuStartDate" :close-on-content-click="false"
                                  :nudge-right="40" transition="scale-transition" offset-y min-width="auto">
                                  <template v-slot:activator="{ on, attrs }">
                                    <v-text-field v-model="editedItem.startDate" label="Fecha Inicio"
                                      prepend-icon="mdi-calendar" readonly v-bind="attrs" v-on="on"
                                      :rules="[rules.required]"></v-text-field>
                                  </template>
                                  <v-date-picker v-model="editedItem.startDate" no-title scrollable first-day-of-week="1"
                                    locale="es" @input="menuStartDate = false"></v-date-picker>
                                </v-menu>
                              </v-col>
                              <v-col cols="12" sm="6" md="4">
                                <v-menu ref="menuEndDate" v-model="menuEndDate" :close-on-content-click="false"
                                  :nudge-right="40" transition="scale-transition" offset-y min-width="auto">
                                  <template v-slot:activator="{ on, attrs }">
                                    <v-text-field v-model="editedItem.endDate" label="Fecha Fin"
                                      prepend-icon="mdi-calendar" readonly v-bind="attrs" v-on="on"
                                      :rules="[rules.required]"></v-text-field>
                                  </template>
                                  <v-date-picker v-model="editedItem.endDate" no-title scrollable first-day-of-week="1"
                                    locale="es" @input="menuEndDate = false"></v-date-picker>
                                </v-menu>
                              </v-col>
                              <v-col cols="12" sm="6" md="4">
                                <v-text-field v-model="editedItem.location" label="Localizacion"
                                  :rules="[rules.required]"></v-text-field>
                              </v-col>
                              <v-col cols="12" sm="12" md="12">
                                <v-text-field v-model="editedItem.observations" label="Observaciones"
                                  :rules="[rules.required]"></v-text-field>
                              </v-col>
                            </v-row>
                          </v-form>

                        </v-container>
                      </v-card-text>
                      <v-card-actions>
                        <v-spacer></v-spacer>
                        <v-btn color="blue darken-1" text @click="close">Cancelar</v-btn>
                        <v-btn color="blue darken-1" text @click="save">Guardar</v-btn>
                      </v-card-actions>
                    </v-card>

                  </v-dialog>
                  <v-dialog v-model="dialogDelete" max-width="1000px">
                    <v-card>
                      <v-card-title class="text-h5">¿Quiere dar de baja a este proyecto?</v-card-title>
                      <v-card-actions>
                        <v-spacer></v-spacer>
                        <v-btn color="blue darken-1" text @click="closeDelete">Cancelar</v-btn>
                        <v-btn color="blue darken-1" text @click="deleteItemConfirm">Confirmar</v-btn>
                        <v-spacer></v-spacer>
                      </v-card-actions>
                    </v-card>
                  </v-dialog>

                </v-toolbar>
              </template>
              <template v-slot:item.actions="{ item }">
                <v-icon small class="mr-2" @click="editItem(item)">
                  mdi-pencil
                </v-icon>
                <v-icon small @click="deleteItem(item)">
                  mdi-delete
                </v-icon>
              </template>
              <template v-slot:no-data>
                <v-btn color="primary" @click="initialize">Reset</v-btn>
              </template>
            </v-data-table>
          </v-card-text>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>
<script>
import axios from 'axios';
import Swal from 'sweetalert2';
import '@sweetalert2/theme-bootstrap-4/bootstrap-4.css';

export default {
  name: 'TableEmployee',
  data: () => ({
    valid: true,
    dialog: false,
    dialogDelete: false,
    menuStartDate: false,
    menuEndDate: false,
    headers: [
      { text: 'Id', value: 'idProject' },
      { text: 'Descripcion', value: 'description' },
      { text: 'Fecha Inicio', value: 'startDate' },
      { text: 'Fecha Fin', value: 'endDate' },
      { text: 'Localizacion', value: 'location' },
      // { text: 'Observaciones', value: 'observations' },
      { text: 'Acciones', value: 'actions', sortable: false },
    ],
    items: [],
    rules: {
      required: value => !!value || 'Requerido.',
    },
    editedIndex: -1,
    editedItem: {
      idProject: '',
      description: '',
      startDate: '',
      endDate: '',
      location: '',
      observations: ''
    },
    defaultItem: {
      idProject: '',
      description: '',
      startDate: '',
      endDate: '',
      location: '',
      observations: ''
    },
  }),

  computed: {
    formTitle() {
      return this.editedIndex === -1 ? 'Nuevo Proyecto' : 'Editar Proyecto';
    },
  },

  watch: {
    dialog(val) {
      val || this.close();
    },
    dialogDelete(val) {
      val || this.closeDelete();
    },
  },

  methods: {
    async initialize() {
      try {
        const response = await axios.get('http://localhost:8080/project/active');
        this.items = response.data;
      } catch (error) {
        console.error('Error fetching data:', error);
      }
    },

    editItem(item) {
      this.editedIndex = this.items.indexOf(item);
      this.editedItem = Object.assign({}, item);
      this.dialog = true;
    },

    deleteItem(item) {
      this.editedIndex = this.items.indexOf(item);
      this.editedItem = Object.assign({}, item);
      this.dialogDelete = true;
    },

    async deleteItemConfirm() {
      try {
        const response = await axios.put(`http://localhost:8080/project/terminate/${this.editedItem.idProject}`);
        if (response.status === 200) { // Verifica el código de estado en la respuesta
          Swal.fire({
            icon: "success",
            title: "El proyecto ha sido dado de baja",
            showConfirmButton: false,
            timer: 1500
          });
          // Elimina el ítem de la lista solo si la respuesta es exitosa
          this.items.splice(this.editedIndex, 1);
        }
        this.closeDelete();
      } catch (error) {
        console.error('Error deleting item:', error);
        Swal.fire({
          icon: "error",
          title: "No puede dar de baja a un Proyecto con empleados asignados",
          showConfirmButton: false,
          timer: 1500
        });
        this.closeDelete();
      }
    },

    close() {
      this.dialog = false;
      this.$nextTick(() => {
        this.editedItem = Object.assign({}, this.defaultItem);
        this.editedIndex = -1;
        this.$refs.form.resetValidation();
      });
    },

    closeDelete() {
      this.dialogDelete = false;
      this.$nextTick(() => {
        this.editedItem = Object.assign({}, this.defaultItem);
        this.editedIndex = -1;
      });
    },

    async save() {
      try {
        if (this.$refs.form.validate()) {
          const isEditing = this.editedIndex > -1;
          const url = isEditing
            ? `http://localhost:8080/project/${this.editedItem.idProject}`
            : 'http://localhost:8080/project';
          const method = isEditing ? 'put' : 'post';


          // Realizar la solicitud HTTP
          const response = await axios({
            method: method,
            url: url,
            data: JSON.stringify(this.editedItem),
            headers: {
              'Content-Type': 'application/json'
            }
          });

          // Verificar el estado de la respuesta
          if (response.status === 200) {
            // Notificación de éxito
            Swal.fire({
              icon: "success",
              title: "El proyecto ha sido editado",
              showConfirmButton: false,
              timer: 1500
            });

          } else if (response.status === 201) {
            // Notificación de error
            Swal.fire({
              icon: "success",
              title: "El proyecto ha sido creado",
              showConfirmButton: false,
              timer: 1500
            });
          }

          // Actualizar el item en la lista si es una edición
          if (isEditing) {
            Object.assign(this.items[this.editedIndex], response.data);
          } else {
            // Agregar el nuevo item a la lista si es una creación
            this.items.push(response.data);
          }
          // Cerrar el formulario
          this.close();
        }
      } catch (error) {
        console.error('Error saving item:', error);
        // Notificación de error
        Swal.fire({
          icon: "error",
          title: "Se ha producido un error",
          showConfirmButton: false,
          timer: 1500
        });
        // Cerrar el formulario
        this.close();
      }
    }
  },
  created() {
    this.initialize();
  }
};
</script>