"use client";

import { observer } from "mobx-react-lite";
import { useStore } from "../stores/stores";
import { Form, Formik } from "formik";
import { Button } from "react-bootstrap";
import CustomTextInput from "./CustomTextInput";
import * as Yup from "yup";
import { useRouter } from "next/navigation";

export default observer(function RegisterForm() {
  const { userStore } = useStore();
  const router = useRouter();

  return (
    <Formik
      initialValues={{
        email: "",
        userName: "",
        firstName: "",
        lastName: "",
        password: "",
        error: null,
      }}
      validationSchema={Yup.object({
        email: Yup.string()
          .email("Podany mail nie jest prawidłowym adresem")
          .required("E-mail jest wymagany"),
        userName: Yup.string()
          .matches(/^[a-zA-Z0-9_]{3,20}$/, {
            message: "Nazwa użytkownika może składać się jedynie z liter",
          })
          .required("Nazwa użytkownika jest wymagana"),
        firstName: Yup.string()
          .matches(/^[a-zA-Zà-ÿÀ-Ÿ\s]{2,50}$/, {
            message: "Imię może składać się jedynie z liter",
          })
          .required("Imię jest wymagane"),
        lastName: Yup.string()
          .matches(/^[a-zA-Zà-ÿÀ-Ÿ\s]{2,50}$/, {
            message: "Nazwisko może składać się jedynie z liter",
          })
          .required("Nazwisko jest wymagane"),
        password: Yup.string()
          .min(8)
          .matches(/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[^\w\s<>;'"&]).{8,}$/, {
            message:
              "Hasło musi zawierać wielką literę, znak specjalny oraz liczbę",
          })
          .required("Hasło jest wymagane"),
      })}
      onSubmit={async (values, { setErrors }) => {
        try {
          await userStore.register(values);
          router.push("/authentication/login");
        } catch (error) {
          setErrors({ error: "Wystąpił błąd podczas rejestracji" });
        }
      }}
    >
      {({ handleSubmit, isSubmitting, isValid, dirty, errors }) => (
        <Form onSubmit={handleSubmit} autoComplete="off">
          <CustomTextInput
            type="email"
            label="E-mail"
            name="email"
            placeholder="a"
          />
          <CustomTextInput
            name="userName"
            label="Nazwa użytkownika"
            placeholder="User Name"
          />
          <CustomTextInput
            name="firstName"
            label="Imię"
            placeholder="First Name"
          />
          <CustomTextInput
            name="lastName"
            label="Nazwisko"
            placeholder="Last Name"
          />
          <CustomTextInput
            type="password"
            label="Hasło"
            name="password"
            placeholder="Password"
          />
          {errors.error && (
            <div className="text-danger mb-2">{errors.error}</div>
          )}
          <Button type="submit" disabled={isSubmitting || !dirty || !isValid}>
            Register
          </Button>
        </Form>
      )}
    </Formik>
  );
});
