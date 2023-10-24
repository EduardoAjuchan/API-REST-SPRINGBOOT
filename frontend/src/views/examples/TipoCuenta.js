import React, { useState, useEffect } from 'react';
import {
  Card,
  CardHeader,
  Table,
  Container,
  Row,
  Modal,
  ModalHeader,
  ModalBody,
  Pagination,
  PaginationItem,
  PaginationLink,
  Button,
} from 'reactstrap';
import '../../assets/css/styles.css';
import Header from 'components/Headers/TipoCuentaHeader';
import NewTipoCuentaModal from '../../components/Modals/NewTipoCuentaModal';
import EditTipoCuentaModal from '../../components/Modals/EditTipoCuentaModal';
import Swal from 'sweetalert2';
const itemsPerPage = 5;

const TipoCuenta = () => {
  const [data, setData] = useState([]);
  const [selectedId, setSelectedId] = useState(null);
  const [isEditModalOpen, setIsEditModalOpen] = useState(false);
  const [isNewModalOpen, setIsNewModalOpen] = useState(false);

  const [isDeleteConfirmationOpen, setIsDeleteConfirmationOpen] = useState(false);
  const [itemToDelete, setItemToDelete] = useState(null);
  const [currentPage, setCurrentPage] = useState(1);
  const fetchData = async () => {
    try {
      const response = await fetch('http://localhost:8092/api/v1/tipoCuentas');
      const responseData = await response.json();
      setData(responseData.object);
    } catch (error) {
      console.error('Error al cargar los datos:', error);
    }
  };
  const totalPages = Math.ceil(data.length / itemsPerPage);
  const startIndex = (currentPage - 1) * itemsPerPage;
  const endIndex = startIndex + itemsPerPage;
  const currentData = data.slice(startIndex, endIndex);

  useEffect(() => {
    fetchData();
  }, [currentData]);

  const openEditModal = (idTipoCuenta) => {
    setSelectedId(idTipoCuenta);
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
    setItemToDelete(item);
    setIsDeleteConfirmationOpen(true);
  };

  const closeDeleteConfirmationModal = () => {
    setItemToDelete(null);
    setIsDeleteConfirmationOpen(false);
  };

  const handleDelete = (item) => {
    // Realiza la eliminación en la API aquí
    const idTipoCuenta = item.idTipoCuenta;
    // Realiza una solicitud DELETE a la API usando el idTipoCuenta
    fetch(`http://localhost:8092/api/v1/tipoCuenta/${idTipoCuenta}`, {
      method: 'DELETE',
    })
      .then((response) => {
        if (response.ok) {
          // Eliminación exitosa, actualiza la lista de tipos de cuenta
          fetchData();
          // Cierra el modal de confirmación
          closeDeleteConfirmationModal();
          // Muestra un mensaje de éxito
          Swal.fire('Éxito', 'El tipo de cuenta se ha eliminado correctamente', 'success');
        } else {
          // Manejar errores en caso de falla en la eliminación
          console.error('Error al eliminar el tipo de cuenta');
          // Muestra un mensaje de error
          Swal.fire('Error', 'No se pudo eliminar el tipo de cuenta', 'error');
        }
      })
      .catch((error) => {
        console.error('Error al eliminar el tipo de cuenta:', error);
        // Muestra un mensaje de error
        Swal.fire('Error', 'No se pudo eliminar el tipo de cuenta', 'error');
      });
  };

  return (
    <>
      <Header />
      <Container className="mt--7" fluid>
        <Row>
          <div className="col">
            <Card className="shadow">
              <CardHeader className="border-0">
                <h3 className="mb-3">Tipos de Cuenta</h3>
                <Button color="success" onClick={openNewModal}>
                  Nuevo Tipo de Cuenta
                </Button>
              </CardHeader>
              <Table className="align-items-center table-flush" responsive>
                <thead className="thead-light">
                  <tr>
                    <th scope="col">No.</th>
                    <th scope="col">Nombre del Tipo de Cuenta</th>
                    <th scope="col"></th>
                    <th scope="col"></th>
                  </tr>
                </thead>
                <tbody>
                  {currentData.map((item) => (
                    <tr key={item.idTipoCuenta}>
                      <td>{item.idTipoCuenta}</td>
                      <td>{item.nombreTipoCuenta}</td>
                      <td>
                        <Button
                          className="btn btn-info"
                          onClick={() => openEditModal(item.idTipoCuenta)}
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
              <div className="d-flex justify-content-center">
                <Pagination aria-label="Page navigation">
                  <PaginationItem disabled={currentPage === 1}>
                    <PaginationLink previous onClick={() => setCurrentPage(currentPage - 1)} />
                  </PaginationItem>
                  {[...Array(totalPages).keys()].map((page) => (
                    <PaginationItem key={page} active={page + 1 === currentPage}>
                      <PaginationLink onClick={() => setCurrentPage(page + 1)}>{page + 1}</PaginationLink>
                    </PaginationItem>
                  ))}
                  <PaginationItem disabled={currentPage === totalPages}>
                    <PaginationLink next onClick={() => setCurrentPage(currentPage + 1)} />
                  </PaginationItem>
                </Pagination>
              </div>
            </Card>
          </div>
        </Row>
      </Container>

      {/* Modal Editar Tipo de Cuenta */}
      {isEditModalOpen && (
        <EditTipoCuentaModal
          idTipoCuenta={selectedId}
          nombreTipoCuenta={
            data.find((item) => item.idTipoCuenta === selectedId).nombreTipoCuenta
          }
          onClose={closeEditModal}
        />
      )}

      {/* Modal Nuevo Tipo de Cuenta */}
      {isNewModalOpen && (
        <NewTipoCuentaModal onClose={closeNewModal} />
      )}

      {/* Modal de Confirmación de Eliminación */}
      <Modal isOpen={isDeleteConfirmationOpen} toggle={closeDeleteConfirmationModal}>
        <ModalHeader toggle={closeDeleteConfirmationModal}>Eliminar Tipo de Cuenta</ModalHeader>
        <ModalBody>
          <p>¿Está seguro de que desea eliminar este tipo de cuenta?</p>
          <Button
            color="danger"
            onClick={() => handleDelete(itemToDelete)}
          >
            Confirmar Eliminación
          </Button>
        </ModalBody>
      </Modal>
    </>
  );
};

export default TipoCuenta;
