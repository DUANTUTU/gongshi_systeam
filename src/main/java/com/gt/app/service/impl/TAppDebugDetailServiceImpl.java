package com.gt.app.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gt.pageModel.BasePageForLayUI;
import com.gt.pageModel.OperInf;
import com.gt.utils.MyBeanUtils;
import org.springframework.stereotype.Service;
import com.gt.app.service.ITAppDebugDetailService;
import com.gt.model.TAppDebugDetail;
import com.gt.pageModel.DatagridForLayUI;
import com.gt.pageModel.Json;
import com.gt.sys.service.impl.BaseServiceImpl;
import com.gt.utils.PbUtils;
import javax.transaction.*;
import org.springframework.beans.factory.annotation.*;
import com.gt.app.dao.ITAppDebugDetailDao;
/**
 * 
 * @功能说明：debug记录表业务接口实现类
 * @作者： liutaok
 * @创建日期：2023-04-27
 * @版本号：V1.0
 */
@Service("tAppDebugDetailService")
public class TAppDebugDetailServiceImpl extends BaseServiceImpl<TAppDebugDetail> implements ITAppDebugDetailService {

    //定义DAO
	@Autowired
	private ITAppDebugDetailDao TAppDebugDetailDao;

	@Override
	public DatagridForLayUI datagrid(TAppDebugDetail tAppDebugDetail) throws Exception{
		DatagridForLayUI grid = new DatagridForLayUI();
		String sqlLeft = "select t.ID as id,t.MProjectid as mprojectid,t.MProjectplanid as mprojectplanid,t.MOpercd as mopercd,t.MWorkdetails as mworkdetails,t.MManhour as mmanhour,t.MCheckstatus as mcheckstatus,t.MCreatedate as mcreatedate,t.MCheckdate as mcheckdate ,t.MConfirm_id as mconfirmId" ;
		String sql = " from t_app_debug_detail t where 1=1 " ;



		
		Map<String, Object> param = new HashMap<String, Object>();
		
		// ID
		if (!PbUtils.isEmpty(tAppDebugDetail.getId())) {
			sql += " and t.ID like:id";
			param.put("id", "%%" + tAppDebugDetail.getId() + "%%");
		}
		// 项目ID
		if (!PbUtils.isEmpty(tAppDebugDetail.getMprojectid())) {
			sql += " and t.MProjectid like:mprojectid";
			param.put("mprojectid", "%%" + tAppDebugDetail.getMprojectid() + "%%");
		}
		// 项目里程碑ID
		if (!PbUtils.isEmpty(tAppDebugDetail.getMprojectplanid())) {
			sql += " and t.MProjectplanid like:mprojectplanid";
			param.put("mprojectplanid", "%%" + tAppDebugDetail.getMprojectplanid() + "%%");
		}
		// 人员id
		if (!PbUtils.isEmpty(tAppDebugDetail.getMopercd())) {
			sql += " and t.MOpercd like:mopercd";
			param.put("mopercd", "%%" + tAppDebugDetail.getMopercd() + "%%");
		}
		// 工作内容
		if (!PbUtils.isEmpty(tAppDebugDetail.getMworkdetails())) {
			sql += " and t.MWorkdetails like:mworkdetails";
			param.put("mworkdetails", "%%" + tAppDebugDetail.getMworkdetails() + "%%");
		}
		// 工时
		if (!PbUtils.isEmpty(tAppDebugDetail.getMmanhour())) {
			sql += " and t.MManhour like:mmanhour";
			param.put("mmanhour", "%%" + tAppDebugDetail.getMmanhour() + "%%");
		}
		// 审核状态
		if (!PbUtils.isEmpty(tAppDebugDetail.getMcheckstatus())) {
			sql += " and t.MCheckstatus like:mcheckstatus";
			param.put("mcheckstatus", "%%" + tAppDebugDetail.getMcheckstatus() + "%%");
		}
		// 创建时间
		if (!PbUtils.isEmpty(tAppDebugDetail.getMcreatedate())) {
			sql += " and t.MCreatedate like:mcreatedate";
			param.put("mcreatedate", "%%" + tAppDebugDetail.getMcreatedate() + "%%");
		}
		// 审核时间
		if (!PbUtils.isEmpty(tAppDebugDetail.getMcheckdate())) {
			sql += " and t.MCheckdate like:mcheckdate";
			param.put("mcheckdate", "%%" + tAppDebugDetail.getMcheckdate() + "%%");
		}
		
		
		String totalsql = "select count(*) " + sql;

		//排序
		sql += " order by  ID desc";
	
		List<Map> maps = findBySql(sqlLeft+sql, param, tAppDebugDetail.getPage(), tAppDebugDetail.getLimit());
		Long total = countBySql(totalsql,param);
		grid.setCount(total);
		grid.setData(maps);

		return grid;
	}

	@Override
	public DatagridForLayUI datagridByUserid(TAppDebugDetail tAppDebugDetail, String userId) throws Exception {
		DatagridForLayUI grid = new DatagridForLayUI();
		String sqlLeft = "select t.ID as id,t.MProjectid as mprojectid,t.MProjectplanid as mprojectplanid,t.MOpercd as mopercd,t.MWorkdetails as mworkdetails,t.MManhour as mmanhour,t.MCheckstatus as mcheckstatus,t.MCreatedate as mcreatedate,t.MCheckdate as mcheckdate,t.debug_finish_date as debugFinishDate,t.MConfirm_id as mconfirmId " ;
		String sql = " from t_app_debug_detail t where 1=1 " ;
		if (!PbUtils.isEmpty(userId)) {
			sql += " and t.MOpercd =\""+userId+"\"";
		}



		Map<String, Object> param = new HashMap<String, Object>();

		// ID
		if (!PbUtils.isEmpty(tAppDebugDetail.getId())) {
			sql += " and t.ID like:id";
			param.put("id", "%%" + tAppDebugDetail.getId() + "%%");
		}
		// 项目ID
		if (!PbUtils.isEmpty(tAppDebugDetail.getMprojectid())) {
			sql += " and t.MProjectid like:mprojectid";
			param.put("mprojectid", "%%" + tAppDebugDetail.getMprojectid() + "%%");
		}
		// 项目里程碑ID
		if (!PbUtils.isEmpty(tAppDebugDetail.getMprojectplanid())) {
			sql += " and t.MProjectplanid like:mprojectplanid";
			param.put("mprojectplanid", "%%" + tAppDebugDetail.getMprojectplanid() + "%%");
		}
		// 人员id
		if (!PbUtils.isEmpty(tAppDebugDetail.getMopercd())) {
			sql += " and t.MOpercd like:mopercd";
			param.put("mopercd", "%%" + tAppDebugDetail.getMopercd() + "%%");
		}
		// 工作内容
		if (!PbUtils.isEmpty(tAppDebugDetail.getMworkdetails())) {
			sql += " and t.MWorkdetails like:mworkdetails";
			param.put("mworkdetails", "%%" + tAppDebugDetail.getMworkdetails() + "%%");
		}
		// 工时
		if (!PbUtils.isEmpty(tAppDebugDetail.getMmanhour())) {
			sql += " and t.MManhour like:mmanhour";
			param.put("mmanhour", "%%" + tAppDebugDetail.getMmanhour() + "%%");
		}
		// 审核状态
		if (!PbUtils.isEmpty(tAppDebugDetail.getMcheckstatus())) {
			sql += " and t.MCheckstatus like:mcheckstatus";
			param.put("mcheckstatus", "%%" + tAppDebugDetail.getMcheckstatus() + "%%");
		}
		// 创建时间
		if (!PbUtils.isEmpty(tAppDebugDetail.getMcreatedate())) {
			sql += " and t.MCreatedate like:mcreatedate";
			param.put("mcreatedate", "%%" + tAppDebugDetail.getMcreatedate() + "%%");
		}
		// 审核时间
		if (!PbUtils.isEmpty(tAppDebugDetail.getMcheckdate())) {
			sql += " and t.MCheckdate like:mcheckdate";
			param.put("mcheckdate", "%%" + tAppDebugDetail.getMcheckdate() + "%%");
		}


		String totalsql = "select count(*) " + sql;

		//排序
		sql += " order by  MCreatedate desc";

		List<Map> maps = findBySql(sqlLeft+sql, param, tAppDebugDetail.getPage(), tAppDebugDetail.getLimit());
		Long total = countBySql(totalsql,param);
		grid.setCount(total);
		grid.setData(maps);

		return grid;
	}

	@Transactional
	@Override
	public Json add(TAppDebugDetail inf) {
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
		String sql = "delete from t_app_debug_detail  where ID in (";
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
	public Json modify(TAppDebugDetail inf) {
		Json j = new Json();

		TAppDebugDetail tInf = getById(TAppDebugDetail.class, inf.getId());
		if (tInf == null) {
			j.setSuccess(false);
			j.setMsg("修改失败：找不到要修改的信息");
			return j;
		}

		// 旧对象
		TAppDebugDetail oldObject = new TAppDebugDetail();
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
	public Json verifyInfo(TAppDebugDetail inf) {
		Json j = new Json();
		Map<String, Object> params= new HashMap<String, Object>();
		if (!PbUtils.isEmpty(inf.getId())) {
			params.put("id", inf.getId());
		}
		Long total = super.count("Select count(*) from TAppDebugDetail t where t.Id =:id ", params);

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
	public List<TAppDebugDetail> getList() {
		List<TAppDebugDetail> l = find("from TAppDebugDetail t");
		return l;
	}

	/*
	* 获取data数据by
	* */
}
