package Zadanie3ZastepowanieStron;

public class Page {
    int processId;
    int pageId;

    public Page(int processId, int pageId) {
        this.processId = processId;
        this.pageId = pageId;
    }

    public int getProcessId() {
        return processId;
    }

    public void setProcessId(int processId) {
        this.processId = processId;
    }

    public int getPageId() {
        return pageId;
    }

    public void setPageId(int pageId) {
        this.pageId = pageId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Page page = (Page) o;
        return processId == page.processId && pageId == page.pageId;
    }

    @Override
    public String toString(){
        return "PageFromProcess: " + processId + ", PageId: " + pageId;
    }
}
