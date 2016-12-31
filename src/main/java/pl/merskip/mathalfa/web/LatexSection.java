package pl.merskip.mathalfa.web;

public class LatexSection {
    
    private String title;
    private String latex;
    
    public LatexSection(String title, String latex) {
        this.title = title;
        this.latex = latex;
    }
    
    public String getTitle() {
        return title;
    }
    
    public String getLatex() {
        return latex;
    }
}
