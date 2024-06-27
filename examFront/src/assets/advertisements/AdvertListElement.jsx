import { useState } from "react";
import "./AdvertListElement.css";
import ModalBasic from "../elements/ModalBasic";
import AdvertDetailedInfo from "./AdvertDetailedInfo";

const AdvertListElement = ({ data }) => {
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
