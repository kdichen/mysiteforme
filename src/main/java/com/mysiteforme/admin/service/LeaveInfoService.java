package com.mysiteforme.admin.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.mysiteforme.admin.entity.LeaveInfo;

import java.util.List;

/**
 * <p>
 * 请假信息 服务类
 * </p>
 *
 * @author : 王云
 */
public interface LeaveInfoService extends IService<LeaveInfo> {

    /**
     * 根据ID，查询请假信息
     */
    LeaveInfo queryObject(Long jobId);

    /**
     * 分页查询请假信息列表
     */
    Page<LeaveInfo> queryList(EntityWrapper<LeaveInfo> wrapper, Page<LeaveInfo> page);

    /**
     * 保存请假信息
     */
    void saveLeaveInfor(LeaveInfo leaveInfor);

    /**
     * 更新请假信息
     */
    void updateLeaveInfor(LeaveInfo leaveInfor);

    /**
     * 批量删除请假信息
     */
    void deleteLeaveInfo(List<Long> ids);

    /**
     * 批量更新请假信息状态
     *
     * @param ids
     * @param status
     * @return
     */
    int updateBatchTasksByStatus(List<Long> ids, Integer status);

    /**
     * 立即执行
     */
//    void run(List<Long> jobIds);

    /**
     * 根据ID修改请假时间
     *
     * @param ids
     */
    void paushStatusById(List<Long> ids);

    /**
     * 恢复运行
     */
    void resumeStatusById(List<Long> jobIds);
}
