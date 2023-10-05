import java.util.ArrayList;
import java.util.Scanner;

import Customers.Customer;
import Exceptions.*;

public class Transaction {

    private Scanner scanner;
    
    private ArrayList<Object> basket = new ArrayList<Object>();
    private Customer customer = null;

    private boolean isAutomatic = false;

    private double totalPaymentBeforeTax = 0;
    private double totalTax = 0;
    private double totalPaymentBeforeDiscounts = 0;
    private double totalPaymentAfterProductPromotion = 0;
    private double totalProductPromotion = 0;
    private double transactionPromotion = 0;
    private double totalPayment = 0;

    private double points;
    private double gainedPoints;
    private double pointsUsed;
    
    public Transaction(Scanner scanner) {
        this.scanner = scanner;
    };
    
    public Transaction(Scanner scanner, Customer customer) {
        this.scanner = scanner;
        this.customer = customer;
    }
    
    public Transaction(Scanner scanner, Customer customer, boolean isAutomatic) {
        this.scanner = scanner;
        this.customer = customer;
        this.isAutomatic = isAutomatic;
    }

    public void addToBasket(int id, ArrayList<Product> products, Integer amount) {
        try {
            if (amount < 0 ) {
                throw new InvalidAmountException();
            }
            if (id > products.size()) {
                throw new InappropriateNumberException();
            }
            basket.add(products.get(id));
            basket.add(amount);   
        }
        
        catch(InvalidAmountException e) {
            System.out.println("Invalid amount. Please enter a valid number.");
        }

        catch(InvalidIdException e) {
            System.out.println("Invalid item ID. Please enter a valid ID.");
        }
    }
    

    public double invoice(){
        
        totalPaymentBeforeTax = 0;
        totalTax = 0;
        totalPaymentBeforeDiscounts = 0;
        totalPaymentAfterProductPromotion = 0;
        totalProductPromotion = 0;
        
        for (int i = 0; i < basket.size(); i += 2) {
            
            Product p = (Product) basket.get(i);
            Integer amount = (Integer) basket.get(i+1);
            
            double price = p.getPrice() * amount;
            double tax = p.getTax() * amount;

            totalPaymentBeforeTax += price;
            totalTax += tax;
            
            double paymentBeforeDiscounts = price + tax;
            totalPaymentBeforeDiscounts += paymentBeforeDiscounts;

            double productPromotion = p.getDiscount() * amount;

            totalProductPromotion += productPromotion;
            totalPaymentAfterProductPromotion += paymentBeforeDiscounts - productPromotion;
            
        }

        if(customer != null) {
            transactionPromotion = customer.transactionPromotion(totalPaymentAfterProductPromotion);
        }
        
        totalPayment = totalPaymentAfterProductPromotion - transactionPromotion;

        displayPayment();
        
        if (this.customer != null && !isAutomatic) {
            totalPayment = calculatePoints(totalPayment);
        }

        return totalPayment;
    }

    private double calculatePoints(double totalPayment) {
        
        
        pointsUsed = 0;
        

        gainedPoints = customer.gainPoints(totalPayment);
        points = customer.getPoints();

        System.out.printf("Points gained: %.2f\n\nYou have %.2f points, please enter the amount you want to use.\nAmount: ",
            gainedPoints, points);
        
        while (true) {
            try {
                pointsUsed = Double.parseDouble(scanner.nextLine());
                if (pointsUsed < 0 || pointsUsed > points || pointsUsed > totalPayment) {
                    throw new InappropriateNumberException();
                }

                customer.deductPoints(pointsUsed);
                totalPayment = totalPayment - pointsUsed;

                System.out.printf("\nRemaining payment: %.2f\n", totalPayment);
                if (pointsUsed != 0) {
                    System.out.printf("Points left: %.2f\n\n", customer.getPoints());
                }
                
                break;
            } 
            catch (InappropriateNumberException e) {
                System.out.print("Invalid amount. Please enter again.\nAmount: ");
            }
            catch (NumberFormatException e) {
                System.out.print("Invalid number. Please enter again.\nAmount: ");
            }
        } // while end

        return totalPayment;////////////////////////////amount -> totalPayment

    }

    private void displayPayment() {
        System.out.printf("Price sum: %.2f\nTotal tax: %.2f\nTotal price after tax: %.2f\nProduct promotions: %.2f\nTransaction promotion: %.2f\n\nTotal payment after discounts: %.2f\n\n",
            totalPaymentBeforeTax, totalTax, totalPaymentBeforeDiscounts, totalProductPromotion, transactionPromotion, totalPayment);
    }

    // Getters-Setters
    public ArrayList<Object> getBasket() {
        return this.basket;
    }

    public void setBasket(ArrayList<Object> basket) {
        this.basket = basket;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public double getTotalPaymentBeforeTax() {
        return totalPaymentBeforeTax;
    }

    public void setTotalPaymentBeforeTax(double totalPaymentBeforeTax) {
        this.totalPaymentBeforeTax = totalPaymentBeforeTax;
    }

    public double getTotalTax() {
        return totalTax;
    }

    public void setTotalTax(double totalTax) {
        this.totalTax = totalTax;
    }

    public double getTotalPaymentBeforeDiscounts() {
        return totalPaymentBeforeDiscounts;
    }

    public void setTotalPaymentBeforeDiscounts(double totalPaymentBeforeDiscounts) {
        this.totalPaymentBeforeDiscounts = totalPaymentBeforeDiscounts;
    }

    public double getTotalPaymentAfterProductPromotion() {
        return totalPaymentAfterProductPromotion;
    }

    public void setTotalPaymentAfterProductPromotion(double totalPaymentAfterProductPromotion) {
        this.totalPaymentAfterProductPromotion = totalPaymentAfterProductPromotion;
    }

    public double getTotalProductPromotion() {
        return totalProductPromotion;
    }

    public void setTotalProductPromotion(double totalProductPromotion) {
        this.totalProductPromotion = totalProductPromotion;
    }

    public double getTransactionPromotion() {
        return transactionPromotion;
    }

    public void setTransactionPromotion(double transactionPromotion) {
        this.transactionPromotion = transactionPromotion;
    }

    public double getTotalPayment() {
        return totalPayment;
    }

    public void setTotalPayment(double totalPayment) {
        this.totalPayment = totalPayment;
    }

    public double getPoints() {
        return points;
    }

    public void setPoints(double points) {
        this.points = points;
    }

    public double getGainedPoints() {
        return gainedPoints;
    }

    public void setGainedPoints(double gainedPoints) {
        this.gainedPoints = gainedPoints;
    }

    public double getPointsUsed() {
        return pointsUsed;
    }

    public void setPointsUsed(double pointsUsed) {
        this.pointsUsed = pointsUsed;
    }

    
    
}
