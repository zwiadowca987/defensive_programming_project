import Link from "next/link";

// TODO: download products from the backend
const productsList = [
  {
    id: 1,
    name: "Produkt 1",
    price: 100,
    description: "Opis produktu 1",
    producer: "Producent 1",
  },
  {
    id: 2,
    name: "Produkt 2",
    price: 200,
    description: "Opis produktu 2",
    producer: "Producent 2",
  },
  {
    id: 3,
    name: "Produkt 3",
    price: 300,
    description: "Opis produktu 3",
    producer: "Producent 3",
  },
];

export default function Products() {
  return (
    <div className={"container"}>
      <div className={"text-center"}>
        <h1 className={"display-2"}>Katalog Produktów</h1>
      </div>
      <div className={"m-5 row justify-content-around"}>
        {productsList.map((product) => (
          <div className={"card m-3 p-3 col-3 d-flex"} key={product.id}>
            <div className={"card-body row"}>
              <h3 className={"card-title col-8"}>{product.name}</h3>
              <h3 className={"card-title col-4 text-end"}>
                {product.price} PLN
              </h3>
              <h4 className={"card-subtitle mb-2 text-muted"}>
                {product.producer}
              </h4>
              <p className={"card-text"}>{product.description}</p>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
}
