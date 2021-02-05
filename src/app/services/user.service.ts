import { Injectable } from '@angular/core';
import { USERS } from '../mock/USERS';
import { User } from '../models/User';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  users: User[]; //mock

  constructor() {
    console.log("User service creation");
    this.users = USERS;
  }

  getAll() {
    return this.users;
  }

  getById(id: number) {
    let user = this.users.find(usr => usr.id === id);
    if(!user) {
      console.info(`No user found with id ${id}`);
      return null;
    } else {
      return user;
    }
  }

  getByEmail(email: string) {
    let user = this.users.find(usr => usr.email === email);
    if(!user) {
      console.error(`No user found with email ${email}`);
      return null;
    } else {
      return user;
    }
  }

  getNotUsedId() { //to fake...
    let i = 0;
    while (true) {
      if(this.getById(i) == null) {
        return i;
      }
      i++;
    }
  }

  add(user: User) {
    if(this.getById(user.id) == null) {
      this.users.push(user);
      return true;
    }
    return false;
  }

  update(user: User) { //sommaire
    let user_tmp = this.getById(user.id);
    if(user_tmp != null) {
        this.users[this.users.indexOf(user_tmp)] = user;
      return [true, `User updated`];
    }
    return [false, "This user doesn't exist, update failed"];
  }

  delete(user: User) {
    let user_tmp = this.getById(user.id);
    if(user_tmp != null) {
      this.users = this.users.filter(obj => obj !== user);
      return [true, `User deleted`];
    }
    return [false, "This user doesn't exist, deletion failed"];
  }
}