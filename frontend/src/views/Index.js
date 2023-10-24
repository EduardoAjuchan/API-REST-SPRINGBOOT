import React, { useState, useEffect } from 'react';
import Swal from 'sweetalert2';
import {
  Card,
  CardHeader,
  CardBody,
  Table,
  Container,
  Row,
  Col,
  FormGroup,
  Input,
  Button,
  Pagination,
  PaginationItem,
  PaginationLink,
  Modal,
  
} from 'reactstrap';
import Header from 'components/Headers/TransaccionesHeader';
import NewChequera from 'components/Modals/NewChequera';

const itemsPerPage = 5;

const Transacciones = () => {
  const [depositAmount, setDepositAmount] = useState(0);
  const [accountNumber, setAccountNumber] = useState('');
  const [deposits, setDeposits] = useState([]);
  const [currentPage, setCurrentPage] = useState(1);

  const [isNewChequeraModalOpen, setIsNewChequeraModalOpen] = useState(false);
  const [chequeData, setChequeData] = useState({
    accountNumber: '',
    chequeNumber: '',
    beneficiary: '',
    amount: 0,
  });

  useEffect(() => {
    fetchDeposits();
  }, [currentPage]);

  const fetchDeposits = async () => {
    try {
      const response = await fetch(
        `http://localhost:8092/api/v1/depositos?page=${currentPage}&pageSize=${itemsPerPage}`
      );
      const data = await response.json();
      if (data.object && Array.isArray(data.object)) {
        setDeposits(data.object);
      } else {
        setDeposits([]);
      }
    } catch (error) {
      console.error('Error al cargar los depósitos:', error);
    }
  };

  const handleDepositSubmit = async () => {
    try {
      const response = await fetch(
        `http://localhost:8092/api/v1/cuenta/deposito?numeroCuenta=${accountNumber}&monto=${depositAmount}`,
        {
          method: 'POST',
        }
      );

      if (response.ok) {
        fetchDeposits();
        setAccountNumber('');
        setDepositAmount(0);

        Swal.fire('Depósito exitoso', 'El depósito se ha realizado con éxito.', 'success');
      } else {
        console.error('Error al realizar el depósito');
        Swal.fire('Error', 'Ha ocurrido un error al realizar el depósito.', 'error');
      }
    } catch (error) {
      console.error('Error al realizar el depósito:', error);
    }
  };

  const totalPages = Math.ceil(deposits.length / itemsPerPage);
  const startIndex = (currentPage - 1) * itemsPerPage;
  const endIndex = startIndex + itemsPerPage;
  const currentDeposits = deposits.slice(startIndex, endIndex);

  const openNewChequeraModal = () => {
    setIsNewChequeraModalOpen(true);
  };

  const closeNewChequeraModal = () => {
    setIsNewChequeraModalOpen(false);
  };

  const handleCobrarCheque = async () => {
    try {
      const response = await fetch(
        `http://localhost:8092/api/v1/ActualizarCheque/${chequeData.accountNumber}/${chequeData.chequeNumber}/${chequeData.beneficiary}/${chequeData.amount}`,
        {
          method: 'POST',
        }
      );

      const responseData = await response.json();

      if (response.ok) {
        fetchDeposits();
        setChequeData({
          accountNumber: '',
          chequeNumber: '',
          beneficiary: '',
          amount: 0,
        });

        Swal.fire('Éxito', 'El cheque se ha cobrado correctamente', 'success');
      } else {
        console.error('Error al cobrar el cheque');
        Swal.fire('Error', responseData.mensaje, 'error');
      }
    } catch (error) {
      console.error('Error al cobrar el cheque:', error);
    }
  };

  return (
    <>
      <Header />
      <Container className="mt--7" fluid>
        <Row>
          <Col md="6">
            <Card>
              <CardHeader className="border-0">
                <h3 className="mb-0">Depósitos</h3>
              </CardHeader>
              <CardBody>
                <FormGroup>
                  <label>Número de Cuenta</label>
                  <Input
                    type="number"
                    placeholder="Número de Cuenta"
                    value={accountNumber}
                    onChange={(e) => setAccountNumber(e.target.value)}
                  />
                </FormGroup>
                <FormGroup>
                  <label>Monto del Depósito (Q)</label>
                  <Input
                    type="number"
                    placeholder="Monto"
                    value={depositAmount}
                    onChange={(e) => setDepositAmount(e.target.value)}
                  />
                </FormGroup>
                <Button color="success" onClick={handleDepositSubmit}>
                  Nuevo Depósito
                </Button>
              </CardBody>
              <h3 className="mb-2">Depósitos</h3>
              <Table className="align-items-center table-dark mb-2" responsive>
                <thead className="thead-dark">
                  <tr>
                    <th scope="col">No.</th>
                    <th scope="col">Número de Cuenta</th>
                    <th scope="col">Monto</th>
                    <th scope="col">Fecha y Hora</th>
                  </tr>
                </thead>
                <tbody>
                  {currentDeposits.map((deposit) => (
                    <tr key={deposit.idDeposito}>
                      <td>{deposit.idDeposito}</td>
                      <td>{deposit.numeroCuenta}</td>
                      <td>Q {deposit.monto}</td>
                      <td>{new Date(deposit.fecha).toLocaleString()}</td>
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
          </Col>
          <Col md="6">
            <Card>
              <CardHeader>
                <h2 className="mb-0">Operaciones con Cheques</h2>
              </CardHeader>
              <CardBody>
                <Button color="info" className="mb-4" onClick={openNewChequeraModal}>
                  Nueva Chequera
                </Button>
                <FormGroup>
                  <h3 className="mb-3">Cobrar Cheque</h3>
                  <label>Número de Cuenta</label>
                  <Input
                    type="number"
                    placeholder="Número de Cuenta"
                    value={chequeData.accountNumber}
                    onChange={(e) =>
                      setChequeData({ ...chequeData, accountNumber: e.target.value })
                    }
                  />
                </FormGroup>
                <FormGroup>
                  <label>Número de Cheque</label>
                  <Input
                    type="number"
                    placeholder="Número de Cheque"
                    value={chequeData.chequeNumber}
                    onChange={(e) =>
                      setChequeData({ ...chequeData, chequeNumber: e.target.value })
                    }
                  />
                </FormGroup>
                <FormGroup>
                  <label>Beneficiario</label>
                  <Input
                    type="text"
                    placeholder="Beneficiario"
                    value={chequeData.beneficiary}
                    onChange={(e) =>
                      setChequeData({ ...chequeData, beneficiary: e.target.value })
                    }
                  />
                </FormGroup>
                <FormGroup>
                  <label>Monto (Q)</label>
                  <Input
                    type="number"
                    placeholder="Monto"
                    value={chequeData.amount}
                    onChange={(e) =>
                      setChequeData({ ...chequeData, amount: e.target.value })
                    }
                  />
                </FormGroup>
                <Button color="success" onClick={handleCobrarCheque}>
                  Cobrar Cheque
                </Button>
              </CardBody>
            </Card>
          </Col>
        </Row>
        <Modal isOpen={isNewChequeraModalOpen} toggle={closeNewChequeraModal}>
          <NewChequera
            isOpen={isNewChequeraModalOpen}
            toggle={closeNewChequeraModal}
            idCuenta={chequeData.accountNumber}
            onCreate={fetchDeposits}
          />
        </Modal>
      </Container>
    </>
  );
};

export default Transacciones;
