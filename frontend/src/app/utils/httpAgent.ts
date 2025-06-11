import axios, { AxiosResponse } from "axios";
import { MFAformVal, User, UserFormVal } from "../models/User";
import { TotpSetupResponse } from "../models/ToTpSetupResponse";


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
}

const Account = {

    current: () => requests.get<User>('/users'),
    login: (user: UserFormVal) => requests.post<User>('/users/login', user),
    register: (user: UserFormVal) => requests.post<User>('/users/register', user),
    MFAverify: (user: MFAformVal) => requests.post<User>('/users/verify', user),
    MFAsetup: () => requests.post<TotpSetupResponse>('/users/setup', {})

}

const httpAgent = {

    Account

}

export default httpAgent;