package main.java.db;

import main.java.model.PropertyView;

import java.util.List;

public interface PropertyViewService {
    List<PropertyView> findByOwnerOrdered(String orderByColumn, String searchTerm, String termLike, String owner);
    List<PropertyView> findAllConfirmedOrdered(String orderByColumn, String searchTerm, String termLike);
    List<PropertyView> findAllOtherConfirmedOrdered(String orderByColumn, String searchTerm, String termLike,
                                                    String owner);
}
