using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ConsoleApp2
{
    class A
    {
        protected List<double> X1;
        public List<double> LinearEquation(double a, double b)
        {
            if (a == 0)
                return null;

            return X1 = new List<double>(){ 
                -b / a 
            };

        }
    } 
}

