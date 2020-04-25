package com.foodmobile.server.controllers;

import com.foodmobile.server.datamodels.Company;
import com.foodmobile.server.datamodels.CompanyDietaryInfo;
import com.foodmobile.server.datamodels.CompanyFinancial;
import com.foodmobile.server.datamodels.DataModelResponse;
import com.foodmobile.server.datamodels.FoodGenre;
import com.foodmobile.server.datamodels.Truck;
import com.foodmobile.server.datapersistence.DAO;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BusinessController {
    @PostMapping(path="/bus/createcompany", produces="application/json")
    public DataModelResponse<Company> createCompany(@RequestParam String name, @RequestParam String financialInfo, @RequestParam String dietaryInfo, @RequestParam String userGuid) {
        var company = Company.create(name, financialInfo, dietaryInfo, userGuid);
        try (var dao = new DAO()) {
            dao.create(company);
            return DataModelResponse.success(company);
        } catch (Exception ex) {
            return DataModelResponse.failure(ex.getMessage());
        }
    }
    
    @PostMapping(path="/bus/createcompanyfinancial", produces="application/json")
    public DataModelResponse<CompanyFinancial> createCompanyFinancial(@RequestParam String ein, @RequestParam String stateCode, @RequestParam String country) {
        if (country.equalsIgnoreCase("usa")) {
            if (stateCode.length() != 2) {
                return DataModelResponse.failure("Invalid state code");
            }
        } else {
            return DataModelResponse.failure("Currently only available in the United States");
        }
        if (ein.replaceAll("[^\\d]+", "").length() != 9) {
            return DataModelResponse.failure("Invalid EIN");
        }
        var companyInfo = CompanyFinancial.create(ein, stateCode, country);
        try (var dao = new DAO()) {
            dao.create(companyInfo);
            return DataModelResponse.success(companyInfo);
        } catch (Exception ex) {
            return DataModelResponse.failure(ex.getMessage());
        }
    }

    @PostMapping(path="/bus/createcompanydietary", produces="application/json")
    public DataModelResponse<CompanyDietaryInfo> createCompanyDietary(@RequestParam boolean hasGMO, @RequestParam boolean hasGF, @RequestParam boolean onlyGMO, @RequestParam boolean onlyGF, @RequestParam boolean usesNuts, @RequestParam boolean veganOptions, @RequestParam boolean onlyVegan, @RequestParam String genreId) {
        if (onlyGF && !hasGF) {
            return DataModelResponse.failure("Cannot be only GF without having GF options");
        } else if (onlyGMO && !hasGMO) {
            return DataModelResponse.failure("Cannot be only GMO free without having GMO free options");
        } else if (onlyVegan && !veganOptions) {
            return DataModelResponse.failure("Cannot be vegan only without having vegan options");
        }
        var dietary = CompanyDietaryInfo.create(hasGMO, hasGF, onlyGMO, onlyGF, usesNuts, veganOptions, onlyVegan, genreId);
        try (var dao = new DAO()) {
            if (!dao.byGuid(genreId, FoodGenre.class).isPresent()) {
                return DataModelResponse.failure("No such food genre");
            }
            dao.create(dietary);
            return DataModelResponse.success(dietary);
        } catch (Exception ex) {
            return DataModelResponse.failure(ex.getMessage());
        }
    }

    @PostMapping(path="/bus/createfoodgenre", produces="application/json")
    public DataModelResponse<FoodGenre> createFoodGenre(@RequestParam String genre) {
        var genreObj = FoodGenre.create(genre);
        try (var dao = new DAO()) {
            dao.create(genreObj);
            return DataModelResponse.success(genreObj);
        } catch (Exception ex) {
            return DataModelResponse.failure(ex.getMessage());
        }
    }

    @PostMapping(path="/bus/updatecompany", produces="application/json")
    public DataModelResponse<Company> updateCompany(@RequestParam String guid, @RequestParam String name, @RequestParam String financialInfo, @RequestParam String dietaryInfo) {
        try (var dao = new DAO()) {
            var companyOpt = dao.byGuid(guid, Company.class);
            if (companyOpt.isEmpty()) {
                return DataModelResponse.failure("No such company");
            }
            var company = companyOpt.get();
            company.name = name;
            company.financialInformationGuid = financialInfo;
            company.dietaryInformationGuid = dietaryInfo;
            dao.update(company);
            return DataModelResponse.success(company);
        } catch (Exception ex) {
            return DataModelResponse.failure(ex.getMessage());
        }
    }

    @PostMapping(path="/bus/updatecompanydietary", produces="application/json")
    public DataModelResponse<CompanyDietaryInfo> updateCompanyDietary(@RequestParam String guid, @RequestParam boolean hasGMO, @RequestParam boolean hasGF, @RequestParam boolean onlyGMO, @RequestParam boolean onlyGF, @RequestParam boolean usesNuts, @RequestParam boolean veganOptions, @RequestParam boolean onlyVegan, @RequestParam String genreId, @RequestParam String companyId) {
        return DataModelResponse.failure("Not yet implemented");
    }

    @PostMapping(path="/bus/createtruck", produces="application/json")
    public DataModelResponse<Truck> createTruck(@RequestParam String companyGuid, @RequestParam String userGuid) {
        var truck = Truck.create(companyGuid, userGuid);
        try (var dao = new DAO()) {
            dao.create(truck);
            return DataModelResponse.success(truck);
        } catch (Exception ex) {
            return DataModelResponse.failure(ex.getMessage());
        }
    }

    @PostMapping(path="/bus/gettruckforuser", produces="application/json")
    public DataModelResponse<Truck> truckForUser(@RequestParam String userGuid) {
        return null;
    }

}
