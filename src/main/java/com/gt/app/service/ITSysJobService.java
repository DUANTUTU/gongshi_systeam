package com.gt.app.service;

import java.util.List;
import com.gt.model.TSysJob;
import com.gt.pageModel.DatagridForLayUI;
import com.gt.pageModel.Json;
import com.gt.sys.service.IBaseService;

/**
 * @功能说明：职位管理业务接口类
 * @作者： zm
 * @创建日期：2020-04-11
 * @版本号：V1.0
 */
public interface ITSysJobService extends IBaseService<TSysJob> {

	/**
	 * 获取datagrid数据
	 * 
	 * @return
	 */
	public DatagridForLayUI datagrid(TSysJob inf) throws Exception;

	/**
	 * 新增
	 * 
	 * @param inf
	 * @return
	 */
	public Json add(TSysJob inf) throws Exception;

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
	public Json modify(TSysJob inf);

	/**
	 * 信息验证
	 * 
	 * @param inf
	 * @return
	 */
	public Json verifyInfo(TSysJob inf);

	/**
	 * 获取下拉框数据
	 * @return
	 */
	public List<TSysJob> getList();
/**
 * 根据ID获取实体
 * */
	public TSysJob getEntityById(String Id);
}
