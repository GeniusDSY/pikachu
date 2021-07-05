package cn.edu.cqupt.pikachu.ad.service.impl;

import cn.edu.cqupt.pikachu.ad.constants.enums.CommonStatus;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        creativeDTO.setAuditStatus(CommonStatus.INVALID.getStatus());

        // 创建新的创意存入
        Creative creative = creativeRepository.save(ConvertUtils.creativeDTO2Creative(creativeDTO));

        return new Response<>(ConvertUtils.creative2CreativeVO(creative));
    }

    /**
     * 获取当前用户的所有创意
     *
     * @param userId 用户Id
     * @return 该用户的所有创意展示数据
     */
    @Override
    public Response<List<CreativeVO>> getAllCreatives(Long userId) {
        List<Creative> allCreatives = creativeRepository.findAllByUserId(userId);
        List<CreativeVO> allCreativeVOs = new ArrayList<>();
        allCreatives.forEach(creative -> {
            allCreativeVOs.add(ConvertUtils.creative2CreativeVO(creative));
        });
        return new Response<>(allCreativeVOs);
    }

    /**
     * 更新创意数据
     *
     * @param creativeDTO 创意数据
     * @return 更新后的创意数据
     */
    @Override
    public Response<CreativeVO> updateCreative(CreativeDTO creativeDTO) {
        try {
            if (!creativeDTO.createValidate()) {
                return new Response<>(ResultStatus.REQUEST_PARAM_ERROR);
            }

            Optional selectResult = creativeRepository.findById(creativeDTO.getId());

            if (!selectResult.isPresent()) {
                return new Response<>(ResultStatus.UNIT_NOT_EXIST);
            }
            Creative oldCreative = (Creative) selectResult.get();

            Creative creative = ConvertUtils.creativeDTO2Creative(creativeDTO);
            creative.setCreateTime(oldCreative.getCreateTime());
            creative.setUpdateTime(new Date());
            creative.setId(oldCreative.getId());
            return new Response<>(ConvertUtils.creative2CreativeVO(creativeRepository.save(creative)));
        } catch (Exception e) {
            log.error("ad-sponsor: CreativeService updateCreative -> request={}-error={}", creativeDTO, e.getMessage());
            return new Response<>(ResultStatus.UPDATE_ADPLAN_ERROR);
        }
    }

    /**
     * 获取所有广告创意信息id+name
     *
     * @param userId
     * @return
     */
    @Override
    public Response<List<String>> getAllCreativeMsg(Long userId) {
        List<Creative> creatives = creativeRepository.findAllByUserId(userId);
        List<String> allAdCreativeMsg = creatives.stream()
                .map(creative -> creative.getId() + "." + creative.getName())
                .collect(Collectors.toList());
        return new Response<>(allAdCreativeMsg);
    }

}
