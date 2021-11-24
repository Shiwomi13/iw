using System;
using System.Collections;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ConsoleApp2
{
    class B : A
    {
        public List<double> QuadraticEquation(double a, double b, double c)
        {
            double D = SearchDiscriminate(a, b, c);

            if (a == 0)
            {
                return LinearEquation(b, c);
            }

            if (D < 0)
            {
                return null;
            }

            if (D == 0)
            {
                return X1 = new List<double>(){
                    (-b) / (2*a)
                };
            }

            return X1 = new List<double>(){
                (-b + Math.Sqrt(D)) / (2 * a),
                (-b - Math.Sqrt(D)) / (2 * a)
            };
        }
        protected double SearchDiscriminate(double a, double b, double c)
        {
            return b * b - (4 * a * c);
        }
    }
}
