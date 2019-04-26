package com.mysiteforme.admin.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.IService;
import com.mysiteforme.admin.entity.BlogChannel;
import com.mysiteforme.admin.entity.VO.ZtreeVO;

import java.util.List;

/**
 * <p>
 * 班级管理 服务类
 * </p>
 *
 * @author wangl
 * @since 2018-01-17
 */
public interface BlogChannelService extends IService<BlogChannel> {

    /**
     * 获取ztree格式的树结构数据
     *
     * @return
     */
    List<ZtreeVO> selectZtreeData();

    /**
     * 查询班级数据 返回树形结构数据(列表页用)
     *
     * @return
     */
    List<BlogChannel> selectChannelList();

    /**
     * 新增或者保存数据
     *
     * @param blogChannel 班级管理
     */
    void saveOrUpdateChannel(BlogChannel blogChannel);

    /**
     * 获取班级名称的数量
     *
     * @param name 班级名称
     * @return
     */
    int getCountByName(String name);

    /**
     * 获取指定数量的班级数据
     *
     * @param limit   数量限制
     * @param wrapper 参数集合
     * @return 班级list集合
     */
    List<BlogChannel> getChannelListByWrapper(int limit, EntityWrapper<BlogChannel> wrapper);

    /**
     * 获得当前班级的所有父班级集合
     *
     * @param channelId 当前班级ID
     * @return
     */
    List<BlogChannel> getParentsChannel(Long channelId);

    /**
     * 根据地址获取班级对象
     *
     * @param href
     * @return
     */
    BlogChannel getChannelByHref(String href);

    /**
     * 查询出父ID
     *
     * @param channelId
     * @return
     */
    String selectPatentTreeId(String channelId);
}
