package com.mysiteforme.admin.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.mysiteforme.admin.entity.PhysicalExamination;

/**
 * <p>
 * 体检 服务类
 * </p>
 *
 * @author wangl
 * @since 2017-11-26
 */
public interface PhysicalExaminationService extends IService<PhysicalExamination> {


    /**
     * 删除一条记录
     *
     * @param id
     */
    void deletePhysicalExamination(Long id);

    /**
     * 查询体检列表
     *
     * @param page
     * @param wrapper
     * @return
     */
    Page<PhysicalExamination> queryList(Page<PhysicalExamination> page, EntityWrapper<PhysicalExamination> wrapper);

    /**
     * 新增或修改记录
     *
     * @param physicalExamination
     */
    void saveOrUpdateDict(PhysicalExamination physicalExamination);


}
