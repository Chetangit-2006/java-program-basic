import java.util.*;

/**
 * Inventory Management System
 *
 * Demonstrates encapsulation, collections, validation,
 * custom exceptions, and CRUD operations.
 *
 * Compile: javac InventoryManagementSystem.java
 * Run:     java InventoryManagementSystem
 */

class Product {
    private final String id;
    private String name;
    private double price;
    private int quantity;

    public Product(String id, String name, double price, int quantity) {
        if (id == null || id.isBlank())
            throw new IllegalArgumentException("Product ID cannot be empty.");
        if (name == null || name.isBlank())
            throw new IllegalArgumentException("Product name cannot be empty.");
        if (price < 0 || quantity < 0)
            throw new IllegalArgumentException("Price and quantity cannot be negative.");

        this.id = id.trim();
        this.name = name.trim();
        this.price = price;
        this.quantity = quantity;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public int getQuantity() { return quantity; }

    public void updateDetails(String name, double price) {
        if (name == null || name.isBlank())
            throw new IllegalArgumentException("Product name cannot be empty.");
        if (price < 0)
            throw new IllegalArgumentException("Price cannot be negative.");
        this.name = name.trim();
        this.price = price;
    }

    public void addStock(int amount) {
        if (amount <= 0)
            throw new IllegalArgumentException("Stock addition must be positive.");
        quantity = Math.addExact(quantity, amount);
    }

    public void removeStock(int amount) {
        if (amount <= 0)
            throw new IllegalArgumentException("Stock removal must be positive.");
        if (amount > quantity)
            throw new InsufficientStockException("Only " + quantity + " unit(s) are available.");
        quantity -= amount;
    }

    @Override
    public String toString() {
        return String.format(Locale.US,
            "ID: %s | Name: %s | Price: %.2f | Quantity: %d",
            id, name, price, quantity);
    }
}

// Custom exception for stock-related errors.
class InsufficientStockException extends RuntimeException {
    public InsufficientStockException(String message) {
        super(message);
    }
}

// Service class that manages inventory operations.
class InventoryService {
    private final Map<String, Product> products = new HashMap<>();

    public void addProduct(Product product) {
        Objects.requireNonNull(product, "Product cannot be null.");
        if (products.containsKey(product.getId()))
            throw new IllegalArgumentException("A product with this ID already exists.");
        products.put(product.getId(), product);
    }

    public Product findProduct(String id) {
        Product product = products.get(normalizeId(id));
        if (product == null)
            throw new NoSuchElementException("Product not found: " + id);
        return product;
    }

    public void updateProduct(String id, String name, double price) {
        findProduct(id).updateDetails(name, price);
    }

    public void deleteProduct(String id) {
        if (products.remove(normalizeId(id)) == null)
            throw new NoSuchElementException("Product not found: " + id);
    }

    public void restock(String id, int amount) {
        findProduct(id).addStock(amount);
    }

    public void sell(String id, int amount) {
        findProduct(id).removeStock(amount);
    }

    public List<Product> getAllProducts() {
        List<Product> result = new ArrayList<>(products.values());
        result.sort(Comparator.comparing(Product::getId));
        return Collections.unmodifiableList(result);
    }

    private String normalizeId(String id) {
        if (id == null || id.isBlank())
            throw new IllegalArgumentException("Product ID cannot be empty.");
        return id.trim();
    }
}

public class InventoryManagementSystem {
    public static void main(String[] args) {
        InventoryService inventory = new InventoryService();

        try {
            // Add sample products.
            inventory.addProduct(new Product("P101", "Keyboard", 799.00, 15));
            inventory.addProduct(new Product("P102", "Mouse", 399.00, 20));
            inventory.addProduct(new Product("P103", "Monitor", 7499.00, 5));

            System.out.println("=== Initial Inventory ===");
            inventory.getAllProducts().forEach(System.out::println);

            // Perform sample inventory operations.
            inventory.sell("P101", 3);
            inventory.restock("P103", 2);
            inventory.updateProduct("P102", "Wireless Mouse", 599.00);

            System.out.println("\n=== Updated Inventory ===");
            inventory.getAllProducts().forEach(System.out::println);

            // Show how insufficient stock is handled.
            try {
                inventory.sell("P103", 100);
            } catch (InsufficientStockException e) {
                System.out.println("\nSale rejected: " + e.getMessage());
            }
        } catch (IllegalArgumentException | NoSuchElementException e) {
            System.err.println("Inventory operation failed: " + e.getMessage());
        }
    }
}
