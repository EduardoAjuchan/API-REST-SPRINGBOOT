import React, { useState } from 'react';
import {
  Modal,
  ModalHeader,
  ModalBody,
  ModalFooter,
  Button,
  Form,
  FormGroup,
  Label,
  Input,
} from 'reactstrap';
import Swal from 'sweetalert2';

const NewClientModal = ({ banks, onClose }) => {
  const [newClient, setNewClient] = useState({
    nombreCliente: '',
    apellidoPaterno: '',
    apellidoMaterno: '',
    edad: '',
    fkBanco: '',
  });

  const handleInputChange = (e) => {
    const { name, value } = e.target;

    
    if (name === 'nombreCliente' || name === 'apellidoPaterno' || name === 'apellidoMaterno') {
     
      const lettersOnly = /^[A-Za-z\s]*$/;
      if (!lettersOnly.test(value)) {
        return;
      }
    }

  
    if (name === 'edad') {
 
      const numbersOnly = /^\d*$/;
      if (!numbersOnly.test(value)) {
        return;
      }
    }

    setNewClient({
      ...newClient,
      [name]: value,
    });
  };

  const handleSubmit = async (event) => {
    event.preventDefault();


    if (
      !newClient.nombreCliente ||
      !newClient.apellidoPaterno ||
      !newClient.apellidoMaterno ||
      !newClient.edad ||
      !newClient.fkBanco
    ) {
      Swal.fire('¡Error!', 'Todos los campos son obligatorios.', 'error');
      return;
    }

  
    if (isNaN(newClient.edad) || newClient.edad <= 0) {
      Swal.fire('¡Error!', 'La edad debe ser un número válido mayor que 0.', 'error');
      return;
    }

    // Obtener el ID del usuario del localStorage
    const idUsuario = JSON.parse(localStorage.getItem('userInfo')).id;

    try {
      const response = await fetch('http://localhost:8092/api/v1/cliente', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          ...newClient,
          idCliente: 0, 
          fkLoginUser: idUsuario, 
        }),
      });

      if (!response.ok) {
        throw new Error('Error al guardar el nuevo cliente');
      }

      Swal.fire('¡Guardado!', 'El nuevo cliente se ha guardado correctamente.', 'success');
      onClose();
    } catch (error) {
      Swal.fire('¡Error!', 'No se pudo guardar el nuevo cliente.', 'error');
    }
  };

  return (
    <Modal isOpen={true}>
      <ModalHeader>Regístrate como cliente</ModalHeader>
      <ModalBody>
        <Form onSubmit={handleSubmit}>
          <FormGroup>
            <Label>Nombre del Cliente</Label>
            <Input
              type="text"
              name="nombreCliente"
              value={newClient.nombreCliente}
              onChange={handleInputChange}
            />
          </FormGroup>
          <FormGroup>
            <Label>Apellido Paterno</Label>
            <Input
              type="text"
              name="apellidoPaterno"
              value={newClient.apellidoPaterno}
              onChange={handleInputChange}
            />
          </FormGroup>
          <FormGroup>
            <Label>Apellido Materno</Label>
            <Input
              type="text"
              name="apellidoMaterno"
              value={newClient.apellidoMaterno}
              onChange={handleInputChange}
            />
          </FormGroup>
          <FormGroup>
            <Label>Edad</Label>
            <Input
              type="number"
              name="edad"
              value={newClient.edad}
              onChange={handleInputChange}
            />
          </FormGroup>
          <FormGroup>
            <Label>Banco</Label>
            <Input
              type="select"
              name="fkBanco"
              value={newClient.fkBanco}
              onChange={handleInputChange}
            >
              <option value="" disabled>
                Seleccione un banco
              </option>
              {banks.map((bank) => (
                <option key={bank.idBanco} value={bank.idBanco}>
                  {bank.nombreBanco}
                </option>
              ))}
            </Input>
          </FormGroup>
          <ModalFooter>
            <Button color="primary" type="submit">
              Guardar
            </Button>
          </ModalFooter>
        </Form>
      </ModalBody>
    </Modal>
  );
};

export default NewClientModal;
