package com.gt.app.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.gt.utils.MyBeanUtils;
import org.springframework.stereotype.Service;
import com.gt.app.service.ITAppWeekPaperService;
import com.gt.model.TAppWeekPaper;
import com.gt.pageModel.DatagridForLayUI;
import com.gt.pageModel.Json;
import com.gt.sys.service.impl.BaseServiceImpl;
import com.gt.utils.PbUtils;
import javax.transaction.*;
import org.springframework.beans.factory.annotation.*;
import com.gt.app.dao.ITAppWeekPaperDao;
/**
 * 
 * @功能说明：周报业务接口实现类
 * @作者： zm
 * @创建日期：2020-04-12
 * @版本号：V1.0
 */
@Service("tAppWeekPaperService")
public class TAppWeekPaperServiceImpl extends BaseServiceImpl<TAppWeekPaper> implements ITAppWeekPaperService {

    //定义DAO
	@Autowired
	private ITAppWeekPaperDao TAppWeekPaperDao;

	@Override
	public DatagridForLayUI datagrid(TAppWeekPaper tAppWeekPaper) throws Exception{
		DatagridForLayUI grid = new DatagridForLayUI();
		String sqlLeft = "select t.ID as id,t.WOpercd as wopercd,t.WSummanhour as wsummanhour,t.WMaster as wmaster,t.WBranch as wbranch,t.WMasterrate as wmasterrate,t.WBranchrate as wbranchrate,date_format(t.WCreatedate,'%Y-%c-%d %h:%i:%s') as wcreatedate " ;
		String sql = " from t_app_week_paper t where 1=1 " ;
		
		Map<String, Object> param = new HashMap<String, Object>();
		
		// ID
		if (!PbUtils.isEmpty(tAppWeekPaper.getId())) {
			sql += " and t.ID like:id";
			param.put("id", "%%" + tAppWeekPaper.getId() + "%%");
		}
		// 人员ID
		if (!PbUtils.isEmpty(tAppWeekPaper.getWopercd())) {
			sql += " and t.WOpercd like:wopercd";
			param.put("wopercd", "%%" + tAppWeekPaper.getWopercd() + "%%");
		}
		// 工时总计
		if (!PbUtils.isEmpty(tAppWeekPaper.getWsummanhour())) {
			sql += " and t.WSummanhour like:wsummanhour";
			param.put("wsummanhour", "%%" + tAppWeekPaper.getWsummanhour() + "%%");
		}
		// 主线工时
		if (!PbUtils.isEmpty(tAppWeekPaper.getWmaster())) {
			sql += " and t.WMaster like:wmaster";
			param.put("wmaster", "%%" + tAppWeekPaper.getWmaster() + "%%");
		}
		// 支线工时
		if (!PbUtils.isEmpty(tAppWeekPaper.getWbranch())) {
			sql += " and t.WBranch like:wbranch";
			param.put("wbranch", "%%" + tAppWeekPaper.getWbranch() + "%%");
		}
		// 主线工时权重
		if (!PbUtils.isEmpty(tAppWeekPaper.getWmasterrate())) {
			sql += " and t.WMasterrate like:wmasterrate";
			param.put("wmasterrate", "%%" + tAppWeekPaper.getWmasterrate() + "%%");
		}
		// 支线工时权重
		if (!PbUtils.isEmpty(tAppWeekPaper.getWbranchrate())) {
			sql += " and t.WBranchrate like:wbranchrate";
			param.put("wbranchrate", "%%" + tAppWeekPaper.getWbranchrate() + "%%");
		}
		// 创建时间
		if (!PbUtils.isEmpty(tAppWeekPaper.getWcreatedate())) {
			sql += " and t.WCreatedate like:wcreatedate";
			param.put("wcreatedate", "%%" + tAppWeekPaper.getWcreatedate() + "%%");
		}
		
		
		String totalsql = "select count(*) " + sql;

		//排序
		sql += " order by  ID desc";
	
		List<Map> maps = findBySql(sqlLeft+sql, param, tAppWeekPaper.getPage(), tAppWeekPaper.getLimit());
		Long total = countBySql(totalsql,param);
		grid.setCount(total);
		grid.setData(maps);

		return grid;
	}

    @Transactional
	@Override
	public Json add(TAppWeekPaper inf) {
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
		String sql = "delete from t_app_week_paper  where ID in (";
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
	public Json modify(TAppWeekPaper inf) {
		Json j = new Json();

		TAppWeekPaper tInf = getById(TAppWeekPaper.class, inf.getId());
		if (tInf == null) {
			j.setSuccess(false);
			j.setMsg("修改失败：找不到要修改的信息");
			return j;
		}

		// 旧对象
		TAppWeekPaper oldObject = new TAppWeekPaper();
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
	public Json verifyInfo(TAppWeekPaper inf) {
		Json j = new Json();
		Map<String, Object> params= new HashMap<String, Object>();
		if (!PbUtils.isEmpty(inf.getId())) {
			params.put("id", inf.getId());
		}
		Long total = super.count("Select count(*) from TAppWeekPaper t where t.Id =:id ", params);

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
	public List<TAppWeekPaper> getList() {
		List<TAppWeekPaper> l = find("from TAppWeekPaper t");
		return l;
	}
}
