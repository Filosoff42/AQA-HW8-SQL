package page;

import com.codeborne.selenide.SelenideElement;
import data.User;

import static com.codeborne.selenide.Selenide.$;

public class LoginPage {
    private SelenideElement loginField = $("[data-test-id=login] input");
    private SelenideElement passwordField = $("[data-test-id=password] input");
    private SelenideElement loginButton = $("[data-test-id=action-login]");

    public String getRealPassword(User user) {
        String password = null;
        String login = user.getLogin();
        if (login.equals("vasya")) {
            password = "qwerty123";
        }
        if (login.equals("petya")) {
            password = "123qwerty";
        }
        return password;
    }

    public VerificationPage validLogin(User user) {
        String password = getRealPassword(user);
        loginField.setValue(user.getLogin());
        passwordField.setValue(password);
        loginButton.click();
        return new VerificationPage();
    }
}
