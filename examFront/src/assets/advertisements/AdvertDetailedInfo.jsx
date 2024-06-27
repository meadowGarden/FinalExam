import axios from "axios";
import { useState } from "react";
import useUserStore from "../store/userStore";
import { useForm } from "react-hook-form";
import ButtonBasic from "../elements/ButtonBasic";
import "./AdvertDetailedInfo.css";

const AdvertDetailedInfo = ({ data }) => {
  const { user } = useUserStore((state) => state);
  const { adDescription, price, city } = data;
  const [adData, setAdData] = useState(data);

  const token =
    useUserStore((state) => state.token) || localStorage.getItem("authToken");

  const {
    register,
    reset,
    handleSubmit,
    formState: { errors },
  } = useForm();

  const onSubmit = (formData) => {
    if (!window.confirm("do you really want to update current advertisement?"))
      return;
    const updateAd = {
      adName: formData.adName,
      adDescription: formData.adDescription,
      price: formData.price,
      city: formData.city,
    };

    axios
      .put(`http://localhost:8080/api/advertisements/${data.id}`, updateAd, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      })
      .then((response) => {
        setAdData(response.data);
        reset(updateAd);
      })
      .catch((error) => console.log(error));
  };

  const handleDelete = () => {
    if (!window.confirm("do you really want to delete advertisement?")) return;

    axios
      .delete(`http://localhost:8080/api/advertisements/${data.id}`, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      })
      .then((response) => console.log(response))
      .catch((error) => console.log(error));
  };

  return (
    <>
      <div>{adDescription}</div>
      <div>price {price}</div>
      <div>city {city}</div>

      {user.userRole === "ADMINISTRATOR" && (
        <form className="adEditForm">
          <input
            {...register("adName", {
              required: false,
              minLength: 1,
              maxLength: 50,
            })}
            placeholder="advertisement title"
            defaultValue={data.adName}
          />

          <input
            {...register("adDescription", {
              required: false,
              minLength: 1,
              maxLength: 200,
            })}
            placeholder="advertisement description"
            defaultValue={data.adDescription}
          />

          <input
            {...register("city", {
              required: false,
              minLength: 1,
              maxLength: 50,
            })}
            placeholder="city"
            defaultValue={data.city}
          />

          <input
            {...register("price", {
              required: false,
              minLength: 1,
              maxLength: 50,
            })}
            placeholder="price"
            defaultValue={data.price}
          />

          {/* <select {...register("uom")} defaultValue={materialData.uom}>
            <option value={"METER"}>m</option>
            <option value={"PIECE"}>pcs</option>
            <option value={"SET"}>set</option>
            <option value={"CUBIC_METER"}>m3</option>
            <option value={"KILOGRAM"}>kg</option>
          </select> */}

          <ButtonBasic
            clickHandle={handleSubmit(onSubmit)}
            text={`update advert`}
          />
          <ButtonBasic clickHandle={handleDelete} text={`delete advert`} />
        </form>
      )}
    </>
  );
};

export default AdvertDetailedInfo;
