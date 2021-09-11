import React, { useRef, useState } from "react";
import {
  Card,
  FormGroup,
  Container,
  Form,
  Button,
  Alert,
  Spinner,
} from "react-bootstrap";
import RESTNotification from "../../RESTCalls/Notification/RESTNotification";
import { useHistory } from "react-router-dom";
import { UserContext } from "../../Auth/UserContext";
import RESTUser from "../../RESTCalls/User/RESTUser";

function VerifyOTP() {
  const [user, setUser] = UserContext();
  const history = useHistory();

  const [userObj, setuserObj] = useState();

  if (user) {
    history.push("/");
  }

  if (!JSON.parse(sessionStorage.getItem("otpID"))) {
    history.push("/login");
  }

  const [verifyotpDisable, setverifyotpDisable] = useState(false);
  const [verifyotpSpinnerDisable, setverifyotpSpinnerDisable] = useState(false);

  const [alertmsg, setalertmsg] = useState("");

  const otp = useRef();

  const [error, setError] = useState("");

  function verifyOTP(e) {
    e.preventDefault();

    setalertmsg("");
    setError("");
    setverifyotpDisable(true);
    setverifyotpSpinnerDisable(true);
    if (otp.current.value) {
      var obj = JSON.parse(sessionStorage.getItem("otpID"));

      var otpuid = obj.id;

      const verifyOTPayload = {
        uid: otpuid,
        otp: otp.current.value,
      };
      RESTNotification.post("/otp/verify", verifyOTPayload)
        .then((response) => {
          if (response.status === 200) {
            setverifyotpSpinnerDisable(false);
            setalertmsg("OTP Verified");
            sessionStorage.removeItem("otpID");
            setUser(JSON.stringify(response.data));
            history.push("/");
          } else if (response.status === 500) {
            setalertmsg("Something went wrong try after sometime");
            setverifyotpSpinnerDisable(false);
          } else if (response.status === 302) {
            setverifyotpSpinnerDisable(false);
            sessionStorage.removeItem("otpID");
            setuserObj(JSON.stringify(response.data));
            setalertmsg(
              "OTP verified but, Another user with same set of credentials already logged in, do you want to logged that out?"
            );
          }
        })
        .catch(function (error) {
          console.log("In error: " + error.response.data);
          if (error.response.status === 302) {
            setverifyotpSpinnerDisable(false);
            setuserObj(JSON.stringify(error.response.data));
            setalertmsg(
              "OTP verified but, Another user with same set of credentials already logged in, do you want to logged him out?"
            );
          } else {
            setError(error.response.data);
            setverifyotpDisable(false);
            setverifyotpSpinnerDisable(false);
          }
        });
    } else {
      setError("OTP must not be empty");
      setverifyotpDisable(false);
      setverifyotpSpinnerDisable(false);
    }
  }

  function invalidateSession() {
    var email = JSON.parse(userObj).email;
    RESTUser.get("/session/invalidate/" + email)
      .then((res) => {
        sessionStorage.removeItem("otpID");

        setUser(JSON.stringify(res.data));
        history.push("/");
      })
      .catch(function (error) {
        setError("Failed to login" + error.response.data);
      });
  }

  return (
    <div>
      <Container
        className="d-flex align-items-center justify-content-center"
        style={{ minHeight: "100vh" }}
      >
        <div className="w-100" style={{ maxWidth: "400px" }}>
          <Card style={{ padding: "10px" }}>
            <Card.Body>
              <h1 className="d-flex align-items-center justify-content-center">
                Verify OTP
              </h1>
              {error && <Alert variant="danger">{error}</Alert>}
              {alertmsg && <Alert variant="primary">{alertmsg}</Alert>}

              <div>
                {!userObj && (
                  <Form onSubmit={verifyOTP}>
                    <FormGroup className="mt-1" id="email">
                      <Form.Label>Email</Form.Label>
                      <Form.Control
                        type="username"
                        ref={otp}
                        id="form1"
                        required
                      />
                    </FormGroup>

                    <Button
                      disabled={verifyotpDisable}
                      onClick={verifyOTP}
                      className="mt-2"
                      variant="secondary"
                    >
                      {verifyotpSpinnerDisable && (
                        <Spinner
                          as="span"
                          animation="border"
                          size="sm"
                          role="status"
                          aria-hidden="true"
                        />
                      )}
                      VerifyOTP
                    </Button>
                  </Form>
                )}

                {userObj && (
                  <Button className="mt-2" onClick={invalidateSession}>
                    Click here to invalidate
                  </Button>
                )}
              </div>
            </Card.Body>
          </Card>
        </div>
      </Container>
    </div>
  );
}

export default VerifyOTP;
