using System.Collections.Generic;
using System.Web.Mvc;
using TalkSharp.Models;
using TalkSharp.Utility;

namespace TalkSharp.Controllers
{
    public class TalkController : Controller
    {

        private static readonly List<Person> People = InMemoryStore.People;

        [HttpPost]
        public ActionResult SavePerson()
        {
            var XmlPerson = Utils.DeserializeXml<Person>(Request.InputStream);
            People.Add(XmlPerson);
            return new HttpStatusCodeResult(200);
        }

        /*
         * There's no way to prevent XmlSerializer from adding "ArrayOfPerson" as root element
         * 
         * Generated Xml :
         * 
             <ArrayOfPerson>
              <Person>
                <birthday>1981-04-15T12:18:59.1425715+05:30</birthday>
                <addresses>
                  <priority>1</priority>
                  <addresstext>1 Microsoft Way, Redmond</addresstext>
                </addresses>
                <addresses>
                  <priority>2</priority>
                  <addresstext>221B Baker Street, London</addresstext>
                </addresses>
              </Person>
            </ArrayOfPerson>
         */

        public ActionResult GetPeople()
        {
            return new XmlResult<List<Person>>(People);
        }

        public ActionResult GetLast()
        {
            Person LastPerson = People.Count > 0
                                 ? People[People.Count - 1]
                                 : null;
            return new XmlResult<Person>(LastPerson);
        }

        public ActionResult List()
        {
            return View();
        }

    }
}
