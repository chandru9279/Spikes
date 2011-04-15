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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static Entry.Start.ServerJsonEndpoint;

public class GetData {


    private DefaultHttpClient httpClient;

    public GetData(DefaultHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public void getAllSimple() throws IOException {
        String JSON = doGetRequest(ServerJsonEndpoint + "GetAllSimple");
        Type type = new TypeToken<ArrayList<Message>>() {
        }.getType();
        Gson gson = new Gson();
        List<Message> messages = gson.fromJson(JSON, type);
        for (Message message : messages)
            log(message.toString());
    }

    public void getLast() throws IOException {
        String JSON = doGetRequest(ServerJsonEndpoint + "GetLast");
        Gson gson = new Gson();
        Message message = gson.fromJson(JSON, Message.class);
        log(message.toString());
    }

    public void getAllComplex() throws IOException {
        String JSON = doGetRequest(ServerJsonEndpoint + "GetAllComplex");

        GsonBuilder builder = new GsonBuilder();
        builder.setDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Gson gson = builder.create();

        Type type = new TypeToken<ArrayList<ComplexMessage>>() {
        }.getType();

        List<ComplexMessage> messages = gson.fromJson(JSON, type);
        int messageNumber = 1;
        for (ComplexMessage message : messages)
        {
            log(messageNumber++ + "");
            log(message.toString());
        }
    }

    private String doGetRequest(String url) throws IOException {
        HttpGet httpGet = new HttpGet(url);
        HttpResponse response = httpClient.execute(httpGet);
        HttpEntity entity = response.getEntity();
        log("GET \"" + url + "\" returned : " + response.getStatusLine());
        String JSON = new BufferedReader(new InputStreamReader(entity.getContent())).readLine();
        log("JSON : " + JSON);
        // This utility method clears the InputStream of the entity making the httpClient available for the next request
        EntityUtils.consume(entity);
        return JSON;
    }

    private void log(String log) {
        System.out.println(log);
    }
}
