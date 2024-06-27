import { useState } from "react";
import "./CategoriesListElement.css";
import ModalBasic from "../elements/ModalBasic";
import CategoryDetailedInfo from "./CategoryDetailedInfo";

const CategoriesListElement = ({ data }) => {
  const [categoryInfoModalVisibility, setCategoryInfoModalVisibility] =
    useState(false);

  const showModal = () => {
    setCategoryInfoModalVisibility(true);
  };

  const closeModal = () => {
    setCategoryInfoModalVisibility(false);
  };

  const { categoryName } = data;

  return (
    <>
      <div onClick={showModal} className="categoriesListElement">
        <div>{categoryName}</div>
      </div>
      <div>
        <ModalBasic
          isModalVisible={categoryInfoModalVisibility}
          closeModal={closeModal}
          title={categoryName}
        >
          <CategoryDetailedInfo data={data} />
        </ModalBasic>
      </div>
    </>
  );
};

export default CategoriesListElement;
