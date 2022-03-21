package Zadanie1AlgorytmyPlanowania;

import java.util.List;

public class RR extends Simulation {
    public int K;
    public int counter = 0;

    public int currentProcessIndex = 0;

    public RR(List<Process> processes, int K) {
        super(processes, new AddAtTheEnd());
        this.K = K;
    }

    @Override
    public void run() {
        //1 time unit
        while (currentProcessToAddIndex < processes.size() || processor.getQueueSize() > 0) {
            addNewProcesses();
            //System.out.println(time);
            //processor.showQueue();
            //System.out.println(currentServedProcess);

            if (!currentServedProcess.isDone()) {
                if (counter < K) {
                    processor.serve(time, currentServedProcess);
                    counter++;
                    serveTime+=1;
                    //System.out.println("Serving Process");
                } else {
                    counter = 0;
                    currentProcessIndex += 1;
                    if(currentProcessIndex >= processor.getQueueSize()){
                        currentProcessIndex = 0;
                    }
                    //System.out.println("Picking process at index: " + currentProcessIndex);
                    switchProcess(currentProcessIndex);
                    if(processor.getQueueSize()==1){
                        processor.serve(time, currentServedProcess);
                        counter++;
                        serveTime+=1;
                    }
                    else{
                        saveSwitch();
                    }
                    //System.out.println("Switching process");
                }
            } else {
                counter = 0;

                if (currentServedProcess.getLength() == 0) {
                    saveValues();
                    //System.out.println("Saving values");

                    processor.removeProcess(currentProcessIndex);

                    if(currentProcessIndex >= processor.getQueueSize()){
                        currentProcessIndex = 0;
                    }

                    if (processor.getQueueSize() >= 1) {
                        switchProcess(currentProcessIndex);
                        //System.out.println("Switching process");
                        saveSwitch();
                        //System.out.println("Switch time: " + switchTime);
                    } else {
                        emptyQueueTime+=1;
                        currentServedProcess.serveProcess(time); //set length to -1
                    }

                } else { //length -1
                    if (processor.getQueueSize() >= 1) {
                        switchProcess(currentProcessIndex);
                        //System.out.println("Switching process");
                        saveSwitch();
                        //System.out.println("Switch time: " + switchTime);
                    }
                    else{
                        emptyQueueTime+=1;
                    }
                }
            }

            time++; //add time when serving and when changing
        }
    }
}
