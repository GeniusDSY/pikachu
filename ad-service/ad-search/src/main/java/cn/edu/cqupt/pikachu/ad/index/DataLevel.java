package cn.edu.cqupt.pikachu.ad.index;

import lombok.Getter;

/**
 * @author :DengSiYuan
 * @date :2021/3/12 13:49
 * @desc : 数据等级
 */
@Getter
public enum DataLevel {

    LEVEL2("2", "level 2"),
    LEVEL3("3", "level 3"),
    LEVEL4("4", "level 4");

    private String level;
    private String desc;

    DataLevel(String level, String desc) {
        this.level = level;
        this.desc = desc;
    }
}
