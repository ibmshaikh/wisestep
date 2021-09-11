import React from "react";
import { Redirect, Route } from "react-router-dom";
import { UserContext } from "./UserContext";

export function PrivateRoute(props) {
  const [user] = UserContext();

  if (!user) {
    return <Redirect to="/login" />;
  }
  return <Route {...props} />;
}
