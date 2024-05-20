<template>
    <v-container class="fill-height">
        <v-row align="center" justify="center">
            <v-col cols="12" sm="12" md="12">
                <v-card>
                    <v-img class="white--text align-end" src="@/assets/1.jpg" height="400px">
                        <v-card-title class="text-h3">Bienvenido a la Gestión de Recursos</v-card-title>
                    </v-img>
                    <v-card-text>
                        Seleccione una de las opciones a continuación para gestionar los recursos de su empresa.
                    </v-card-text>
                    <v-card-actions>

                        <v-row class="d-flex justify-center" >
                            <v-col class="d-flex justify-center" cols="4" sm="4" md="4">
                                <v-btn color="primary" :to="{ path: '/employee' }" class="white--text">
                                    <v-icon left>mdi-account-group</v-icon>
                                    Empleados
                                </v-btn>
                            </v-col>
                            <v-col class="d-flex justify-center" cols="4" sm="4" md="4">
                                <v-btn color="primary" :to="{ path: '/project' }" class="white--text">
                                    <v-icon left>mdi-animation</v-icon>
                                    Proyectos
                                </v-btn>
                            </v-col>
                            <v-col class="d-flex justify-center" cols="14" sm="4" md="4">
                                <v-btn color="primary" :to="{ path: '/assignation' }" class="white--text">
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
        card: [
            { title: 'Bienvenido a la Gestión de Recursos', src: '@/assets/1.jpg', flex: 12 },
        ],
    }),
}
</script>
  
<style scoped>
.blue-background {
    background-color: #1976D2 !important;
    /* Vuetify blue */
}

.white-text {
    color: white !important;
}

.white--text {
    color: white !important;
}
</style>
  