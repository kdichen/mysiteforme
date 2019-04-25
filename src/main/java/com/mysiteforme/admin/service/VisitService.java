package com.mysiteforme.admin.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.mysiteforme.admin.entity.Visit;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 家访内容 服务类
 * </p>
 *
 * @author wangl
 * @since 2018-01-17
 */
public interface VisitService extends IService<Visit> {


    /**
     * 查询家访的详细数据(关联表查询)
     *
     * @param id 家访ID
     * @return
     */
    Visit selectOneDetailById(Long id);


    /**
     * 分页查询家访列表
     *
     * @param wrapper
     * @param page
     * @return
     */
    Page<Visit> selectDetailVisit(EntityWrapper<Visit> wrapper, Page<Visit> page);


    /**
     * 根据条件查询家访数据(不关联表查询)
     *
     * @param paramMap 参数Map
     * @return
     */
    List<Visit> selectVisitData(Map<String, Object> paramMap);


    /**
     * 更新或新增数据
     *
     * @param visit
     * @return
     */
    Visit saveOrUpdateVisit(Visit visit);

    /**
     * 删除一条数据
     *
     * @param visit
     * @return
     */
    boolean deleteById(Visit visit);

    /**
     * 查询请假信息
     * @param studentName
     * @param id
     * @param begin
     * @param end
     * @param page
     * @param limit
     * @return
     */
    Page<Visit> selectVisitList(String studentName, Long id, Date begin, Date end, Integer page, Integer limit);


}
