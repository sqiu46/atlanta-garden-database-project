package main.java.model;

public class FarmItem {
    private String name;
    private boolean isApproved;

    public enum FarmItemType {
        ANIMAL, FRUIT, FLOWER, VEGETABLE, NUT
    }

    private FarmItemType itemType;

    public FarmItem(String name, boolean isApproved, FarmItemType itemType) {
        this.name = name;
        this.isApproved = isApproved;
        this.itemType = itemType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getIsApproved() {
        return isApproved;
    }

    public void setApproved(boolean approved) {
        isApproved = approved;
    }

    public FarmItemType getItemType() {
        return itemType;
    }

    public String getItemTypeStr() {return  itemType.name(); }

    public void setItemType(FarmItemType itemType) {
        this.itemType = itemType;
    }
}
