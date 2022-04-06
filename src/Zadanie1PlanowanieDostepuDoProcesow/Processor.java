package Zadanie1PlanowanieDostepuDoProcesow;

import java.util.ArrayList;
import java.util.List;

public class Processor {
    private List<Process> queue;
    private AddBehavior ab;

    public Processor(AddBehavior ab) {
        queue = new ArrayList<>();
        this.ab = ab;
    }

    public void serve(long t, Process p){
        p.serveProcess(t);
    }

    public Process getProcess(int index){
        return queue.get(index);
    }

    public void addNewProcess(Process p){
        ab.addProcess(queue, p);
    }

    public int getQueueSize() {
        return queue.size();
    }

    public void showQueue(){
        System.out.println(queue);
    }

    public void removeProcess(int index){
        queue.remove(index);
    }
}
