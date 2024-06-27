import axios from "axios";
import { useEffect, useState } from "react";
import { useForm } from "react-hook-form";
import "./AdvertDataCard.css";
import useUserStore from "../store/userStore";

const defaultPaginationSettings = {
  pageNumber: 1,
  pageSize: 10,
  nameContains: "",
  sortBy: "adName",
  sortAsc: true,
};

const AdvertDataCard = ({ setAds }) => {
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
  const [categories, setCategories] = useState([]);
  const [totalPages, setTotalPages] = useState(0);
  const [currentPage, setCurrentPage] = useState(1);

  const pageNumber = watch("pageNumber");
  const pageSize = watch("pageSize");
  const nameContains = watch("nameContains");
  const categoryContains = watch("categoryContains");
  const sortAsc = watch("sortAsc");

  useEffect(() => {
    if (token) {
      axios
        .get("http://localhost:8080/api/advertisements", {
          params: paginationSettings,
          headers: {
            Authorization: `Bearer ${token}`,
          },
        })
        .then((response) => {
          setAds(response.data.content);
          setTotalPages(response.data.totalPages);
        })
        .catch((error) => console.log(error));
    }
  }, [paginationSettings, setAds, token]);

  useEffect(() => {
    if (token) {
      axios
        .get("http://localhost:8080/api/categories", {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        })
        .then((response) => {
          setCategories(response.data.content);
        })
        .catch((error) => console.log(error));
    }
  }, [setCategories, token]);

  useEffect(
    (prevSettings) => {
      setPaginationSettings({
        ...prevSettings,
        pageNumber: pageNumber,
        pageSize: pageSize,
        nameContains: nameContains,
        categoryContains: categoryContains,
        sortAsc: sortAsc,
      });
    },
    [pageNumber, pageSize, nameContains, categoryContains, sortAsc]
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

  const categoriesToDisplay = categories.map((category) => {
    return (
      <option key={category.id} value={category.categoryName}>
        {category.categoryName}
      </option>
    );
  });

  return (
    <>
      <form className="advertDataCard">
        <section className="paginationElement">
          <label>advertisements title</label>
          <input
            {...register("nameContains")}
            placeholder="search a advertisement"
          />
        </section>

        <section className="paginationElement">
          <label>category</label>
          <select {...register("categoryContains")}>
            <option value=""></option>
            {categoriesToDisplay}
          </select>
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
          <label>number of ads</label>
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

export default AdvertDataCard;
