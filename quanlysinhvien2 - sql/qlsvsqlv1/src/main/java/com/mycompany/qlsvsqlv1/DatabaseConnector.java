package com.mycompany.qlsvsqlv1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {

    private static final String URL = "jdbc:mysql://localhost:3306/qlsv";
    private static final String USER = "root"; // <-- THAY THẾ bằng username của bạn
    private static final String PASSWORD = "vuvandung2981@"; // <-- THAY THẾ bằng password của bạn

    private static Connection connection = null;

    /**
     * Lấy kết nối đến cơ sở dữ liệu.
     * Sử dụng mẫu Singleton để chỉ có một kết nối duy nhất trong toàn bộ ứng dụng.
     * @return Đối tượng Connection hoặc null nếu kết nối thất bại.
     */
    public static Connection getConnection() {
        try {
            // Kiểm tra nếu kết nối đã bị đóng hoặc null, tạo kết nối mới
            if (connection == null || connection.isClosed()) {
                // 1. Nạp driver JDBC của MySQL
                Class.forName("com.mysql.cj.jdbc.Driver");

                // 2. Tạo kết nối với các tham số bổ sung
                connection = DriverManager.getConnection(URL + "?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true", USER, PASSWORD);

                System.out.println("Kết nối tới MySQL thành công!");
            }
        } catch (ClassNotFoundException e) {
            System.err.println("Lỗi: Không tìm thấy MySQL JDBC Driver.");
            System.err.println("Hãy đảm bảo đã thêm MySQL Connector/J vào pom.xml và chạy 'mvn clean install'");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Lỗi: Không thể kết nối tới cơ sở dữ liệu.");
            System.err.println("Kiểm tra lại:");
            System.err.println("  - URL: " + URL);
            System.err.println("  - Username: " + USER);
            System.err.println("  - Password: " + (PASSWORD.isEmpty() ? "(trống)" : "***"));
            System.err.println("  - MySQL server đang chạy");
            System.err.println("  - Database 'quanlysinhvien' đã được tạo");
            e.printStackTrace();
        }
        return connection;
    }

    /**
     * Đóng kết nối đến cơ sở dữ liệu khi ứng dụng kết thúc.
     */
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Đã đóng kết nối MySQL.");
            } catch (SQLException e) {
                System.err.println("Lỗi khi đóng kết nối.");
                e.printStackTrace();
            }
        }
    }
}
