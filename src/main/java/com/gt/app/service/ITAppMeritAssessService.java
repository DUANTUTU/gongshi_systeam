package com.gt.app.service;

import java.util.List;
import java.util.Map;

import com.gt.model.TAppMeritAssess;
import com.gt.pageModel.DatagridForLayUI;
import com.gt.pageModel.Json;
import com.gt.sys.service.IBaseService;

/**
 * @功能说明：绩效评估业务接口类
 * @作者： zm
 * @创建日期：2020-04-13
 * @版本号：V1.0
 */
public interface ITAppMeritAssessService extends IBaseService<TAppMeritAssess> {

	/**
	 * 获取datagrid数据
	 * 
	 * @return
	 */
	public DatagridForLayUI datagrid(TAppMeritAssess inf) throws Exception;

	/**
	 * 新增
	 * 
	 * @param inf
	 * @return
	 */
	public Json add(TAppMeritAssess inf) throws Exception;

	/**
	 * 删除
	 * 
	 * @param ids
	 */
	public void remove(String ids);
	/**
	 * 删除
	 *
	 * @param leftDay
	 * @param rightDay
	 */
	public void remove(String leftDay, String rightDay);
	/**
	 * 修改
	 * 
	 * @param menuInf
	 */
	public Json modify(TAppMeritAssess inf);

	/**
	 * 信息验证
	 * 
	 * @param inf
	 * @return
	 */
	public Json verifyInfo(TAppMeritAssess inf);

	/**
	 * 获取下拉框数据
	 * @return
	 */
	public List<TAppMeritAssess> getList();

	/**
	 * 绩效情况统计
	 * */
	public List<Map> getMeritAssessEcharts(String sJobId,String leftDate,String rightDate);
}
