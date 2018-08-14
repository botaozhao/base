package com.kviuff.common.mapper;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * BaseMapper
 * mapper接口公共的方法
 * @author kanglan
 */
public interface BaseMapper<T> extends Mapper<T>, MySqlMapper<T>{

}
