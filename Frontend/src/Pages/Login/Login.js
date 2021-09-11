import React, { useRef, useState } from "react";
import {
  Card,
  Container,
  Form,
  FormGroup,
  Button,
  Alert,
} from "react-bootstrap";
import { Link, useHistory } from "react-router-dom";
import RESTUser from "../../RESTCalls/User/RESTUser";
import { UserContext } from "../../Auth/UserContext";
function Login() {
  const history = useHistory();

  const [user] = UserContext();

  if (user) {
    history.push("/");
  }

  const emailRef = useRef();
  const passwordRef = useRef();

  const [error, setError] = useState("");
  const [loading, setLoading] = useState(false);

  function handleSubmit(e) {
    e.preventDefault();
    setError("");
    setLoading(true);
    const email = emailRef.current.value;
    const password = passwordRef.current.value;
    const userLogin = {
      email: email,
      password: password,
    };
    RESTUser.post("/login", userLogin)
      .then((res) => {
        sessionStorage.setItem("otpID", JSON.stringify(res.data));
        history.push("/otp");
      })
      .catch(function (error) {
        setError("Failed to login" + error.response.data);
        setLoading(false);
      });
  }

  return (
    <Container
      className="d-flex align-items-center justify-content-center"
      style={{ minHeight: "100vh" }}
    >
      <div className="w-100" style={{ maxWidth: "400px" }}>
        <Card style={{ padding: "10px" }}>
          <Card.Body>
            <h2 className="text-center">Login</h2>

            {error && <Alert variant="danger">{error}</Alert>}
            <Form onSubmit={handleSubmit}>
              <FormGroup className="mt-1" id="email">
                <Form.Label>Email</Form.Label>
                <Form.Control type="mail" ref={emailRef} required />
              </FormGroup>

              <FormGroup className="mt-1" id="password">
                <Form.Label>Password</Form.Label>
                <Form.Control type="password" ref={passwordRef} required />
              </FormGroup>
              <Button disabled={loading} type="submit" className="w-100 mt-3">
                Login
              </Button>
            </Form>
          </Card.Body>
        </Card>
        <div className="w-100 text-center mt-1">
          Don't have an account? <Link to="register">Register</Link>
        </div>
      </div>
    </Container>
  );
}

export default Login;
