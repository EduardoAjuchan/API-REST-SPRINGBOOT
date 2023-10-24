import React, { useState } from 'react';
import {  Form, FormGroup, Label, Input } from 'reactstrap';
import { Modal, ModalHeader, ModalBody, ModalFooter } from 'reactstrap';
import Swal from 'sweetalert2'; // Importa la librería SweetAlert2

const NewModal = ({ onClose }) => {
  const [nombreTransferencia, setNombreTransferencia] = useState('');

  const handleSubmit = async (event) => {
    event.preventDefault();
  
    if (!nombreTransferencia || /\d/.test(nombreTransferencia)) {
      Swal.fire('¡Error!', 'Debe ingresar un nombre válido sin números.', 'error');
      return;
    }
  
    try {
      const response = await fetch('http://localhost:8092/api/v1/tipoTransferencia', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          nombreTransferencia,
        }),
      });
  
      if (!response.ok) {
        throw new Error('Error al guardar los datos');
      }
  
      Swal.fire('¡Guardado!', 'Los datos se guardaron correctamente.', 'success'); // Alerta de éxito
      onClose();
    } catch (error) {
      Swal.fire('¡Error!', 'No se pudieron guardar los datos.', 'error'); // Alerta de error
    }
  };

  return (
    <Modal isOpen={true}>
        <ModalHeader>
            Nueva Transferencia
        </ModalHeader>
        <ModalBody>
            <Form onSubmit={handleSubmit}>
            <FormGroup>
                <Label>Nombre del Tipo de Transferencia</Label>
                <Input type="text" value={nombreTransferencia} onChange={(event) => setNombreTransferencia(event.target.value)} />
            </FormGroup>
            <ModalFooter>
                <button className="btn btn-primary" type="submit">Guardar</button>
                <button className="btn btn-danger" onClick={onClose}>Cancelar</button>
            </ModalFooter>
            </Form>
        </ModalBody>
    </Modal>
  );
};

export default NewModal;
