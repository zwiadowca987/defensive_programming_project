"use client";

import { observer } from "mobx-react-lite";
import { useStore } from "../stores/stores";
import { Field, Form, Formik } from "formik";
import { Button, ButtonGroup, Container, ToggleButton } from "react-bootstrap";
import CustomTextInput from "./CustomTextInput";
import { useRouter } from "next/navigation";
import React from "react";
import { useState } from 'react'

export default observer(function LoginForm() {
  const { userStore } = useStore();
  const router = useRouter();
  const [isMFALogin, setMFALogin] = useState(false);

  function toggleMfaLogin() {

    setMFALogin(!isMFALogin);

  }
 

  return (
    <Formik
      initialValues={{ email: "", password: "", totpCode:"", error: null }}
      onSubmit={async (values, { setErrors }) => {
        try {
          const user = await userStore.login(values);

          router.push("/authentication/verify");
        } catch (error) {
          setErrors({ error: "Wrong password or e-mail" });
        }
      }}
    >
      {({ handleSubmit, isSubmitting, errors }) => (
        <Form onSubmit={handleSubmit}>
          <Container>
            <CustomTextInput
              type="email"
              name="email"
              label="E-mail"
              placeholder="mail"
            />
            <CustomTextInput
              type="password"
              name="password"
              label="Hasło"
              placeholder="pass"
            />
            {errors.error && (
              <div className="text-danger mb-2">{errors.error}</div>
            )}
            <ButtonGroup className="mb-2">
              <ToggleButton 
                className="mb-2"
                id="toggle-check"
                type="checkbox"
                variant="outline-primary"
                checked={isMFALogin}
                value="1"
                onChange={(e) => setMFALogin(e.currentTarget.checked)}
                >
                Użyj autoryzacji wieloetapowej
              </ToggleButton>
            </ButtonGroup>
            <br/>
            {isMFALogin?<CustomTextInput 
              type="totpCode"
              name="totpCode"
              label="Kod 2FA"
              placeholder="Kod 2FA"
              />:
              <></>}
            <ButtonGroup className="mb-2">
              <Button type="submit" disabled={isSubmitting}>
                Login
              </Button>
            </ButtonGroup>
          </Container>
        </Form>
      )}
    </Formik>
  );
});
