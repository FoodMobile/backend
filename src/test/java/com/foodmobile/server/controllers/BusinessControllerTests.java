package com.foodmobile.server.controllers;

import com.foodmobile.server.datamodels.FoodGenre;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class BusinessControllerTests {
    private final BusinessController businessController = new BusinessController();

    private final AuthController authController = new AuthController();

    @Test
    public void testCreateCompanyFinancial() {
        var response = businessController.createCompanyFinancial("00-0000000", "nc", "usa");
        assertTrue(response.success);
    }

    @Test
    public void testCreateCompanyFinancialBadState() {
        var response = businessController.createCompanyFinancial("00-0000000", "bad", "usa");
        assertFalse(response.success);
    }

    @Test
    public void testCreateCompanyFinancialUnsupportedCountry() {
        var response = businessController.createCompanyFinancial("00-0000000", "", "unsupported");
        assertFalse(response.success);
    }

    @Test
    public void testCreateCompanyFinancialBadEin() {
        var response = businessController.createCompanyFinancial("00-000000", "ny", "usa");
        assertFalse(response.success);
    }

    @Test
    public void testCreateCompanyDietary() {
        var genreResponse = businessController.createFoodGenre(UUID.randomUUID().toString());
        assertTrue(genreResponse.success);
        var foodGenre = genreResponse.data.guid;
        var response = businessController.createCompanyDietary(false, false, false, false, false, false, false, foodGenre);
        assertTrue(response.success);
    }

    @Test
    public void testCreateCompanyDietaryInvalidGMO() {
        var genreResponse = businessController.createFoodGenre(UUID.randomUUID().toString());
        assertTrue(genreResponse.success);
        var foodGenre = genreResponse.data.guid;
        var response = businessController.createCompanyDietary(false, false, true, false, false, false, false, foodGenre);
        assertFalse(response.success);
    }

    @Test
    public void testCreateCompanyDietaryInvalidGF() {
        var genreResponse = businessController.createFoodGenre(UUID.randomUUID().toString());
        assertTrue(genreResponse.success);
        var foodGenre = genreResponse.data.guid;
        var response = businessController.createCompanyDietary(false, false, false, true, false, false, false, foodGenre);
        assertFalse(response.success);
    }

    @Test
    public void testCreateCompanyDietaryInvalidVegan() {
        var genreResponse = businessController.createFoodGenre(UUID.randomUUID().toString());
        assertTrue(genreResponse.success);
        var foodGenre = genreResponse.data.guid;
        var response = businessController.createCompanyDietary(false, false, false, true, false, false, false, foodGenre);
        assertFalse(response.success);
    }

    @Test
    public void testCreateCompany() {
        var username = UUID.randomUUID().toString();
        var password = UUID.randomUUID().toString();
        var email = String.format("%s@gmail.com", UUID.randomUUID().toString());
        var registerResponse = authController.registerNormal("Joe Shmoe", username, password, email);
        assertTrue(registerResponse.success);

        var userResponse = authController.userInfo(username);
        assertTrue(userResponse.success);

        var genreResponse = businessController.createFoodGenre(UUID.randomUUID().toString());
        assertTrue(genreResponse.success);
        var foodGenre = genreResponse.data.guid;

        var dietaryResponse = businessController.createCompanyDietary(false, false, false, false, false, false, false, foodGenre);
        assertTrue(dietaryResponse.success);

        var financialResponse = businessController.createCompanyFinancial("00-0000000", "nc", "usa");
        assertTrue(financialResponse.success);

        businessController.createCompany("Test co", financialResponse.data.guid, dietaryResponse.data.guid, userResponse.data.guid);
    }
}
