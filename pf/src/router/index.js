import Vue from 'vue'
import VueRouter from 'vue-router'
import EmployeeView from '../views/EmployeeView.vue'


Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'HomeView',
    component: () => import(/* webpackChunkName: "about" */ '../views/HomeView.vue')
  },
  {
    path: '/employee',
    name: 'EmployeeView',
    component: EmployeeView
  },
  {
    path: '/project',
    name: 'ProjectView',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/ProjectView.vue')
  },
  {
    path: '/assignation',
    name: 'AssignationView',
    component: () => import(/* webpackChunkName: "about" */ '../views/AssignationView.vue')
  },
]

const router = new VueRouter({
  routes
})

export default router
