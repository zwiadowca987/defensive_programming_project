"use client";
import { useParams } from "next/navigation";
import Link from "next/link";

// TODO: pobieranie produktu po id
const products = [
  {
    id: 1,
    name: "Produkt 1",
    price: 100,
    description: "Opis produktu 1",
    producer: "Producent 1",
    amount: 10,
  },
];

export default function EditOrder() {
  const params = useParams();
  const id = Number(params.id);
  const product = products.find((p) => p.id === id);

  if (!product) return <div>Nie znaleziono produktu</div>;

  return (
    <div className={"container"}>
      <div className={"text-center"}>
        <h1 className={"display-2"}>Edytuj Produkt</h1>
      </div>
      <div className={"m-5 list-group"}>
        <form className={"list-group-item"}>
          <div className={"mb-3"}>
            <label>Nazwa</label>
            <input
              className={"form-control"}
              type={"text"}
              value={product.name}
            />
          </div>

          <div className={"mb-3"}>
            <label>Cena</label>
            <div className="input-group">
              <input
                className="form-control"
                type="text"
                value={product.price}
              />
              <span className="input-group-text">PLN</span>
            </div>
          </div>

          <div className={"mb-3"}>
            <label>Opis</label>
            <input
              className={"form-control"}
              type={"text"}
              value={product.description}
            />
          </div>

          <div className={"mb-3"}>
            <label>Producent</label>
            <input
              className={"form-control"}
              type={"text"}
              value={product.producer}
            />
          </div>

          <div className={"mb-3"}>
            <label>Ilość w Magazynach</label>
            <input
              className={"form-control"}
              type={"text"}
              value={product.amount}
            />
          </div>

          <Link className={"btn"} href={`/products/save/${product.id}`}>
            <i className={"bi bi-save"}></i> Zapisz
          </Link>

          <Link className={"btn"} href={"/products/"}>
            <i className={"bi bi-arrow-left-circle"}></i> Powrót
          </Link>
        </form>
      </div>
    </div>
  );
}
