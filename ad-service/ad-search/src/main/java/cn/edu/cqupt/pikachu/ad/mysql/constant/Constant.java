package cn.edu.cqupt.pikachu.ad.mysql.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * @author :DengSiYuan
 * @date :2021/3/28 12:04
 * @desc : 数据表的结构常量
 */
public class Constant {

    /**
     * 数据库名称
     */
    public static final String DB_NAME = "pikachu";

    /**
     * 广告计划表信息
     */
    public static class AD_PLAN_TABLE_INFO {

        /**
         * 数据表名称
         */
        public static final String TABLE_NAME = "ad_plan";

        /**
         * 数据列——Id
         */
        public static final String COLUMN_ID = "id";

        /**
         * 数据列——用户Id
         */
        public static final String COLUMN_USER_ID = "user_id";

        /**
         * 开始时间
         */
        public static final String COLUMN_START_DATE = "start_date";

        /**
         * 结束时间
         */
        public static final String COLUMN_END_DATE = "end_date";
    }

    /**
     * 广告创意表信息
     */
    public static class AD_CREATIVE_TABLE_INFO {

        /**
         * 数据表名称——广告创意
         */
        public static final String TABLE_NAME = "ad_creative";

        /**
         * id列
         */
        public static final String COLUMN_ID = "id";

        /**
         * type列
         */
        public static final String COLUMN_TYPE = "type";

        /**
         * material_type列
         */
        public static final String COLUMN_MATERIAL_TYPE = "material_type";

        /**
         * height列
         */
        public static final String COLUMN_HEIGHT = "height";

        /**
         * width列
         */
        public static final String COLUMN_WIDTH = "width";

        /**
         * aduit_status列
         */
        public static final String COLUMN_AUDIT_STATUS = "audit_status";

        /**
         * url列
         */
        public static final String COLUMN_URL = "url";
    }

    /**
     * 广告单元表信息
     */
    public static class AD_UNIT_TABLE_INFO {

        /**
         * 数据表名称
         */
        public static final String TABLE_NAME = "ad_unit";

        /**
         * id列
         */
        public static final String COLUMN_ID = "id";

        /**
         * unit_status列
         */
        public static final String COLUMN_UNIT_STATUS = "unit_status";

        /**
         * position_type列
         */
        public static final String COLUMN_POSITION_TYPE = "position_type";

        /**
         * plan_id列
         */
        public static final String COLUMN_PLAN_ID = "plan_id";
    }

    /**
     * 广告创意单元关联表信息
     */
    public static class AD_CREATIVE_UNIT_TABLE_INFO {

        /**
         * 数据表名称
         */
        public static final String TABLE_NAME = "creative_unit";

        /**
         * creative_id列
         */
        public static final String COLUMN_CREATIVE_ID = "creative_id";

        /**
         * unit_id列
         */
        public static final String COLUMN_UNIT_ID = "unit_id";
    }

    /**
     * 广告单元区域影响因素表信息
     */
    public static class AD_UNIT_DISTRICT_TABLE_INFO {

        /**
         * 数据表名称
         */
        public static final String TABLE_NAME = "ad_unit_district";

        /**
         * unit_id列
         */
        public static final String COLUMN_UNIT_ID = "unit_id";

        /**
         * province列
         */
        public static final String COLUMN_PROVINCE = "province";

        /**
         * city列
         */
        public static final String COLUMN_CITY = "city";
    }

    /**
     * 广告单元兴趣爱好影响因素表信息
     */
    public static class AD_UNIT_IT_TABLE_INFO {

        /**
         * 数据表名称
         */
        public static final String TABLE_NAME = "ad_unit_it";

        /**
         * unit_id列
         */
        public static final String COLUMN_UNIT_ID = "unit_id";

        /**
         * it_tag列
         */
        public static final String COLUMN_IT_TAG = "it_tag";
    }

    /**
     * 广告单元关键字影响因素表信息
     */
    public static class AD_UNIT_KEYWORD_TABLE_INFO {

        /**
         * 数据表名称
         */
        public static final String TABLE_NAME = "ad_unit_keyword";

        /**
         * unit_id列
         */
        public static final String COLUMN_UNIT_ID = "unit_id";

        /**
         * keyword列
         */
        public static final String COLUMN_KEYWORD = "keyword";
    }

    /**
     * table和db的映射关系
     */
    public static Map<String, String> table2Db;

    static {

        table2Db = new HashMap<>();
        table2Db.put(AD_PLAN_TABLE_INFO.TABLE_NAME, DB_NAME);
        table2Db.put(AD_CREATIVE_TABLE_INFO.TABLE_NAME, DB_NAME);
        table2Db.put(AD_CREATIVE_UNIT_TABLE_INFO.TABLE_NAME, DB_NAME);
        table2Db.put(AD_UNIT_TABLE_INFO.TABLE_NAME, DB_NAME);
        table2Db.put(AD_UNIT_DISTRICT_TABLE_INFO.TABLE_NAME, DB_NAME);
        table2Db.put(AD_UNIT_IT_TABLE_INFO.TABLE_NAME, DB_NAME);
        table2Db.put(AD_UNIT_KEYWORD_TABLE_INFO.TABLE_NAME, DB_NAME);
    }
}
