import Speak.GetData;
import Speak.SaveData;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;


/* http://stackoverflow.com/questions/338586/a-better-java-json-library */

public class Start {

    public static void main(String[] args) throws IOException {
        DefaultHttpClient httpClient = new DefaultHttpClient();
        try {
            GetData getData = new GetData(httpClient);
            SaveData saveData = new SaveData(httpClient);
            getData.GetAll();
            getData.GetLast();
            saveData.saveMessage("Ten");
            getData.GetLast();
            saveData.saveMessage("Eleven");
            getData.GetLast();
            saveData.saveMessage("Twelve");
            getData.GetAll();

        } finally {
            httpClient.getConnectionManager().shutdown();
        }

    }


}
