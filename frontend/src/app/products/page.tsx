import Link from "next/link";

// TODO: download products from the backend
// TODO: prawdopodobnie nie ma zliczania ilości produktów
const productsList = [
  {
    id: 1,
    name: "Produkt 1",
    price: 100,
    description: "Opis produktu 1",
    producer: "Producent 1",
    amount: 10,
  },
  {
    id: 2,
    name: "Produkt 2",
    price: 200,
    description: "Opis produktu 2",
    producer: "Producent 2",
    amount: 20,
  },
  {
    id: 3,
    name: "Produkt 3",
    price: 300,
    description: "Opis produktu 3",
    producer: "Producent 3",
    amount: 30,
  },
];

export default function Products() {
  return (
    <div className={"container"}>
      <div className={"text-center"}>
        <h1 className={"display-2"}>Katalog Produktów</h1>
      </div>
      <div className={"m-5 list-group"}>
        <Link className={"btn d-flex text-end"} href={"/products/create/"}>
          Dodaj Nowy Produkt
        </Link>

        {productsList.map((product) => (
          <div className={"list-group-item"} key={product.id}>
            <p className={"mb-1"}>{product.name}</p>
            <p className={"mb-1"}>{product.price} PLN</p>
            <p className={"mb-1"}>{product.description}</p>
            <p className={"mb-1"}>{product.producer}</p>
            <p className={"mb-1"}>Ilość dostępna: {product.amount}</p>

            <Link className={"btn"} href={`/products/edit/${product.id}`}>
              <i className={"bi bi-pencil-square"}></i> Edytuj
            </Link>

            <Link className={"btn"} href={`/products/delete/${product.id}`}>
              <i className={"bi bi-trash"}></i> Usuń
            </Link>
          </div>
        ))}
      </div>
    </div>
  );
}
