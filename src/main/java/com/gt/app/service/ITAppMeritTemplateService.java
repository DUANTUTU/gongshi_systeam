package com.gt.app.service;

import com.gt.model.TAppMeritTemplate;
import com.gt.pageModel.DatagridForLayUI;
import com.gt.pageModel.Json;
import com.gt.sys.service.IBaseService;

import java.util.List;
import java.util.Map;

/**
 * @功能说明：绩效模板管理业务接口类
 * @作者： zm
 * @创建日期：2020-04-11
 * @版本号：V1.0
 */
public interface ITAppMeritTemplateService extends IBaseService<TAppMeritTemplate> {

	/**
	 * 获取datagrid数据
	 * 
	 * @return
	 */
	public DatagridForLayUI datagrid(TAppMeritTemplate inf) throws Exception;

	/**
	 * 新增
	 * 
	 * @param inf
	 * @return
	 */
	public Json add(TAppMeritTemplate inf) throws Exception;

	/**
	 * 删除
	 * 
	 * @param ids
	 */
	public void remove(String ids);

	/**
	 * 修改
	 * 
	 * @param menuInf
	 */
	public Json modify(TAppMeritTemplate inf);

	/**
	 * 信息验证
	 * 
	 * @param inf
	 * @return
	 */
	public Json verifyInfo(TAppMeritTemplate inf);

	/**
	 * 获取下拉框数据
	 * @return
	 */
	public List<TAppMeritTemplate> getList();

	/**
	 * 查看职位是否已存在绩效模板
	 * @return
	 */
	public Long countEntityByMjobid(TAppMeritTemplate inf);
	/**
	 * 根据人员ID查询所对应的绩效模板
	 * */
	public List<Map> getEntityByOpercd(String opercd);
}
