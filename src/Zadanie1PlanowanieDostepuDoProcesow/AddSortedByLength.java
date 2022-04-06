package Zadanie1PlanowanieDostepuDoProcesow;

import java.util.List;

//used in SJF
public class AddSortedByLength implements AddBehavior {
    //adding new process with same length as last compared to older processes with same length
    private int findLargestIndexOfSameValue(int start, List<Process> list){
        int value  = list.get(start).getLength();
        for (int i = start+1; i < list.size(); i++) {
            if(list.get(i).getLength()!=value) return i;
        }
        return list.size();
    }

    //binary search to add new process in SJF algorithm
    private int findIndex(List<Process> list,Process p) {
        int left = 0;
        int right = list.size() - 1;
        int middle;

        while (left <= right) {
            middle = (left + right) / 2;

            if (list.get(middle).compareTo(p) < 0) {
                left = middle + 1;
            } else if (list.get(middle).compareTo(p) > 0) {
                right = middle - 1;
            } else return findLargestIndexOfSameValue(middle, list);
        }
        return left;
    }

    @Override
    public void addProcess(List<Process> list, Process p) {
        int index = findIndex(list,p);
        list.add(index, p);
    }
}
