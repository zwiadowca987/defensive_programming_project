"use client";
import Link from "next/link";
import { useState } from "react";

const clientsList = [
  {
    id: 1,
    name: "Janek 1",
    address: "Jakiś 1",
    email: "jakiś 1",
    phone: 100,
  },
];

export default function CreateClient() {
  const [form, setForm] = useState({
    name: "",
    address: "",
    email: "",
    phone: "",
  });

  return (
    <div className={"container"}>
      <div className={"text-center"}>
        <h1 className={"display-2"}>Dodaj Klienta</h1>
      </div>
      <div className={"m-5 list-group"}>
        <form className={"list-group-item"}>
          <div className={"mb-3"}>
            <label>Imię i nazwisko</label>
            <input
              className={"form-control"}
              type={"text"}
              value={form.name}
              onChange={(e) => setForm({ ...form, name: e.target.value })}
            />
          </div>
          <div className={"mb-3"}>
            <label>Adres</label>
            <input
              className={"form-control"}
              type={"text"}
              value={form.address}
              onChange={(e) => setForm({ ...form, address: e.target.value })}
            />
          </div>
          <div className={"mb-3"}>
            <label>Email</label>
            <input
              className={"form-control"}
              type={"email"}
              value={form.email}
              onChange={(e) => setForm({ ...form, email: e.target.value })}
            />
          </div>
          <div className={"mb-3"}>
            <label>Telefon</label>
            <input
              className={"form-control"}
              type={"tel"}
              value={form.phone}
              onChange={(e) => setForm({ ...form, phone: e.target.value })}
            />
          </div>
          <Link className={"btn"} href={"/clients/"}>
            <i className={"bi bi-arrow-left-circle"}></i> Powrót
          </Link>
          <button className={"btn btn-primary ms-2"} type="submit">
            <i className={"bi bi-save"}></i> Zapisz
          </button>
        </form>
      </div>
    </div>
  );
}
