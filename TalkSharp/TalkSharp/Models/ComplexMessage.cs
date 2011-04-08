using System;
using System.Collections.Generic;

namespace TalkSharp.Models
{
    public class ComplexMessage
    {
        public ComplexMessage(DateTime Timestamp, Dictionary<string, string> Metadata, List<Message> Messages)
        {
            this.Timestamp = Timestamp;
            this.Metadata = Metadata;
            this.Messages = Messages;
        }

        public DateTime Timestamp { get; set; }

        public Dictionary<string, string> Metadata { get; set; }

        public List<Message> Messages { get; set; }
    }
}