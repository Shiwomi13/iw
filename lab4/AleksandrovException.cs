using System;
using System.Collections.Generic;
using System.Text;

namespace Aleksandrov
{
    class AleksandrovException : Exception
    {
        public AleksandrovException(string message) : base(message) { }
    }
}