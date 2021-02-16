import { User } from "../models/User";
import { Role } from "../utils/Role";

export const USERS: User[] = [
    new User("admin.email@email.email", "psw", Role.Admin)
];