package Zadanie3ZastepowanieStron;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Process {
    private final Random random = new Random();
    private int processId;
    private int numberOfPages;

    private List<Page> pages;

    private List<Page> references;

    public Process(int processId, int numberOfPages) {
        this.processId = processId;
        this.numberOfPages = numberOfPages;

        pages = new ArrayList<>(numberOfPages);
        for (int i = 0; i < numberOfPages; i++) {
            pages.add(new Page(processId, i));
        }
    }

    public int getProcessId() {
        return processId;
    }

    public void setProcessId(int processId) {
        this.processId = processId;
    }

    public int getNumberOfPages() {
        return numberOfPages;
    }

    public void setNumberOfPages(int numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

    public List<Page> getReferences() {
        return references;
    }

    public void setReferences(List<Page> references) {
        this.references = references;
    }

    public List<Page> generateReferences(int numberOfReferences, int localReferencesChance) {
        List<Page> returnList = new ArrayList<>();

        boolean startLocal = false;
        List<Page> localPages = new ArrayList<>();
        int numberOfLocalReferences = 0;

        for (int i = 0; i < numberOfReferences; i++) {

            if (!startLocal && random.nextInt(100) < localReferencesChance) {
                startLocal = true;

                numberOfLocalReferences = random.nextInt(numberOfPages * 2) + numberOfPages;
                localPages = chooseLocalPages();
            }

            if (startLocal && numberOfLocalReferences > 0) {
                returnList.add(chooseRandomPage(localPages));
                if (--numberOfLocalReferences == 0) {
                    startLocal = false;
                }
            } else {
                returnList.add(chooseRandomPage(pages));
            }
        }

        references = returnList;

        return returnList;
    }

    private Page chooseRandomPage(List<Page> pages) {
        return pages.get(random.nextInt(pages.size()));
    }

    private List<Page> chooseLocalPages() {
        List<Page> localPages = new ArrayList<>(pages);

        Collections.shuffle(localPages);
        return localPages.subList(0, random.nextInt(numberOfPages - 3) + 2);
    }

    @Override
    public String toString() {
        return "processId: " + processId + ", numberOfPages: " + numberOfPages + ", references: " + references;
    }
}
