import java.util.ArrayList;

public class Scanner{
    ArrayList<Token> process (String input) {
        String[] parts = input.split(" ");
        ArrayList<String> words = new ArrayList<String>();

        for(String p: parts) {
            String thing = "";
            char[] chartemp = p.toCharArray();

            for(char c: chartemp) {
                if (c == '+' || c == '*' || c == '(' || c == ')' || c == '[' || c == ']') {
                    if (!thing.isEmpty()) {
                        words.add(thing);
                    }
                    thing = String.valueOf(c);
                    words.add(thing);
                    thing = "";
                } else {
                    thing += String.valueOf(c);
                }
            }
            if (!thing.isEmpty()) {
                words.add(thing);
            }
        }

        ArrayList<Token> tokenList = new ArrayList();
        for (String w : words) {
            if (w.equals("")) {
                //do nothing
            } else {
                Token t = new Token(w);
                tokenList.add(t);
            }
        }

        return tokenList;
    }
}