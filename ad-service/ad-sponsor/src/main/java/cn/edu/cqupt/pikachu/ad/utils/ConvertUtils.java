package cn.edu.cqupt.pikachu.ad.utils;

import cn.edu.cqupt.pikachu.ad.exception.AdException;
import cn.edu.cqupt.pikachu.ad.model.dto.AdPlanDTO;
import cn.edu.cqupt.pikachu.ad.model.dto.UserDTO;
import cn.edu.cqupt.pikachu.ad.model.entity.AdPlan;
import cn.edu.cqupt.pikachu.ad.model.entity.AdUser;
import cn.edu.cqupt.pikachu.ad.model.vo.AdPlanVO;
import cn.edu.cqupt.pikachu.ad.model.vo.UserVO;
import cn.edu.cqupt.pikachu.ad.model.vo.response.Response;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author :DengSiYuan
 * @date :2021/2/5 22:39
 * @desc : 转换工具类
 */
public class ConvertUtils {

    /**
     * UserDTO转换成Entity AdUser
     *
     * @param userDTO 用户传入信息
     * @return AdUser实体数据信息
     * @throws AdException Ad自定义异常
     */
    public static AdUser userDTO2AdUser(UserDTO userDTO) throws AdException {
        AdUser user = new AdUser();
        user.setUsername(userDTO.getUsername());
        user.setToken(CommonUtils.md5Encrypt(userDTO.getUsername()));
        return user;
    }

    /**
     * Entity AdUser转换成UserVO
     *
     * @param adUser 用户数据库数据
     * @return 用户响应展示数据
     */
    public static UserVO adUser2UserVO(AdUser adUser) {
        UserVO userVO = new UserVO();
        userVO.setUserId(adUser.getId());
        userVO.setUsername(adUser.getUsername());
        userVO.setToken(adUser.getToken());
        userVO.setCreateTime(adUser.getCreateTime());
        userVO.setUpdateTime(adUser.getUpdateTime());
        return userVO;
    }

    /**
     * AdPlanDTO 转换成Entity AdPlan
     *
     * @param adPlanDTO 请求入参AdPlanDTO
     * @return Entity AdPlan
     * @throws AdException 广告系统异常
     */
    public static AdPlan adPlanDTO2AdPlan(AdPlanDTO adPlanDTO) throws AdException {
        return new AdPlan(adPlanDTO.getUserId(),
                adPlanDTO.getPlanName(),
                CommonUtils.parseString2Date(adPlanDTO.getStartDate()),
                CommonUtils.parseString2Date(adPlanDTO.getEndDate()));
    }

    /**
     * Entity AdPlan 转换成 AdPlanVO
     *
     * @param adPlan Entity AdPlan
     * @return AdPlan展示数据
     */
    public static AdPlanVO adPlan2AdPlanVO(AdPlan adPlan) {
        return new AdPlanVO(adPlan.getId(), adPlan.getPlanName());
    }
    /**
     * Entity AdPlan 转换成 AdPlanVO
     *
     * @param adPlan Entity AdPlan
     * @return AdPlan展示数据
     */
    public static List<AdPlanVO> adPlan2AdPlanVO(List<AdPlan> adPlans) {
        
        return adPlans.stream(adPlan -> {
            new AdPlanVO(adPlan.getId(), adPlan.getPlanName());
        }).collect(Collectors.toList());
    }


}
