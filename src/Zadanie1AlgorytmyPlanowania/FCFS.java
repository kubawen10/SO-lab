package Zadanie1AlgorytmyPlanowania;

import java.util.List;

public class FCFS extends Simulation {
    public FCFS(List<Process> processes) {
        super(processes, new AddAtTheEnd());

    }

    @Override
    public void run() {
        //1 time unit
        while (currentProcessToAddIndex < processes.size() || processor.getQueueSize() > 0) {
            addNewProcesses();

            if (!currentServedProcess.isDone()) {
                processor.serve(time, currentServedProcess);
            } else {
                if (currentServedProcess.getLength() == 0) {
                    saveValues();

                    processor.removeProcess(0);

                    if(processor.getQueueSize()>=1){
                        switchProcess(0);

                        saveSwitch();
                    }else{
                        currentServedProcess.serveProcess(time); //set length to -1
                    }

                } else { //length -1
                    if(processor.getQueueSize()>=1){
                        switchProcess(0);

                        saveSwitch();
                    }
                }
            }

            time++; //add time when serving and when changing
        }
    }
}
