import {observer} from "mobx-react-lite"
import { useStore } from "../stores/stores"
import { Field, Form, Formik } from "formik";
import { Button, Container, Row } from "react-bootstrap";
import CustomTextInput from "./CustomTextInput";
import * as Yup from "yup"
import { useEffect, useState } from "react";

export default observer (function VerifyForm(){

    const{userStore} = useStore();

    return(

        <Formik 
            initialValues={{username:userStore.user?.userName!, totpCode:'', error:null}}
            validationSchema={ Yup.object({
                totpCode: Yup.number()
            })
            }
            onSubmit={(values, {setErrors}) => userStore.MFAVerify(values).catch(error => setErrors({error:"wrong password or e-mail"}))}>

            {({handleSubmit, isSubmitting, errors})=>(
                <Form onSubmit={handleSubmit}>
                    <Container>
                        <CustomTextInput name="totpCode" type="number" label="Input your code" placeholder="code"/>
                        <Button type="submit" disabled={isSubmitting}>Verify</Button>
                    </Container>
                </Form>
            )}
        </Formik>

    )

})