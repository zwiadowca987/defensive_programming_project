"use client";
import { useParams } from "next/navigation";
import Link from "next/link";
import React, { useState } from "react";
import { useStore } from "@/app/stores/stores";

// TODO: pobieranie zamówienia po id
const orders = [
  {
    id: 1,
    customer: "Produkt 1",
    date: "10.10.2023",
    status: "Zrealizowane",
    productsList: [
      { id: 1, product: "Produkt 1", amount: 1, price: 50 },
      { id: 2, product: "Produkt 2", amount: 2, price: 100 },
      { id: 3, product: "Produkt 3", amount: 3, price: 150 },
    ],
    totalPrice: 100,
  },
];

export default function EditOrder() {
  const params = useParams();
  const id = Number(params.id);

  const {orderStore} = useStore()

  const order = orderStore.orderRegistry.get(id.toString());

  // Stan dla pozycji zamówienia
  const [productsList, setProductsList] = useState(
    order ? order.products : []
  );

  // Dodawanie nowego wiersza
  const handleAddRow = () => {
    setProductsList([
      ...productsList,
      { productId: Date.now(), productName: "", quantity: 1, price: 0 },
    ]);
  };

  // Usuwanie wiersza
  const handleRemoveRow = (id: number) => {
    setProductsList(productsList.filter((p) => p.productId !== id));
  };

  if (!order) return <div>Nie znaleziono zamówienia</div>;

  return (
    <div className={"container"}>
      <div className={"text-center"}>
        <h1 className={"display-2"}>Edytuj Zamówienie</h1>
      </div>
      <div className={"m-5 list-group"}>
        <form className={"list-group-item"}>
          <div className={"mb-3"}>
            <label>Klient</label>
            <input
              className={"form-control"}
              type={"text"}
              value={order.clientId}
            />
          </div>

          <div className={"mb-3"}>
            <label>Całkowita Kwota</label>
            <input
              className={"form-control"}
              type={"text"}
              value={order.price + " PLN"}
            />
          </div>

          <div className={"mb-3"}>
            <label>Data</label>
            <input
              className={"form-control"}
              type={"text"}
              value={order.date.toISOString()}
            />
          </div>

          <div className={"mb-3"}>
            <label>Status</label>
            <select className={"form-select"}>
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
              {productsList.map((product, idx) => (
                <tr key={product.productId}>
                  <td>
                    <input
                      className="form-control"
                      type="text"
                      value={product.productName}
                      onChange={(e) => {
                        const newList = [...productsList];
                        newList[idx].productName = e.target.value;
                        setProductsList(newList);
                      }}
                    />
                  </td>
                  <td>
                    <input
                      className="form-control"
                      type="number"
                      value={product.quantity}
                      min={1}
                      onChange={(e) => {
                        const newList = [...productsList];
                        newList[idx].quantity = Number(e.target.value);
                        setProductsList(newList);
                      }}
                    />
                  </td>
                  <td>
                    <input
                      className="form-control"
                      type="number"
                      value={product.price}
                      min={0}
                      onChange={(e) => {
                        const newList = [...productsList];
                        newList[idx].price = Number(e.target.value);
                        setProductsList(newList);
                      }}
                    />
                  </td>
                  <td>
                    <button
                      type="button"
                      className="btn btn-danger"
                      onClick={() => handleRemoveRow(product.productId)}
                    >
                      <i className="bi bi-trash"></i> Usuń
                    </button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
          <button
            type="button"
            className="btn btn-primary mb-3"
            onClick={handleAddRow}
          >
            Dodaj pozycję
          </button>

          <Link className={"btn"} href={`/orders`} onClick={(e) => orderStore.update(order,id)}>
            <i className={"bi bi-save"}></i> Zapisz
          </Link>

          <Link className={"btn"} href={"/orders"}>
            <i className={"bi bi-arrow-left-circle"}></i> Powrót
          </Link>
        </form>
      </div>
    </div>
  );
}
