import React, { useState } from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import axios from 'axios';
import { API_URL } from "../constants";
import Alert from 'react-bootstrap/Alert';

const URL_CAD_NIV = `${API_URL}/niveis`;

const CadNivel = () => {
    const [nivel, setNivel] = useState('');
    const [alertVisible, setAlertVisible] = useState(false);
    const [alertMessage, setAlertMessage] = useState('');
    const [alertVariant, setAlertVariant] = useState('');

    const handleSubmit = (event) => {
        event.preventDefault();

        const requestBody = {
            nivel: nivel
        };

        axios.post(URL_CAD_NIV, requestBody, {
            headers: {
                'Content-Type': 'application/json'
            }
        }).then(response => {
            if (response.status === 200) {
                console.log('Nível cadastrado com sucesso.');
                setNivel('');
                setAlertVariant('success');
                setAlertMessage(response.data.message);
                setAlertVisible(true);
                setTimeout(() => setAlertVisible(false), 3000);
            } else {
                console.error('Falha ao cadastrar o nível.');
                setAlertVariant('danger');
                setAlertMessage(response.data.message);
                setAlertVisible(true);
            }
        }).catch(error => {
            console.error('Erro ao enviar requisição:', error);
            setAlertVariant('danger');
            setAlertMessage(`Erro ao enviar requisição: ${error.message}`);
            setAlertVisible(true);
        }).finally(
            setTimeout(() => {
                setAlertVisible(true);
            }, 3000)
        );
    };

    return (
        <div className="container mt-5">
            <div className="row justify-content-center">
                <div className="col-md-6">
                    <div className="card">
                        <div className="card-body">
                            <h3 className="card-title text-center mb-4">Cadastro de Nível</h3>
                            <form onSubmit={handleSubmit}>
                                <div className="mb-3">
                                    <label htmlFor="nivel" className="form-label">Nível:</label>
                                    <input
                                        type="text"
                                        className="form-control"
                                        id="nivel"
                                        value={nivel}
                                        onChange={(e) => setNivel(e.target.value)}
                                        required
                                    />
                                </div>
                                <div className="text-center">
                                    <button type="submit" className="btn btn-primary">Cadastrar Nível</button>
                                </div>
                            </form>
                            {alertVisible &&
                                <Alert variant={alertVariant} className="mt-3">
                                    {alertMessage}
                                </Alert>
                            }
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default CadNivel;
