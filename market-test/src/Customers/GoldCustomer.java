// Başak Barış Salık 2017203072
// Ata Gündüz Uyan 2021502135

package Customers;

public class GoldCustomer extends Customer{
    

    private static double transactionPromotionRate = 0.02;
    private static double pointRate = 0.03;

    public GoldCustomer(String name, int id) {
        this(name, id, 0);
    }

    public GoldCustomer(String name, int id, double points) {
        super(name, id, points);
    }

    @Override
    public double transactionPromotion(double price) {
        return price * transactionPromotionRate;
    }

    @Override
    public double gainPoints(double netPayment) {
        double gainedPoints = netPayment * pointRate;
        
        // Rounding to ensure the amount shown is the correct amount.
        // Financial consequences disregarded.
        gainedPoints = Math.round(gainedPoints * 100.0) / 100.0;
        this.points += gainedPoints;
        return gainedPoints;
    }
}
