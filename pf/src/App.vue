<template>
  <v-app>
    <v-navigation-drawer v-model="drawer" app temporary class="blue-background white-text">
      <v-list dense>
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

    <v-app-bar app class="blue-background white-text">
      <v-app-bar-nav-icon @click="drawer = !drawer" class="white-text"></v-app-bar-nav-icon>
      <v-toolbar-title>{{ pageTitle }}</v-toolbar-title>
    </v-app-bar>

    <v-main>
      <router-view></router-view>
    </v-main>
    <v-footer app>
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
    drawer: null,
    items: [
      { title: 'Home', icon: 'mdi-home', route: '/' },
      { title: 'Empleados', icon: 'mdi-account-group', route: '/employee' },
      { title: 'Proyectos', icon: 'mdi-animation', route: '/project' },
      { title: 'Asignacion', icon: 'mdi-account-arrow-right', route: '/assignation' }
    ],
    routeTitles: {
      '/': 'Home',
      '/employee': 'Empleados',
      '/project': 'Proyectos',
      '/assignation': 'Asignacion'
    }
  }),
  computed: {
    pageTitle() {
      return this.routeTitles[this.$route.path] || 'Gestion';
    }
  }
}
</script>

<style>
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
