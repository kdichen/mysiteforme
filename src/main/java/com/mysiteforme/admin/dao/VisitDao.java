package com.mysiteforme.admin.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.mysiteforme.admin.entity.Visit;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * <p>
 * 家访内容 Mapper 接口
 * </p>
 *
 * @author 王云
 */
public interface VisitDao extends BaseMapper<Visit> {

    List<Visit> selectDetailVisi(EntityWrapper<Visit> wrapper, Page<Visit> page);

    List<Visit> selectDetailVisi(Map<String, Object> map);

    Page<Visit> selectVisitList(@Param("studentName") String studentName,
                                @Param("id") Long id,
                                @Param("begin") Date begin,
                                @Param("end") Date end,
                                @Param("page") Integer page,
                                @Param("limit") Integer limit);
}
