import { useState } from "react";
import ButtonBasic from "../elements/ButtonBasic";
import ModalBasic from "../elements/ModalBasic";
import CreateAccountCard from "../register_login/CreateAccountCard";
import LogInAccountCard from "../register_login/LogInAccountCard";
import "./MainPage.css";

const MainPage = () => {
  const [isResgisterModalVisible, setIsRegisterModalVisible] = useState(false);
  const [isLogInModalVisible, setIsLogInModalVisible] = useState(false);

  const showRegisterModal = () => {
    setIsRegisterModalVisible(!isResgisterModalVisible);
  };
  const closeRegisterModal = () => {
    setIsRegisterModalVisible(false);
  };

  const showLogInModal = () => {
    setIsLogInModalVisible(!isLogInModalVisible);
  };
  const closeLogInModal = () => {
    setIsLogInModalVisible(false);
  };

  return (
    <>
      <div className="mainPage">
        <div className="mainAuthContainer">
          {" "}
          <ButtonBasic clickHandle={showRegisterModal} text="register" />
          <ModalBasic
            isModalVisible={isResgisterModalVisible}
            setIsModalVisible={setIsRegisterModalVisible}
            closeModal={closeRegisterModal}
            title="create account"
          >
            <CreateAccountCard />
          </ModalBasic>
        </div>

        <div className="mainAuthContainer">
          {" "}
          <ButtonBasic clickHandle={showLogInModal} text="login" />
          <ModalBasic
            isModalVisible={isLogInModalVisible}
            setIsModalVisible={setIsLogInModalVisible}
            closeModal={closeLogInModal}
            title="login"
          >
            <LogInAccountCard />
          </ModalBasic>
        </div>
      </div>
    </>
  );
};

export default MainPage;
