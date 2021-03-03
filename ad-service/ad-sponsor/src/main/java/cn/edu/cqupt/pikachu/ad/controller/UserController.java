package cn.edu.cqupt.pikachu.ad.controller;

import cn.edu.cqupt.pikachu.ad.exception.AdException;
import cn.edu.cqupt.pikachu.ad.model.dto.UserDTO;
import cn.edu.cqupt.pikachu.ad.model.vo.UserVO;
import cn.edu.cqupt.pikachu.ad.service.IUserService;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author :DengSiYuan
 * @date :2021/2/17 19:53
 * @desc : 用户Controller
 */
@Slf4j
@RestController
@RequestMapping("user")
public class UserController {

    @Resource
    private IUserService userService;

    /**
     * 创建用户接口
     *
     * @param userDTO 用户传入数据
     * @return 用户展示数据
     * @throws AdException 广告系统异常
     */
    @PostMapping("create")
    public UserVO createUser(UserDTO userDTO) throws AdException {
        log.info("UserController#createUser userDTO -> {}", JSON.toJSONString(userDTO));
        return userService.createUser(userDTO);
    }

    /**
     * 更新用户数据
     *
     * @param userDTO 用户传入数据
     * @return 用户展示数据
     * @throws AdException 广告系统异常
     */
    @PostMapping("update")
    public UserVO updateUser(UserDTO userDTO) throws AdException {
        log.info("UserController#updateUser userDTO -> {}", JSON.toJSONString(userDTO));
        return userService.updateUser(userDTO);
    }

}
