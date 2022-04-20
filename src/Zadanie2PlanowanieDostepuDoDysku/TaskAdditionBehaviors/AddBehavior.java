package Zadanie2PlanowanieDostepuDoDysku.TaskAdditionBehaviors;

import Zadanie2PlanowanieDostepuDoDysku.Disc;
import Zadanie2PlanowanieDostepuDoDysku.Task;

import java.util.LinkedList;

public interface AddBehavior {
    public int add(LinkedList<Task> tasksToAdd, Disc disc);
}
