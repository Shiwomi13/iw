using System;
using System.Collections.Generic;
using System.Text;

namespace Aleksandrov
{
    class Kvad : Lin, EquationInterface
    {
        protected float Discriminant(float a, float b, float c)
        {
            return b * b - (4 * a * c);
        }
        public List<float> Solve(float a, float b, float c)
        {
            if (a == 0)
                return Check(b, c);
            AleksandrovLog.I().Log("Определено, что это квадратное уравнение");
            float disc = Discriminant(a, b, c);
            if (disc < 0)
                throw new AleksandrovException("Ошибка: уравнение не имеет решений");
            if (disc == 0)
                return x = new List<float>() { -b / (2 * a) };
            disc = (float)Math.Sqrt(disc);
            return x = new List<float> {
                (-b + disc) / (2 * a),
                (-b - disc) / (2 * a)
            };
        }
    }
}
