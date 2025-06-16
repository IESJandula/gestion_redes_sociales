<template>
  <div id="login-container">
    <header class="header">
      <img
        src="../assets/imgs/fotoHeader.png"
        alt="Header Image"
        class="header-img"
      />
    </header>
    <div>
      <h1>Iniciar sesión para subir publicaciones</h1>
    </div>

    <main>
      <form id="login-form" @submit.prevent="login">
        <label for="email">Correo electrónico:</label>
        <input type="email" id="email" v-model="email" required />

        <label for="password">Contraseña:</label>
        <input type="password" id="password" v-model="password" required />

        <button type="submit">Iniciar sesión</button>

        <p v-if="error" class="error">{{ error }}</p>
      </form>
    </main>
  </div>
</template>

<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router";
import { auth } from "@/firebase";
import { signInWithEmailAndPassword } from "firebase/auth";

const router = useRouter();
const email = ref("");
const password = ref("");
const error = ref("");
const login = async () => {
  try {
    if (email.value === "admin@gmail.com" && password.value === "1234") {
      console.log(" Usuario admin detectado");
      router.push("/admin");
      return;
    }

    const userCredential = await signInWithEmailAndPassword(
      auth,
      email.value,
      password.value
    );

    console.log(" Usuario autenticado:", userCredential.user);

    const token = await userCredential.user.getIdToken();
    console.log("🔑 Token JWT:", token);

    localStorage.setItem("token", token);

    router.push("/publicaciones");
  } catch (err) {
    console.error("Código de error:", err.code);

    // Mostrar mensajes personalizados
    switch (err.code) {
      case "auth/user-not-found":
        error.value = "No existe una cuenta con este correo.";
        break;
      case "auth/wrong-password":
        error.value = "Contraseña incorrecta. Inténtalo de nuevo.";
        break;
      case "auth/invalid-email":
        error.value = "Correo electrónico no válido.";
        break;
      case "auth/invalid-credential":
        error.value = "Correo o contraseña incorrectos.";
        break;
      default:
        error.value =
          "Ocurrió un error al iniciar sesión. Inténtalo más tarde.";
        break;
    }
  }
};
</script>

<style scoped>
@import url("https://fonts.googleapis.com/css2?family=Roboto:wght@300;400;700&display=swap");

* {
  font-family: "Roboto", sans-serif;
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

#login-container {
  display: flex;
  flex-direction: column;
  min-height: 100vh;
}

header {
  position: relative;
  width: 100%;
  margin-bottom: 0;
}

header img {
  width: 100%;
  height: auto;
  object-fit: cover;
}

/* TÍTULO */
h1 {
  background-color: rgb(43, 126, 252);
  text-align: center;
  padding: 20px 10px;
  font-size: 28px;
  color: white;
  margin: 0;
  margin-top: -10px;
}

/* FORMULARIO */
form {
  display: flex;
  flex-direction: column;
  gap: 20px;
  margin: 30px auto;
  width: 90%;
  padding: 20px;
  text-align: center;
}

input {
  width: 100%;
  padding: 10px 15px;
  font-size: 16px;
  border: 1px solid #ccc;
  border-radius: 30px;
  outline: none;
  transition: border-color 0.3s ease;
}

input:focus {
  border-color: #0072ff;
}

button {
  padding: 12px;
  background-color: #0072ff;
  color: white;
  font-size: 18px;
  border: none;
  border-radius: 30px;
  cursor: pointer;
  transition: background-color 0.3s ease;
  width: 100%;
}

button:hover {
  background-color: #005bb5;
}

.error {
  color: red;
  font-size: 14px;
  margin-top: 10px;
}
/* 1024px */
@media (max-width: 1024px) {
  header {
    object-fit: cover;
  }
  h1 {
    font-size: 26px;
  }

  form {
    width: 90%;
  }
}

/* 768px */
@media (max-width: 768px) {
  h1 {
    font-size: 20px;
    padding: 16px;
  }

  form {
    width: 90%;
    font-size: 15px;
  }

  input,
  button {
    font-size: 15px;
  }
}

/*  480px */
@media (max-width: 480px) {
  h1 {
    font-size: 17px;
    padding: 12px;
  }

  form {
    margin: 0 auto;
    width: 90%;
    font-size: 14px;
  }

  input,
  button {
    font-size: 14px;
  }
}
</style>
