package cn.edu.cqupt.pikachu.ad.service.impl;

import cn.edu.cqupt.pikachu.ad.constants.enums.ResultStatus;
import cn.edu.cqupt.pikachu.ad.dao.AdUserRepository;
import cn.edu.cqupt.pikachu.ad.exception.AdException;
import cn.edu.cqupt.pikachu.ad.model.dto.UserDTO;
import cn.edu.cqupt.pikachu.ad.model.entity.AdUser;
import cn.edu.cqupt.pikachu.ad.model.vo.UserVO;
import cn.edu.cqupt.pikachu.ad.model.vo.response.Response;
import cn.edu.cqupt.pikachu.ad.service.IUserService;
import cn.edu.cqupt.pikachu.ad.utils.ConvertUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author :DengSiYuan
 * @date :2021/2/5 16:02
 * @desc : 广告系统用户服务接口实现
 */
@Slf4j
@Service
public class UserServiceImpl implements IUserService {

    @Resource
    private AdUserRepository userRepository;

    /**
     * 创建新用户
     *
     * @param userDTO 用户信息
     * @return 用户信息响应
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Response<UserVO> createUser(UserDTO userDTO) throws AdException {

        if (!userDTO.validate()) {
            return new Response<>(ResultStatus.REQUEST_PARAM_ERROR);
        }

        AdUser oldUser = userRepository.findByUsername(userDTO.getUsername());

        if (null != oldUser) {
            return new Response<>((ResultStatus.USER_EXISTED));
        }

        AdUser newUser = userRepository.save(ConvertUtils.userDTO2AdUser(userDTO));
        log.info("ad-sponsor: UserService createUser -> newUser:{}", newUser);

        return new Response<>(ConvertUtils.adUser2UserVO(newUser));
    }

    /**
     * 更新用户资料
     *
     * @param userDTO 用户信息
     * @return 用户展示信息
     * @throws AdException 自定义异常捕获
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Response<UserVO> updateUser(UserDTO userDTO) throws AdException{

        if (!userDTO.validate()) {
            return new Response<>(ResultStatus.REQUEST_PARAM_ERROR);
        }
        AdUser oldUser = userRepository.findByUsername(userDTO.getUsername());

        if (null == oldUser) {
            return new Response<>((ResultStatus.USER_NOT_EXISTED));
        }

        AdUser newUser = userRepository.save(ConvertUtils.userDTO2AdUser(userDTO));

        return new Response<>(ConvertUtils.adUser2UserVO(newUser));
    }
}
