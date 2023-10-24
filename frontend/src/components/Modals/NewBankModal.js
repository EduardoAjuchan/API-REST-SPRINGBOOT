import React, { useState } from 'react';
import { Form, FormGroup, Label, Input } from 'reactstrap';
import { Modal, ModalHeader, ModalBody, ModalFooter } from 'reactstrap';
import Swal from 'sweetalert2';

const NewBankModal = ({ onClose, fetchData }) => {
  const [nombreBanco, setNombreBanco] = useState('');

  const handleSubmit = async (event) => {
    event.preventDefault();

    // Verifica si el campo está vacío
    if (!nombreBanco || /\d/.test(nombreBanco)) {
      Swal.fire('¡Error!', 'Debe ingresar un nombre de banco válido.', 'error');
      return;
    }

    try {
      const response = await fetch('http://localhost:8092/api/v1/banco', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          idBanco: 0, 
          nombreBanco,
        }),
      });

      if (!response.ok) {
        throw new Error('Error al guardar los datos');
      }

      Swal.fire('¡Guardado!', 'Los datos se guardaron correctamente.', 'success');
      onClose();
      fetchData(); 
    } catch (error) {
      Swal.fire('¡Error!', 'No se pudieron guardar los datos.', 'error');
    }
  };

  return (
    <Modal isOpen={true}>
      <ModalHeader>Nuevo Banco</ModalHeader>
      <ModalBody>
        <Form onSubmit={handleSubmit}>
          <FormGroup>
            <Label>Nombre del Banco</Label>
            <Input
              type="text"
              value={nombreBanco}
              onChange={(event) => setNombreBanco(event.target.value)}
            />
          </FormGroup>
          <ModalFooter>
            <button className="btn btn-primary" type="submit">
              Guardar
            </button>
            <button className="btn btn-danger" onClick={onClose}>
              Cancelar
            </button>
          </ModalFooter>
        </Form>
      </ModalBody>
    </Modal>
  );
};

export default NewBankModal;
