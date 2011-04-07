package Models;

import java.util.List;

public class Message {

    public Message(int Number, String Text) {
        this.Number = Number;
        this.Text = Text;
    }

    public Message() {
    }

    public int Number;

    public String Text;

    // Gson gives back a LinkedList for C# System.Collections.Generic.List
    public List<Message> Messages;

    // Gson gives back a LinkedHashMap for a .NET Dictionary<string, string>
    public MessageMetadata Metadata;
}
