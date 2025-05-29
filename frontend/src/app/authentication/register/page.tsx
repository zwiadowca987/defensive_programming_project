'use client';
import {useState} from "react";
import Link from "next/link";
import RegisterForm from "@/app/components/registerForm";

export default function Register() {

    return (
        <div className="container d-flex justify-content-center align-items-center">
            <div className="card p-4" style={{width: "400px"}}>
                <h2 className="text-center">Rejestracja</h2>
                    <RegisterForm/>
                <div className="text-center mt-3">
                    <p>Masz już konto? <Link href="/authentication/login">Zaloguj się</Link></p>
                </div>
            </div>
        </div>
    );
}
