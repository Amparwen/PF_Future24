<template>
    <v-container class="fill-height">
      <v-row align="center" justify="center">
        <v-col cols="12" sm="8" md="6">
          <v-card class="blue-background white-text">
            <v-card-title class="text-h5">Bienvenido a la Gestión de Recursos</v-card-title>
            <v-card-text>
              Seleccione una de las opciones a continuación para gestionar los recursos de su empresa.
            </v-card-text>
            <v-card-actions>
              <v-btn :to="{ path: '/employee' }" class="white--text">
                <v-icon left>mdi-account-group</v-icon>
                Empleados
              </v-btn>
              <v-btn :to="{ path: '/project' }" class="white--text">
                <v-icon left>mdi-animation</v-icon>
                Proyectos
              </v-btn>
              <v-btn :to="{ path: '/assignation' }" class="white--text">
                <v-icon left>mdi-account-arrow-right</v-icon>
                Asignación
              </v-btn>
            </v-card-actions>
          </v-card>
        </v-col>
      </v-row>
    </v-container>
  </template>
  
  <script>
  export default {
    name: 'Home'
  }
  </script>
  
  <style scoped>
  .blue-background {
    background-color: #1976D2 !important; /* Vuetify blue */
  }
  .white-text {
    color: white !important;
  }
  .white--text {
    color: white !important;
  }
  </style>
  