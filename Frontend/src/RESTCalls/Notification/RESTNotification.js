import axios from "axios";

const instance = axios.create({
    baseURL:"/api/v1/notification/mail"
});


export default instance;