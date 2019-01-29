package main.java.db;

import main.java.model.VisitorView;

import java.util.List;

public interface VisitorViewService {
    List<VisitorView> findAllOrdered(String orderByColumns, String searchTerm, String termLike);
}
