import React, { Component } from "react";
import {
    Badge,
    DropdownMenu,
    DropdownItem,
    UncontrolledDropdown,
    DropdownToggle,
    Media,
    Progress,
    Table,
    UncontrolledTooltip
  } from "reactstrap";
  

class Tables extends Component {
  constructor() {
    super();
    this.state = {
      data: [],
    };
  }

  componentDidMount() {
      // Realiza una solicitud a la API
      fetch("http://localhost:8092/api/vi/tiposTransferencia")
        .then((response) => response.json())
        .then((data) => {
          // Almacena los datos en el estado del componente
          this.setState({ data: data.object });
        })
        .catch((error) => console.error("Error al obtener los datos de la API", error));
  }

  render() {
    return (
      <>
        <Table className="align-items-center" responsive>
          {/* ... Resto del código de la tabla */}
          <tbody>
            {this.state.data.map((item) => (
              <tr key={item.idTipoTransferencia}>
                <th scope="row">
                  {/* Puedes mostrar los datos de la API aquí */}
                  {item.nombreTransferencia}
                </th>
                <td>{item.nombreTransferencia}</td>
                <td>
                  <Badge color="" className="badge-dot mr-4">
                    <i className="bg-warning" />
                    pending
                  </Badge>
                </td>
                {/* ... Resto de las columnas */}
              </tr>
            ))}
          </tbody>
        </Table>
      </>
    );
  }
}

export default Tables;
