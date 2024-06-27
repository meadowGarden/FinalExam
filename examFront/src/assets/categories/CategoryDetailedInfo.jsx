const CategoryDetailedInfo = ({ data }) => {
  const { categoryName } = data;

  return (
    <>
      <div>{categoryName}</div>
    </>
  );
};

export default CategoryDetailedInfo;
