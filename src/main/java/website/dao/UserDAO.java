package website.dao;

import website.utils.DatabaseConnection;
import website.model.UserInfo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    public List<UserInfo> getAllUsers() throws Exception {
        List<UserInfo> users = new ArrayList<>();

        String sql = "SELECT * FROM users";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)){

            while(rs.next()){
                UserInfo user = new UserInfo(
                        rs.getInt("user_id"),
                        rs.getString("username"),
                        rs.getString("email"),
                        rs.getString("password")
                );
                users.add(user);

            }

        }
        return users;
    }

    public boolean userExists(String email) throws SQLException {
        String sql = "SELECT COUNT(*) FROM users WHERE email = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                return rs.getInt(1) > 0;
            }
        }
    }

    public void signUp(UserInfo userInfo) {

        String sql = "INSERT INTO users (user_id, username, email, password) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)){

            ps.setInt(1, userInfo.getUserID());
            ps.setString(2, userInfo.getUserName());
            ps.setString(3, userInfo.getEmail());
            ps.setString(4, userInfo.getPassword());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        //insert into cart
    }

    public int getUserId (String email) throws SQLException {
        String sql = "SELECT user_id FROM users WHERE email = ?";
        int userId;
        try (Connection conn = DatabaseConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                userId = rs.getInt("user_id");
            }
        }
        return userId;
    }

}
