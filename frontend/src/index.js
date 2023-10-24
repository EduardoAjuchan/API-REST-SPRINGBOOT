import React from "react";
import ReactDOM from "react-dom";
import { BrowserRouter, Route, Routes, Navigate } from "react-router-dom";
 // Importa el componente ProtectedRoute
import ProtectedRoute from "hoc/ProtectedRoute";  
import "assets/plugins/nucleo/css/nucleo.css";
import "@fortawesome/fontawesome-free/css/all.min.css";
import "assets/scss/argon-dashboard-react.scss";

import AdminLayout from "layouts/Admin.js";
import AuthLayout from "layouts/Auth.js";
import routes from "./routes";

const filteredRoutes = routes.filter(
  (route) => route.path !== "/login" && route.path !== "/register"
);

const root = ReactDOM.createRoot(document.getElementById("root"));

document.title = "Global Pay";
root.render(
  <BrowserRouter>
    <Routes>
      <Route path="/admin/*" element={<AdminLayout />} />
      <Route path="/auth/*" element={<AuthLayout />} />
      {filteredRoutes.map((route, index) => (
        <Route
          key={index}
          path={route.path}
          element={
            // Utiliza el componente ProtectedRoute para proteger las rutas internas
            <ProtectedRoute
              isUserLoggedIn={!!localStorage.getItem("userInfo")}
              redirectTo="/auth/login"
              {...route}
            />
          }
        />
      ))}
      <Route path="*" element={<Navigate to="/auth/Login" replace />} />
    </Routes>
  </BrowserRouter>
);
