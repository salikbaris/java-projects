public class Product {
    
    // Instance variables
    private String name;
    private double price;
    private boolean isLuxType;
    private double promotionRate;
    
    // These are set by the previous instance variables,
    // so their set methods are private.
    // I chose to keep them as variables, not methods because
    // if an object of class Product is to be used,
    // then its tax and discount has to be used.
    // This assumes tax of a product will not change during working hours :)
    private double tax;
    private double discount;
    
    // Static variables
    private static double normalTaxRate = 0.10;
    private static double luxTaxRate = 0.20;

    
    // Constructor
    public Product(String name, double price, boolean isLuxType, double promotionRate) {
        this.setName(name);
        this.setPrice(price);
        this.setLuxType(isLuxType);
        this.setPromotionRate(promotionRate);
        
        // These are set by the given parameters.
        this.setTax();
        this.setDiscount();
    }




    // Get methods
    public String getName() {
        return this.name;
    }

    public double getPrice() {
        return this.price;
    }
    
    public boolean isLuxType() {
        return this.isLuxType;
    }
    
    public double getPromotionRate() {
        return this.promotionRate;
    }

    public static double getNormalTaxRate() {
        return Product.normalTaxRate;
    }

    public static double getLuxTaxRate() {
        return Product.luxTaxRate;
    }

    public double getTax() {
        return this.tax;
    }

    public double getDiscount() {
        return this.discount;
    }

    // Set methods
    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setLuxType(boolean isLuxType) {
        this.isLuxType = isLuxType;
    }
    
    public void setPromotionRate(double promotionRate) {
        this.promotionRate = promotionRate;
        // Set the discount again as the rate is changed.
        this.setDiscount();
    }

    public static void setNormalTaxRate(double normalTaxRate) {
        Product.normalTaxRate = normalTaxRate;
    }

    public static void setLuxTaxRate(double luxTaxRate) {
        Product.luxTaxRate = luxTaxRate;
    }

    // Tax and discount variables' set methods are private because
    // explicit call to these methods are not expected. 
    private void setTax() {
        this.tax = price * (this.isLuxType ? Product.luxTaxRate : Product.normalTaxRate);
    }

    private void setDiscount() {
        this.discount = (this.price + this.tax) * this.promotionRate;
    }

    

    
}
