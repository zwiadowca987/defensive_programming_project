"use client";
import { useParams } from "next/navigation";
import Link from "next/link";
import { observer } from "mobx-react-lite";
import { useStore } from "@/app/stores/stores";

// TODO: pobieranie produktu po id;


export default observer(function EditOrder() {
  
  const {productStore} = useStore();
  const params = useParams();
  const id = Number(params.id);
  const product = productStore.productRegistry.get(id.toString())


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

          <Link className={"btn"} href={"/products"} onClick={(e) => {
            if(product)
              productStore.editProduct(product)
          }}>
            <i className={"bi bi-save"}></i> Zapisz
          </Link>

          <Link className={"btn"} href={"/products/"}>
            <i className={"bi bi-arrow-left-circle"}></i> Powrót
          </Link>
        </form>
      </div>
    </div>
  );
})
