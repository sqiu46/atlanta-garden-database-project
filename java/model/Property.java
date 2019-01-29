package main.java.model;

public class Property {
    private int id;
    private String name;
    private float size;
    private boolean isCommercial;
    private boolean isPublic;
    private String street;
    private String city;
    private int zipcode;
    private String ownerUsername;
    private String approverUsername;

    public enum PropertyType {
        FARM, GARDEN, ORCHARD
    }

    private PropertyType propertyType;

    public Property(int id, String name, float size, boolean isCommercial, boolean isPublic, String street, String city,
                    int zipcode, PropertyType propertyType, String ownerUsername, String approverUsername) {
        this.id = id;
        this.name = name;
        this.size = size;
        this.isCommercial = isCommercial;
        this.isPublic = isPublic;
        this.street = street;
        this.city = city;
        this.zipcode = zipcode;
        this.propertyType = propertyType;
        this.ownerUsername = ownerUsername;
        this.approverUsername = approverUsername;
    }

    public Property(int id, String name, float size, boolean isCommercial, boolean isPublic, String street, String city,
                    int zipcode, PropertyType propertyType, String ownerUsername) {
        this(id, name, size, isCommercial, isPublic, street, city, zipcode, propertyType, ownerUsername,
                null);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getSize() {
        return size;
    }

    public void setSize(float size) {
        this.size = size;
    }

    public boolean getIsCommercial() {
        return isCommercial;
    }

    public void setCommercial(boolean commercial) {
        isCommercial = commercial;
    }

    public boolean getIsPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getZipcode() {
        return zipcode;
    }

    public void setZipcode(int zipcode) {
        this.zipcode = zipcode;
    }

    public String getOwnerUsername() {
        return ownerUsername;
    }

    public void setOwnerUsername(String ownerUsername) {
        this.ownerUsername = ownerUsername;
    }

    public String getApproverUsername() {
        return approverUsername;
    }

    public void setApproverUsername(String approverUsername) {
        this.approverUsername = approverUsername;
    }

    public PropertyType getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(PropertyType propertyType) {
        this.propertyType = propertyType;
    }
}
