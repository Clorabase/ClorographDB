package db.clorabase.clorograph;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UncheckedIOException;

import db.clorabase.clorograph.graphs.Graph;
import db.clorabase.clorograph.trees.Tree;

/**
 * Main driver and controller of the database.
 */
public class CloroGraph {
    private final File DB_DIR;

    /**
     * Initialize the database.
     *
     * @param databaseDir The directory to store the database.
     */
    public CloroGraph(File databaseDir) {
        DB_DIR = databaseDir;
    }

    /**
     * Deletes a datastore from the database.
     *
     * @param name The name of the datastore to delete.
     */
    public void delete(String name) {
        File file = new File(DB_DIR, name);
        file.delete();
    }

    /**
     * Opens a graph from the database, or creates a new one if it doesn't exist. The graph created
     * will be not be automatically saved. You manually need to call save() to save the graph.
     *
     * @param name The name of the graph to open.
     * @param <T>  The type of the graph.
     * @return The graph.
     */
    public <T extends Savable> Graph<T> createOrOpenGraph(String name, Graph<T> defaultGraph) {
        File file = new File(DB_DIR, name);
        if (file.exists()) {
            return openGraph(name);
        } else {
            return defaultGraph;
        }
    }

    /**
     * Opens a tree from the database, or creates a new one if it doesn't exist. The tree created
     * will be not be automatically saved. You manually need to call save() to save the tree.
     *
     * @param name The name of the graph to open.
     * @return The graph.
     */
    public <T extends Savable> Tree<T> createOrOpenTree(String name, Tree<T> defaultTree) {
        File file = new File(DB_DIR, name);
        if (file.exists()) {
            return openTree(name);
        } else {
            return defaultTree;
        }
    }

    /**
     * Opens a tree from the database.
     *
     * @param name The name of the tree to open.
     * @return The tree.
     */
    public <T extends Savable> Tree<T> openTree(String name) {
        File file = new File(DB_DIR, name);
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
            Tree<T> tree = (Tree) ois.readObject();
            ois.close();
            return tree;
        } catch (Exception e) {
            throw new IllegalArgumentException("No such tree : " + name);
        }
    }

    /**
     * Saves a graph to the database.
     *
     * @param name  The name of the graph to save.
     * @param graph The graph to save.
     */
    public <T extends Savable> void saveGraph(String name, Graph<T> graph) {
        File file = new File(DB_DIR, name);
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
            oos.writeObject(graph);
            oos.close();
        } catch (IOException e) {
            if (e instanceof NotSerializableException)
                throw new IllegalStateException("Implement the Graphalizable interface in your POJO class");
            else
                throw new UncheckedIOException(e);
        }
    }

    /**
     * Saves tree to the database.
     *
     * @param name The name of the tree to save.
     * @param tree The tree to save.
     */
    public <T extends Savable> void saveTree(String name, Tree<T> tree) {
        File file = new File(DB_DIR, name);
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
            oos.writeObject(tree);
            oos.close();
        } catch (IOException e) {
            if (e instanceof NotSerializableException)
                throw new IllegalStateException("Implement the Graphalizable interface in your POJO class");
            else
                throw new UncheckedIOException(e);
        }
    }

    /**
     * Opens a graph from the database.
     *
     * @param name The name of the graph to open.
     * @param <T>  The datatype of the graph.
     * @return The graph.
     */
    public <T extends Savable> Graph<T> openGraph(String name) {
        File file = new File(DB_DIR, name);
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
            Graph<T> graph = (Graph<T>) ois.readObject();
            ois.close();
            return graph;
        } catch (Exception e) {
            throw new IllegalArgumentException("No such graph : " + name);
        }
    }
}
