package cn.edu.cqupt.pikachu.ad.service.impl;

import cn.edu.cqupt.pikachu.ad.constants.enums.ResultStatus;
import cn.edu.cqupt.pikachu.ad.dao.AdUserRepository;
import cn.edu.cqupt.pikachu.ad.dao.CreativeRepository;
import cn.edu.cqupt.pikachu.ad.exception.AdException;
import cn.edu.cqupt.pikachu.ad.model.dto.CreativeDTO;
import cn.edu.cqupt.pikachu.ad.model.entity.AdUser;
import cn.edu.cqupt.pikachu.ad.model.entity.Creative;
import cn.edu.cqupt.pikachu.ad.model.vo.CreativeVO;
import cn.edu.cqupt.pikachu.ad.model.vo.response.Response;
import cn.edu.cqupt.pikachu.ad.service.ICreativeService;
import cn.edu.cqupt.pikachu.ad.utils.ConvertUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * @author :DengSiYuan
 * @date :2021/2/17 9:56
 * @desc : 创意服务实现
 */
@Slf4j
@Service
public class CreativeServiceImpl implements ICreativeService {

    @Resource
    private CreativeRepository creativeRepository;

    @Resource
    private AdUserRepository userRepository;

    /**
     * 创建创意
     *
     * @param creativeDTO 创意传入数据
     * @return 创意展示数据
     */
    @Override
    public Response<CreativeVO> createCreative(CreativeDTO creativeDTO) {

        if (!creativeDTO.createValidate()) {
            return new Response<>(ResultStatus.REQUEST_PARAM_ERROR);
        }

        // 判断用户是否存在
        Optional<AdUser> user = userRepository.findById(creativeDTO.getUserId());
        if (!user.isPresent()) {
            return new Response<>(ResultStatus.USER_NOT_EXISTED);
        }

        // 创建新的创意存入
        Creative creative = creativeRepository.save(ConvertUtils.creativeDTO2Creative(creativeDTO));

        return new Response<>(new CreativeVO(creative.getId(), creative.getName()));
    }

}
