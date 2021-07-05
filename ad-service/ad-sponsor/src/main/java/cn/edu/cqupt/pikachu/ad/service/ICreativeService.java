package cn.edu.cqupt.pikachu.ad.service;

import cn.edu.cqupt.pikachu.ad.model.dto.CreativeDTO;
import cn.edu.cqupt.pikachu.ad.model.vo.CreativeVO;
import cn.edu.cqupt.pikachu.ad.model.vo.response.Response;

import java.util.List;

/**
 * @author :DengSiYuan
 * @date :2021/2/14 15:49
 * @desc : 创意服务接口
 */
public interface ICreativeService {

    /**
     * 创建创意
     *
     * @param creativeDTO 创意传入数据
     * @return 创意展示数据
     */
    Response<CreativeVO> createCreative(CreativeDTO creativeDTO);


    /**
     * 获取当前用户的所有创意
     *
     * @param userId 用户Id
     * @return 该用户的所有创意展示数据
     */
    Response<List<CreativeVO>> getAllCreatives(Long userId);

    /**
     * 更新创意数据
     *
     * @param creativeDTO 创意数据
     * @return 更新后的创意数据
     */
    Response<CreativeVO> updateCreative(CreativeDTO creativeDTO);

    /**
     * 获取所有广告创意信息id+name
     *
     * @param userId
     * @return
     */
    Response<List<String>> getAllCreativeMsg(Long userId);
}
