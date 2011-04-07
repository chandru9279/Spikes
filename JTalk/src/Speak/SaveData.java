package Speak;

import Models.Message;
import com.google.gson.Gson;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SaveData {
    private int MessageNumber = 10;

    private DefaultHttpClient httpClient;

    public SaveData(DefaultHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public void saveMessage(String MessageText) throws IOException {
        HttpPost httpPost = new HttpPost("http://localhost:6419/Speak/Save");

        Gson gson = new Gson();

        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
        pairs.add(new BasicNameValuePair("Message", gson.toJson(new Message(MessageNumber, MessageText))));

        httpPost.setEntity(new UrlEncodedFormEntity(pairs, HTTP.UTF_8));
        HttpResponse response = httpClient.execute(httpPost);

        log("POST \"http://localhost:6419/Speak/Save\" returned : " + response.getStatusLine());
        MessageNumber++;

        EntityUtils.consume(response.getEntity());
    }

    private void log(String log) {
        System.out.println(log);
    }
}
