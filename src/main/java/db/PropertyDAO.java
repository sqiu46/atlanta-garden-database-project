package main.java.db;

import main.java.model.Property;

import java.util.List;

public interface PropertyDAO {
    List<Property> findAll();
    List<Property> findByOwner(String owner);
    List<Property> findByType(Property.PropertyType propertyType);
    List<Property> findByNameLike(String nameLike);
    List<Property> findUnapprovedOrdered(String orderByColumns, String searchTerm,
                                                String termLike);

    Property findByName(String propertyName);
    Property findByID(int propertyID);

    boolean insertProperty(Property property);
    boolean updateProperty(Property property);
    boolean deleteProperty(Property property);
}
