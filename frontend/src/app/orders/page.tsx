import Link from "next/link";

// TODO: download orders from the backend
const ordersList = [
  {
    id: 1,
    customer: "Produkt 1",
    date: "10.10.2023",
    status: "Zrealizowane",
    productsList: [
      {
        id: 1,
        product: "Produkt 1",
        amount: 1,
        price: 50,
      },
      {
        id: 2,
        product: "Produkt 2",
        amount: 2,
        price: 100,
      },
      {
        id: 3,
        product: "Produkt 3",
        amount: 3,
        price: 150,
      },
    ],
    totalPrice: 100,
  },
  {
    id: 2,
    customer: "Produkt 1",
    date: "10.10.2023",
    status: "Zrealizowane",
    productsList: [
      {
        id: 1,
        product: "Produkt 1",
        amount: 2,
        price: 50,
      },
    ],
    totalPrice: 100,
  },
  {
    id: 3,
    customer: "Produkt 1",
    date: "10.10.2023",
    status: "Zrealizowane",
    productsList: [
      {
        id: 1,
        product: "Produkt 1",
        amount: 2,
        price: 50,
      },
    ],
    totalPrice: 100,
  },
];

export default function Orders() {
  return (
    <div className={"container"}>
      <div className={"text-center"}>
        <h1 className={"display-2"}>Zamówienia</h1>
      </div>
      <div className={"m-5 list-group"}>
        <Link className={"btn d-flex text-end"} href={"/orders/create/"}>
          Dodaj Nowe Zamówienie
        </Link>

        {ordersList.map((order) => (
          <li className={"list-group-item"} key={order.id}>
            <p className={"mb-1"}>{order.customer}</p>
            <p className={"mb-1"}>{order.totalPrice} PLN</p>
            <p className={"mb-1"}>{order.date}</p>
            <p className={"mb-1"}>{order.status}</p>
            <ul className={"list-group"}>
              {order.productsList.map((product) => (
                <li className={"list-group-item"} key={product.id}>
                  {product.product} - {product.amount} szt. - {product.price}{" "}
                  PLN
                </li>
              ))}
            </ul>
            {/* TODO: Tutaj chyba trochę inaczej to ale to najpierw musi być pobieranie */}
            <Link className={"btn"} href={`/orders/edit/${order.id}`}>
              <i className={"bi bi-pencil-square"}></i> Edytuj
            </Link>
            <Link className={"btn"} href={`/orders/delete/${order.id}`}>
              <i className={"bi bi-trash"}></i> Usuń
            </Link>
          </li>
        ))}
      </div>
    </div>
  );
}
