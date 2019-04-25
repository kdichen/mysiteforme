package com.mysiteforme.admin.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.mysiteforme.admin.entity.Reward;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 获奖记录 Mapper 接口
 * </p>
 *
 * @author 王云
 */
public interface RewardDao extends BaseMapper<Reward> {

    /**
     * 查询评论 手动分页
     *
     * @param map
     * @return
     */
    List<Reward> selectArticleComments(Map<String, Object> map);

    Integer selectArticleCommentsCount(Map<String, Object> map);

    List<Reward> selectArticleCommentsByPlus(Map<String, Object> map, Page page);

    List<Reward> getCommentByReplyId(Long replyId);

}
