package db.clorabase.clorograph.graphs;

import java.io.Serializable;
import java.util.List;
import java.util.function.Predicate;

public interface Graph<T> extends Serializable {
    void addEdge(T from, T to,String relation);
    void removeEdge(T from, T to);
    boolean hasEdge(T from, T to);
    void addVertex(T vertex);
    void removeVertex(T vertex);
    boolean hasVertex(T vertex);
    List<T> getAdjacentVertices(T vertex);
    Object[] search(Predicate<T> predicate);
}
