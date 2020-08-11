package mo.ed.movies.inc.util;

import java.util.Date;
import java.util.TimeZone;

public class Utils {
    public static int getTimezoneOffset(){
        TimeZone tz = TimeZone.getDefault();
        Date now = new Date();
        return tz.getOffset(now.getTime()) / 1000;
    }
}
