import React, { useState, useEffect } from "react";
import {
  Button,
  Card,
  CardHeader,
  CardBody,
  FormGroup,
  Form,
  Input,
  InputGroupAddon,
  InputGroupText,
  InputGroup,
  Col,
} from "reactstrap";
import swal from "sweetalert";
import { useNavigate } from "react-router-dom";
import BlackModal from "../components/Modals/BlankModal";

const Login = () => {
  const [formData, setFormData] = useState({
    username: "",
    password: "",
  });

  const navigate = useNavigate();
  const [showClientModal, setShowClientModal] = useState(false);
  const [banks, setBanks] = useState([]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };

  const handleLoginSuccess = (redirect = true) => {
    if (redirect) {
      navigate("/admin/user-profile", { replace: true });
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

  useEffect(() => {
    const handlePopState = () => {
      const userData = JSON.parse(localStorage.getItem("userInfo"));
      if (userData) {
        navigate("/admin/user-profile", { replace: true });
      }
    };

    window.addEventListener("popstate", handlePopState);

    fetchBanks();

    return () => {
      window.removeEventListener("popstate", handlePopState);
    };
  }, [navigate]);

  const handleLogin = async () => {
    try {
      const response = await fetch("http://localhost:8080/api/user/login", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(formData),
      });

      if (response.status === 200) {
        const data = await response.text();

        if (data.includes("Login Success")) {
          const userData = parseUserData(data);

          if (userData) {
            // Guarda la información de userData en el localStorage
            localStorage.setItem("userInfo", JSON.stringify(userData));

            const clientIdResponse = await fetch(
              `http://localhost:8092/api/user/idClienteCuenta?id_login_user=${userData.id}`
            );
            const clientIdData = await clientIdResponse.text();

            // Guarda la respuesta de la segunda API en el localStorage
            localStorage.setItem("clientIdData", JSON.stringify(clientIdData));

            if (clientIdData !== "0") {
              handleLoginSuccess();
            } else {
              setShowClientModal(true);
            }
          } else {
            swal("Error", "Credenciales incorrectas", "error");
          }
        } else {
          swal("Error", "Credenciales incorrectas", "error");
        }
      } else {
        swal("Error", "Credenciales incorrectas", "error");
      }
    } catch (error) {
      console.error("Error al iniciar sesión:", error);
      swal("Error", "Ocurrió un error al iniciar sesión", "error");
    }
};

  const parseUserData = (data) => {
    const parts = data.split("+");
    if (parts.length === 2) {
      const userDataString = parts[1].replace(/,/g, ",");
      const userDataArray = userDataString.split(",");

      const userData = {};
      for (const entry of userDataArray) {
        const [key, value] = entry.split(":");
        userData[key] = value;
      }

      return userData;
    }
    return null;
  };

  return (
    <Col lg="5" md="7">
      <Card className="bg-secondary shadow border-0">
        <CardHeader className="bg-transparent pb-4">
          <div className="text-muted text-center mt-2 mb-0">
            <h2>Iniciar Sesión</h2>
          </div>
        </CardHeader>
        <CardBody className="px-lg-5 py-lg-5">
          <div className="text-center text-muted mb-3">
            <small>Ingresa tus credenciales</small>
          </div>
          <Form role="form">
            <FormGroup className="mb-3">
              <InputGroup className="input-group-alternative">
                <InputGroupAddon addonType="prepend">
                  <InputGroupText>
                    <i className="ni ni-circle-08" />
                  </InputGroupText>
                </InputGroupAddon>
                <Input
                  placeholder="Nombre de Usuario"
                  type="text"
                  name="username"
                  value={formData.username}
                  onChange={handleChange}
                />
              </InputGroup>
            </FormGroup>
            <FormGroup>
              <InputGroup className="input-group-alternative">
                <InputGroupAddon addonType="prepend">
                  <InputGroupText>
                    <i className="ni ni-lock-circle-open" />
                  </InputGroupText>
                </InputGroupAddon>
                <Input
                  placeholder="Contraseña"
                  type="password"
                  name="password"
                  value={formData.password}
                  onChange={handleChange}
                />
              </InputGroup>
            </FormGroup>

            <div className="text-center">
              <Button
                className="my-4"
                color="primary"
                type="button"
                onClick={handleLogin}
              >
                Iniciar Sesión
              </Button>
            </div>
          </Form>
        </CardBody>
      </Card>
      {showClientModal && (
        <BlackModal banks={banks} onClose={handleLoginSuccess} />
      )}
    </Col>
  );
};

export default Login;
