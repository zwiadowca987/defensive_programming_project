'use client';
import React, {useState} from "react";
import Link from "next/link";
import VerifyForm from "@/app/components/verifyForm";

export default function Verify() {

    

    return (
        <div className="container d-flex justify-content-center align-items-center vh-100">
            <div className="card p-4 shadow" style={{ width: "400px" }}>
                <h2 className="text-center mb-4">2 Factor authorisation</h2>
                <VerifyForm/>
                <div className="text-center mt-3">
                </div>
            </div>
        </div>
    );
}
