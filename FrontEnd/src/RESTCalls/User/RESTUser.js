import axios from "axios";

const instance = axios.create({
  baseURL: "/api/v1/user",
});

export default instance;
