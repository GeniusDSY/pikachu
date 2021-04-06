package cn.edu.cqupt.pikachu.ad.service;

import cn.edu.cqupt.pikachu.ad.exception.AdException;
import cn.edu.cqupt.pikachu.ad.model.dto.UserDTO;
import cn.edu.cqupt.pikachu.ad.model.vo.UserVO;
import cn.edu.cqupt.pikachu.ad.model.vo.response.Response;

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
     * @throws AdException 自定义异常捕获
     */
    Response<UserVO> createUser(UserDTO userDTO);

    /**
     * 更新用户资料
     *
     * @param userDTO 用户信息
     * @return 用户展示信息
     * @throws AdException 自定义异常捕获
     */
    Response<UserVO> updateUser(UserDTO userDTO);

}
