package website.dao;

import website.model.Cart;
import website.model.CartProduct;
import website.utils.DatabaseConnection;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CartDAO {

    ProductDAO productService = new ProductDAO();

    public List<CartProduct> getCart(Integer userId) throws SQLException {

        List<CartProduct> products = new ArrayList<>();
        String sql = String.format("SELECT * FROM cart WHERE user_id = %d", userId);

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                int productId = rs.getInt("product_id");
                int quantity = rs.getInt("quantity");
                var product = productService.getProduct(productId);
                CartProduct cartProduct = new CartProduct(quantity,product);
                products.add(cartProduct);
            }
        }
        return products;
    }

    public void addProductToCart(Cart cart) throws SQLException {

        String sql = "INSERT INTO cart (cart_id, user_id, product_id, quantity) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, cart.getCart_id());
            stmt.setInt(2, cart.getUser_id());
            stmt.setInt(3, cart.getProduct_id());
            stmt.setInt(4, cart.getQuantity());
            stmt.executeUpdate();
        }
    }

    public boolean removeProductFromCart(int user_id, int product_id) throws SQLException {

            List<CartProduct> products = getCart(user_id);

            for (CartProduct cartProduct : products) {
                if (cartProduct.getProduct().getProductId() == product_id) {

                    String sql = "DELETE FROM cart WHERE product_id = ?";

                    try(Connection conn = DatabaseConnection.getConnection();
                        PreparedStatement stmt = conn.prepareStatement(sql)){
                        stmt.setInt(1, product_id);
                        stmt.executeUpdate();
                    }
                    return true;
                }
            }
            return false;

    }

}
