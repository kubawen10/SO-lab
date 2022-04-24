package Zadanie2PlanowanieDostepuDoDysku;

import Zadanie2PlanowanieDostepuDoDysku.Task.NormalTask;
import Zadanie2PlanowanieDostepuDoDysku.Task.RealTimeTask;
import Zadanie2PlanowanieDostepuDoDysku.Task.Task;

import java.util.LinkedList;
import java.util.Random;

public class TaskGenerator {
    private final int numberOfTasks;
    private final int discSize;
    private final int realTimeChance;
    private final int density;
    Random r = new Random();

    public TaskGenerator(int numberOfTasks, int discSize, int density) {
        this.numberOfTasks = numberOfTasks;
        this.discSize = discSize;
        this.realTimeChance = 0;
        this.density = density;
    }

    public TaskGenerator(int numberOfTasks, int discSize, int density, int realTimeChance) {
        this.numberOfTasks = numberOfTasks;
        this.discSize = discSize;
        this.realTimeChance = realTimeChance;
        this.density = density;
    }

    public LinkedList<Task> generate() {
        LinkedList<Task> returnList = new LinkedList<>();
        Task t;

        int curTime = 0;

        for (int i = 1; i <= numberOfTasks; i++) {
            if (r.nextInt(100) < realTimeChance) {
                t = new RealTimeTask(i, curTime, r.nextInt(discSize), r.nextInt(discSize)+1);
            }else{
                t = new NormalTask(i, curTime, r.nextInt(discSize));
            }

            curTime += addTime();
            returnList.addLast(t);
        }

        return returnList;
    }

    public LinkedList<Task> copyList(LinkedList<Task> l){
        LinkedList<Task> returnList = new LinkedList<>();
        int id = 0;

        for (Task t : l) {
            id++;

            if(t.isRealTime()){
                RealTimeTask rt = (RealTimeTask) t;
                returnList.addLast(new RealTimeTask(id, t.getCreationTime(), t.getIndex(), rt.getDeadline()));
            }else{
                returnList.addLast(new NormalTask(id, t.getCreationTime(), t.getIndex()));
            }
        }

        return returnList;
    }

    //density should be 1-9
    private int addTime(){
        return (10-density) * r.nextInt(discSize)/8;
    }
}
