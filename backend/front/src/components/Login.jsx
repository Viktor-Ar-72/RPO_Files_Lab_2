import React, {useState} from "react";
import BackendService from "../services/BackendService";
import Utils from "../utils/Utils";
import {useNavigate} from "react-router-dom";

//Компонент должен быть подключен к хранилищу, для того что бы пользоваться функционалом redux.
import {connect} from "react-redux";
import {store, userActions} from "../utils/Rdx";
// Хуки — это функции, с помощью которых можно «подцепиться» к состоянию и методам жизненного цикла React из функциональных компонентов

// Форма авторизации пользователя
export default connect()(function Login() {
    // Здесь используется хук useState - позволяет сохранять и изменять переменные состояния так, как если бы они
    // были компонентами класса
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [loggingIn, setLoggingIn] = useState(false);
    const [submitted, setSubmitted] = useState(false);

    //const [error_message, setErrorMessage] = useState(null);

    // Данный хук позволяет осуществлять навигацию между компонентами классов
    const nav = useNavigate();

    function handleChangeLogin(e) {
        setUsername(e.target.value);
    }

    function handleChangePassword(e) {
        setPassword(e.target.value);
    }

    // Если всё обработано хорошо, то переходим на страницу /home
    function handleSubmit(e) {
        e.preventDefault();
        setSubmitted(true);

        //setErrorMessage(null);

        setLoggingIn(true);
        BackendService.login(username, password)
            .then(resp => {
                console.log(resp.data);
                setLoggingIn(false);
                store.dispatch(userActions.login(resp.data))
                nav("/home");
            })
            // Закоменчено, так как обработка ошибок перенесена в App.js, BackendService.jsx и Rdx.jsx
            //.catch(err => {

                // if (err.response && err.response.status === 401)
                //     setErrorMessage("Ошибка авторизации");
                // else
                //     setErrorMessage(err.message);
                // setLoggingIn(false);
            //})
            .catch()
    }

    // В качестве возвращаемого значения можно использовать форму авторизации (то есть то, что нам нужно)
    return (
        <div className="col-md-6 me-0">

            {/*Старая реализация, не используется*/}
            {/*{error_message &&*/}
            {/*    <div className="alert  alert-danger mt-1 me-0 ms-0">{error_message}</div>}*/}
            {/*Конец старой реализации*/}

            <h2>Авторизация</h2>
            <form name="form" onSubmit={handleSubmit}>

                <div className="form-group">
                    <label htmlFor="username">Логин</label>
                    <input type="text" className={'form-control' + (submitted && !username ? ' is-invalid' : '')}
                           name="username" value={username} onChange={handleChangeLogin}/>
                    {submitted && !username && <div className="help-block text-danger">Введите логин</div>}
                </div>

                <div className="form-group">
                    <label htmlFor="password">Pin</label>
                    <input type="password"
                           className={'form-control' + (submitted && !password ? ' is-invalid' : '')}
                           name="password" value={password} onChange={handleChangePassword}/>
                    {submitted && !password && <div className="help-block text-danger">Введите пароль</div>}
                </div>

                <div className="form-group mt-2">
                    <button className="btn btn-primary">
                        {loggingIn && <span className="spinner-border spinner-border-sm" role="status" aria-hidden="true" onSubmit={handleSubmit}></span>}
                        ВОЙТИ
                    </button>
                </div>
            </form>
        </div>

    );
})