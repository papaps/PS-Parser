import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Driver{

    public static void main(String[] args) throws IOException{
        Scanner scanner = new Scanner();
        Parser parser = new Parser();

        InputStream is = new FileInputStream("Input.txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        FileWriter fw = new FileWriter("Output.txt");
        BufferedWriter bw = new BufferedWriter(fw);

        String input;
        String text = "";
        HashMap<String, Rule> rules = parser.createRules();
        while((input = br.readLine()) != null){
            ArrayList<Token> tokenList = scanner.process(input);
            text += input;
            if (tokenList.size() > 0){
                text += parser.parse(tokenList, rules);
            }

            text += "\n";
        }
        text = text.trim();
        bw.write(text);

        is.close();
        br.close();
        bw.close();
    }
}