<template>
  <div class="container">
    <h1 class="title">Administrar Publicaciones</h1>

    <section>
      <button @click="Volver">Volver</button>
      <h2 class="subtitle">Pendientes:</h2>
      <div v-if="pendientes.length" class="publicaciones">
        <PublicacionCard
          v-for="p in pendientes"
          :key="p.id"
          :publicacion="p"
          @estado-cambiado="cargarPublicaciones"
        />
      </div>
      <p v-else>No hay publicaciones pendientes.</p>
    </section>

    <section>
      <h2 class="subtitle">Completadas:</h2>
      <div v-if="completadas.length" class="publicaciones">
        <PublicacionCard
          v-for="p in completadas"
          :key="p.id"
          :publicacion="p"
          @estado-cambiado="cargarPublicaciones"
        />
      </div>
      <p v-else>No hay publicaciones completadas.</p>
    </section>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from "vue";
import axios from "axios";
import { auth } from "@/firebase";
import { signOut, getAuth } from "firebase/auth";
import PublicacionCard from "./PublicacionCard.vue";
import { useRouter } from "vue-router";
const router = useRouter();

const publicaciones = ref([]);

const cargarPublicaciones = async () => {
  try {
    const response = await axios.get("http://localhost:8080/publicacion", {});
    console.log(response);
    publicaciones.value = response.data;
  } catch (error) {
    console.error(" Error al obtener publicaciones:", error);
  }
};

const pendientes = computed(() =>
  publicaciones.value.filter((p) => p.estado === "PENDIENTE")
);

const completadas = computed(() =>
  publicaciones.value.filter(
    (p) => p.estado === "APROBADO" || p.estado === "PUBLICADO"
  )
);

onMounted(() => {
  cargarPublicaciones();
});

const Volver = async () => {
  await signOut(auth);
  router.push("/");
};
</script>

<style scoped>
.image {
  width: 200px;
  height: auto;
  margin-right: 10px;
}

.container {
  max-width: 1000px;
  margin: auto;
  padding: 20px;
  font-family: Arial, sans-serif;
}

.title {
  text-align: center;
  font-size: 28px;
  margin-bottom: 30px;
  color: #002147;
}

.subtitle {
  font-size: 22px;
  margin: 20px 0 10px;
  color: #0f4c75;
}

.publicaciones {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(280px, 1fr));
  gap: 20px;
}

/* 1024px */
@media (max-width: 1024px) {
  .title {
    font-size: 24px;
  }

  .subtitle {
    font-size: 20px;
  }

  .publicaciones {
    grid-template-columns: repeat(auto-fill, minmax(240px, 1fr));
  }
}

/* 768px  */
@media (max-width: 768px) {
  .title {
    font-size: 22px;
  }

  .subtitle {
    font-size: 18px;
  }

  .publicaciones {
    grid-template-columns: repeat(auto-fill, minmax(200px, 1fr));
  }
}

/* 480px  */
@media (max-width: 480px) {
  .title {
    font-size: 20px;
  }

  .subtitle {
    font-size: 18px;
  }

  .publicaciones {
    grid-template-columns: 1fr;
  }
}
</style>
