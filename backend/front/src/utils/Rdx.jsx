// Набор действий для Redux
// По сути, определяет логику работы
import Utils from "./Utils";
import {applyMiddleware, combineReducers, createStore} from "redux";
import {createLogger} from "redux-logger"

// Константы пользователя - для функций логина и логаута
const userConstants = {
    LOGIN: 'USER_LOGIN',
    LOGOUT: 'USER_LOGOUT'
};
// ГЕНЕРАТОРЫ логина и логаута

export const userActions = {
    login,
    logout
};
// Логин
function login(user) {
    Utils.saveUser(user);
    return {type: userConstants.LOGIN, user}
}

// Логаут
function logout() {
    Utils.removeUser();
    return {type: userConstants.LOGOUT}
}

// Reducers
// Reducer - чистая функция, которая принимает предыдущее состояние и action (state и action)
// и возвращает следующее состояние (новую версию предыдущего).
let user = Utils.getUser()
const initialState = user ? {user} : {}
// Пока всего один - authentication, отвечающий за процесс аутентификации
function authentication(state = initialState, action) {
    console.log("authentication")
    // Всё это нужно так как при каждом запуске хранилище сбрасывается
    switch(action.type) {
        case userConstants.LOGIN:
            return {user: action.user};
        case userConstants.LOGOUT:
            return {};
        default:
            return state
    }
}

// ГЕНЕРАТОРЫ действия при ошибках
function error(msg) {
    return {type: alertConstants.ERROR, msg}
}

function clear() {
    return {type: alertConstants.CLEAR}
}

function alert(state = {}, action) {
    console.log("ALERT")
    switch(action.type) {
        case alertConstants.ERROR:
            return {msg: action.msg};
        case alertConstants.CLEAR:
            return {};
        default:
            return state
    }
}

// Константы для сообщения об ошибке и сброса ошибочного состояния.
const alertConstants = {
    ERROR: 'ERROR',
    CLEAR: 'CLEAR',
};

// Объединение различных reducerov
const rootReducer = combineReducers({
    authentication, alert
});

const loggerMiddleware = createLogger();
// applyMiddleware позволяет внедрить дополнительные обработчики в процесс управления хранилищем
export const store = createStore(rootReducer, applyMiddleware(loggerMiddleware));

// Действия при ошибке
export const alertActions = {
    error,
    clear
};