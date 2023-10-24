import React, { useState } from 'react';
import { Modal, ModalHeader, ModalBody, ModalFooter, Input } from 'reactstrap';
import Swal from 'sweetalert2';

const EditBankModal = ({ idBanco, nombreBanco, onClose }) => {
  const [nombreBancoState, setNombreBancoState] = useState(nombreBanco);

  const handleSave = async () => {
    // Verifica si el campo está vacío
    if (!nombreBancoState || /\d/.test(nombreBancoState)) {
        Swal.fire('¡Error!', 'Debe ingresar un nombre válido sin números.', 'error');
        return;
      }

    try {
      const response = await fetch(`http://localhost:8092/api/v1/banco/${idBanco}`, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ nombreBanco: nombreBancoState }),
      });

      if (!response.ok) {
        throw new Error('Error al guardar los cambios');
      }

      // Muestra un mensaje de éxito de SweetAlert2
      Swal.fire({
        title: 'Modificado con éxito',
        text: 'Los cambios se han guardado correctamente.',
        icon: 'success',
      });

      // Cierra el modal después de guardar
      onClose();
    } catch (error) {
      console.error(error);
    }
  };

  return (
    <Modal isOpen={true}>
      <ModalHeader>Editar Banco</ModalHeader>
      <ModalBody>
        <div className="form-group">
          <label>Nombre del Banco</label>
          <Input
            type="text"
            className="form-control"
            value={nombreBancoState}
            onChange={(event) => setNombreBancoState(event.target.value)}
            required={true}
          />
        </div>
      </ModalBody>
      <ModalFooter>
        <button className="btn btn-primary" onClick={handleSave}>
          Guardar
        </button>
        <button className="btn btn-danger" onClick={onClose}>
          Cancelar
        </button>
      </ModalFooter>
    </Modal>
  );
};

export default EditBankModal;
