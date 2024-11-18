package website;

import website.dao.UserDAO;
import website.model.UserInfo;

import java.sql.SQLException;


public class Main {
    public Main() throws SQLException {
    }

    public static void main(String[] args) {


//        try {
//            Product newProduct = new Product(4,"Digital watch", "A beautiful smart watch", 244.00,"computer accessories");
//            ProductDAO ap = new ProductDAO();
//            ap.addProduct(newProduct);
//            System.out.println("Product updated successfully!");
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }

        //Code to update a product
//        try {
//            Product newProduct = new Product(4,"Joy", "A beautiful LED mouse", 144.00,"computer accessories");
//            ProductDAO ap = new ProductDAO();
//            ap.updateProduct(newProduct);
//            System.out.println("Product updated successfully!");
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }

        //Code to delete a product
//        try {
//            ProductDAO ap = new ProductDAO();
//            ap.deleteProduct(4);
//            System.out.println("Product updated successfully!");
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }

        //code to get all products
//        try {
//            ProductDAO dao = new ProductDAO();
//            List<Product> products = dao.getAllProducts();
//
//            for (Product product : products) {
//                System.out.println("ID: " + product.getProductId());
//                System.out.println("Name: " + product.getName());
//                System.out.println("Description: " + product.getDescription());
//                System.out.println("Price: " + product.getPrice());
//                System.out.println("Category: " + product.getCategory());
//                System.out.println("-----------");
//            }
//        } catch (SQLException e) {
//            System.out.println("Error retrieving products: " + e.getMessage());
//            e.printStackTrace();
//        }

        //code to add a user
        try{
            UserDAO userService = new UserDAO();
            UserInfo user = new UserInfo("Tomilola","tomilolaaturaka@gmail.com","123456");
            userService.signUp(user);

            System.out.println("User added successfully!");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        //code to show all users
//         try{
//             UserDAO user = new UserDAO();
//             List<UserInfo> users = user.getAllUsers();
//             for (UserInfo userInfo : users) {
//                 System.out.println("ID: " + userInfo.getUserID());
//                 System.out.println("Name: " + userInfo.getUserName());
//                 System.out.println("Email: " + userInfo.getEmail());
//                 System.out.println("Password: " + userInfo.getPassword());
//                 System.out.println("-----------");
//             }
//         } catch (Exception e) {
//             throw new RuntimeException(e);
//         }

    }


}
