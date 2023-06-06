package Design.dbutils.uitils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    /*
    返回当前日期以“yyyy-mm-dd kk：mm：ss”格式输出字符串
     */
    public static String getDateTimeNow(){
        Date time = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
        return sdf.format(time);
    }
}
