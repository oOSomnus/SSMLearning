package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.enumeration.OperationType;
import com.sky.vo.SetmealVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SetmealMapper {

    /**
     * Delete by ids
     *
     * @param ids
     */
    void deleteByIds(List<Long> ids);

    /**
     * 根据分类id查询套餐的数量
     *
     * @param id
     * @return
     */
    @Select("select count(id) from setmeal where category_id = #{categoryId}")
    Integer countByCategoryId(Long id);

    /**
     * Add setmeal
     *
     * @param setmeal
     */
    @AutoFill(value = OperationType.INSERT)
    void save(Setmeal setmeal);


    /**
     * Page Query
     *
     * @param setmealPageQueryDTO
     * @return
     */
    Page<SetmealVO> pageQuery(SetmealPageQueryDTO setmealPageQueryDTO);

    /**
     * Select by Ids
     *
     * @param ids
     * @return
     */
    List<Setmeal> selectByIds(List<Long> ids);

    /**
     * Update by id
     *
     * @param setmeal
     */
    void updateById(Setmeal setmeal);

    @Select("select * from setmeal where id = ${id}")
    Setmeal getById(Long id);
}
