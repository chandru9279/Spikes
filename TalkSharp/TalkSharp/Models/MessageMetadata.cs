using System;
using System.Collections.Generic;

namespace TalkSharp.Models
{
    public class MessageMetadata
    {
        public DateTime TimeStamp { get; set; }

        public Dictionary<string, string> Pairs { get; set; }
    }
}