'use client';
import {useState} from "react";
import Link from "next/link";

export default function Login() {
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");

    const handleSubmit = (e: React.FormEvent) => {
        e.preventDefault();
        // Tutaj dodasz logikę logowania np. API call
        console.log("Email:", email, "Password:", password);
    };

    return (
        <div className="container d-flex justify-content-center align-items-center">
            <div className="card p-4" style={{width: "400px"}}>
                <h2 className="text-center">Logowanie</h2>
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
                    <button type="submit" className="btn btn-primary w-100">Zaloguj się</button>
                </form>
                <div className="text-center mt-3">
                    <p>Nie masz konta? <Link href="/authentication/register">Zarejestruj się</Link></p>
                </div>
            </div>
        </div>
    );
}
