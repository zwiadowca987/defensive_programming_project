import {observer} from "mobx-react-lite"
import { useStore } from "../stores/stores"
import { Field, Form, Formik } from "formik";
import { Button, Container, Row } from "react-bootstrap";
import CustomTextInput from "./CustomTextInput";

export default observer (function LoginForm(){

    const{userStore} = useStore();

    return(

        <Formik 
        initialValues={{email:'', password:'', error:null}}
        onSubmit={(values, {setErrors}) => userStore.login(values).catch(error => setErrors({error:"wrong password or e-mail"}))}>
            {({handleSubmit, isSubmitting, errors})=>(
                <Form onSubmit={handleSubmit}>
                    <Container>
                        <CustomTextInput type="email" name="email" label="e-mail" placeholder="mail"/>
                        <CustomTextInput type="password" name="password" label="Password" placeholder="pass"/>
                        <Button type="submit" disabled={isSubmitting}>Login</Button>
                    </Container>
                </Form>
            )}
        </Formik>

    )

})