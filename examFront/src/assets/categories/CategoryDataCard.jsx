import axios from "axios";
import { useEffect, useState } from "react";
import { useForm } from "react-hook-form";
import "./CategoryDataCard.css";
import useUserStore from "../store/userStore";

const defaultPaginationSettings = {
  pageNumber: 1,
  pageSize: 10,
  nameContains: "",
  sortBy: "categoryName",
  sortAsc: true,
};

const CategoryDataCard = ({ categories, setCategories }) => {
  const [paginationSettings, setPaginationSettings] = useState(
    defaultPaginationSettings
  );

  const {
    register,
    watch,
    formState: { errors },
  } = useForm();

  const token =
    useUserStore((state) => state.token) || localStorage.getItem("authToken");
  const [totalPages, setTotalPages] = useState(0);
  const [currentPage, setCurrentPage] = useState(1);

  const pageNumber = watch("pageNumber");
  const pageSize = watch("pageSize");
  const contains = watch("contains");
  const sortAsc = watch("sortAsc");

  useEffect(() => {
    if (token) {
      axios
        .get("http://localhost:8080/api/categories", {
          params: paginationSettings,
          headers: {
            Authorization: `Bearer ${token}`,
          },
        })
        .then((response) => {
          setCategories(response.data.content);
          setTotalPages(response.data.totalPages);
        })
        .catch((error) => console.log(error));
    }
  }, [paginationSettings, setCategories, token]);

  console.log(categories);

  useEffect(
    (prevSettings) => {
      setPaginationSettings({
        ...prevSettings,
        pageNumber: pageNumber,
        pageSize: pageSize,
        contains: contains,
        sortAsc: sortAsc,
      });
    },
    [pageNumber, pageSize, contains, sortAsc]
  );

  const handlePageSelect = (event) => {
    const selectedPage = parseInt(event.target.value, 10);
    setCurrentPage(selectedPage);
    setPaginationSettings({ ...paginationSettings, pageNumber: selectedPage });
  };

  const pageNumberOptions = Array.from(
    { length: totalPages },
    (_, i) => i + 1
  ).map((page) => (
    <option key={page} value={page}>
      {page}
    </option>
  ));

  return (
    <>
      <form className="categoryDataCard">
        <section className="paginationElement">
          <label>category name</label>
          <input {...register("contains")} placeholder="search a category" />
        </section>

        <section className="paginationElement">
          <label>page number</label>
          <select
            {...register("pageNumber")}
            value={currentPage}
            onChange={handlePageSelect}
          >
            {pageNumberOptions}
          </select>
        </section>

        <section className="paginationElement">
          <label>lines to show</label>
          <select {...register("pageSize")} placeholder="page number">
            <option value={5}>5</option>
            <option value={10}>10</option>
            <option value={15}>15</option>
          </select>
        </section>

        <section className="paginationElement">
          <label>sorting</label>
          <select {...register("sortAsc")}>
            <option value={true}>ascenting</option>
            <option value={false}>descending</option>
          </select>
        </section>
      </form>
    </>
  );
};

export default CategoryDataCard;
