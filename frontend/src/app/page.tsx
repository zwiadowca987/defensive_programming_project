import styles from "./page.module.css";

export default function Home() {
    return (
        <div className={styles.page}>
            <main className={styles.main}>
                <h1 className={"text-center display-1"}>
                    Strona Główna
                </h1>
            </main>
        </div>
    );
}
