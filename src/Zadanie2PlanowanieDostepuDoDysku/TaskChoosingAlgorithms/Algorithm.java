package Zadanie2PlanowanieDostepuDoDysku.TaskChoosingAlgorithms;

import Zadanie2PlanowanieDostepuDoDysku.Disc;
import Zadanie2PlanowanieDostepuDoDysku.Task.Task;

public abstract class Algorithm {
    protected Algorithm realTimeAlgorithm = null;

    protected int lastTaskIndex = -1;

    public abstract Task chooseTask(int currentHeadIndex, Disc disc);

    public abstract String toString();
}
