import "./App.css";
import Login from "./Pages/Login/Login";
import Register from "./Pages/Register/Register";
import { BrowserRouter as Router, Switch, Route } from "react-router-dom";
import VerifyOTP from "./Pages/VerifyOTP/VerifyOTP";
import Details from "./Pages/Details/Details";
import { PrivateRoute } from "./Auth/PrivateRoute";
import React from "react";

function App() {
  return (
    <Router>
      <div className="App">
        <Switch>
          <PrivateRoute path="/" exact>
            <Details />
          </PrivateRoute>
          <Route path="/login" exact component={Login} />
          <Route path="/register" exact component={Register} />
          <Route path="/otp" exact component={VerifyOTP} />
        </Switch>
      </div>
    </Router>
  );
}
export default App;
