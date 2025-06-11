export interface User {
  userName: string;
}

export interface UserFormVal {
  email: string;
  password: string;
  totpCode?: string;
}

export interface MFAformVal {
  username: String;
  totpCode: String;
}
