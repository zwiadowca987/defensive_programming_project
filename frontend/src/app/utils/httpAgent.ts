import axios, { AxiosResponse } from "axios";
import { User, UserFormVal } from "../models/User";

axios.defaults.baseURL = 'http://localhost:8080/api'

const responseBody = <T> (response : AxiosResponse) => response.data;

const requests = {
    get: <T> (url:string) => axios.get(url).then(responseBody),
    post: <T> (url:string, body: {}) => axios.post(url, body).then(responseBody),
    put: <T> (url:string, body: {}) => axios.put(url,body).then(responseBody),
    del: <T> (url:string) => axios.delete(url).then(responseBody),
}

const Account = {

    current: () => requests.get<User>('/users'),
    login: (user: UserFormVal) => requests.post<User>('/users/login', user),
    register: (user: UserFormVal) => requests.post<User>('/users/register', user)

}

const httpAgent = {

    Account

}

export default httpAgent;