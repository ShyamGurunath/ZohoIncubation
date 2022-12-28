package ORC;


import java.io.IOException;
import java.text.ParseException;

public class OrcMain {


    public static void main(String[] args) throws IOException, ParseException {
        String filepath = OrcWriter.writeOrc();
       OrcReader.reader(filepath);

    }
}
