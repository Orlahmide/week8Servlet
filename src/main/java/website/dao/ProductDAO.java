package website.dao;


import website.utils.DatabaseConnection;
import website.model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {

    public void addProduct(Product product) throws SQLException {

        String sql = "INSERT INTO products (name, description, price, category,image_path) VALUES (?, ?, ?, ?,?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, product.getName());
            stmt.setString(2, product.getDescription());
            stmt.setDouble(3, product.getPrice());
            stmt.setString(4, product.getCategory());
            stmt.setString(5, product.getImagePath());
            stmt.executeUpdate();
        }
    }


    public List<Product> getAllProducts() throws SQLException {

        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Product product = new Product(
                        rs.getInt("product_id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getDouble("price"),
                        rs.getString("category"),
                        rs.getString("image_path")
                );
                products.add(product);
            }
        }
        return products;
    }


    public List<Product> updateProduct(Product product) throws SQLException {

        String sql = "UPDATE products SET name = ?, price = ?, description  = ?, category = ? WHERE product_id = ?";

        try(Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setString(1, product.getName());
            stmt.setDouble(2, product.getPrice());
            stmt.setString(3, product.getDescription());
            stmt.setString(4, product.getCategory());
            stmt.setInt(5, product.getProductId());
            stmt.executeUpdate();
        }

        return null;
    }


    public boolean deleteProduct(int productID) throws SQLException {

        Product products = getProduct(productID);

        if (products == null) {
            System.out.println("Product does not exist");
            return false;

        }
        String sql = "DELETE FROM products WHERE product_id = ?";

        try(Connection conn = DatabaseConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setInt(1, productID);
            stmt.executeUpdate();
        }
        return true;
    }



    public Product getProduct(int productID) throws SQLException{

        String sql = String.format( "SELECT * FROM products WHERE product_id = %d", productID );

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Product product = new Product(
                        rs.getInt("product_id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getDouble("price"),
                        rs.getString("category")
                );
                return product;
            }
        }
        return null;
    }



}
