package Models;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class ComplexMessage {

    public Date Timestamp;

    // Gson gives back a LinkedList for C# System.Collections.Generic.List
    public List<Message> Messages;

    // Gson gives back a LinkedHashMap for a .NET Dictionary<string, string>
    public Map<String, String> Metadata;




    public String toString() {
        StringBuilder builder = new StringBuilder();

        if (Messages != null) {
            builder.append("Messages : \n");
            for (Message nestedMessage : Messages)
                builder.append("\t" + nestedMessage.Number + " : " + nestedMessage.Text);
        }
        builder.append("\nTimeStamp : " + Timestamp + "\n");
        if (Metadata != null) {
            builder.append("Metadata : \n");
            for (Map.Entry<String, String> pair : Metadata.entrySet())
                builder.append("\t" + pair.getKey() + " : " + pair.getValue() + "\n");
        }
        return builder.toString();
    }
}
