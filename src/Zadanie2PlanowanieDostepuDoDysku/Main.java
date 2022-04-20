package Zadanie2PlanowanieDostepuDoDysku;

import Zadanie2PlanowanieDostepuDoDysku.Simulations.FCFS;
import Zadanie2PlanowanieDostepuDoDysku.Simulations.Simulation;
import Zadanie2PlanowanieDostepuDoDysku.TaskChoosingAlgorithms.EDFAlgorithm;

import java.util.LinkedList;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        LinkedList<Task> tasks = generateTasks(15, 10, true);


        Simulation s = new FCFS(10, tasks, new EDFAlgorithm());

        s.run();
    }

    public static LinkedList<Task> generateTasks(int ammount, int discSize, boolean withRealTime){
        LinkedList<Task> returnList = new LinkedList<>();
        Random r = new Random();
        Task t;

        int realTimeChance;

        for (int i = 1; i <= ammount; i++) {
            if (withRealTime){
                realTimeChance = r.nextInt(100);
                if (realTimeChance>50){
                    t = new RealTimeTask(i, i-1, r.nextInt(discSize), r.nextInt(5)+3);
                }else{
                    t = new NormalTask(i, i-1, r.nextInt(discSize));
                }
            }else{
                t = new NormalTask(i, i-1, r.nextInt(discSize));
            }

            returnList.addLast(t);
        }

        return returnList;
    }

}
