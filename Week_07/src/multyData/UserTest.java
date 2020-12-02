package multyData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicInteger;

public class UserTest {
    private static final int NUM = 1000000;
    private static final String SQL = "insert into user(id,name,sex,mobile) values(?,?,?,?)";
    private static AtomicInteger atomicInteger = new AtomicInteger(0);

    public static void main(String[] args) {
        method1();
        method2();
        method3();
    }

    private static void method1() {
        Connection connection = ConnectionUtils.getConnection();
        if (connection == null) {
            return;
        }
        long start = System.currentTimeMillis();
        try {
            for (int i = 0; i < NUM; i++) {
                connection.nativeSQL("insert into user(id,name,sex,mobile) values("
                        + atomicInteger.incrementAndGet() + ",name" + i + ", 0" + ",mobile" + i + ")");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        System.out.println("cost time:" + (System.currentTimeMillis() - start));
    }

    private static void method2() {
        Connection connection = ConnectionUtils.getConnection();
        if (connection == null) {
            return;
        }
        long start = System.currentTimeMillis();
        try {
            PreparedStatement ps = connection.prepareStatement(SQL);
            for (int i = 0; i < NUM; i++) {
                ps.setInt(1, atomicInteger.incrementAndGet());
                ps.setString(2, "name" + i);
                ps.setString(3, "1");
                ps.setString(4, "mobile" + i);
                ps.execute();
                ps.clearParameters();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        System.out.println("cost time:" + (System.currentTimeMillis() - start));
    }

    private static void method3() {
        Connection connection = ConnectionUtils.getConnection();
        if (connection == null) {
            return;
        }
        long start = System.currentTimeMillis();
        try {
            PreparedStatement ps = connection.prepareStatement(SQL);
            for (int i = 0; i < NUM; i++) {
                ps.setInt(1, atomicInteger.incrementAndGet());
                ps.setString(2, "name" + i);
                ps.setString(3, "1");
                ps.setString(4, "mobile" + i);
                ps.addBatch();
            }
            ps.executeBatch();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        System.out.println("cost time:" + (System.currentTimeMillis() - start));
    }
}
