// Başak Barış Salık 2017203072
// Ata Gündüz Uyan 2021502135

package Customers;

public class RegularCustomer extends Customer {

    private static double promotionLimit = 40;
    private static double transactionPromotionRate = 0.05;
    private static double pointRate = 0.02;
    private static double pointLimit = 100;

    public RegularCustomer(String name, int id) {
        this(name, id, 0);
    }

    public RegularCustomer(String name, int id, double points) {
        super(name, id, points);
    }

    @Override
    public double transactionPromotion(double price) {
        // No discount if it's below the limit.
        return Math.max((price - promotionLimit) * transactionPromotionRate, 0);
    }

    @Override
    public double gainPoints(double netPayment) {
        
        double gainedPoints = (netPayment - pointLimit) * pointRate;

        // Rounding to ensure the amount shown is the correct amount.
        // Financial consequences disregarded.
        gainedPoints = Math.round(gainedPoints * 100.0) / 100.0;
        
        // No points if it's below the limit.
        this.points += Math.max(gainedPoints, 0);
        
        return Math.max(gainedPoints, 0);
    }
}
