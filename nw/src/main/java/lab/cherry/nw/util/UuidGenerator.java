package lab.cherry.nw.util;

import java.util.UUID;

public class UuidGenerator {
    public static UUID next() {
        /*
           1. TsidCreator.getTsid()
           2. (Long) TsidCreator.getTsid().toLong()
           3. (String) TsidCreator.getTsid().toString()
        
         */
        return UUID.randomUUID();
    }
    
}