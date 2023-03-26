package db.clorabase.clorograph.trees;

import java.io.Serializable;
import java.util.function.Predicate;

import db.clorabase.clorograph.CloroGraph;

public interface TreeNode<T> extends Serializable {
    TreeNode<T> add(T data);
    void remove(String id);
    boolean contains(String id);
    int size();
    boolean isEmpty();
    Object[] search(Predicate<T> predicate);
    TreeNode<T>[] children();
}
