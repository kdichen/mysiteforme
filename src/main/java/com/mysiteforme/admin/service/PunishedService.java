package com.mysiteforme.admin.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.mysiteforme.admin.entity.Punished;

import java.util.List;

/**
 * <p>
 * 受罚记录 服务类
 * </p>
 *
 * @author wangl
 * @since 2018-01-17
 */
public interface PunishedService extends IService<Punished> {


    /**
     * 查询受罚记录数据 (列表页用)
     *
     * @return
     */
    List<Punished> selectPunishedList();

    /**
     * 编辑受罚记录
     *
     * @param punished
     */
    void updatePunished(Punished punished);

    /**
     * 删除所选受罚记录
     *
     * @param ids
     */
    void deletePunished(List<Long> ids);

    /**
     * 新增或修改处罚记录
     *
     * @param punished
     */
    void saveOrUpdatePunished(Punished punished);

    Page<Punished> queryList(EntityWrapper<Punished> wrapper, Page<Punished> objectPage);
}
