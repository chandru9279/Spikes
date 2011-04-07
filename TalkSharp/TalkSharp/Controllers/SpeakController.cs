using System;
using System.Collections.Generic;
using System.IO;
using System.Web.Mvc;
using Newtonsoft.Json;
using TalkSharp.Models;
using TalkSharp.Utility;

namespace TalkSharp.Controllers
{
    /* Couple of options available : Json.NET (use custom JsonizerResult) / JavaScriptSerializer (use return this.Json(object))
     * http://stackoverflow.com/questions/509632/which-is-faster-asp-net-mvc-json-or-json-net */

    public class SpeakController : Controller
    {
        private static readonly Dictionary<string, string> _Dictionary = new Dictionary<string, string>
                                                                             {
                                                                                 {"SenderName", "Sender"},
                                                                                 {"Tag", "Urgent"}
                                                                             };

        // This will simulate state - A bunch of Messages stored on the ServerSide
        public static List<Message> Recordings = new List<Message>
                                                     {
                                                         new Message(1, "One"),
                                                         new Message(2, "Two", new List<Message>
                                                                                   {
                                                                                       new Message(3, "Three"),
                                                                                       new Message(4, "Four")
                                                                                   }),
                                                         new Message(5, "Five", new MessageMetadata
                                                                                    {
                                                                                        TimeStamp = DateTime.Now,
                                                                                        Pairs = _Dictionary
                                                                                    })
                                                     };

        [HttpPost]
        public ActionResult Save(string Message)
        {
            var Serializer = new JsonSerializer();
            var DeserializedMessage = Serializer.Deserialize<Message>(new JsonTextReader(new StringReader(Message)));
            Recordings.Add(DeserializedMessage);
            return new HttpStatusCodeResult(200);
        }

        public ActionResult GetAll()
        {
            return new JsonizerResult(Recordings);
        }

        public ActionResult GetLast()
        {
            Message Message = Recordings.Count > 1
                                  ? Recordings[Recordings.Count - 1]
                                  : new Message(0, "No Messages");
            return new JsonizerResult(Message);
        }

        public ActionResult List()
        {
            return View(Recordings);
        }
    }
}