using System.Xml.Serialization;

namespace TalkSharp.Models
{
    [XmlRoot("address")]
    public class Address
    {
        // Xml Serializer insists on having a parameterless constructor
        public Address()
        {
        }

        public Address(int Priority, string AddressText)
        {
            this.Priority = Priority;
            this.AddressText = AddressText;
        }

        [XmlElement("priority")]
        public int Priority { get; set; }

        [XmlElement("addressText")]
        public string AddressText { get; set; }
    }
}