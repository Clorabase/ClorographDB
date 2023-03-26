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
    private static CloroGraph instance;

    private CloroGraph(File file) {
        DB_DIR = file;
    }

    /**
     * Initialize the database.
     *
     * @param directory The directory to store the database.
     */
    public static CloroGraph getInstance(File directory) {
        if (instance == null) {
            instance = new CloroGraph(directory);
        }
        return instance;
    }

    /**
     * Deletes a graph or tree from the database.
     *
     * @param name The name of the graph or tree to delete. It must end with .graph or .tree.
     */
    public void delete(String name) {
        File file = new File(DB_DIR, name);
        file.delete();
    }

    /**
     * Opens a graph from the database, or creates a new one if it doesn't exist.
     *
     * @param <T>  The graph that will be created
     * @return The graph that is just created. You need to save it again after making any changes
     */
    public <T extends Savable> Graph<T> createOrOpenGraph(Graph<T> defaultGraph) {
        File file = new File(DB_DIR, defaultGraph.getName() + ".graph");
        if (file.exists()) {
            return openGraph(defaultGraph.getName());
        } else {
            saveGraph(defaultGraph);
            return defaultGraph;
        }
    }

    /**
     * Opens a tree from the database, or creates a new one if it doesn't exist.
     *
     * @param name The name of the tree to open.
     * @return The tree. Any changes to it will not be saved automatically. You need to save it by calling saveTree().
     */
    public <T extends Savable> Tree<T> createOrOpenTree(String name, Tree<T> defaultTree) {
        File file = new File(DB_DIR, name + ".tree");
        if (file.exists()) {
            return openTree(name);
        } else {
            saveTree(name,defaultTree);
            return defaultTree;
        }
    }

    /**
     * Opens a tree from the database.
     *
     * @param name The name of the tree to open.
     * @return The tree.
     */
    public  <T extends Savable> Tree<T> openTree(String name) {
        File file = new File(DB_DIR, name + ".tree");
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
     * @param graph The graph to save.
     */
    public <T extends Savable> void saveGraph(Graph<T> graph) {
        File file = new File(DB_DIR, graph.getName() + ".graph");
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
        File file = new File(DB_DIR, name + ".tree");
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
            oos.writeObject(tree);
            oos.close();
        } catch (IOException e) {
            if (e instanceof NotSerializableException)
                throw new IllegalStateException("Implement the savable interface in your POJO class");
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
        File file = new File(DB_DIR, name + ".graph");
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