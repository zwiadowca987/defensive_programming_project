'use client';
import React, {useState} from "react";
import Link from "next/link";
import axios from 'axios';
import LoginForm from "@/app/components/loginForm";

export default function Login() {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState('');
    const [user, setUser] = useState(null);

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        try {
            const response = await axios.post('http://localhost:8080/api/users/login', {
                email,
                password,
            });
            console.log("Zalogowany użytkownik:", response.data);
            setUser(response.data);
            setError('');
        } catch (err) {
            console.error("Błąd logowania:", err);
            setError('Nieprawidłowy email lub hasło');
        }
    };

    return (
        <div className="container d-flex justify-content-center align-items-center vh-100">
            <div className="card p-4 shadow" style={{ width: "400px" }}>
                <h2 className="text-center mb-4">Logowanie</h2>
                <LoginForm/>
                <div className="text-center mt-3">
                    <p>Nie masz konta? <Link href="/authentication/register">Zarejestruj się</Link></p>
                </div>
            </div>
        </div>
    );
}
