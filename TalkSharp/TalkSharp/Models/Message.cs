namespace TalkSharp.Models
{
    /* Use [JsonIgnore] if you need to ignore a field */

    public class Message
    {
        public Message(int Number, string Text)
        {
            this.Number = Number;
            this.Text = Text;
        }

        public int Number { get; set; }

        public string Text { get; set; }
    }
}