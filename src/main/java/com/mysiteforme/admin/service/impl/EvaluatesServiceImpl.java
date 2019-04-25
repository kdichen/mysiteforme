package com.mysiteforme.admin.service.impl;

import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mysiteforme.admin.dao.EvaluateDao;
import com.mysiteforme.admin.entity.Evaluate;
import com.mysiteforme.admin.service.EvaluateService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 评价记录 服务实现类
 * </p>
 *
 * @author wangl
 * @since 2018-01-17
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class EvaluatesServiceImpl extends ServiceImpl<EvaluateDao, Evaluate> implements EvaluateService {

    @Override
    public Integer getCountByName(String name) {
        EntityWrapper<Evaluate> wrapper = new EntityWrapper<>();
        wrapper.eq("del_flag", false);
        wrapper.eq("name", name);
        return selectCount(wrapper);
    }

    @Override
    public void saveEvaluate(Evaluate tags) {
        Object o = selectObj(Condition.create().setSqlSelect("max(sort)").eq("del_flag", false));
        int sort = 0;
        if (o != null) {
            sort = (Integer) o + 1;
        }
        tags.setSort(sort);
        insert(tags);
    }

    @Override
    public List<Evaluate> listAll() {
        EntityWrapper<Evaluate> wrapper = new EntityWrapper<>();
        wrapper.eq("del_flag", false);
        wrapper.orderBy("sort", false);
        return selectList(wrapper);
    }

//    @Cacheable(value = "blogTagsData", key = "'blog_tags_channel_'+#channelId", unless = "#result == null or #result.size() == 0")
    @Override
    public List<Evaluate> getEvaluatesByChannelId(Long channelId) {
        return baseMapper.getEvaluateByChannelId(channelId);
    }

//    @Cacheable(value = "blogTagsData", key = "'blog_tags_article_'+#articleId", unless = "#result == null or #result.size() == 0")
    @Override
    public List<Evaluate> getEvaluatesByArticleId(Long articleId) {
        return baseMapper.getEvaluateByArticleId(articleId);
    }

    @CacheEvict(value = "blogTagsData", allEntries = true)
    @Override
    public void deleteThisEvaluate(Long id) {
        deleteById(id);
        baseMapper.removeArticleByEvaluateId(id);
    }

    @Override
    public Page<Evaluate> selectEvaluatesPage(Map<String, Object> map, Page<Evaluate> page) {
        List<Evaluate> blogTagsList = baseMapper.selectEvaluatePage(map, page);
        page.setRecords(blogTagsList);
        return page;
    }

    @Override
    public List<Evaluate> selectEvaluatesPage(Map<String, Object> map) {
        return baseMapper.selectEvaluatePage(map);
    }

    @Override
    public Page<Evaluate> queryList(EntityWrapper<Evaluate> wrapper, Page<Evaluate> page) {
        return selectPage(page, wrapper);
    }
}
