package com.mysiteforme.admin.service.impl;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mysiteforme.admin.dao.PhysicalExaminationDao;
import com.mysiteforme.admin.entity.PhysicalExamination;
import com.mysiteforme.admin.service.PhysicalExaminationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 字典表 服务实现类
 * </p>
 *
 * @author wangl
 * @since 2017-11-26
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class PhysicalExaminationServiceImpl extends ServiceImpl<PhysicalExaminationDao, PhysicalExamination> implements PhysicalExaminationService {

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    @Override
    public void deletePhysicalExamination(Long id) {
        baseMapper.deleteById(id);
    }

    @Override
    public Page<PhysicalExamination> queryList(Page<PhysicalExamination> page, EntityWrapper<PhysicalExamination> wrapper) {
        return selectPage(page, wrapper);
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    @Override
    public void saveOrUpdateDict(PhysicalExamination dict) {
        insertOrUpdate(dict);
    }

}
