package cn.edu.cqupt.pikachu.ad.search.vo.media;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author :DengSiYuan
 * @date :2021/3/29 19:02
 * @desc : 应用的基本信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class App {

    /**
     * 应用编码
     */
    private String appCode;

    /**
     * 应用名称
     */
    private String appName;

    /**
     * 应用包名称
     */
    private String packageName;

    /**
     * 活动名称
     */
    private String activityName;
}
