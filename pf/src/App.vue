<template>
  <v-app>
    <!-- Menu lateral -->
    <v-navigation-drawer v-model="drawer" app temporary class="blue-background white-text">
      <v-list dense>
        <!-- Elementos de la lista de navegación -->
        <v-list-item link v-for="item in items" :key="item.title" :to="item.route">
          <v-list-item-icon>
            <v-icon class="white-text">{{ item.icon }}</v-icon>
          </v-list-item-icon>
          <v-list-item-content>
            <v-list-item-title class="white-text">{{ item.title }}</v-list-item-title>
          </v-list-item-content>
        </v-list-item>
      </v-list>
    </v-navigation-drawer>

    <!-- Barra de aplicación -->
    <v-app-bar app class="blue-background white-text">
      <v-app-bar-nav-icon @click="drawer = !drawer" class="white-text"></v-app-bar-nav-icon>
      <v-toolbar-title>{{ pageTitle }}</v-toolbar-title>
    </v-app-bar>

    <!-- Contenido principal -->
    <v-main>
      <router-view></router-view>
    </v-main>

    <!-- Pie de página -->
    <v-footer height="auto"  absolute app>
      <v-container>
        <v-row justify="center">
          <v-col cols="12" class="text-center">
            <span>&copy; 2024 Jennifer Madrigal. All rights reserved.</span>
          </v-col>
        </v-row>
      </v-container>
    </v-footer>
  </v-app>
</template>

<script>
export default {
  name: 'App',
  data: () => ({
    drawer: null, // Controla la visibilidad de la navegación lateral
    items: [ // Elementos de la navegación lateral
      { title: 'Home', icon: 'mdi-home', route: '/' },
      { title: 'Empleados', icon: 'mdi-account-group', route: '/employee' },
      { title: 'Proyectos', icon: 'mdi-animation', route: '/project' },
      { title: 'Asignacion', icon: 'mdi-account-arrow-right', route: '/assignation' }
    ],
    routeTitles: { // Títulos de las rutas para mostrar en la barra de aplicación
      '/': 'Home',
      '/employee': 'Gestión de Empleados',
      '/project': 'Gestión de Proyectos',
      '/assignation': 'Asignación'
    }
  }),
  computed: {
    // Computed property para obtener el título de la página actual basado en la ruta
    pageTitle() {
      return this.routeTitles[this.$route.path] || 'Gestion';
    }
  }
}
</script>

<style>
.blue-background {
  background-color: #1976D2 !important; /* Azul de Vuetify */
}
.white-text {
  color: white !important; /* Texto blanco */
}

</style>