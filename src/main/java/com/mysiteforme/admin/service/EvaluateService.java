package com.mysiteforme.admin.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.mysiteforme.admin.entity.Evaluate;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 评价记录 服务类
 * </p>
 *
 * @author wangl
 * @since 2018-01-17
 */
public interface EvaluateService extends IService<Evaluate> {

    /**
     * 根据评价名称获取数量
     *
     * @param name 评价名称
     * @return
     */
    Integer getCountByName(String name);

    /**
     * 新增评价
     *
     * @param evaluate
     */
    void saveEvaluate(Evaluate evaluate);

    /**
     * 获取所有评价
     *
     * @return
     */
    List<Evaluate> listAll();

    /**
     * 获取栏目对应的文章的所有评价
     *
     * @param channelId 栏目ID
     * @return
     */
    List<Evaluate> getEvaluatesByChannelId(Long channelId);

    /**
     * 获取文章的所有评价
     *
     * @param articleId 文章ID
     * @return
     */
    List<Evaluate> getEvaluatesByArticleId(Long articleId);

    void deleteThisEvaluate(Long id);

    Page<Evaluate> selectEvaluatesPage(Map<String, Object> map, Page<Evaluate> page);

    List<Evaluate> selectEvaluatesPage(Map<String, Object> map);

    Page<Evaluate> queryList(EntityWrapper<Evaluate> wrapper, Page<Evaluate> objectPage);
}
