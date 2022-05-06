package Zadanie3ZastepowanieStron.Algorithm;

import Zadanie3ZastepowanieStron.Page;

import java.util.LinkedList;
import java.util.List;

public class ALRU extends PageReplacementAlgorithm {
    class MarkedPage {
        Page p;
        int marker;

        public MarkedPage(Page page) {
            p = page;
            marker = 1;
        }

        public String toString() {
            return "Page: " + p + " marker: " + marker + "   ";
        }
    }

    private LinkedList<MarkedPage> queue;
    private int lastSeen = 0;

    public ALRU() {
        queue = new LinkedList<>();
    }

    @Override
    protected int chooseDeletePage(List<Page> frames, List<Page> references, int curIndex) {
        //i dont think it works :(

        //init queue after memory is filled
        initQueue(frames);

        //when pageFault didnt happen
        updateQueue(references, curIndex);
        lastSeen = curIndex;

        MarkedPage markedPage;

        //finding marked page without second chance, and update pages with second page
        while ((markedPage = queue.removeFirst()).marker == 1) {
            markedPage.marker = 0;
            queue.addLast(markedPage);
        }

        //add current
        queue.addLast(new MarkedPage(references.get(curIndex)));

        System.out.println(queue);
        //find page in memory and return its index
        for (int i = 0; i < frames.size(); i++) {
            if (frames.get(i).equals(markedPage.p)) {
                return i;
            }
        }

        System.out.println("cos nie tak");
        return 0;
    }

    private void initQueue(List<Page> frames) {
        if (queue.isEmpty()) {
            for (int i = 0; i < frames.size(); i++) {
                queue.addLast(new MarkedPage(frames.get(i)));
            }
        }
    }

    private void updateQueue(List<Page> references, int curIndex) {
        for (int i = lastSeen + 1; i < curIndex; i++) {
            pageUsedAgain(references.get(i));
        }
    }

    private void pageUsedAgain(Page page) {
        for (int i = 0; i < queue.size(); i++) {
            MarkedPage markedPage = queue.get(i);
            if (markedPage.p.equals(page)) {
                markedPage.marker = 1;
                System.out.println("updating " + page);
                return;
            }
        }
    }
}
