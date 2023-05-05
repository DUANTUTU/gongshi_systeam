package com.gt.app.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.gt.utils.MyBeanUtils;
import org.springframework.stereotype.Service;
import com.gt.app.service.ITest3Service;
import com.gt.model.Test3;
import com.gt.pageModel.DatagridForLayUI;
import com.gt.pageModel.Json;
import com.gt.sys.service.impl.BaseServiceImpl;
import com.gt.utils.PbUtils;
import javax.transaction.*;
import org.springframework.beans.factory.annotation.*;
import com.gt.app.dao.ITest3Dao;
/**
 * 
 * @功能说明：测试业务接口实现类
 * @作者： liutaok
 * @创建日期：2018-12-17
 * @版本号：V1.0
 */
@Service("test3Service")
public class Test3ServiceImpl extends BaseServiceImpl<Test3> implements ITest3Service {

    //定义DAO
	@Autowired
	private ITest3Dao Test3Dao;

	@Override
	public DatagridForLayUI datagrid(Test3 test3) throws Exception{
		DatagridForLayUI grid = new DatagridForLayUI();
		String sqlLeft = "select t.ID as id,t.NAME as name,t.SEX as sex " ;
		String sql = " from test3 t where 1=1 " ;
		
		Map<String, Object> param = new HashMap<String, Object>();
		
		// 编码
		if (!PbUtils.isEmpty(test3.getId())) {
			sql += " and t.ID like:id";
			param.put("id", "%%" + test3.getId() + "%%");
		}
		// 姓名
		if (!PbUtils.isEmpty(test3.getName())) {
			sql += " and t.NAME like:name";
			param.put("name", "%%" + test3.getName() + "%%");
		}
		// 性别
		if (!PbUtils.isEmpty(test3.getSex())) {
			sql += " and t.SEX like:sex";
			param.put("sex", "%%" + test3.getSex() + "%%");
		}
		
		
		String totalsql = "select count(*) " + sql;

		//排序
		sql += " order by  ID desc";
	
		List<Map> maps = findBySql(sqlLeft+sql, param, test3.getPage(), test3.getLimit());
		Long total = countBySql(totalsql,param);
		grid.setCount(total);
		grid.setData(maps);

		return grid;
	}

    @Transactional
	@Override
	public Json add(Test3 inf) {
		Json j = new Json();
		save(inf);
		j.setSuccess(true);
		j.setMsg("新增成功");
		j.setObj(inf);	// 设置返回对象
		return j;

	}

    @Transactional
	@Override
	public void remove(String ids) {
		String[] nids = ids.split(",");
		String sql = "delete from test3  where ID in (";
		for (int i = 0; i < nids.length; i++) {
			if (i > 0) {
				sql += ",";
			}
			sql += "'" + nids[i] + "'";
		}
		sql += ")";
		executeSql(sql);

	}

    @Transactional
	@Override
	public Json modify(Test3 inf) {
		Json j = new Json();

		Test3 tInf = getById(Test3.class, inf.getId());
		if (tInf == null) {
			j.setSuccess(false);
			j.setMsg("修改失败：找不到要修改的信息");
			return j;
		}

		// 旧对象
		Test3 oldObject = new Test3();
		MyBeanUtils.copyProperties(tInf, oldObject);
		j.setOldObj(oldObject);
		
		MyBeanUtils.copyProperties(inf, tInf);
		update(tInf);// 更新
		j.setSuccess(true);
		j.setMsg("更新成功");
		j.setObj(tInf);// 设置返回对象
		return j;
	}

	
	@Override
	public Json verifyInfo(Test3 inf) {
		Json j = new Json();
		Map<String, Object> params= new HashMap<String, Object>();
		if (!PbUtils.isEmpty(inf.getId())) {
			params.put("id", inf.getId());
		}
		Long total = super.count("Select count(*) from Test3 t where t.Id =:id ", params);

		if (total > 0) {
			j.setSuccess(false);
			j.setMsg("此信息已经存在");
			return j;
		}

		j.setSuccess(true);
		j.setMsg("成功！");
		j.setObj(inf);
		return j;
	}

	@Override
	public List<Test3> getList() {
		List<Test3> l = find("from Test3 t");
		return l;
	}
}
