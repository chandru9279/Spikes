using System.Collections.Generic;

namespace TalkSharp.Models
{
    public class Message
    {
        public Message(int Number, string Text)
        {
            this.Number = Number;
            this.Text = Text;
        }

        public Message(int Number, string Text, List<Message> Messages) : this(Number, Text)
        {
            this.Messages = Messages;
        }

        public Message(int Number, string Text, MessageMetadata Metadata) : this(Number, Text)
        {
            this.Metadata = Metadata;
        }

        public Message()
        {
        }

        public int Number { get; set; }

        public string Text { get; set; }

        public List<Message> Messages { get; set; }

        public MessageMetadata Metadata { get; set; }
    }
}