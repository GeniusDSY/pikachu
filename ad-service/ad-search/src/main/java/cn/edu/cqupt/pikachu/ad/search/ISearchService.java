package cn.edu.cqupt.pikachu.ad.search;

import cn.edu.cqupt.pikachu.ad.model.vo.response.Response;
import cn.edu.cqupt.pikachu.ad.search.vo.SearchRequest;
import cn.edu.cqupt.pikachu.ad.search.vo.SearchResponse;

/**
 * @author :DengSiYuan
 * @date :2021/3/29 17:57
 * @desc : 检索服务
 */
public interface ISearch {

    /**
     * 获取广告
     *
     * @param request 检索请求
     * @return 检索响应
     */
    Response<SearchResponse> fetchAds(SearchRequest request);
}
