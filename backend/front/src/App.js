import './App.css';
import React from "react";
import {BrowserRouter, Route, Routes, Navigate} from "react-router-dom";
import 'bootstrap/dist/css/bootstrap-grid.min.css'

import NavigationBarClass from "./components/NavigationBarClass";
import Home from "./components/Home";
import Another_Home from "./components/Another_Home";
import Login from "./components/Login";
// Lab_10 added
import Utils from "./utils/Utils";
import {connect} from "react-redux";

const ProtectedRoute = ({children}) => {
    let user = Utils.getUser();
    return user ? children : <Navigate to={'/login'}/>
};

function App(props) {
  return (
      <div className="App">
        <BrowserRouter>
            {/* При импорте с / просто вставляем компоненту. Если же с открывающими и закрывающими
             - то передаем внутрь компонента по сути параметры, с игнорированием компонента
            */}

            {/*Функция обертка ProtectedRoute используется для защиты маршрутов приложения,
                    которые доступны только авторизованным пользователям. Функция проверяет,
                    есть ли пользователь в локальном хранилище и, если его там нет,
                    с помощью компонента Navigate перенаправляет маршрут на страницу login.
            */}

            <NavigationBarClass />
            <div className="container-fluid"> {
                props.error_message &&
                <div className="alert alert-danger m-1">{props.error_message}</div>
            }
            <Routes>
                <Route path="login" element={<Login/>}/>
                {/* Не забываем про вторую страницу Another Home*/}
                <Route path="Another_home" element={
                    <ProtectedRoute>
                        <Another_Home/>
                    </ProtectedRoute>
                }/>

                <Route path="home" element={
                    <ProtectedRoute>
                        <Home/>
                    </ProtectedRoute>}/>
            </Routes>
          </div>
        </BrowserRouter>
      </div>
  );
}

// mapStateToProps преобразует сообщение из хранилища в свойство App error_message.
// В функции render для обращения к props не используется ключевое слово this -
// так как App реализован как компонент без состояния. Это функция JavaScript, к которой не применимо ключевое слово this
// Параметры props просто передаются через аргумент этой функции
const mapStateToProps = function (state) {
    const {msg} = state.alert;
    return {error_message: msg};
}

export default connect(mapStateToProps)(App);

/**
import logo from './logo.svg';
import './App.css';

function App() {
  return (
    <div className="App">
      <header className="App-header">
        <img src={logo} className="App-logo" alt="logo" />
        <p>
          Edit <code>src/App.js</code> and save to reload.
        </p>
        <a
          className="App-link"
          href="https://reactjs.org"
          target="_blank"
          rel="noopener noreferrer"
        >
          Learn React
        </a>
      </header>
    </div>
  );
}

export default App;
*/