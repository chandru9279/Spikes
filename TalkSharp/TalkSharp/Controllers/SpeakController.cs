using System.Collections.Generic;
using System.Web.Mvc;
using TalkSharp.Models;
using TalkSharp.Utility;

namespace TalkSharp.Controllers
{
    /* Couple of options available : Json.NET (use custom JsonizerResult) / JavaScriptSerializer (use return this.Json(object))
     * http://stackoverflow.com/questions/509632/which-is-faster-asp-net-mvc-json-or-json-net */

    public class SpeakController : Controller
    {
        private static readonly List<Message> Recordings = InMemoryStore.Recordings;
        private static readonly List<ComplexMessage> ComplexRecordings = InMemoryStore.ComplexRecordings;
        
        [HttpPost]
        public ActionResult Save()
        {
            var JsonMessage = Utils.DeserializeJson<Message>(Request.InputStream);
            Recordings.Add(JsonMessage);
            return new HttpStatusCodeResult(200);
        }

        [HttpPost]
        public ActionResult SaveComplex()
        {
            var JsonComplexMessage = Utils.DeserializeJson<ComplexMessage>(Request.InputStream);
            ComplexRecordings.Add(JsonComplexMessage);
            return new HttpStatusCodeResult(200);
        }

        public ActionResult GetAllSimple()
        {
            return new JsonDotNetResult(Recordings);
        }

        public ActionResult GetAllComplex()
        {
            return new JsonDotNetResult(ComplexRecordings);
        }


        public ActionResult GetLast()
        {
            Message Message = Recordings.Count > 0
                                                ? Recordings[Recordings.Count - 1]
                                                : new Message(0, "No Messages");
            return new JsonDotNetResult(Message);
        }

        public ActionResult List()
        {
            return View();
        }
    }
}