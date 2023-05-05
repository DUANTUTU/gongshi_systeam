package com.gt.app.service;

import java.util.List;
import java.util.Map;

import com.gt.model.TAppDayPaper;
import com.gt.pageModel.DatagridForLayUI;
import com.gt.pageModel.Json;
import com.gt.sys.service.IBaseService;

/**
 * @功能说明：日报业务接口类
 * @作者： zm
 * @创建日期：2020-04-12
 * @版本号：V1.0
 */
public interface ITAppDayPaperService extends IBaseService<TAppDayPaper> {

	/**
	 * 获取datagrid数据
	 * 
	 * @return
	 */
	public DatagridForLayUI datagrid(TAppDayPaper inf) throws Exception;

	/**
	 * 新增
	 * 
	 * @param inf
	 * @return
	 */
	public Json add(TAppDayPaper inf) throws Exception;

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
	public Json modify(TAppDayPaper inf);

	/**
	 * 信息验证
	 * 
	 * @param inf
	 * @return
	 */
	public Json verifyInfo(TAppDayPaper inf);

	/**
	 * 获取下拉框数据
	 * @return
	 */
	public List<TAppDayPaper> getList();

	/**
	 * 查询时间区间每个用户的日报统计
	 * */
	public List<Map> getCountManhourByDate(String leftDate, String rightDate);
	/**
	 * 获取统计人员确认工时情况
	 * */
	public List<Map> getPersonManhourEcharts();

	
}
