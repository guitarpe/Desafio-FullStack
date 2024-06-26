import React from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import { BrowserRouter as Router, Routes, Route, Link } from 'react-router-dom';
import Home from './pages/Home';
import Desenvolvedores from './pages/Desenvolvedores';
import Niveis from './pages/Niveis';
import CadDesenvolvedor from './pages/CadDesenvolvedor';
import CadNivel from "./pages/CadNivel";

const App = () => {
    return (
        <Router>
            <div>
                <nav className="navbar navbar-expand-lg navbar-light bg-light">
                    <div className="container-fluid">
                        <Link className="navbar-brand" to="/">Gazin</Link>
                        <button className="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                            <span className="navbar-toggler-icon"></span>
                        </button>
                        <div className="collapse navbar-collapse" id="navbarNav">
                            <ul className="navbar-nav">
                                <li className="nav-item">
                                    <Link className="nav-link" to="/">Home</Link>
                                </li>
                                <li className="nav-item">
                                    <Link className="nav-link" to="/desenvolvedores">Desenvolvedores</Link>
                                </li>
                                <li className="nav-item">
                                    <Link className="nav-link" to="/niveis">Níveis</Link>
                                </li>
                            </ul>
                        </div>
                    </div>
                </nav>

                <div className="container mt-4">
                    <Routes>
                        <Route path="/" element={<Home />} />
                        <Route path="/desenvolvedores" element={<Desenvolvedores />} />
                        <Route path="/niveis" element={<Niveis />} />
                        <Route path="/cad-desenvolvedor" element={<CadDesenvolvedor />} />
                        <Route path="/cad-nivel" element={<CadNivel />} />
                    </Routes>
                </div>
            </div>
        </Router>
    );
};

export default App;
