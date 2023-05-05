package com.gt.app.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.gt.model.TAppManhourConfirm;
import com.gt.pageModel.DatagridForLayUI;
import com.gt.pageModel.Json;
import com.gt.sys.service.IBaseService;

/**
 * @功能说明：工时提报业务接口类
 * @作者： zm
 * @创建日期：2020-04-12
 * @版本号：V1.0
 */
public interface ITAppManhourConfirmService extends IBaseService<TAppManhourConfirm> {

	/**
	 * 获取datagrid数据
	 * 
	 * @return
	 */
	public DatagridForLayUI datagrid(TAppManhourConfirm inf,String createProOpercd,String insId,String jobType) throws Exception;

	/**
	 * 新增
	 * 
	 * @param inf
	 * @return
	 */
	public Json add(TAppManhourConfirm inf) throws Exception;

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
	public Json modify(TAppManhourConfirm inf);

	/**
	 * 信息验证
	 * 
	 * @param inf
	 * @return
	 */
	public Json verifyInfo(TAppManhourConfirm inf);

	/**
	 * 获取下拉框数据
	 * @return
	 */
	public List<TAppManhourConfirm> getList();

	/**
	 * 获取总提报工时
	 * insId机构ID
	 * operCd人员ID
	 * */
	public Long queryAllManhourConfirm(String insId,String operCd);
	/**
	 * 人员7日工时统计
	 * */
	public List<Map> echartsAccount7DayManhour(TAppManhourConfirm inf, Date leftDate, Date rightDate);

	
}
