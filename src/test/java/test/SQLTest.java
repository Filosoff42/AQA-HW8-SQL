package test;

import data.DataHelper;
import lombok.val;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import page.LoginPage;

import static com.codeborne.selenide.Selenide.open;

public class SQLTest {

    @BeforeEach
    void openPage() {
        open("http://localhost:9999");
    }

    @AfterAll
    static void shouldClearCodes(){
        DataHelper.clearCodes();
    }

    @Test
    public void shouldLogInSuccessOneUser() {
        val loginPage = new LoginPage();
        val user = DataHelper.getAuthInfoForUser();
        val verificationPage = loginPage.validLogin(user);
        val verificationCode = DataHelper.getVerificationCodeForUser(user);
        val dashboardPage = verificationPage.validVerify(verificationCode);
    }

    @Test
    public void shouldLogInSuccessAnotherUser() {
        val loginPage = new LoginPage();
        val user = DataHelper.getAuthInfoForAnotherUser();
        val verificationPage = loginPage.validLogin(user);
        val verificationCode = DataHelper.getVerificationCodeForUser(user);
        val dashboardPage = verificationPage.validVerify(verificationCode);
    }


}


