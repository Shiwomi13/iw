using System;
using System.Collections.Generic;
using System.Text;

namespace Aleksandrov
{
    interface LogInterface
    {
        LogInterface Log(string str);
        LogInterface Write();
    }
}
