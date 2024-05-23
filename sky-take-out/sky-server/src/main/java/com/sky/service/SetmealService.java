package com.sky.service;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.vo.SetmealVO;

import java.util.List;

public interface SetmealService {

    /**
     * Add meal
     *
     * @param setmealDTO
     */
    void save(SetmealDTO setmealDTO);


    /**
     * Page query
     *
     * @param setmealPageQueryDTO
     * @return
     */
    PageResult pageQuery(SetmealPageQueryDTO setmealPageQueryDTO);

    /**
     * Delete by id
     *
     * @param id
     */
    void deleteByIds(List<Long> ids);

    /**
     * Update by Id
     *
     * @param setmeal
     */

    void updateById(SetmealDTO setmeal);

    /**
     * 根据id查询套餐和关联的菜品数据
     *
     * @param id
     * @return
     */
    SetmealVO getByIdWithDish(Long id);

    /**
     * Start or stop
     *
     * @param status
     * @param id
     */
    void startOrStop(Integer status, Long id);
}
