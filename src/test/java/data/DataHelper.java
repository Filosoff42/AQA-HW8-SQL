package data;

import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.DriverManager;

public class DataHelper {

    private DataHelper() {
    }

    @SneakyThrows
    public static User getAuthInfoForUser(int user_number) {
        var userData = "SELECT id, login, password FROM (SELECT ROW_NUMBER() OVER (ORDER BY id) row_num, id, login, password FROM users) users WHERE row_num = ?;";
        var runner = new QueryRunner();
        try (
                var conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/database", "user", "password");
        ) {
            var loginData = runner.query(conn, userData, new BeanHandler<>(User.class), user_number);
            return loginData;
        }
    }


    @SneakyThrows
    public static VerificationCode getVerificationCodeForUser(User user) {
        var verificationData = "SELECT code FROM auth_codes WHERE user_id = ? and created >= NOW() - INTERVAL 5 SECOND;";
        var runner = new QueryRunner();
        try (
                var conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/database", "user", "password");
        ) {
            var verificationCode = runner.query(conn, verificationData, new BeanHandler<>(VerificationCode.class), user.getId());
            return verificationCode;
        }
    }
}

