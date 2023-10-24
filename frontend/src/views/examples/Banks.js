import React, { useState, useEffect } from "react";
import {
  Card,
  CardHeader,
  Table,
  Container,
  Row,
  Button,
  Modal,
  ModalHeader,
  ModalBody,
  Pagination,
  PaginationItem,
  PaginationLink,
} from "reactstrap";
import "../../assets/css/styles.css";
import Header from "components/Headers/BanksHeader";
import NewBankModal from "../../components/Modals/NewBankModal";
import EditBankModal from "../../components/Modals/EditBankModal";
import Swal from "sweetalert2";
const itemsPerPage = 5;
const Banks = () => {
  const [data, setData] = useState([]);
  const [selectedId, setSelectedId] = useState(null);
  const [isEditModalOpen, setIsEditModalOpen] = useState(false);
  const [isNewModalOpen, setIsNewModalOpen] = useState(false);
  const [currentPage, setCurrentPage] = useState(1);

  const [isDeleteConfirmationOpen, setIsDeleteConfirmationOpen] =
    useState(false);
  const [itemToDelete, setItemToDelete] = useState(null);

  const fetchData = async () => {
    try {
      const response = await fetch("http://localhost:8092/api/v1/bancos");
      const responseData = await response.json();
      setData(responseData.object);
    } catch (error) {
      console.error("Error al cargar los datos:", error);
    }
  };
  const totalPages = Math.ceil(data.length / itemsPerPage);
  const startIndex = (currentPage - 1) * itemsPerPage;
  const endIndex = startIndex + itemsPerPage;
  const currentData = data.slice(startIndex, endIndex);

  useEffect(() => {
    fetchData();
  }, [currentPage]);

  const openEditModal = (idBanco) => {
    setSelectedId(idBanco);
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
   
    const idBanco = item.idBanco;
    
    fetch(`http://localhost:8092/api/v1/banco/${idBanco}`, {
      method: "DELETE",
    })
      .then((response) => {
        if (response.ok) {
          
          fetchData();
          
          closeDeleteConfirmationModal();
          
          Swal.fire(
            "Éxito",
            "El banco se ha eliminado correctamente",
            "success"
          );
        } else {
        
          console.error("Error al eliminar el banco");
       
          Swal.fire("Error", "No se pudo eliminar el banco", "error");
        }
      })
      .catch((error) => {
        console.error("Error al eliminar el banco:", error);
        
        Swal.fire("Error", "No se pudo eliminar el banco", "error");
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
                <h3 className="mb-3">Bancos</h3>
                <Button color="success" onClick={openNewModal}>
                  Nuevo Banco
                </Button>
              </CardHeader>
              <Table className="align-items-center table-flush" responsive>
                <thead className="thead-light">
                  <tr>
                    <th scope="col">No.</th>
                    <th scope="col">Nombre del Banco</th>
                    <th scope="col"></th>
                    <th scope="col"></th>
                  </tr>
                </thead>
                <tbody>
                  {currentData.map((item) => (
                    <tr key={item.idBanco}>
                      <td>{item.idBanco}</td>
                      <td>{item.nombreBanco}</td>
                      <td>
                        <Button
                          className="btn btn-info"
                          onClick={() => openEditModal(item.idBanco)}
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

      
      {isEditModalOpen && (
        <EditBankModal
          idBanco={selectedId}
          nombreBanco={
            data.find((item) => item.idBanco === selectedId).nombreBanco
          }
          onClose={closeEditModal}
        />
      )}

      
      {isNewModalOpen && (
        <NewBankModal onClose={closeNewModal} fetchData={fetchData} />
      )}

      
      <Modal
        isOpen={isDeleteConfirmationOpen}
        toggle={closeDeleteConfirmationModal}
      >
        <ModalHeader toggle={closeDeleteConfirmationModal}>
          Eliminar Banco
        </ModalHeader>
        <ModalBody>
          <p>¿Está seguro de que desea eliminar este banco?</p>
          <Button color="danger" onClick={() => handleDelete(itemToDelete)}>
            Confirmar Eliminación
          </Button>
        </ModalBody>
      </Modal>
    </>
  );
};

export default Banks;
