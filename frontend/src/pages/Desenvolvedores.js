import React, { useState, useEffect } from 'react';
import { Table, Button, Pagination, Form, Alert } from 'react-bootstrap';
import { Link } from 'react-router-dom';
import axios from 'axios';
import { API_URL } from "../constants";
import ConfirmationModal from "../components/ConfirmationModal";
import { formatDataBrasil } from "../utils/dateUtils";

const Desenvolvedores = () => {
    const [showModal, setShowModal] = useState(false);
    const [action, setAction] = useState('');
    const [actionIndex, setActionIndex] = useState(null);
    const [showAlert, setShowAlert] = useState(false);
    const [alertMessage, setAlertMessage] = useState('');
    const [alertVariant, setAlertVariant] = useState('success');
    const [niveis, setNiveis] = useState([]);
    const [data, setData] = useState([]);
    const [editingIndex, setEditingIndex] = useState(null);
    const [editedData, setEditedData] = useState({
        id: '', nome: '', sexo: '', nivel: '', dtNascimento: '', hobby: ''
    });
    const [currentPage, setCurrentPage] = useState(1);
    const [totalPages, setTotalPages] = useState(0);
    const itemsPerPage = 10;

    const URL_NIVEIS = `${API_URL}/lista-niveis`;
    const URL_DESENV = `${API_URL}/desenvolvedores?page=${currentPage}&size=${itemsPerPage}`;

    useEffect(() => {
        fetchData();
    }, [currentPage]);

    useEffect(() => {
        fetchDataNiveis();
    }, []);

    const fetchDataNiveis = async () => {
        try {
            const response = await axios.get(URL_NIVEIS);
            setNiveis(response.data.data);
        } catch (error) {
            handleFetchError(error, 'Erro ao buscar níveis.');
        }
    };

    const fetchData = async () => {
        try {
            const response = await axios.get(URL_DESENV);
            const responseData = response.data.data;
            setData(responseData.data);
            setTotalPages(responseData.meta.last_page);
        } catch (error) {
            handleFetchError(error, 'Erro ao buscar desenvolvedores.');
        }
    };

    const handleConfirmAction = async () => {
        if (action === 'delete' && actionIndex !== null) {
            await handleRemoveConfirmed(actionIndex);
        } else if (action === 'save' && actionIndex !== null) {
            await handleSaveConfirmed();
        }
        handleCloseModal();
    };

    const handleRequestError = (error) => {
        console.error('Erro ao buscar dados:', error);
        setShowAlert(true);
        setAlertVariant('danger');
        setAlertMessage('Houve um erro ao buscar os dados.');
    };

    const handleShowModal = (actionType, index) => {
        setAction(actionType);
        setActionIndex(index);
        setShowModal(true);
    };

    const handleCloseModal = () => {
        setShowModal(false);
    };

    const handleFetchError = (error, defaultMessage) => {
        if (error.response && error.response.status === 404) {
            setData([]);
            setTotalPages(0);
        } else {
            console.error('Erro ao buscar dados:', error);
            setAlertVariant('danger');
            setAlertMessage('Erro ao buscar dados');
        }
        setAlertMessage(defaultMessage);
        setShowAlert(true);
    };

    const handleEditClick = (index) => {
        setEditingIndex(index);
        setEditedData(data[index]);
    };

    const handleSaveClick = () => {
        handleShowModal('save', editingIndex);
    };

    const handleSaveConfirmed = async () => {

        if (typeof editedData.nivel.nivel === "undefined"){
            editedData.nivel = niveis.find(item => item.id == editedData.nivel);
        }

        const dataForm = {
            nome: editedData.nome,
            sexo: editedData.sexo,
            nivel_id: editedData.nivel.id,
            data_nascimento: editedData.dtNascimento,
            hobby: editedData.hobby
        };

        try {
            const response = await axios.put(`${API_URL}/desenvolvedores/${editedData.id}`, dataForm);
            const newData = [...data];
            newData[editingIndex] = editedData;
            setData(newData);
            setEditingIndex(null);
            setAlertVariant('success');
            setAlertMessage(response.data.message);
            setShowAlert(true);
            setTimeout(() => setShowAlert(false), 3000);
        } catch (error) {
            handleRequestError(error);
        }finally {
            setTimeout(() => setShowAlert(false), 3000);
        }
    };

    const handleCancelClick = () => {
        setEditingIndex(null);
    };

    const handleChange = (e) => {
        const { name, value } = e.target;
        setEditedData((prevData) => ({ ...prevData, [name]: value }));
    };

    const handleRemoveClick = (index) => {
        handleShowModal('delete', index);
    };

    const handleRemoveConfirmed = async (index) => {
        try {
            const response = await axios.delete(`${API_URL}/desenvolvedores/${data[index].id}`);
            const newData = data.filter((_, i) => i !== index);
            setData(newData);
            setEditingIndex(null);
            setAlertVariant('success');
            setAlertMessage(response.data.message);
            setShowAlert(true);
            setTimeout(() => setShowAlert(false), 3000);
        } catch (error) {
            handleRequestError(error);
        }
    };

    const handlePageChange = (pageNumber) => {
        setCurrentPage(pageNumber);
    };

    const displayedData = data.map(item => ({
        id: item.id,
        nome: item.nome,
        sexo: item.sexo,
        nivel: item.nivel,
        dtNascimento: formatDataBrasil(item.dtNascimento),
        hobby: item.hobby
    }));

    return (
        <div className="container mt-5">
            <div className="d-flex justify-content-between align-items-center mb-3">
                <h2>Lista de Desenvolvedores</h2>
                <Link to="/cad-desenvolvedor">
                    <Button variant="primary">Cadastrar</Button>
                </Link>
            </div>
            {showAlert && (
                <Alert variant={alertVariant} onClose={() => setShowAlert(false)} dismissible>
                    {alertMessage}
                </Alert>
            )}
            {data.length === 0 ? (
                <Alert variant="info">Nenhum desenvolvedor encontrado.</Alert>
            ) : (
                <Table striped bordered hover>
                    <thead>
                    <tr>
                        <th>Nome</th>
                        <th style={{ width: '120px' }}>Sexo</th>
                        <th>Nível</th>
                        <th style={{ width: '150px' }}>Data Nascimento</th>
                        <th>Hobby</th>
                        <th style={{ width: '250px' }}>Ações</th>
                    </tr>
                    </thead>
                    <tbody>
                    {displayedData.map((item, index) => (
                        <tr key={item.id}>
                            <td>
                                {editingIndex === index ? (
                                    <Form.Control
                                        type="text"
                                        name="name"
                                        value={editedData.nome}
                                        onChange={handleChange}
                                    />
                                ) : (
                                    item.nome
                                )}
                            </td>
                            <td>
                                {editingIndex === index ? (
                                    <Form.Control
                                        as="select"
                                        name="sexo"
                                        value={editedData.sexo}
                                        onChange={handleChange}
                                    >
                                        <option value="M">Masculino</option>
                                        <option value="F">Feminino</option>
                                    </Form.Control>
                                ) : (
                                    item.sexo === 'M' ? 'Masculino' : 'Feminino'
                                )}
                            </td>
                            <td>
                                {editingIndex === index ? (
                                    <Form.Control
                                        as="select"
                                        name="nivel"
                                        value={editedData.nivel.id}
                                        onChange={handleChange}
                                    >
                                        {niveis.map((nivel) => (
                                            <option key={nivel.id} value={nivel.id}>
                                                {nivel.nivel}
                                            </option>
                                        ))}
                                    </Form.Control>
                                ) : (
                                    item.nivel.nivel
                                )}
                            </td>
                            <td>
                                {editingIndex === index ? (
                                    <Form.Control
                                        type="date"
                                        name="dtNascimento"
                                        value={editedData.dtNascimento}
                                        onChange={handleChange}
                                    />
                                ) : (
                                    item.dtNascimento
                                )}
                            </td>
                            <td>
                                {editingIndex === index ? (
                                    <Form.Control
                                        type="text"
                                        name="hobby"
                                        value={editedData.hobby}
                                        onChange={handleChange}
                                    />
                                ) : (
                                    item.hobby
                                )}
                            </td>
                            <td className="text-center align-middle">
                                {editingIndex === index ? (
                                    <>
                                        <Button variant="success" className="me-2" onClick={handleSaveClick}>
                                            Salvar
                                        </Button>
                                        <Button variant="danger" onClick={handleCancelClick}>
                                            Cancelar
                                        </Button>
                                    </>
                                ) : (
                                    <>
                                        <Button variant="primary" className="me-2" onClick={() => handleEditClick(index)}>
                                            Editar
                                        </Button>
                                        <Button variant="danger" onClick={() => handleRemoveClick(index)}>
                                            Remover
                                        </Button>
                                    </>
                                )}
                            </td>
                        </tr>
                    ))}
                    </tbody>
                </Table>
            )}
            <Pagination>
                {[...Array(totalPages)].map((_, pageIndex) => (
                    <Pagination.Item
                        key={pageIndex + 1}
                        active={pageIndex + 1 === currentPage}
                        onClick={() => handlePageChange(pageIndex + 1)}
                    >
                        {pageIndex + 1}
                    </Pagination.Item>
                ))}
            </Pagination>
            <ConfirmationModal
                show={showModal}
                handleClose={() => setShowModal(false)}
                handleConfirm={handleConfirmAction}
                title={`Confirmação`}
                body={`Você tem certeza que deseja ${action === 'save' ? 'salvar as alterações' : `${action} este item`}?`}
            />
        </div>
    );
};

export default Desenvolvedores;
