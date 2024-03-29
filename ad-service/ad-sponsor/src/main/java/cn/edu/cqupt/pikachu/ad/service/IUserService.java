package cn.edu.cqupt.pikachu.ad.service;

import cn.edu.cqupt.pikachu.ad.model.dto.UserDTO;
import cn.edu.cqupt.pikachu.ad.model.vo.UserVO;
import cn.edu.cqupt.pikachu.ad.model.vo.response.Response;

import javax.servlet.http.HttpServletRequest;

/**
 * @author :DengSiYuan
 * @date :2021/2/4 22:37
 * @desc : 用户服务接口
 */
public interface IUserService {

    /**
     * 创建新用户
     *
     * @param userDTO 用户信息
     * @return 用户展示信息
     */
    Response<UserVO> createUser(UserDTO userDTO);

    /**
     * 更新用户资料
     *
     * @param userDTO 用户信息
     * @return 用户展示信息
     */
    Response<UserVO> updateUser(UserDTO userDTO);

    /**
     * 用户登录
     *
     * @param userDTO 用户信息
     * @param request 请求信息
     * @return 用户展示信息
     */
    Response<UserVO> login(UserDTO userDTO, HttpServletRequest request);

    /**
     * 用户修改密码
     *
     * @param userDTO 用户信息
     * @param request 请求信息
     * @return 是否修改成功
     */
    Response<Boolean> modifyPassword(UserDTO userDTO, HttpServletRequest request);
}
