package main.java.db;

import main.java.model.Has;

import java.util.List;

public interface HasDAO {
    List<Has> findAnimalsByProperty(String propertyID);
    List<Has> findCropsByProperty(String propertyID);

    boolean insertHas(Has has);
    boolean updateHas(Has has);
    boolean deleteHas(Has has);
}
