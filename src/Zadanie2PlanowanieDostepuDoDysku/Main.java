package Zadanie2PlanowanieDostepuDoDysku;

import Zadanie2PlanowanieDostepuDoDysku.Simulations.*;
import Zadanie2PlanowanieDostepuDoDysku.Task.Task;
import Zadanie2PlanowanieDostepuDoDysku.TaskChoosingAlgorithms.Algorithm;
import Zadanie2PlanowanieDostepuDoDysku.TaskChoosingAlgorithms.RealTimeAlgorithms.EDFAlgorithm;
import Zadanie2PlanowanieDostepuDoDysku.TaskChoosingAlgorithms.RealTimeAlgorithms.FDScanAlgorithm;

import java.util.LinkedList;

public class Main {
    public static void main(String[] args) {
        int numberOfTasks;
        int discSize;
        int density;
        int realTimeChance;
        Algorithm realTimeAlg;

        //NO REAL TIME
        //low numberOfTasks
        numberOfTasks = 1000;
        discSize = 200;
        density = 1;
        realTimeChance = 0;
        realTimeAlg = new FDScanAlgorithm();
        runSim(numberOfTasks, discSize, density, realTimeChance, realTimeAlg);

        numberOfTasks = 1000;
        discSize = 200;
        density = 5;
        realTimeChance = 0;
        realTimeAlg = new FDScanAlgorithm();
        runSim(numberOfTasks, discSize, density, realTimeChance, realTimeAlg);

        numberOfTasks = 1000;
        discSize = 200;
        density = 9;
        realTimeChance = 0;
        realTimeAlg = new FDScanAlgorithm();
        runSim(numberOfTasks, discSize, density, realTimeChance, realTimeAlg);

        //high numberOfTasks
        numberOfTasks = 10000;
        discSize = 200;
        density = 1;
        realTimeChance = 0;
        realTimeAlg = new FDScanAlgorithm();
        runSim(numberOfTasks, discSize, density, realTimeChance, realTimeAlg);

        numberOfTasks = 10000;
        discSize = 200;
        density = 5;
        realTimeChance = 0;
        realTimeAlg = new FDScanAlgorithm();
        runSim(numberOfTasks, discSize, density, realTimeChance, realTimeAlg);

        numberOfTasks = 10000;
        discSize = 200;
        density = 9;
        realTimeChance = 0;
        realTimeAlg = new FDScanAlgorithm();
        runSim(numberOfTasks, discSize, density, realTimeChance, realTimeAlg);


        //WITH REAL TIME
        //low numberOfTasks
        numberOfTasks = 1000;
        discSize = 200;
        density = 1;
        realTimeChance = 30;
        realTimeAlg = new EDFAlgorithm();
        runSim(numberOfTasks, discSize, density, realTimeChance, realTimeAlg);
        numberOfTasks = 1000;
        discSize = 200;
        density = 1;
        realTimeChance = 30;
        realTimeAlg = new FDScanAlgorithm();
        runSim(numberOfTasks, discSize, density, realTimeChance, realTimeAlg);

        numberOfTasks = 1000;
        discSize = 200;
        density = 5;
        realTimeChance = 30;
        realTimeAlg = new EDFAlgorithm();
        runSim(numberOfTasks, discSize, density, realTimeChance, realTimeAlg);
        numberOfTasks = 1000;
        discSize = 200;
        density = 5;
        realTimeChance = 30;
        realTimeAlg = new FDScanAlgorithm();
        runSim(numberOfTasks, discSize, density, realTimeChance, realTimeAlg);

        numberOfTasks = 1000;
        discSize = 200;
        density = 9;
        realTimeChance = 30;
        realTimeAlg = new EDFAlgorithm();
        runSim(numberOfTasks, discSize, density, realTimeChance, realTimeAlg);
        numberOfTasks = 1000;
        discSize = 200;
        density = 9;
        realTimeChance = 30;
        realTimeAlg = new FDScanAlgorithm();
        runSim(numberOfTasks, discSize, density, realTimeChance, realTimeAlg);

        //highnumberOfTasks
        numberOfTasks = 10000;
        discSize = 200;
        density = 1;
        realTimeChance = 30;
        realTimeAlg = new EDFAlgorithm();
        runSim(numberOfTasks, discSize, density, realTimeChance, realTimeAlg);
        numberOfTasks = 10000;
        discSize = 200;
        density = 1;
        realTimeChance = 30;
        realTimeAlg = new FDScanAlgorithm();
        runSim(numberOfTasks, discSize, density, realTimeChance, realTimeAlg);

        numberOfTasks = 10000;
        discSize = 200;
        density = 5;
        realTimeChance = 30;
        realTimeAlg = new EDFAlgorithm();
        runSim(numberOfTasks, discSize, density, realTimeChance, realTimeAlg);
        numberOfTasks = 10000;
        discSize = 200;
        density = 5;
        realTimeChance = 30;
        realTimeAlg = new FDScanAlgorithm();
        runSim(numberOfTasks, discSize, density, realTimeChance, realTimeAlg);

        numberOfTasks = 10000;
        discSize = 200;
        density = 9;
        realTimeChance = 30;
        realTimeAlg = new EDFAlgorithm();
        runSim(numberOfTasks, discSize, density, realTimeChance, realTimeAlg);
        numberOfTasks = 10000;
        discSize = 200;
        density = 9;
        realTimeChance = 30;
        realTimeAlg = new FDScanAlgorithm();
        runSim(numberOfTasks, discSize, density, realTimeChance, realTimeAlg);
    }

    public static void runSim(int numberOfTasks, int discSize, int density, int realTimeChance, Algorithm realTimeAlg) {
        System.out.println("RUNNING SIMULATIONS FOR:\n" +
                "number of tasks: " + numberOfTasks + "\n" +
                "discSize: " + discSize + "\n" +
                "density: " + density + "\n" +
                "realTimeChance: " + realTimeChance + "\n");
        TaskGenerator taskGenerator = new TaskGenerator(numberOfTasks, discSize, density, realTimeChance);

        LinkedList<Task> tasks = taskGenerator.generate();

        Simulation FCFS = new FCFS(discSize, taskGenerator.copyList(tasks), realTimeAlg);
        Simulation SSTF = new SSTF(discSize, taskGenerator.copyList(tasks), realTimeAlg);
        Simulation SCAN = new SCAN(discSize, taskGenerator.copyList(tasks), realTimeAlg);
        Simulation CSCAN = new CSCAN(discSize, taskGenerator.copyList(tasks), realTimeAlg);

        FCFS.run();
        System.out.println();
        SSTF.run();
        System.out.println();
        SCAN.run();
        System.out.println();
        CSCAN.run();
        System.out.println("\n");
    }
}
