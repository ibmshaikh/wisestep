import React, { useEffect, useState } from "react";
import { Alert, Button, Card, Container } from "react-bootstrap";
import { UserContext } from "../../Auth/UserContext";
import { useHistory } from "react-router-dom";
import RESTUser from "../../RESTCalls/User/RESTUser";

function Details() {
  const history = useHistory();

  const [user] = UserContext();

  const [error, setError] = useState("");
  const userObj = JSON.parse(user);

  function logoutByUser() {
    RESTUser.get("/logout/" + userObj.email).then((res) => {
      localStorage.removeItem("user");
      history.push("/login");
    });
  }

  function logoutBysys() {
    localStorage.removeItem("user");
    history.push("/login");
  }

  useEffect(() => {
    //check weather this session still valid or not

    RESTUser.get("/session/" + userObj.session)
      .then((res) => {
        if (res.status === 200) {
          console.log("session present");
        }
      })
      .catch(function (error) {
        setError(
          "A new session has been started you will now redirected to login page ..."
        );
        console.log("session not present");
        setTimeout(() => {
          logoutBysys();
        }, 3000);
      });
  });

  return (
    <div>
      <Container
        className="d-flex align-items-center justify-content-center"
        style={{ minHeight: "50vh" }}
      >
        <Card
          className="d-flex align-items-center justify-content-center"
          style={{ padding: "100px" }}
        >
          <div>Thank you for logging in</div>
          <div>Currenlty logged in as: {userObj.email}</div>
          {error && <Alert variant="danger">{error}</Alert>}
          <div className="mt-2">
            <Button onClick={logoutByUser} class="btn btn-dark">
              Click here to logged out
            </Button>
          </div>
        </Card>
      </Container>
    </div>
  );
}

export default Details;
