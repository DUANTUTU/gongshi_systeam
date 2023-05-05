package com.gt.app.service;

import java.util.List;
import com.gt.model.TAppWeekPaper;
import com.gt.pageModel.DatagridForLayUI;
import com.gt.pageModel.Json;
import com.gt.sys.service.IBaseService;

/**
 * @功能说明：周报业务接口类
 * @作者： zm
 * @创建日期：2020-04-12
 * @版本号：V1.0
 */
public interface ITAppWeekPaperService extends IBaseService<TAppWeekPaper> {

	/**
	 * 获取datagrid数据
	 * 
	 * @return
	 */
	public DatagridForLayUI datagrid(TAppWeekPaper inf) throws Exception;

	/**
	 * 新增
	 * 
	 * @param inf
	 * @return
	 */
	public Json add(TAppWeekPaper inf) throws Exception;

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
	public Json modify(TAppWeekPaper inf);

	/**
	 * 信息验证
	 * 
	 * @param inf
	 * @return
	 */
	public Json verifyInfo(TAppWeekPaper inf);

	/**
	 * 获取下拉框数据
	 * @return
	 */
	public List<TAppWeekPaper> getList();

	
}
