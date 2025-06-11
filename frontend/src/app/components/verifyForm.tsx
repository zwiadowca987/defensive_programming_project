'use client';

import { observer } from "mobx-react-lite";
import { useStore } from "../stores/stores";
import { Form, Formik } from "formik";
import { Button, Container } from "react-bootstrap";
import CustomTextInput from "./CustomTextInput";
import * as Yup from "yup";
import { useRouter } from "next/navigation";

export default observer(function VerifyForm() {
  const { userStore } = useStore();
  const router = useRouter();

  return (
    <Formik
      initialValues={{ username: userStore.user?.userName ?? '', totpCode: '', error: null }}
      validationSchema={Yup.object({
        totpCode: Yup.string()
          .matches(/^\d{6}$/, "Kod musi mieć 6 cyfr")
          .required("Kod jest wymagany"),
      })}
      onSubmit={async (values, { setErrors }) => {
        try {
          await userStore.MFAVerify(values);
          router.push("/");
        } catch (error) {
          setErrors({ error: "Nieprawidłowy kod" });
        }
      }}
    >
      {({ handleSubmit, isSubmitting, errors }) => (
        <Form onSubmit={handleSubmit}>
          <Container>
            <CustomTextInput name="totpCode" type="text" label="Kod MFA" placeholder="123456" />
            {errors.error && <div className="text-danger mb-2">{errors.error}</div>}
            <Button type="submit" disabled={isSubmitting}>Zweryfikuj</Button>
          </Container>
        </Form>
      )}
    </Formik>
  );
});
