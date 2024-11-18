<%@ page import="website.model.Product" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    List<Product> products = (List<Product>) request.getAttribute("products");
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Product List</title>
    <style>
        body {
            font-family: 'Poppins', Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f8f9fa;
            color: #333;
        }

        h2 {
            text-align: center;
            color: #2c3e50;
            font-weight: 700;
            margin-top: 20px;
            margin-bottom: 40px;
        }

        /* Container for product cards */
        .product-container {
            display: flex;
            flex-wrap: wrap;
            gap: 30px;
            justify-content: center;
            padding: 0 20px;
        }

        /* Card styling */
        .product-card {
            background-color: #ffffff;
            border-radius: 12px;
            overflow: hidden;
            box-shadow: 0 8px 20px rgba(0, 0, 0, 0.15);
            transition: transform 0.3s, box-shadow 0.3s;
            width: 280px;
        }

        .product-card:hover {
            transform: translateY(-8px);
            box-shadow: 0 12px 24px rgba(0, 0, 0, 0.25);
        }

        /* Product image */
        .product-image img {
            width: 100%;
            height: 200px;
            object-fit: cover;
        }

        /* Product details */
        .product-details {
            padding: 20px;
            text-align: center;
        }

        .product-name {
            font-size: 1.2em;
            font-weight: 600;
            margin: 10px 0;
            color: #34495e;
        }

        .product-description {
            font-size: 0.95em;
            color: #7f8c8d;
            margin-bottom: 10px;
        }

        .product-price {
            font-size: 1.1em;
            font-weight: bold;
            color: #16a085;
            margin: 12px 0;
        }

        .product-category {
            font-size: 0.9em;
            color: #95a5a6;
            margin-bottom: 20px;
        }

        /* Action buttons */
        .action-btn {
            display: inline-block;
            padding: 12px 20px;
            font-size: 0.95em;
            font-weight: bold;
            color: #ffffff;
            border: none;
            border-radius: 6px;
            cursor: pointer;
            text-transform: uppercase;
            transition: background-color 0.3s;
        }

        .add-btn {
            background-color: #27ae60;
        }

        .add-btn:hover {
            background-color: #1e8449;
        }

        .remove-btn {
            background-color: #e74c3c;
        }

        .remove-btn:hover {
            background-color: #c0392b;
        }

        .view-cart-btn {
            background-color: #2980b9;
            padding: 12px 30px;
            font-size: 1em;
            border-radius: 8px;
            margin-bottom: 30px;
        }

        .view-cart-btn:hover {
            background-color: #21618c;
        }

        /* Responsive adjustments */
        @media (max-width: 768px) {
            .product-card {
                width: 100%;
            }
        }
    </style>
</head>
<body>

<h2>Discover Our Exclusive Products</h2>

<!-- View Cart Button -->
<div style="text-align: center;">
    <form action="cart" method="get">
        <button type="submit" class="action-btn view-cart-btn">View Cart</button>
    </form>
</div>

<% if (products != null && !products.isEmpty()) { %>
<div class="product-container">
    <% for (Product product : products) { %>
    <div class="product-card">
        <div class="product-image">
            <img src="uploads/<%= product.getImagePath() %>" alt="Product Image">
        </div>
        <div class="product-details">
            <div class="product-name"><%= product.getName() %></div>
            <div class="product-description"><%= product.getDescription() %></div>
            <div class="product-price">$<%= String.format("%.2f", product.getPrice()) %></div>
            <div class="product-category"><em>Category: <%= product.getCategory() %></em></div>
            <!-- Add to Cart Button -->
            <form action="cart" method="post" style="margin-top: 10px;">
                <input type="hidden" name="action" value="add">
                <input type="hidden" name="productId" value="<%= product.getProductId() %>">
                <label for="quantity">Quantity:</label>
                <input type="number" id="quantity" name="quantity" min="1" required style="margin-bottom: 10px;">
                <button type="submit" class="action-btn add-btn">Add to Cart</button>
            </form>
            <!-- Remove from Cart Button -->
            <form action="cart" method="post" style="margin-top: 5px;">
                <input type="hidden" name="action" value="remove">
                <input type="hidden" name="productId" value="<%= product.getProductId() %>">
                <button type="submit" class="action-btn remove-btn">Remove from Cart</button>
            </form>
        </div>
    </div>
    <% } %>
</div>
<% } else { %>
<p style="text-align: center;">No products available at the moment. Please check back later.</p>
<% } %>

</body>
</html>
