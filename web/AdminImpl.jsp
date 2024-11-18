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
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Product Management</title>
    <style>
        /* Reset styling */
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        /* Body styling */
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f9;
            color: #333;
            display: flex;
            justify-content: center;
            align-items: center;
            flex-direction: column;
            min-height: 100vh;
            padding: 20px;
        }

        /* Container styling */
        .container {
            width: 100%;
            max-width: 700px;
            background-color: #fff;
            padding: 2rem;
            border-radius: 8px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        }

        /* Headings */
        h1, h2 {
            text-align: center;
            color: #444;
            margin-bottom: 1rem;
        }

        h2 {
            color: #007bff;
            border-bottom: 2px solid #007bff;
            padding-bottom: 0.5rem;
        }

        /* Table styling */
        table {
            width: 100%;
            border-collapse: collapse;
            margin-bottom: 2rem;
        }
        th, td {
            padding: 10px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }
        th {
            background-color: #f4f4f4;
            color: #333;
        }
        tr:hover {
            background-color: #f9f9f9;
        }

        /* Form styling */
        form {
            margin-bottom: 2rem;
        }
        label {
            font-weight: bold;
            display: block;
            margin: 8px 0 4px;
        }
        input[type="text"],
        input[type="number"],
        textarea {
            width: 100%;
            padding: 8px;
            margin-bottom: 12px;
            border: 1px solid #ddd;
            border-radius: 4px;
        }
        textarea {
            resize: vertical;
        }

        /* Button styling */
        button {
            width: 100%;
            padding: 10px;
            background-color: #007bff;
            color: #fff;
            font-size: 1rem;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            transition: background-color 0.3s;
        }
        button:hover {
            background-color: #0056b3;
        }

        /* Info text */
        p {
            text-align: center;
            color: #666;
            font-size: 0.9rem;
            margin-top: 2rem;
        }
        a {
            color: #007bff;
            text-decoration: none;
        }
        a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>

<div class="container">
    <h1>Product Management</h1>

    <!-- Product List -->
    <h2>Available Products</h2>
    <%
        if (products != null && !products.isEmpty()) {
    %>
    <table>
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Description</th>
            <th>Price</th>
            <th>Category</th>
        </tr>
        <% for (Product product : products) { %>
        <tr>
            <td><%= product.getProductId() %></td>
            <td><%= product.getName() %></td>
            <td><%= product.getDescription() %></td>
            <td>$<%= String.format("%.2f", product.getPrice()) %></td>
            <td><%= product.getCategory() %></td>
        </tr>
        <% } %>
    </table>
    <%
    } else {
    %>
    <p>No products available.</p>
    <%
        }
    %>

    <!-- Add Product Form -->
    <h2>Add Product</h2>
    <form action="product" method="post" enctype="multipart/form-data">
        <input type="hidden" name="action" value="add"/>

        <label for="name">Product Name:</label>
        <input type="text" id="name" name="name" required/>

        <label for="description">Product Description:</label>
        <textarea id="description" name="description"></textarea>

        <label for="price">Price:</label>
        <input type="number" id="price" name="price" step="0.01" required/>

        <label for="category">Category:</label>
        <input type="text" id="category" name="category"/>

        <label for="image">Product Image:</label>
        <input type="file" id="image" name="image" accept="image/*" required/>

        <button type="submit">Add Product</button>
    </form>

    <!-- Update Product Form -->
    <h2>Update Product</h2>
    <form action="product" method="post">
        <input type="hidden" name="action" value="update"/>
        <label for="productId">Product ID:</label>
        <input type="text" id="productId" name="productId" required/>

        <label for="updateName">Product Name:</label>
        <input type="text" id="updateName" name="name" required/>

        <label for="updateDescription">Product Description:</label>
        <textarea id="updateDescription" name="description"></textarea>

        <label for="updatePrice">Price:</label>
        <input type="number" id="updatePrice" name="price" step="0.01" required/>

        <label for="updateCategory">Category:</label>
        <input type="text" id="updateCategory" name="category"/>

        <button type="submit">Update Product</button>
    </form>

    <!-- Delete Product Form -->
    <h2>Delete Product</h2>
    <form action="product" method="post">
        <input type="hidden" name="action" value="delete"/>
        <label for="deleteProductId">Product ID:</label>
        <input type="text" id="deleteProductId" name="productId" required/>

        <button type="submit">Delete Product</button>
    </form>

    <p>Make sure to fill out the necessary details for each action before submitting.</p>
</div>

</body>
</html>
