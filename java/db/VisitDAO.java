package main.java.db;

import main.java.model.Visit;

import java.util.List;

public interface VisitDAO {
    List<Visit> findAll();
    List<Visit> findByProperty(int propertyID);
    List<Visit> findByUsernameOrdered(String username, String orderByColumns, String searchTerm, String termLike);

    boolean updateVisit(Visit visit);
    boolean insertVisit(Visit visit);
    boolean deleteVisit(Visit visit);
}
