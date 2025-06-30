import Link from "next/link";
import { useEffect } from "react";
import { useStore } from "../stores/stores";
import { observer } from "mobx-react-lite";

// TODO: download products from the backend
// TODO: prawdopodobnie nie ma zliczania ilości produktów

export default observer (function Products() {

  const {productStore} = useStore()

  useEffect(() => {
      if (productStore.productRegistry.size <= 1) productStore.loadProducts();
    }, [productStore.productRegistry, productStore.productRegistry.size])

  return (
    <div className={"container"}>
      <div className={"text-center"}>
        <h1 className={"display-2"}>Katalog Produktów</h1>
      </div>
      <div className={"m-5 list-group"}>
        <Link className={"btn d-flex text-end"} href={"/products/create/"}>
          Dodaj Nowy Produkt
        </Link>

        {productStore.productList.map((product) => (
          <div className={"list-group-item"} key={product.id}>
            <p className={"mb-1"}>{product.productName}</p>
            <p className={"mb-1"}>{product.price} PLN</p>
            <p className={"mb-1"}>{product.description}</p>
            <p className={"mb-1"}>{product.producer}</p>

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
})
