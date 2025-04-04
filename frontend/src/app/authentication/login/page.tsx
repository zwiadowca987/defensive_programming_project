'use client';
import React, {useState} from "react";
import Link from "next/link";
import axios from 'axios';

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
                <form onSubmit={handleSubmit}>
                    <div className="mb-3">
                        <label htmlFor="email" className="form-label">Email</label>
                        <input
                            type="email"
                            className="form-control"
                            id="email"
                            value={email}
                            onChange={(e) => setEmail(e.target.value)}
                            required
                        />
                    </div>
                    <div className="mb-3">
                        <label htmlFor="password" className="form-label">Hasło</label>
                        <input
                            type="password"
                            className="form-control"
                            id="password"
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                            required
                        />
                    </div>
                    {error && <div className="alert alert-danger" role="alert">{error}</div>}
                    <button type="submit" className="btn btn-primary w-100">Zaloguj się</button>
                </form>
                <div className="text-center mt-3">
                    <p>Nie masz konta? <Link href="/authentication/register">Zarejestruj się</Link></p>
                </div>
            </div>
        </div>
    );
}
