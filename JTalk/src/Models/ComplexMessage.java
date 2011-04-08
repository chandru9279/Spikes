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

}
