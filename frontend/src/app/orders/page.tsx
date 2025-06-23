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
        amount: 2,
        price: 50,
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
          </li>
        ))}
      </div>
    </div>
  );
}
