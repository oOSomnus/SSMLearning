package com.sky.controller.admin;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

import static com.sky.result.Result.success;

/**
 * 菜品管理
 */
@RestController
@RequestMapping("/admin/dish")
@Api(tags = "Dish Interface")
@Slf4j
public class DishController {
    @Autowired
    private DishService dishService;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 新增菜品
     *
     * @param dishDTO
     * @return
     */
    @PostMapping
    @ApiOperation("Add new dish")
    public Result save(@RequestBody DishDTO dishDTO) {
        log.info("Dish adding: {}", dishDTO);
        dishService.saveWithFlavor(dishDTO);
        String key = "dish_" + dishDTO.getCategoryId();
        redisTemplate.delete(key);
        return success();
    }

    @GetMapping("/page")
    @ApiOperation("Dish page select")
    public Result<PageResult> page(DishPageQueryDTO dishPageQueryDTO) {
        log.info("Dish page select: {}", dishPageQueryDTO);
        PageResult pageResult = dishService.pageQuery(dishPageQueryDTO);
        return Result.success(pageResult);
    }

    @GetMapping
    @ApiOperation("Dish Delete")
    public Result delete(@RequestParam List<Long> ids) {
        log.info("Dish patch delete: {}", ids);
        dishService.deleteBatch(ids);
        Set dish = redisTemplate.keys("dish_");
        redisTemplate.delete(dish);
        return Result.success();
    }

    @GetMapping("/{id}")
    @ApiOperation("Get Dish By Id")
    public Result<DishVO> getById(@PathVariable Long id) {
        log.info("Dish getById: {}", id);
        DishVO dishVO = dishService.getByIdWithFlavor(id);
        return Result.success(dishVO);
    }

    @PutMapping
    @ApiOperation("Update dish")
    public Result update(@RequestBody DishDTO dishDTO) {
        log.info("Dish update: {}", dishDTO);
        dishService.updateWithFlavor(dishDTO);
        Set dish = redisTemplate.keys("dish_");
        redisTemplate.delete(dish);
        return Result.success();
    }

    /**
     * 根据分类id查询菜品
     *
     * @param categoryId
     * @return
     */
    @GetMapping("/list")
    @ApiOperation("根据分类id查询菜品")
    public Result<List<Dish>> list(Long categoryId) {
        List<Dish> list = dishService.list(categoryId);
        return Result.success(list);
    }

//    private void cleanCache(String pattern) {
//        Set keys = redisTemplate.keys(pattern);
//        redisTemplate.delete(keys);
//    }
}
