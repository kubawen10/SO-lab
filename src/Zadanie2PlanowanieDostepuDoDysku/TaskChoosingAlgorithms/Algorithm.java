package Zadanie2PlanowanieDostepuDoDysku.TaskChoosingAlgorithms;

import Zadanie2PlanowanieDostepuDoDysku.Disc;
import Zadanie2PlanowanieDostepuDoDysku.Task;

public abstract class Algorithm {
    protected Algorithm realTimeAlgorithm = null;

    protected int lastTaskIndex = -1;

    public abstract int chooseTask(int currentHeadIndex, Disc disc);

    public void setLastTaskIndex(int lastTaskIndex){
        this.lastTaskIndex = lastTaskIndex;
    }
}
