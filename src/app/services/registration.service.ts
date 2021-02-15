import { Injectable } from '@angular/core';


/**/ 
export class Registration {
  ia_id: string;
  year: number;
  
  constructor(ia_id:string, year:number) {
      this.ia_id = ia_id;
      this.year = year;
  };
}
/**/

@Injectable({
  providedIn: 'root'
})
export class RegistrationService {

  REGISTRATION: Registration[] = [
    new Registration("0",2011),
    new Registration("1",2011),
    new Registration("2",2011),
    new Registration("3",2011),
    new Registration("4",2011),
    new Registration("4",2011),
    new Registration("4",2011),
    new Registration("4",2011),
    new Registration("4",2011),
    new Registration("4",2011),
    new Registration("4",2011),
    new Registration("4",2011),
    new Registration("4",2011),
    new Registration("4",2011),
    new Registration("4",2011),
    new Registration("4",2011),
    new Registration("4",2011),
    new Registration("4",2011),
    new Registration("4",2011),
    new Registration("4",2011),
    new Registration("4",2011),
    new Registration("4",2011),
    new Registration("4",2011),
    new Registration("4",2011),
    new Registration("4",2011),
    new Registration("4",2011),
    new Registration("5",2012),
    new Registration("6",2012),
    new Registration("7",2012),
    new Registration("7",2012),
    new Registration("7",2012),
    new Registration("7",2012),
    new Registration("7",2012),
    new Registration("7",2012),
    new Registration("7",2012),
    new Registration("7",2012),
    new Registration("7",2012),
    new Registration("7",2012),
    new Registration("7",2012),
    new Registration("7",2012),
    new Registration("7",2012),
    new Registration("7",2012),
    new Registration("7",2012),
    new Registration("7",2012),
    new Registration("7",2012),
    new Registration("7",2012),
    new Registration("7",2012),
    new Registration("7",2012),
    new Registration("7",2012),
    new Registration("7",2012),
    new Registration("7",2012),
    new Registration("7",2012),
    new Registration("7",2012),
    new Registration("7",2012),
    new Registration("7",2012),
    new Registration("7",2012),
    new Registration("7",2012),
    new Registration("7",2012),
    new Registration("7",2012),
    new Registration("7",2012),
    new Registration("7",2012),
    new Registration("8",2012),
    new Registration("9",2012),
    new Registration("10",2013),
    new Registration("11",2013),
    new Registration("12",2013),
    new Registration("12",2013),
    new Registration("12",2013),
    new Registration("12",2013),
    new Registration("12",2013),
    new Registration("12",2013),
    new Registration("12",2013),
    new Registration("12",2013),
    new Registration("12",2013),
    new Registration("12",2013),
    new Registration("12",2013),
    new Registration("12",2013),
    new Registration("12",2013),
    new Registration("12",2013),
    new Registration("12",2013),
    new Registration("12",2013),
    new Registration("12",2013),
    new Registration("12",2013),
    new Registration("12",2013),
    new Registration("12",2013),
    new Registration("12",2013),
    new Registration("12",2013),
    new Registration("12",2013),
    new Registration("12",2014),
    new Registration("12",2015),
    new Registration("12",2016),
    new Registration("12",2017),
    new Registration("12",2018),
    new Registration("12",2013),
    new Registration("13",2013),
    new Registration("14",2013),
    new Registration("15",2013)
  ];

  constructor() { }

  getAllRegistrations():number{
    return this.REGISTRATION.length;
  }

  getNumberOfRegistrationsByYear(year: number):number{
    let count = 0;
    this.REGISTRATION.forEach(registration => {
      if(registration.year == year) {
        count++;
      }      
    });
    return count;
  }

  getTimestampOfRegistrationByYear(year: number):Date{
    return new Date();
  }

  getAllYearAvailable() {
    let years = new Array<number>();
    this.REGISTRATION.forEach(registration => {
      if(!years.includes(registration.year)) {
        years.push(registration.year);
      }
    });
    return years;
  }
}
