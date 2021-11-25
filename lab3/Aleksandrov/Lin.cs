using System;
using System.Collections.Generic;
using System.Text;

namespace Aleksandrov
{
    class Lin
    {
        public List<float> x;
        public List<float> Check(float b, float c)
        {
            if (b == 0)
                throw new AleksandrovException("Определено, что такое уравнение не существует"); 
            AleksandrovLog.I().Log("Определено, что это линейное уравнение");
            return x = new List<float>() { -c / b };
        }
    }
}