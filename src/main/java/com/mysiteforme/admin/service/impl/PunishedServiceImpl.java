package com.mysiteforme.admin.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.mysiteforme.admin.dao.PunishedDao;
import com.mysiteforme.admin.entity.Punished;
import com.mysiteforme.admin.service.PunishedService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 受罚记录 服务实现类
 * </p>
 *
 * @author wangl
 * @since 2018-01-17
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class PunishedServiceImpl extends ServiceImpl<PunishedDao, Punished> implements PunishedService {


    @Override
    public List<Punished> selectPunishedList() {
        List<Punished> list = Lists.newArrayList();
        try {
            list = baseMapper.selectPunishedData();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void updatePunished(Punished punished) {
        baseMapper.updateById(punished);
    }

    @Override
    public void deletePunished(List<Long> ids) {
        for (Long id : ids) {
            baseMapper.paushStatusById(id);
        }
    }

    @Override
    public void saveOrUpdatePunished(Punished punished) {
        try {
            insertOrUpdate(punished);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public Page<Punished> queryList(EntityWrapper<Punished> wrapper, Page<Punished> objectPage) {
        return selectPage(objectPage, wrapper);
    }


}
