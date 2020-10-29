using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace 專題.Models
{
    public class Order
    {
        public string Ordername { set; get; }
        public int OrderArgument { set; get; }
        public string OrderDescription { set; get; }
        public string Status { set; get; }
    }
}