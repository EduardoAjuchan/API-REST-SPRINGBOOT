import React, { useEffect, useState } from "react";
import {
  Card,
  CardHeader,
  CardBody,
  FormGroup,
  Form,
  Input,
  Container,
  Row,
  Col,
  Button,
} from "reactstrap";
import swal from "sweetalert";
import UserHeader from "components/Headers/UserHeader.js";
import TransferModal from "../../components/Modals/TransferModal";


const Profile = () => {
  const [userData, setUserData] = useState({
    username: "",
    email: "",
    firstName: "",
    lastName: "",
    password: "",
    phone: "",
  });

  const [updatedData, setUpdatedData] = useState({});
  const [showTransferModal, setShowTransferModal] = useState(false);
  

  useEffect(() => {
    const userInfo = JSON.parse(localStorage.getItem("userInfo"));
    if (userInfo) {
      setUserData({
        username: userInfo.username,
        email: userInfo.email,
        firstName: userInfo.name,
        lastName: userInfo.lastname,
        password: userInfo.password,
        phone: userInfo.phone,
      });

      // Inicializa updatedData con los valores actuales
      setUpdatedData({
        username: userInfo.username,
        email: userInfo.email,
        firstName: userInfo.name,
        lastName: userInfo.lastname,
        password: userInfo.password,
        phone: userInfo.phone,
        status: 1, // Siempre se envía como 1
      });
    } else {
      swal("Error", "No se encontró la información del usuario", "error");
    }
  }, []);

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setUpdatedData({ ...updatedData, [name]: value });
  };
  const handleUpdateProfile = async () => {
    if (Object.keys(updatedData).length === 0) {
      swal(
        "Advertencia",
        "Realiza al menos un cambio antes de actualizar",
        "warning"
      );
    } else if (updatedData.email === "") {
      swal(
        "Advertencia",
        "El campo de correo electrónico no puede estar vacío",
        "warning"
      );
    } else {
      const userInfo = JSON.parse(localStorage.getItem("userInfo"));
      const id = userInfo.id; // Obtener el ID del usuario del localStorage

      // Ensure that updatedData contains the correct keys and values
      const data = {
        name: updatedData.firstName,
        lastname: updatedData.lastName,
        username: updatedData.username,
        password: updatedData.password,
        email: updatedData.email, // Include the email field in the data object
        phone: updatedData.phone,
        status: 1, // Siempre se envía como 1
      };

      try {
        const response = await fetch(
          `http://localhost:8080/api/user/update/${id}`,
          {
            method: "PUT",
            headers: {
              "Content-Type": "application/json",
            },
            body: JSON.stringify(data),
          }
        );

        if (response.ok) {
          swal(
            "Actualización completa",
            "Los cambios se mostrarán la próxima vez que inicies sesión",
            "success"
          );
        } else {
          swal("Error", "Hubo un problema al actualizar el perfil", "error");
        }
      } catch (error) {
        console.error("Error al actualizar el perfil:", error);
        swal("Error", "Hubo un problema al actualizar el perfil", "error");
      }
    }
  };

  return (
    <>
      <UserHeader />

      <Container className="mt--7" fluid>
        <Row>
          <Col className="order-xl-2 mb-5 mb-xl-0" xl="4">
            <Card className="card-profile shadow">
              <Row className=" justify-content-center">
                <Col className="order-lg-2" lg="3">
                  <div className="card-profile-image">
                    <a href="#edo" onClick={(e) => e.preventDefault()}>
                      <img
                        alt="..."
                        className="rounded-circle"
                        src={require("../../assets/img/theme/gojo.png")}
                      />
                    </a>
                  </div>
                </Col>
              </Row>
              <CardHeader className="text-center border-0 pt-8 pt-md-4 pb-0 pb-md-4"></CardHeader>
              <CardHeader className="text-center border-0 pt-8 pt-md-4 pb-0 pb-md-4"></CardHeader>
              <CardBody className="pt-0 pt-md-4">
                <Row>
                  <div className="col">
                    <div className="card-profile-stats d-flex justify-content-center mt-md-4">
                      <div>
                        <Button
                          color="primary"
                          onClick={() => setShowTransferModal(true)}
                        >
                          Transferir
                        </Button>
                      </div>
                      
                    </div>
                  </div>
                </Row>
                <div className="text-center">
                  <h2>
                    {userData.username}{" "}
                    <span className="font-weight-light"></span>
                  </h2>
                  <div className="h5 font-weight-300">
                    <i className="ni location_pin mr-2" />
                    {userData.email}
                  </div>
                  <div className="h5 mt-4">
                    <i className="ni business_briefcase-24 mr-2" />
                    Usuario Estándar - Cliente
                  </div>
                  <div>
                    <i className="ni education_hat mr-2" />
                    Global Pay Services
                  </div>
                </div>
              </CardBody>
            </Card>
          </Col>
          <Col className="order-xl-1" xl="8">
            <Card className="bg-secondary shadow">
              <CardBody>
                <Form>
                  <h6 className="heading-small text-muted mb-4">
                    Información de usuario
                  </h6>
                  <div className="pl-lg-4">
                    <Row>
                      <Col lg="6">
                        <FormGroup>
                          <label
                            className="form-control-label"
                            htmlFor="input-username"
                          >
                            Nombre
                          </label>
                          <Input
                            className="form-control-alternative"
                            name="firstName"
                            value={updatedData.firstName}
                            onChange={handleInputChange}
                            id="input-username"
                            placeholder="Nombre"
                            type="text"
                          />
                        </FormGroup>
                      </Col>
                      <Col lg="6">
                        <FormGroup>
                          <label
                            className="form-control-label"
                            htmlFor="input-email"
                          >
                            Apellido
                          </label>
                          <Input
                            className="form-control-alternative"
                            name="lastName"
                            value={updatedData.lastName}
                            onChange={handleInputChange}
                            id="input-email"
                            placeholder="Apellido"
                            type="text"
                          />
                        </FormGroup>
                      </Col>
                    </Row>
                    <Row>
                      <Col lg="6">
                        <FormGroup>
                          <label
                            className="form-control-label"
                            htmlFor="input-first-name"
                          >
                            Nombre de usuario
                          </label>
                          <Input
                            className="form-control-alternative"
                            name="username"
                            value={updatedData.username}
                            onChange={handleInputChange}
                            id="input-first-name"
                            placeholder="Nombre de usuario"
                            type="text"
                          />
                        </FormGroup>
                      </Col>
                      <Col lg="6">
                        <FormGroup>
                          <label
                            className="form-control-label"
                            htmlFor="input-last-name"
                          >
                            Contraseña
                          </label>
                          <Input
                            className="form-control-alternative"
                            name="password"
                            value={updatedData.password}
                            onChange={handleInputChange}
                            id="input-last-name"
                            placeholder="Contraseña"
                            type="password"
                          />
                        </FormGroup>
                      </Col>
                    </Row>
                  </div>
                  <hr className="my-4" />
                  {/* Address */}
                  <h6 className="heading-small text-muted mb-4">
                    Información de contacto
                  </h6>
                  <div className="pl-lg-4">
                    <Row>
                      <Col md="6">
                        <FormGroup>
                          <label
                            className="form-control-label"
                            htmlFor="input-address"
                          >
                            Correo electrónico
                          </label>
                          <Input
                            className="form-control-alternative"
                            name="email"
                            value={updatedData.email}
                            onChange={handleInputChange}
                            id="input-address"
                            placeholder="Correo electrónico"
                            type="text"
                          />
                        </FormGroup>
                      </Col>
                      <Col md="6">
                        <FormGroup>
                          <label
                            className="form-control-label"
                            htmlFor="input-city"
                          >
                            Teléfono
                          </label>
                          <Input
                            className="form-control-alternative"
                            name="phone"
                            value={updatedData.phone}
                            onChange={handleInputChange}
                            id="input-city"
                            placeholder="Teléfono"
                            type="text"
                          />
                        </FormGroup>
                      </Col>
                    </Row>
                  </div>
                </Form>
                <div className="text-center">
                  <button
                    className="btn btn-primary"
                    onClick={handleUpdateProfile}
                  >
                    Editar perfil
                  </button>
                </div>
              </CardBody>
            </Card>
          </Col>
        </Row>
      </Container>
      
      <TransferModal
        isOpen={showTransferModal}
        toggle={() => setShowTransferModal(!showTransferModal)}
      />

     
     
    </>
  );
};

export default Profile;
