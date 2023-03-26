package db.clorabase.clorograph.trees;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import db.clorabase.clorograph.Savable;

public class Tree<T extends Savable> implements TreeNode<T> {
    private final T data;
    private final Map<String, Tree<T>> nodes = new Hashtable<>();

    public Tree(T data) {
        this.data = data;
    }

    @Override
    public TreeNode<T> add(T data) {
        var node = new Tree<>(data);
        nodes.putIfAbsent(data.getId(), node);
        return node;
    }

    @Override
    public void remove(String id) {
        nodes.remove(id);
    }

    @Override
    public boolean contains(String id) {
        return nodes.containsKey(id);
    }

    @Override
    public int size() {
        return nodes.size();
    }

    @Override
    public boolean isEmpty() {
        return nodes.isEmpty();
    }

    @Override
    public TreeNode<T>[] children() {
        return nodes.values().toArray(new TreeNode[0]);
    }


    @Override
    public String toString() {
        return "Node{" +
                "data=" + data +
                ", nodes=" + nodes +
                '}';
    }

    @Override
    public Object[] search(Predicate<T> predicate) {
        List<T> result = new ArrayList<>();
        traverse(result,nodes,predicate);
        return result.toArray();
    }

    private void traverse(List<T> results, Map<String, Tree<T>> map, Predicate<T> predicate){
        if (map.size() == 0)
            return;

        for (var node : map.values()) {
            if (predicate.test(node.data))
                results.add(node.data);
            traverse(results, node.nodes, predicate);
        }
    }
}
