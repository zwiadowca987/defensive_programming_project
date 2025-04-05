'use client';
import {useState} from "react";
import Link from "next/link";

export default function Register() {
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [confirmPassword, setConfirmPassword] = useState("");
    const [userName, setUserName] = useState("");
    const [firstName, setFirstName] = useState("");
    const [lastName, setLastName] = useState("");

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        if (password !== confirmPassword) {
            alert("Hasła nie są takie same!");
            return;
        }

        const usernameRegex = /^[a-zA-Z0-9_]{3,20}$/;
        if (!usernameRegex.test(userName)) {
            alert("Nazwa użytkownika musi zawierać tylko litery, cyfry i znak podkreślenia (_), oraz mieć długość od 3 do 20 znaków.");
            return;
        }

        const nameRegex = /^[a-zA-Zà-ÿÀ-Ÿ\s]{2,50}$/;
        if (!nameRegex.test(firstName) || !nameRegex.test(lastName)) {
            alert("Imię i nazwisko mogą zawierać tylko litery (łącznie z akcentami) oraz spacje, i muszą mieć długość od 2 do 50 znaków.");
            return;
        }

        const emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
        if (!emailRegex.test(email)) {
            alert("Adres email musi mieć poprawny format (np. example@example.com).");
            return;
        }

        const passwordRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[^\w\s<>;'"&]).{8,}$/;
        if (!passwordRegex.test(password)) {
            alert("Hasło musi mieć co najmniej 8 znaków, jedną dużą literę, jedną małą literę, jedną cyfrę i jeden znak specjalny. Akceptowane znaki specjalne to: ! @ # $ % ^ & * ( ) _ + - = { } [ ] | \\ : ; , . ? /.");
            return;
        }

        const response = await fetch("http://localhost:8080/api/users/register", {
            method: "POST",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify({
                userName,
                firstName,
                lastName,
                email,
                password,
                role: "USER"
            }),
        });

        if (response.ok) {
            alert("Rejestracja zakończona sukcesem!");
        } else {
            const error = await response.text();
            alert("Błąd rejestracji: " + error);
        }
    };

    return (
        <div className="container d-flex justify-content-center align-items-center">
            <div className="card p-4" style={{width: "400px"}}>
                <h2 className="text-center">Rejestracja</h2>
                <form onSubmit={handleSubmit}>
                    <div className="mb-3">
                        <label className="form-label">Nazwa użytkownika</label>
                        <input type="text" className="form-control" value={userName}
                               onChange={(e) => setUserName(e.target.value)} required/>
                    </div>
                    <div className="mb-3">
                        <label className="form-label">Imię</label>
                        <input type="text" className="form-control" value={firstName}
                               onChange={(e) => setFirstName(e.target.value)} required/>
                    </div>
                    <div className="mb-3">
                        <label className="form-label">Nazwisko</label>
                        <input type="text" className="form-control" value={lastName}
                               onChange={(e) => setLastName(e.target.value)} required/>
                    </div>
                    <div className="mb-3">
                        <label className="form-label">Email</label>
                        <input type="email" className="form-control" value={email}
                               onChange={(e) => setEmail(e.target.value)} required/>
                    </div>
                    <div className="mb-3">
                        <label className="form-label">Hasło</label>
                        <input type="password" className="form-control" value={password}
                               onChange={(e) => setPassword(e.target.value)} required/>
                    </div>
                    <div className="mb-3">
                        <label className="form-label">Potwierdź hasło</label>
                        <input type="password" className="form-control" value={confirmPassword}
                               onChange={(e) => setConfirmPassword(e.target.value)} required/>
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
