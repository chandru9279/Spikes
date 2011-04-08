using System.Web.Mvc;
using System.Web.Routing;
using TalkSharp.Utility;

namespace TalkSharp
{
    // Note: For instructions on enabling IIS6 or IIS7 classic mode, 
    // visit http://go.microsoft.com/?LinkId=9394801

    public class MvcApplication : System.Web.HttpApplication
    {
        public static void RegisterGlobalFilters(GlobalFilterCollection Filters)
        {
            Filters.Add(new HandleErrorAttribute());
        }

        public static void RegisterRoutes(RouteCollection Routes)
        {
            Routes.IgnoreRoute("{resource}.axd/{*pathInfo}");

            Routes.MapRoute(
                "Default", // Route name
                "{controller}/{action}/{id}", // URL with parameters
                new { controller = "Speak", action = "List", id = UrlParameter.Optional } // Parameter defaults
            );

        }

        protected void Application_Start()
        {
            AreaRegistration.RegisterAllAreas();

            /*  Before writing my custom Json ModelBinder I searched and got this :) 
             *  http://mgolchin.net/posts/19/dive-deep-into-mvc-ivalueprovider
             *  http://haacked.com/archive/2010/04/15/sending-json-to-an-asp-net-mvc-action-method-argument.aspx
             *  
             *  This ValueProvider picks up posted Json and populates Action method arguments, using the default JavaScriptSerializer
             *  ValueProviderFactories.Factories.Add(new JsonValueProviderFactory());
             *  
             *  Or we can skip adding ValueProvider, and have this code segement in all action methods :
             *  
             *  var Serializer = new JsonSerializer();
             *  var DeserializedMessage = Serializer.Deserialize<WhateverType>(new JsonTextReader(new StreamReader(Request.InputStream)));
             */

            RegisterGlobalFilters(GlobalFilters.Filters);
            RegisterRoutes(RouteTable.Routes);
        }
    }
}