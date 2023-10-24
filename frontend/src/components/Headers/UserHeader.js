import React from "react";
import { Container, Row, Col } from "reactstrap";

const UserHeader = () => {
  const userInfo = JSON.parse(localStorage.getItem("userInfo"));
  const username = userInfo ? userInfo.username : "";

  return (
    <>
      <div
        className="header pb-8 pt-5 pt-lg-8 d-flex align-items-center"
        style={{
          minHeight: "500px",
          backgroundImage:
            "url(" + require("../../assets/img/theme/portada.jpg") + ")",
          backgroundSize: "cover",
          backgroundPosition: "center top",
        }}
      >
        {/* Mask */}
        <span className="mask bg-gradient-default opacity-8" />
        {/* Header container */}
        <Container className="d-flex align-items-center" fluid>
          <Row>
            <Col lg="7" md="10">
              <h1 className="display-2 text-white">Hola {username}</h1>
              <p className="text-white mt-0 mb-5">
                Aquí podrás ver y editar tu información personal. También podrás ver tus cuentas y realizar transferencias.
              </p>
              
            </Col>
          </Row>
        </Container>
      </div>
    </>
  );
};

export default UserHeader;
