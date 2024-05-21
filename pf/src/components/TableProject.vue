<template>
  <v-container fluid>
    <v-row class="d-flex justify-center">
      <v-col cols="12" sm="8" md="10">
        <v-card>
          <v-card-title>
            <h3>Proyectos</h3>
          </v-card-title>
          <v-card-text>
            <!-- Tabla de datos de los proyectos -->
            <v-data-table :headers="headers" :items="items" sort-by="name" class="elevation-1">
              <!-- Encabezado de la tabla con barra de búsqueda y botón de nuevo proyecto -->
              <template v-slot:top>
                <v-toolbar flat>
                  <v-text-field v-model="search" append-icon="mdi-magnify" label="Buscar empleado" single-line
                    hide-details class="mb-4"></v-text-field>
                  <v-divider class="mx-4" inset vertical></v-divider>
                  <!-- <v-spacer></v-spacer> -->
                  <!-- Botón de nuevo proyecto -->
                  <v-dialog v-model="dialog" max-width="1000px">
                    <template v-slot:activator="{ on, attrs }">
                      <v-btn color="primary" dark class="mb-2" v-bind="attrs" v-on="on">
                        Nuevo Proyecto
                      </v-btn>
                    </template>

                    <!-- Formulario de creación/edición de proyectos -->
                    <v-card>
                      <v-card-title>
                        <span class="text-h5">{{ formTitle }}</span>
                      </v-card-title>
                      <v-card-text>
                        <v-container>
                          <v-form ref="form" v-model="valid" lazy-validation>
                            <v-row>
                              <!-- Descripción del proyecto -->
                              <v-col cols="12" sm="12" md="12">
                                <v-text-field v-model="editedItem.description" label="Descripcion"
                                  :rules="[rules.required]" required></v-text-field>
                              </v-col>
                              <!-- Fecha de inicio del proyecto -->
                              <v-col cols="12" sm="6" md="4">
                                <v-menu ref="menuStartDate" v-model="menuStartDate" :close-on-content-click="false"
                                  :nudge-right="40" transition="scale-transition" offset-y min-width="auto">
                                  <template v-slot:activator="{ on, attrs }">
                                    <v-text-field v-model="editedItem.startDate" label="Fecha Inicio"
                                      prepend-icon="mdi-calendar" readonly v-bind="attrs" v-on="on"
                                      :rules="[rules.required, rules.dateIsBefore(editedItem.endDate)]"></v-text-field>
                                  </template>
                                  <v-date-picker v-model="editedItem.startDate" no-title scrollable first-day-of-week="1"
                                    locale="es" @input="menuStartDate = false"></v-date-picker>
                                </v-menu>
                              </v-col>
                              <!-- Fecha de fin del proyecto -->
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
                              <!-- Localización del proyecto -->
                              <v-col cols="12" sm="6" md="4">
                                <v-text-field v-model="editedItem.location" label="Localizacion"
                                  :rules="[rules.required]"></v-text-field>
                              </v-col>
                              <!-- Observaciones del proyecto -->
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
                        <v-btn color="blue darken-1" text @click="save">Guardar</v-btn>
                        <v-btn color="error" text @click="close">Cancelar</v-btn>
                      </v-card-actions>
                    </v-card>
                    <!-- Diálogo para confirmar la baja del proyecto -->
                  </v-dialog>
                  <v-dialog v-model="dialogDelete" max-width="1000px">
                    <v-card>
                      <v-card-title class="text-h5">¿Quiere dar de baja a este proyecto?</v-card-title>
                      <v-card-actions>
                        <v-spacer></v-spacer>
                        <v-btn color="blue darken-1" text @click="deleteItemConfirm">Confirmar</v-btn>
                        <v-btn color="error" text @click="closeDelete">Cancelar</v-btn>
                        <v-spacer></v-spacer>
                      </v-card-actions>
                    </v-card>
                  </v-dialog>
                </v-toolbar>
              </template>

               <!-- Iconos de acción en la tabla -->
              <template v-slot:item.actions="{ item }">
                <v-icon small class="mr-2" @click="editItem(item)">
                  mdi-pencil
                </v-icon>
                <v-icon small @click="deleteItem(item)">
                  mdi-delete
                </v-icon>
              </template>

              <!-- Mensaje cuando no hay datos -->
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
    valid: true, // Booleano que indica si el formulario es válido
    dialog: false, // Booleano que controla la visibilidad del diálogo de creación/edición de proyectos
    dialogDelete: false, // Booleano que controla la visibilidad del diálogo de confirmación de eliminación
    menuStartDate: false, // Booleano que controla la visibilidad del menú de selección de la fecha de inicio
    menuEndDate: false, // Booleano que controla la visibilidad del menú de selección de la fecha de fin
    // Encabezados de las columnas de la tabla y sus valores correspondientes asociados
    headers: [
      { text: 'Id', value: 'idProject' },
      { text: 'Descripcion', value: 'description' },
      { text: 'Fecha Inicio', value: 'startDate' },
      { text: 'Fecha Fin', value: 'endDate' },
      { text: 'Localizacion', value: 'location' },
      // { text: 'Observaciones', value: 'observations' },
      { text: 'Acciones', value: 'actions', sortable: false },
    ],
    items: [], // Lista de proyectos obtenida desde la API
     // Reglas de validación para los campos del formulario
    rules: {
      required: value => !!value || 'Requerido.',
      dateIsBefore: endDate => {
        return startDate => {
          if (!startDate || !endDate) return true; // No validar si no hay fechas
          const startDateV = new Date(startDate);
          const endDateV = new Date(endDate);
          return startDateV < endDateV || 'La fecha de inicio debe ser anterior a la fecha de fin.';
        };
      },
    },
    editedIndex: -1, // Índice del proyecto que se está editando
     // Objeto que contiene los datos del proyecto que se está editando/creando
    editedItem: {
      idProject: '',
      description: '',
      startDate: '',
      endDate: '',
      location: '',
      observations: ''
    },
    // Objeto que contiene los datos por defecto para un nuevo proyecto
    defaultItem: {
      idProject: '',
      description: '',
      startDate: '',
      endDate: '',
      location: '',
      observations: ''
    },
  }),
 // Computed property para determinar el título del formulario de creación/edición
  computed: {
    formTitle() {
      return this.editedIndex === -1 ? 'Nuevo Proyecto' : 'Editar Proyecto';
    },
  },

  watch: {
     // Observa cambios en la propiedad `dialog`
    dialog(val) {
      val || this.close();// Si el diálogo se cierra (val es false), se llama a `close`
    },
    // Observa cambios en la propiedad `dialogDelete`
    dialogDelete(val) {
      val || this.closeDelete();// Si el diálogo de eliminación se cierra (val es false), se llama a `closeDelete`
    },
  },

  methods: {
    // Método para inicializar los datos de la tabla desde la API
    async initialize() {
      try {
        const response = await axios.get('http://localhost:8080/project/active');
        this.items = response.data;
      } catch (error) {
        console.error('Error fetching data:', error);
      }
    },

    // Método para editar un elemento de la tabla
    editItem(item) {
      this.editedIndex = this.items.indexOf(item);
      this.editedItem = Object.assign({}, item);
      this.dialog = true;
    },

    // Método para eliminar un proyecto de la tabla
    deleteItem(item) {
      this.editedIndex = this.items.indexOf(item);
      this.editedItem = Object.assign({}, item);
      this.dialogDelete = true;
    },

    // Confirmación de eliminación del proyecto, lannza la petición a la API con el ID del proyecto a dar de baja
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
// Método para cerrar el diálogo de creación/edición
    close() {
      this.dialog = false;
      this.$nextTick(() => {
        this.editedItem = Object.assign({}, this.defaultItem);
        this.editedIndex = -1;
        this.$refs.form.resetValidation();
      });
    },
 // Método para cerrar el diálogo de eliminación
    closeDelete() {
      this.dialogDelete = false;
      this.$nextTick(() => {
        this.editedItem = Object.assign({}, this.defaultItem);
        this.editedIndex = -1;
      });
    },
    // Guardar cambios realizados en el formulario de edición/creación
    async save() {
      try {
        if (this.$refs.form.validate()) {
          const isEditing = this.editedIndex > -1;//booleano, si el indice es mayor de -1 editamos, si no es un nuevo proyecto
          const url = isEditing
            ? `http://localhost:8080/project/${this.editedItem.idProject}`
            : 'http://localhost:8080/project';
          const method = isEditing ? 'put' : 'post';


          // Realiza la solicitud HTTP
          const response = await axios({
            method: method,
            url: url,
            data: JSON.stringify(this.editedItem),
            headers: {
              'Content-Type': 'application/json'
            }
          });

          // Verifica el estado de la respuesta
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

          // Actualiza el item en la lista si es una edición
          if (isEditing) {
            Object.assign(this.items[this.editedIndex], response.data);
          } else {
            // Agrega el nuevo item a la lista si es una creación
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