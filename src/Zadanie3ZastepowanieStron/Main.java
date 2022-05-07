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

        List<Page> references2 = new ArrayList<>();
        references2.add(new Page(1, 1));
        references2.add(new Page(1, 2));
        references2.add(new Page(1, 3));
        references2.add(new Page(1, 4));
        references2.add(new Page(1, 1));
        references2.add(new Page(1, 2));
        references2.add(new Page(1, 5));
        references2.add(new Page(1, 3));
        references2.add(new Page(1, 2));
        references2.add(new Page(1, 1));
        references2.add(new Page(1, 4));
        references2.add(new Page(1, 5));
        //s.setReferences(references2);

        Simulation s;

        //same number of pages, 0 percent chance for localReferences, different number of frames (same references)
        Process process = new Process(1, 20);

        //0 percent chance for localReferences, same references, different number of frames
        process.generateReferences(100000, 0);
        Memory m;

        m = new Memory(18);
        s = new Simulation(process, m);
        s.runAll();

        m = new Memory(14);
        s = new Simulation(process, m);
        s.runAll();

        m = new Memory(10);
        s = new Simulation(process, m);
        s.runAll();

        m = new Memory(6);
        s = new Simulation(process, m);
        s.runAll();

        m = new Memory(3);
        s = new Simulation(process, m);
        s.runAll();
        System.out.println();


        //same number of pages, same number of frames, different localReferences chance
        System.out.println("Local chance: " + 0);
        s = new Simulation(1, 20, 17, 100000,0);
        s.runAll();
        s = new Simulation(1, 20, 10, 100000,0);
        s.runAll();
        s = new Simulation(1, 20, 5, 100000,0);
        s.runAll();


        System.out.println("\nLocal chance: " + 10);
        s = new Simulation(1, 20, 17, 100000,10);
        s.runAll();
        s = new Simulation(1, 20, 10, 100000,10);
        s.runAll();
        s = new Simulation(1, 20, 5, 100000,10);
        s.runAll();


        System.out.println("\nLocal chance: " + 20);
        s = new Simulation(1, 20, 17, 100000,20);
        s.runAll();
        s = new Simulation(1, 20, 10, 100000,20);
        s.runAll();
        s = new Simulation(1, 20, 5, 100000,20);
        s.runAll();


        System.out.println("\nLocal chance: " + 30);
        s = new Simulation(1, 20, 17, 100000,30);
        s.runAll();
        s = new Simulation(1, 20, 10, 100000,30);
        s.runAll();
        s = new Simulation(1, 20, 5, 100000,30);
        s.runAll();


        System.out.println("\nLocal chance: " + 40);
        s = new Simulation(1, 20, 17, 100000,40);
        s.runAll();
        s = new Simulation(1, 20, 10, 100000,40);
        s.runAll();
        s = new Simulation(1, 20, 5, 100000,40);
        s.runAll();
    }
}
