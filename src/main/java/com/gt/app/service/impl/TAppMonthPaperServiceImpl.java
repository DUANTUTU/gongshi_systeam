package com.gt.app.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.gt.utils.MyBeanUtils;
import org.springframework.stereotype.Service;
import com.gt.app.service.ITAppMonthPaperService;
import com.gt.model.TAppMonthPaper;
import com.gt.pageModel.DatagridForLayUI;
import com.gt.pageModel.Json;
import com.gt.sys.service.impl.BaseServiceImpl;
import com.gt.utils.PbUtils;
import javax.transaction.*;
import org.springframework.beans.factory.annotation.*;
import com.gt.app.dao.ITAppMonthPaperDao;
/**
 * 
 * @功能说明：月报业务接口实现类
 * @作者： zm
 * @创建日期：2020-04-12
 * @版本号：V1.0
 */
@Service("tAppMonthPaperService")
public class TAppMonthPaperServiceImpl extends BaseServiceImpl<TAppMonthPaper> implements ITAppMonthPaperService {

    //定义DAO
	@Autowired
	private ITAppMonthPaperDao TAppMonthPaperDao;

	@Override
	public DatagridForLayUI datagrid(TAppMonthPaper tAppMonthPaper) throws Exception{
		DatagridForLayUI grid = new DatagridForLayUI();
		String sqlLeft = "select t.ID as id,t.MOpercd as mopercd,t.MSummanhour as msummanhour,t.MMaster as mmaster,t.MBranch as mbranch,t.MMasterrate as mmasterrate,t.MBranchrate as mbranchrate,date_format(t.MCreatedate,'%Y-%c-%d %h:%i:%s') as mcreatedate " ;
		String sql = " from t_app_month_paper t where 1=1 " ;
		
		Map<String, Object> param = new HashMap<String, Object>();
		
		// ID
		if (!PbUtils.isEmpty(tAppMonthPaper.getId())) {
			sql += " and t.ID like:id";
			param.put("id", "%%" + tAppMonthPaper.getId() + "%%");
		}
		// 人员ID
		if (!PbUtils.isEmpty(tAppMonthPaper.getMopercd())) {
			sql += " and t.MOpercd like:mopercd";
			param.put("mopercd", "%%" + tAppMonthPaper.getMopercd() + "%%");
		}
		// 工时总计
		if (!PbUtils.isEmpty(tAppMonthPaper.getMsummanhour())) {
			sql += " and t.MSummanhour like:msummanhour";
			param.put("msummanhour", "%%" + tAppMonthPaper.getMsummanhour() + "%%");
		}
		// 主线工时
		if (!PbUtils.isEmpty(tAppMonthPaper.getMmaster())) {
			sql += " and t.MMaster like:mmaster";
			param.put("mmaster", "%%" + tAppMonthPaper.getMmaster() + "%%");
		}
		// 支线工时
		if (!PbUtils.isEmpty(tAppMonthPaper.getMbranch())) {
			sql += " and t.MBranch like:mbranch";
			param.put("mbranch", "%%" + tAppMonthPaper.getMbranch() + "%%");
		}
		// 主线工时权重
		if (!PbUtils.isEmpty(tAppMonthPaper.getMmasterrate())) {
			sql += " and t.MMasterrate like:mmasterrate";
			param.put("mmasterrate", "%%" + tAppMonthPaper.getMmasterrate() + "%%");
		}
		// 支线工时权重
		if (!PbUtils.isEmpty(tAppMonthPaper.getMbranchrate())) {
			sql += " and t.MBranchrate like:mbranchrate";
			param.put("mbranchrate", "%%" + tAppMonthPaper.getMbranchrate() + "%%");
		}
		// 创建时间
		if (!PbUtils.isEmpty(tAppMonthPaper.getMcreatedate())) {
			sql += " and t.MCreatedate like:mcreatedate";
			param.put("mcreatedate", "%%" + tAppMonthPaper.getMcreatedate() + "%%");
		}
		
		
		String totalsql = "select count(*) " + sql;

		//排序
		sql += " order by  ID desc";
	
		List<Map> maps = findBySql(sqlLeft+sql, param, tAppMonthPaper.getPage(), tAppMonthPaper.getLimit());
		Long total = countBySql(totalsql,param);
		grid.setCount(total);
		grid.setData(maps);

		return grid;
	}

    @Transactional
	@Override
	public Json add(TAppMonthPaper inf) {
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
		String sql = "delete from t_app_month_paper  where ID in (";
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
	public Json modify(TAppMonthPaper inf) {
		Json j = new Json();

		TAppMonthPaper tInf = getById(TAppMonthPaper.class, inf.getId());
		if (tInf == null) {
			j.setSuccess(false);
			j.setMsg("修改失败：找不到要修改的信息");
			return j;
		}

		// 旧对象
		TAppMonthPaper oldObject = new TAppMonthPaper();
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
	public Json verifyInfo(TAppMonthPaper inf) {
		Json j = new Json();
		Map<String, Object> params= new HashMap<String, Object>();
		if (!PbUtils.isEmpty(inf.getId())) {
			params.put("id", inf.getId());
		}
		Long total = super.count("Select count(*) from TAppMonthPaper t where t.Id =:id ", params);

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
	public List<TAppMonthPaper> getList() {
		List<TAppMonthPaper> l = find("from TAppMonthPaper t");
		return l;
	}
}
