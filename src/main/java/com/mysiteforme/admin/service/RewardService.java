package com.mysiteforme.admin.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.mysiteforme.admin.entity.Reward;

/**
 * <p>
 * 获奖内容
 * </p>
 *
 * @author 王云
 */
public interface RewardService extends IService<Reward> {


    /**
     * 不采用mybatisPlus自动分页
     *
     * @param articleId
     * @param type
     * @param page
     * @return
     */
    Page<Reward> getArticleComments(Long articleId, Integer type, Page<Reward> page);

    /**
     * 新增或修改
     *
     * @param blogComment
     */
    void saveOrUpdateReward(Reward blogComment);

    /**
     * 查询单个记录
     *
     * @param articleId
     * @return
     */
    Integer getArticleCommentsCount(Long articleId);
}
