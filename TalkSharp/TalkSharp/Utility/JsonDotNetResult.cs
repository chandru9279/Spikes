using System.Web.Mvc;
using Newtonsoft.Json;

namespace TalkSharp.Utility
{
    public class JsonDotNetResult : ActionResult
    {
        public JsonDotNetResult(object Response)
        {
            this.Response = Response;
        }

        public object Response { get; set; }

        public override void ExecuteResult(ControllerContext Context)
        {
            Context.HttpContext.Response.ContentType = "application/json";
            JsonSerializer Serializer = Utils.GetJsonSerializer();
            Serializer.Serialize(Context.HttpContext.Response.Output, Response);
        }
    }
}