import axios from "axios";
import Utils from "../utils/Utils";

import {config} from "@fortawesome/fontawesome-svg-core";

// Тут именно импортируется, так как
// BackendService - это не компонент, его через
// Redux так просто не вызвать
import {store, alertActions} from "../utils/Rdx";

const API_URL = 'http://localhost:8081/api/v1'
const AUTH_URL = 'http://localhost:8081/auth'

// Компонент, который осуществляется авторизацию и лог-аут пользователя через GUI
class BackendService {
    // Авторизация
    login(login, password) {
        return axios.post(`${AUTH_URL}/login`, {login, password})
    }

    // Деавторизация
    logout() {
        return axios.get(`${AUTH_URL}/logout`, { headers : {Authorization : Utils.getToken()}})
    }
}

// Вывод error - используем своеобразную "подфункцию" для сокращения текста
function showError(msg) {
    store.dispatch(alertActions.error(msg))
}

axios.interceptors.request.use(
    config => {
        store.dispatch(alertActions.clear())
        //TD: Проверить, почему надо бы комментить эти строки
        //let token = Utils.getToken();
        //if (token)
        //config.headers.Authorization = token;
        return config;
    },
    error => {
        showError(error.message)
        return Promise.reject(error);
    })
//
axios.interceptors.response.use(undefined,
    error => {
        if (error.response && error.response.status && [401, 403].indexOf(error.response.status) !== -1) {
            showError("Ошибка авторизации")
        }
        else if (error.response && error.response.data && error.response.data.message) {
            showError(error.response.data.message)
        }
        else {
            showError(error.message)
        }
        return Promise.reject(error);
    })

export default new BackendService()