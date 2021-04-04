package cn.edu.cqupt.pikachu.ad.service;

import cn.edu.cqupt.pikachu.ad.exception.AdException;
import cn.edu.cqupt.pikachu.ad.model.dto.CreativeDTO;
import cn.edu.cqupt.pikachu.ad.model.vo.CreativeVO;
import cn.edu.cqupt.pikachu.ad.model.vo.response.Response;

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



}
