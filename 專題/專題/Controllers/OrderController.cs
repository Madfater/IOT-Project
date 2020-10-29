using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using 專題.Models;
using System.Data.SqlClient;
using System.Data;

namespace 專題.Controllers
{
    public class OrderController : ApiController
    {
        String cnstr = @"Data Source=DESKTOP-2L2S7UK\SQLEXPRESS;Initial Catalog='Smart Home';Integrated Security=True";
        SqlConnection cn = new SqlConnection();
        List<Order> orders = new List<Order>();

        [Route("order/all")]
        [HttpGet]
        public HttpResponseMessage GetAllAccount()//測試用
        {
            cn.ConnectionString = cnstr;
            SqlDataAdapter da = new SqlDataAdapter("SELECT * FROM command", cn);
            DataSet ds = new DataSet();
            da.Fill(ds);
            foreach (DataRow row in ds.Tables[0].Rows)
            {
                orders.Add(new Models.Order { Ordername = row["Ordername"].ToString(), OrderArgument =Convert.ToInt16(row["OrderArgument"]),OrderDescription=row["OrderDescription"].ToString(),Status=row["Status"].ToString() });
            }
            return Request.CreateResponse(HttpStatusCode.Created, orders,
            GlobalConfiguration.Configuration.Formatters.JsonFormatter);
        }

        [Route("order/add")]//新增指令
        [HttpPost]
        public HttpResponseMessage NewOrder([FromBody]Order order)
        {
            cn.ConnectionString = cnstr;
            cn.Open();
            Int32 id = (Int32)(DateTime.Now.Subtract(new DateTime(1970, 1, 1))).TotalSeconds;
            SqlCommand cmd = new SqlCommand("INSERT INTO command(ORDERID,ORDERNAME,ORDERARGUMENT,ORDERDESCRIPTION,STATUS) VALUES(" + id + ",'" + order.Ordername + "','" + order.OrderArgument + "','" + order.OrderDescription + "','" + "new" + "')", cn);
            cmd.ExecuteNonQuery();
            cn.Close();
            return Request.CreateResponse(HttpStatusCode.OK,id, GlobalConfiguration.Configuration.Formatters.JsonFormatter);
        }

        [Route("order/update")]//硬體回覆
        [HttpPost]
        public HttpResponseMessage UpdateOrder([FromBody]response response)
        {
            cn.ConnectionString = cnstr;
            cn.Open();
            SqlCommand cmd = new SqlCommand("UPDATE command SET STATUS = 'success' WHERE ORDERID = " + Convert.ToInt32(response.res), cn);
            cmd.ExecuteNonQuery();
            cn.Close();
            return Request.CreateResponse(HttpStatusCode.OK);
        }

        [Route("order/get")]
        [HttpPost]
        public HttpResponseMessage GetOrder([FromBody]response response)//硬體搜尋指令
        {
            cn.ConnectionString = cnstr;
            SqlDataAdapter da = new SqlDataAdapter("SELECT MAX(OrderID) OrderID FROM command WHERE STATUS NOT IN('success') AND ORDERID = " + response.res, cn);
            DataSet ds = new DataSet();
            da.Fill(ds);
            int id =Convert.ToInt32(ds.Tables[0].Rows[0]["OrderID"]);
            SqlDataAdapter da_final = new SqlDataAdapter("SELECT * FROM command WHERE Orderid = " + id, cn);
            DataSet ds_final = new DataSet();
            da_final.Fill(ds_final);
            foreach (DataRow row in ds_final.Tables[0].Rows)
            {
                orders.Add(new Models.Order { Ordername = row["Ordername"].ToString(), OrderArgument = Convert.ToInt16(row["OrderArgument"]), OrderDescription = row["OrderDescription"].ToString(), Status = row["Status"].ToString() });
            }
            return Request.CreateResponse(HttpStatusCode.Created, orders,
            GlobalConfiguration.Configuration.Formatters.JsonFormatter);
        }
        [Route("order/check")]
        [HttpPost]
        public HttpResponseMessage CheckOrder([FromBody]response response)//手機確認
        {
            cn.ConnectionString = cnstr;
            SqlDataAdapter da = new SqlDataAdapter("SELECT * FROM command WHERE ORDERID = "+response.res, cn);
            DataSet ds = new DataSet();
            da.Fill(ds);
            return Request.CreateResponse(HttpStatusCode.OK,ds.Tables[0].Rows[0]["STATUS"]);
        }
    }
}
