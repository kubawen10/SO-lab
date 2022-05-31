package Zadanie4PrzydzialRamek.PageFaultFrequencyAlloc;

import Zadanie3ZastepowanieStron.Memory;
import Zadanie3ZastepowanieStron.Page;
import Zadanie3ZastepowanieStron.Process;
import Zadanie4PrzydzialRamek.ProportionalAlloc.ProportionalAlgorithm;
import Zadanie4PrzydzialRamek.Simulation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class PFFSimulation extends Simulation {
    class Env implements Comparable<Env> {
        Process p;
        Memory memory;
        boolean isActive;
        int curReference;

        LinkedList<Integer> faultTimes = new LinkedList<>();

        public Env(Process p, int numOfFrames) {
            this.p = p;
            memory = new Memory(numOfFrames);
            isActive = true;
            curReference = 0;
        }

        public int PFF() {
            if (!isActive) return u + 1;

            return faultTimes.size();
        }

        public void removeOldFaultTime() {
            if (faultTimes.isEmpty()) return;

            if (faultTimes.getFirst() < t - dt) {
                faultTimes.removeFirst();
            }
        }

        public boolean serve(Page page) {
            if (!memory.pageInMemory(page)) {
                int indexForPage = pageReplacementAlgorithm.insertPageIndex(memory, p.getReferences(), curReference);
                memory.setFrame(indexForPage, page);
                faultTimes.addLast(t);

                curReference++;
                return false;
            }
            curReference++;
            return true;
        }

        public void giveOneFrame() {
            if (!isActive) {
                numOfDeactivated--;
                isActive = true;
                memory = new Memory(1);
            } else {
                memory.addFrames(1);
            }

            freeFrames--;
        }

        public void freeOneFrame() {
            if (memory.getNumberOfFrames() <= 1) {
                return;
            }

            Memory newM = new Memory(memory.getNumberOfFrames() - 1);
            List<Page> frames = memory.getFrames();

            for (int i = 0; i < frames.size() - 1; i++) {
                newM.setFrame(i, frames.get(i));
            }
            memory = newM;
            freeFrames++;
        }

        private void freeAllFrames() {
            freeFrames += memory.getNumberOfFrames();
        }

        public void deactivate() {
            isActive = false;
            numOfDeactivated++;
            faultTimes = new LinkedList<>();

            freeAllFrames();
        }

        @Override
        public int compareTo(Env o) {
            return Integer.compare(o.PFF(), PFF());
        }

        @Override
        public String toString() {
            return "Id: " + p.getProcessId() + " IsActive: " + isActive + " Faults: " + faultTimes + " PFF: " + PFF() + " Memory: " + memory + "\n";
        }
    }

    private int l;
    private int u;
    private int dt;
    private final int uPlus = 2;

    private ArrayList<Env> envs;
    private int freeFrames;
    private int numOfDeactivated;
    private int t;

    private LinkedList<Integer> thrashingDetection;
    private int thrashingDetectionTime = 50;
    private int thrashingSum = 0;


    public PFFSimulation(ArrayList<Process> processes, int numberOfFrames, ArrayList<Page> unitedReferences, int l, int u, int dt) {
        super(processes, numberOfFrames, unitedReferences);
        this.l = l;
        this.u = u;
        this.dt = dt;
        freeFrames = numberOfFrames;
        numOfDeactivated = 0;
        thrashingDetection = new LinkedList<>();
    }

    @Override
    public int run() {
        numberOfFaults = 0;
        t = 0;

        initializeMemories();
        copyUnitedReferences();

        while (!unitedReferences.isEmpty()) {
            int curPageIndex = getFirstActive();
            //System.out.println("Time: " + t);

            if (curPageIndex != -1) {
                Page curPage = unitedReferences.get(curPageIndex);
                //System.out.println(curPage);
                boolean served = envs.get(curPage.getProcessId()).serve(curPage);

                unitedReferences.remove(curPageIndex);
                if (!served) {
                    //System.out.println("Page fault occurred");
                    numberOfFaults++;
                    thrashingDetection.addLast(t);
                }
            }

            removeOldFaultTimes();
            if (thrashingDetection.size() > (thrashingDetectionTime / 2)) thrashingSum++;


            //System.out.println(envs);
            //System.out.println("Wolne ramki: " + freeFrames);
            if (t == dt || ((t % (dt / 6)) == 0 && t > dt)) {
                //System.out.println("Doing some magic");
                magic();
            }

            t++;
            //System.out.println();
            //System.out.println();
        }

        System.out.println("Thrashing sum: " + thrashingSum);
        System.out.println("Number of faults: " + numberOfFaults);
        return numberOfFaults;
    }

    private void magic() {

        //System.out.println("Wolne ramki przed zwolnieniem: " + freeFrames);
        giveFramesUnderL();
        //System.out.println("Wolne ramki po zwolnieniu: " + freeFrames);

        ArrayList<Env> addTo = envsToAddFrame();
        Collections.sort(addTo);
        //System.out.println("Deficyt ramek: " + addTo);

        if (addTo.size() > freeFrames) {
            deactivateHighest(addTo.size() - freeFrames, addTo);
            //System.out.println("Po zwolnieniu ramek: " + freeFrames);
        }
        giveFreeFramesForActive(addTo);
        giveFreeFramesForNotActive(addTo);
    }

    private void deactivateHighest(int num, ArrayList<Env> addTo) {
        for (int i = 0; i < num; i++) {
            if (addTo.get(i).PFF() > u + uPlus) {
                addTo.get(i).deactivate();
                addTo.remove(i);
                i--;
                num--;
            } else {
                return;
            }
        }
    }

    private void giveFreeFramesForActive(ArrayList<Env> addTo) {
        for (int i = 0; i < addTo.size(); i++) {
            if (addTo.get(i).PFF() > u && addTo.get(i).isActive) {
                if (freeFrames <= 0) return;
                addTo.get(i).giveOneFrame();
                addTo.remove(i);
                i--;
            }
        }
    }

    private void giveFreeFramesForNotActive(ArrayList<Env> addTo) {
        int i = 0;
        while (freeFrames > 0 && !addTo.isEmpty()) {
            addTo.get(i).giveOneFrame();
            i++;
            i %= addTo.size();
        }
    }

    private ArrayList<Env> envsToAddFrame() {
        ArrayList<Env> ret = new ArrayList<>();
        for (int i = 0; i < envs.size(); i++) {
            if (envs.get(i).PFF() > u || !envs.get(i).isActive) {
                ret.add(envs.get(i));
            }
        }

        return ret;
    }


    private void giveFramesUnderL() {
        for (int i = 0; i < envs.size(); i++) {
            Env curEnv = envs.get(i);

            if (curEnv.isActive && curEnv.PFF() < l) {
                //System.out.println("giving frame " + i);
                curEnv.freeOneFrame();
            }
        }
    }

    private void removeOldFaultTimes() {
        for (int i = 0; i < envs.size(); i++) {
            envs.get(i).removeOldFaultTime();
        }


        if (thrashingDetection.size() != 0 && t == thrashingDetection.getFirst() + thrashingDetectionTime) {
            thrashingDetection.removeFirst();
        }
    }

    private int getFirstActive() {
        if (numOfDeactivated == processes.size()) return -1;

        Page p;
        for (int i = 0; i < unitedReferences.size(); i++) {
            p = unitedReferences.get(i);

            if (envs.get(p.getProcessId()).isActive) {
                return i;
            }
        }
        return -1;
    }

    private void initializeMemories() {
        ProportionalAlgorithm algorithm = new ProportionalAlgorithm();
        int sumOfPages = 0;

        for (int i = 0; i < processes.size(); i++) {
            sumOfPages += processes.get(i).getNumberOfPages();
        }

        envs = new ArrayList<>(processes.size());
        int curProcessFramesAlloc;

        for (int i = 0; i < processes.size(); i++) {
            curProcessFramesAlloc = algorithm.giveFrames(processes.get(i).getNumberOfPages(), sumOfPages, numberOfFrames);

            freeFrames -= curProcessFramesAlloc;
            envs.add(new Env(processes.get(i), curProcessFramesAlloc));
        }
    }
}
