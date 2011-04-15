using System;
using System.Collections.Generic;
using System.Globalization;
using System.Xml.Serialization;

namespace TalkSharp.Models
{
    [XmlRoot("person")]
    public class Person
    {
        public static string formatString = "yyyy-MM-ddTHH:mm:ss.fffzzz";
   
        private DateTime _Birthday;

        // Xml Serializer insists on having a parameterless constructor
        public Person()
        {
        }

        public Person(DateTime Birthday, Dictionary<string, string> ProfileFields, List<Address> Addresses)
        {
            _Birthday = Birthday;
            Profile = ProfileFields;
            this.Addresses = Addresses;
        }

        // I had to make Birthday property into a string - no other choice, like doing this once cross cuttingly - while serializing/while creating a serializer etc.
        // It works for basic DateTime information like just the year, month and day WITHOUT converting it into a string. If you want time & timezone, you need to use this
        // workaround and represent DateTime as strings in our Model/Domain classes
        // http://blogs.msdn.com/b/dotnetinterop/archive/2007/10/10/date-and-time-values-and-xstream-interop.aspx
        [XmlElement("birthday")]
        public string Birthday
        {
            get { return _Birthday.ToString(formatString); }
            set
            {
                try
                {
                    _Birthday =
                        DateTime.ParseExact(value, new[] {formatString},
                                            new CultureInfo("en-US", true),
                                            DateTimeStyles.AllowWhiteSpaces);
                }
                catch
                {
                }
            }
        }

        /* 
         * System.Xml.XmlSerializer cannot serialize Dictionaries and HashTables
         * 
         * MSDN Quote:
         * "The XmlSerializer cannot process classes implementing the IDictionary interface. 
         * This was partly due to schedule constraints and partly due to the fact that a hashtable 
         * does not have a counterpart in the XSD type system. The only solution is to implement 
         * a custom hashtable that does not implement the IDictionary interface."
         * 
         * http://msdn.microsoft.com/en-us/library/ms950721.aspx - Look at the FAQ portion
         * 
         * Throws a System.InvalidOperationException if you try it - Details are in 3rd level InnerException
         * Serializer throws Exceptions without messages xD
         * 
         * xstream.net does this, but has some issues regarding verbosity and readability :
         * http://code.google.com/p/xstream-dot-net/issues/detail?id=18
         * 
         * and the last release stopped @ Sep 23, 2009, used to happen every month. Activity in project is low.
         * http://code.google.com/p/xstream-dot-net/source/list
         * 
         */

        [XmlIgnore]
        public Dictionary<string, string> Profile { get; set; }

        [XmlElement("address")]
        public List<Address> Addresses { get; set; }
    }
}