using System.Web.Mvc;

namespace TalkSharp.Utility
{
    public class XmlResult<T> : ActionResult
    {
        public XmlResult(T Response)
        {
            this.Response = Response;
        }

        public T Response { get; set; }

        public override void ExecuteResult(ControllerContext Context)
        {
            Context.HttpContext.Response.ContentType = "application/xml";
            Utils.XmlSerialize(Response, Context.HttpContext.Response.Output);
        }
    }
}