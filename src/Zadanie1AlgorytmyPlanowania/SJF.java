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
                serveTime += 1;
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
                        emptyQueueTime += 1;
                    }
                } else { //length -1
                    addNewProcesses();
                    if (processor.getQueueSize() >= 1) {
                        switchProcess(0);
                        saveSwitch();
                    } else {
                        emptyQueueTime += 1;
                    }
                }
            }

            time++; //add time when serving and when changing
        }
    }
}
