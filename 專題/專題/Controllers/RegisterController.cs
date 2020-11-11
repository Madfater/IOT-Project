using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using System.Data.SqlClient;
using 專題.Models;
using System.Data;

namespace 專題.Controllers
{
    [Route("reg")]
    public class RegisterController : ApiController
    {
        String cnstr = @"Data Source=DESKTOP-2L2S7UK\SQLEXPRESS;Initial Catalog='Smart Home';Integrated Security=True";
        SqlConnection cn = new SqlConnection();
        [Route("reg/do")]
        [HttpPost]
        public HttpResponseMessage Register([FromBody]User user)
        {
            var result = false;
            cn.ConnectionString = cnstr;
            cn.Open();
            SqlCommand cmd = new SqlCommand("SELECT COUNT (ACCOUNTID) FROM ACCOUNT WHERE BOOKNAME='"+user.username+"'", cn);
            if(Convert.ToInt32(cmd.ExecuteScalar())==0)
            {
                SqlCommand cmd_ID = new SqlCommand("SELECT COUNT (ACCOUNTID) FROM ACCOUNT",cn);
                int id = Convert.ToInt32(cmd_ID.ExecuteScalar())+1;
                SqlCommand cmd_Insert = new SqlCommand("INSERT INTO ACCOUNT(ACCOUNTID,ACCOUNTNAME,PASSWORD) VALUES("+id+",'"+user.username+"','"+user.password+"')", cn);
                cmd_Insert.ExecuteNonQuery();
                result = true;
            }     
            cn.Close();
            return Request.CreateResponse(HttpStatusCode.OK, result,GlobalConfiguration.Configuration.Formatters.JsonFormatter);
        }
    }
}
