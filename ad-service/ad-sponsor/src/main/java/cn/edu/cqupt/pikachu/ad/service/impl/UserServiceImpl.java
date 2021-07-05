package cn.edu.cqupt.pikachu.ad.service.impl;

import cn.edu.cqupt.pikachu.ad.constants.enums.ResultStatus;
import cn.edu.cqupt.pikachu.ad.dao.AdUserRepository;
import cn.edu.cqupt.pikachu.ad.exception.AdException;
import cn.edu.cqupt.pikachu.ad.model.dto.UserDTO;
import cn.edu.cqupt.pikachu.ad.model.entity.AdUser;
import cn.edu.cqupt.pikachu.ad.model.vo.UserVO;
import cn.edu.cqupt.pikachu.ad.model.vo.response.Response;
import cn.edu.cqupt.pikachu.ad.service.IUserService;
import cn.edu.cqupt.pikachu.ad.utils.CommonUtils;
import cn.edu.cqupt.pikachu.ad.utils.ConvertUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Optional;

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
    public Response<UserVO> createUser(UserDTO userDTO) {
        try {
            if (userDTO.validate()) {
                return new Response<>(ResultStatus.REQUEST_PARAM_ERROR);
            }

            AdUser oldUser = userRepository.findByUsername(userDTO.getUsername());

            if (null != oldUser) {
                return new Response<>((ResultStatus.USER_EXISTED));
            }

            AdUser newUser = userRepository.save(ConvertUtils.userDTO2AdUser(userDTO));
            log.info("ad-sponsor: UserService createUser -> newUser:{}", newUser);
            return new Response<>(ConvertUtils.adUser2UserVO(newUser));
        } catch (Exception e) {
            log.error("ad-sponsor: UserService updateUser -> request={}-error={}", userDTO, e.getMessage());
            return new Response<>(ResultStatus.CREATE_USER_ERROR);
        }

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
    public Response<UserVO> updateUser(UserDTO userDTO) {
        try {
            if (userDTO.validate()) {
                return new Response<>(ResultStatus.REQUEST_PARAM_ERROR);
            }
            Optional<AdUser> searchResult = userRepository.findById(userDTO.getUserId());

            if (!searchResult.isPresent()) {
                return new Response<>((ResultStatus.USER_NOT_EXISTED));
            }

            AdUser newUser = ConvertUtils.userDTO2AdUser(userDTO);
            newUser.setCreateTime(searchResult.get().getCreateTime());
            newUser.setUpdateTime(new Date());
            return new Response<>(ConvertUtils.adUser2UserVO(userRepository.save(newUser)));
        } catch (Exception e) {
            log.error("ad-sponsor: UserService updateUser -> request={}-error={}", userDTO, e.getMessage());
            return new Response<>(ResultStatus.UPDATE_USER_ERROR);
        }
    }

    /**
     * 用户登录
     *
     * @param userDTO 用户信息
     * @param request
     * @return 用户展示信息
     */
    @Override
    public Response<UserVO> login(UserDTO userDTO, HttpServletRequest request) {
        try {
            if (userDTO.validate()) {
                return new Response<>(ResultStatus.REQUEST_PARAM_ERROR);
            }
            // 判断账号密码是否匹配
            AdUser user = userRepository.findByUsername(userDTO.getUsername());

            if (user.getPassword().equals(CommonUtils.md5Encrypt(userDTO.getPassword()))) {
                request.getSession().setAttribute("userToken", user.getToken());
                return new Response<>(ConvertUtils.adUser2UserVO(user));
            }
            return new Response<>(ResultStatus.WRONG_PASSWORD);
        } catch (AdException e) {
            log.error("ad-sponsor: UserService login -> request={}-error={}", userDTO, e.getMessage());
            return new Response<>(ResultStatus.LOGIN_ERROR);
        }
    }

    /**
     * 用户修改密码
     *
     * @param userDTO 用户信息
     * @param request 请求信息
     * @return 是否修改成功
     */
    @Override
    public Response<Boolean> modifyPassword(UserDTO userDTO, HttpServletRequest request) {

        //if ()
        return null;
    }
}
