import React, { useEffect, useState } from 'react';
import { Table, Button, Pagination, Form, Alert } from 'react-bootstrap';
import { Link } from "react-router-dom";
import axios from "axios";
import { API_URL } from "../constants";
import ConfirmationModal from "../components/ConfirmationModal";

const Niveis = () => {
    const [showModal, setShowModal] = useState(false);
    const [action, setAction] = useState('');
    const [actionIndex, setActionIndex] = useState(null);
    const [alertMessage, setAlertMessage] = useState('');
    const [showAlert, setShowAlert] = useState(false);
    const [alertVariant, setAlertVariant] = useState('success');
    const [data, setData] = useState([]);
    const [editingIndex, setEditingIndex] = useState(null);
    const [editedData, setEditedData] = useState({ id: '', nivel: ''});
    const [currentPage, setCurrentPage] = useState(1);
    const [totalPages, setTotalPages] = useState(0);
    const itemsPerPage = 10;

    const URL_NIVEIS = `${API_URL}/niveis?page=${currentPage}&size=${itemsPerPage}`;

    useEffect(() => {
        fetchData();
    }, [currentPage]);

    const fetchData = async () => {
        try {
            const response = await axios.get(URL_NIVEIS);
            const responseData = response.data.data;

            setData(responseData.data);
            setTotalPages(responseData.meta.last_page);
        } catch (error) {
            handleRequestError(error);
        }
    };

    const handleConfirmAction = async () => {
        if (action === 'delete' && actionIndex !== null) {
            await handleRemoveConfirmed(actionIndex);
        } else if (action === 'save') {
            await handleConfirmSave();
        }
        handleCloseModal();
    };

    const handleRequestError = (error) => {
        console.error('Erro ao buscar dados:', error);
        setShowAlert(true);
        setAlertVariant('danger');
        setAlertMessage('Houve um erro ao buscar os dados.');
    };

    const handleEditClick = (index) => {
        setEditingIndex(index);
        setEditedData(data[index]);
    };

    const handleSaveClick = () => {
        handleShowModal('save');
    };

    const handleConfirmSave = async () => {
        try {
            const response = await axios.put(`${API_URL}/niveis/${editedData.id}`, editedData);
            const newData = data.map((item, index) => index === editingIndex ? editedData : item);
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
            const response = await axios.delete(`${API_URL}/niveis/${data[index].id}`);
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
        nivel: item.nivel
    }));

    const handleShowModal = (actionType, index) => {
        setAction(actionType);
        setActionIndex(index);
        setShowModal(true);
    };

    const handleCloseModal = () => {
        setShowModal(false);
    };

    return (
        <div className="container mt-5">
            <div className="d-flex justify-content-between align-items-center mb-3">
                <h2>Lista de Níveis</h2>
                <Link to="/cad-nivel">
                    <Button variant="primary">Cadastrar</Button>
                </Link>
            </div>
            {showAlert && (
                <Alert variant={alertVariant} onClose={() => setShowAlert(false)} dismissible>
                    {alertMessage}
                </Alert>
            )}
            {data.length === 0 ? (
                <Alert variant="info">Nenhum nível encontrado.</Alert>
            ) : (
                <Table striped bordered hover>
                    <thead>
                    <tr>
                        <th>Nível</th>
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
                                        name="nivel"
                                        value={editedData.nivel}
                                        onChange={handleChange}
                                    />
                                ) : (
                                    item.nivel
                                )}
                            </td>
                            <td className="text-center align-middle">
                                {editingIndex === index ? (
                                    <>
                                        <Button variant="success" className="me-2" onClick={handleSaveClick}>
                                            Salvar
                                        </Button>
                                        <Button variant="danger" className="me-2" onClick={handleCancelClick}>
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
                handleClose={handleCloseModal}
                handleConfirm={handleConfirmAction}
                title={`Confirmação de ${action}`}
                body={`Você tem certeza que deseja ${action} este item?`}
            />
        </div>
    );
};

export default Niveis;
