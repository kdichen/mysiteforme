package com.mysiteforme.admin.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.google.common.collect.Maps;
import com.mysiteforme.admin.dao.VisitDao;
import com.mysiteforme.admin.entity.Visit;
import com.mysiteforme.admin.service.VisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 家访内容 服务实现类
 * </p>
 *
 * @author wangl
 * @since 2018-01-17
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class VisitServiceImpl extends ServiceImpl<VisitDao, Visit> implements VisitService {


    @Autowired
    private VisitDao visitDao;


    //    @Cacheable(value = "oneArticle",key = "'article_id_'+#id",unless = "#result == null ")
    @Override
    public Visit selectOneDetailById(Long id) {
        Map<String, Object> map = Maps.newHashMap();
        map.put("id", id);
        List<Visit> list = baseMapper.selectDetailVisi(map);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public Page<Visit> selectDetailVisit(EntityWrapper<Visit> wrapper, Page<Visit> page) {
        return selectPage(page, wrapper);
    }


    //    @Cacheable(value = "myarticle",key="'directive_limit_'+#paramMap['limit'].toString()+'_channelId_'+#paramMap['channelId'].toString()",unless = "#result == null or #result.size() == 0")
    @Override
    public List<Visit> selectVisitData(Map<String, Object> paramMap) {
        Long channelId = (Long) paramMap.get("channelId");
        Integer limit = (Integer) paramMap.get("limit");
        EntityWrapper<Visit> wrapper = new EntityWrapper<>();
        wrapper.eq("del_flag", false);
        wrapper.eq("channel_id", channelId);
        wrapper.orderBy("is_top", false).orderBy("is_recommend", false).orderBy("sort", false).orderBy("publist_time", false);
        Page<Visit> pageData = selectPage(new Page<Visit>(1, limit), wrapper);
        return pageData.getRecords();
    }


    @Caching(evict = {@CacheEvict(value = "myarticle", allEntries = true), @CacheEvict(value = "blogTagsData", allEntries = true), @CacheEvict(value = "oneArticle", allEntries = true),})
    @Override
    public Visit saveOrUpdateVisit(Visit visit) {
        insertOrUpdate(visit);
        return visit;
    }

    @Override
    public boolean deleteById(Visit visit) {
        return deleteById(visit.getId());
    }

    @Override
    public Page<Visit> selectVisitList(String studentName, Long id, Date begin, Date end, Integer page, Integer limit) {

        return visitDao.selectVisitList(studentName, id, begin, end, page, limit);

    }


}
