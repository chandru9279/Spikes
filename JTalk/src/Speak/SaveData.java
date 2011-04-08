package Speak;

import Models.ComplexMessage;
import Models.Message;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

public class SaveData {
    private int messageNumber = 10;

    private DefaultHttpClient httpClient;

    public SaveData(DefaultHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public void sendMessage(String MessageText) throws IOException {
        doPostRequest(new Message(messageNumber, MessageText), new Gson(), "http://localhost:6419/Speak/Save");
    }

    public void sendComplexMessage(Map<String, String> metadata, String[] messages) throws IOException {

        GsonBuilder builder = new GsonBuilder();
        builder.setDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Gson gson = builder.create();

        ComplexMessage complexMessage = new ComplexMessage();
        complexMessage.Messages = new ArrayList<Message>();
        for (int i = 0; i < messages.length; i++)
            if (isNotEmpty(messages[i]))
                complexMessage.Messages.add(new Message(messageNumber, messages[i]));
        complexMessage.Timestamp = new Date();
        complexMessage.Metadata = metadata;

        doPostRequest(complexMessage, gson, "http://localhost:6419/Speak/SaveComplex");
    }

    private void doPostRequest(Object message, Gson gson, String url) throws IOException {
        HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(new StringEntity(gson.toJson(message), "application/json", HTTP.UTF_8));
        HttpResponse response = httpClient.execute(httpPost);

        log("POST \"" + url + "\" returned : " + response.getStatusLine());
        messageNumber += 2;
        EntityUtils.consume(response.getEntity());
    }

    public boolean isNotEmpty(String message) {
        return message != null && !message.trim().equals("");
    }

    private void log(String log) {
        System.out.println(log);
    }
}
