import { makeAutoObservable } from "mobx";
import { Product } from "../models/Product";
import httpAgent from "../utils/httpAgent";
import axios from "axios";

export default class ProductStore {

    productRegistry = new Map<string, Product>();

    constructor() {
        makeAutoObservable(this);
    }

    get productList() {

        return Array.from(this.productRegistry.values())

    }

    loadProducts = async () => {

        try {

            const products = await httpAgent.Products.list()
            
            products.array.forEach((prod : Product) => {
                this.setProducts(prod)               
            });

        } 
        catch(err) {

            console.log(err)

        }
    } 

    loadProductByID = async (id:string) => {

        try {
            const product = httpAgent.Products.getByID(id)

            return product
        }
        catch(err) {
            console.log(err)
        }

    }

    editProduct = async (prod:Product) => {

        try {

            const product = await httpAgent.Products.update(prod)

        }
        catch(err){

            console.log(err)

        }

    }

    createProduct = async (prod:Product) => {

        try{
            const response = await httpAgent.Products.create(prod)
        }
        catch(err){

            console.log(err)

        }

    }

    setProducts = (prod : Product) => {

        //@ts-ignore
        this.productRegistry.set(prod.id?.toString(), prod)

    }
}