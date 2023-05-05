package com.gt.app.service;

import java.util.List;
import com.gt.model.TAppDebugDetail;
import com.gt.pageModel.DatagridForLayUI;
import com.gt.pageModel.Json;
import com.gt.sys.service.IBaseService;

/**
 * @功能说明：debug记录表业务接口类
 * @作者： liutaok
 * @创建日期：2023-04-27
 * @版本号：V1.0
 */
public interface ITAppDebugDetailService extends IBaseService<TAppDebugDetail> {

	/**
	 * 获取datagrid数据
	 * 
	 * @return
	 */
	public DatagridForLayUI datagrid(TAppDebugDetail inf) throws Exception;
	/*
	* 获取datagrid数据，通过uerid
	* */
	public DatagridForLayUI datagridByUserid(TAppDebugDetail inf,String userId) throws Exception;
	/**
	 * 新增
	 * 
	 * @param inf
	 * @return
	 */
	public Json add(TAppDebugDetail inf) throws Exception;

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
	public Json modify(TAppDebugDetail inf);

	/**
	 * 信息验证
	 * 
	 * @param inf
	 * @return
	 */
	public Json verifyInfo(TAppDebugDetail inf);

	/**
	 * 获取下拉框数据
	 * @return
	 */
	public List<TAppDebugDetail> getList();

	
}
