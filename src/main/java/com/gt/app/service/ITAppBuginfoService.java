package com.gt.app.service;

import java.util.List;
import com.gt.model.TAppBuginfo;
import com.gt.pageModel.DatagridForLayUI;
import com.gt.pageModel.Json;
import com.gt.sys.service.IBaseService;

/**
 * @功能说明：缺陷记录表业务接口类
 * @作者： liutaok
 * @创建日期：2023-04-27
 * @版本号：V1.0
 */
public interface ITAppBuginfoService extends IBaseService<TAppBuginfo> {

	/**
	 * 获取datagrid数据
	 * 
	 * @return
	 */
	public DatagridForLayUI datagrid(TAppBuginfo inf) throws Exception;

	/**
	 * 新增
	 * 
	 * @param inf
	 * @return
	 */
	public Json add(TAppBuginfo inf) throws Exception;

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
	public Json modify(TAppBuginfo inf);

	/**
	 * 信息验证
	 * 
	 * @param inf
	 * @return
	 */
	public Json verifyInfo(TAppBuginfo inf);

	/**
	 * 获取下拉框数据
	 * @return
	 */
	public List<TAppBuginfo> getList();

	
}
