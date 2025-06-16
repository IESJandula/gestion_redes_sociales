import { createRouter, createWebHistory } from 'vue-router'
import Login from '../components/Login.vue'
import Publicaciones from '../components/Publicaciones.vue'
import Admin from "../components/Admin.vue"


const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'login',
      component: Login,
    },
    {
      path: '/publicaciones',
      name: 'publicaciones',
      component: Publicaciones,
    },
    {
      path: '/admin',
      name: 'admin',
      component: Admin,
    }
   
  ],
})

export default router
