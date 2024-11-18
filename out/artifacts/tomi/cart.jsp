<%@ page import="website.model.Product" %>
<%@ page import="java.util.List" %>
<%@ page import="website.model.CartProduct" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Your Cart</title>
    <style>
        body {
            font-family: 'Poppins', Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f8f9fa;
            color: #333;
        }

        h1 {
            text-align: center;
            margin-top: 30px;
            color: #2c3e50;
            font-weight: 700;
        }

        table {
            width: 90%;
            margin: 30px auto;
            border-collapse: collapse;
            background-color: #ffffff;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        }

        th, td {
            padding: 15px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }

        th {
            background-color: #2c3e50;
            color: white;
            text-transform: uppercase;
            font-weight: bold;
        }

        td {
            color: #555;
        }

        .total-cost {
            margin: 20px auto;
            width: 90%;
            text-align: right;
            font-size: 1.2rem;
            color: #333;
            font-weight: bold;
        }

        .total-cost span {
            color: #4CAF50;
        }

        .empty-cart {
            text-align: center;
            margin-top: 50px;
            color: #888;
            font-size: 1.2rem;
        }
    </style>
</head>
<body>
<h1>Your Cart</h1>

<%
    List<CartProduct> products = (List<CartProduct>) request.getAttribute("products");
    double totalCost = 0;

    if (products != null && !products.isEmpty()) {
%>
<table>
    <thead>
    <tr>
        <th>Product ID</th>
        <th>Product Name</th>
        <th>Price</th>
        <th>Quantity</th>
        <th>Subtotal</th>
    </tr>
    </thead>
    <tbody>
    <%
        for (CartProduct product : products) {
            double subtotal = product.getProduct().getPrice() * product.getQuantity();
            totalCost += subtotal;
    %>
    <tr>
        <td><%= product.getProduct().getProductId() %></td>
        <td><%= product.getProduct().getName() %></td>
        <td>$<%= String.format("%.2f", product.getProduct().getPrice()) %></td>
        <td><%= product.getQuantity() %></td>
        <td>$<%= String.format("%.2f", subtotal) %></td>
    </tr>
    <%
        }
    %>
    </tbody>
</table>

<div class="total-cost">
    Total Cost: <span>$<%= String.format("%.2f", totalCost) %></span>
</div>
<%
} else {
%>
<p class="empty-cart">Your cart is empty!</p>
<%
    }
%>

</body>
</html>
