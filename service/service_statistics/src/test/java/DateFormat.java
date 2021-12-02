import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author xiaobai
 * @create 2021-08-29 23:59
 */
public class DateFormat {
    @Test
    public void test(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        System.out.println(sdf.format(date));
    }
}
