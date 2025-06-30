import { makeAutoObservable } from "mobx";
import { PurchaseCreation, PurchaseInfo } from "../models/Order";
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
            const order = await httpAgent.Orders.findById(id)

            return order
        }
        catch(err) {
            console.log(err)
        }
    }

    create = async (order : PurchaseCreation) => {
        try {
            const newOrder = await httpAgent.Orders.create(order)

            return newOrder
        }
        catch(err) {
            console.log(err)
        }
    }

    update = async (order : PurchaseCreation, id : number) => {
        try {
            const newOrder = await httpAgent.Orders.update(order , id)

            return newOrder
        }
        catch(err) {
            console.log(err)
        }
    }
}