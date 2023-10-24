import React, { useState } from 'react';
import { Modal, ModalHeader, ModalBody, ModalFooter, Input } from 'reactstrap';
import Swal from 'sweetalert2';

const EditTipoCuentaModal = ({ idTipoCuenta, nombreTipoCuenta, onClose }) => {
  const [nombreTipoCuentaState, setNombreTipoCuentaState] = useState(nombreTipoCuenta);

  const handleSave = async () => {
    // Verifica si el campo está vacío o contiene números
    if (!nombreTipoCuentaState || /\d/.test(nombreTipoCuentaState)) {
      Swal.fire('¡Error!', 'Debe ingresar un nombre válido o sin números.', 'error');
      return;
    }

    try {
      const response = await fetch(`http://localhost:8092/api/v1/tipoCuenta/${idTipoCuenta}`, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ nombreTipoCuenta: nombreTipoCuentaState }),
      });

      if (!response.ok) {
        throw new Error('Error al guardar los cambios');
      }

      // Muestra un mensaje de éxito de SweetAlert2
      Swal.fire({
        title: "Modificado con éxito",
        text: "Los cambios se han guardado correctamente.",
        icon: "success",
      });

      // Cierra el modal después de guardar
      onClose();
    } catch (error) {
      console.error(error);
    }
  };

  return (
    <Modal isOpen={true}>
      <ModalHeader>
        Editar Tipo de Cuenta
      </ModalHeader>
      <ModalBody>
        <div className="form-group">
          <label>Nombre del Tipo de Cuenta</label>
          <Input
            type="text"
            className="form-control"
            value={nombreTipoCuentaState}
            onChange={(event) => setNombreTipoCuentaState(event.target.value)}
            required={true}
          />
        </div>
      </ModalBody>
      <ModalFooter>
        <button className="btn btn-primary" onClick={handleSave}>Guardar</button>
        <button className="btn btn-danger" onClick={onClose}>Cancelar</button>
      </ModalFooter>
    </Modal>
  );
};

export default EditTipoCuentaModal;
