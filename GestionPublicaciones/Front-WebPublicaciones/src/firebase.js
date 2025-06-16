import { initializeApp } from "firebase/app";
import { getAuth } from "firebase/auth";
import { getStorage } from "firebase/storage";  

const firebaseConfig = {
  apiKey: "AIzaSyDTqMVZX6920VbeOarZ5qPms3LmIRkDqF4",
  authDomain: "gestionpublicaciones-2f389.firebaseapp.com",
  projectId: "gestionpublicaciones-2f389",
  storageBucket: "gestionpublicaciones-2f389.appspot.com",
  messagingSenderId: "27857643037",
  appId: "1:27857643037:web:0bb08fdb79829b0e2f88f4"
};

const app = initializeApp(firebaseConfig);
const auth = getAuth(app);
const storage = getStorage(app); 

console.log("🔥 Firebase inicializado correctamente");

export { auth, storage };  








