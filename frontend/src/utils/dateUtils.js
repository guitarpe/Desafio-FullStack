export const formatDataBrasil = (dateString) => {
    const [year, month, day] = dateString.split('-');
    return `${day}/${month}/${year}`;
};
/*
const handleConfirmAction = async () => {
    if (action === 'delete' && actionIndex !== null) {
        await handleRemoveConfirmed(actionIndex);
    } else if (action === 'save' && actionIndex !== null) {
        await handleSaveConfirmed();
    }
    handleCloseModal();
};

const handleShowModal = (actionType, index) => {
    setAction(actionType);
    setActionIndex(index);
    setShowModal(true);
};

const handleCloseModal = () => {
    setShowModal(false);
};


const handleConfirmAction = async () => {
        if (action === 'delete' && actionIndex !== null) {
            await handleRemoveConfirmed(actionIndex);
        } else if (action === 'save') {
            await handleConfirmSave();
        }
        handleCloseModal();
    };
*/
