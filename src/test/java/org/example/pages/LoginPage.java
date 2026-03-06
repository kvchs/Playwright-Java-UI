package org.example.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.qameta.allure.Step;
import org.example.properties.Environment;

public class LoginPage extends AbstractPageBase {

    private final Locator emailInput;
    private final Locator passwordInput;
    private final Locator loginButton;

    public LoginPage(Page page) {
        super(page);
        this.emailInput = page.locator("[data-test='email']");
        this.passwordInput = page.locator("[data-test='password']");
        this.loginButton = page.locator("[data-test='login-submit']");
    }

    @Step("Navigate to login page")
    public LoginPage navigate() {
        page.navigate(Environment.getProperties().url() + "/auth/login");
        return this;
    }

    @Step("Login with email: {email}")
    public AccountPage login(String email, String password) {
        emailInput.fill(email);
        passwordInput.fill(password);
        loginButton.click();
        return new AccountPage(page);
    }
}
