package com.mysiteforme.admin.service.impl;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mysiteforme.admin.dao.LeaveInfoDao;
import com.mysiteforme.admin.entity.LeaveInfo;
import com.mysiteforme.admin.service.LeaveInfoService;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 请假信息 服务实现类
 * </p>
 *
 * @author wangl
 * @since 2018-01-24
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class LeaveInfoServiceImpl extends ServiceImpl<LeaveInfoDao, LeaveInfo> implements LeaveInfoService {

    @Autowired
    private Scheduler scheduler;

    @Autowired
    private LeaveInfoDao leaveInfoDao;


    @Override
    public LeaveInfo queryObject(Long jobId) {
        return baseMapper.selectById(jobId);
    }

    @Override
    public Page<LeaveInfo> queryList(EntityWrapper<LeaveInfo> wrapper, Page<LeaveInfo> page) {
        return selectPage(page, wrapper);
    }

    @Override
    public void saveLeaveInfor(LeaveInfo leaveInfor) {
        baseMapper.insert(leaveInfor);
    }

    @Override
    public void updateLeaveInfor(LeaveInfo leaveInfor) {
        baseMapper.updateById(leaveInfor);
    }



    @Override
    public int updateBatchTasksByStatus(List<Long> ids, Integer status) {
        List<LeaveInfo> list = selectBatchIds(ids);
        for (LeaveInfo task : list) {
            task.setStatus(status);
        }
        updateBatchById(list);
        return 0;
    }

    /*@Override
    public void run(List<Long> jobIds) {
        for (Long jobId : jobIds) {
            ScheduleUtils.run(scheduler, queryObject(jobId));
        }
    }*/

    /**
     * @param ids
     */
    @Override
    public void paushStatusById(List<Long> ids) {
        for (Long id : ids) {
            leaveInfoDao.paushStatusById(id);
        }
    }

    @Override
    public void deleteLeaveInfo(List<Long> ids) {
        for (Long id : ids) {
            leaveInfoDao.deleteLeaveInfoById(id);
        }
    }

    @Override
    public void resumeStatusById(List<Long> ids) {
        for (Long id : ids) {
            leaveInfoDao.resumeStatusById(id);
        }
    }
}
