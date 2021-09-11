import React, { useRef, useState } from "react";
import { Card, Form, Button, Container, Alert } from "react-bootstrap";
import { Link, useHistory } from "react-router-dom";
import RESTUser from "../../RESTCalls/User/RESTUser";
import { UserContext } from "../../Auth/UserContext";

function Register() {
  const history = useHistory();

  const [user] = UserContext();

  if (user) {
    history.push("/");
  }
  const firstnameRef = useRef();
  const lastnameRef = useRef();
  const emailRef = useRef();
  const passwordRef = useRef();
  const confirmPasswordRef = useRef();

  const [error, setError] = useState("");
  const [loading, setLoading] = useState(false);

  async function handleSubmit(e) {
    e.preventDefault();
    setLoading(true);

    const email = emailRef.current.value;
    const password = passwordRef.current.value;
    const first_name = firstnameRef.current.value;
    const last_name = lastnameRef.current.value;

    const userRegPayload = {
      first_name: first_name,
      last_name: last_name,
      email: email,
      password: password,
    };

    RESTUser.post("/register", userRegPayload)
      .then(async (res) => {
        if (res.status === 200) {
          try {
            setError("");
            console.log(res.data);
            sessionStorage.setItem("otpID", JSON.stringify(res.data));
            history.push("/otp");
          } catch (e) {
            setError("Failed to create an account " + e);
            setLoading(false);
          }
        }
      })
      .catch(function (err) {
        if (err.response.data.message) {
          setError("Something went wrong, " + err.response.data.message);
          setLoading(false);
        } else {
          setError("Something went wrong, " + err.response.data);
          setLoading(false);
        }
      });
  }

  return (
    <Container
      className="d-flex align-items-center justify-content-center"
      style={{ minHeight: "100vh" }}
    >
      <div className="w-100" style={{ maxWidth: "400px" }}>
        <Card style={{ padding: "20px" }}>
          <Card.Body>
            <h2 className="text-center mb4">SignUp</h2>
            {error && <Alert variant="danger">{error}</Alert>}
            <Form onSubmit={handleSubmit}>
              <Form.Group id="firstname">
                <Form.Label>Firstname</Form.Label>
                <Form.Control type="name" ref={firstnameRef} required />
              </Form.Group>

              <Form.Group className="mt-1" id="lastname">
                <Form.Label>Lastname</Form.Label>
                <Form.Control type="name" ref={lastnameRef} required />
              </Form.Group>

              <Form.Group className="mt-1" id="email">
                <Form.Label>Email</Form.Label>
                <Form.Control type="email" ref={emailRef} required />
              </Form.Group>

              <Form.Group className="mt-1" id="password">
                <Form.Label>Password</Form.Label>
                <Form.Control type="password" ref={passwordRef} required />
              </Form.Group>

              <Form.Group className="mt-1" id="confirmPassword">
                <Form.Label>Confirm Password</Form.Label>
                <Form.Control
                  type="password"
                  ref={confirmPasswordRef}
                  required
                />
              </Form.Group>
              <Button disabled={loading} className="w-100 mt-3" type="submit">
                Sign Up
              </Button>
            </Form>
          </Card.Body>
        </Card>
        <div className="w-100 text-center mt-2">
          Already have an account? <Link to="login">Login</Link>
        </div>
      </div>
    </Container>
  );
}

export default Register;
