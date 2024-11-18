package website.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import website.dao.CartDAO;
import website.dao.ProductDAO;
import website.model.Cart;
import website.model.CartProduct;
import website.model.Product;

import java.io.IOException;
import java.sql.*;
import java.util.List;

@WebServlet("/cart")
public class CartServlet extends HttpServlet {

    ProductDAO productService = new ProductDAO();

    CartDAO cartService = new CartDAO();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();
        var userID = (int) session.getAttribute("userId");
        List<CartProduct> products = null;
        try {
            products = cartService.getCart(userID);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        req.setAttribute("products", products);
        req.getRequestDispatcher("cart.jsp").forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        HttpSession session = request.getSession();
        switch (action) {
            case "add":
                try {
                    addProductToCart(request,response);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                break;
            case "remove":
                try {
                    removeProductFromCart(request,response);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                break;
        }

    }


    private void addProductToCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        int userID = (int) request.getSession().getAttribute("userId");
        int productID = Integer.parseInt( request.getParameter("productId"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));

        Cart cart = new Cart(userID,productID,quantity);
        cartService.addProductToCart(cart);
        displayCustomerProducts(request, response);
    }

    private void displayCustomerProducts(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        List<Product> products = productService.getAllProducts();
        request.setAttribute("products", products);
        request.getRequestDispatcher("customer.jsp").forward(request, response);
    }

    private void removeProductFromCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        int userID = (int) request.getSession().getAttribute("userId");
        int productID = Integer.parseInt( request.getParameter("productId"));

        Cart cart = new Cart(userID,productID,0);
        cartService.removeProductFromCart(userID,productID);
        displayCustomerProducts(request, response);

    }

}