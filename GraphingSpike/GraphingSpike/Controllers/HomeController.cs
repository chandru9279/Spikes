using System.Web.Mvc;

namespace GraphingSpike.Controllers
{
    public class HomeController : Controller
    {
        public ActionResult Default()
        {
            return RedirectToAction("Show", "Graphs");
        }

    }
}
