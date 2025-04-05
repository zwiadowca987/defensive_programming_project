import type {Metadata} from "next";
import {Geist, Geist_Mono} from "next/font/google";
import "./globals.css";
import "bootstrap/dist/css/bootstrap.min.css";
import Link from "next/link";

const geistSans = Geist({
    variable: "--font-geist-sans",
    subsets: ["latin"],
});

const geistMono = Geist_Mono({
    variable: "--font-geist-mono",
    subsets: ["latin"],
});

export const metadata: Metadata = {
    title: "System Sklepu TechPoint",
    description: "System wspomagający zarządzanie sklepem i magazynem sklepu TechPoint",
};

function getYear() {
    return new Date().getFullYear();
}

export default function RootLayout({children,}: Readonly<{
    children: React.ReactNode;
}>) {
    return (
        <html lang="en">
            <body className={`${geistSans.variable} ${geistMono.variable} d-flex flex-column justify-content-between vh-100`}>
                <header className={"mb-3 text-center"}>
                    <h1 className={"display-1"}>{metadata.title?.toString()}</h1>

                    <nav className={"navbar navbar-expand-lg navbar-light bg-light border-bottom box-shadow m-3"}>
                        <ul className={"navbar-nav m-auto"}>
                            <li className={"nav-item mx-3"}><Link className={"nav-link"} href={"/"}>Strona Główna</Link></li>
                            <li className={"nav-item mx-3"}><Link className={"nav-link"} href={"/products"}>Produkty</Link></li>
                            <li className={"nav-item mx-3"}><Link className={"nav-link"} href={"/partnership"}>Magazyn</Link></li>
                            <li className={"nav-item mx-3"}><Link className={"nav-link"} href={"/about"}>Informacje</Link></li>
                            <li className={"nav-item mx-3"}><Link className={"nav-link"} href={"/authentication/login"}>Logowanie</Link></li>
                        </ul>
                    </nav>
                </header>

                <main>
                    <section className={"m-3 p-3"}>
                        {children}
                    </section>
                </main>

                <footer className={"text-center m-3 p-3 bg-light"}>
                    <p>Copyright &copy; {getYear()} TechPoint</p>
                </footer>
            </body>
        </html>
    );
}
