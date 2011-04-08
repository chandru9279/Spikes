using System;
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

        // This will simulate state - A bunch of Messages stored on the ServerSide
        public static List<Message> Recordings = new List<Message>
                                                     {
                                                         new Message(1, "One"),
                                                         new Message(2, "Two"),
                                                         new Message(3, "Three")
                                                     };

        [HttpPost]
        public ActionResult Save()
        {
            var JsonMessage = TalkSharpUtils.Deserialize<Message>(Request.InputStream);
            Recordings.Add(JsonMessage);
            return new HttpStatusCodeResult(200);
        }

        [HttpPost]
        public ActionResult SaveComplex()
        {
            var JsonComplexMessage = TalkSharpUtils.Deserialize<ComplexMessage>(Request.InputStream);
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
            Message Message = Recordings.Count > 1
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