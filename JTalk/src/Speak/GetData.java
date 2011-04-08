package Speak;

import Models.ComplexMessage;
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

    public void GetAllSimple() throws IOException {
        HttpEntity entity = doGetRequest("http://localhost:6419/Speak/GetAllSimple");

        Gson gson = new Gson();

        Type type = new TypeToken<ArrayList<Message>>() {
        }.getType();

        List<Message> messages = gson.fromJson(new InputStreamReader(entity.getContent()), type);
        for (Message message : messages)
            logMessage(message);

        // This utility method clears the InputStream of the entity making the httpClient available for the next request
        EntityUtils.consume(entity);
    }

    public void GetLast() throws IOException {
        HttpEntity entity = doGetRequest("http://localhost:6419/Speak/GetLast");

        Gson gson = new Gson();
        Message message = gson.fromJson(new InputStreamReader(entity.getContent()), Message.class);
        logMessage(message);

        EntityUtils.consume(entity);
    }

    public void GetAllComplex() throws IOException {
        HttpEntity entity = doGetRequest("http://localhost:6419/Speak/GetAllComplex");

        GsonBuilder builder = new GsonBuilder();
        builder.setDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Gson gson = builder.create();

        Type type = new TypeToken<ArrayList<ComplexMessage>>() {
        }.getType();

        List<ComplexMessage> messages = gson.fromJson(new InputStreamReader(entity.getContent()), type);
        int messageNumber = 1;
        for (ComplexMessage message : messages)
        {
            log(messageNumber++ + "");
            logComplexMessage(message);
        }
    }

    private HttpEntity doGetRequest(String url) throws IOException {
        HttpGet httpGet = new HttpGet(url);
        HttpResponse response = httpClient.execute(httpGet);
        HttpEntity entity = response.getEntity();
        log("GET \"" + url + "\" returned : " + response.getStatusLine());
        return entity;
    }

    private void logMessage(Message message) {
        log(message.Number + " : " + message.Text);
    }

    private void logComplexMessage(ComplexMessage message) {
        if (message.Messages != null) {
            log("Messages : ");
            for (Message nestedMessage : message.Messages)
                log("\t" + nestedMessage.Number + " : " + nestedMessage.Text);
        }
        log("TimeStamp : " + message.Timestamp);
        if (message.Metadata != null) {
            log("Metadata : ");
            for (Map.Entry<String, String> pair : message.Metadata.entrySet())
                log("\t" + pair.getKey() + " : " + pair.getValue());
        }
    }


    private void log(String log) {
        System.out.println(log);
    }
}
