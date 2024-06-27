import { useState } from "react";
import "../base/BaseStyle.css";
import CategoryDataCard from "./CategoryDataCard";
import CategoriesListElement from "./CategoriesListElement";

const CategoriesPage = () => {
  const [categories, setCategories] = useState([]);

  const categoriesToDisplay = categories.map((ad) => {
    return <CategoriesListElement key={ad.id} data={ad} />;
  });

  return (
    <>
      <div className="pageContainer">
        <div>
          <CategoryDataCard
            categories={categories}
            setCategories={setCategories}
          />
        </div>
        <div>{categoriesToDisplay}</div>
      </div>
    </>
  );
};

export default CategoriesPage;
