public class Token{
    public enum TokenType{
        NUM,
        ADD,
        MUL,
        LPAREN,
        RPAREN,
        LBRACK,
        RBRACK,
        ERROR
    }

    public TokenType tokenType;
    public String lexeme;

    private static final int q0 = 0;
    private static final int q1 = 1;
    private static final int q2 = 2;
    private static final int q3 = 3;
    private static final int q4 = 4;
    private static final int q5 = 5;
    private static final int q6 = 6;
    private static final int q7 = 7;
    private static final int qdead = -1;

    private int state;

    public Token (String word) {
        this.lexeme = word;
        this.tokenType = identifyToken(word);
    }

    static private int transitions (int s, char c) {
        switch (s) {
            case q0: switch (c) {
                case '0': case '1': case '2': case '3': case '4': case '5': case '6': case '7': case '8': case '9': return q1;
                case '+': return q2;
                case '*': return q3;
                case '(': return q4;
                case ')': return q5;
                case '[': return q6;
                case ']': return q7;
                default: return qdead;
            }
            case q1: switch (c) {
                case '0': case '1': case '2': case '3': case '4': case '5': case '6': case '7': case '8': case '9': return q1;
                default: return qdead;
            }
            case q2: switch (c) {
                default: return qdead;
            }
            case q3: switch (c) {
                default: return qdead;
            }
            case q4: switch (c) {
                default: return qdead;
            }
            case q5: switch (c) {
                default: return qdead;
            }
            case q6: switch (c) {
                default: return qdead;
            }
            case q7: switch (c) {
                default: return qdead;
            }
            default: return qdead;
        }
    }

    public TokenType identifyToken(String word){
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            state = transitions(state, c);
        }
        if (state == 1)
            return TokenType.NUM;
        else if (state == 2)
            return TokenType.ADD;
        else if (state == 3)
            return TokenType.MUL;
        else if (state == 4)
            return TokenType.LPAREN;
        else if (state == 5)
            return TokenType.RPAREN;
        else if (state == 6)
            return TokenType.LBRACK;
        else if (state == 7)
            return TokenType.RBRACK;
        else
            return TokenType.ERROR;
    }

    public String getTokenType() {return tokenType.toString();}
    public String getLexeme() {return lexeme;}

    public static boolean checkTokenType(String type) {
        for (TokenType token: TokenType.values()) {
            if (token.toString().equals(type))
                return true;
        }
        return false;
    }

    public static String offendingOpeningBracket(String type) {
        if (type.equals(TokenType.RPAREN.toString())){
            return "(";
        } else if (type.equals(TokenType.RBRACK.toString())){
            return "[";
        }
        return "";
    }

    @Override
    public String toString(){
        return this.tokenType.toString();
    }
}