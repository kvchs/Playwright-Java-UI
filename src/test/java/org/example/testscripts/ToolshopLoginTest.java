package org.example.testscripts;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.example.api.clients.UserApiClient;
import org.example.api.factories.UserDataFactory;
import org.example.api.models.UserRegistration;
import org.example.pages.AccountPage;
import org.example.pages.HomePage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Test
@Feature("User Authentication")
@Story("User Login")
public class ToolshopLoginTest extends TestBase {

    private UserApiClient userApiClient;
    private UserRegistration testUser;
    private Response registrationResponse;

    @BeforeMethod
    public void setupApiClient() {
        userApiClient = new UserApiClient();
        testUser = UserDataFactory.createRandomUser();
        registrationResponse = userApiClient.registerUser(testUser);
        assertThat(userApiClient.isRegistrationSuccessful(registrationResponse))
                .as("User registration should be successful")
                .isTrue();
    }

    @Test
    @Description("Create a new user via API and login through the UI")
    public void testCreateUserViaApiAndLogin() {
        AccountPage accountPage = new HomePage(getPage())
                .navigate()
                .clickSignIn()
                .login(testUser.email(), testUser.password());

        assertThat(accountPage.isMyAccountPageDisplayed().textContent()).isEqualTo("My account")
                .as("My Account page should be displayed after successful login");
    }

}
