import { useForm } from "react-hook-form";
import ButtonBasic from "../elements/ButtonBasic";
import axios from "axios";
import "./CreateAccountCard.css";
import { useNavigate } from "react-router-dom";
import FormFieldError from "../elements/FormFieldError";

const CreateAccountCard = () => {
  const {
    register,
    reset,
    handleSubmit,
    watch,
    formState: { errors },
  } = useForm();
  const navigate = useNavigate();

  const onSubmit = (formData) => {

    console.log("reg", formData);

    const accoutData = {
      firstName: formData.firstName,
      lastName: formData.lastName,
      email: formData.email,
      password: formData.password,
      passwordConfirm: formData.passwordConfirm,
    };
    axios
      .post("http://localhost:8080/api/auth/register", accoutData)
      .then((response) => {
        localStorage.setItem("token", response.data.token);
        reset();
        navigate("/");
        alert("registration succesful");
      })
      .catch((error) => console.log(error));
  };

  return (
    <>
      <form className="createAccountCard">
        <input
          {...register("firstName", {
            required: true,
            minLength: 1,
            maxLength: 50,
          })}
          placeholder="first name"
        />

        <input
          {...register("lastName", {
            required: true,
            minLength: 1,
            maxLength: 50,
          })}
          placeholder="last name"
        />

        <input
          {...register("email", {
            required: true,
            minLength: 1,
            maxLength: 50,
          })}
          placeholder="email"
        />

        <section>
          <input
            type="password"
            id="registerPassword"
            {...register("password", {
              required: {
                value: true,
                message: "enter your chosen password",
              },
              minLength: {
                value: 5,
                message: "password must be at least 5 symbols long",
              },
              maxLength: {
                value: 50,
                message: "password must be no longer than 99 symbols",
              },
            })}
            placeholder="password"
          />
        </section>

        <section>
          <input
            type="password"
            id="registerConfirmPassword"
            {...register("passwordConfirm", {
              required: {
                value: true,
                message: "enter your chosen password",
              },
              validate: (value) => {
                if (watch("password") !== value) {
                  return "password fiels must match"
                }
              },
              minLength: {
                value: 5,
                message: "password must be at least 5 symbols long",
              },
              maxLength: {
                value: 99,
                message: "password must be no longer than 99 symbols",
              },
            })}
            placeholder="password"
          />
          {errors.passwordConfirm && (
            <FormFieldError>{errors.passwordConfirm.message}</FormFieldError>
          )}
        </section>

        <ButtonBasic
          clickHandle={handleSubmit(onSubmit)}
          text="create account"
        />
      </form>
    </>
  );
};

export default CreateAccountCard;
