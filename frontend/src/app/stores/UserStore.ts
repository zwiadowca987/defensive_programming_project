import { makeAutoObservable, runInAction } from "mobx";
import { User, UserFormVal } from "../models/User";
import httpAgent from "../utils/httpAgent";
import { redirect } from "next/navigation";

export default class UserStore {

    user:User | null = null;


    constructor() {

        makeAutoObservable(this);

    }


    login = async (creds:UserFormVal) => {
     
        try{
            const user = await httpAgent.Account.login(creds);
            
            runInAction(() => this.user = user);
            console.log(user);
            redirect('/products');
            
        } catch(err){throw err}
    }

    register = async (creds:UserFormVal) => {

        try{
            const user = await httpAgent.Account.register(creds);
            
            runInAction(() => this.user =user);
            console.log(user);
            redirect('product')

        } catch(err){throw err}

    }

    logout = () => this.user = null;

}