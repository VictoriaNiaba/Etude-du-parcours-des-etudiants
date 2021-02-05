import { Role } from "../utils/Role";

export class User {
    id: number;
    lastName: string;
    firstName: string;
    email: string;
    password: string;
    role: Role;

    constructor(id: number, lastName: string, firstName: string, email: string, password: string, role: Role) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.email = email;
        this.password = password;
        this.role = role;
    }
    
}