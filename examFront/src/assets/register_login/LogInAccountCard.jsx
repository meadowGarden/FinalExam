import { useForm } from "react-hook-form";
import ButtonBasic from "../elements/ButtonBasic";
import axios from "axios";
import "./CreateAccountCard.css";
import { useNavigate } from "react-router-dom";
import FormFieldError from "../elements/FormFieldError";
import useUserStore from "../store/userStore";

const LogInAccountCard = () => {
  const {
    register,
    reset,
    handleSubmit,
    formState: { errors },
  } = useForm();
  const navigate = useNavigate();
  const setUser = useUserStore((state) => state.setUser);

  const onSubmit = (formData) => {
    const accoutData = {
      email: formData.email,
      password: formData.password,
    };
    axios
      .post("http://localhost:8080/api/auth/login", accoutData)
      .then((response) => {
        console.log("login card response", response);

        const { token, userLoginDTO } = response.data;
        setUser({
          ...userLoginDTO,
          token,
        });
        localStorage.setItem("authToken", token);

        reset();
        navigate("/advertisements");
      })
      .catch((error) => console.log(error));
  };

  return (
    <>
      <form className="createAccountCard" onSubmit={handleSubmit(onSubmit)}>
        <section>
          <input
            {...register("email", {
              required: true,
              minLength: 1,
              maxLength: 50,
            })}
            placeholder="email"
          />
        </section>

        <section>
          <input
            type="password"
            id="loginConfirmPassword"
            {...register("password", {
              required: {
                value: true,
                message: "enter your password",
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

        <ButtonBasic clickHandle={handleSubmit(onSubmit)} text="login" />
      </form>
    </>
  );
};

export default LogInAccountCard;
