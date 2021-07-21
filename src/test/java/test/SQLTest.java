package test;

import data.DataHelper;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import page.LoginPage;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class SQLTest {

    @BeforeEach
    void openPage() {
        open("http://localhost:9999");
    }

    @Test
    public void shouldLogInSuccessOneUser() {
        val loginPage = new LoginPage();
        val user = DataHelper.getAuthInfoForUser(1);
        val verificationPage = loginPage.validLogin(user);
        val verificationCode = DataHelper.getVerificationCodeForUser(user);
        verificationPage.validVerify(verificationCode);
        $("[data-test-id=dashboard]").shouldBe(visible);
    }

    @Test
    public void shouldLogInSuccessAnotherUser() {
        val loginPage = new LoginPage();
        val user = DataHelper.getAuthInfoForUser(2);
        val verificationPage = loginPage.validLogin(user);
        val verificationCode = DataHelper.getVerificationCodeForUser(user);
        verificationPage.validVerify(verificationCode);
        $("[data-test-id=dashboard]").shouldBe(visible);
    }
}


