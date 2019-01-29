package main.java.db;

import main.java.model.FarmItem;

import java.util.List;

public interface FarmItemDAO {
    List<FarmItem> findAll();
    List<FarmItem> findApprovedOrdered(String orderByColumns, String searchTerm, String termLike);
    List<FarmItem> findPendingOrdered(String orderByColumns);

    boolean insertFarmItem(FarmItem farmItem);
    boolean updateFarmItem(FarmItem farmItem);
    boolean deleteFarmItem(FarmItem farmItem);
}
