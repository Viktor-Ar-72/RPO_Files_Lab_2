import './App.css';
import React from "react";
import {BrowserRouter, Route, Routes} from "react-router-dom";
import {createBrowserHistory} from "history";
import 'bootstrap/dist/css/bootstrap-grid.min.css'

import NavigationBarClass from "./components/NavigationBarClass";
import Home from "./components/Home";
import Another_Home from "./components/Another_Home";
import Login from "./components/Login";

function App() {
  return (
      <div className="App">
        <BrowserRouter>
            {/* При импорте с / просто вставляем компоненту. Если же с открывающими и закрывающими
             - то передаем внутрь компонента по сути параметры, с игнорированием компонента
            */}
            <NavigationBarClass />
          <div className="container-fluid">
            <Routes>
                <Route path="home" element={<Home />}/>
                <Route path="Another_Home" element={<Another_Home />}></Route>
                {/* Added from Lab_9*/}
                <Route path="Login" element={<Login />}></Route>
            </Routes>
          </div>
        </BrowserRouter>
      </div>
  );
}

export default App;



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