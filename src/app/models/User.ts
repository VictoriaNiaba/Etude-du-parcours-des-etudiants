import { Role } from "../utils/Role";

export class User {
    email: string;
    password: string;
    role: Role;

    constructor(email: string, password: string, role: Role) {
        this.email = email;
        this.password = password;
        this.role = role;
    }
    
}