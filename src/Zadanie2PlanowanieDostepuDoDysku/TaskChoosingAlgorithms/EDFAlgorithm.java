package Zadanie2PlanowanieDostepuDoDysku.TaskChoosingAlgorithms;

import Zadanie2PlanowanieDostepuDoDysku.Disc;
import Zadanie2PlanowanieDostepuDoDysku.RealTimeTask;
import Zadanie2PlanowanieDostepuDoDysku.Task;

public class EDFAlgorithm extends Algorithm {
    @Override
    public int chooseTask(int currentHeadIndex, Disc disc) {
        int returnIndex = -1;
        int min = 100000;

        for (RealTimeTask t : disc.getRealTimeTasks()) {
            if (t.getDeadline() < min) {
                min = t.getDeadline();
                returnIndex = t.getIndex();
            }
        }

        return returnIndex;
    }
}
