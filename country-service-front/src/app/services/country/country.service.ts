import { Injectable } from '@angular/core';
import { Countries } from 'src/app/types/Countries';
import { Country } from 'src/app/types/Country';

@Injectable({
  providedIn: 'root'
})
export class CountryService {

  constructor() {
  }


  getAllCountries(): Country[] {

    // Dummy data to return for now
    const countryData: Countries = {
      "countries": [
        { name: "Afghanistan", country_code: "AF" },
        { name: "Albania", country_code: "AL" },
        { name: "Algeria", country_code: "DZ" },
        { name: "Andorra", country_code: "AD" },
        { name: "Angola", country_code: "AO" },
        { name: "Anguilla", country_code: "AI" },
        { name: "Antigua and Barbuda", country_code: "AG" },
        { name: "Argentina", country_code: "AR" },
        { name: "Armenia", country_code: "AM" },
        { name: "Aruba", country_code: "AW" },
        { name: "Australia", country_code: "AU" },
        { name: "Austria", country_code: "AT" },
        { name: "Azerbaijan", country_code: "AZ" },
        { name: "Bahamas", country_code: "BS" },
        { name: "Bahrain", country_code: "BH" },
        { name: "Bangladesh", country_code: "BD" },
        { name: "Barbados", country_code: "BB" },
        { name: "Belarus", country_code: "BY" },
        { name: "Belgium", country_code: "BE" },
        { name: "Belize", country_code: "BZ" },
        { name: "Benin", country_code: "BJ" },
        { name: "Bermuda", country_code: "BM" },
        { name: "Bhutan", country_code: "BT" },
        { name: "Bosnia and Herzegovina", country_code: "BA" },
        { name: "Botswana", country_code: "BW" },
        { name: "Bouvet Island", country_code: "BV" },
        { name: "Brazil", country_code: "BR" },
        { name: "British Indian Ocean Territory", country_code: "IO" },
        { name: "Brunei", country_code: "BN" },
        { name: "Bulgaria", country_code: "BG" },
        { name: "Burkina Faso", country_code: "BF" },
        { name: "Burundi", country_code: "BI" },
        { name: "Cambodia", country_code: "KH" },
        { name: "Cameroon", country_code: "CM" },
        { name: "Canada", country_code: "CA" },
        { name: "Cape Verde", country_code: "CV" },
        { name: "Cayman Islands", country_code: "KY" },
        { name: "Central African Republic", country_code: "CF" },
        { name: "Chad", country_code: "TD" },
        { name: "Chile", country_code: "CL" },
        { name: "China", country_code: "CN" },
        { name: "Christmas Island", country_code: "CX" },
        { name: "Cocos (Keeling) Islands", country_code: "CC" },
        { name: "Colombia", country_code: "CO" },
        { name: "Comoros", country_code: "KM" },
        { name: "Congo", country_code: "CG" },
        { name: "Cook Islands", country_code: "CK" },
        { name: "Costa Rica", country_code: "CR" },
        { name: "Croatia", country_code: "HR" },
        { name: "Cuba", country_code: "CU" },
        { name: "Cyprus", country_code: "CY" },
        { name: "Czech Republic", country_code: "CZ" },
        { name: "Denmark", country_code: "DK" },
        { name: "Djibouti", country_code: "DJ" },
        { name: "Dominica", country_code: "DM" },
        { name: "Dominican Republic", country_code: "DO" },
        { name: "Ecuador", country_code: "EC" },
        { name: "Egypt", country_code: "EG" },
        { name: "El Salvador", country_code: "SV" },
        { name: "Equatorial Guinea", country_code: "GQ" },
        { name: "Eritrea", country_code: "ER" },
        { name: "Estonia", country_code: "EE" },
        { name: "Ethiopia", country_code: "ET" },
        { name: "Falkland Islands", country_code: "FK" },
        { name: "Faroe Islands", country_code: "FO" },
        { name: "Fiji", country_code: "FJ" },
        { name: "Finland", country_code: "FI" },
        { name: "France", country_code: "FR" },
        { name: "French Polynesia", country_code: "PF" },
        { name: "Gabon", country_code: "GA" },
        { name: "Gambia", country_code: "GM" },
        { name: "Georgia", country_code: "GE" },
        { name: "Germany", country_code: "DE" },
        { name: "Ghana", country_code: "GH" },
        { name: "Gibraltar", country_code: "GI" },
        { name: "Greece", country_code: "GR" },
        { name: "Greenland", country_code: "GL" },
        { name: "Grenada", country_code: "GD" },
        { name: "Guadeloupe", country_code: "GP" },
        { name: "Guam", country_code: "GU" },
        { name: "Guatemala", country_code: "GT" },
        { name: "Guernsey", country_code: "GG" },
        { name: "Guinea", country_code: "GN" },
        { name: "Guinea-Bissau", country_code: "GW" },
        { name: "Guyana", country_code: "GY" },
        { name: "Haiti", country_code: "HT" },
      ]
    };

    return countryData.countries;
  
  }


}
