import java.util.*;

class Product {
    private String productId;
    private String name;
    private double price;
    private int quantity;

    public Product(String productId, String name, double price, int quantity) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public String getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}

class ShoppingCart {
    private Map<String, Integer> cartItems;

    public ShoppingCart() {
        cartItems = new HashMap<>();
    }

    public void addToCart(String productId, int quantity, Map<String, Product> products) {
        if (products.containsKey(productId)) {
            if (products.get(productId).getQuantity() >= quantity) {
                if (cartItems.containsKey(productId)) {
                    int existingQuantity = cartItems.get(productId);
                    cartItems.put(productId, existingQuantity + quantity);
                } else {
                    cartItems.put(productId, quantity);
                }
                System.out.println(quantity + " " + products.get(productId).getName() + "(s) added to cart.");
            } else {
                System.out.println("Not enough stock available for " + products.get(productId).getName());
            }
        } else {
            System.out.println("Product not found.");
        }
    }

    public void viewCart(Map<String, Product> products) {
        if (cartItems.isEmpty()) {
            System.out.println("Your cart is empty.");
        } else {
            System.out.println("Cart Items:");
            for (Map.Entry<String, Integer> entry : cartItems.entrySet()) {
                String productId = entry.getKey();
                int quantity = entry.getValue();
                Product product = products.get(productId);
                System.out.println("Product ID: " + productId + ", Name: " + product.getName() +
                        ", Quantity: " + quantity + ", Price per unit: $" + product.getPrice());
            }
        }
    }

    public double checkout(Map<String, Product> products) {
        double total = 0;
        System.out.println("\nChecking out...");
        for (Map.Entry<String, Integer> entry : cartItems.entrySet()) {
            String productId = entry.getKey();
            int quantity = entry.getValue();
            Product product = products.get(productId);
            total += product.getPrice() * quantity;
            product.setQuantity(product.getQuantity() - quantity);
        }
        cartItems.clear();
        System.out.println("Total amount to be paid: $" + total);
        return total;
    }
}

public class OnlineShoppingSystem {
    public static void main(String[] args) {
        Map<String, Product> products = new HashMap<>();
        products.put("101", new Product("101", "Laptop", 899.99, 10));
        products.put("102", new Product("102", "Smartphone", 499.99, 20));
        products.put("103", new Product("103", "Headphones", 99.99, 15));

        ShoppingCart cart = new ShoppingCart();
        Scanner scanner = new Scanner(System.in);

        int choice;
        do {
            System.out.println("\nOnline Shopping System");
            System.out.println("1. View Products");
            System.out.println("2. Add to Cart");
            System.out.println("3. View Cart");
            System.out.println("4. Checkout");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    displayProducts(products);
                    break;
                case 2:
                    addToCart(cart, products, scanner);
                    break;
                case 3:
                    cart.viewCart(products);
                    break;
                case 4:
                    double amount = cart.checkout(products);
                    // Here you could integrate payment gateways or further processing
                    break;
                case 5:
                    System.out.println("Exiting Online Shopping System. Thank you!");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        } while (choice != 5);

        scanner.close();
    }

    private static void displayProducts(Map<String, Product> products) {
        System.out.println("Available Products:");
        for (Map.Entry<String, Product> entry : products.entrySet()) {
            Product product = entry.getValue();
            System.out.println("Product ID: " + product.getProductId() + ", Name: " + product.getName() +
                    ", Price: $" + product.getPrice() + ", Available Quantity: " + product.getQuantity());
        }
    }

    private static void addToCart(ShoppingCart cart, Map<String, Product> products, Scanner scanner) {
        System.out.print("Enter product ID to add to cart: ");
        String productId = scanner.nextLine();
        System.out.print("Enter quantity: ");
        int quantity = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        cart.addToCart(productId, quantity, products);
    }
}
