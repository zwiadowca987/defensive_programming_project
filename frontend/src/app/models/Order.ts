import { number, string } from "yup";

export interface OrderProductList {
    productId:number,
    quantity:number
}

export interface PurchaseProductInfo {

    productId:number,
    productName:string,
    quantity:number,
    price:number

}


export interface PurchaseInfo {

    id:number,
    clientId:number,
    date: Date,
    price:number,
    status:string,
    products:PurchaseProductInfo[]
}

export interface PurchaseCreation {
    clientId:number,
    date:Date | null,
    price:number,
    status:string,
    products:PurchaseProductInfo[]
}