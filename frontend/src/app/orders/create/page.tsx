"use client";
import Link from "next/link";
import { useState } from "react";

// TODO: pobieranie produktu po id
type Product = {
  id: number;
  product: string;
  amount: number;
  price: number;
};

type Order = {
  id: number;
  customer: string;
  date: string;
  status: string;
  productsList: Product[];
  totalPrice: number;
};

const initialOrder: Order = {
  id: 0,
  customer: "",
  date: "",
  status: "",
  productsList: [],
  totalPrice: 0,
};

export default function CreateOrder() {
  const [order, setOrder] = useState<Order>(initialOrder);

  // Dodawanie nowego produktu do zamówienia
  const handleAddRow = () => {
    setOrder((prev) => ({
      ...prev,
      productsList: [
        ...prev.productsList,
        { id: Date.now(), product: "", amount: 1, price: 0 },
      ],
    }));
  };

  // Usuwanie produktu z zamówienia
  const handleRemoveRow = (id: number) => {
    setOrder((prev) => ({
      ...prev,
      productsList: prev.productsList.filter((p) => p.id !== id),
    }));
  };

  // Obsługa zmiany wartości w wierszu
  const handleProductChange = (
    idx: number,
    field: string,
    value: string | number
  ) => {
    setOrder((prev) => {
      const newList = [...prev.productsList];
      // @ts-ignore
      newList[idx][field] = value;
      return { ...prev, productsList: newList };
    });
  };

  return (
    <div className={"container"}>
      <div className={"text-center"}>
        <h1 className={"display-2"}>Dodaj Zamówienie</h1>
      </div>
      <div className={"m-5 list-group"}>
        <form
          className={"list-group-item"}
          onSubmit={(e) => e.preventDefault()}
        >
          <div className={"mb-3"}>
            <div className={"mb-3"}>
              <label>Klient</label>
              <input
                className={"form-control"}
                type={"text"}
                value={order.customer}
                onChange={(e) =>
                  setOrder({ ...order, customer: e.target.value })
                }
              />
            </div>
            <div className={"mb-3"}>
              <label>Całkowita Kwota</label>
              <input
                className={"form-control"}
                type={"text"}
                value={order.totalPrice + " PLN"}
                readOnly
              />
            </div>
            <div className={"mb-3"}>
              <label>Data</label>
              <input
                className={"form-control"}
                type={"text"}
                value={order.date}
                onChange={(e) => setOrder({ ...order, date: e.target.value })}
              />
            </div>
            <div className={"mb-3"}>
              <label>Status</label>
              <select
                className={"form-select"}
                value={order.status}
                onChange={(e) => setOrder({ ...order, status: e.target.value })}
              >
                <option value={""}>Wybierz status</option>
                <option value={"new"}>Nowe</option>
                <option value={"canceal"}>Anulowane</option>
                <option value={"finished"}>Zakończone</option>
              </select>
            </div>
            <label>Pozycje Zamówienia</label>
            <table className={"table table-bordered table-striped"}>
              <thead>
                <tr>
                  <th>Nazwa Produktu</th>
                  <th>Ilość</th>
                  <th>Kwota</th>
                  <th></th>
                </tr>
              </thead>
              <tbody>
                {order.productsList.map((product, idx) => (
                  <tr key={product.id}>
                    <td>
                      <input
                        className="form-control"
                        type="text"
                        value={product.product}
                        onChange={(e) =>
                          handleProductChange(idx, "product", e.target.value)
                        }
                      />
                    </td>
                    <td>
                      <input
                        className="form-control"
                        type="number"
                        min={1}
                        value={product.amount}
                        onChange={(e) =>
                          handleProductChange(
                            idx,
                            "amount",
                            Number(e.target.value)
                          )
                        }
                      />
                    </td>
                    <td>
                      <input
                        className="form-control"
                        type="number"
                        min={0}
                        value={product.price}
                        onChange={(e) =>
                          handleProductChange(
                            idx,
                            "price",
                            Number(e.target.value)
                          )
                        }
                      />
                    </td>
                    <td>
                      <button
                        type="button"
                        className="btn btn-danger"
                        onClick={() => handleRemoveRow(product.id)}
                      >
                        <i className={"bi bi-trash"}></i> Usuń
                      </button>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>

            <button type="button" className={"btn"} onClick={handleAddRow}>
              <i className={"bi bi-plus-circle"}></i> Dodaj Produkt
            </button>
          </div>

          <Link className={"btn"} href={`/orders/save/${order.id}`}>
            <i className={"bi bi-save"}></i> Zapisz
          </Link>

          <Link className={"btn"} href={"/orders/"}>
            <i className={"bi bi-arrow-left-circle"}></i> Powrót
          </Link>
        </form>
      </div>
    </div>
  );
}
