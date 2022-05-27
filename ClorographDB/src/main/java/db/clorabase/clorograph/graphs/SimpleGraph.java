package db.clorabase.clorograph.graphs;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class SimpleGraph<T> implements Graph<T> {
    protected final int[][] MATRIX;
    protected final List<T> data;

    public SimpleGraph(int vertices) {
        MATRIX = new int[vertices][vertices];
        data = new ArrayList<>(vertices);
    }

    public void addEdge(T from, T to) {
        addVertex(from);
        addVertex(to);
        MATRIX[data.indexOf(from)][data.indexOf(to)] = 1;
    }

    @Override
    public void addEdge(T from, T to,String relation) {
        throw new UnsupportedOperationException("Please use another addEdge method of class SimpleGraph");
    }

    @Override
    public void removeEdge(T from, T to) {
        MATRIX[data.indexOf(from)][data.indexOf(to)] = 0;
    }

    @Override
    public boolean hasEdge(T from, T to) {
        return MATRIX[data.indexOf(from)][data.indexOf(to)] == 1;
    }

    @Override
    public void addVertex(T vertex) {
        if (!data.contains(vertex))
            data.add(vertex);
    }

    @Override
    public void removeVertex(T vertex) {
        if (data.contains(vertex)) {
            int index = data.indexOf(vertex);
            for (int i = 0; i < MATRIX.length; i++) {
                MATRIX[i][index] = 0;
                MATRIX[index][i] = 0;
            }
            data.remove(vertex);
        }
    }

    @Override
    public boolean hasVertex(T vertex) {
        return data.contains(vertex);
    }

    @Override
    public List<T> getAdjacentVertices(T vertex) {
        var index = data.indexOf(vertex);
        List<T> adjacent = new ArrayList<T>();
        for (int i = 0; i < MATRIX.length; i++) {
            if (MATRIX[index][i] == 1)
                adjacent.add(data.get(i));
        }
        return adjacent;
    }

    @Override
    public Object[] search(Predicate<T> predicate) {
        return data.stream().filter(predicate).toArray();
    }

    @Override
    public String toString() {
        boolean[] visited = new boolean[data.size()];
        String str = "";
        for (int i = 0; i < data.size(); i++) {
            for (int j = 0; j < MATRIX.length; j++) {
                if (MATRIX[i][j] == 1) {
                    str += data.get(i) + " --> " + data.get(j) + "\n";
                    visited[i] = true;
                    visited[j] = true;
                }
            }
        }
        for (int i = 0; i < visited.length; i++) {
            if (!visited[i])
                str += data.get(i) + "\n";
        }
        return str;
    }
}
