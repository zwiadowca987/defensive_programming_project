import {observer} from "mobx-react-lite"
import { useStore } from "../stores/stores"
import { Form, Formik } from "formik";
import { Button, Container } from "react-bootstrap";
import CustomTextInput from "./CustomTextInput";
import * as Yup from "yup"
import QRCode from "react-qr-code";

export default observer (function LoginForm(){

    const{userStore} = useStore();

    const code = userStore.MFASetup();

    return(
        <>
        
        { userStore.getQrCode().localeCompare('default') ? <QRCode value={userStore.getQrCode().toString()}/> : <p>Loading...</p>}
        
        <Formik 
            initialValues={{username:userStore.user?.userName!, totpCode:'', error:null}}
            validationSchema={ Yup.object({

            })
            }
            onSubmit={(values, {setErrors}) => userStore.MFAVerify(values).catch(error => setErrors({error:"wrong password or e-mail"}))}>

            {({handleSubmit, isSubmitting, errors})=>(
                <Form onSubmit={handleSubmit}>
                    <Container>
                        <CustomTextInput name="totpCode" type="number" label="Input your code" placeholder="code"/>
                        <Button type="submit" disabled={isSubmitting}>Setup</Button>
                    </Container>
                </Form>
            )}
        </Formik>
        </>
    )

})