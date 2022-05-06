package Zadanie3ZastepowanieStron.Algorithm;

import Zadanie3ZastepowanieStron.Page;
import java.util.List;

public class FIFO extends PageReplacementAlgorithm {
    private int firstAdded = 0;

    public FIFO() {
    }

    @Override
    protected int chooseDeletePage(List<Page> frames, List<Page> references, int afterIndex) {
        int index = firstAdded;
        firstAdded = (firstAdded + 1) % frames.size();
        return index;
    }
}
