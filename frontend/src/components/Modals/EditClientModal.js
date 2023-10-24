import React, { useState, useEffect } from "react";
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
} from "reactstrap";
import Swal from "sweetalert2";

const EditClientModal = ({ client: initialClient, banks, onClose }) => {
  const [client, setClient] = useState(initialClient);

  useEffect(() => {
    setClient(initialClient);
  }, [initialClient]);

  const handleSubmit = async (event) => {
    event.preventDefault();

   
    if (
      !client.nombreCliente ||
      !client.apellidoPaterno ||
      !client.apellidoMaterno ||
      !client.edad ||
      !client.fkBanco
    ) {
      Swal.fire("¡Error!", "Todos los campos son obligatorios.", "error");
      return;
    }

    if (isNaN(client.edad) || client.edad <= 0) {
      Swal.fire(
        "¡Error!",
        "La edad debe ser un número válido mayor que 0.",
        "error"
      );
      return;
    }

    try {
      const response = await fetch(
        `http://localhost:8092/api/v1/cliente/${client.idCliente}`,
        {
          method: "PUT",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify(client),
        }
      );

      if (!response.ok) {
        throw new Error("Error al guardar los cambios");
      }

      Swal.fire(
        "¡Guardado!",
        "Los cambios se han guardado correctamente.",
        "success"
      );
      onClose();
    } catch (error) {
      Swal.fire("¡Error!", "No se pudieron guardar los cambios.", "error");
    }
  };

  return (
    <Modal isOpen={true}>
      <ModalHeader>Editar Cliente</ModalHeader>
      <ModalBody>
        <Form onSubmit={handleSubmit}>
          <FormGroup>
            <Label>Nombre del Cliente</Label>
            <Input
              type="text"
              name="nombreCliente"
              value={client.nombreCliente}
              onChange={(e) =>
                setClient({ ...client, nombreCliente: e.target.value })
              }
            />
          </FormGroup>
          <FormGroup>
            <Label>Apellido Paterno</Label>
            <Input
              type="text"
              name="apellidoPaterno"
              value={client.apellidoPaterno}
              onChange={(e) =>
                setClient({ ...client, apellidoPaterno: e.target.value })
              }
            />
          </FormGroup>
          <FormGroup>
            <Label>Apellido Materno</Label>
            <Input
              type="text"
              name="apellidoMaterno"
              value={client.apellidoMaterno}
              onChange={(e) =>
                setClient({ ...client, apellidoMaterno: e.target.value })
              }
            />
          </FormGroup>
          <FormGroup>
            <Label>Edad</Label>
            <Input
              type="number"
              name="edad"
              value={client.edad}
              onChange={(e) => setClient({ ...client, edad: e.target.value })}
            />
          </FormGroup>
          <FormGroup>
            <Label>Banco</Label>
            <Input
              type="select"
              name="fkBanco"
              value={client.fkBanco}
              onChange={(e) =>
                setClient({ ...client, fkBanco: e.target.value })
              }
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
            <Button color="danger" onClick={onClose}>
              Cancelar
            </Button>
          </ModalFooter>
        </Form>
      </ModalBody>
    </Modal>
  );
};

export default EditClientModal;
