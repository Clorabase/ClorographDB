package db.clorabase.clorograph.graphs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import db.clorabase.clorograph.CloroGraph;
import db.clorabase.clorograph.Savable;

public class RelationalGraph<T extends Savable> implements Graph<T>{
    private final String name;
    private final Map<T,Map<T,String>> nodes = new Hashtable<>();
    private final Map<String,T> idMap = new HashMap<>();

    public RelationalGraph(String name) {
        this.name = name;
    }

    public void addEdge(T from, T to,String relation) {
        addVertex(from);
        addVertex(to);
        nodes.get(from).put(to,relation);
    }

    public void setRelationShip(String from, String to, String relationship) {
        if (!hasEdge(from,to))
            throw new IllegalStateException("No edge between " + from + " and " + to);

        nodes.get(idMap.get(from)).put(idMap.get(to),relationship);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void removeEdge(String from, String to) {
        if (!hasEdge(from,to))
            throw new IllegalStateException("No edge between " + from + " and " + to);

        nodes.get(idMap.get(from)).remove(idMap.get(to));
    }

    @Override
    public boolean hasEdge(String from, String to) {
        return nodes.get(idMap.get(from)).containsKey(idMap.get(to));
    }

    @Override
    public void addVertex(T vertex) {
        idMap.put(vertex.getId(),vertex);
        nodes.put(vertex,new HashMap<>());
    }

    @Override
    public void removeVertex(String key) {
        nodes.remove(idMap.get(key));
        idMap.remove(key);
    }

    @Override
    public boolean hasVertex(String key) {
        return nodes.containsKey(idMap.get(key));
    }

    @Override
    public List<T> getAdjacentVertices(String key) {
        return new LinkedList<>(nodes.get(idMap.get(key)).keySet());
    }

    @Override
    public List<T> getAllVertices() {
        return new ArrayList<>(nodes.keySet());
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