import React, { useState, useEffect } from "react";
import { Modal, ModalHeader, ModalBody, ModalFooter, Input, Button } from "reactstrap";
import Swal from "sweetalert2"; // Corregí la importación de SweetAlert

const TransferModal = ({ isOpen, toggle }) => {
  const [cuentasCliente, setCuentasCliente] = useState([]);
  const [selectedCuenta, setSelectedCuenta] = useState(null);
  const [cuentaDestinatario, setCuentaDestinatario] = useState("");
  const [monto, setMonto] = useState("");
  const [selectedTipoTransferencia, setSelectedTipoTransferencia] = useState(null);
  const [tiposTransferencia, setTiposTransferencia] = useState([]);
  const [idCliente, setIdCliente] = useState(null);

  useEffect(() => {
    obtenerIdCliente();
    obtenerTiposTransferencia();
  }, []);
  // Restablecer los campos del modal cuando se abre o se cierra
  useEffect(() => {
    if (isOpen) {
      setCuentaDestinatario("");
      setMonto("");
      setSelectedTipoTransferencia(null);
      setSelectedCuenta(null);
    }
  }, [isOpen]);
  const obtenerTiposTransferencia = () => {
    // Reemplaza esta URL con la dirección real de tu API para obtener los tipos de transferencia.
    fetch("http://localhost:8092/api/v1/tiposTransferencia")
      .then((response) => response.json())
      .then((data) => {
        if (data && data.object) {
          setTiposTransferencia(data.object);
        }
      })
      .catch((error) => {
        console.error("Error al obtener los tipos de transferencia:", error);
      });
  };

  const obtenerIdCliente = () => {
    // Obtén el valor "clientIdData" del localStorage
    const clientIdData = localStorage.getItem("clientIdData");
    if (clientIdData) {
      const id = JSON.parse(clientIdData);

      // envía ese id a la API http://localhost:8092/api/v1/cuentasCliente/idCliente?idCliente={id} y obtiene las cuentas del cliente
      fetch(`http://localhost:8092/api/v1/cuentasCliente/idCliente?idCliente=${id}`)
        .then((response) => response.json())
        .then((data) => {
          if (data && data.object) {
            setCuentasCliente(data.object);
          }
        })
        .catch((error) => {
          console.error("Error al obtener las cuentas del cliente:", error);
        });
      setIdCliente(id);
    }
  };

  const toggleModal = () => {
    toggle();
  };
  const [selectedCuentaNum, setSelectedCuentaNum] = useState(null); // Declarar un estado para almacenar el número de cuenta

const handleCuentaSelection = (selectedCuentaNum) => {
  console.log('Selected cuenta number:', selectedCuentaNum);
  setSelectedCuentaNum(selectedCuentaNum); // Almacenar el número de cuenta en el estado
  if (idCliente) {
    const selectedCuentaObj = cuentasCliente.find((cuenta) => cuenta.numero_cuenta === selectedCuentaNum);
    if (selectedCuentaObj) {
      setSelectedCuenta(selectedCuentaObj);
    }
  }
};

const handleTransferir = () => {
  if (!cuentaDestinatario || !monto || !selectedCuentaNum || !selectedTipoTransferencia) {
    Swal.fire("Error", "Por favor, completa todos los campos necesarios para la transferencia", "error");
    return;
  }

  // Construye la URL con los parámetros en la cadena de consulta
  const url = `http://localhost:8092/api/v1/cuenta/transferencia?cuentaRemitente=${selectedCuentaNum}&cuentaDestinatario=${cuentaDestinatario}&monto=${monto}&idTipoTransferencia=${selectedTipoTransferencia.idTipoTransferencia}`;

  // Realiza la solicitud POST a la URL construida
  fetch(url, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
  })
    .then((response) => {
      console.log("Respuesta de la API:", response);
      if (response.status === 201) {
        // Si el estado de respuesta es 201, considera la transferencia como exitosa
        Swal.fire("Transferencia Exitosa", "La transferencia se ha completado con éxito", "success");
      } else {
        // En caso contrario, muestra un mensaje de error
        Swal.fire("Error", "Hubo un problema al realizar la transferencia", "error");
      }
      toggle(); // Cierra el modal
    })
    .catch((error) => {
      console.error("Error al realizar la transferencia:", error);
      Swal.fire("Error", "Hubo un problema al realizar la transferencia", "error");
    });
};
  
return (
  <Modal isOpen={isOpen} toggle={toggleModal}>
    <ModalHeader toggle={toggleModal}>Transferencia</ModalHeader>
    <ModalBody>
      <div>
        <div className="form-group">
          <label>Cuenta Remitente</label>
          <select
            className="form-control"
            value={selectedCuenta ? selectedCuenta.numero_cuenta : ""}
            onChange={(e) => {
              const selectedCuentaNum = e.target.value;
              setSelectedCuenta(selectedCuentaNum);
              handleCuentaSelection(selectedCuentaNum);
            }}
          >
            <option value="" disabled>
              Selecciona una cuenta
            </option>
            {cuentasCliente.map((cuenta) => (
              <option key={cuenta.numero_cuenta} value={cuenta.numero_cuenta}>
                {`${cuenta.numero_cuenta} - ${cuenta.tipo_cuenta} - Saldo ${cuenta.saldo}`}
              </option>
            ))}
          </select>
        </div>
      </div>

      <div>
        <div className="form-group">
          <label>Cuenta Destinatario</label>
          <Input
            type="text"
            value={cuentaDestinatario}
            onChange={(e) => setCuentaDestinatario(e.target.value)}
            placeholder="Cuenta Destinatario"
          />
        </div>
      </div>

      <div>
        <div className="form-group">
          <label>Monto de Transferencia</label>
          <Input
            type="text"
            value={monto}
            onChange={(e) => setMonto(e.target.value)}
            placeholder="Monto"
          />
        </div>
      </div>

      <div>
        <div className="form-group">
          <label>Tipo de Transferencia</label>
          <select
            className="form-control"
            value={selectedTipoTransferencia ? selectedTipoTransferencia.idTipoTransferencia : ""}
            onChange={(e) => {
              const selectedTypeId = e.target.value;
              setSelectedTipoTransferencia(
                tiposTransferencia.find((tipo) => tipo.idTipoTransferencia === Number(selectedTypeId))
              );
            }}
          >
            <option value="" disabled>
              Selecciona un tipo de transferencia
            </option>
            {tiposTransferencia.map((tipo) => (
              <option key={tipo.idTipoTransferencia} value={tipo.idTipoTransferencia}>
                {tipo.nombreTransferencia}
              </option>
            ))}
          </select>
        </div>
      </div>
    </ModalBody>
    <ModalFooter>
      <Button color="primary" onClick={handleTransferir}>
        Transferir
      </Button>
      <Button color="danger" onClick={toggleModal}>
        Cancelar
      </Button>
    </ModalFooter>
  </Modal>
);
};

export default TransferModal;