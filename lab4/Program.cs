using Aleksandrov;
using System;

namespace yo
{
    class Program
    {
        static void Main(string[] args)
        {
            A a1 = new A();
            A a2 = new A();

            B b1 = new B();
            B b2 = new B();

            C c1 = new C();

            b1.a = b2;
            b2.a = a2;

            c1.a = a1;
            c1.b = b1;

            try
            {
                Console.WriteLine("Введите параметры: ");
                float a = Int32.Parse(Console.ReadLine());
                float b = Int32.Parse(Console.ReadLine());
                float c = Int32.Parse(Console.ReadLine());
                Console.WriteLine($"Введено уравнение: {a}x^2 + {b}x + {c} = 0");
                Kvad Yow = new Kvad();
                AleksandrovLog.I().Log("Корни уравнения " + String.Join("; ", Yow.Solve(a, b, c)));
            }
            catch (AleksandrovException ex)
            {
                AleksandrovLog.I().Log(ex.Message);
            }
            AleksandrovLog.I().Write();
        }
    }

    public class A
    {

    }

    public class B : A
    {
        public Object a;
    }

    public class C : B
    {
        public Object b;
    }
}
