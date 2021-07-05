package cn.edu.cqupt.pikachu.ad.controller;

import cn.edu.cqupt.pikachu.ad.model.dto.CreativeDTO;
import cn.edu.cqupt.pikachu.ad.model.vo.CreativeVO;
import cn.edu.cqupt.pikachu.ad.model.vo.response.Response;
import cn.edu.cqupt.pikachu.ad.service.ICreativeService;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author :DengSiYuan
 * @date :2021/2/27 21:41
 * @desc : 创意Controller
 */
@Slf4j
@RestController
@RequestMapping("creative")
public class CreativeController {

    @Resource
    private ICreativeService creativeService;

    @PostMapping("create")
    public Response<CreativeVO> createCreative(@RequestBody CreativeDTO creativeDTO) {
        log.info("ad-sponsor: CreativeController createCreative -> {}", JSON.toJSONString(creativeDTO));
        return creativeService.createCreative(creativeDTO);
    }

    @GetMapping("get")
    public Response<List<CreativeVO>> getAllCreatives(Long userId) {
        log.info("ad-sponsor: CreativeController getAllCreatives -> {}", userId);
        return creativeService.getAllCreatives(userId);
    }

    @PutMapping("update")
    public Response<CreativeVO> updateCreative(@RequestBody CreativeDTO creativeDTO) {
        log.info("ad-sponsor: CreativeController updateCreative -> {}", JSON.toJSONString(creativeDTO));
        return creativeService.updateCreative(creativeDTO);
    }

    @GetMapping("getCreativeMsg")
    public Response<List<String>> getAllCreativeMsg(Long userId) {
        log.info("ad-sponsor: CreativeController getAllCreativeMsg -> {}", userId);
        return creativeService.getAllCreativeMsg(userId);
    }

}
