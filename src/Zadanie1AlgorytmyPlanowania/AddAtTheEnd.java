package Zadanie1AlgorytmyPlanowania;

import java.util.List;

public class AddAtTheEnd implements AddBehavior {
    @Override
    public void addProcess(List<Process> list, Process p) {
        list.add(p);
    }
}
