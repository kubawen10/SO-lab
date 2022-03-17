package Zadanie1;

import java.util.List;

public class SJFSimulation extends Simulation{
    public SJFSimulation(List<Process> processes) {
        super(processes, new AddSortedByLength());
    }

    @Override
    public void run() {
        //1 time unit
        while (currentProcessToAddIndex < processes.size() || processor.getQueueSize() > 0) {

            //System.out.print("Time: " + time + " ");

            //current process isnt yet done so serve it
            if (currentServedProcess.getLength() >= 1) {
                //System.out.println("Serving process: " + currentServedProcess);
                processor.serve(time, currentServedProcess);
            }
            //current process is done
            else if (currentServedProcess.getLength() == 0) {

                //System.out.println("Saving process data: " + currentServedProcess);

                //saving data
                saveValues();

                //decreasing length to -1 for another else
                currentServedProcess.serveProcess(time);

                //there is another process in the queue
                if (processor.getQueueSize() > 1) {
                    processor.removeProcess(0);
                    //adding is there not at the beginning of the loop
                    addNewProcesses();
                    currentServedProcess = processor.getProcess(0);
                    numberOfProcessSwitches++;
                    //System.out.println("Changing process for " + currentServedProcess);
                }
                //there isnt another process in the queue, so make the queue empty to allow while loop exit
                else {
                    processor.removeProcess(0);
                    addNewProcesses();
                    //System.out.println("Removing process from queue");
                    if(processor.getQueueSize()>=1){
                        currentServedProcess = processor.getProcess(0);
                        numberOfProcessSwitches++;
                    }
                }

            }
            //currentProcess length is -1, so nothing is happening, check if there is new process in the queue
            else {
                addNewProcesses();
                if (processor.getQueueSize() >= 1) {
                    currentServedProcess = processor.getProcess(0);
                    numberOfProcessSwitches++;
                    //System.out.println("Changing with queue >=1");
                }
            }

            //increase time
            time++;
        }

        //there is one second after each process as swap time so after last one there is one additional second
        //and last iteration is for saving data
        time-=2;

        displayData("SJF");
    }

    @Override
    public void addNewProcesses(){
        while ((currentProcessToAddIndex < processes.size()) && (time >= processes.get(currentProcessToAddIndex).getCreationTime())) {
            processor.addNewProcess(processes.get(currentProcessToAddIndex));
            currentProcessToAddIndex += 1;

            //binary search, dont know what values can be correct
            int timeComplexity;
            timeComplexity=(int)(Math.log(processor.getQueueSize()) / Math.log(2));
            if (timeComplexity > 0) {
                time += (timeComplexity / 2);
            }
        }
    }

}
