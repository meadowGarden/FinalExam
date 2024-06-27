import { Modal } from "react-bootstrap";
import "./ButtonBasic.css";

const ModalBasic = ({ children, isModalVisible, closeModal, title }) => {
  return (
    <Modal show={isModalVisible} onHide={closeModal}>
      {/* <Modal.Header closeButton>
        <Modal.Title>{title}</Modal.Title>
      </Modal.Header> */}

      <Modal.Body>
        {children}
      </Modal.Body>
    </Modal>
  );
};

export default ModalBasic;
