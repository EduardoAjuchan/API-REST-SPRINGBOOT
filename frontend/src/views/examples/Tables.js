import React, { useState, useEffect } from "react";
import {
  Card,
  CardHeader,
  Table,
  Container,
  Row,
  Modal,
  ModalHeader,
  ModalBody,
  ModalFooter,
  Button,
} from "reactstrap";
import "../../assets/css/styles.css";
import Header from "components/Headers/TablesHeader";
import EditModal from "../../components/Modals/EditModal";
import NewModal from "../../components/Modals/NewModal";
import Swal from "sweetalert2";

const Tables = () => {
  const [data, setData] = useState([]);
  const [selectedId, setSelectedId] = useState(null);
  const [isEditModalOpen, setIsEditModalOpen] = useState(false);
  const [isNewModalOpen, setIsNewModalOpen] = useState(false);
  const [isDeleteConfirmationModalOpen, setIsDeleteConfirmationModalOpen] =
    useState(false);

  const fetchData = async () => {
    try {
      const response = await fetch(
        "http://localhost:8092/api/v1/tiposTransferencia"
      );
      const data = await response.json();
      setData(data);
    } catch (error) {
      console.error("Error al cargar los datos:", error);
    }
  };

  useEffect(() => {
    fetchData();
  }, []);

  const openEditModal = (idTipoTransferencia) => {
    setSelectedId(idTipoTransferencia);
    setIsEditModalOpen(true);
  };

  const closeEditModal = () => {
    setSelectedId(null);
    setIsEditModalOpen(false);
    fetchData();
  };

  const openNewModal = () => {
    setIsNewModalOpen(true);
  };

  const closeNewModal = () => {
    setIsNewModalOpen(false);
    fetchData();
  };

  const openDeleteConfirmationModal = (item) => {
    setSelectedId(item.idTipoTransferencia);
    setIsDeleteConfirmationModalOpen(true);
  };

  const closeDeleteConfirmationModal = () => {
    setIsDeleteConfirmationModalOpen(false);
  };

  const handleDelete = async (id) => {
    try {
      const response = await fetch(
        `http://localhost:8092/api/v1/tipoTransferencia/${id}`,
        {
          method: "DELETE",
        }
      );

      if (response.ok) {
        // Eliminación exitosa
        fetchData();
        // Muestra una alerta de éxito con SweetAlert
        Swal.fire({
          icon: "success",
          title: "Eliminado",
          text: "Elemento eliminado correctamente",
        });
      } else {
        console.error("Error al eliminar el elemento");

        Swal.fire({
          icon: "error",
          title: "Error",
          text: "Error al eliminar el elemento",
        });
      }
    } catch (error) {
      console.error("Error al eliminar el elemento:", error);

      Swal.fire({
        icon: "error",
        title: "Error",
        text: "Error al eliminar el elemento",
      });
    }

    setIsDeleteConfirmationModalOpen(false);
  };

  return (
    <>
      <Header />
      <Container className="mt--7" fluid>
        <Row>
          <div className="col">
            <Card className="shadow">
              <CardHeader className="border-0">
                <h3 className="mb-3">Registros</h3>
                <Button color="success" onClick={openNewModal}>
                  Nuevo
                </Button>
              </CardHeader>
              <Table className="align-items-center table-flush" responsive>
                <thead className="thead-light">
                  <tr>
                    <th scope="col">No.</th>
                    <th scope="col">Tipo de Transferencia</th>
                    <th scope="col"></th>
                    <th scope="col"></th>
                  </tr>
                </thead>
                <tbody>
                  {Array.isArray(data.object) &&
                    data.object.map((item) => (
                      <tr key={item.idTipoTransferencia}>
                        <td>{item.idTipoTransferencia}</td>
                        <td>{item.nombreTransferencia}</td>
                        <td>
                          <Button
                            className="btn btn-info"
                            onClick={() =>
                              openEditModal(item.idTipoTransferencia)
                            }
                            id="editar"
                          >
                            Editar
                          </Button>
                        </td>
                        <td>
                          <Button
                            className="btn btn-danger"
                            onClick={() => openDeleteConfirmationModal(item)}
                          >
                            Eliminar
                          </Button>
                        </td>
                      </tr>
                    ))}
                </tbody>
              </Table>

              <Modal isOpen={isEditModalOpen} toggle={closeEditModal}>
                <ModalHeader toggle={closeEditModal}>
                  Editar Tipo de Transferencia
                </ModalHeader>
                <ModalBody>
                  {isEditModalOpen && (
                    <EditModal
                      idTipoTransferencia={selectedId}
                      nombreTransferencia={
                        data.object.find(
                          (item) => item.idTipoTransferencia === selectedId
                        ).nombreTransferencia
                      }
                      onClose={closeEditModal}
                    />
                  )}
                </ModalBody>
                <ModalFooter>
                  <Button color="secondary" onClick={closeEditModal}>
                    Cancelar
                  </Button>
                </ModalFooter>
              </Modal>

              <Modal isOpen={isNewModalOpen} toggle={closeNewModal}>
                <ModalHeader toggle={closeNewModal}>
                  Nuevo Tipo de Transferencia
                </ModalHeader>
                <ModalBody>
                  {isNewModalOpen && <NewModal onClose={closeNewModal} />}
                </ModalBody>
                <ModalFooter>
                  <Button color="secondary" onClick={closeNewModal}>
                    Cancelar
                  </Button>
                </ModalFooter>
              </Modal>

              <Modal
                isOpen={isDeleteConfirmationModalOpen}
                toggle={closeDeleteConfirmationModal}
              >
                <ModalHeader>Confirmar eliminación</ModalHeader>
                <ModalBody>
                  ¿Estás seguro de que deseas eliminar este elemento?
                </ModalBody>
                <ModalFooter>
                  <Button
                    color="danger"
                    onClick={() => handleDelete(selectedId)}
                  >
                    Eliminar
                  </Button>
                  <Button
                    color="secondary"
                    onClick={closeDeleteConfirmationModal}
                  >
                    Cancelar
                  </Button>
                </ModalFooter>
              </Modal>
            </Card>
          </div>
        </Row>
      </Container>
    </>
  );
};

export default Tables;
