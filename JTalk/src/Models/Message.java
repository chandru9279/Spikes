package Models;

public class Message {

    public Message(int Number, String Text) {
        this.Number = Number;
        this.Text = Text;
    }

    public Message() {
    }

    public int Number;

    public String Text;


    @Override
    public String toString() {
        return Number + " : " + Text;
    }
}
