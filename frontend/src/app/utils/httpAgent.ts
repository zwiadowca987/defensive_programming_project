import axios, { AxiosResponse } from "axios";
import { MFAformVal, User, UserFormVal } from "../models/User";
import { TotpSetupResponse } from "../models/ToTpSetupResponse";
import { Product, ProductDTO } from "../models/Product";
import { OrderProductList, PurchaseCreation } from "../models/Order";


const axiosAgent =
    axios.create(
        {
            withCredentials:true,
            baseURL:'http://localhost:8080/api',
            validateStatus:(status) => status >= 200 && status < 400
        }
    )

const responseBody = <T> (response : AxiosResponse) => response.data;

const requests = {
    get: <T> (url:string) => axiosAgent.get(url).then(responseBody),
    post: <T> (url:string, body: {}) => axiosAgent.post(url, body).then(responseBody),
    put: <T> (url:string, body: {}) => axiosAgent.put(url, body).then(responseBody),
    del: <T> (url:string) => axiosAgent.delete(url).then(responseBody),
    delBody: <T> (url:string, body: {}) => axiosAgent.delete(url, body).then(responseBody)
}

const Account = {

    current: () => requests.get<User>('/users'),
    login: (user: UserFormVal) => requests.post<User>('/users/login', user),
    register: (user: UserFormVal) => requests.post<User>('/users/register', user),
    MFAverify: (user: MFAformVal) => requests.post<User>('/users/verify', user),
    MFAsetup: () => requests.post<TotpSetupResponse>('/users/setup', {})

}

const Products = {

    //wszystkie produkty
    list: () => requests.get('/products'),
    //pojedyńczy produkt
    getByID: ( id: string ) => requests.get(`/products/${id}`),
    //tworzenie produktu
    create: ( prod : Product ) => requests.post<void>('/products', prod),
    //aktualizacja produktu
    update: ( prod : Product ) => requests.put<void>(`/products/${prod.id}`, prod),
    //usuń produkt
    delete: (id : string) => requests.del<void>(`/products/${id}`)

}

const Orders = {

    findAll: () => requests.get('/orders'),
    findById: (id:number) => requests.get(`/orders/${id}`),
    create:(purchase:PurchaseCreation)=>requests.post<void>('/orders', purchase),
    update:(purchase:PurchaseCreation, id:number) => requests.put<void>(`/orders/${id}`, purchase),
    delete:(id:number) => requests.del<void>(`/orders/${id}`),
    addProduct:(order:OrderProductList, id:number) => requests.post(`/orders/${id}/products`, order),
    updateProduct:(order:OrderProductList, id:number) => requests.put<void>(`/orders/${id}/products`, order),
    deleteProduct:(order:OrderProductList, id:number) => requests.delBody<void>(`/orders/${id}/products`, order),

}

const httpAgent = {

    Account,
    Products,
    Orders

}

export default httpAgent;