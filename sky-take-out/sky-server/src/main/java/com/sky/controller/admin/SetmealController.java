package com.sky.controller.admin;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.SetmealService;
import com.sky.vo.SetmealVO;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/setmeal")
@Slf4j
public class SetmealController {

    @Autowired
    private SetmealService setmealService;

    @CacheEvict(cacheNames = "setmealCache", key = "#setmealDTO.categoryId")
    @PostMapping
    @ApiOperation("Add Setmeal")
    public Result save(@RequestBody SetmealDTO setmealDTO) {
        setmealService.save(setmealDTO);
        return Result.success();
    }

    @CacheEvict(cacheNames = "setmealCache", allEntries = true)
    @GetMapping("/page")
    @ApiOperation("Setmeal page query")
    public Result<PageResult> pageQuery(@RequestParam SetmealPageQueryDTO setmealPageQueryDTO) {
        PageResult pageResult = setmealService.pageQuery(setmealPageQueryDTO);
        return Result.success(pageResult);
    }

    @CacheEvict(cacheNames = "setmealCache", allEntries = true)
    @DeleteMapping
    @ApiOperation("Delete by Id")
    public Result deleteById(@RequestParam List<Long> ids) {
        setmealService.deleteByIds(ids);
        return Result.success();
    }

    @CacheEvict(cacheNames = "setmealCache", allEntries = true)
    @PutMapping
    @ApiOperation("Update by Id")
    public Result updateById(@RequestBody SetmealDTO setmealDTO) {

        //修改setmeal
        setmealService.updateById(setmealDTO);

        //修改set-dish
        return null;
    }

    /**
     * 根据id查询套餐，用于修改页面回显数据
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @ApiOperation("根据id查询套餐")
    public Result<SetmealVO> getById(@PathVariable Long id) {
        SetmealVO setmealVO = setmealService.getByIdWithDish(id);
        return Result.success(setmealVO);
    }

    @CacheEvict(cacheNames = "setmealCache", allEntries = true)
    @PostMapping("/status/{status}")
    @ApiOperation("Start or stop")
    public Result startOrStop(@PathVariable Integer status, Long id) {
        setmealService.startOrStop(status, id);
        return Result.success();

    }
}
