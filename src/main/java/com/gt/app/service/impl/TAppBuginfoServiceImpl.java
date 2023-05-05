package com.gt.app.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.gt.utils.MyBeanUtils;
import org.springframework.stereotype.Service;
import com.gt.app.service.ITAppBuginfoService;
import com.gt.model.TAppBuginfo;
import com.gt.pageModel.DatagridForLayUI;
import com.gt.pageModel.Json;
import com.gt.sys.service.impl.BaseServiceImpl;
import com.gt.utils.PbUtils;
import javax.transaction.*;
import org.springframework.beans.factory.annotation.*;
import com.gt.app.dao.ITAppBuginfoDao;
/**
 * 
 * @功能说明：缺陷记录表业务接口实现类
 * @作者： liutaok
 * @创建日期：2023-04-27
 * @版本号：V1.0
 */
@Service("tAppBuginfoService")
public class TAppBuginfoServiceImpl extends BaseServiceImpl<TAppBuginfo> implements ITAppBuginfoService {

    //定义DAO
	@Autowired
	private ITAppBuginfoDao TAppBuginfoDao;

	@Override
	public DatagridForLayUI datagrid(TAppBuginfo tAppBuginfo) throws Exception{
		DatagridForLayUI grid = new DatagridForLayUI();
		String sqlLeft = "select t.id as id,t.title as title,t.content as content,t.responsible_person as responsiblePerson,t.submitter as submitter,t.status as status,t.created_time as createdTime,t.updated_time as updatedTime,t.estimated_time as estimatedTime,t.project_name as projectName,t.project_id as projectId,t.project_milestones as projectMilestones " ;
		String sql = " from t_app_buginfo t where 1=1 " ;
		
		Map<String, Object> param = new HashMap<String, Object>();
		
		// 
		if (!PbUtils.isEmpty(tAppBuginfo.getId())) {
			sql += " and t.id like:id";
			param.put("id", "%%" + tAppBuginfo.getId() + "%%");
		}
		// 标题
		if (!PbUtils.isEmpty(tAppBuginfo.getTitle())) {
			sql += " and t.title like:title";
			param.put("title", "%%" + tAppBuginfo.getTitle() + "%%");
		}
		// 内容
		if (!PbUtils.isEmpty(tAppBuginfo.getContent())) {
			sql += " and t.content like:content";
			param.put("content", "%%" + tAppBuginfo.getContent() + "%%");
		}
		// 负责人
		if (!PbUtils.isEmpty(tAppBuginfo.getResponsiblePerson())) {
			sql += " and t.responsible_person like:responsiblePerson";
			param.put("responsiblePerson", "%%" + tAppBuginfo.getResponsiblePerson() + "%%");
		}
		// 提交人
		if (!PbUtils.isEmpty(tAppBuginfo.getSubmitter())) {
			sql += " and t.submitter like:submitter";
			param.put("submitter", "%%" + tAppBuginfo.getSubmitter() + "%%");
		}
		// 状态
		if (!PbUtils.isEmpty(tAppBuginfo.getStatus())) {
			sql += " and t.status like:status";
			param.put("status", "%%" + tAppBuginfo.getStatus() + "%%");
		}
		// 
		if (!PbUtils.isEmpty(tAppBuginfo.getCreatedTime())) {
			sql += " and t.created_time like:createdTime";
			param.put("createdTime", "%%" + tAppBuginfo.getCreatedTime() + "%%");
		}
		// 
		if (!PbUtils.isEmpty(tAppBuginfo.getUpdatedTime())) {
			sql += " and t.updated_time like:updatedTime";
			param.put("updatedTime", "%%" + tAppBuginfo.getUpdatedTime() + "%%");
		}
		// 预计时间
		if (!PbUtils.isEmpty(tAppBuginfo.getEstimatedTime())) {
			sql += " and t.estimated_time like:estimatedTime";
			param.put("estimatedTime", "%%" + tAppBuginfo.getEstimatedTime() + "%%");
		}
		// 项目名称
		if (!PbUtils.isEmpty(tAppBuginfo.getProjectName())) {
			sql += " and t.project_name like:projectName";
			param.put("projectName", "%%" + tAppBuginfo.getProjectName() + "%%");
		}
		// 项目id
		if (!PbUtils.isEmpty(tAppBuginfo.getProjectId())) {
			sql += " and t.project_id like:projectId";
			param.put("projectId", "%%" + tAppBuginfo.getProjectId() + "%%");
		}
		// 项目里程碑
		if (!PbUtils.isEmpty(tAppBuginfo.getProjectMilestones())) {
			sql += " and t.project_milestones like:projectMilestones";
			param.put("projectMilestones", "%%" + tAppBuginfo.getProjectMilestones() + "%%");
		}
		
		
		String totalsql = "select count(*) " + sql;

		//排序
		sql += " order by  id desc";
	
		List<Map> maps = findBySql(sqlLeft+sql, param, tAppBuginfo.getPage(), tAppBuginfo.getLimit());
		Long total = countBySql(totalsql,param);
		grid.setCount(total);
		grid.setData(maps);

		return grid;
	}

    @Transactional
	@Override
	public Json add(TAppBuginfo inf) {
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
		String sql = "delete from t_app_buginfo  where id in (";
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
	public Json modify(TAppBuginfo inf) {
		Json j = new Json();

		TAppBuginfo tInf = getById(TAppBuginfo.class, inf.getId());
		if (tInf == null) {
			j.setSuccess(false);
			j.setMsg("修改失败：找不到要修改的信息");
			return j;
		}

		// 旧对象
		TAppBuginfo oldObject = new TAppBuginfo();
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
	public Json verifyInfo(TAppBuginfo inf) {
		Json j = new Json();
		Map<String, Object> params= new HashMap<String, Object>();
		if (!PbUtils.isEmpty(inf.getId())) {
			params.put("id", inf.getId());
		}
		Long total = super.count("Select count(*) from TAppBuginfo t where t.Id =:id ", params);

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
	public List<TAppBuginfo> getList() {
		List<TAppBuginfo> l = find("from TAppBuginfo t");
		return l;
	}
}
