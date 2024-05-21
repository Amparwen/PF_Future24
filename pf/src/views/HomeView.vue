<template>
    <!-- Contenedor principal que llena la altura disponible -->
    <v-container class="fill-height">
        <v-row align="center" justify="center">
            <v-col cols="12" sm="12" md="12">
                 <!-- Tarjeta principal -->
                <v-card>
                     <!-- Imagen en la tarjeta con título -->
                    <v-img class="white-text align-end" src="@/assets/1.jpg" height="400px">
                        <v-card-title class="text-h3">Bienvenido a la Gestión de Recursos</v-card-title>
                    </v-img>
                    <!-- Texto en la tarjeta -->
                    <v-card-text>
                        Seleccione una de las opciones a continuación para gestionar los recursos de su empresa.
                    </v-card-text>
                    <!-- Acciones de la tarjeta -->
                    <v-card-actions>
                         <!-- Fila para los botones -->
                        <v-row class="d-flex justify-center" >
                            <v-col class="d-flex justify-center" cols="4" sm="4" md="4">
                                <v-btn color="primary" :to="{ path: '/employee' }" class="white-text">
                                    <v-icon left>mdi-account-group</v-icon>
                                    Empleados
                                </v-btn>
                            </v-col>
                            <v-col class="d-flex justify-center" cols="4" sm="4" md="4">
                                <v-btn color="primary" :to="{ path: '/project' }" class="white-text">
                                    <v-icon left>mdi-animation</v-icon>
                                    Proyectos
                                </v-btn>
                            </v-col>
                            <v-col class="d-flex justify-center" cols="14" sm="4" md="4">
                                <v-btn color="primary" :to="{ path: '/assignation' }" class="white-text">
                                    <v-icon left>mdi-account-arrow-right</v-icon>
                                    Asignación
                                </v-btn>
                            </v-col>
                        </v-row>

                    </v-card-actions>
                </v-card>
            </v-col>
        </v-row>
    </v-container>
</template>
  
<script>
export default {
    name: 'Home', 
    data: () => ({
    }),
}
</script>
  
<style scoped>
/* Estilos personalizados */
.blue-background {
    background-color: #1976D2 !important; /* Azul de Vuetify */
}

.white-text{
    color: white !important; /* Texto blanco */
}
</style>