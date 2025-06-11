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

export default function RootLayout({children,}: Readonly<{
    children: React.ReactNode;
}>) {
    return (
        <html lang="en">
            <body className={`${geistSans.variable} ${geistMono.variable} d-flex flex-column justify-content-between 100-vh`}>
                <header className={"mb-3 text-center"}>
                    <nav className={"navbar navbar-expand-lg navbar-light bg-light border-bottom box-shadow mb-3"}>
                        <ul className={"navbar-nav m-auto"}>
                            <li className={"nav-item mx-3"}><Link className={"nav-link"} href={"/"}>Strona Główna</Link></li>
                            <li className={"nav-item mx-3"}><Link className={"nav-link"} href={"/authentication/login"}>Logowanie</Link></li>
                            <li className={"nav-item mx-3"}><Link className={"nav-link"} href={"/authentication/register"}>Rejestracja</Link></li>
                        </ul>
                    </nav>
                </header>

                <main>
                    <section className={"m-3 p-3"}>
                        {children}
                    </section>
                </main>

                <footer className={"text-center mt-3 p-3 bg-light"}>
                    <p>Copyright &copy; {new Date().getFullYear()}</p>
                </footer>
            </body>
        </html>
    );
}
