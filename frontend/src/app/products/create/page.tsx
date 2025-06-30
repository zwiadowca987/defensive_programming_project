"use client";
import { useParams } from "next/navigation";
import Link from "next/link";
import ProductStore from "@/app/stores/productStore";
import { useEffect } from "react";
import { observer } from "mobx-react-lite";
import { useStore } from "@/app/stores/stores";
import { Product } from "@/app/models/Product";

// TODO: pobieranie produktu po id

const product:Product = {

    productName:"",
    price: 0,
    description: "",
    producer: ""

}

export default observer(function EditOrder() {

  const params = useParams();
  const id = String(params.id)
  const {productStore} = useStore()

  return (
    <div className={"container"}>
      <div className={"text-center"}>
        <h1 className={"display-2"}>Dodaj Produkt</h1>
      </div>
      <div className={"m-5 list-group"}>
        <form className={"list-group-item"}>
          <div className={"mb-3"}>
            <label>Nazwa</label>
            <input
              className={"form-control"}
              type={"text"}
              value={product.productName}
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

          <Link className={"btn"} href={`/products`} onClick={(e)=>{
            productStore.createProduct(product)
          }}>
            <i className={"bi bi-save"}></i> Zapisz
          </Link>

          <Link className={"btn"} href={"/products/"} >
            <i className={"bi bi-arrow-left-circle"}></i> Powrót
          </Link>
        </form>
      </div>
    </div>
  );
})
