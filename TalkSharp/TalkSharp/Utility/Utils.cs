using System.IO;
using System.Text;
using System.Xml;
using System.Xml.Serialization;
using Newtonsoft.Json;
using Newtonsoft.Json.Converters;
using Formatting = System.Xml.Formatting;

namespace TalkSharp.Utility
{
    public static class Utils
    {

        #region  -----------===== JSON =====-----------  

        public static JsonSerializer GetJsonSerializer()
        {
            var Serializer = new JsonSerializer();
            IsoDateTimeConverter IsoDateTimeConverter = new IsoDateTimeConverter();
            IsoDateTimeConverter.DateTimeFormat = "yyyy-MM-dd'T'HH:mm:ss";
            Serializer.Converters.Add(IsoDateTimeConverter);
            return Serializer;
        }

        public static T DeserializeJson<T>(Stream RequestStream)
        {
            return GetJsonSerializer().Deserialize<T>(new JsonTextReader(new StreamReader(RequestStream, Encoding.UTF8)));
        }

        
        #endregion






        #region -----------===== XML =====-----------

        /* There's a bunch of workarounds that needed to be done to get xstream be compatible with System.Xml.XmlSerailizer*/

        public static void XmlSerialize<T>(T Model, TextWriter ResponseStream)
        {
            XmlSerializer Serializer = new XmlSerializer(typeof (T));
            // Suppress the default xsd and xsd-instance namespaces from the root element.
            // xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema"
            XmlSerializerNamespaces Namespaces = new XmlSerializerNamespaces();
            Namespaces.Add("", "");

            Serializer.Serialize(new XmlTextWriterFormattedNoDeclaration(ResponseStream), Model, Namespaces);
        }

        public static T DeserializeXml<T>(Stream RequestStream) where T : class 
        {
            XmlSerializer Serializer = new XmlSerializer(typeof (T));
            return Serializer.Deserialize(RequestStream) as T;
        }

        #region Nested type: XmlTextWriterFormattedNoDeclaration

        public class XmlTextWriterFormattedNoDeclaration : XmlTextWriter
        {
            // Remove formatting : Why sometimes this default does not apply?
            public XmlTextWriterFormattedNoDeclaration(TextWriter Writer)
                : base(Writer)
            {
                //Removes all newlines & tabs etc
                Formatting = Formatting.None;
            }

            // Eliminates the XML Documentation at the start of a XML doc.
            // <?xml version="1.0" encoding="utf-8" ?> 
            public override void WriteStartDocument()
            {
            }
        }

        #endregion

        #endregion
    }
}