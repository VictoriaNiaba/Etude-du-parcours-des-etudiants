import { User } from "../models/User";
import { Role } from "../utils/Role";

export const USERS: User[] = [
    new User(1, "admin", "ADMIN", "admin.email@email.email", "psw", Role.Admin)
];