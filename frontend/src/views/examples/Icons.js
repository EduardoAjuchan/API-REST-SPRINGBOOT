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
  ModalFooter,
  Pagination,
  PaginationItem,
  PaginationLink,
  Button,
} from 'reactstrap';
import '../../assets/css/styles.css';
import Header from 'components/Headers/CuentasHeader';
import NewCuentaModal from '../../components/Modals/NewCuentaModal';
import Swal from 'sweetalert2';
const itemsPerPage = 10;

const Icons = () => {
  const [data, setData] = useState([]);
  const [isNewModalOpen, setIsNewModalOpen] = useState(false);
  const [tipoCuentas, setTipoCuentas] = useState([]);
  const [clientes, setClientes] = useState([]);
  const [deleteModalOpen, setDeleteModalOpen] = useState(false);
  const [cuentaToDelete, setCuentaToDelete] = useState(null);
  const [currentPage, setCurrentPage] = useState(1);

  const fetchData = async () => {
    try {
      // Obtener los tipos de cuenta desde la API
      const tipoCuentasResponse = await fetch('http://localhost:8092/api/v1/tipoCuentas');
      const tipoCuentasData = await tipoCuentasResponse.json();
      setTipoCuentas(tipoCuentasData.object);

      // Obtener los clientes desde la API
      const clientesResponse = await fetch('http://localhost:8092/api/v1/clientes');
      const clientesData = await clientesResponse.json();
      setClientes(clientesData.object);

      // Obtener todas las cuentas desde la API
      const cuentasResponse = await fetch('http://localhost:8092/api/v1/cuentas');
      const cuentasData = await cuentasResponse.json();
      setData(cuentasData.object);
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
  }, [currentPage]);

  const openNewModal = () => {
    setIsNewModalOpen(true);
  };

  const closeNewModal = () => {
    setIsNewModalOpen(false);
    fetchData(); // Actualiza los datos después de cerrar el modal de nuevo registro
  };

  const openDeleteModal = (cuenta) => {
    setCuentaToDelete(cuenta);
    setDeleteModalOpen(true);
  };

  const closeDeleteModal = () => {
    setCuentaToDelete(null);
    setDeleteModalOpen(false);
  };

  const handleDelete = async () => {
    if (!cuentaToDelete) {
      return;
    }
  
    try {
      const response = await fetch(`http://localhost:8092/api/v1/cuenta/${cuentaToDelete.idCuenta}`, {
        method: 'DELETE',
      });
  
      if (response.ok) {
        fetchData(); // Actualiza los datos después de eliminar
        // Mostrar alerta de eliminación exitosa
        Swal.fire('¡Eliminado!', 'La cuenta ha sido eliminada exitosamente.', 'success');
      }
  
      closeDeleteModal();
    } catch (error) {
      console.error('Error al eliminar la cuenta:', error);
    }
  };

  return (
    <>
      <Header />
      <Container className="mt--7" fluid>
        <Row>
          <div className="col">
            <Card className="shadow">
              <CardHeader className="border-0">
                <h3 className="mb-3">Cuentas Bancarias</h3>
                <Button color="success" onClick={openNewModal}>
                  Nueva Cuenta
                </Button>
              </CardHeader>
              <Table className="align-items-center table-flush" responsive>
                <thead className="thead-light">
                  <tr>
                    <th scope="col">No.</th>
                    <th scope="col">Número de Cuenta</th>
                    <th scope="col">Tipo de Cuenta</th>
                    <th scope="col">Cliente</th>
                    <th scope="col">Saldo</th>
                    <th scope="col"></th>
                  </tr>
                </thead>
                <tbody>
                  {currentData.map((cuenta) => (
                    <tr key={cuenta.idCuenta}>
                      <td>{cuenta.idCuenta}</td>
                      <td>{cuenta.numeroCuenta}</td>
                      <td>
                        {tipoCuentas.find((tipoCuenta) => tipoCuenta.idTipoCuenta === cuenta.idTipoCuenta)
                          ?.nombreTipoCuenta}
                      </td>
                      <td>
                        {clientes.find((cliente) => cliente.idCliente === cuenta.idCliente)?.nombreCliente}{' '}
                        {clientes.find((cliente) => cliente.idCliente === cuenta.idCliente)?.apellidoPaterno}{' '}
                        {clientes.find((cliente) => cliente.idCliente === cuenta.idCliente)?.apellidoMaterno}
                      </td>
                      <td>{`Q${cuenta.saldo.toFixed(2)}`}</td>
                      <td>
                        <Button color="danger" onClick={() => openDeleteModal(cuenta)}>
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
      {/* Modal Nuevo Tipo de Cuenta */}
      <Modal isOpen={isNewModalOpen} toggle={closeNewModal}>
        <ModalHeader toggle={closeNewModal}>Nuevo Tipo de Cuenta</ModalHeader>
        <ModalBody>{isNewModalOpen && <NewCuentaModal onClose={closeNewModal} />}</ModalBody>
      </Modal>
      {/* Modal de Confirmación de Eliminación */}
      <Modal isOpen={deleteModalOpen} toggle={closeDeleteModal}>
        <ModalHeader toggle={closeDeleteModal}>Confirmar Eliminación</ModalHeader>
        <ModalBody>
          ¿Estás seguro de que quieres eliminar esta cuenta?
        </ModalBody>
        <ModalFooter>
          <Button color="danger" onClick={handleDelete}>
            Eliminar
          </Button>{' '}
          <Button color="secondary" onClick={closeDeleteModal}>
            Cancelar
          </Button>
        </ModalFooter>
      </Modal>
    </>
  );
};

export default Icons;
