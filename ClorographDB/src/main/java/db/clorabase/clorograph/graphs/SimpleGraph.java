package db.clorabase.clorograph.graphs;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import db.clorabase.clorograph.Savable;

public class SimpleGraph<T extends Savable> implements Graph<T> {
    private final String name;
    protected final int[][] MATRIX;
    protected final Map<String,T> key_data;

    public SimpleGraph(String name, int vertices) {
        this.name = name;
        MATRIX = new int[vertices][vertices];
        key_data = new LinkedHashMap<>(vertices);
    }


    public void addEdge(T from, T to) {
        addVertex(from);
        addVertex(to);
        var data = new ArrayList<>(key_data.values());
        MATRIX[data.indexOf(from)][data.indexOf(to)] = 1;
    }


    @Override
    public String getName() {
        return name;
    }

    @Override
    public void removeEdge(String from, String to) {
        var data = new ArrayList<>(key_data.keySet());
        MATRIX[data.indexOf(from)][data.indexOf(to)] = 0;
    }

    @Override
    public boolean hasEdge(String from, String to) {
        var data = new ArrayList<>(key_data.keySet());
        return MATRIX[data.indexOf(from)][data.indexOf(to)] == 1;
    }

    @Override
    public void addVertex(T vertex) {
        key_data.put(vertex.getId(),vertex);
    }

    @Override
    public void removeVertex(String key) {
        var data = new ArrayList<>(key_data.keySet());
        if (data.contains(key)) {
            int index = data.indexOf(key);
            for (int i = 0; i < MATRIX.length; i++) {
                MATRIX[i][index] = 0;
                MATRIX[index][i] = 0;
            }
            data.remove(key);
        }
    }

    @Override
    public boolean hasVertex(String key) {
        return key_data.containsKey(key);
    }

    @Override
    public List<T> getAdjacentVertices(String key) {
        var data = new ArrayList<>(key_data.keySet());
        var index = data.indexOf(key);
        List<T> adjacent = new ArrayList<T>();
        for (int i = 0; i < MATRIX.length; i++) {
            if (MATRIX[index][i] == 1)
                adjacent.add(key_data.get(data.get(i)));
        }
        return adjacent;
    }

    @Override
    public List<T> getAllVertices() {
        return new ArrayList<>(key_data.values());
    }

    @Override
    public Object[] search(Predicate<T> predicate) {
        return key_data.values().stream().filter(predicate).toArray();
    }

    @Override
    public String toString() {
        var data = new ArrayList<>(key_data.keySet());
        boolean[] visited = new boolean[key_data.size()];
        String str = "";
        for (int i = 0; i < key_data.size(); i++) {
            for (int j = 0; j < MATRIX.length; j++) {
                if (MATRIX[i][j] == 1) {
                    str += key_data.get(data.get(i)) + " --> " + key_data.get(data.get(j)) + "\n";
                    visited[i] = true;
                    visited[j] = true;
                }
            }
        }
        for (int i = 0; i < visited.length; i++) {
            if (!visited[i])
                str += key_data.get(data.get(i)) + "\n";
        }
        return str;
    }
}
