<template>
  <v-container fluid>
    <v-row class="d-flex justify-center">
      <v-col cols="12" sm="8" md="10">
        <v-card>
          <v-card-title>
            <h3>Empleados</h3>
          </v-card-title>
          <v-card-text>
           <!-- Tabla datos empleados -->
            <v-data-table :headers="headers" :items="items" :search="search" sort-by="id" class="elevation-1 dense-table"
              :items-per-page="10">
              <!-- Encabezado de la tabla con barra de búsqueda y botón de nuevo empleado -->
              <template v-slot:top>
                <v-toolbar flat>
                  <v-text-field v-model="search" append-icon="mdi-magnify" label="Buscar empleado" single-line
                    hide-details class="mb-4"></v-text-field>
                  <v-divider class="mx-4" inset vertical></v-divider>
                  
                 
                  <v-dialog v-model="dialog" max-width="1000px">
                    <template v-slot:activator="{ on, attrs }">
                      <v-btn color="primary" dark class="mb-2" v-bind="attrs" v-on="on">
                        Nuevo Empleado
                      </v-btn>
                    </template>

                    <!-- Formulario de creación/edición de empleados -->
                    <v-card>
                      <v-card-title>
                        <span class="text-h5">{{ formTitle }}</span>
                      </v-card-title>
                      <v-card-text>
                        <v-container>
                          <v-form ref="form" v-model="valid" lazy-validation>
                            <v-row>
                              <!-- id -->
                              <v-col cols="12" sm="6" md="4">
                                <v-text-field v-model="editedItem.idEmployee" label="id" disabled></v-text-field>
                              </v-col>
                              <!-- NIF -->
                              <v-col cols="12" sm="6" md="4">
                                <v-text-field v-model="editedItem.nif" label="NIF"
                                  :rules="[rules.required, rules.nif]"></v-text-field>
                              </v-col>
                              <!-- Nombre -->
                              <v-col cols="12" sm="6" md="4">
                                <v-text-field v-model="editedItem.name" label="Nombre"
                                  :rules="[rules.required]"></v-text-field>
                              </v-col>
                              <!-- Primer apellido -->
                              <v-col cols="12" sm="6" md="4">
                                <v-text-field v-model="editedItem.surname1" label="Primer apellido"
                                  :rules="[rules.required]"></v-text-field>
                              </v-col>
                              <!-- Segundo apellido -->
                              <v-col cols="12" sm="6" md="4">
                                <v-text-field v-model="editedItem.surname2" label="Segundo apellido"
                                  :rules="[rules.required]"></v-text-field>
                              </v-col>
                              <!-- Fecha Nacimiento -->
                              <v-col cols="12" sm="6" md="4">
                                <v-menu ref="menuFN" v-model="menuFN" :close-on-content-click="false" :nudge-right="40"
                                  transition="scale-transition" offset-y min-width="auto">
                                  <template v-slot:activator="{ on, attrs }">
                                    <v-text-field v-model="editedItem.birthdate" label="Fecha Nacimiento"
                                      prepend-icon="mdi-calendar" readonly v-bind="attrs" v-on="on"
                                      :rules="[rules.required]"></v-text-field>
                                  </template>
                                  <v-date-picker v-model="editedItem.birthdate" no-title scrollable first-day-of-week="1"
                                    locale="es" @input="menuFN = false"></v-date-picker>
                                </v-menu>
                              </v-col>
                              <!-- Telefono 1 -->
                              <v-col cols="12" sm="6" md="4">
                                <v-text-field v-model="editedItem.phone1" label="Telefono Principal"
                                  :rules="[rules.required, rules.phone]"></v-text-field>
                              </v-col>
                              <!-- Telefono 2 -->
                              <v-col cols="12" sm="6" md="4">
                                <v-text-field v-model="editedItem.phone2" label="Telefono Secundario"
                                  :rules="[rules.required, rules.phone]"></v-text-field>
                              </v-col>
                              <!-- Email -->
                              <v-col cols="12" sm="6" md="4">
                                <v-text-field v-model="editedItem.email" label="Email"
                                  :rules="[rules.required, rules.email]"></v-text-field>
                              </v-col>
                              <!-- Fecha alta empleado -->
                              <v-col cols="12" sm="6" md="4">
                                <v-menu ref="menuFA" v-model="menuFA" :close-on-content-click="false" :nudge-right="40"
                                  transition="scale-transition" offset-y min-width="auto">
                                  <template v-slot:activator="{ on, attrs }">
                                    <v-text-field v-model="editedItem.hireDate" label="Fecha Alta"
                                      prepend-icon="mdi-calendar" readonly v-bind="attrs" v-on="on"
                                      :rules="[rules.required]"></v-text-field>
                                  </template>
                                  <v-date-picker v-model="editedItem.hireDate" no-title scrollable first-day-of-week="1"
                                    locale="es" @input="menuFA = false"></v-date-picker>
                                </v-menu>
                              </v-col>
                              <!-- Estado civil -->
                              <v-col cols="12" sm="6" md="4">
                                <v-select v-model="editedItem.maritalStatus" :items="maritalStatusOptions"
                                  :rules="[rules.required]" label="Estado Civil" item-text="text"
                                  item-value="value"></v-select>
                              </v-col>
                              <!-- Servicio Militar -->
                              <v-col cols="12" sm="6" md="4">
                                <v-select v-model="editedItem.militaryService" :items="militaryServiceOptions"
                                  :rules="[rules.required]" label="Servicio Militar" item-text="text"
                                  item-value="value"></v-select>
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

                  <!-- Diálogo para confirmar la baja del empleado -->
                  <v-dialog v-model="dialogDelete" max-width="800px">
                    <v-card>
                      <v-card-title class="text-h5 d-flex justify-center">¿Quiere dar de baja a este empleado?</v-card-title>
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
    search:'',// variable utilizada para la búsqueda en la tabla
    valid: true,// Booleano que indica si el formulario es válido
    dialog: false,// Booleano que controla la visibilidad del diálogo de creación/edición de empleados
    dialogDelete: false,// Booleano que controla la visibilidad del diálogo de confirmación de eliminación
    menuFA: false, // Booleano que controla la visibilidad del menú de selección de la fecha de alta)
    menuFN: false,// Booleano que controla la visibilidad del menú de selección de la fecha de nacimiento)
    
    // Encabezados de las columnas de la tabla y sus valores correspondientes asociados
    headers: [
      { text: 'Id', value: 'idEmployee' },
      { text: 'NIF', value: 'nif' },
      { text: 'Nombre', value: 'name' },
      { text: '1º Apellido', value: 'surname1' },
      { text: '2º Apellido', value: 'surname2' },
      { text: 'Fecha Nacimiento', value: 'birthdate' },
      { text: 'Telefono', value: 'phone1' },
      // { text: 'Telefono 2', value: 'phone2' },
      { text: 'Email', value: 'email' },
      { text: 'Fecha Alta', value: 'hireDate' },
      { text: 'Estado Civil', value: 'maritalStatus' },
      { text: 'Servicio Militar', value: 'militaryService' },
      { text: 'Acciones', value: 'actions', sortable: false },
    ],
     // Opciones de selección (select formulario) para el campo de estado civil
    maritalStatusOptions: [
      { text: 'Casado', value: 'C' },
      { text: 'Soltero', value: 'S' }
    ],
     // Opciones de selección (select formulario) para el campo de servicio militar
    militaryServiceOptions: [
      { text: 'Si', value: 'S' },
      { text: 'No', value: 'N' }
    ],
    // Reglas de validación para los campos del formulario
    rules: {
      required: value => !!value || 'Requerido.',
      nif: value => /^[0-9]{8}[A-Z]$/.test(value) || 'NIF inválido.',
      phone: value => /^[0-9]{9}$/.test(value) || 'Teléfono inválido.',
      email: value => /.+@.+\..+/.test(value) || 'Email inválido.'
    },
    items: [],// Lista de empleados obtenida desde la API
    editedIndex: -1, // Índice del empleado que se está editando

     // Objeto que contiene los datos del empleado que se está editando/creando
    editedItem: {
      idEmployee: '',
      nif: '',
      name: '',
      surname1: '',
      surname2: '',
      birthdate: '',
      phone1: '',
      phone2: '',
      email: '',
      hireDate: '',
      maritalStatus: '',
      militaryService: '',
    },
     // Objeto que contiene los datos por defecto para un nuevo empleado
    defaultItem: {
      idEmployee: '',
      nif: '',
      name: '',
      surname1: '',
      surname2: '',
      birthdate: '',
      phone1: '',
      phone2: '',
      email: '',
      hireDate: '',
      maritalStatus: '',
      militaryService: '',
    },
  }),

  computed: {
    // Computed property para determinar el título del formulario de creación/edición
    formTitle() {
      return this.editedIndex === -1 ? ' Nuevo Empleado' : 'Editar Empleado';
    },
  },

  watch: {
    // Observa cambios en la propiedad `dialog`
    dialog(val) {
      val || this.close(); // Si el diálogo se cierra (val es false), se llama a `close`
    },
    // Observa cambios en la propiedad `dialogDelete`
    dialogDelete(val) {
      val || this.closeDelete(); // Si el diálogo de eliminación se cierra (val es false), se llama a `closeDelete`
    },
  },
  // Llama a `initialize` cuando el componente es creado y carga los datos de la api
  created() {
    this.initialize();
  },

  methods: {
     // Método para validar el formulario
    validate() {
      this.$refs.form.validate()
    },
    // Método para resetear el formulario
    reset() {
      this.$refs.form.reset()
    },
    // Método para resetear la validación del formulario
    resetValidation() {
      this.$refs.form.resetValidation()
    },
    // Método para inicializar los datos de la tabla desde la API
    async initialize() {
      try {
        const response = await axios.get('http://localhost:8080/employee/active');
        // Transforma los datos recibidos para la tabla para mostrar la cadena en vez del código de cada estado
        this.items = response.data.map(item => ({
          ...item,
          maritalStatus: this.transformMaritalStatus(item.maritalStatus),
          militaryService: this.transformMilitaryService(item.militaryService),
        }));
      } catch (error) {
        console.error('Error fetching data:', error);
      }
    },

    // Método para transformar el estado civil de código a texto
    transformMaritalStatus(maritalStatus) {
      switch (maritalStatus) {
        case 'S':
          return 'Soltero';
        case 'C':
          return 'Casado';
        default:
          return maritalStatus;
      }
    },
    // Método para transformar el estado del servicio militar de código a texto
    transformMilitaryService(militaryService) {
      switch (militaryService) {
        case 'S':
          return 'Si';
        case 'N':
          return 'No';
        default:
          return militaryService;
      }
    },
     // Método para editar un elemento de la tabla
    editItem(item) {
      this.editedIndex = this.items.indexOf(item);
      this.editedItem = Object.assign({}, item);
     // Convierte los valores legibles a sus valores originales para el formulario de edición
      this.editedItem.maritalStatus = this.inverseTransformMaritalStatus(this.editedItem.maritalStatus);
      this.editedItem.militaryService = this.inverseTransformMilitaryService(this.editedItem.militaryService);
      this.dialog = true;
    },
    // Método inverso para transformar estado civil a su valor original
    inverseTransformMaritalStatus(maritalStatus) {
      switch (maritalStatus) {
        case 'Soltero':
          return 'S';
        case 'Casado':
          return 'C';
        default:
          return maritalStatus;
      }
    },
    // Método inverso para transformar servicio militar a su valor original
    inverseTransformMilitaryService(militaryService) {
      switch (militaryService) {
        case 'Si':
          return 'S';
        case 'No':
          return 'N';
        default:
          return militaryService;
      }
    },
    // Método para eliminar un empleado de la tabla
    deleteItem(item) {
      this.editedIndex = this.items.indexOf(item);
      this.editedItem = Object.assign({}, item);
      this.dialogDelete = true;
    },
    // Confirmación de eliminación del empleado, lanza la petición a la api con el id del empleado al que dar de baja
    async deleteItemConfirm() {
      try {
        await axios.put(`http://localhost:8080/employee/terminate/${this.editedItem.idEmployee}`);
        this.items.splice(this.editedIndex, 1);
        Swal.fire({//Notificación de exito
          icon: "success",
          title: "El empleado ha sido dado de baja",
          showConfirmButton: false,
          timer: 1500
        });
        this.closeDelete();
      } catch (error) {
        console.error('Error deleting item:', error);
        Swal.fire({//Notificación de error
          icon: "error",
          title: "No puede dar de baja a un Empleado que está asignado a un proyecto",
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
          const isEditing = this.editedIndex > -1;
          const url = isEditing
            ? `http://localhost:8080/employee/${this.editedItem.idEmployee}`
            : 'http://localhost:8080/employee';
          const method = isEditing ? 'put' : 'post';
          
         // Convierte los valores antes de ser enviados al servidor
          const itemToSave = {
            ...this.editedItem,
            maritalStatus: this.inverseTransformMaritalStatus(this.editedItem.maritalStatus),
            militaryService: this.inverseTransformMilitaryService(this.editedItem.militaryService)
          };

          const response = await axios({
            method: method,
            url: url,
            data: JSON.stringify(itemToSave),
            headers: {
              'Content-Type': 'application/json'
            }
          });

          if (response.status === 200 || response.status === 201) {
            Swal.fire({
              icon: "success",
              title: isEditing ? "El empleado ha sido editado" : "El empleado ha sido creado",
              showConfirmButton: false,
              timer: 1500
            });

            const transformedItem = {//Transforma los datos a su cadena correspondiente antes de ser mostrados en la tabla
              ...response.data,
              maritalStatus: this.transformMaritalStatus(response.data.maritalStatus),
              militaryService: this.transformMilitaryService(response.data.militaryService)
            };

            if (isEditing) {
              Object.assign(this.items[this.editedIndex], transformedItem);
            } else {
              this.items.push(transformedItem);
            }
            this.close();
          }
        }
      } catch (error) {
        console.error('Error saving item:', error);
        Swal.fire({
          icon: "error",
          title: "Se ha producido un error",
          showConfirmButton: false,
          timer: 1500
        });
        this.close();
      }
    }
  },
};
</script>