package pl.merskip.mathalfa.web;

import pl.merskip.mathalfa.base.core.Symbol;

public class SymbolSection extends Section {
    
    private Symbol symbol;
    
    public SymbolSection(String title, Symbol symbol) {
        super(title);
        this.symbol = symbol;
    }
    
    public Symbol getSymbol() {
        return symbol;
    }
}
