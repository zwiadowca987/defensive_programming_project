// TODO: download orders from the backend
const ordersList = [
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

export default function Orders() {
  return (
    <div className={"container"}>
      <div className={"text-center"}>
        <h1 className={"display-2"}>Zamówienia</h1>
      </div>
      <div className={"m-5 list-group"}>
        {ordersList.map((order) => (
          <li className={"list-group-item"} key={order.id}>
            <div
              className={"d-flex justify-content-between align-items-center"}
            >
              <h3 className={"mb-1"}>{order.name}</h3>
              <h3 className={"mb-1"}>{order.price} PLN</h3>
            </div>
            <h4 className={"text-muted"}>{order.producer}</h4>
            <p className={"mb-1"}>{order.description}</p>
            <p className={"mb-1"}>Ilość dostępna: {order.amount}</p>
          </li>
        ))}
      </div>
    </div>
  );
}
