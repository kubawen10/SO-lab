package Zadanie1;

import java.util.List;

public class RRSimulation extends Simulation {
    public int K;
    public int i = 0;

    public int currentProcessIndex = 0;

    public RRSimulation(List<Process> processes, int K) {
        super(processes, new AddAtTheEnd());
        this.K = K;
    }


    @Override
    public void run() {
        //1 time unit
        while (currentProcessToAddIndex < processes.size() || processor.getQueueSize() > 0) {
            //adding new processes to queue
            //changing K values?
            addNewProcesses();

            //System.out.print("Time: " + time + " ");

            if (currentServedProcess.getLength() >= 1) {
                //served process K times
                if (i == K) {
                    //if there is next process in the queue pick it
                    if (currentProcessIndex + 1 < processor.getQueueSize() ) {
                        currentProcessIndex++;
                    }
                    //if this is the last element pick first element in the queue,
                    //there will always be one because it can still be current process, queue.len=1
                    else {
                        currentProcessIndex = 0;
                    }
                    i = 0;
                    currentServedProcess = processor.getProcess(currentProcessIndex);
                    System.out.println("Changing process to: " + currentServedProcess);
                    numberOfProcessSwitches++;
                } else {
                    System.out.println("Serving process: " + currentServedProcess);
                    processor.serve(time, currentServedProcess);
                    i++;
                }
            } else if (currentServedProcess.getLength() == 0) {
                System.out.println("Saving process data: " + currentServedProcess);
                saveValues();

                currentServedProcess.serveProcess(time);

                if (currentProcessIndex + 1 < processor.getQueueSize()) {
                    processor.removeProcess(currentProcessIndex);
                    currentServedProcess = processor.getProcess(currentProcessIndex);
                    System.out.println("Changing process for " + currentServedProcess);
                    numberOfProcessSwitches++;
                }
                //queue length will be zero after removing process
                else {

                    System.out.println("Removing process from queue");
                    currentProcessIndex=0;
                }
                i=0;
            }else{
                if (processor.getQueueSize() >= 1) {
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    if(processor.getProcess(0).getLength()==-1){
                        processor.removeProcess(0);
                        continue;
                    }

                    currentServedProcess = processor.getProcess(0);
                    numberOfProcessSwitches++;
                    System.out.println("Changing with queue >=1");
                    processor.showQueue();
                }
            }
            time++;
        }
        //there is one second after each process as swap time so after last one there is one additional second
        //and last iteration is for saving data
        time -= 2;

        displayData("RR");
    }
}
