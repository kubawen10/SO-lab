package Zadanie1AlgorytmyPlanowania;

import java.util.List;

public class SJF extends Simulation {
    public SJF(List<Process> processes) {
        super(processes, new AddSortedByLength());

    }

    @Override
    public void run() {
        //1 time unit
        while (currentProcessToAddIndex < processes.size() || processor.getQueueSize() > 0) {

            if (!currentServedProcess.isDone()) {
                processor.serve(time, currentServedProcess);
            } else {
                if (currentServedProcess.getLength() == 0) {
                    saveValues();

                    processor.removeProcess(0);

                    addNewProcesses();

                    if (processor.getQueueSize() >= 1) {
                        switchProcess(0);
                        saveSwitch();
                    } else {
                        currentServedProcess.serveProcess(time);
                    }
                } else { //length -1
                    addNewProcesses();
                    if (processor.getQueueSize() >= 1) {
                        switchProcess(0);
                        saveSwitch();
                    }
                }
            }

            time++; //add time when serving and when changing
        }
    }
}
