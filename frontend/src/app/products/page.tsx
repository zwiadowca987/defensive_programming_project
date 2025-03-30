import Link from "next/link";

export default function Products() {
    return (
        <div className={"container"}>
            <div className={"text-center"}>
                <h1 className={"display-2"}>Katalog Produktów</h1>
            </div>
            <div className={"m-5"}>
                <div className={"card m-3 p-3"}>
                    <div className={"row"}>
                        <div className={"col-4"}>
                            image
                        </div>
                        <div className={"card-body col-8"}>
                            <h3 className={"card-title"}>Placeholder</h3>
                            <p className={"card-text"}>Placeholder</p>
                            <Link className={"btn btn-primary"} href={"/shop"}>Przejdź dalej</Link>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}