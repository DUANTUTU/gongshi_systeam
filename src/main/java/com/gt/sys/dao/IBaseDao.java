package com.gt.sys.dao;

import org.hibernate.*;

import javax.persistence.*;
import java.io.*;
import java.util.*;

/**
 * @Auther:liutaok
 * @Date: 2018/9/5 16:14
 * @Description:BaseDao基础接口，其他dao接口继续此类
 */
public interface IBaseDao<T> {
    /**
     * 获得对象列表
     *
     * @param hql HQL语句
     * @return List
     */
    public List<T> find(String hql);

    /**
     * 获得对象列表
     *
     * @param hql    HQL语句
     * @param params 参数
     * @return List
     */
    public List<T> find(String hql, Map<String, Object> params);

    /**
     * 获得分页后的对象列表
     *
     * @param hql  HQL语句
     * @param page 要显示第几页
     * @param rows 每页显示多少条
     * @return List
     */
    public List<T> find(String hql, int page, int rows);


    /**
     * 获得分页后的对象列表
     *
     * @param hql    HQL语句
     * @param params 参数
     * @param page   要显示第几页
     * @param rows   每页显示多少条
     * @return List
     */
    public List<T> find(String hql, Map<String, Object> params, int page, int rows);


    /**
     * 获得结果集
     *
     * @param sql SQL语句
     * @return 结果集
     */
    public List<Map> findBySql(String sql);

    /**
     * 获得结果集
     *
     * @param sql  SQL语句
     * @param page 要显示第几页
     * @param rows 每页显示多少条
     * @return 结果集
     */
    public List<Map> findBySql(String sql, int page, int rows);

    /**
     * 获得结果集
     *
     * @param sql    SQL语句
     * @param params 参数
     * @return 结果集
     */
    public List<Map> findBySql(String sql, Map<String, Object> params);

    /**
     * 获得结果集
     *
     * @param sql    SQL语句
     * @param params 参数
     * @param page   要显示第几页
     * @param rows   每页显示多少条
     * @return 结果集
     */
    public List<Map> findBySql(String sql, Map<String, Object> params, int page, int rows);


    /**
     * 保存一个对象
     *
     * @param o 对象
     * @return 对象的ID
     */
    public T save(T o);

    /**
     * 删除一个对象
     *
     * @param o 对象
     */
    public void delete(T o);

    /**
     * 更新一个对象
     *
     * @param o 对象
     */
    public T update(T o);

    /**
     * 保存或更新一个对象
     *
     * @param o 对象
     */
    public void saveOrUpdate(T o);

    /**
     * 通过主键获得对象
     *
     * @param c  类名.class
     * @param id 主键
     * @return 对象
     */
    public T getById(Class<T> c, Serializable id);

    /**
     * 通过主键获得对象
     *
     * @param c  类名.class
     * @param id 主键
     * @return 对象
     */
    public T getById(Class<T> c, Serializable id, LockModeType lockModeType);

    /**
     * 通过HQL语句获取一个对象
     *
     * @param hql HQL语句
     * @return 对象
     */
    public T getByHql(String hql);

    /**
     * 通过HQL语句获取一个对象
     *
     * @param hql HQL语句
     * @return 对象
     */
    public T getByHql(String hql, LockModeType lockModeType);

    /**
     * 通过HQL语句获取一个对象
     *
     * @param hql    HQL语句
     * @param params 参数
     * @return 对象
     */
    public T getByHql(String hql, Map<String, Object> params);


    /**
     * 通过HQL语句获取一个对象
     *
     * @param hql    HQL语句
     * @param params 参数
     * @return 对象
     */
    public T getByHql(String hql, Map<String, Object> params, LockModeType lockModeType);


    /**
     * 获得对象列表,多表查询时使用
     *
     * @param hql HQL语句
     * @return List
     */
    public List findByHqL(String hql);

    /**
     * 获得对象列表 ,多表查询时使用
     *
     * @param hql    HQL语句
     * @param params 参数
     * @return List
     */
    public List findByHqL(String hql, Map<String, Object> params);

    /**
     * 获得分页后的对象列表,多表查询时使用
     *
     * @param hql  HQL语句
     * @param page 要显示第几页
     * @param rows 每页显示多少条
     * @return List
     */
    public List findByHqL(String hql, int page, int rows);

    /**
     * 获得分页后的对象列表,多表查询时使用
     *
     * @param hql    HQL语句
     * @param params 参数
     * @param page   要显示第几页
     * @param rows   每页显示多少条
     * @return List
     */
    public List findByHqL(String hql, Map<String, Object> params, int page, int rows);

    /**
     * 执行SQL语句
     *
     * @param sql SQL语句
     * @return 响应行数
     */
    public int executeSql(String sql);

    /**
     * 执行SQL语句
     *
     * @param sql    SQL语句
     * @param params 参数
     * @return 响应行数
     */
    public int executeSql(String sql, Map<String, Object> params);

    /**
     * 统计
     *
     * @param sql SQL语句
     * @return 数目
     */
    public Long countBySql(String sql);

    /**
     * 统计
     *
     * @param sql    SQL语句
     * @param params 参数
     * @return 数目
     */
    public Long countBySql(String sql, Map<String, Object> params);

    /**
     * 统计数目
     *
     * @param hql HQL语句(select count(*) from T)
     * @return long
     */
    public Long count(String hql);

    /**
     * 统计数目
     *
     * @param hql    HQL语句(select count(*) from T where xx = :xx)
     * @param params 参数
     * @return long
     */
    public Long count(String hql, Map<String, Object> params);

    /**
     * 执行一条HQL语句
     *
     * @param hql HQL语句
     * @return 响应结果数目
     */
    public int executeHql(String hql);

    /**
     * 执行一条HQL语句
     *
     * @param hql    HQL语句
     * @param params 参数
     * @return 响应结果数目
     */
    public int executeHql(String hql, Map<String, Object> params);
}
