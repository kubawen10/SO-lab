package Zadanie3ZastepowanieStron;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Page> references = new ArrayList<>();
        references.add(new Page(1, 1));
        references.add(new Page(1, 2));
        references.add(new Page(1, 3));
        references.add(new Page(1, 4));
        references.add(new Page(1, 1));
        references.add(new Page(1, 2));
        references.add(new Page(1, 5));
        references.add(new Page(1, 1));
        references.add(new Page(1, 2));
        references.add(new Page(1, 3));
        references.add(new Page(1, 4));
        references.add(new Page(1, 5));


        Simulation s = new Simulation(1, 5, 4, 80000,10);
        s.setReferences(references);

        //s.runAll();
        s.runALRU();
    }
}
