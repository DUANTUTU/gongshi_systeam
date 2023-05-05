package com.gt.app.service;

import java.util.List;
import com.gt.model.Test3;
import com.gt.pageModel.DatagridForLayUI;
import com.gt.pageModel.Json;
import com.gt.sys.service.IBaseService;

/**
 * @功能说明：测试业务接口类
 * @作者： liutaok
 * @创建日期：2018-12-17
 * @版本号：V1.0
 */
public interface ITest3Service extends IBaseService<Test3> {

	/**
	 * 获取datagrid数据
	 * 
	 * @return
	 */
	public DatagridForLayUI datagrid(Test3 inf) throws Exception;

	/**
	 * 新增
	 * 
	 * @param inf
	 * @return
	 */
	public Json add(Test3 inf) throws Exception;

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
	public Json modify(Test3 inf);

	/**
	 * 信息验证
	 * 
	 * @param inf
	 * @return
	 */
	public Json verifyInfo(Test3 inf);

	/**
	 * 获取下拉框数据
	 * @return
	 */
	public List<Test3> getList();

	
}
