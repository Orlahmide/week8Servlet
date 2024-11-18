package website.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import website.dao.AuthenticationDAO;
import website.dao.ProductDAO;
import website.dao.UserDAO;
import website.model.Product;
import website.model.UserInfo;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/product")
@MultipartConfig
public class ProductServlet extends HttpServlet {
    private ProductDAO productService = new ProductDAO();
    private AuthenticationDAO authService = new AuthenticationDAO();
    private UserDAO userService = new UserDAO();
    private static final String UPLOAD_DIR = "uploads";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action == null) {
            action = "view";
        }

        switch (action) {
//            case "login":
//                request.getRequestDispatcher("login.jsp").forward(request, response);
//                break;
            case "signup":
                request.getRequestDispatcher("signup.jsp").forward(request, response);
                break;
            case "view":
                try {
                    displayProductPage(request, response);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                break;
            case "customerProducts":
                try {
                    displayCustomerProducts(request, response);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                break;
            default:
                response.getWriter().println("Invalid action.");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            if ("login".equals(action)) {
                handleLogin(request, response);
            } else if ("signup".equals(action)) {
                handleSignUp(request, response);
            } else {
                HttpSession session = request.getSession(false);
                if (session == null || session.getAttribute("user") == null) {
                    response.getWriter().println("Unauthorized access. Please log in first.");
                    return;
                }

                switch (action) {
                    case "add":
                        addProduct(request, response);
                        break;
                    case "update":
                        updateProduct(request, response);
                        break;
                    case "delete":
                        deleteProduct(request, response);
                        break;
                    default:
                        response.getWriter().println("Invalid action.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("An error occurred: " + e.getMessage());
        }
    }

    private void displayProductPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        List<Product> products = productService.getAllProducts();
        request.setAttribute("products", products);
        request.getRequestDispatcher("AdminImpl.jsp").forward(request, response);
    }

    private void displayCustomerProducts(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        List<Product> products = productService.getAllProducts();
        request.setAttribute("products", products);
        request.getRequestDispatcher("customer.jsp").forward(request, response);
    }

    private void handleLogin(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, SQLException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        var v = request. getContextPath();
        boolean isAuthenticated = authService.authenticate(email, password);
        if (isAuthenticated) {
            HttpSession session = request.getSession();
            session.setAttribute("user", email);

            int userId = userService.getUserId(email);
            session.setAttribute("userId", userId);

            String role = authService.getUserRole(email);
            session.setAttribute("role", role);

            if ("admin".equalsIgnoreCase(role)) {
                displayProductPage(request, response);
            } else if ("customer".equalsIgnoreCase(role)) {
                displayCustomerProducts(request, response);
            } else {
                response.getWriter().println("Invalid role.");
            }
        } else {
            // Check if the user exists
            boolean userExists = userService.userExists(email);
            if (userExists) {
                // Invalid password but existing user, redirect to login with error message
                request.setAttribute("errorMessage", "Invalid password. Please try again.");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            } else {
                // Non-existing user, redirect to signup
                request.setAttribute("signupMessage", "Email not registered. Please sign up.");
                request.getRequestDispatcher("signup.jsp").forward(request, response);
            }
        }
    }

    private void handleSignUp(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        String email = request.getParameter("email");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String role = request.getParameter("role");

        if (userService.userExists(email)) {
            response.getWriter().println("Email already registered. Please log in.");
            return;
        }

        UserInfo newUser = new UserInfo(0, username, email, password);
        userService.signUp(newUser);

        response.sendRedirect("login.jsp");
    }


    private void addProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        double price = Double.parseDouble(request.getParameter("price"));
        String category = request.getParameter("category");

        // Handle image upload
        Part filePart = request.getPart("image"); // Retrieves the image file part
        String fileName = extractFileName(filePart);
        String uploadPath = getServletContext().getRealPath("") + File.separator + UPLOAD_DIR;

        // Ensure upload directory exists
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }

        // Save the uploaded file
        String filePath = uploadPath + File.separator + fileName;
        filePart.write(filePath);

        // Save product details and image path
        Product product = new Product(name, description, price, category, fileName); // Assuming Product class has an image field
        productService.addProduct(product);

        response.sendRedirect("product?action=view");
    }

    private void updateProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {

        int productId = Integer.parseInt(request.getParameter("productId"));
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        double price = Double.parseDouble(request.getParameter("price"));
        String category = request.getParameter("category");

        Product product = new Product(productId, name, description, price, category);
        productService.updateProduct(product);

        response.sendRedirect("product?action=view");
    }

    private void deleteProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        int productId = Integer.parseInt(request.getParameter("productId"));

        productService.deleteProduct(productId);

        response.sendRedirect("product?action=view");
    }

    private String extractFileName(Part part) {
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(content.indexOf("=") + 2, content.length() - 1);
            }
        }
        return null;
    }
}
