package com.foodmobile.server.controllers;

import com.foodmobile.server.datamodels.Company;
import com.foodmobile.server.datamodels.CompanyDietaryInfo;
import com.foodmobile.server.datamodels.CompanyFinancial;
import com.foodmobile.server.datamodels.DataModelResponse;
import com.foodmobile.server.datapersistence.DAO;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TruckController {

    @PostMapping(path="/truck/createcompany", produces="application/json")
    public DataModelResponse<Company> createCompany(@RequestParam String name, @RequestParam String financialInfo) {
        var company = Company.create(name, financialInfo);
        try (var dao = new DAO()) {
            dao.create(company);
            return DataModelResponse.success(company);
        } catch (Exception ex) {
            return DataModelResponse.failure(ex.getMessage());
        }
    }
    
    @PostMapping(path="/truck/createcompanyfinancial", produces="application/json")
    public DataModelResponse<CompanyFinancial> createCompanyFinancial(@RequestParam String ein, @RequestParam String stateCode, @RequestParam String country) {
        var companyInfo = CompanyFinancial.create(ein, stateCode, country);
        try (var dao = new DAO()) {
            dao.create(companyInfo);
            return DataModelResponse.success(companyInfo);
        } catch (Exception ex) {
            return DataModelResponse.failure(ex.getMessage());
        }
    }

    @PostMapping(path="/truck/createcompanydietary", produces="application/json")
    public DataModelResponse<CompanyDietaryInfo> createCompanyDietary() {
        return null;
    }
}