import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import Customers.*;
import Exceptions.InappropriateNumberException;

public class Test {

    public static void main(String[] args) {
        // I don't like decimals separated by "," instead of ".".
        java.util.Locale.setDefault(java.util.Locale.ROOT);

        // I chose not to use these as static fields because having methods need parameters is better for reusability I think.

        // Whole program uses this scanner object. Not sure if it's good practice but closing System.in seems undesirable
        // and I wanted to find a solution to that resource leak.
        Scanner scanner = new Scanner(System.in);

        Product p1 = new Product("apple", 20, false, 0.15);
        Product p2 = new Product("orange", 30, true, 0.05);
        Product p3 = new Product("bacon", 40, true, 0);
        Product p4 = new Product("cake", 35, true, 0.15);

        Customer rCustomer1 = new RegularCustomer("John", 42, 14.3);
        Customer rCustomer2 = new RegularCustomer("Jane", 24);
        Customer gCustomer1 = new GoldCustomer("Sir John", 1337, 15);
        Customer gCustomer2 = new GoldCustomer("Madame Jane", 7331);

        ArrayList<Product> products = new ArrayList<Product>(Arrays.asList(p1, p2, p3, p4));
        ArrayList<Customer> customers = new ArrayList<Customer>(Arrays.asList(rCustomer1, rCustomer2, gCustomer1, gCustomer2));
        ArrayList<Transaction> transactions = new ArrayList<Transaction>();

        // I wasn't sure if I understood this part correct.
        
        Transaction transR1 = new Transaction(scanner, rCustomer1, true);
        Transaction transR2 = new Transaction(scanner, rCustomer2, true);
        Transaction transG1 = new Transaction(scanner, gCustomer2, true);
        Transaction transG2 = new Transaction(scanner, gCustomer2, true);

        transR1.addToBasket(0, products, 2);
        transR1.addToBasket(1, products, 3);

        transR2.addToBasket(2, products, 4);
        transR2.addToBasket(3, products, 6);

        transG1.addToBasket(0, products, 10);
        transG1.addToBasket(3, products, 2);

        transG2.addToBasket(1, products, 3);
        transG2.addToBasket(2, products, 5);
        
        
        System.out.println("---------Automatic transactions---------\n\n");
        
        System.out.println("Regular 1:\n");
        transR1.invoice();
        
        System.out.println("Regular 2:\n");
        transR2.invoice();
        
        System.out.println("Gold 1:\n");
        transG2.invoice();
        
        System.out.println("Gold 2:\n");
        transG2.invoice();

        transactions.add(transR1);
        transactions.add(transR2);
        transactions.add(transG1);
        transactions.add(transG2);

        System.out.println("---------End of automatic transactions---------\n\n");
        
        // System.out.println(trans.invoice());

        int choice = startMenu(scanner);

        if (choice == 1) {
            customerOps(scanner, products, customers, transactions);
        }
        else if (choice == 2) {
            managementOps(scanner, transactions, customers);
        }

        
    }
    
    public static int startMenu(Scanner scanner) {
        
        
        int choice;
        
        while (true) {
            try {
                System.out.print("\n1: Customer\n2: Manager\n");
                choice = Integer.parseInt(scanner.nextLine());
                break;
            }
            catch(NumberFormatException e) {
                System.out.print("\nPlease enter an integer.");
            }
        }

        return choice;
    }

    public static void customerOps(Scanner scanner, ArrayList<Product> products, ArrayList<Customer> customers, ArrayList<Transaction> transactions) {
        
        Customer customer = getCustomer(scanner, customers);
        
        Transaction trans = new Transaction(scanner, customer);
        
        int id = 0;
        do {
            try {
                displayProducts(products);
                System.out.print("ID and amount: ");
                String[] idAndAmountString = scanner.nextLine().split(" ");
                
                id = Integer.parseInt(idAndAmountString[0]) - 1;
                if (id == -1) break;
                
                if (idAndAmountString.length != 2) {
                    throw new InappropriateNumberException();
                }
                int amount = Integer.parseInt(idAndAmountString[1]);

                trans.addToBasket(id, products, amount);
            }

            catch (InappropriateNumberException e) {
                System.out.println("Please only enter the ID of the item and the amount you wish to get.");
                continue;
            }
            catch (NumberFormatException e) {
                System.out.println("Please enter valid numbers.");
                continue;
            }

        } while (id != -1);

        System.out.println();
        trans.invoice();

        transactions.add(trans);

    }

    public static void managementOps(Scanner scanner, ArrayList<Transaction> transactions, ArrayList<Customer> customers) {
        
        boolean inProgress = true;

        while (inProgress) {
            System.out.printf("\n%s\n%s\n%s\n%s\n%s\n",
            "1: see total revenue from all transactions up until now",
            "2: see total amount of product promotions",
            "3: see total amount of ttransaction promotions",
            "4: total accumulatred points of all customers",
            "Press 0 to exit.");
        

            try {
                int choice = Integer.parseInt(scanner.nextLine());
                if (choice < 0 || choice > 4) {
                    throw new InappropriateNumberException();
                }

                switch (choice) {
                    case 1:
                        System.out.printf("Total revenue: %.2f\n", totalRevenue(transactions));
                        break;
                    case 2:
                        System.out.printf("Total product promotions: %.2f\n", totalProductPromotions(transactions));
                        break;
                    case 3:
                        System.out.printf("Total transaction promotions: %.2f\n", totalTransactionPromotions(transactions));
                        break;
                    case 4:
                        System.out.printf("Total accumulated points: %.2f\n", totalPoints(customers));
                        break;
                    default:
                        inProgress = false;
                        break;
                }

            }
            catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }

            catch (InappropriateNumberException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    public static double totalRevenue(ArrayList<Transaction> transactions) {
        double revenue = 0;
        for(Transaction trans : transactions) {
            revenue += trans.getTotalPayment();
        }

        return revenue;
    }

    public static double totalProductPromotions(ArrayList<Transaction> transactions) {
        double totalProductPromotion = 0;
        for(Transaction trans : transactions) {
            totalProductPromotion += trans.getTotalProductPromotion();
        }
        return totalProductPromotion;
    }

    public static double totalTransactionPromotions(ArrayList<Transaction> transactions) {
        double totalTransactionPromotion = 0;
        for(Transaction trans : transactions) {
            totalTransactionPromotion += trans.getTransactionPromotion();
        }
        return totalTransactionPromotion;
    }

    public static double totalPoints(ArrayList<Customer> customers) {
        double totalPoints = 0;
        for(Customer customer : customers) {
            totalPoints += customer.getPoints();
        }
        return totalPoints;
    }

    public static Customer getCustomer(Scanner scanner, ArrayList<Customer> customers) {
        System.out.println("\nWelcome to the shop! Please enter your customer ID. If you are not a registered customer simply enter 0.");
        int id;
        boolean id_found = false;
        int customer_idx = 0;
        Customer customer = null;
        while (true) {
            try {
                System.out.print("ID: ");
                id = Integer.parseInt(scanner.nextLine());
                if(id == 0) break;
                for(customer_idx = 0; customer_idx < customers.size(); customer_idx++) {
                    if (customers.get(customer_idx).getId() == id) {
                        id_found = true;
                        break;
                    }
                }
                if (id_found) break;
                throw new InappropriateNumberException();
            }
            catch(NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
            catch(InappropriateNumberException e) {
                System.out.println("Please enter a valid ID or 0 if you haven't got one.");
            }
        }
        
        if(id_found) {
            customer = customers.get(customer_idx);
        }

        return customer;
    }

    public static void displayProducts(ArrayList<Product> products) {
        for(int i = 0; i < products.size(); i++) {
            System.out.printf("%d %s\n", i+1, products.get(i).getName());
        }
    }
}
