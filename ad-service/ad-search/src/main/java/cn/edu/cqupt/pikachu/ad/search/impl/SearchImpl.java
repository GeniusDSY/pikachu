package cn.edu.cqupt.pikachu.ad.search.impl;

import cn.edu.cqupt.pikachu.ad.constants.enums.CommonStatus;
import cn.edu.cqupt.pikachu.ad.index.DataTable;
import cn.edu.cqupt.pikachu.ad.index.adunit.AdUnitIndex;
import cn.edu.cqupt.pikachu.ad.index.adunit.AdUnitObject;
import cn.edu.cqupt.pikachu.ad.index.creative.CreativeIndex;
import cn.edu.cqupt.pikachu.ad.index.creative.CreativeObject;
import cn.edu.cqupt.pikachu.ad.index.creativeunit.CreativeUnitIndex;
import cn.edu.cqupt.pikachu.ad.index.district.UnitDistrictIndex;
import cn.edu.cqupt.pikachu.ad.index.interest.UnitItIndex;
import cn.edu.cqupt.pikachu.ad.index.keyword.UnitKeywordIndex;
import cn.edu.cqupt.pikachu.ad.search.ISearch;
import cn.edu.cqupt.pikachu.ad.search.vo.SearchRequest;
import cn.edu.cqupt.pikachu.ad.search.vo.SearchResponse;
import cn.edu.cqupt.pikachu.ad.search.vo.feature.DistrictFeature;
import cn.edu.cqupt.pikachu.ad.search.vo.feature.FeatureRelation;
import cn.edu.cqupt.pikachu.ad.search.vo.feature.ItFeature;
import cn.edu.cqupt.pikachu.ad.search.vo.feature.KeywordFeature;
import cn.edu.cqupt.pikachu.ad.search.vo.media.AdSlot;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author :DengSiYuan
 * @date :2021/3/31 17:00
 * @desc : 检索服务实现类
 */
@Slf4j
@Service
public class SearchImpl implements ISearch {

    /**
     * 获取广告
     *
     * @param request 检索请求
     * @return 检索响应
     */
    @Override
    public SearchResponse fetchAds(SearchRequest request) {

        // 请求的广告位信息
        List<AdSlot> adSlots = request.getRequestInfo().getAdSlots();

        // 三个过滤信息Feature
        KeywordFeature keywordFeature = request.getFeatureInfo().getKeywordFeature();
        DistrictFeature districtFeature = request.getFeatureInfo().getDistrictFeature();
        ItFeature itFeature = request.getFeatureInfo().getItFeature();

        // 过滤信息之间的关系
        FeatureRelation relation = request.getFeatureInfo().getRelation();

        // 构造响应对象
        SearchResponse response = new SearchResponse();
        Map<String, List<SearchResponse.Creative>> adSlot2Ads = response.getAdSlot2Ads();

        // 遍历广告位进行过滤
        for (AdSlot adSlot : adSlots) {

            Set<Long> targetUnitIdSets;

            // 根据流量类型获取初始的AdUnit
            Set<Long> adUnitIdSet = DataTable.of(AdUnitIndex.class).match(adSlot.getPositionType());

            // 若过滤关系是“且”
            if (FeatureRelation.AND == relation) {

                filterKeywordFeature(adUnitIdSet, keywordFeature);
                filterDistrictFeature(adUnitIdSet, districtFeature);
                filterItFeature(adUnitIdSet, itFeature);
                targetUnitIdSets = adUnitIdSet;
            } else {
                targetUnitIdSets = getORRelationUnitIds(adUnitIdSet, keywordFeature, districtFeature, itFeature);
            }

            List<AdUnitObject> unitObjects = DataTable.of(AdUnitIndex.class).fetch(targetUnitIdSets);

            // 过滤筛选有效的广告单元数据对象
            filterAdUnitAndPlanStatus(unitObjects, CommonStatus.VALID);

            List<Long> adIds = DataTable.of(CreativeUnitIndex.class).selectAds(unitObjects);
            List<CreativeObject> creatives = DataTable.of(CreativeIndex.class).fetch(adIds);

            // 通过AdSlot实现对CreativeObject 的过滤
            filterCreativeByAdSlot(creatives, adSlot.getWidth(), adSlot.getHeight(), adSlot.getType());

            adSlot2Ads.put(adSlot.getAdSlotCode(), buildCreativeResponse(creatives));
        }

        log.info("ad-search:Search fetchAds -> {}-{}", JSON.toJSONString(request), JSON.toJSONString(response));
        return response;
    }

    /**
     * 过滤关系为Or关系的处理方法
     *
     * @param adUnitIdSet     广告单元Id集合
     * @param keywordFeature  关键字信息
     * @param districtFeature 区域信息
     * @param itFeature       兴趣爱好信息
     * @return 过滤完成后符合条件的广告单元Id
     */
    private Set<Long> getORRelationUnitIds(Set<Long> adUnitIdSet, KeywordFeature keywordFeature,
                                           DistrictFeature districtFeature, ItFeature itFeature) {

        if (CollectionUtils.isEmpty(adUnitIdSet)) {
            return Collections.emptySet();
        }

        Set<Long> keywordUnitItSet = new HashSet<>(adUnitIdSet);
        Set<Long> districtUnitIdSet = new HashSet<>(adUnitIdSet);
        Set<Long> itUnitItSet = new HashSet<>(adUnitIdSet);

        filterKeywordFeature(keywordUnitItSet, keywordFeature);
        filterDistrictFeature(districtUnitIdSet, districtFeature);
        filterItFeature(itUnitItSet, itFeature);

        return new HashSet<>(CollectionUtils.union(
                CollectionUtils.union(keywordUnitItSet, districtUnitIdSet), itUnitItSet));
    }

    /**
     * 过滤广告单元和投放计划状态
     *
     * @param unitObjects 广告单元数据对象
     * @param status      投放计划状态
     */
    private void filterAdUnitAndPlanStatus(List<AdUnitObject> unitObjects, CommonStatus status) {

        // 不存在广告单元，则无需判断状态
        if (CollectionUtils.isEmpty(unitObjects)) {
            return;
        }

        CollectionUtils.filter(unitObjects, object ->
                object.getUnitStatus().equals(status.getStatus())
                        && object.getAdPlanObject().getPlanStatus().equals(status.getStatus()));
    }

    /**
     * 过滤创意信息通过广告位限制
     *
     * @param creatives 创意信息
     * @param width     广告位的宽度
     * @param height    广告位的高度
     * @param type      广告位支持的类型
     */
    private void filterCreativeByAdSlot(List<CreativeObject> creatives, Integer width, Integer height, List<Integer> type) {

        if (CollectionUtils.isEmpty(creatives)) {
            return;
        }

        CollectionUtils.filter(creatives, creative ->
                CommonStatus.VALID.getStatus().equals(creative.getAuditStatus())
                        && width.equals(creative.getWidth())
                        && height.equals(creative.getHeight())
                        && type.contains(creative.getType()));
    }

    /**
     * 关键字过滤
     *
     * @param adUnitIds      需要过滤的广告单元
     * @param keywordFeature 关键字信息
     */
    private void filterKeywordFeature(Collection<Long> adUnitIds, KeywordFeature keywordFeature) {

        // 若广告单元不存在，则无需进行过滤
        if (CollectionUtils.isEmpty(adUnitIds)) {
            return;
        }

        // 若关键字过滤信息都为空，则不需要过滤
        if (CollectionUtils.isEmpty(keywordFeature.getKeywords())) {
            return;
        }

        CollectionUtils.filter(adUnitIds, adUnitId -> DataTable.of(UnitKeywordIndex.class)
                .match(adUnitId, keywordFeature.getKeywords()));
    }

    /**
     * 地域过滤
     *
     * @param adUnitIds       需要过滤的广告单元Id
     * @param districtFeature 地域信息
     */
    private void filterDistrictFeature(Collection<Long> adUnitIds, DistrictFeature districtFeature) {

        // 若广告单元不存在，则无需进行过滤
        if (CollectionUtils.isEmpty(adUnitIds)) {
            return;
        }

        // 若地域过滤信息都为空，则不需要过滤
        if (CollectionUtils.isEmpty(districtFeature.getDistricts())) {
            return;
        }

        CollectionUtils.filter(adUnitIds, adUnitId -> DataTable.of(UnitDistrictIndex.class)
                .match(adUnitId, districtFeature.getDistricts()));
    }

    /**
     * 兴趣爱好过滤
     *
     * @param adUnitIds 需要过滤的广告单元Id
     * @param itFeature 兴趣爱好信息
     */
    private void filterItFeature(Collection<Long> adUnitIds, ItFeature itFeature) {

        // 若广告单元不存在，则无需进行过滤
        if (CollectionUtils.isEmpty(adUnitIds)) {
            return;
        }

        // 若兴趣爱好过滤信息都为空，则不需要过滤
        if (CollectionUtils.isEmpty(itFeature.getIts())) {
            return;
        }

        CollectionUtils.filter(adUnitIds, adUnitId -> DataTable.of(UnitItIndex.class)
                .match(adUnitId, itFeature.getIts()));
    }

    /**
     * 构建创意响应
     *
     * @param creatives 创意信息
     * @return 检索响应创意列表
     */
    private List<SearchResponse.Creative> buildCreativeResponse(List<CreativeObject> creatives) {

        // 创意信息为空则之间返回空的响应列表
        if (CollectionUtils.isEmpty(creatives)) {
            return Collections.emptyList();
        }

        // TODO 随机获取的创意对象
        CreativeObject randomObject = creatives.get(Math.abs(new Random().nextInt() % creatives.size()));

        return Collections.singletonList(SearchResponse.convert(randomObject));
    }
}
