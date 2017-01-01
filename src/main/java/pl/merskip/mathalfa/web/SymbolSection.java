package pl.merskip.mathalfa.web;

import pl.merskip.mathalfa.base.core.Symbol;
import pl.merskip.mathalfa.base.infixparser.ToPostfixConverter;

import java.util.List;
import java.util.stream.Collectors;

public class SymbolSection extends Section {
    
    private Symbol symbol;
    
    public SymbolSection(String title, Symbol symbol) {
        super(title);
        this.symbol = symbol;
    }
    
    public Symbol getSymbol() {
        return symbol;
    }
    
    public String getJsonRPN() {
        return "[ "+ getRPN().stream()
                .map(s -> "\"" + s + "\"")
                .collect(Collectors.joining(", "))
                + " ]";
    }
    
    public List<String> getRPN() {
        return ToPostfixConverter.toPostfix(symbol);
    }
}
