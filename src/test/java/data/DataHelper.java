package data;

import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.DriverManager;

public class DataHelper {

    private DataHelper() {
    }

    @SneakyThrows
    public static User getAuthInfoForUser() {
        var userData = "SELECT id FROM users WHERE login = 'vasya';";
        var runner = new QueryRunner();
        try (var conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/database", "user", "password")) {
            var userId = runner.query(conn, userData, new ScalarHandler<String>());
            return new User(userId, "vasya", "qwerty123");
        }
    }

    @SneakyThrows
    public static User getAuthInfoForAnotherUser() {
        var userData = "SELECT id FROM users WHERE login = 'petya';";
        var runner = new QueryRunner();
        try (var conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/database", "user", "password")) {
            var userId = runner.query(conn, userData, new ScalarHandler<String>());
            return new User(userId, "petya", "123qwerty");
        }
    }

    @SneakyThrows
    public static VerificationCode getVerificationCodeForUser(User user) {
        var verificationData = "SELECT code FROM auth_codes WHERE user_id = ? and created >= NOW() - INTERVAL 5 SECOND;";
        var runner = new QueryRunner();
        try (
                var conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/database", "user", "password")
        ) {
            return runner.query(conn, verificationData, new BeanHandler<>(VerificationCode.class), user.getId());
            }
    }

    @SneakyThrows
    public static void clearCodes() {
        var clearData = "DELETE FROM auth_codes;";
        var runner = new QueryRunner();
        try (var conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/database", "user", "password")) {
            runner.execute(conn, clearData);
        }
    }
}

