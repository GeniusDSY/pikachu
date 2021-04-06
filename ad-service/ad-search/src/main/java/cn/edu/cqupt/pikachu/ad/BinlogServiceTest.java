package cn.edu.cqupt.pikachu.ad;

import com.github.shyiko.mysql.binlog.BinaryLogClient;
import com.github.shyiko.mysql.binlog.event.DeleteRowsEventData;
import com.github.shyiko.mysql.binlog.event.EventData;
import com.github.shyiko.mysql.binlog.event.UpdateRowsEventData;
import com.github.shyiko.mysql.binlog.event.WriteRowsEventData;

import java.io.IOException;

/**
 * @author :DengSiYuan
 * @date :2021/3/25 14:53
 * @desc :
 */
public class BinlogServiceTest {

    public static void main(String[] args) throws IOException {

        BinaryLogClient client = new BinaryLogClient(
                "127.0.0.1", 3306, "root", "root"
        );
        client.registerEventListener(event -> {
            EventData data = event.getData();

            if (data instanceof UpdateRowsEventData) {
                System.out.println("update-----" + data.toString());
            } else if (data instanceof WriteRowsEventData) {
                System.out.println("write-----" + data.toString());
            } else if (data instanceof DeleteRowsEventData) {
                System.out.println("delete-----" + data.toString());
            }
        });

        client.connect();
    }
}
