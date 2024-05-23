package com.sky.controller.admin;

import com.sky.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController("adminShopController")
@RequestMapping("/admin/shop")
@Api(tags = "shop interface")
@Slf4j
public class ShopController {

    @Autowired
    private RedisTemplate redisTemplate;

    @PutMapping("/{status}")
    @ApiOperation("shop status setting")
    public Result setStatus(@PathVariable Integer Status) {
        log.info("Setting shop status ... ");
        redisTemplate.opsForValue().set("SHOP_STATUS", Status);
        return Result.success();
    }

    @GetMapping
    @ApiOperation("Get shop status")
    public Result<Integer> getStatus() {
        log.info("Get shop status");
        Integer shopStatus = (Integer) redisTemplate.opsForValue().get("SHOP_STATUS");
        return Result.success(shopStatus);
    }
}
