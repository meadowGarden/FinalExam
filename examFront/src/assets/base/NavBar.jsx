import axios from "axios";
import { Container, Nav, Navbar } from "react-bootstrap";
import { useNavigate } from "react-router-dom";
import useUserStore from "../store/userStore";

const NavBar = () => {
  const navigate = useNavigate();
  const clearUser = useUserStore((state) => state.clearUser);
  const { user } = useUserStore((state) => state);

  const logOut = () => {
    const accountData = {
      token: localStorage.getItem("authToken"),
    };

    axios
      .post("http://localhost:8080/api/auth/logout", accountData)
      .then((response) => {
        console.log(response);
        localStorage.removeItem("authToken");
        clearUser();
        navigate("/");
      })
      .catch((error) => console.log(error));
  };

  return (
    <Navbar bg="dark" data-bs-theme="dark">
      <Container>
        {user.userRole === "" && <Navbar.Brand href="/">home</Navbar.Brand>}

        <Nav className="me-auto">
          {user.userRole !== "" && (
            <Nav.Link href="advertisements">advertisements</Nav.Link>
          )}

          {user.userRole === "ADMINISTRATOR" && (
            <Nav.Link href="categories">categories</Nav.Link>
          )}

          {user.userRole !== "" && <Nav.Link onClick={logOut}>logout</Nav.Link>}
        </Nav>
      </Container>
    </Navbar>
  );
};

export default NavBar;
