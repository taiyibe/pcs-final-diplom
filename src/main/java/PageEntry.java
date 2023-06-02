import com.fasterxml.jackson.annotation.JsonProperty;

public class PageEntry implements Comparable<PageEntry> {
    @JsonProperty
    private final String pdfName;
    @JsonProperty
    private final int page;
    @JsonProperty
    private final int count;

    public PageEntry(String pName, int pg, int cnt) {
        pdfName = pName;
        page = pg;
        count = cnt;
    }


    public int getCount() {
        return count;
    }

    public int getPage() {
        return page;
    }

    public String getPdfName() {
        return pdfName;
    }

    @Override
    public int compareTo(PageEntry o) {
        return -Integer.compare(count, o.count);
    }
}
