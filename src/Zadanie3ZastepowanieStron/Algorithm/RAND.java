package Zadanie3ZastepowanieStron.Algorithm;

import Zadanie3ZastepowanieStron.Page;

import java.util.List;
import java.util.Random;

public class RAND extends PageReplacementAlgorithm {
    private final Random random;

    public RAND() {
        random = new Random();
    }

    @Override
    protected int chooseDeletePage(List<Page> frames, List<Page> references, int curIndex) {
        return random.nextInt(frames.size());
    }
}
