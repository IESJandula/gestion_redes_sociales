<template>
  <div class="card">
    <div class="image-container" v-if="archivos.length > 0">
      <img
        v-for="archivo in archivos"
        :key="archivo.id"
        :src="`http://localhost:8080${archivo.archivoUrl}`"
        alt="Imagen de publicación"
        class="image"
      />
    </div>

    <div class="content">
      <h3 class="titulo">{{ publicacion.titulo }}</h3>
      <p class="estado-actual">
        Estado: <strong>{{ publicacion.estado || "SIN ESTADO" }}</strong>
      </p>
      <p><strong>Descripción:</strong> {{ publicacion.descripcion }}</p>

      <label for="estadoSelect"><strong>Cambiar estado:</strong></label>
      <select id="estadoSelect" v-model="nuevoEstado" @change="cambiarEstado">
        <option disabled value="">Seleccione estado</option>
        <option value="PENDIENTE">PENDIENTE</option>
        <option value="PUBLICADO">PUBLICADO</option>
      </select>
    </div>
  </div>
</template>

<script setup>
import { ref, watchEffect, onMounted } from "vue";
import axios from "axios";

const props = defineProps({
  publicacion: Object,
});
const emit = defineEmits(["estado-cambiado"]);

const nuevoEstado = ref("");
const archivos = ref([]); // Aquí guardamos los archivos (imágenes)

watchEffect(() => {
  nuevoEstado.value = props.publicacion.estado || "";
});

// Función para cargar las imágenes de la publicación
const cargarArchivos = async () => {
  try {
    const response = await axios.get(
      `http://localhost:8080/publicacion_archivos/${props.publicacion.id}/archivos`
    );

    archivos.value = response.data;
    console.log("Archivos para mostrar:", archivos.value);
  } catch (error) {
    console.error("Error al cargar archivos:", error);
  }
};

onMounted(() => {
  cargarArchivos();
});

const cambiarEstado = async () => {
  try {
    const token = localStorage.getItem("token");
    await axios.post(
      `http://localhost:8080/publicacion/estado/${props.publicacion.id}`,
      { estado: nuevoEstado.value },
      {
        headers: {
          Authorization: `Bearer ${token}`,
          "Content-Type": "application/json",
        },
      }
    );
    emit("estado-cambiado");
  } catch (error) {
    console.error("Error al cambiar estado:", error);
    alert("No se pudo actualizar el estado.");
  }
};
</script>

<style scoped>
.card {
  background: white;
  border-radius: 10px;
  box-shadow: 2px 2px 8px rgba(0, 0, 0, 0.1);

  transition: 0.3s;
  max-width: 100%;
  margin-bottom: 20px;
}

.image-container {
  width: 100%;
  height: 200px;
  overflow-x: auto;
  overflow-y: hidden;
  white-space: nowrap;
  padding: 10px 0;
  box-sizing: border-box;
  background-color: #f0f0f0;
}

.image {
  display: inline-block;
  width: 150px;
  height: 100%;
  object-fit: cover;
  margin-right: 10px;
  vertical-align: middle;
  user-select: none;
  cursor: pointer;
}

.content {
  padding: 15px;
}

.titulo {
  font-size: 20px;
  margin-bottom: 8px;
}

.estado-actual {
  font-size: 14px;
  margin-bottom: 10px;
  color: #444;
}

p {
  font-size: 14px;
  margin: 4px 0;
}

label {
  display: block;
  margin-top: 10px;
  font-weight: bold;
}

select {
  padding: 6px;
  border-radius: 5px;
  border: 1px solid #ddd;
  width: 100%;
}

/* Responsive */
@media (max-width: 768px) {
  .titulo {
    font-size: 18px;
  }
  .image-container {
    height: 160px;
  }
}
</style>
