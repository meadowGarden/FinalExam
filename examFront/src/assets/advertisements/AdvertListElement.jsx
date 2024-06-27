import { useState } from "react";
import "./AdvertListElement.css";
import ModalBasic from "../elements/ModalBasic";
import AdvertDetailedInfo from "./AdvertDetailedInfo";
import useUserStore from "../store/userStore";

const AdvertListElement = ({ data }) => {
  const token =
    useUserStore((state) => state.token) || localStorage.getItem("authToken");
  const [adInfoModalVisibility, setAdInfoModalVisibility] = useState(false);

  const showModal = () => {
    setAdInfoModalVisibility(true);
  };

  const closeModal = () => {
    setAdInfoModalVisibility(false);
  };

  const { adName } = data;

  return (
    <>
      <div onClick={showModal} className="advertListElement">
        <div>{adName}</div>
      </div>
      <div>
        <ModalBasic
          isModalVisible={adInfoModalVisibility}
          closeModal={closeModal}
          title={adName}
        >
          <AdvertDetailedInfo data={data} />
        </ModalBasic>
      </div>
    </>
  );
};

export default AdvertListElement;
