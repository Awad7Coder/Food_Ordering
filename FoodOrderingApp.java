import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class MenuItem {
    private final String name;
    private final double price;

    public MenuItem(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return String.format("%-15s $%.2f", name, price);
    }
}

class MenuRepository {
    private final List<MenuItem> menuItems;

    public MenuRepository() {
        menuItems = new ArrayList<>();
        
        menuItems.add(new MenuItem("Burger", 5.99));
        menuItems.add(new MenuItem("Pizza", 8.49));
        menuItems.add(new MenuItem("Pasta", 7.25));
        menuItems.add(new MenuItem("Salad", 4.50));
        menuItems.add(new MenuItem("Soda", 1.99));
        menuItems.add(new MenuItem("Ice Cream", 3.75));
    }

    public List<MenuItem> getMenuItems() {
        return menuItems;
    }

    
    public void displayMenu() {
        System.out.println("===== MENU =====");
        for (int i = 0; i < menuItems.size(); i++) {
            System.out.println((i + 1) + ". " + menuItems.get(i));
        }
        System.out.println("================");
    }

    
    public MenuItem getItemByNumber(int itemNumber) {
        if (itemNumber < 1 || itemNumber > menuItems.size()) {
            return null;
        }
        return menuItems.get(itemNumber - 1);
    }

    public int getMenuSize() {
        return menuItems.size();
    }
}

class Order {
    private final List<MenuItem> selectedItems;

    public Order() {
        selectedItems = new ArrayList<>();
    }

    public void addItem(MenuItem item) {
        selectedItems.add(item);
    }

    public List<MenuItem> getSelectedItems() {
        return selectedItems;
    }

    public boolean isEmpty() {
        return selectedItems.isEmpty();
    }

    
    public double calculateTotal() {
        double total = 0.0;
        for (MenuItem item : selectedItems) {
            total += item.getPrice();
        }
        return total;
    }
}

class PaymentProcessor {
    private final Scanner scanner;

    public PaymentProcessor(Scanner scanner) {
        this.scanner = scanner;
    }

    
    public String selectPaymentMethod() {
        System.out.println("\nChoose payment method:");
        System.out.println("1. Cash");
        System.out.println("2. Card");
        System.out.print("Enter your choice: ");

        int choice = scanner.nextInt();
        String paymentMethod;

        if (choice == 1) {
            paymentMethod = "Cash";
        } else if (choice == 2) {
            paymentMethod = "Card";
        } else {
            System.out.println("Invalid choice. Defaulting to Cash.");
            paymentMethod = "Cash";
        }

        return paymentMethod;
    }

    
    public boolean processPayment(String paymentMethod, double amount) {
        System.out.printf("Processing %s payment of $%.2f...%n", paymentMethod, amount);
        
        System.out.println("Payment successful.");
        return true;
    }
}

class ReceiptPrinter {

    public void printReceipt(Order order, String paymentMethod) {
        System.out.println("\n===== ORDER SUMMARY =====");

        if (order.isEmpty()) {
            System.out.println("No items ordered.");
        } else {
            for (MenuItem item : order.getSelectedItems()) {
                System.out.println(item);
            }
        }

        System.out.println("--------------------------");
        System.out.printf("Total: $%.2f%n", order.calculateTotal());
        System.out.println("Payment Method: " + paymentMethod);
        System.out.println("==========================");
    }
}

class NotificationService {

    public void sendOrderConfirmation() {
        System.out.println("\nNotification: Your order has been placed successfully!");
    }
}

public class FoodOrderingApp {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        
        MenuRepository menuRepository = new MenuRepository();
        Order order = new Order();
        PaymentProcessor paymentProcessor = new PaymentProcessor(scanner);
        ReceiptPrinter receiptPrinter = new ReceiptPrinter();
        NotificationService notificationService = new NotificationService();

        
        menuRepository.displayMenu();

        
        collectOrderItems(scanner, menuRepository, order);

        
        String paymentMethod = paymentProcessor.selectPaymentMethod();

        
        double total = order.calculateTotal();
        paymentProcessor.processPayment(paymentMethod, total);

        
        receiptPrinter.printReceipt(order, paymentMethod);

        
        notificationService.sendOrderConfirmation();

        scanner.close();
    }

    
    private static void collectOrderItems(Scanner scanner, MenuRepository menuRepository, Order order) {
        boolean isAddingItems = true;

        while (isAddingItems) {
            System.out.print("\nEnter item number to add to order (or 0 to finish): ");
            int choice = scanner.nextInt();

            if (choice == 0) {
                isAddingItems = false;
            } else {
                MenuItem selectedItem = menuRepository.getItemByNumber(choice);
                if (selectedItem != null) {
                    order.addItem(selectedItem);
                    System.out.println(selectedItem.getName() + " added to your order.");
                } else {
                    System.out.println("Invalid choice. Please try again.");
                }
            }
        }
    }
}
