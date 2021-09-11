import firebase from "firebase/app";
import "firebase/auth"

var app = firebase.initializeApp({
  apiKey: "AIzaSyC17BUoDDBoKGGXhCysQk3iRHze_T40AoQ",
  authDomain: "socialoffice-cb01c.firebaseapp.com",
  projectId: "socialoffice-cb01c",
  storageBucket: "socialoffice-cb01c.appspot.com",
  messagingSenderId: "462170420332",
  appId: "1:462170420332:web:69fadc3a34d3ec39ff9449",
  measurementId: "G-5YMMBX2TLM",
});

export const auth = app.auth();
export default app;