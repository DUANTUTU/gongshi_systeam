package com.gt.app.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.gt.utils.MyBeanUtils;
import org.springframework.stereotype.Service;
import com.gt.app.service.ITAppProjectAssessService;
import com.gt.model.TAppProjectAssess;
import com.gt.pageModel.DatagridForLayUI;
import com.gt.pageModel.Json;
import com.gt.sys.service.impl.BaseServiceImpl;
import com.gt.utils.PbUtils;
import javax.transaction.*;
import org.springframework.beans.factory.annotation.*;
import com.gt.app.dao.ITAppProjectAssessDao;
/**
 * 
 * @功能说明：项目工时评估业务接口实现类
 * @作者： zm
 * @创建日期：2020-04-13
 * @版本号：V1.0
 */
@Service("tAppProjectAssessService")
public class TAppProjectAssessServiceImpl extends BaseServiceImpl<TAppProjectAssess> implements ITAppProjectAssessService {

    //定义DAO
	@Autowired
	private ITAppProjectAssessDao TAppProjectAssessDao;

	@Override
	public DatagridForLayUI datagrid(TAppProjectAssess tAppProjectAssess) throws Exception{
		DatagridForLayUI grid = new DatagridForLayUI();
		String sqlLeft = "select t.ID as id,t.PProjectid as pprojectid,t.PCompleterate as pcompleterate,t.PFinishhour as pfinishhour,t.PFinishhourrate as pfinishhourrate,t.PUnfinishhour as punfinishhour,t.PCreatedate as pcreatedate " ;
		String sql = " from t_app_project_assess t " +
				" left join " +
				"" +
				"" +
				" where 1=1 " ;
		
		Map<String, Object> param = new HashMap<String, Object>();
		
		// ID
		if (!PbUtils.isEmpty(tAppProjectAssess.getId())) {
			sql += " and t.ID like:id";
			param.put("id", "%%" + tAppProjectAssess.getId() + "%%");
		}
		// 项目ID
		if (!PbUtils.isEmpty(tAppProjectAssess.getPprojectid())) {
			sql += " and t.PProjectid like:pprojectid";
			param.put("pprojectid", "%%" + tAppProjectAssess.getPprojectid() + "%%");
		}
		// 项目完成率
		if (!PbUtils.isEmpty(tAppProjectAssess.getPcompleterate())) {
			sql += " and t.PCompleterate like:pcompleterate";
			param.put("pcompleterate", "%%" + tAppProjectAssess.getPcompleterate() + "%%");
		}
		// 实际工时
		if (!PbUtils.isEmpty(tAppProjectAssess.getPfinishhour())) {
			sql += " and t.PFinishhour like:pfinishhour";
			param.put("pfinishhour", "%%" + tAppProjectAssess.getPfinishhour() + "%%");
		}
		// 实际工时比计划工时
		if (!PbUtils.isEmpty(tAppProjectAssess.getPfinishhourrate())) {
			sql += " and t.PFinishhourrate like:pfinishhourrate";
			param.put("pfinishhourrate", "%%" + tAppProjectAssess.getPfinishhourrate() + "%%");
		}
		// 未完成工时
		if (!PbUtils.isEmpty(tAppProjectAssess.getPunfinishhour())) {
			sql += " and t.PUnfinishhour like:punfinishhour";
			param.put("punfinishhour", "%%" + tAppProjectAssess.getPunfinishhour() + "%%");
		}
		// 创建时间
		if (!PbUtils.isEmpty(tAppProjectAssess.getPcreatedate())) {
			sql += " and t.PCreatedate like:pcreatedate";
			param.put("pcreatedate", "%%" + tAppProjectAssess.getPcreatedate() + "%%");
		}
		
		
		String totalsql = "select count(*) " + sql;

		//排序
		sql += " order by  ID desc";
	
		List<Map> maps = findBySql(sqlLeft+sql, param, tAppProjectAssess.getPage(), tAppProjectAssess.getLimit());
		Long total = countBySql(totalsql,param);
		grid.setCount(total);
		grid.setData(maps);

		return grid;
	}

    @Transactional
	@Override
	public Json add(TAppProjectAssess inf) {
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
		String sql = "delete from t_app_project_assess  where ID in (";
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
	public Json modify(TAppProjectAssess inf) {
		Json j = new Json();

		TAppProjectAssess tInf = getById(TAppProjectAssess.class, inf.getId());
		if (tInf == null) {
			j.setSuccess(false);
			j.setMsg("修改失败：找不到要修改的信息");
			return j;
		}

		// 旧对象
		TAppProjectAssess oldObject = new TAppProjectAssess();
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
	public Json verifyInfo(TAppProjectAssess inf) {
		Json j = new Json();
		Map<String, Object> params= new HashMap<String, Object>();
		if (!PbUtils.isEmpty(inf.getId())) {
			params.put("id", inf.getId());
		}
		Long total = super.count("Select count(*) from TAppProjectAssess t where t.Id =:id ", params);

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
	public List<TAppProjectAssess> getList() {
		List<TAppProjectAssess> l = find("from TAppProjectAssess t");
		return l;
	}
}
