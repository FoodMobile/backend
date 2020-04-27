[Back](../index.html)


#### Post /login
---
- username : String
- password : String

---
Returns: [LoginResponse](../com/foodmobile/server/datamodels/LoginResponse.html)
#### Post /auth/userinfo
---
- username : String

---
[DataModelResponse](../com/foodmobile/server/datamodels/DataModelResponse.html) [User](../com/foodmobile/server/datamodels/User.html)
#### Post /resetpassword
---
- username : String
- oldPassword : String
- newPassword : String

---
[SimpleStatusResponse](../com/foodmobile/server/datamodels/SimpleStatusResponse.html)
#### Post /register/normal
---
- name : String
- username : String
- password : String
- email : String

---
[SimpleStatusResponse](../com/foodmobile/server/datamodels/SimpleStatusResponse.html)
#### Post /profile/update
---
- token : String

---
[SimpleStatusResponse](../com/foodmobile/server/datamodels/SimpleStatusResponse.html)
#### Post /nearbytrucks
---
- lat : double
- lon : double
---

#### Post /offline
---
- token : String
---
[SimpleStatusResponse](../com/foodmobile/server/datamodels/SimpleStatusResponse.html)
#### Post /bus/createcompany
---
- name : String
- financialInfo : String
- dietaryInfo : String
- userGuid : String

---
[DataModelResponse](../com/foodmobile/server/datamodels/DataModelResponse.html) [Company](../com/foodmobile/server/datamodels/Company.html)
#### Post /bus/companyinfobyid
---
- guid : String

---
[DataModelResponse](../com/foodmobile/server/datamodels/DataModelResponse.html) [Company](../com/foodmobile/server/datamodels/Company.html)
#### Post /bus/createcompanyfinancial
---
- ein : String
- stateCode : String
- country : String

---
[DataModelResponse](../com/foodmobile/server/datamodels/DataModelResponse.html) [CompanyFinancial](../com/foodmobile/server/datamodels/CompanyFinancial.html)
#### Post /bus/createcompanydietary
---
- hasGMO : boolean
- hasGF : boolean
- onlyGMO : boolean
- onlyGF : boolean
- usesNuts : boolean
- veganOptions : boolean
- onlyVegan : boolean
- genreId : String

---
[DataModelResponse](../com/foodmobile/server/datamodels/DataModelResponse.html) [CompanyDietaryInfo](../com/foodmobile/server/datamodels/CompanyDietaryInfo.html)
#### Post /bus/createfoodgenre
---
- genre : String

---
[DataModelResponse](../com/foodmobile/server/datamodels/DataModelResponse.html) [FoodGenre](../com/foodmobile/server/datamodels/FoodGenre.html)
#### Post /bus/joincompany
---
- companyGuid : String
- userGuid : String

---
[DataModelResponse](../com/foodmobile/server/datamodels/DataModelResponse.html) [User](../com/foodmobile/server/datamodels/User.html)
#### Post /bus/updatecompany
---
- guid : String
- name : String
- financialInfo : String
- dietaryInfo : String

---
[DataModelResponse](../com/foodmobile/server/datamodels/DataModelResponse.html) [Company](../com/foodmobile/server/datamodels/Company.html)
#### Post /bus/updatecompanydietary
---
- guid : String
- hasGMO : boolean
- hasGF : boolean
- onlyGMO : boolean
- onlyGF : boolean
- usesNuts : boolean
- veganOptions : boolean
- onlyVegan : boolean
- genreId : String
- companyId : String

---
[DataModelResponse](../com/foodmobile/server/datamodels/DataModelResponse.html) [CompanyDietaryInfo](../com/foodmobile/server/datamodels/CompanyDietaryInfo.html)
#### Post /bus/createtruck
---
- companyGuid : String
- userGuid : String

---
[DataModelResponse](../com/foodmobile/server/datamodels/DataModelResponse.html) [Truck](../com/foodmobile/server/datamodels/Truck.html)
#### Post /bus/getloggedintruck
---
- token : String

---
[DataModelResponse](../com/foodmobile/server/datamodels/DataModelResponse.html) [Truck](../com/foodmobile/server/datamodels/Truck.html)
#### Post /bus/gettruckforusername
---
- username : String

---
[DataModelResponse](../com/foodmobile/server/datamodels/DataModelResponse.html) [Truck](../com/foodmobile/server/datamodels/Truck.html)
#### Post /bus/gettruckforuser
---
- userGuid : String

---
[DataModelResponse](../com/foodmobile/server/datamodels/DataModelResponse.html) [Truck](../com/foodmobile/server/datamodels/Truck.html)
#### Post /bus/createcompany
---
- name : String
- financialInfo : String
- dietaryInfo : String
- userGuid : String

---
[DataModelResponse](../com/foodmobile/server/datamodels/DataModelResponse.html) [Company](../com/foodmobile/server/datamodels/Company.html)
#### Post /bus/companyinfobyid
---
- guid : String

---
[DataModelResponse](../com/foodmobile/server/datamodels/DataModelResponse.html) [Company](../com/foodmobile/server/datamodels/Company.html)
#### Post /bus/createcompanyfinancial
---
- ein : String
- stateCode : String
- country : String

---
[DataModelResponse](../com/foodmobile/server/datamodels/DataModelResponse.html) [CompanyFinancial](../com/foodmobile/server/datamodels/CompanyFinancial.html)
#### Post /bus/createcompanydietary
---
- hasGMO : boolean
- hasGF : boolean
- onlyGMO : boolean
- onlyGF : boolean
- usesNuts : boolean
- veganOptions : boolean
- onlyVegan : boolean
- genreId : String

---
[DataModelResponse](../com/foodmobile/server/datamodels/DataModelResponse.html) [CompanyDietaryInfo](../com/foodmobile/server/datamodels/CompanyDietaryInfo.html)
#### Post /bus/createfoodgenre
---
- genre : String

---
[DataModelResponse](../com/foodmobile/server/datamodels/DataModelResponse.html) [FoodGenre](../com/foodmobile/server/datamodels/FoodGenre.html)
#### Post /bus/joincompany
---
- companyGuid : String
- userGuid : String

---
[DataModelResponse](../com/foodmobile/server/datamodels/DataModelResponse.html) [User](../com/foodmobile/server/datamodels/User.html)
#### Post /bus/updatecompany
---
- guid : String
- name : String
- financialInfo : String
- dietaryInfo : String

---
[DataModelResponse](../com/foodmobile/server/datamodels/DataModelResponse.html) [Company](../com/foodmobile/server/datamodels/Company.html)
#### Post /bus/updatecompanydietary
---
- guid : String
- hasGMO : boolean
- hasGF : boolean
- onlyGMO : boolean
- onlyGF : boolean
- usesNuts : boolean
- veganOptions : boolean
- onlyVegan : boolean
- genreId : String
- companyId : String

---
[DataModelResponse](../com/foodmobile/server/datamodels/DataModelResponse.html) [CompanyDietaryInfo](../com/foodmobile/server/datamodels/CompanyDietaryInfo.html)
#### Post /bus/createtruck
---
- companyGuid : String
- userGuid : String

---
[DataModelResponse](../com/foodmobile/server/datamodels/DataModelResponse.html) [Truck](../com/foodmobile/server/datamodels/Truck.html)
#### Post /bus/getloggedintruck
---
- token : String

---
[DataModelResponse](../com/foodmobile/server/datamodels/DataModelResponse.html) [Truck](../com/foodmobile/server/datamodels/Truck.html)
#### Post /bus/gettruckforusername
---
- username : String

---
[DataModelResponse](../com/foodmobile/server/datamodels/DataModelResponse.html) [Truck](../com/foodmobile/server/datamodels/Truck.html)
#### Post /bus/gettruckforuser
---
- userGuid : String

---
[DataModelResponse](../com/foodmobile/server/datamodels/DataModelResponse.html) [Truck](../com/foodmobile/server/datamodels/Truck.html)
#### Post /menu/itemsfortruck
---
- truckGuid : String

---
[MultiDataModelResponse](../com/foodmobile/server/datamodels/MultiDataModelResponse.html) [MenuItem](../com/foodmobile/server/datamodels/MenuItem.html)
#### Post /menu/itemsforcompany
---
- companyGuid : String

---
[MultiDataModelResponse](../com/foodmobile/server/datamodels/MultiDataModelResponse.html) [MenuItem](../com/foodmobile/server/datamodels/MenuItem.html)
#### Post /menu/deleteitem
---
- guid : String

---
[SimpleStatusResponse](../com/foodmobile/server/datamodels/SimpleStatusResponse.html)

