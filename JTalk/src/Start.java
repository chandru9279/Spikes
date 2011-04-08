import Speak.GetData;
import Speak.SaveData;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;


/* http://stackoverflow.com/questions/338586/a-better-java-json-library */

public class Start {

    public static void main(String[] args) throws IOException {
        DefaultHttpClient httpClient = new DefaultHttpClient();
        final GetData getData = new GetData(httpClient);
        final SaveData saveData = new SaveData(httpClient);
        Menu menu = new Menu(getData, saveData);
        try {
            while (true)
                if (!menu.show()) break;
        } finally {
            httpClient.getConnectionManager().shutdown();
        }
    }
}
