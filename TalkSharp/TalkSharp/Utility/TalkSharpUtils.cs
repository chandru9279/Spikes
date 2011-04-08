using System.IO;
using System.Text;
using Newtonsoft.Json;
using Newtonsoft.Json.Converters;

namespace TalkSharp.Utility
{
    public static class TalkSharpUtils
    {
        public static JsonSerializer GetSerializer()
        {
            var Serializer = new JsonSerializer();
            IsoDateTimeConverter IsoDateTimeConverter = new IsoDateTimeConverter();
            IsoDateTimeConverter.DateTimeFormat = "yyyy-MM-dd'T'HH:mm:ss";
            Serializer.Converters.Add(IsoDateTimeConverter);
            return Serializer;   
        }

        public static T Deserialize<T>(Stream RequestStream)
        {
            return GetSerializer().Deserialize<T>(new JsonTextReader(new StreamReader(RequestStream, Encoding.UTF8)));
        }
    }
}