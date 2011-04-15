package Entry;

import Speak.GetData;
import Speak.SaveData;
import Talk.PersonXChange;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;


/* http://stackoverflow.com/questions/338586/a-better-java-json-library */

public class Start {

    public static final String ServerJsonEndpoint = "http://localhost:6419/Speak/";
    public static final String ServerXmlEndpoint = "http://localhost:6419/Talk/";

    public static void main(String[] args) throws IOException {
        DefaultHttpClient httpClient = new DefaultHttpClient();
        final GetData getData = new GetData(httpClient);
        final SaveData saveData = new SaveData(httpClient);
        final PersonXChange personXChange = new PersonXChange(httpClient);
        Menu menu = new Menu(getData, saveData, personXChange);
        try {
            while (true)
                if (!menu.show()) break;
        } finally {
            httpClient.getConnectionManager().shutdown();
        }
    }
}
