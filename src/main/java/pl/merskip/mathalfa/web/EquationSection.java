package pl.merskip.mathalfa.web;

import pl.merskip.mathalfa.base.core.Symbol;

import java.util.ArrayList;
import java.util.List;

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
}
