package main.java.model;

public class Has {
    private int propertyID;
    private String itemName;

    public Has(int propertyID, String itemName) {
        this.propertyID = propertyID;
        this.itemName = itemName;
    }

    public int getPropertyID() {
        return propertyID;
    }

    public void setPropertyID(int propertyID) {
        this.propertyID = propertyID;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
}
