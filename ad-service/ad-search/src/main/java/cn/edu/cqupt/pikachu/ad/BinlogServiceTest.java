package cn.edu.cqupt.pikachu.ad;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.TypeReference;
import com.github.shyiko.mysql.binlog.BinaryLogClient;
import com.github.shyiko.mysql.binlog.event.DeleteRowsEventData;
import com.github.shyiko.mysql.binlog.event.EventData;
import com.github.shyiko.mysql.binlog.event.UpdateRowsEventData;
import com.github.shyiko.mysql.binlog.event.WriteRowsEventData;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author :DengSiYuan
 * @date :2021/3/25 14:53
 * @desc :
 */
public class BinlogServiceTest {

    public static void main(String[] args) throws IOException {
        String str = "[\"123\",\"456\"]";
        List<String> a = new ArrayList<>();
        a.add("https://cbu01.alicdn.com/img/ibank/2019/577/762/11989267775_269226833.220x220.jpg");
        a.add("https://cbu01.alicdn.com/img/ibank/2019/577/762/11989267775_269226833.220x220.jpg");
        a.add("https://cbu01.alicdn.com/img/ibank/2019/577/762/11989267775_269226833.220x220.jpg");
        String s = JSON.toJSONString(a);
        System.out.println(s);



    }
}
