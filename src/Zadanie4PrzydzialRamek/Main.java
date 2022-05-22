package Zadanie4PrzydzialRamek;

import Zadanie4PrzydzialRamek.EqualAlloc.EqualSimulation;
import Zadanie4PrzydzialRamek.ProportionalAlloc.ProportionalSimulation;

import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        ArrayList<Integer> numOfPagesForEach = new ArrayList<>();
        numOfPagesForEach.add(5);
        numOfPagesForEach.add(10);
        numOfPagesForEach.add(15);
        numOfPagesForEach.add(20);

        //EqualSimulation s = new EqualSimulation(numOfPagesForEach, 20, 1000, 30);
        ProportionalSimulation s = new ProportionalSimulation(numOfPagesForEach, 20, 10, 30);

        System.out.println(s.run());
    }
}
