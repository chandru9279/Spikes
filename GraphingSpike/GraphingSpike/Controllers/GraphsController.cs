using System;
using System.Diagnostics;
using System.IO;
using System.Web;
using System.Web.Mvc;
using System.Web.UI.DataVisualization.Charting;

namespace GraphingSpike.Controllers
{
    public class GraphsController : Controller
    {
        private readonly string[] lines;

        public GraphsController()
        {
            lines = new[]
                        {
                            "LineBestInClass",
                            "LineIndustryAvg",
                            "LineTopQuartile"
                        };
        }

        public ActionResult Show()
        {
            Array.ForEach(lines, it => Session[it] = true);
            Chart chart = RefreshChartAndUpdateSession();
            return View(new GraphsViewModel
                            {
                                ImageMap = new MvcHtmlString(chart.GetHtmlImageMap("GraphImageMap")),
                            });
        }

        /* This method has to be called immediately after Show or ChartTable is called. */

        public FileContentResult ChartImage()
        {
            Response.Cache.SetCacheability(HttpCacheability.NoCache);
            return File(((byte[]) Session["Image"]), "image/png");
        }

        public ActionResult ChartTable()
        {
            Chart chart = RefreshChartAndUpdateSession();
            return View(new GraphsViewModel
                            {
                                ImageMap =
                                    new MvcHtmlString(chart.GetHtmlImageMap("GraphImageMap")),
                            });
        }

        public HttpStatusCodeResult Remove([Bind(Prefix = "id")] int key)
        {
            if (InMemoryStore.DataSet.ContainsKey(key))
                InMemoryStore.DataSet.Remove(key);
            else
                Debug.Print("Random key came - {0}", key);
            return new HttpStatusCodeResult(200);
        }

        public HttpStatusCodeResult ToggleSeries([Bind(Prefix = "id")] string seriesName)
        {
            try
            {
                Session[seriesName] = !(bool) Session[seriesName];
                return new HttpStatusCodeResult(200);
            }
            catch (ArgumentException)
            {
                return new HttpStatusCodeResult(404);
            }
        }


        private Chart RefreshChartAndUpdateSession()
        {
            var chart = new Chart
                            {
                                Width = 600,
                                Height = 400,
                                RenderType = RenderType.BinaryStreaming,
                                Palette = ChartColorPalette.Bright
                            };

            chart.ChartAreas.Add("Main");
            chart.CreateSeries("DataSet", InMemoryStore.DataSet, SeriesChartType.Point, true, true);

            Array.ForEach(lines,
                          it => { if ((bool) Session[it]) chart.CreateSeries(it, InMemoryStore.GetSimpleLineData()[it]); });
            // http://geekswithblogs.net/DougLampe/archive/2011/01/23/charts-in-asp.net-mvc-2-with-drill-down.aspx

            Session["Graph"] = chart;
            using (var memoryStream = new MemoryStream())
            {
                chart.SaveImage(memoryStream, ChartImageFormat.Png);
                Session["Image"] = memoryStream.ToArray();
            }
            return chart;
        }

        #region Nested type: GraphsViewModel

        public class GraphsViewModel
        {
            public MvcHtmlString ImageMap { get; set; }
        }

        #endregion
    }
}