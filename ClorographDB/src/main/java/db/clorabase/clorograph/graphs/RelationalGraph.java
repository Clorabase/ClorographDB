package db.clorabase.clorograph.graphs;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import db.clorabase.clorograph.Savable;

public class RelationalGraph<T extends Savable> implements Graph<T>{
    private final Map<T,Map<T,String>> nodes = new Hashtable<>();

    @Override
    public void addEdge(T from, T to,String relation) {
        addVertex(from);
        addVertex(to);
        nodes.get(from).put(to,relation);
    }

    public void setRelationShip(T from, T to, String relationship) {
        if (!hasEdge(from,to))
            throw new IllegalStateException("No edge between " + from + " and " + to);

        nodes.get(from).put(to,relationship);
    }

    @Override
    public void removeEdge(T from, T to) {
        if (!hasEdge(from,to))
            throw new IllegalStateException("No edge between " + from + " and " + to);

        nodes.get(from).remove(to);
    }

    @Override
    public boolean hasEdge(T from, T to) {
        return nodes.get(from).containsKey(to);
    }

    @Override
    public void addVertex(T vertex) {
        nodes.putIfAbsent(vertex,new HashMap<>());
    }

    @Override
    public void removeVertex(T vertex) {
        nodes.remove(vertex);
    }

    @Override
    public boolean hasVertex(T vertex) {
        return nodes.containsKey(vertex);
    }

    @Override
    public List<T> getAdjacentVertices(T vertex) {
        return new LinkedList<>(nodes.get(vertex).keySet());
    }

    @Override
    public Object[] search(Predicate<T> predicate) {
        return nodes.keySet().stream().filter(predicate).toArray();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        nodes.forEach((key,value) -> {
            if (value.isEmpty())
                builder.append(key.getId() + "\n");
            else {
                value.forEach((node,relationship) -> {
                    builder.append(key.toString() + " --| " + relationship + " |--> " + node.toString() + "\n");
                });
            }
        });
        return builder.toString();
    }

    public T getDataById(String id) {
        return (T) nodes.keySet().stream().filter(vertex -> id.equals(vertex.getId())).findFirst().get();
    }
}