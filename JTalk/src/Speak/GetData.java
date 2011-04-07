package Speak;

import Models.Message;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GetData {

    private DefaultHttpClient httpClient;

    public GetData(DefaultHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public void GetAll() throws IOException {
        HttpGet httpGet = new HttpGet("http://localhost:6419/Speak/GetAll");

        HttpResponse response = httpClient.execute(httpGet);
        HttpEntity entity = response.getEntity();
        log("GET \"http://localhost:6419/Speak/GetAll\" returned : " + response.getStatusLine());

        GsonBuilder b = new GsonBuilder();
        b.setDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Gson gson = b.create();

        Type type = new TypeToken<ArrayList<Message>>() {
        }.getType();

        List<Message> messages = gson.fromJson(new InputStreamReader(entity.getContent()), type);
        for (Message message : messages)
            logMessage(message);

        // This utility method clears the InputStream of the entity making it available for the next request
        EntityUtils.consume(entity);
    }

    public void GetLast() throws IOException {
        HttpGet httpGet = new HttpGet("http://localhost:6419/Speak/Getlast");

        HttpResponse response = httpClient.execute(httpGet);
        HttpEntity entity = response.getEntity();
        log("GET \"http://localhost:6419/Speak/Getlast\" returned : " + response.getStatusLine());

        GsonBuilder b = new GsonBuilder();
        b.setDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Gson gson = b.create();
        Message message = gson.fromJson(new InputStreamReader(entity.getContent()), Message.class);
        logMessage(message);

        EntityUtils.consume(entity);
    }

    private void logMessage(Message message) {
        log(message.Number + " : " + message.Text);
        if (message.Messages != null)
            for (Message nestedMessage : message.Messages)
                log("\t" + nestedMessage.Number + " : " + nestedMessage.Text);
        if (message.Metadata != null)
        {
            if(message.Metadata.TimeStamp != null)
                log("\t TimeStamp : " + message.Metadata.TimeStamp);
            if(message.Metadata.Pairs != null) {
                for (Map.Entry<String, String> pair : message.Metadata.Pairs.entrySet())
                    log("\t" + pair.getKey() + " : " + pair.getValue());
            }
        }
    }

    private void log(String log) {
        System.out.println(log);
    }
}
