package Zadanie4PrzydzialRamek;

import Zadanie3ZastepowanieStron.Page;
import Zadanie3ZastepowanieStron.Process;

import Zadanie4PrzydzialRamek.EqualAlloc.EqualSimulation;
import Zadanie4PrzydzialRamek.PageFaultFrequencyAlloc.PFFSimulation;
import Zadanie4PrzydzialRamek.ProportionalAlloc.ProportionalSimulation;
import Zadanie4PrzydzialRamek.WorkingSetSizeAlloc.WSSSimulation;


import java.util.ArrayList;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        int numberOfFrames;
        ArrayList<Integer> numOfPagesForEach;
        ArrayList<Process> processes;
        ArrayList<Page> unitedReferences;
        int numOfReferences;
        int localChance;

        numOfPagesForEach = new ArrayList<>();
        int j = 3;
        for (int i = 0; i <= 9; i++) {
            numOfPagesForEach.add(j);
            j++;
        }

        numOfReferences = 10000;
        localChance = 30;
        numberOfFrames = 25;
        processes = generateProcesses(numOfPagesForEach, numOfReferences, localChance);
        unitedReferences = referencesUnion(processes);

        //PFFControler(processes, numberOfFrames, unitedReferences);
        WSSControler(processes, numberOfFrames, unitedReferences);
        //EqualAndProportionalControler(processes, unitedReferences);
    }

    public static void WSSControler(ArrayList<Process> processes, int numberOfFrames, ArrayList<Page> unitedReferences){
        for (int i = 20; i < 161; i += 20) {
            WSSSim(processes, numberOfFrames, unitedReferences, i);
        }
    }

    public static void PFFControler(ArrayList<Process> processes, int numberOfFrames, ArrayList<Page> unitedReferences) {
        //zawsze dochodza do ok 30k bledow w zaleznosci od ilosci ramek thrashing rozny
        //dt ma spore znaczenie, im wieksze tym mniej bledow ale jest granica

        for (int i = 20; i < 161; i += 20) {
            PFFSim(processes, numberOfFrames, unitedReferences, 2, 9, i);
        }

        for (int i = 20; i < 161; i += 20) {
            PFFSim(processes, numberOfFrames, unitedReferences, 2, 4, i);
        }

        for (int i = 20; i < 161; i += 20) {
            PFFSim(processes, numberOfFrames, unitedReferences, 4, 8, i);
        }
    }

    public static void EqualAndProportionalControler(ArrayList<Process> processes, ArrayList<Page> unitedReferences) {
        //equal lepsze bo male procesy wykonaja sie szybciej a pozniej tylko te duze
        for (int i = 25; i < 56; i += 5) {
            System.out.println("Number of frames: " + i);
            EqualSim(processes, i, unitedReferences);
            ProportionalSim(processes, i , unitedReferences);
        }
    }


    public static void WSSSim(ArrayList<Process> processes, int numberOfFrames, ArrayList<Page> unitedReferences, int dt){
        //gwaltownie spada thrashing a ppozniej rosnie
        System.out.println("WSS SIMULATION: dt-" + dt + " \t\tNumberOfFrames: " + numberOfFrames);
        WSSSimulation s = new WSSSimulation(processes, numberOfFrames, unitedReferences, dt);
        s.run();
        System.out.println();
    }

    public static void PFFSim(ArrayList<Process> processes, int numberOfFrames, ArrayList<Page> unitedReferences, int l, int u, int dt) {
        //granica odpowiednich parametrow jest, maly zakres daje lepsze rezultaty chyba
        System.out.println("PFF SIMULATION: l-" + l + " u-" + u + " dt-" + dt + " \t\tNumberOfFrames: " + numberOfFrames);
        PFFSimulation s = new PFFSimulation(processes, numberOfFrames, unitedReferences, l, u, dt);
        s.run();
        System.out.println();
    }

    public static void EqualSim(ArrayList<Process> processes, int numberOfFrames, ArrayList<Page> unitedReferences) {
        System.out.print("EQUAL SIMULATION: ");
        EqualSimulation s = new EqualSimulation(processes, numberOfFrames, unitedReferences);
        System.out.println("Number of faults: " + s.run());
    }

    public static void ProportionalSim(ArrayList<Process> processes, int numberOfFrames, ArrayList<Page> unitedReferences) {
        System.out.print("PROPORTIONAL SIMULATION: ");
        ProportionalSimulation s = new ProportionalSimulation(processes, numberOfFrames, unitedReferences);
        System.out.println("Number of faults: " + s.run());
        System.out.println();
    }

    public static ArrayList<Process> generateProcesses(ArrayList<Integer> numOfPagesForEach, int numOfReferences, int localChance) {
        ArrayList<Process> ret = new ArrayList<>();
        for (int i = 0; i < numOfPagesForEach.size(); i++) {
            Process p = new Process(i, numOfPagesForEach.get(i));
            p.generateReferences(numOfReferences, localChance);
            ret.add(p);
        }

        return ret;
    }

    public static ArrayList<Page> referencesUnion(ArrayList<Process> processes) {
        ArrayList<Page> unitedReferences = new ArrayList<>();
        Random r = new Random();

        //list of each process' cur reference index
        ArrayList<Integer> indexes = new ArrayList<>();
        for (int i = 0; i < processes.size(); i++) {
            indexes.add(0);
        }

        //track references lists of processes that are already added
        int wholeAdded = 0;
        int chooseProcess;
        int curIndex;
        boolean added;

        //while there are references that arent fully added
        while (wholeAdded < indexes.size()) {
            added = false;
            //choose random process that will add its reference
            chooseProcess = r.nextInt(processes.size());
            curIndex = indexes.get(chooseProcess);

            while (!added) {
                //if it isnt fully added
                if (curIndex < processes.get(chooseProcess).getReferences().size()) {
                    unitedReferences.add(processes.get(chooseProcess).getReferences().get(curIndex));
                    curIndex++;

                    //increasing current reference index
                    indexes.set(chooseProcess, curIndex);

                    //if current process' references has been fully added
                    if (curIndex == processes.get(chooseProcess).getReferences().size()) {
                        wholeAdded++;
                    }
                    added = true;
                } else {
                    //go to next process in list
                    chooseProcess = (chooseProcess + 1) % processes.size();
                    curIndex = indexes.get(chooseProcess);
                }
            }
        }
        return unitedReferences;
    }
}
