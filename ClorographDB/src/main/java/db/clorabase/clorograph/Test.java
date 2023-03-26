package db.clorabase.clorograph;

import java.io.File;
import java.util.Arrays;
import java.util.function.Predicate;

import db.clorabase.clorograph.graphs.RelationalGraph;
import db.clorabase.clorograph.graphs.SimpleGraph;

public class Test {
    public static void main(String[] args) {
        CloroGraph db = CloroGraph.getInstance(new File("C:\\Users\\rahil\\Desktop\\test"));
        RelationalGraph<Person> graph = (RelationalGraph) db.createOrOpenGraph(new RelationalGraph<>("final"));
    }
}

class Person implements Savable {
    private String id;
    private boolean test;
    private String name;

    public Person(String id, boolean test, String name) {
        this.id = id;
        this.test = test;
        this.name = name;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id='" + id + '\'' +
                ", test=" + test +
                ", name='" + name + '\'' +
                '}';
    }
}
