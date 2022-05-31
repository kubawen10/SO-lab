package Zadanie4PrzydzialRamek.ProportionalAlloc;

public class ProportionalAlgorithm {
    public ProportionalAlgorithm() {
    }

    public int giveFrames(int curNum, int sumOfPages, int numberOfFrames) {
        return (int)(((double)curNum / sumOfPages) * numberOfFrames);
    }
}
