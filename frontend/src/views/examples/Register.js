import React, { useState } from "react";
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
  Tooltip,
} from "reactstrap";
import swal from "sweetalert";

const Register = () => {
  const [formData, setFormData] = useState({
    name: "",
    lastname: "",
    username: "",
    email: "",
    password: "",
    phone: "",
  });

  const [tooltips, setTooltips] = useState({
    name: false,
    email: false,
    lastname: false,
  });

  const handleChange = (e) => {
    const { name, value } = e.target;

    // Validaciones y actualización de tooltips
    const newTooltips = { ...tooltips };

    if (name === "name" || name === "lastname" || name === "username") {
      if (!/^[A-Za-z]+$/.test(value)) {
        newTooltips[name] = true;
      } else {
        newTooltips[name] = false;
      }
    } else if (name === "email") {
      if (!/^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,4}$/.test(value)) {
        newTooltips[name] = true;
      } else {
        newTooltips[name] = false;
      }
    } else if (name === "phone") {
      if (!/^\d+$/.test(value)) {
        newTooltips[name] = true;
      } else {
        newTooltips[name] = false;
      }
    }

    setFormData({ ...formData, [name]: value });
    setTooltips(newTooltips);
  };

  const handleSubmit = async () => {
    // Campos requeridos
    const requiredFields = {
      name: "",
      lastname: "",
      username: "",
      email: "",
      password: "",
      phone: "",
    };


    for (const field in requiredFields) {
      if (formData[field] === "") {
        swal("Error", "Por favor, complete todos los campos.", "error");
        return;
      }
    }

    try {
      const response = await fetch("http://localhost:8080/api/user/save", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({ ...formData, status: 1 }),
      });

      if (response.status === 200) {
        swal("Éxito", "Registro exitoso", "success").then(() => {
          
          window.location.href = "/views/Login.js";
        });
      } else {
        swal(
          "Error",
          "Hubo un problema al registrar. Por favor, inténtelo de nuevo.",
          "error"
        );
      }
    } catch (error) {
      console.error("Error al enviar los datos:", error);
      swal(
        "Error",
        "Hubo un problema al registrar. Por favor, inténtelo de nuevo.",
        "error"
      );
    }
  };

  return (
    <>
      <Col lg="6" md="8">
        <Card className="bg-secondary shadow border-0">
          <CardHeader className="bg-transparent pb-5">
            <div className="text-muted text-center mt-3 mb-0">
              <h2>Crea tu cuenta</h2>
            </div>
          </CardHeader>
          <CardBody className="px-lg-5 py-lg-2">
            <Form role="form">
              <FormGroup>
                <InputGroup className="input-group-alternative mb-1">
                  <InputGroupAddon addonType="prepend">
                    <InputGroupText>
                      <i className="ni ni-hat-3" />
                    </InputGroupText>
                  </InputGroupAddon>
                  <Input
                    placeholder="Nombre"
                    type="text"
                    name="name"
                    value={formData.name}
                    onChange={handleChange}
                    id="name"
                  />
                  <Tooltip
                    isOpen={tooltips.name}
                    target="name"
                    placement="right"
                  >
                    Solo se permiten letras.
                  </Tooltip>
                </InputGroup>
              </FormGroup>
              <FormGroup>
                <InputGroup className="input-group-alternative mb-3">
                  <InputGroupAddon addonType="prepend">
                    <InputGroupText>
                      <i className="ni ni-circle-08" />
                    </InputGroupText>
                  </InputGroupAddon>
                  <Input
                    placeholder="Apellido"
                    type="text"
                    name="lastname"
                    value={formData.lastname}
                    onChange={handleChange}
                    id="lastname"
                  />
                  <Tooltip
                    isOpen={tooltips.lastname}
                    target="lastname"
                    placement="right"
                  >
                    Solo se permiten letras.
                  </Tooltip>
                </InputGroup>
              </FormGroup>
              <FormGroup>
                <InputGroup className="input-group-alternative mb-3">
                  <InputGroupAddon addonType="prepend">
                    <InputGroupText>
                      <i className="ni ni-single-02" />
                    </InputGroupText>
                  </InputGroupAddon>
                  <Input
                    placeholder="Nombre de usuario"
                    type="text"
                    name="username"
                    value={formData.username}
                    onChange={handleChange}
                  />
                </InputGroup>
              </FormGroup>
              <FormGroup>
                <InputGroup className="input-group-alternative mb-3">
                  <InputGroupAddon addonType="prepend">
                    <InputGroupText>
                      <i className="ni ni-email-83" />
                    </InputGroupText>
                  </InputGroupAddon>
                  <Input
                    id="email"
                    placeholder="Email"
                    type="email"
                    name="email"
                    value={formData.email}
                    onChange={handleChange}
                  />

                  <Tooltip
                    isOpen={tooltips.email}
                    target="email"
                    placement="right"
                  >
                    Ingrese una dirección de correo electrónico válida.
                  </Tooltip>
                </InputGroup>
              </FormGroup>
              <FormGroup>
                <FormGroup>
                  <InputGroup className="input-group-alternative mb-3">
                    <InputGroupAddon addonType="prepend">
                      <InputGroupText>
                        <i className="ni ni-key-25" />
                      </InputGroupText>
                    </InputGroupAddon>
                    <Input
                      id="password"
                      placeholder="Contraseña"
                      type="password" 
                      name="password"
                      value={formData.password}
                      onChange={handleChange}
                    />
                  </InputGroup>
                </FormGroup>
                <InputGroup className="input-group-alternative mb-3">
                  <InputGroupAddon addonType="prepend">
                    <InputGroupText>
                      <i className="ni ni-mobile-button" />
                    </InputGroupText>
                  </InputGroupAddon>
                  <Input
                    placeholder="Teléfono"
                    type="text"
                    name="phone"
                    value={formData.phone}
                    onChange={handleChange}
                  />
                </InputGroup>
              </FormGroup>
              <div className="text-center">
                <Button
                  className="mt-4 mb-4"
                  color="primary"
                  type="button"
                  onClick={handleSubmit}
                >
                  Crear Cuenta
                </Button>
              </div>
            </Form>
          </CardBody>
        </Card>
      </Col>
    </>
  );
};

export default Register;
