package pl.merskip.mathalfa.web;

public abstract class Section {
    
    private String title;
    
    public Section(String title) {
        this.title = title;
    }
    
    public String getTitle() {
        return title;
    }
}
