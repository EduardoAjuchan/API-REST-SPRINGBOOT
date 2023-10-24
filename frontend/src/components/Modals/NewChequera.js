import React, { useState } from "react";
import { Modal, ModalHeader, ModalBody, Input, Button } from "reactstrap";
import Swal from "sweetalert2";

const NewChequera = ({ isOpen, toggle, onCreate }) => {
  const [cantidadCheques, setCantidadCheques] = useState(10);
  const [inputError, setInputError] = useState(false);
  const [idCuenta, setIdCuenta] = useState("");

  const handleCantidadChequesChange = (e) => {
    setCantidadCheques(parseInt(e.target.value));
  };

  const handleCreateChequera = async () => {
    if (idCuenta === "" || isNaN(idCuenta)) {
      setInputError(true);
      return;
    }

    setInputError(false);

    try {
      const response = await fetch(
        `http://localhost:8092/api/v1/chequera?idCuenta=${idCuenta}&cantidad=${cantidadCheques}`,
        {
          method: "POST",
        }
      );

      if (response.ok) {
        onCreate();
        Swal.fire("Éxito", "La chequera se ha creado correctamente", "success").then(() => {
          // Cerrar el modal después de mostrar el mensaje de éxito
          toggle();
        });
      } else {
        Swal.fire("Error", "No se pudo crear la chequera", "error");
      }
    } catch (error) {
      console.error("Error al crear la chequera:", error);
      Swal.fire("Error", "No se pudo crear la chequera", "error");
    }
  };

  return (
    <Modal isOpen={isOpen} toggle={toggle}>
      <ModalHeader toggle={toggle}>Nueva Chequera</ModalHeader>
      <ModalBody>
        <div>
          <label>ID de Cuenta:</label>
          <Input
            type="number"
            value={idCuenta}
            invalid={inputError}
            onChange={(e) => {
              const value = e.target.value;
              setIdCuenta(value);
            }}
          />
        </div>
        <div>
          <label>Cantidad de Cheques:</label>
          <Input 
            type="range"
            min="10"
            max="30"
            step="10"
            value={cantidadCheques}
            onChange={handleCantidadChequesChange}
          />
          <div className="d-flex justify-content-between mb-4">
            <span>10</span>
            <span>20</span>
            <span>30</span>
          </div>
        </div>
        <div>
          <Button color="primary" onClick={handleCreateChequera}>
            Crear
          </Button>
          <Button className="btn btn-danger" onClick={toggle}>
            Cancelar
          </Button>
        </div>
      </ModalBody>
    </Modal>
  );
};

export default NewChequera;