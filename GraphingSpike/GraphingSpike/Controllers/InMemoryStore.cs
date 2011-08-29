using System;
using System.Collections.Generic;

namespace GraphingSpike.Controllers
{
    /// <summary>
    /// This class simulates a database. Any data extracted from this class, are to be imagined as having come from database tables.
    /// </summary>
    public class InMemoryStore
    {
        public static Dictionary<int, double> DataSet = GetSimplePointsData(20);

        public static Dictionary<int, double> GetSimplePointsData(int maxYValue)
        {
            var gen = new Random();
            var data = new Dictionary<int, double>();
            10.Times(it => data.Add(it, gen.Next(0, maxYValue)));
            return data;
        }

        public static Dictionary<string, Line> GetSimpleLineData()
        {
            var lines = new Dictionary<string, Line>(3)
                            {
                                {"LineBestInClass", new Line(0.25f, 10)},
                                {"LineIndustryAvg", new Line(0.55f, 3)},
                                {"LineTopQuartile", new Line(0.85f, 7)}
                            };
            return lines;
        }
    }

    public class Line : Dictionary<int, double>
    {
        public Line(float slope, int constant)
        {
            10.Times(it => Add(it, it*slope + constant));
        }
    }
}