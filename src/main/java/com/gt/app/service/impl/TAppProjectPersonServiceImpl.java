package com.gt.app.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gt.model.TAppProject;
import com.gt.model.TSysRoleFunction;
import com.gt.pageModel.RoleFunction;
import com.gt.utils.Contans;
import com.gt.utils.MyBeanUtils;
import org.springframework.stereotype.Service;
import com.gt.app.service.ITAppProjectPersonService;
import com.gt.model.TAppProjectPerson;
import com.gt.pageModel.DatagridForLayUI;
import com.gt.pageModel.Json;
import com.gt.sys.service.impl.BaseServiceImpl;
import com.gt.utils.PbUtils;
import javax.transaction.*;
import org.springframework.beans.factory.annotation.*;
import com.gt.app.dao.ITAppProjectPersonDao;
/**
 * 
 * @功能说明：项目人员表业务接口实现类
 * @作者： zm
 * @创建日期：2020-04-11
 * @版本号：V1.0
 */
@Service("tAppProjectPersonService")
public class TAppProjectPersonServiceImpl extends BaseServiceImpl<TAppProjectPerson> implements ITAppProjectPersonService {

    //定义DAO
	@Autowired
	private ITAppProjectPersonDao TAppProjectPersonDao;

	@Override
	public DatagridForLayUI datagrid(TAppProjectPerson tAppProjectPerson) throws Exception{
		DatagridForLayUI grid = new DatagridForLayUI();
		String sqlLeft = "select a.OPER_NM as popercdNm,b.dict_nm as pappointstatusNm,c.INS_NM as pinscdNm, " +
				" t.ID as id,t.POpercd as popercd,t.PInscd as pinscd,t.PProjectid as pprojectid,t.PAppointstatus as pappointstatus,t.PManhour as pmanhour,date_format(t.PCreatedate,'%Y-%c-%d %h:%i:%s')  as pcreatedate,date_format(t.PConfirmdate,'%Y-%c-%d %h:%i:%s') as pconfirmdate " ;
		String sql = " from t_app_project_person t " +
				" left join t_sys_oper_inf a on a.OPER_CD=t.POpercd " +
				" left join t_sys_ins_inf c on t.PInscd=c.UUID " +
				" left join t_sys_dict_cd b on t.PAppointstatus=b.dict_cd " +
				" and b.dict_type_cd='appoint_status' " +
				" where 1=1 " ;
		
		Map<String, Object> param = new HashMap<String, Object>();
		
		// ID
		if (!PbUtils.isEmpty(tAppProjectPerson.getId())) {
			sql += " and t.ID like:id";
			param.put("id", "%%" + tAppProjectPerson.getId() + "%%");
		}
		// 人员ID
		if (!PbUtils.isEmpty(tAppProjectPerson.getPopercd())) {
			sql += " and t.POpercd like:popercd";
			param.put("popercd", "%%" + tAppProjectPerson.getPopercd() + "%%");
		}
		// 部门ID
		if (!PbUtils.isEmpty(tAppProjectPerson.getPinscd())) {
			sql += " and t.PInscd like:pinscd";
			param.put("pinscd", "%%" + tAppProjectPerson.getPinscd() + "%%");
		}
		// 项目ID
		if (!PbUtils.isEmpty(tAppProjectPerson.getPprojectid())) {
			sql += " and t.PProjectid like:pprojectid";
			param.put("pprojectid", "%%" + tAppProjectPerson.getPprojectid() + "%%");
		}
		// 委派状态
		if (!PbUtils.isEmpty(tAppProjectPerson.getPappointstatus())) {
			sql += " and t.PAppointstatus like:pappointstatus";
			param.put("pappointstatus", "%%" + tAppProjectPerson.getPappointstatus() + "%%");
		}
		// 委派工时
		if (!PbUtils.isEmpty(tAppProjectPerson.getPmanhour())) {
			sql += " and t.PManhour like:pmanhour";
			param.put("pmanhour", "%%" + tAppProjectPerson.getPmanhour() + "%%");
		}
		// 创建时间
		if (!PbUtils.isEmpty(tAppProjectPerson.getPcreatedate())) {
			sql += " and t.PCreatedate like:pcreatedate";
			param.put("pcreatedate", "%%" + tAppProjectPerson.getPcreatedate() + "%%");
		}
		// 确认时间
		if (!PbUtils.isEmpty(tAppProjectPerson.getPconfirmdate())) {
			sql += " and t.PConfirmdate like:pconfirmdate";
			param.put("pconfirmdate", "%%" + tAppProjectPerson.getPconfirmdate() + "%%");
		}
		
		
		String totalsql = "select count(*) " + sql;

		//排序
		sql += " order by  ID desc";
	
		List<Map> maps = findBySql(sqlLeft+sql, param, tAppProjectPerson.getPage(), tAppProjectPerson.getLimit());
		Long total = countBySql(totalsql,param);
		grid.setCount(total);
		grid.setData(maps);

		return grid;
	}

    @Transactional
	@Override
	public Json add(TAppProjectPerson inf) {
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
		String sql = "delete from t_app_project_person  where ID in (";
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
	public Json modify(TAppProjectPerson inf) {
		Json j = new Json();

		TAppProjectPerson tInf = getById(TAppProjectPerson.class, inf.getId());
		if (tInf == null) {
			j.setSuccess(false);
			j.setMsg("修改失败：找不到要修改的信息");
			return j;
		}

		// 旧对象
		TAppProjectPerson oldObject = new TAppProjectPerson();
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
	public Json verifyInfo(TAppProjectPerson inf) {
		Json j = new Json();
		Map<String, Object> params= new HashMap<String, Object>();
		if (!PbUtils.isEmpty(inf.getId())) {
			params.put("id", inf.getId());
		}
		Long total = super.count("Select count(*) from TAppProjectPerson t where t.Id =:id ", params);

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
	public List<TAppProjectPerson> getList() {
		List<TAppProjectPerson> l = find("from TAppProjectPerson t");
		return l;
	}

	@Override
	public List<Map> getProjectListByOpercd(TAppProjectPerson inf) {
		String sql = "select t.PProjectid as pprojectid,a.PName as pname,," +
				" from t_app_project_person t " +
				" left join t_app_project a on t.PProjectid=a.ID " +
				" and a.PIscomplete="+ Contans.IS_COMPLETE_ARRY[0] +
				" and t.PAppointstatus="+Contans.APPOINT_STATUS_ARRY[0] +
				" where 1=1 " +
				" and t.POpercd=:opercd";
		Map<String,Object> params = new HashMap<>();
		params.put("opercd",params);
		List<Map> list = findBySql(sql);
		return list;
	}

	@Override
	public List<Map> getPersonProjectEcharts() {
		String sql = "select count(b.PType) as proAllNum,sum(b.PType='0') as proMasterNum,sum(b.PType='1') as proBranchNum,a.OPER_NM as opernm " +
				"from t_app_project_person t " +
				"join t_sys_oper_inf a  " +
				"left join t_app_project b on b.ID=t.PProjectid " +
				"where a.OPER_CD = t.POpercd " +
				"and a.OPER_ST='0' " +
				"and t.PAppointstatus='0' " +
				"GROUP BY t.POpercd";
		List<Map> list = findBySql(sql);
		return list;
	}

	@Override
	public List<String> getPersonByProjectId(String projectid) {
		List<String> opercdlist=new ArrayList<>();
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = " from TAppProjectPerson t where 1=1  and t.pprojectid=:projectid ";

		params.put("projectid", projectid);

		List<TAppProjectPerson> tList = find(hql, params);
		for (TAppProjectPerson tAppProjectPerson : tList){
			opercdlist.add(tAppProjectPerson.getPopercd());
		}

		return opercdlist ;
	}
}
