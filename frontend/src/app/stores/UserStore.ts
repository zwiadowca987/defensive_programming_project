import { makeAutoObservable, runInAction } from "mobx";
import { MFAformVal, User, UserFormVal } from "../models/User";
import httpAgent from "../utils/httpAgent";
import { redirect } from "next/navigation";

export default class UserStore {

    user:User | null = null;
    qrCode:String = 'default';

    constructor() {

        makeAutoObservable(this);

    }

    
    getQrCode = () => {

        return this.qrCode

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
            redirect('/product')

        } catch(err){throw err}

    }


    MFAVerify = async (creds:MFAformVal) => {

        try{

            const user = await httpAgent.Account.MFAverify(creds)

            console.log(user)
            
        } catch(err){throw err}
    }

    MFASetup = async () => {

        try{

            const totpData = await httpAgent.Account.MFAsetup();

            console.log(totpData);
            
            this.qrCode = totpData.qrCodeImage;

        } catch(err){throw err}

    }

    logout = () => this.user = null;

}