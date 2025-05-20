import {observer} from "mobx-react-lite"
import { useStore } from "../stores/stores"
import { Field, Form, Formik } from "formik";
import { Button } from "react-bootstrap";
import * as Yup from "yup"

export default observer (function LoginForm(){

    const{userStore} = useStore();

    return(

        <Formik 
        initialValues={{email:'',userName:'',firstName:'',lastName:'', password:'', error:null}}
        validationSchema={Yup.object({
            email:Yup.string().email().required(),
            userName:Yup.string().required(),
            firstName:Yup.string().required(),
            lastName:Yup.string().required(),
            password:Yup.string().length(8).required()
        })}
        onSubmit={(values, {setErrors}) => userStore.register(values).catch(error => setErrors({error:"wrong password or e-mail"}))}>
            {({handleSubmit, isSubmitting, errors, isValid, dirty})=>(
                <Form onSubmit={handleSubmit} autoComplete="off">
                    <Field type="email" name="email"/>
                    <Field name="userName" placeholder="User Name"/>
                    <Field name="firstName" placeholder="First Name"/>
                    <Field name="lastName" placeholder="Last Name"/>
                    <Field type="password" name="password" placeholder="Password"/>
                    <Button type="submit" disabled={isSubmitting || !dirty || !isValid}>Register</Button>
                </Form>
            )}
        </Formik>

    )

})