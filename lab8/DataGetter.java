package lab8;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class DataGetter {
    public String getData() throws Exception{
        URL oracle = new URL("http://localhost:3000/");
        BufferedReader in = new BufferedReader(
                new InputStreamReader(oracle.openStream()));

        String inputLine;
        StringBuilder data = new StringBuilder();
        while ((inputLine = in.readLine()) != null)
            data.append(inputLine);
        in.close();
        return data.toString();
    }
}
