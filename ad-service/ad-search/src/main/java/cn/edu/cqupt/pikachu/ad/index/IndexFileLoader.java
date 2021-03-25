package cn.edu.cqupt.pikachu.ad.index;

import cn.edu.cqupt.pikachu.ad.dump.DConstant;
import cn.edu.cqupt.pikachu.ad.dump.table.*;
import cn.edu.cqupt.pikachu.ad.handler.AdLevelDataHandler;
import cn.edu.cqupt.pikachu.ad.mysql.constant.OpType;
import com.alibaba.fastjson.JSON;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author :DengSiYuan
 * @date :2021/3/23 14:19
 * @desc : 索引文件加载
 */
@Component
@DependsOn("dataTable")
public class IndexFileLoader {

    /**
     * 初始化加载数据索引
     */
    @PostConstruct
    public void init() {

        // 广告计划序列化数据加载
        List<String> adPlanStrings = loadDumpData(
                String.format("%s%s", DConstant.DATA_ROOT_STR, DConstant.AD_PLAN)
        );
        adPlanStrings.forEach(p -> AdLevelDataHandler.handleLevel2(
                JSON.parseObject(p, AdPlanTable.class),
                OpType.ADD
        ));

        // 广告创意序列化数据加载
        List<String> adCreativeStrings = loadDumpData(
                String.format("%s%s", DConstant.DATA_ROOT_STR, DConstant.AD_CREATIVE)
        );
        adCreativeStrings.forEach(c -> AdLevelDataHandler.handleLevel2(
                JSON.parseObject(c, AdCreativeTable.class),
                OpType.ADD
        ));

        // 广告单元序列化数据加载
        List<String> adUnitStrings = loadDumpData(
                String.format("%s%s", DConstant.DATA_ROOT_STR, DConstant.AD_UNIT)
        );
        adUnitStrings.forEach(u -> AdLevelDataHandler.handleLevel3(
                JSON.parseObject(u, AdUnitTable.class),
                OpType.ADD
        ));

        // 广告创意单元序列化数据加载
        List<String> adCreativeUnitStrings = loadDumpData(
                String.format("%s%s", DConstant.DATA_ROOT_STR, DConstant.AD_CREATIVE_UNIT)
        );
        adCreativeUnitStrings.forEach(cu -> AdLevelDataHandler.handleLevel3(
                JSON.parseObject(cu, AdCreativeUnitTable.class),
                OpType.ADD
        ));

        // 广告单元区域影响因素序列化数据加载
        List<String> adUnitDistrictStrings = loadDumpData(
                String.format("%s%s", DConstant.DATA_ROOT_STR, DConstant.AD_UNIT_DISTRICT)
        );
        adUnitDistrictStrings.forEach(ud -> AdLevelDataHandler.handlelevel4(
                JSON.parseObject(ud, AdUnitDistrictTable.class),
                OpType.ADD
        ));

        // 广告单元兴趣爱好影响因素序列化数据加载
        List<String> adUnitItStrings = loadDumpData(
                String.format("%s%s", DConstant.DATA_ROOT_STR, DConstant.AD_UNIT_IT)
        );
        adUnitItStrings.forEach(ui -> AdLevelDataHandler.handleLevel4(
                JSON.parseObject(ui, AdUnitItTable.class),
                OpType.ADD
        ));

        // 广告单元关键字影响因素序列化数据加载
        List<String> adUnitKeywordStrings = loadDumpData(
                String.format("%s%s", DConstant.DATA_ROOT_STR, DConstant.AD_UNIT_KEYWORD)
        );
        adUnitKeywordStrings.forEach(uk -> AdLevelDataHandler.handleLevel4(
                JSON.parseObject(uk, AdKeywordTable.class),
                OpType.ADD
        ));
    }

    /**
     * 加载dump的数据
     *
     * @param fileName 文件名称
     * @return 数据
     */
    private List<String> loadDumpData(String fileName) {
        try (BufferedReader reader = Files.newBufferedReader(
                Paths.get(fileName)
        )) {
            return reader.lines().collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
