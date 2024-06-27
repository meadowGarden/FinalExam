import axios from "axios";
import ButtonBasic from "../elements/ButtonBasic";
import { useForm } from "react-hook-form";
import useUserStore from "../store/userStore";

const AddAdvertCard = () => {
  const token =
    useUserStore((state) => state.token) || localStorage.getItem("authToken");

  const {
    register,
    reset,
    handleSubmit,
    formState: { errors },
  } = useForm();

  const onSubmit = (formData) => {
    const newAdvert = {
      adName: formData.adName,
      adDescription: formData.adDescription,
      price: formData.price,
      city: formData.city,
    };

    axios
      .post("http://localhost:8080/api/advertisements", newAdvert, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      })
      .then((response) => {
        console.log(response);
        reset();
      })
      .catch((error) => console.log("error -> ", error));
  };

  return (
    <>
      <form className="saveAdvertForm" onSubmit={handleSubmit(onSubmit)}>
        <input
          {...register("adName", {
            required: false,
            minLength: 1,
            maxLength: 50,
          })}
          placeholder="advertisement title"
        />

        <input
          {...register("adDescription", {
            required: false,
            minLength: 1,
            maxLength: 200,
          })}
          placeholder="advertisement description"
        />

        <input
          {...register("city", {
            required: false,
            minLength: 1,
            maxLength: 50,
          })}
          placeholder="city"
        />

        <input
          {...register("price", {
            required: false,
            minLength: 1,
            maxLength: 50,
          })}
          placeholder="price"
        />
        <ButtonBasic clickHandle={handleSubmit(onSubmit)} text={`post`} />
      </form>
    </>
  );
};

export default AddAdvertCard;
