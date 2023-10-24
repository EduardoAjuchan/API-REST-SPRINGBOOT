import Index from "views/Index.js";
import Profile from "views/examples/Profile.js";
import Banks from "views/examples/Banks.js";
import Register from "views/examples/Register.js";
import Login from "views/Login.js";
import Tables from "views/examples/Tables.js";
import Icons from "views/examples/Icons.js";
import Clients from "views/examples/Clients.js";
import TipoCuenta from "views/examples/TipoCuenta.js";

var routes = [
  {
    path: "/index",
    name: "Transacciones",
    icon: "ni ni-money-coins text-primary",
    component: <Index />,
    layout: "/admin",
    condition: () => !!localStorage.getItem("userInfo"), // Verifica si hay información de usuario en localStorage
  },
  {
    path: "/icons",
    name: "Cuentas",
    icon: "ni ni-single-copy-04 text-blue",
    component: <Icons />,
    layout: "/admin",
  },
  {
    path: "/banks",
    name: "Bancos",
    icon: "ni ni ni-building text-orange",
    component: <Banks />,
    layout: "/admin",
  },
  {
    path: "/user-profile",
    name: "Perfil de Usuario",
    icon: "ni ni-single-02 text-yellow",
    component: <Profile />,
    layout: "/admin",
  },
  {
    path: "/tables",
    name: "Tipo de Transferencias",
    icon: "ni ni-credit-card text-red",
    component: <Tables />,
    layout: "/admin",
  },
  {
    path: "/tipoCuenta",
    name: "Tipo de Cuentas",
    icon: "ni ni-archive-2 text-yellow",
    component: <TipoCuenta />,
    layout: "/admin",
  },

  {
    path: "/clients",
    name: "Clientes",
    icon: "ni ni-badge text-green",
    component: <Clients />,
    layout: "/admin",
  },
  {
    path: "/login",
    name: "Login",
    icon: "ni ni-key-25 text-info",
    component: <Login />,
    layout: "/auth",
    condition: () => !localStorage.getItem("userInfo"), // Verifica si no hay información de usuario en localStorage
  },
  // Ruta de registro (Register) solo accesible si el usuario no ha iniciado sesión
  {
    path: "/register",
    name: "Register",
    icon: "ni ni-circle-08 text-pink",
    component: <Register />,
    layout: "/auth",
    condition: () => !localStorage.getItem("userInfo"), // Verifica si no hay información de usuario en localStorage
  },
  // Otras rutas...
];
export default routes;
