package com.mysiteforme.admin.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.mysiteforme.admin.entity.Punished;

import java.util.List;

/**
 * <p>
 * 博客栏目 Mapper 接口
 * </p>
 *
 * @author wangl
 * @since 2018-01-17
 */
public interface PunishedDao extends BaseMapper<Punished> {


    List<Punished> selectPunishedData();

    void paushStatusById(Long id);
}
