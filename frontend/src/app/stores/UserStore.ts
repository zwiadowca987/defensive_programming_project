// UserStore.ts
import { makeAutoObservable, runInAction } from "mobx";
import { MFAformVal, User, UserFormVal } from "../models/User";
import httpAgent from "../utils/httpAgent";

export default class UserStore {
  user: User | null = null;
  qrCode: string = "default";

  constructor() {
    makeAutoObservable(this);
  }

  getQrCode = () => {
    return this.qrCode;
  };

  login = async (creds: UserFormVal) => {
    try {
      const user = await httpAgent.Account.login(creds);
      runInAction(() => {
        this.user = user;
      });
      alert("Zalogowano Pomyślnie");
    } catch (err) {
      throw err;
    }
  };

  register = async (creds: UserFormVal) => {
    try {
      const user = await httpAgent.Account.register(creds);
      runInAction(() => {
        this.user = user;
      });
      alert("Zarejestrowano pomyślnie");
    } catch (err) {
      throw err;
    }
  };

  MFAVerify = async (creds: MFAformVal) => {
    try {
      const user = await httpAgent.Account.MFAverify(creds);
      runInAction(() => {
        this.user = user;
      });
      alert("Zweryfikowano pomyślnie");
    } catch (err) {
      throw err;
    }
  };

  MFASetup = async () => {
    try {
      const totpData = await httpAgent.Account.MFAsetup();
      runInAction(() => {
        this.qrCode = totpData.qrCodeImage;
      });
      alert("Ustawiono Pomyślnie");
    } catch (err) {
      throw err;
    }
  };

  logout = () => {
    this.user = null;
  };
}
