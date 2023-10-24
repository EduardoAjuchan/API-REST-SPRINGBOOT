import React, { useState, useEffect } from 'react';
import { Form, FormGroup, Label, Input } from 'reactstrap';
import { Modal, ModalHeader, ModalBody, ModalFooter } from 'reactstrap';
import Swal from 'sweetalert2';

const NewCuentaModal = ({ onClose, fetchData }) => {
  const [idTipoCuenta, setIdTipoCuenta] = useState('');
  const [idCliente, setIdCliente] = useState('');
  const [saldo, setSaldo] = useState(''); 
  const [tipoCuentas, setTipoCuentas] = useState([]); 
  const [clientes, setClientes] = useState([]); 

  useEffect(() => {
    fetch('http://localhost:8092/api/v1/tipoCuentas')
      .then((response) => response.json())
      .then((data) => setTipoCuentas(data.object))
      .catch((error) => console.error('Error al obtener tipos de cuenta:', error));

    fetch('http://localhost:8092/api/v1/clientes')
      .then((response) => response.json())
      .then((data) => setClientes(data.object))
      .catch((error) => console.error('Error al obtener clientes:', error));
  }, []);

  const handleSubmit = async (event) => {
    event.preventDefault();

    if (!idTipoCuenta || !idCliente || !saldo) {
      Swal.fire('¡Error!', 'Todos los campos son obligatorios.', 'error');
      return;
    }

    try {
      const response = await fetch('http://localhost:8092/api/v1/cuenta', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          idCuenta: 0,
          numeroCuenta: 0,
          idTipoCuenta,
          idCliente,
          saldo,
        }),
      });

      const responseData = await response.json();

      if (!response.ok) {
        throw new Error(responseData.message);
      }

      Swal.fire('¡Guardado!', 'Los datos se guardaron correctamente.', 'success');
      onClose();
      // Actualiza los datos
      
    } catch (error) {
      Swal.fire('¡Error!', `No se pudieron guardar los datos. Error: ${error.message}`, 'error');
    }
  };
  return (
    <Modal isOpen={true}>
      <ModalHeader>Nueva Cuenta</ModalHeader>
      <ModalBody>
        <Form onSubmit={handleSubmit}>
          <FormGroup>
            <Label>Tipo de Cuenta</Label>
            <Input
              type="select"
              value={idTipoCuenta}
              onChange={(event) => setIdTipoCuenta(event.target.value)}
            >
              <option value="" disabled>
                Seleccione un tipo de cuenta
              </option>
              {tipoCuentas.map((tipoCuenta) => (
                <option key={tipoCuenta.idTipoCuenta} value={tipoCuenta.idTipoCuenta}>
                  {tipoCuenta.nombreTipoCuenta}
                </option>
              ))}
            </Input>
          </FormGroup>
          <FormGroup>
            <Label>Cliente</Label>
            <Input
              type="select"
              value={idCliente}
              onChange={(event) => setIdCliente(event.target.value)}
            >
              <option value="" disabled>
                Seleccione un cliente
              </option>
              {clientes.map((cliente) => (
                <option key={cliente.idCliente} value={cliente.idCliente}>
                  {`${cliente.nombreCliente} ${cliente.apellidoPaterno} ${cliente.apellidoMaterno}`}
                </option>
              ))}
            </Input>
          </FormGroup>
          <FormGroup>
            <Label>Saldo</Label>
            <Input
              type="number"
              value={saldo}
              onChange={(event) => setSaldo(event.target.value)}
              placeholder="Saldo"
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

export default NewCuentaModal;
