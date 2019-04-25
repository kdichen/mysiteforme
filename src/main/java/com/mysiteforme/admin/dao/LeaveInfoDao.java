package com.mysiteforme.admin.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.mysiteforme.admin.entity.LeaveInfo;

/**
 * <p>
 * 请假信息 Mapper 接口
 * </p>
 *
 * @author wangl
 * @since 2018-01-24
 */
public interface LeaveInfoDao extends BaseMapper<LeaveInfo> {
    /**
     * 根据ID修改请假状态
     *
     * @param id
     */
    void paushStatusById(Long id);

    /**
     * 根据ID修改请假状态
     *
     * @param id
     */
    void resumeStatusById(Long id);

    /**
     * 根据ID删除请假信息
     *
     * @param id
     */
    void deleteLeaveInfoById(Long id);

}
