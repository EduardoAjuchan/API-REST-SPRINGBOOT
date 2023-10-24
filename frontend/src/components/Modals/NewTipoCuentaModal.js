import React, { useState } from 'react';
import { Form, FormGroup, Label, Input } from 'reactstrap';
import { Modal, ModalHeader, ModalBody, ModalFooter } from 'reactstrap';
import Swal from 'sweetalert2';

const NewTipoCuentaModal = ({ onClose, onUpdateData }) => {
  const [nombreTipoCuenta, setNombreTipoCuenta] = useState('');

  const handleSubmit = async (event) => {
    event.preventDefault();

    // Verifica si el campo está vacío
    if (!nombreTipoCuenta) {
      Swal.fire('¡Error!', 'Debe ingresar un nombre de tipo de cuenta válido.', 'error');
      return;
    }

    // Verifica si el campo contiene números
    if (/\d/.test(nombreTipoCuenta)) {
      Swal.fire('¡Error!', 'No se pueden ingresar números en el nombre del tipo de cuenta.', 'error');
      return;
    }

    try {
      const response = await fetch('http://localhost:8092/api/v1/tipoCuenta', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          idTipoCuenta: 0, // El id es autoincrementable según lo mencionaste
          nombreTipoCuenta,
        }),
      });

      if (!response.ok) {
        throw new Error('Error al guardar los datos');
      }

      Swal.fire('¡Guardado!', 'Los datos se guardaron correctamente.', 'success');
      onClose();

      // Llama a la función para actualizar la tabla en TipoCuenta.js
      if (onUpdateData) {
        onUpdateData();
      }
    } catch (error) {
      Swal.fire('¡Error!', 'No se pudieron guardar los datos.', 'error');
    }
  };

  return (
    <Modal isOpen={true}>
      <ModalHeader>Nuevo Tipo de Cuenta</ModalHeader>
      <ModalBody>
        <Form onSubmit={handleSubmit}>
          <FormGroup>
            <Label>Nombre del Tipo de Cuenta</Label>
            <Input
              type="text"
              value={nombreTipoCuenta}
              onChange={(event) => setNombreTipoCuenta(event.target.value)}
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

export default NewTipoCuentaModal;
