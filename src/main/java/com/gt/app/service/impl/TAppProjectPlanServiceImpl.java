package com.gt.app.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gt.utils.Contans;
import com.gt.utils.MyBeanUtils;
import org.springframework.stereotype.Service;
import com.gt.app.service.ITAppProjectPlanService;
import com.gt.model.TAppProjectPlan;
import com.gt.pageModel.DatagridForLayUI;
import com.gt.pageModel.Json;
import com.gt.sys.service.impl.BaseServiceImpl;
import com.gt.utils.PbUtils;
import javax.transaction.*;
import org.springframework.beans.factory.annotation.*;
import com.gt.app.dao.ITAppProjectPlanDao;
/**
 *
 * @功能说明：项目计划-里程碑业务接口实现类
 * @作者： zm
 * @创建日期：2020-04-11
 * @版本号：V1.0
 */
@Service("tAppProjectPlanService")
public class TAppProjectPlanServiceImpl extends BaseServiceImpl<TAppProjectPlan> implements ITAppProjectPlanService {

    //定义DAO
	@Autowired
	private ITAppProjectPlanDao TAppProjectPlanDao;

	@Override
	public DatagridForLayUI datagrid(TAppProjectPlan tAppProjectPlan) throws Exception{
		DatagridForLayUI grid = new DatagridForLayUI();
		String sqlLeft = "select a.dict_nm as piscompleteNm," +
                " t.ID as id,t.PProjectid as pprojectid,t.PPlanname as pplanname,t.PPlanmanhour as pplanmanhour,t.PPlanorder as pplanorder,t.PIscomplete as piscomplete,t.PSummanhour as psummanhour,date_format(t.PCreatedate,'%Y-%c-%d %h:%i:%s') as pcreatedate,date_format(t.PCompletedate,'%Y-%c-%d %h:%i:%s') as pcompletedate " ;
		String sql = " from t_app_project_plan t " +
				" left join t_sys_dict_cd a on t.PIscomplete=a.dict_cd " +
				" and a.dict_type_cd='is_complete' " +
				" where 1=1 " ;

		Map<String, Object> param = new HashMap<String, Object>();

		// ID
		if (!PbUtils.isEmpty(tAppProjectPlan.getId())) {
			sql += " and t.ID like:id";
			param.put("id", "%%" + tAppProjectPlan.getId() + "%%");
		}
		// 项目ID
		if (!PbUtils.isEmpty(tAppProjectPlan.getPprojectid())) {
			sql += " and t.PProjectid like:pprojectid";
			param.put("pprojectid", "%%" + tAppProjectPlan.getPprojectid() + "%%");
		}
		// 里程碑名称
		if (!PbUtils.isEmpty(tAppProjectPlan.getPplanname())) {
			sql += " and t.PPlanname like:pplanname";
			param.put("pplanname", "%%" + tAppProjectPlan.getPplanname() + "%%");
		}
		// 里程碑工时预估
		if (!PbUtils.isEmpty(tAppProjectPlan.getPplanmanhour())) {
			sql += " and t.PPlanmanhour like:pplanmanhour";
			param.put("pplanmanhour", "%%" + tAppProjectPlan.getPplanmanhour() + "%%");
		}
		// 里程碑顺序
		if (!PbUtils.isEmpty(tAppProjectPlan.getPplanorder())) {
			sql += " and t.PPlanorder like:pplanorder";
			param.put("pplanorder", "%%" + tAppProjectPlan.getPplanorder() + "%%");
		}
		// 是否完成
		if (!PbUtils.isEmpty(tAppProjectPlan.getPiscomplete())) {
			sql += " and t.PIscomplete like:piscomplete";
			param.put("piscomplete", "%%" + tAppProjectPlan.getPiscomplete() + "%%");
		}
		// 里程碑确认工时
		if (!PbUtils.isEmpty(tAppProjectPlan.getPsummanhour())) {
			sql += " and t.PSummanhour like:psummanhour";
			param.put("psummanhour", "%%" + tAppProjectPlan.getPsummanhour() + "%%");
		}
		// 创建时间
		if (!PbUtils.isEmpty(tAppProjectPlan.getPcreatedate())) {
			sql += " and t.PCreatedate like:pcreatedate";
			param.put("pcreatedate", "%%" + tAppProjectPlan.getPcreatedate() + "%%");
		}
		// 完成时间
		if (!PbUtils.isEmpty(tAppProjectPlan.getPcompletedate())) {
			sql += " and t.PCompletedate like:pcompletedate";
			param.put("pcompletedate", "%%" + tAppProjectPlan.getPcompletedate() + "%%");
		}


		String totalsql = "select count(*) " + sql;

		//排序
		sql += " order by  PPlanorder asc";

		List<Map> maps = findBySql(sqlLeft+sql, param, tAppProjectPlan.getPage(), tAppProjectPlan.getLimit());
		Long total = countBySql(totalsql,param);
		grid.setCount(total);
		grid.setData(maps);

		return grid;
	}

    @Transactional
	@Override
	public Json add(TAppProjectPlan inf) {
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
		String sql = "delete from t_app_project_plan  where ID in (";
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
	public void delByProjectId(String ids) {
		String[] nids = ids.split(",");
		String sql = "delete from t_app_project_plan  where PProjectid in (";
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
	public Json modify(TAppProjectPlan inf) {
		Json j = new Json();

		TAppProjectPlan tInf = getById(TAppProjectPlan.class, inf.getId());
		if (tInf == null) {
			j.setSuccess(false);
			j.setMsg("修改失败：找不到要修改的信息");
			return j;
		}

		// 旧对象
		TAppProjectPlan oldObject = new TAppProjectPlan();
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
	public Json verifyInfo(TAppProjectPlan inf) {
		Json j = new Json();
		Map<String, Object> params= new HashMap<String, Object>();
		if (!PbUtils.isEmpty(inf.getId())) {
			params.put("id", inf.getId());
		}
		Long total = super.count("Select count(*) from TAppProjectPlan t where t.Id =:id ", params);

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
	public List<TAppProjectPlan> getList() {
		List<TAppProjectPlan> l = find("from TAppProjectPlan t");
		return l;
	}

	@Override
	public List<TAppProjectPlan> getListPlanByProjectid(String pprojectid,String iscomplete) {
		String hql = "from TAppProjectPlan t where t.pprojectid=:projectid";
		Map<String,Object> params = new HashMap<>();
		params.put("projectid",pprojectid);
		if (!PbUtils.isEmpty(iscomplete)) {
			hql += " and t.piscomplete=:iscomplete";
			params.put("iscomplete", iscomplete);
		}

		List<TAppProjectPlan> l = find(hql,params);
		return l;
	}

	@Override
	public Long countByProjectId(String projectid,String iscomplete) {
		String sql = "select count(1) " +
				" from t_app_project_plan " +
                " where PProjectid=:projectid ";
		Map<String,Object> params = new HashMap<>();
		params.put("projectid",projectid);

		if (!PbUtils.isEmpty(iscomplete)) {
			sql += " and PIscomplete=:iscomplete";
			params.put("iscomplete",iscomplete);
		}
		return countBySql(sql,params);
	}
}
