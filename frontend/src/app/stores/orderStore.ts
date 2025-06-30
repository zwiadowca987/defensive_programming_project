import { makeAutoObservable } from "mobx";
import { PurchaseInfo } from "../models/Order";
import httpAgent from "../utils/httpAgent";

export default class OrderStore {

    orderRegistry = new Map<string, PurchaseInfo>()

    constructor() {
        makeAutoObservable(this)
    }

    get orderList(){
        return Array.from(this.orderRegistry.values())
    }

    loadOrders = async () => {
        try {

            const products = await httpAgent.Orders.findAll()
            
            products.array.forEach((order : PurchaseInfo) => {
                this.setOrders(order)               
            });

        } 
        catch(err) {

            console.log(err)

        }
    }

    setOrders = (order : PurchaseInfo) => {
        this.orderRegistry.set(order.id.toString(), order)
    }

    loadOrderById = async (id:number) => {
        try {
            const order = httpAgent.Orders.findById(id)

            return order
        }
        catch(err) {
            console.log(err)
        }
    }

    create = async () => {
        
    }
}