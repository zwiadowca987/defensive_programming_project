"use client";
import React, { useState } from "react";
import Link from "next/link";
import SetupForm from "@/app/components/setupForm";

export default function Setup() {
  return (
    <div className="container d-flex justify-content-center align-items-center">
      <div className="card p-4 shadow" style={{ width: "400px" }}>
        <h2 className="text-center mb-4">2 Factor authorisation</h2>
        <SetupForm />
        <div className="text-center mt-3">
          <p>
            Nie masz konta?{" "}
            <Link href="/authentication/register">Zarejestruj się</Link>
          </p>
        </div>
      </div>
    </div>
  );
}
