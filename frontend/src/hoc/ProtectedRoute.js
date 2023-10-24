
import React from "react";
import { Navigate, Route } from "react-router-dom";
import Login from "views/Login";

const ProtectedRoute = ({
 isUserLoggedIn,
 redirectTo,
 component: Component,
  ...route
}) => {
   return isUserLoggedIn ? (
  <Route {...route} element={<Login />} />
  ) : (
  <Navigate to={redirectTo} replace />
 );
};

export default ProtectedRoute;
