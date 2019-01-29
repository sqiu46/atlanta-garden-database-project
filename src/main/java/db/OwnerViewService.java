package main.java.db;

import main.java.model.OwnerView;

import java.util.List;

public interface OwnerViewService {
    List<OwnerView> findAllOrdered(String orderByColumns, String searchTerm, String termLike);
}
