package Zadanie3ZastepowanieStron.Algorithm;

import Zadanie3ZastepowanieStron.Page;

import java.util.List;

public class LRU extends PageReplacementAlgorithm {
    public LRU() {
    }

    @Override
    protected int chooseDeletePage(List<Page> frames, List<Page> references, int beforeIndex) {
        int maxDistance = -1;
        int maxDistanceIndex = 0;

        int distance;
        for (int i = 0; i < frames.size(); i++) {
            distance = distance(frames.get(i), references, beforeIndex);

            if (distance > references.size()) {
                return i;
            }

            if (distance > maxDistance) {
                maxDistance = distance;
                maxDistanceIndex = i;
            }
        }
        return maxDistanceIndex;
    }

    private int distance(Page p, List<Page> references, int beforeIndex) {
        int distance = 0;

        for (int i = beforeIndex - 1; i >= 0; i--) {
            distance++;

            if (p.equals(references.get(i))) {
                return distance;
            }
        }

        return references.size() + 10;
    }
}
