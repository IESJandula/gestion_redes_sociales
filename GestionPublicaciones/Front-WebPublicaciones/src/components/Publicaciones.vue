<template>
  <div id="publicaciones-container">
    <header class="header">
      <h1>Crear Publicación</h1>
      <button class="logout-btn" @click="cerrarSesion">Cerrar Sesión</button>
    </header>

    <main>
      <form @submit.prevent="publicar">
        <label for="titulo">Título:</label>
        <input type="text" id="titulo" v-model="titulo" required />

        <label for="descripcion">Descripción:</label>
        <textarea
          id="descripcion"
          v-model="descripcion"
          placeholder="Escribe tu descripción aquí..."
          required
        ></textarea>

        <label for="imagenes">Imágenes/Videos:</label>
        <input
          type="file"
          id="imagenes"
          multiple
          @change="seleccionarArchivos"
          accept="image/*,video/*"
        />

        <div class="preview-container">
          <div
            v-for="(archivo, index) in archivos"
            :key="index"
            class="preview"
          >
            <div class="media-preview">
              <img
                v-if="archivo.type.startsWith('image/')"
                :src="archivo.url"
              />
              <video v-else controls :src="archivo.url" />
            </div>
            <button class="bin-button" @click="eliminarArchivo(index, $event)">
              <svg
                class="bin-top"
                viewBox="0 0 39 7"
                fill="none"
                xmlns="http://www.w3.org/2000/svg"
              >
                <line
                  y1="5"
                  x2="39"
                  y2="5"
                  stroke="white"
                  stroke-width="4"
                ></line>
                <line
                  x1="12"
                  y1="1.5"
                  x2="26.0357"
                  y2="1.5"
                  stroke="white"
                  stroke-width="3"
                ></line>
              </svg>
              <svg
                class="bin-bottom"
                viewBox="0 0 33 39"
                fill="none"
                xmlns="http://www.w3.org/2000/svg"
              >
                <mask id="path-1-inside-1_8_19" fill="white">
                  <path
                    d="M0 0H33V35C33 37.2091 31.2091 39 29 39H4C1.79086 39 0 37.2091 0 35V0Z"
                  ></path>
                </mask>
                <path
                  d="M0 0H33H0ZM37 35C37 39.4183 33.4183 43 29 43H4C-0.418278 43 -4 39.4183 -4 35H4H29H37ZM4 43C-0.418278 43 -4 39.4183 -4 35V0H4V35V43ZM37 0V35C37 39.4183 33.4183 43 29 43V35V0H37Z"
                  fill="white"
                  mask="url(#path-1-inside-1_8_19)"
                ></path>
                <path d="M12 6L12 29" stroke="white" stroke-width="4"></path>
                <path d="M21 6V29" stroke="white" stroke-width="4"></path>
              </svg>
            </button>
          </div>
        </div>

        <label for="musica">Nombre de la Canción:</label>
        <input type="text" id="musica" v-model="musica" />

        <button type="submit">Publicar</button>
      </form>
    </main>
  </div>
</template>

<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router";

import { auth } from "@/firebase";
import { signOut, getAuth } from "firebase/auth";

import axios from "axios";
import "emoji-picker-element";

const router = useRouter();

const titulo = ref("");
const descripcion = ref("");
const musica = ref("");
const archivos = ref([]);

const cerrarSesion = async () => {
  await signOut(auth);
  router.push("/");
};

const eliminarArchivo = (index, event) => {
  event.preventDefault();
  archivos.value.splice(index, 1);
};
const seleccionarArchivos = (event) => {
  const files = Array.from(event.target.files);
  files.forEach((file) => {
    if (!archivos.value.some((archivo) => archivo.file.name === file.name)) {
      archivos.value.push({
        file,
        url: URL.createObjectURL(file),
        type: file.type,
      });
    }
  });
};
const publicar = async () => {
  try {
    const token = localStorage.getItem("token");
    if (!token) {
      alert("No estás autenticado.");
      return;
    }

    // 1. Crear publicación sin URLs
    const nuevaPublicacion = {
      titulo: titulo.value,
      descripcion: descripcion.value,
      cancion: musica.value,
    };

    const response = await axios.post(
      "http://localhost:8080/publicacion/crear",
      nuevaPublicacion
    );

    const publicacion_id = response.data.id;

    // 2. Subir archivos uno a uno y guardar URLs en la tabla relacionada
    for (const archivoObj of archivos.value) {
      const archivo = archivoObj.file;
      const formData = new FormData();
      formData.append("file", archivo);

      // Subir archivo y obtener URL
      const uploadResp = await axios.post(
        "http://localhost:8080/archivos/upload",
        formData
      );

      const archivo_url = uploadResp.data.url;

      // Guardar la URL en publicacion_archivos
      await axios.post("http://localhost:8080/publicacion_archivos/crear", {
        archivoUrl: archivo_url,
        publicacion: {
          id: publicacion_id,
        },
      });
    }

    alert("Publicación creada exitosamente con archivos!");

    titulo.value = "";
    descripcion.value = "";
    musica.value = "";
    archivos.value = [];
  } catch (error) {
    console.error("Error al crear la publicación:", error);
    alert("Error al crear la publicación. Revisa consola.");
  }
};
</script>

<style scoped>
@import url("https://fonts.googleapis.com/css2?family=Roboto:wght@300;400;700&display=swap");

* {
  font-family: "Roboto", sans-serif;
  margin: 0;
  padding: 0;
}

#publicaciones-container {
  max-width: 600px;
  width: 100%;
  margin: 20px auto;
  padding: 20px;
}

.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  background: linear-gradient(to right, #0f4c75, #3282b8);
  padding: 20px;
  border-radius: 10px;
  color: white;
  box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.3);
}

.header h1 {
  font-size: 1.5rem;
}

.logout-btn {
  background: white;
  color: #0f4c75;
  border: none;
  padding: 8px 12px;
  border-radius: 5px;
  cursor: pointer;
  font-weight: bold;
  transition: 0.3s;
}

.logout-btn:hover {
  background: #bbe1fa;
}

main {
  background: white;
  padding: 20px;
  border-radius: 10px;
  margin-top: 20px;
  box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.1);
}

form {
  display: flex;
  flex-direction: column;
}

label {
  margin-top: 10px;
  font-weight: bold;
  color: #0f4c75;
}

input,
textarea {
  padding: 10px;
  margin-top: 5px;
  border: 1px solid #ddd;
  border-radius: 5px;
  transition: 0.3s;
}

input:focus,
textarea:focus {
  border-color: #3282b8;
  outline: none;
}

textarea {
  resize: none;
  height: 80px;
}

button {
  margin-top: 15px;
  padding: 10px;
  border: none;
  border-radius: 5px;
  background: #316b8a;
  color: white;
  font-size: 1rem;
  cursor: pointer;
  transition: 0.3s;
}

button:hover {
  background: #0873b9;
}

.preview-container {
  display: flex;
  gap: 10px;
  flex-wrap: wrap;
  margin: 10px 0;
}
.preview {
  position: relative;
}
.media-preview img,
.media-preview video {
  width: 200px;
  height: 200px;
  object-fit: cover;
}
.preview button {
  position: absolute;
  top: 0;
  right: 0;
  background: rgb(199, 28, 28);
  border: none;
  color: white;
  cursor: pointer;
  width: 40px;
  height: 40px;
  font-size: 20px;
}

/* From Uiverse.io by vinodjangid07 */
.bin-button {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  width: 55px;
  height: 55px;
  border-radius: 15px;
  background-color: rgb(255, 95, 95);
  cursor: pointer;
  border: 3px solid rgb(255, 201, 201);
  transition-duration: 0.3s;
}
.bin-bottom {
  width: 15px;
}
.bin-top {
  width: 17px;
  transform-origin: right;
  transition-duration: 0.3s;
}
.bin-button:hover .bin-top {
  transform: rotate(45deg);
}
.bin-button:hover {
  background-color: rgb(255, 0, 0);
}
.bin-button:active {
  transform: scale(0.9);
}

/* Responsive Styles */

@media (min-width: 1024px) {
  #publicaciones-container {
    max-width: 800px;
    padding: 40px;
  }

  .header h1 {
    font-size: 2rem;
  }

  .logout-btn {
    font-size: 1.2rem;
  }

  button {
    font-size: 1.1rem;
  }
}

@media (max-width: 768px) {
  #publicaciones-container {
    padding: 15px;
  }

  .header h1 {
    font-size: 1.3rem;
  }

  .preview-container {
    justify-content: center;
  }
}

@media (max-width: 600px) {
  #publicaciones-container {
    padding: 10px;
  }

  .preview-container {
    flex-direction: column;
    align-items: center;
  }

  .media-preview img,
  .media-preview video {
    width: 100%;
    height: auto;
  }

  button {
    font-size: 1rem;
    padding: 8px 12px;
  }
}
</style>
