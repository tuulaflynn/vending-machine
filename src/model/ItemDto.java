package model;

public class ItemDto {

    private int itemId;
    private String itemName;
    private double itemCost;
    private int itemStock;

    public ItemDto(int itemId, String itemName, double itemCost, int itemStock) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemCost = itemCost;
        this.itemStock = itemStock;
    }

    public int getItemId() {
        return this.itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return this.itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public double getItemCost() {
        return this.itemCost;
    }

    public void setItemCost(double itemCost) {
        this.itemCost = itemCost;
    }

    public int getItemStock() {
        return this.itemStock;
    }

    public void setItemStock(int itemStock) {
        this.itemStock = itemStock;
    }

    @Override
    public String toString() {
        return ("item id:" + this.itemId + " | item name:'" + this.itemName + "' | item cost:" + this.itemCost +
                " | item stock:" + this.itemStock + "\n");
    }

    public ItemDto copyItemObject() {
        ItemDto copiedItemDto = new ItemDto(this.itemId, this.itemName, this.itemCost, this.itemStock);
        return copiedItemDto;
    }

}
