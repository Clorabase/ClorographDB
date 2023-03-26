package db.clorabase.clorograph.graphs;

import java.io.Serializable;
import java.util.List;
import java.util.function.Predicate;

import db.clorabase.clorograph.CloroGraph;
import db.clorabase.clorograph.Savable;

public interface Graph<T extends Savable> extends Serializable {
    String getName();
    void removeEdge(String from, String to);
    boolean hasEdge(String from, String to);
    void addVertex(T vertex);
    void removeVertex(String key);
    boolean hasVertex(String key);
    List<T> getAdjacentVertices(String key);
    List<T> getAllVertices();
    Object[] search(Predicate<T> predicate);

    default void commit(CloroGraph db) {
        db.saveGraph(this);
    }
}
