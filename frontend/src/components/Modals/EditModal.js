import React, { useState } from 'react';
import { Modal, ModalHeader, ModalBody, ModalFooter, Input } from 'reactstrap';
import Swal from 'sweetalert2';

const EditModal = ({ idTipoTransferencia, nombreTransferencia, onClose }) => {
    const [nombreTransferenciaState, setNombreTransferenciaState] = useState(nombreTransferencia);

    const handleSave = async () => {
      // Verifica si el campo está vacío o contiene números
      if (!nombreTransferenciaState || /\d/.test(nombreTransferenciaState)) {
        Swal.fire('¡Error!', 'Debe ingresar un nombre válido sin números.', 'error');
        return;
      }
    
      try {
        const response = await fetch(`http://localhost:8092/api/v1/tipoTransferencia/${idTipoTransferencia}`, {
          method: 'PUT',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify({ nombreTransferencia: nombreTransferenciaState }),
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
            Editar Tipo de Transferencia
        </ModalHeader>
        <ModalBody>
            <div className="form-group">
            <label>Nombre de la Transferencia</label>
            <Input type="text" className="form-control" value={nombreTransferenciaState} onChange={(event) => setNombreTransferenciaState(event.target.value)} required="true" />
            </div>
        </ModalBody>
        <ModalFooter>
            <button className="btn btn-primary" onClick={handleSave}>Guardar</button>
            <button className="btn btn-danger" onClick={onClose}>Cancelar</button>
        </ModalFooter>
    </Modal>
    );
}

export default EditModal;
