package lab.cherry.nw.util;

import com.github.f4b6a3.tsid.TsidCreator;

public class TsidGenerator {
    public static Long next() {
        /*
           1. TsidCreator.getTsid()
           2. (Long) TsidCreator.getTsid().toLong()
           3. (String) TsidCreator.getTsid().toString()
        
         */
        return TsidCreator.getTsid().toLong();
    }
    
}