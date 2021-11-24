using System;
using System.Collections.Generic;
using System.Text;

namespace Aleksandrov
{
    class AleksandrovLog:LogAbstract, LogInterface
    {
        private static AleksandrovLog instance;
        List<string> ListArr = new List<string>();
        public static AleksandrovLog I()
        {
            if (instance == null)
                instance = new AleksandrovLog();
            return instance;
        }
        public LogInterface Log(string str)
        {
            ListArr.Add(str);
            return this;
        }
        public LogInterface Write()
        {
            writeConsole(ListArr.ToArray());
            return this;
        }
    }
}
