<template>
    <v-container fluid>
        <!-- Fila para la selección del proyecto -->
        <v-row class="d-flex justify-center" >
            <v-col cols="12" sm="10" md="8">
                <v-card>
                    <v-card-title>
                        <v-col cols="12" class="d-flex justify-center">
                        <h3>Proyectos</h3>
                   
                        <v-spacer></v-spacer>
                            <v-btn color="primary" @click="acceptChanges">Guardar Cambios</v-btn>
                        </v-col>
                    </v-card-title>
                    <v-card-text>
                        <v-select :items="projects" label="Seleccione un proyecto" item-text="description"
                            item-value="idProject" solo @change="onProjectSelect">
                        </v-select>
                    </v-card-text>
                </v-card>
            </v-col>
        </v-row>

        <!-- Fila para la tabla de empleados -->
        <v-row class="d-flex justify-center" style="margin-top: 20px;">
            <v-col cols="12" sm="10" md="8">
                <v-card>
                    <v-card-title>
                        <h3>Empleados</h3>
                    </v-card-title>
                    <v-card-text>
                        <v-text-field v-model="search" append-icon="mdi-magnify" label="Buscar empleado" single-line
                            hide-details></v-text-field>
                        <v-data-table :headers="headers" :items="employees" :search="search" class="elevation-1 dense-table"
                            :items-per-page="5">
                            <template v-slot:item.actions="{ item }">
                                <v-simple-checkbox v-model="item.assigned"></v-simple-checkbox>
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
    name: 'AssignationComponent',
    data() {
        return {
            search: '',
            projects: [],  // Lista de proyectos
            employees: [], // Lista de empleados
            selectedEmployeeIds: [], // IDs de empleados asignados
            selectedProjectId: '', // ID del proyecto seleccionado
            headers: [
                { text: 'ID', value: 'idEmployee' },
                { text: 'Nombre', value: 'name' },
                { text: 'Apellidos', value: 'fullName' },
                { text: 'Asignado', value: 'actions' }
            ],
        };
    },

    methods: {
        // Petición para obtener los proyectos activos del servidor
        async getProyects() {
            try {
                const response = await axios.get('http://localhost:8080/project/active');
                this.projects = response.data.map(project => ({
                    idProject: project.idProject,
                    description: project.description
                }));
            } catch (error) {
                console.error('Error fetching projects:', error);
            }
        },

        // Petición para obtener los IDs de los empleados asignados al proyecto seleccionado
        async onProjectSelect(selectedId) {
            try {
                this.selectedProjectId = selectedId;
                const response = await axios.get(`http://localhost:8080/assignment/${selectedId}`);
                this.selectedEmployeeIds = response.data;
                await this.getEmployees(); // Cargar empleados después de obtener los IDs del proyecto seleccionado
            } catch (error) {
                console.error('Error fetching selected project employee IDs:', error);
            }
        },

        // Petición para obtener empleados activos del servidor
        async getEmployees() {
            try {
                const response = await axios.get('http://localhost:8080/employee/active');
                this.employees = response.data.map(employee => ({
                    idEmployee: employee.idEmployee,
                    name: employee.name,
                    fullName: `${employee.surname1} ${employee.surname2}`,
                    assigned: this.isEmployeeSelected(employee.idEmployee) // Establecer estado de asignación
                }));
            } catch (error) {
                console.error('Error fetching employees:', error);
            }
        },

        // Comprueba si un empleado está asignado al proyecto seleccionado
        isEmployeeSelected(idEmployee) {
            return this.selectedEmployeeIds.includes(idEmployee);
        },

         // Maneja los cambios y envia la solicitud de actualización al servidor
        async acceptChanges() {
            const selectedIds = this.employees
                .filter(employee => employee.assigned)
                .map(employee => employee.idEmployee);

            const employeeRemove = this.selectedEmployeeIds.filter(id => !selectedIds.includes(id));//recogemos los ids de los empleados que ya no están asignados al proyecto
            const employeeAdd = selectedIds.filter(id => !this.selectedEmployeeIds.includes(id)); //recogemos los ids de los empleados que se han asignado al proyecto

            const updateData = { //construimos el objeto con los datos necesarios para la petición
                idProject: this.selectedProjectId,
                idsEmployeeRemove: employeeRemove,
                idsEmployeeAdd: employeeAdd
            };

            try {
                const response = await axios.post('http://localhost:8080/assignment/update', updateData, {
                    headers: { 'Content-Type': 'application/json' }
                });
                if (response.status === 200) {
                    // Notificación de éxito
                    Swal.fire({
                        icon: "success",
                        title: "Los cambios han sido guardados",
                        showConfirmButton: false,
                        timer: 1500
                    });
                }
                await this.onProjectSelect(this.selectedProjectId); // Actualizar datos después de la actualización
            } catch (error) {
                console.error('Error updating assignments:', error);
                Swal.fire({
                    icon: "error",
                    title: "Se ha producido un error",
                    showConfirmButton: false,
                    timer: 1500
                });
            }
        }
    },

    mounted() {
        this.getProyects();
    }
};
</script>

<style scoped>
.v-container {
    height: 100vh;
    /* Coge el alto de la pantalla */
}
</style>

  