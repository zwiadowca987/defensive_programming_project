import { createContext, useContext } from "react"
import UserStore from "./UserStore";
import ProductStore from "./productStore";
import OrderStore from "./orderStore";

export interface Store {

    userStore:UserStore
    productStore:ProductStore
    orderStore:OrderStore

}

export const store:Store = {

    userStore:new UserStore(),
    productStore:new ProductStore(),
    orderStore:new OrderStore()

}

export const StoreContext = createContext(store);

export function useStore() {

    return useContext(StoreContext);

}