import { observer } from "mobx-react-lite";
import Link from "next/link";
import { useEffect } from "react";
import { useStore } from "../stores/stores";

// TODO: download orders from the backend

export default observer( function Orders() {

    const {orderStore} = useStore()

    useEffect(() => {
      if (orderStore.orderRegistry.size <= 1) orderStore.loadOrders();
    }, [orderStore.orderRegistry, orderStore.orderRegistry.size])

  return (
    <div className={"container"}>
      <div className={"text-center"}>
        <h1 className={"display-2"}>Zamówienia</h1>
      </div>
      <div className={"m-5 list-group"}>
        <Link className={"btn d-flex text-end"} href={"/orders/create/"}>
          Dodaj Nowe Zamówienie
        </Link>

        {orderStore.orderList.map((order) => (
          <li className={"list-group-item"} key={order.id}>
            <p className={"mb-1"}>{order.clientId}</p>
            <p className={"mb-1"}>{order.price} PLN</p>
            <p className={"mb-1"}>{order.date.toISOString()}</p>
            <p className={"mb-1"}>{order.status}</p>
            <ul className={"list-group"}>
              {order.products.map((product) => (
                <li className={"list-group-item"} key={product.productId}>
                  {product.productName} - {product.quantity} szt. - {product.price}{" "}
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
)