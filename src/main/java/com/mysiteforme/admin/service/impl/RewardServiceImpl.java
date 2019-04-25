package com.mysiteforme.admin.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.google.common.collect.Maps;
import com.mysiteforme.admin.dao.RewardDao;
import com.mysiteforme.admin.entity.Reward;
import com.mysiteforme.admin.service.RewardService;
import com.mysiteforme.admin.util.Constants;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 获奖记录 服务实现类
 * </p>
 *
 * @author 王云
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class RewardServiceImpl extends ServiceImpl<RewardDao, Reward> implements RewardService {


    @Override
    public Page<Reward> getArticleComments(Long articleId, Integer type, Page<Reward> page) {
        Map<String, Object> map = Maps.newHashMap();
        if (articleId != null) {
            map.put("articleId", articleId);
        }
        map.put("type", type);
        List<Reward> list = baseMapper.selectArticleCommentsByPlus(map, page);
        page.setRecords(list);
        return page;
    }

    @CacheEvict(value = "commentData", key = "'article_'+#blogComment.articleId+'_commentcount'")
    @Override
    public void saveOrUpdateReward(Reward blogComment) {
        insertOrUpdate(blogComment);
    }

//    @Cacheable(value = "commentData", key = "'article_'+#articleId+'_commentcount'")
    @Override
    public Integer getArticleCommentsCount(Long articleId) {
        EntityWrapper<Reward> wrapper = new EntityWrapper<>();
        wrapper.eq("del_flag", false);
        wrapper.eq("type", Constants.COMMENT_TYPE_ARTICLE_COMMENT);
        wrapper.eq("article_id", articleId);
        return selectCount(wrapper);
    }
}
