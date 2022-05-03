import axios from "axios";
import Utils from "../utils/Utils";

const API_URL = 'http://localhost:8081/api/v1'
const AUTH_URL = 'http://localhost:8081/auth'

// Компонент, который осуществляется авторизацию и лог-аут пользователя через GUI
class BackendService {
    // Авторизация
    login(login, password) {
        return axios.post('${AUTH_URL}/login', {login, password})
    }
    // Деавторизация
    logout() {
        return axios.get(`${AUTH_URL}/logout`, { headers : {Authorization : Utils.getToken()}})
    }
}

export default new BackendService()