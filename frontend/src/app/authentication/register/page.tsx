'use client';
import {useState} from "react";
import Link from "next/link";

export default function Register() {
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [confirmPassword, setConfirmPassword] = useState("");

    const handleSubmit = (e: React.FormEvent) => {
        e.preventDefault();
        if (password !== confirmPassword) {
            alert("Hasła nie są takie same!");
            return;
        }
        // Tutaj dodasz logikę rejestracji np. API call
        console.log("Email:", email, "Password:", password);
    };

    return (
        <div className="container d-flex justify-content-center align-items-center">
            <div className="card p-4" style={{width: "400px"}}>
                <h2 className="text-center">Rejestracja</h2>
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
                    <div className="mb-3">
                        <label htmlFor="confirmPassword" className="form-label">Potwierdź hasło</label>
                        <input
                            type="password"
                            className="form-control"
                            id="confirmPassword"
                            value={confirmPassword}
                            onChange={(e) => setConfirmPassword(e.target.value)}
                            required
                        />
                    </div>
                    <button type="submit" className="btn btn-primary w-100">Zarejestruj się</button>
                </form>
                <div className="text-center mt-3">
                    <p>Masz już konto? <Link href="/authentication/login">Zaloguj się</Link></p>
                </div>
            </div>
        </div>
    );
}
