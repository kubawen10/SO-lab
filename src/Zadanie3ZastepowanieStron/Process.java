package Zadanie3ZastepowanieStron;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Process {
    private final Random random = new Random();
    private final int processId;
    private final int numberOfPages;

    private final List<Page> pages;

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

    public int getNumberOfPages() {
        return numberOfPages;
    }

    public List<Page> getReferences() {
        return references;
    }

    public void setReferences(List<Page> references) {
        this.references = references;
    }

    public List<Page> generateReferences(int numberOfReferences, int localReferencesChance) {
        List<Page> returnList = new ArrayList<>();
        int sum=0;
        int summ=0;

        boolean startLocal = false;
        List<Page> localPages = new ArrayList<>();
        int numberOfLocalReferences = 0;

        for (int i = 0; i < numberOfReferences; i++) {

            //wasnt local so maybe init(p/1000 chance of starting)
            if (!startLocal && random.nextInt(1000) < localReferencesChance) {
                startLocal = true;

                //number of local pages is in range of [numOfPagesInMem; numOfPages + random(0; numOfPages*2))
                numberOfLocalReferences = numberOfPages + random.nextInt(numberOfPages * 2);
                localPages = chooseLocalPages();

                //System.out.println("started local num: " + numberOfLocalReferences + " pages: " + localPages);
            }

            //if local and not finished add from local range
            if (startLocal && numberOfLocalReferences > 0) {
                returnList.add(chooseRandomPage(localPages));

                //end of local references
                if (--numberOfLocalReferences == 0) {
                    startLocal = false;
                }
            } else { //else choose from all pages
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

        int to=Math.max(3, localPages.size()/(random.nextInt(10)+2));
        //System.out.println(to);
        //sublist of 2 to pagesSize * 3/4 local pages
        //return localPages.subList(0, Math.min(localPages.size() / 2, random.nextInt(localPages.size()) + 2));
        return localPages.subList(0, to);
    }

    @Override
    public String toString() {
        return "processId: " + processId + ", numberOfPages: " + numberOfPages + ", references: " + references;
    }
}
