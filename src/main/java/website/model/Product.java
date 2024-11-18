package website.model;

public class Product {
        private int productId;
        private String name;
        private String description;
        private double price;
        private String category;
        private String imagePath;

        // Constructor
        public Product(int productId, String name, String description, double price, String category) {
            this.productId = productId;
            this.name = name;
            this.description = description;
            this.price = price;
            this.category = category;
        }

    public Product( String name, String description, double price, String category, String imagePath) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
        this.imagePath = imagePath;
    }

    public Product(int productId, String name, String description, double price, String category, String imagePath) {
        this.productId = productId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
        this.imagePath = imagePath;
    }

    public Product(String name, String description, double price, String category) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
    }

    public Product(int productId) {
        this.productId = productId;
    }

    public Product() {

    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    // Getters
        public int getProductId() { return productId; }
        public String getName() { return name; }
        public String getDescription() { return description; }
        public double getPrice() { return price; }
        public String getCategory() { return category; }

        // Setters if needed
        public void setProductId(int productId) { this.productId = productId; }
        public void setName(String name) { this.name = name; }
        public void setDescription(String description) { this.description = description; }
        public void setPrice(double price) { this.price = price; }
        public void setCategory(String category) { this.category = category; }

}
