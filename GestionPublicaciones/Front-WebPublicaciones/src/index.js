import { createRouter, createWebHistory } from "vue-router";
import LoginView from "@/views/LoginView.vue";
import PublicacionesView from "@/views/PublicacionesView.vue";
import { auth } from "@/firebase";

const routes = [
  { path: "/", component: LoginView },
  {
    path: "/publicaciones",
    component: PublicacionesView,
    meta: { requiresAuth: true }, // Solo accesible si está autenticado
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

// Verificar autenticación antes de acceder a ciertas rutas
router.beforeEach((to, from, next) => {
  const user = auth.currentUser;
  if (to.meta.requiresAuth && !user) {
    next("/");
  } else {
    next();
  }
});

export default router;
