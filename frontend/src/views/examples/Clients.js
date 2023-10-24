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
  Pagination,
  PaginationItem,
  PaginationLink,
  Button,
} from "reactstrap";
import "../../assets/css/styles.css";
import Header from "components/Headers/ClientsHeader";
import NewClientModal from "../../components/Modals/NewClientModal";
import EditClientModal from "../../components/Modals/EditClientModal";
import Swal from 'sweetalert2';
const itemsPerPage = 10;
const Clients = () => {
  const [clients, setClients] = useState([]);
  const [banks, setBanks] = useState([]);
  const [selectedClient, setSelectedClient] = useState(null);
  const [isNewModalOpen, setIsNewModalOpen] = useState(false);
  const [isEditModalOpen, setIsEditModalOpen] = useState(false);
  const [isDeleteConfirmationOpen, setIsDeleteConfirmationOpen] = useState(false);
  const [currentPage, setCurrentPage] = useState(1);

  const [itemToDelete, setItemToDelete] = useState(null);

  
  const fetchClients = async () => {
    try {
      const response = await fetch("http://localhost:8092/api/v1/clientes");
      const responseData = await response.json();
      setClients(responseData.object);
    } catch (error) {
      console.error("Error al cargar los datos de clientes:", error);
    }
  };

  
  const fetchBanks = async () => {
    try {
      const response = await fetch("http://localhost:8092/api/v1/bancos");
      const responseData = await response.json();
      setBanks(responseData.object);
    } catch (error) {
      console.error("Error al cargar los datos de bancos:", error);
    }
  };
  const totalPages = Math.ceil(clients.length / itemsPerPage);
  const startIndex = (currentPage - 1) * itemsPerPage;
  const endIndex = startIndex + itemsPerPage;
  const currentClients = clients.slice(startIndex, endIndex);


  useEffect(() => {
    fetchClients();
    fetchBanks();
  }, [currentPage ]);

  const openNewModal = () => {
    setIsNewModalOpen(true);
  };

  const closeNewModal = () => {
    setIsNewModalOpen(false);
    
    fetchClients();
  };

  const openEditModal = (client) => {
    setSelectedClient(client);
    setIsEditModalOpen(true);
  };

  const closeEditModal = () => {
    setSelectedClient(null);
    setIsEditModalOpen(false);
    
    fetchClients();
  };

  const openDeleteConfirmationModal = (client) => {
    setItemToDelete(client);
    setIsDeleteConfirmationOpen(true);
  };

  const closeDeleteConfirmationModal = () => {
    setItemToDelete(null);
    setIsDeleteConfirmationOpen(false);
  };

  const handleDelete = (client) => {
    
    const clientId = client.idCliente;
    
    fetch(`http://localhost:8092/api/v1/cliente/${clientId}`, {
      method: 'DELETE',
    })
      .then((response) => {
        if (response.ok) {
          
          fetchClients();
         
          closeDeleteConfirmationModal();
         
          Swal.fire('Éxito', 'El cliente se ha eliminado correctamente', 'success');
        } else {
         
          console.error('Error al eliminar el cliente');
          
          Swal.fire('Error', 'No se pudo eliminar el cliente', 'error');
        }
      })
      .catch((error) => {
        console.error('Error al eliminar el cliente:', error);
        
        Swal.fire('Error', 'No se pudo eliminar el cliente', 'error');
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
                <h3 className="mb-3">Clientes</h3>
                <Button color="success" onClick={openNewModal}>
                  Nuevo Cliente
                </Button>
              </CardHeader>
              <Table className="align-items-center table-flush" responsive>
                <thead className="thead-light">
                  <tr>
                    <th scope="col">No.</th>
                    <th scope="col">Nombre</th>
                    <th scope="col">Apellido Paterno</th>
                    <th scope="col">Apellido Materno</th>
                    <th scope="col">Edad</th>
                    <th scope="col">Banco</th>
                    <th scope="col"></th>
                    <th scope="col"></th>
                  </tr>
                </thead>
                <tbody>
                  {currentClients.map((client) => (
                    <tr key={client.idCliente}>
                      <td>{client.idCliente}</td>
                      <td>{client.nombreCliente}</td>
                      <td>{client.apellidoPaterno}</td>
                      <td>{client.apellidoMaterno}</td>
                      <td>{client.edad}</td>
                      <td>
                        {
                          banks?.find((bank) => bank.idBanco === client.fkBanco)
                            ?.nombreBanco
                        }
                      </td>
                      <td>
                        <Button
                          className="btn btn-info"
                          onClick={() => openEditModal(client)}
                        >
                          Editar
                        </Button>
                      </td>
                      <td>
                        <Button
                          className="btn btn-danger"
                          onClick={() => openDeleteConfirmationModal(client)}
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

      {/* Modal Nuevo Cliente */}
      <Modal isOpen={isNewModalOpen} toggle={closeNewModal}>
        <ModalHeader toggle={closeNewModal}>Nuevo Cliente</ModalHeader>
        <ModalBody>
          {isNewModalOpen && (
            <NewClientModal banks={banks} onClose={closeNewModal} />
          )}
        </ModalBody>
      </Modal>

      
      <Modal isOpen={isEditModalOpen} toggle={closeEditModal}>
        <ModalHeader toggle={closeEditModal}>Editar Cliente</ModalHeader>
        <ModalBody>
          {isEditModalOpen && (
            <EditClientModal
              client={selectedClient}
              banks={banks}
              onClose={closeEditModal}
            />
          )}
        </ModalBody>
      </Modal>

      
      <Modal isOpen={isDeleteConfirmationOpen} toggle={closeDeleteConfirmationModal}>
        <ModalHeader toggle={closeDeleteConfirmationModal}>Eliminar Cliente</ModalHeader>
        <ModalBody>
          <p>¿Está seguro de que desea eliminar a este cliente?</p>
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

export default Clients;