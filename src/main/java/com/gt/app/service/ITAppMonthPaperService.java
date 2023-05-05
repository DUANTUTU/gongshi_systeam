package com.gt.app.service;

import java.util.List;
import com.gt.model.TAppMonthPaper;
import com.gt.pageModel.DatagridForLayUI;
import com.gt.pageModel.Json;
import com.gt.sys.service.IBaseService;

/**
 * @功能说明：月报业务接口类
 * @作者： zm
 * @创建日期：2020-04-12
 * @版本号：V1.0
 */
public interface ITAppMonthPaperService extends IBaseService<TAppMonthPaper> {

	/**
	 * 获取datagrid数据
	 * 
	 * @return
	 */
	public DatagridForLayUI datagrid(TAppMonthPaper inf) throws Exception;

	/**
	 * 新增
	 * 
	 * @param inf
	 * @return
	 */
	public Json add(TAppMonthPaper inf) throws Exception;

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
	public Json modify(TAppMonthPaper inf);

	/**
	 * 信息验证
	 * 
	 * @param inf
	 * @return
	 */
	public Json verifyInfo(TAppMonthPaper inf);

	/**
	 * 获取下拉框数据
	 * @return
	 */
	public List<TAppMonthPaper> getList();

	
}
