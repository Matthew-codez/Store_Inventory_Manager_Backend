package domain;

public class Inventory {
    private Long inventoryId;
    private Category category;
    private Product product;
    private Supplier supplier;
    private int quantityInStock;
    private int minimumStockLevel;
    private int maximumStockLevel;
    private double unitPrice;
    private String lastRestockedDate;
    private String location;

    public Inventory() {}

    public Long getInventoryId() {
        return inventoryId; }

    public void setInventoryId(Long inventoryId) {
        this.inventoryId = inventoryId; }

    public Category getCategory() {
        return category; }

    public void setCategory(Category category) {
        this.category = category; }

    public Product getProduct() {
        return product; }

    public void setProduct(Product product) {
        this.product = product; }

    public Supplier getSupplier() {
        return supplier; }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier; }

    public int getQuantityInStock() {
        return quantityInStock; }

    public void setQuantityInStock(int quantityInStock) { this.quantityInStock = quantityInStock; }
    public int getMinimumStockLevel() { return minimumStockLevel; }
    public void setMinimumStockLevel(int minimumStockLevel) { this.minimumStockLevel = minimumStockLevel; }
    public int getMaximumStockLevel() { return maximumStockLevel; }
    public void setMaximumStockLevel(int maximumStockLevel) { this.maximumStockLevel = maximumStockLevel; }
    public double getUnitPrice() { return unitPrice; }
    public void setUnitPrice(double unitPrice) { this.unitPrice = unitPrice; }
    public String getLastRestockedDate() { return lastRestockedDate; }
    public void setLastRestockedDate(String lastRestockedDate) { this.lastRestockedDate = lastRestockedDate; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
}
