package Customers;

import Exceptions.InappropriateNumberException;

public abstract class Customer {
    
    private String name;
    private int id;
    protected double points = 0;

    public Customer(String name, int id) {
        this(name, id, 0);
    }

    // All constructors, including sub-classes call this constructor.
    public Customer(String name, int id, double points) {
        this.name = name;
        this.id = id;
        this.points = points;
    }

    // Get Methods
    public String getName() {
        return this.name;
    }
    
    public int getId() {
        return this.id;
    }

    public double getPoints() {
        return this.points;
    }
    
    // Set Methods
    public void setId(int id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }

    public void setPoints(double points) {
        this.points = points;
    }

    // deducts points and returns remaining amount.
    public void deductPoints(double pointsUsed) throws InappropriateNumberException {
        
        double pointsBefore = this.points;
        
        // Rounding to ensure the amount shown is the correct amount.
        // Financial consequences disregarded.
        double pointsLeft = Math.round((pointsBefore - pointsUsed) * 100.0) / 100.0;
        
        if (pointsLeft < 0) throw new InappropriateNumberException("More than available or negative amount entered.");
        
        this.setPoints(pointsLeft);
    }
    
    public abstract double transactionPromotion(double price);

    public abstract double gainPoints(double payment);
}
