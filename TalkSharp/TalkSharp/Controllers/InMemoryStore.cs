using System;
using System.Collections.Generic;
using TalkSharp.Models;

namespace TalkSharp.Controllers
{

    // This is our In Memory Database - simulates state on the Service-Side
    public class InMemoryStore
    {
        private static readonly Dictionary<string, string> SampleMetadata = new Dictionary<string, string>
                                                                                {
                                                                                    {"SenderName", "Sender"},
                                                                                    {"Tag", "Urgent"}
                                                                                };

        public static List<ComplexMessage> ComplexRecordings = new List<ComplexMessage>
                                                                   {
                                                                       new ComplexMessage(
                                                                           DateTime.Now,
                                                                           SampleMetadata, new List<Message>
                                                                                               {
                                                                                                   new Message(4, "Four"),
                                                                                                   new Message(5, "Five")
                                                                                               })
                                                                   };

        public static List<Message> Recordings = new List<Message>
                                                     {
                                                         new Message(1, "One"),
                                                         new Message(2, "Two"),
                                                         new Message(3, "Three")
                                                     };

        private static readonly Dictionary<string, string> ProfileFields = new Dictionary<string, string>
                                                                                {
                                                                                    {"Likes", "Fiction, Movies.."},
                                                                                    {"FavoriteQuote", "No Pressure, No Diamonds"}
                                                                                };

        public static List<Person> People = new List<Person>
                                                {
                                                    new Person(
                                                        DateTime.Now.AddYears(-30),
                                                        ProfileFields, new List<Address>
                                                                            {
                                                                                new Address(1, "1 Microsoft Way, Redmond"),
                                                                                new Address(2, "221B Baker Street, London")
                                                                            })
                                                };
    }
}