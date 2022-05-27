package db.clorabase.clorograph.trees;

import java.util.function.Predicate;

public interface TreeNode<T> {
    TreeNode<T> add(T data);
    void remove(String id);
    boolean contains(String id);
    int size();
    boolean isEmpty();
    Object[] search(Predicate<T> predicate);
    TreeNode<T>[] children();
}
