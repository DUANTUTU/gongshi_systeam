package com.gt.app.service;

import java.util.List;
import com.gt.model.TAppProjectAssess;
import com.gt.pageModel.DatagridForLayUI;
import com.gt.pageModel.Json;
import com.gt.sys.service.IBaseService;

/**
 * @功能说明：项目工时评估业务接口类
 * @作者： zm
 * @创建日期：2020-04-13
 * @版本号：V1.0
 */
public interface ITAppProjectAssessService extends IBaseService<TAppProjectAssess> {

	/**
	 * 获取datagrid数据
	 * 
	 * @return
	 */
	public DatagridForLayUI datagrid(TAppProjectAssess inf) throws Exception;

	/**
	 * 新增
	 * 
	 * @param inf
	 * @return
	 */
	public Json add(TAppProjectAssess inf) throws Exception;

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
	public Json modify(TAppProjectAssess inf);

	/**
	 * 信息验证
	 * 
	 * @param inf
	 * @return
	 */
	public Json verifyInfo(TAppProjectAssess inf);

	/**
	 * 获取下拉框数据
	 * @return
	 */
	public List<TAppProjectAssess> getList();

	
}
