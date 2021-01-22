import java.util.ArrayList;

public class Rule {
    private String LHS;
    private ArrayList<String> RHS;

    public Rule(String LHS) {
        this.LHS = LHS;
        this.RHS = new ArrayList<>();
    }

    public String getLHS() {
        return LHS;
    }

    public ArrayList<String> getRHS() {
        return RHS;
    }

    public void setLHS(String LHS) {
        this.LHS = LHS;
    }

    public void addRHS(String RHS) {
        this.RHS.add(RHS);
    }
}