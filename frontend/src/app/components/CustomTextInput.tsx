import { useField } from "formik";
import { FloatingLabel, Form } from "react-bootstrap";

interface Props {
    name:string
    label:string
    placeholder:string
    type?:string
}

export default function CustomTextInput(props:Props){

    const [field, meta] = useField(props.name)
 
    const warnStyle = {

        color:'red'

    }

    return (
        <>
            <FloatingLabel label={props.label} controlId="floatingInput" className="mb-3">
                <Form.Control {...field} {...props}/>
            </FloatingLabel>
            {meta.error && meta.touched ? (
                <Form.Label style={warnStyle}>{meta.error}</Form.Label>
            ) : null}
        </>
    )

}