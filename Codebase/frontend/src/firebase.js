import { initializeApp } from "firebase/app";
import { getAuth } from "firebase/auth";
import { getFirestore } from "firebase/firestore";

const firebaseConfig = {
  apiKey: "AIzaSyC0VBzlAXNixGhEHT36vz3Tmj4wnRN-3WE",
  authDomain: "bookbuddy-c0df9.firebaseapp.com",
  projectId: "bookbuddy-c0df9",
  storageBucket: "bookbuddy-c0df9.appspot.com",
  messagingSenderId: "288055428438",
  appId: "1:288055428438:web:20cf1812bbe95c28f202f7",
};

const app = initializeApp(firebaseConfig);
const auth = getAuth(app);
const db = getFirestore(app);

export { auth, db };
