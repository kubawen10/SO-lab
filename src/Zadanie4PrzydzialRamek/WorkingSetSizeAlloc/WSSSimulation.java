package Zadanie4PrzydzialRamek.WorkingSetSizeAlloc;

import Zadanie3ZastepowanieStron.Memory;
import Zadanie3ZastepowanieStron.Page;
import Zadanie3ZastepowanieStron.Process;
import Zadanie4PrzydzialRamek.ProportionalAlloc.ProportionalAlgorithm;
import Zadanie4PrzydzialRamek.Simulation;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class WSSSimulation extends Simulation {
    class Env {
        Process p;
        Memory memory;
        boolean isActive;
        int curReference;

        ArrayList<Integer> pageLastlyUsed;

        public Env(Process p, int numOfFrames) {
            this.p = p;
            memory = new Memory(numOfFrames);
            isActive = true;
            curReference = 0;

            initLastlyUsed();
        }

        private void initLastlyUsed() {
            pageLastlyUsed = new ArrayList<>(p.getNumberOfPages());
            for (int i = 0; i < p.getNumberOfPages(); i++) {
                pageLastlyUsed.add(-1);
            }
        }

        public boolean serve(Page page) {
            pageLastlyUsed.set(page.getPageId(), t);

            if (!memory.pageInMemory(page)) {
                int indexForPage = pageReplacementAlgorithm.insertPageIndex(memory, p.getReferences(), curReference);
                memory.setFrame(indexForPage, page);

                curReference++;
                return false;
            }
            curReference++;
            return true;
        }

        public int WSS() {
            int sum = 0;

            for (int i = 0; i < pageLastlyUsed.size(); i++) {
                if (pageLastlyUsed.get(i) > t - dt && pageLastlyUsed.get(i) != -1) {
                    sum++;
                }
            }
            return sum;
        }

        public boolean castToProperSize() {
            if (!isActive) return false;

            int wss = WSS();

            if (wss == 0) {
                deactivate();
                return true;
            }

            freeFrames += memory.getNumberOfFrames() - wss;

            Memory newM = new Memory(wss);
            List<Page> frames = memory.getFrames();

            for (int i = 0; i < wss && i < frames.size(); i++) {
                newM.setFrame(i, frames.get(i));
            }

            memory = newM;
            return false;
        }

        public void giveFrames(int numOfFrames) {
            memory.addFrames(numOfFrames);
            freeFrames -= numOfFrames;
        }

        public void deactivate() {
            isActive = false;
            numOfDeactivated++;

            freeAllFrames();
        }

        private void freeAllFrames() {
            freeFrames += memory.getNumberOfFrames();
        }

        public void activate(int numOfFrames) {
            isActive = true;
            initLastlyUsed();
            freeFrames -= numOfFrames;
            numOfDeactivated--;

            memory = new Memory(numOfFrames);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Env env = (Env) o;
            return p.getProcessId() == env.p.getProcessId();
        }

        @Override
        public String toString() {
            return "Id: " + p.getProcessId() + " IsActive: " + isActive + " WSS: " + WSS() + " Memory: " + memory + "\n";
        }
    }

    private int dt;

    private ArrayList<Env> envs;
    private int freeFrames;
    private int numOfDeactivated;
    private int t;

    private LinkedList<Integer> thrashingDetection;
    private int thrashingDetectionTime = 50;
    private int thrashingSum = 0;

    public WSSSimulation(ArrayList<Process> processes, int numberOfFrames, ArrayList<Page> unitedReferences, int dt) {
        super(processes, numberOfFrames, unitedReferences);
        this.dt = dt;
        freeFrames = numberOfFrames;
        numOfDeactivated = 0;
        thrashingDetection = new LinkedList<>();
    }

    @Override
    public int run() {
        numberOfFaults = 0;
        t = 0;
        int curPageIndex;

        initializeMemories();
        copyUnitedReferences();

        while (!unitedReferences.isEmpty()) {
            curPageIndex = getFirstActive();
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

            if (thrashingDetection.size() > (thrashingDetectionTime / 2)) thrashingSum++;
            removeOldFaultTimes();

            //System.out.println(envs);
            //System.out.println("Wolne ramki: " + freeFrames);
            if (t == dt || ((t % (dt / 2)) == 0 && t > dt)) {
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
        int D = sumWSS();
        //System.out.println("Calculated D: " + D);
        //System.out.println(numberOfFrames);

        if (D > numberOfFrames) {
            //System.out.println("Need to remove");
            //System.out.println("Before deactivation:\n " + envs);
            while (D > numberOfFrames) {
                int dec = findAndDeactivateMaxWSS();
                if (dec <= 0) break;
                D -= dec;
            }
            //System.out.println("After deactivation:\n " + envs);

            giveProportional();
        } else {
            //System.out.println("Casting");
            ArrayList<Env> deactivated = castEnvsToProperNumOfFrames();
            activateEnvs(deactivated);
        }
    }

    private void giveProportional() {
        ArrayList<Env> envsToGive = new ArrayList<>();
        Env curEnv;
        int sumOfWSS = 0;

        for (int i = 0; i < envs.size(); i++) {
            curEnv = envs.get(i);
            if (!curEnv.isActive) continue;

            int curWss = curEnv.WSS();

            sumOfWSS += curWss;
            envsToGive.add(curEnv);
        }

        int tempFree = freeFrames;
        for (int i = 0; i < envsToGive.size(); i++) {
            curEnv = envsToGive.get(i);

            int add = (int) ((((double) curEnv.WSS()) / sumOfWSS) * tempFree);
            curEnv.giveFrames(add);
        }
    }

    private void activateEnvs(ArrayList<Env> deactivated) {
        ArrayList<Env> activate = new ArrayList<>();
        for (int i = 0; i < envs.size(); i++) {
            if (!envs.get(i).isActive) {
                if (!deactivated.contains(envs.get(i))) {
                    activate.add(envs.get(i));
                }
            }
        }

        if (activate.isEmpty()) return;

        int numForEach = freeFrames / activate.size();
        if (numForEach > 0) {
            Env curEnv;
            for (int i = 0; i < activate.size(); i++) {
                curEnv = activate.get(i);
                curEnv.activate(numForEach);
            }
        }
    }

    private int findAndDeactivateMaxWSS() {
        int max = -1;
        Env maxEnv = null;

        for (int i = 0; i < envs.size(); i++) {
            Env curEnv = envs.get(i);
            int curWSS;
            if (curEnv.isActive && (curWSS = curEnv.WSS()) > max) {
                maxEnv = curEnv;
                max = curWSS;
            }
        }

        if (maxEnv != null) {
            maxEnv.deactivate();
        }

        return max;
    }

    private ArrayList<Env> castEnvsToProperNumOfFrames() {
        ArrayList<Env> deactivated = new ArrayList<>();

        Env curEnv;
        for (int i = 0; i < envs.size(); i++) {
            curEnv = envs.get(i);
            if (curEnv.castToProperSize()) {
                deactivated.add(curEnv);
            }
        }

        return deactivated;
    }

    private int sumWSS() {
        int sum = 0;
        Env curEnv;

        for (int i = 0; i < envs.size(); i++) {
            curEnv = envs.get(i);

            if (curEnv.isActive) {
                sum += curEnv.WSS();
            }
        }

        return sum;
    }

    private void removeOldFaultTimes() {
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
