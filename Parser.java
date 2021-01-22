import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class Parser {
    public String readGrammar() throws IOException {
        InputStream is = new FileInputStream("Grammar.txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        String input;
        String grammar = "";
        while((input = br.readLine()) != null){
            grammar += input;
        }

        return grammar;
    }

    public HashMap<String, Rule> createRules() throws IOException {
        HashMap<String, Rule> rules = new HashMap<String, Rule>();
        String grammar = readGrammar();

        String[] rule = grammar.split(";");

        for(String str_rule: rule) {
            str_rule = str_rule.trim();
            String[] productions = str_rule.split(":");
            String[] production = productions[1].split("\\|");
            Rule r = new Rule(productions[0].trim());

            for(String p: production){
                p = p.trim();
                if (p.equals("epsilon"))
                    r.addRHS("");
                else
                    r.addRHS(p);
            }
            rules.put(productions[0].trim(), r);
        }
        return rules;
    }

    public String parse(ArrayList<Token> tokenList, HashMap<String, Rule> rulesMap) {
        Stack<String> stack = new Stack<>();
        Stack<String> backstack = new Stack<>();
        Rule current_r = null;

        int pointer = 0;
        int rule_index = 0;
        boolean noRule = true;
        boolean over = false;

        stack.push("S");

        while (!stack.isEmpty()) {
            Token current_t = null;

            if (pointer < tokenList.size()) {
                current_t = tokenList.get(pointer);
            } else {
                over = true;
            }

            if (!over && current_t.getTokenType().equals("ERROR")) {
                return " - REJECT. Offending token '" + current_t.getLexeme() + "'";
            } else if (stack.peek().equals("")) {
                rule_index = 0;
                stack.pop();
            } else if (!over && stack.peek().equals(current_t.getTokenType())) {
                if (!backstack.isEmpty()) {
                    backstack.pop();
                }
                rule_index = 0;
                stack.pop();
                pointer++;
            } else {
                Rule r = rulesMap.get(stack.peek());
                if (r != null) {
                    current_r = r;
                    ArrayList<String> RHS = r.getRHS();
                    if (rule_index < RHS.size()) {
                        expand(stack, backstack, RHS.get(rule_index));
                    } else if (!over) {
                        return " - REJECT. Offending token '" + current_t.getLexeme() + "'";
                    } else {
                        return " - REJECT. Offending token '" + tokenList.get(pointer-1).getLexeme() + "'";
                    }
                } else if (Token.offendingOpeningBracket(stack.peek()).equals("(")) {
                        return " - REJECT. Offending token '('";
                } else if (Token.offendingOpeningBracket(stack.peek()).equals("[")) {
                    return " - REJECT. Offending token '['";
                } else {
                    rule_index ++;
                    performBacktrack(stack, backstack, current_r);
                }
            }

            if (pointer == tokenList.size() && stack.isEmpty()) {
                noRule = false;
            }
        }

        if (!noRule) {
            return " - ACCEPT";
        } else {
            return " - REJECT. Offending token '" + tokenList.get(pointer).getLexeme() + "'";
        }
    }

    public void expand(Stack stack, Stack backstack, String production) {
        if (stack.isEmpty()) {
            return;
        }

        stack.pop();
        backstack.clear();

        String[] symbol = production.split(" ");

        for(int i = symbol.length-1; i >= 0; i--) {
            stack.push(symbol[i]);
            backstack.push(symbol[i]);
        }
    }

    public void performBacktrack(Stack stack, Stack backstack, Rule rule) {
        if (stack.isEmpty()){
            return;
        }

        while (!backstack.isEmpty()) {
            stack.pop();
            backstack.pop();
        }

        String LHS = rule.getLHS();
        stack.push(LHS);
    }
}