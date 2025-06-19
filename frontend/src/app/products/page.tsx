import Link from "next/link";

// TODO: download products from the backend
const productsList = [
  {
    id: 1,
    name: "Produkt 1",
    description: "Opis produktu 1",
  },
  {
    id: 2,
    name: "Produkt 2",
    description: "Opis produktu 2",
  },
  {
    id: 3,
    name: "Produkt 3",
    description: "Opis produktu 3",
  },
];

export default function Products() {
  return (
    <div className={"container"}>
      <div className={"text-center"}>
        <h1 className={"display-2"}>Katalog Produktów</h1>
      </div>
      <div className={"m-5"}>
        <div className={"card m-3 p-3"}>
          <div className={"row"}>
            {productsList.map((product) => (
              <div className={"card-body col"} key={product.id}>
                <h3 className={"card-title"}>{product.name}</h3>
                <p className={"card-text"}>{product.description}</p>
                <Link
                  className={"btn btn-primary"}
                  href={"/shop/products/" + product.id}
                >
                  Przejdź dalej
                </Link>
              </div>
            ))}
          </div>
        </div>
      </div>
    </div>
  );
}
