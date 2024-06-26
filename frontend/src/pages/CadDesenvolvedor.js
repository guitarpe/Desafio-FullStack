import React, { useState, useEffect } from 'react';
import { API_URL } from "../constants";
import Alert from "react-bootstrap/Alert";
import axios from "axios";

const URL_NIVEIS = `${API_URL}/lista-niveis`;
const URL_CAD_DEV = `${API_URL}/desenvolvedores`;

const CadDesenvolvedor = () => {
    const [niveis, setNiveis] = useState([]);
    const [alertVisible, setAlertVisible] = useState(false);
    const [alertMessage, setAlertMessage] = useState('');
    const [alertVariant, setAlertVariant] = useState('');
    const [formData, setFormData] = useState({
        nome: '',
        sexo: '',
        nivel_id: '',
        data_nascimento: '',
        hobby: ''
    });

    useEffect(() => {
        fetchData();
    }, []);

    const handleChange = (e) => {
        setFormData({
            ...formData,
            [e.target.name]: e.target.value
        });
    };

    const fetchData = async () => {
        try {
            const response = await axios.get(URL_NIVEIS);
            setNiveis(response.data.data);
        } catch (error) {
            setAlertVariant('danger');
            setAlertMessage('Erro ao buscar dados.');
            setAlertVisible(true);
        }
    };

    const handleSubmit = (event) => {
        event.preventDefault();

        axios.post(URL_CAD_DEV, formData, {
            headers: {
                'Content-Type': 'application/json'
            }
        }).then(response => {
            if (response.status === 200) {
                console.log('Desenvolvedor cadastrado com sucesso.');
                resetForm();
                setAlertVariant('success');
                setAlertMessage(response.data.message);
                setAlertVisible(true);
                setTimeout(() => setAlertVisible(false), 3000);
            } else {
                console.error('Falha ao cadastrar o desenvolvedor.');
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

    const resetForm = () => {
        setFormData({
            nome: '',
            sexo: '',
            nivel_id: '',
            data_nascimento: '',
            hobby: ''
        });
    };

    return (
        <div className="container mt-5">
            <div className="row justify-content-center">
                <div className="col-md-6">
                    <div className="card">
                        <div className="card-body">
                            <h3 className="card-title text-center mb-4">Cadastro de Desenvolvedor</h3>
                            <form onSubmit={handleSubmit}>
                                <div className="mb-3">
                                    <label htmlFor="nome" className="form-label">Nome</label>
                                    <input
                                        type="text"
                                        className="form-control"
                                        id="nome"
                                        name="nome"
                                        value={formData.nome}
                                        onChange={handleChange}
                                        required
                                    />
                                </div>
                                <div className="mb-3">
                                    <label htmlFor="sexo" className="form-label">Sexo</label>
                                    <select
                                        className="form-select"
                                        id="sexo"
                                        name="sexo"
                                        value={formData.sexo}
                                        onChange={handleChange}
                                        required
                                    >
                                        <option value="">Selecione</option>
                                        <option value="M">Masculino</option>
                                        <option value="F">Feminino</option>
                                    </select>
                                </div>
                                <div className="mb-3">
                                    <label htmlFor="nivel_id" className="form-label">Nível</label>
                                    <select
                                        className="form-select"
                                        id="nivel_id"
                                        name="nivel_id"
                                        value={formData.nivel_id}
                                        onChange={handleChange}
                                        required
                                    >
                                        <option value="">Selecione</option>
                                        {niveis.map((nivel) => (
                                            <option key={nivel.id} value={nivel.id}>
                                                {nivel.nivel}
                                            </option>
                                        ))}
                                    </select>
                                </div>
                                <div className="mb-3">
                                    <label htmlFor="data_nascimento" className="form-label">Data de Nascimento</label>
                                    <input
                                        type="date"
                                        className="form-control"
                                        id="data_nascimento"
                                        name="data_nascimento"
                                        value={formData.data_nascimento}
                                        onChange={handleChange}
                                        required
                                    />
                                </div>
                                <div className="mb-3">
                                    <label htmlFor="hobby" className="form-label">Hobby</label>
                                    <input
                                        type="text"
                                        className="form-control"
                                        id="hobby"
                                        name="hobby"
                                        value={formData.hobby}
                                        onChange={handleChange}
                                    />
                                </div>
                                <button type="submit" className="btn btn-primary">Cadastrar</button>
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

export default CadDesenvolvedor;
