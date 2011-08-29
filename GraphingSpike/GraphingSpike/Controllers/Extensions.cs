using System;
using System.Collections.Generic;
using System.Web.UI.DataVisualization.Charting;

namespace GraphingSpike.Controllers
{
    public static class Extensions
    {
        public static void Times(this int count, Action<int> action)
        {
            for (var i = 0; i < count; i++)
                action(i);
        }

        public static void CreateSeries(this Chart chart, string name, Dictionary<int, double> data, SeriesChartType chartType = SeriesChartType.Line, bool markers = false, bool interactive = false)
        {
            chart.Series.Add(name);
            chart.Series[name].ChartType = chartType;
            foreach (var item in data)
                chart.Series[name].Points.AddXY(item.Key, item.Value);
            chart.Series[name].MarkerSize = 10;
            chart.Series[name].MarkerStyle = markers ? MarkerStyle.Circle : MarkerStyle.None;
            if(interactive) 
                chart.Series[name].MapAreaAttributes ="id=\"#SER-#INDEX\" class=\"InteractiveArea\"";
        }
    }
}