using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using 專題.Models;
using System.Data;
using System.Data.SqlClient;

namespace 專題.Controllers
{
    [Route("login")]
    public class LoginController : ApiController
    {
        String cnstr = @"Data Source=.;Initial Catalog='Smart Home';Integrated Security=True";
        SqlConnection cn=new SqlConnection();
        List<User> users=new List<User>();

        [Route("login/all")]
        [HttpGet]
        public HttpResponseMessage GetAllAccount()
        {
            cn.ConnectionString = cnstr;
            SqlDataAdapter da = new SqlDataAdapter("SELECT * FROM ACCOUNT", cn);
            DataSet ds = new DataSet();
            da.Fill(ds);
            foreach(DataRow row in ds.Tables[0].Rows)
                {
                users.Add(new Models.User {username=row["AccountName"].ToString(),password=row["Password"].ToString() });
                }
            return Request.CreateResponse(HttpStatusCode.Created, users,
            GlobalConfiguration.Configuration.Formatters.JsonFormatter);
        }
        [Route("api/login/check")]
        [HttpPost]
        public HttpResponseMessage AccountCheck([FromBody]User user)
        {
            Boolean result=false;
            foreach(User i in users)
            {
                if(i.username==user.username)
                {
                    if (i.password == user.password)
                    {
                        result = true;
                        break;
                    } 
                }
            }
            return Request.CreateResponse<bool>(HttpStatusCode.Created, result,
            GlobalConfiguration.Configuration.Formatters.JsonFormatter);
        }
    }
}
