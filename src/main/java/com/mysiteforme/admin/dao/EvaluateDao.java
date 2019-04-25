package com.mysiteforme.admin.dao;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.mysiteforme.admin.entity.Evaluate;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 评价记录 Mapper 接口
 * </p>
 *
 * @author : 王云
 */
public interface EvaluateDao extends BaseMapper<Evaluate> {

    /**
     * 根据栏目ID获取评价集合
     *
     * @param channelId
     * @return
     */
    List<Evaluate> getEvaluateByChannelId(Long channelId);

    /**
     * 根据文章ID获取评价集合
     *
     * @param articleId
     * @return
     */
    List<Evaluate> getEvaluateByArticleId(Long articleId);

    /**
     * 删除跟这个评价相关的所有关系
     *
     * @param id 评价ID
     */
    void removeArticleByEvaluateId(Long id);

    /**
     * 根据删选条件获取评价记录的分页列表
     *
     * @param map
     * @param page
     * @return
     */
    List<Evaluate> selectEvaluatePage(Map<String, Object> map, Page<Evaluate> page);

    /**
     * 查询评价记录分页列表
     *
     * @param map
     * @return
     */
    List<Evaluate> selectEvaluatePage(Map<String, Object> map);
}
