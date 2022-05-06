package Zadanie3ZastepowanieStron.Algorithm;

import Zadanie3ZastepowanieStron.Memory;
import Zadanie3ZastepowanieStron.Page;

import java.util.List;

public abstract class PageReplacementAlgorithm {
    private int index = -1;

    public int insertPageIndex(Memory memory, List<Page> references, int curPage){
        List<Page> frames = memory.getFrames();

        index = hasFreeSpace(frames);

        if (index == -1) {
            index = chooseDeletePage(frames, references, curPage);
        }

        return index;
    }

    protected int hasFreeSpace(List<Page> frames) {
        for (int i = 0; i < frames.size(); i++) {
            if (frames.get(i) == null) {
                return i;
            }
        }
        return -1;
    }

    protected abstract int chooseDeletePage(List<Page> frames, List<Page> references, int curIndex);
}
