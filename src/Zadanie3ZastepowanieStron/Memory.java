package Zadanie3ZastepowanieStron;

import java.util.ArrayList;
import java.util.List;

public class Memory {
    private int numberOfFrames;
    private List<Page> frames;

    public Memory(int numberOfFrames) {
        this.numberOfFrames = numberOfFrames;
        resetMemory();
    }

    public boolean pageInMemory(Page p) {
        return frames.contains(p);
    }

    public void setFrame(int index, Page p) {
        frames.set(index, p);
    }

    public int getNumberOfFrames() {
        return numberOfFrames;
    }

    public void setNumberOfFrames(int numberOfFrames) {
        this.numberOfFrames = numberOfFrames;
    }

    public List<Page> getFrames() {
        return frames;
    }

    public void setFrames(List<Page> frames) {
        this.frames = frames;
    }

    public void resetMemory() {
        frames = new ArrayList<>(numberOfFrames);
        for (int i = 0; i < numberOfFrames; i++) {
            frames.add(null);
        }
    }

    public void addFrames(int n) {
        for (int i = 0; i < n; i++) {
            frames.add(null);
        }

        numberOfFrames += n;
    }

    @Override
    public String toString() {
        return "Frames(" + numberOfFrames + "): " + frames;
    }
}
