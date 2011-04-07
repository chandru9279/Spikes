using System.Globalization;
using System.Web.Mvc;
using Newtonsoft.Json;
using Newtonsoft.Json.Converters;

namespace TalkSharp.Utility
{
    public class JsonizerResult : ActionResult
    {
        public JsonizerResult(object Response)
        {
            this.Response = Response;
        }

        public object Response { get; set; }

        public override void ExecuteResult(ControllerContext Context)
        {
            Context.HttpContext.Response.ContentType = "application/json";
            var Serializer = new JsonSerializer();
            IsoDateTimeConverter IsoDateTimeConverter = new IsoDateTimeConverter();
            IsoDateTimeConverter.DateTimeFormat = "yyyy-MM-dd'T'HH:mm:ss";
            Serializer.Converters.Add(IsoDateTimeConverter);
            Serializer.Serialize(Context.HttpContext.Response.Output, Response);
        }
    }
}