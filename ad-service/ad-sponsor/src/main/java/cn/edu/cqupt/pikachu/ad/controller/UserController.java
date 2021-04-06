package cn.edu.cqupt.pikachu.ad.controller;

import cn.edu.cqupt.pikachu.ad.exception.AdException;
import cn.edu.cqupt.pikachu.ad.model.dto.UserDTO;
import cn.edu.cqupt.pikachu.ad.model.vo.UserVO;
import cn.edu.cqupt.pikachu.ad.model.vo.response.Response;
import cn.edu.cqupt.pikachu.ad.service.IUserService;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    public Response<UserVO> createUser(@RequestBody UserDTO userDTO) throws AdException {
        log.info("ad-sponsor: UserController#createUser userDTO -> {}", JSON.toJSONString(userDTO));
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
    public Response<UserVO> updateUser(@RequestBody UserDTO userDTO) throws AdException {
        log.info("ad-sponsor: UserController#updateUser userDTO -> {}", JSON.toJSONString(userDTO));
        return userService.updateUser(userDTO);
    }

}
