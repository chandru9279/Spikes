package Talk;

import Models.Address;
import Models.Person;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.extended.ISO8601DateConverter;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static Entry.Start.ServerXmlEndpoint;

public class PersonXChange {
    private DefaultHttpClient httpClient;

    public PersonXChange(DefaultHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public void getPeople() throws IOException {
        String XML = doGetRequest(ServerXmlEndpoint + "GetPeople");
        XStream xstream = getXStreamSerializer();
        List<Person> people = (List<Person>) xstream.fromXML(XML);
        int personNumber = 1;
        for (Person person : people) {
            log(personNumber++ + "");
            log(person.toString());
        }
    }

    public void getLast() throws IOException {
        String XML = doGetRequest(ServerXmlEndpoint + "GetLast");
        XStream xstream = getXStreamSerializer();
        Person person = (Person) xstream.fromXML(XML, new Person());
        log(person.toString());
    }

    public void sendPerson(Date birthDay, ArrayList<Address> addresses) throws IOException {
        Person person = new Person(birthDay, addresses);
        HttpPost httpPost = new HttpPost(ServerXmlEndpoint + "SavePerson");

        XStream xstream = getXStreamSerializer();
        String XML = xstream.toXML(person);
        httpPost.setEntity(new StringEntity(XML, "application/xml", HTTP.UTF_8));
        log("Sending XML : \n" + XML);
        HttpResponse response = httpClient.execute(httpPost);
        log("POST \"" + ServerXmlEndpoint + "SavePerson" + "\" returned : " + response.getStatusLine());
        EntityUtils.consume(response.getEntity());
    }

    private String doGetRequest(String url) throws IOException {
        HttpGet httpGet = new HttpGet(url);
        HttpResponse response = httpClient.execute(httpGet);
        HttpEntity entity = response.getEntity();
        log("GET \"" + url + "\" returned : " + response.getStatusLine());

        String XML = new BufferedReader(new InputStreamReader(entity.getContent())).readLine();
        log("XML : " + XML);
        // This utility method clears the InputStream of the entity making the httpClient available for the next request
        EntityUtils.consume(entity);
        return XML;
    }

    private XStream getXStreamSerializer() {
        XStream xstream = new XStream();

/*      Use [this kind (below) ] of configuration or [annotations and processAnnotation]
 *      but on the whole, needs some configuration, in quite a few places.
 *
 *      xstream.alias("person", Person.class);
 *      xstream.alias("birthday", Person.class);
 */
        xstream.alias("ArrayOfPerson", LinkedList.class);
        xstream.alias("Person", Person.class);
        xstream.processAnnotations(new Class[]{Person.class, Address.class});
        xstream.registerConverter(new ISO8601DateConverter());
        return xstream;
    }

    private void log(String log) {
        System.out.println(log);
    }
}
