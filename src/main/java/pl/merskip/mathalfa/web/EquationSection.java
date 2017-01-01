package pl.merskip.mathalfa.web;

import pl.merskip.mathalfa.base.core.Symbol;
import pl.merskip.mathalfa.base.infixparser.ToPostfixConverter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class EquationSection extends Section {
    
    private List<Symbol> members;
    
    public EquationSection(String title) {
        super(title);
        members = new ArrayList<>();
    }
    
    public EquationSection(String title, Symbol leftMember, Symbol rightMember) {
        this(title);
        add(leftMember).add(rightMember);
    }
    
    public EquationSection add(Symbol member) {
        members.add(member);
        return this;
    }
    
    public List<Symbol> getMembers() {
        return members;
    }
    
    public String getJsonRPN() {
        return "[ "+ getRPN().stream()
                .map(s -> "\"" + s + "\"")
                .collect(Collectors.joining(", "))
                + " ]";
    }
    
    public List<String> getRPN() {
        List<String> rpn = new ArrayList<>();
        members.forEach(member -> rpn.addAll(ToPostfixConverter.toPostfix(member)));
        rpn.addAll(Collections.nCopies(members.size() - 1, "="));
        return rpn;
    }
}
