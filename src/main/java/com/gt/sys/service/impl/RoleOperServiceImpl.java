package com.gt.sys.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.gt.model.TSysRoleOper;
import com.gt.pageModel.RoleOper;
import com.gt.sys.service.IRoleOperService;
import org.springframework.transaction.annotation.*;

/**
 * 
 * @功能说明：人员角色service实现
 * @作者： liutaok
 * @创建日期：2018-10-08
 * @版本号：V1.0
 */
@Service("roleOperService")
public class RoleOperServiceImpl extends BaseServiceImpl<TSysRoleOper> implements IRoleOperService {

	@Override
	public List<TSysRoleOper> getList(String operCd) {
		String hql = "from TSysRoleOper t where t.TSysOperInf.operCd=:operCd";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("operCd", operCd);
		List<TSysRoleOper> listRoleOpers = find(hql, params);
		return listRoleOpers;
	}

	@Transactional
	@Override
	public void add(RoleOper roleOper) {
		String sql = "insert into t_sys_role_oper (uuid,  role_cd, oper_cd) values (:uuid,:role_cd,:oper_cd)";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("uuid", roleOper.getUuid());
		params.put("role_cd", roleOper.getRoleCd());
		params.put("oper_cd", roleOper.getOperCd());
		executeSql(sql, params);
	}

	@Transactional
	@Override
	public void remove(String operCd) {
		String hql = "delete from TSysRoleOper t where t.TSysOperInf.operCd=:operCd";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("operCd", operCd);
		executeHql(hql, params);
	}

}
