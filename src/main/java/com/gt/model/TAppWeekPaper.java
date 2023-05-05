package com.gt.model;

import com.gt.pageModel.BasePageForLayUI;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @功能说明：周报
 * @作者： zm
 * @创建日期：2020-04-12
 * @版本号：V1.0
 */
@Entity
@Table(name = "t_app_week_paper", schema = "")
public class TAppWeekPaper extends BasePageForLayUI implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	
	private String id;//ID

	private String wopercd;//人员ID

	private Integer wsummanhour;//工时总计

	private Integer wmaster;//主线工时

	private Integer wbranch;//支线工时

	private Float wmasterrate;//主线工时权重

	private Float wbranchrate;//支线工时权重

	private Date wcreatedate;//创建时间


	//构造方法
	public TAppWeekPaper() {
	}
	
	
	@Id @Column( name = "ID" , unique = true  ,nullable = false  , length = 36  )
	public String getId() {
		return  id;
	}

	public void setId(String id) {
		this.id = id;
	}

	
	@Column( name = "WOpercd"  , length = 36  )
	public String getWopercd() {
		return  wopercd;
	}

	public void setWopercd(String wopercd) {
		this.wopercd = wopercd;
	}

	
	@Column( name = "WSummanhour"  , length = 10  )
	public Integer getWsummanhour() {
		return  wsummanhour;
	}

	public void setWsummanhour(Integer wsummanhour) {
		this.wsummanhour = wsummanhour;
	}

	
	@Column( name = "WMaster"  , length = 10  )
	public Integer getWmaster() {
		return  wmaster;
	}

	public void setWmaster(Integer wmaster) {
		this.wmaster = wmaster;
	}

	
	@Column( name = "WBranch"  , length = 10  )
	public Integer getWbranch() {
		return  wbranch;
	}

	public void setWbranch(Integer wbranch) {
		this.wbranch = wbranch;
	}

	
	@Column( name = "WMasterrate"  , length = 10  )
	public Float getWmasterrate() {
		return  wmasterrate;
	}

	public void setWmasterrate(Float wmasterrate) {
		this.wmasterrate = wmasterrate;
	}

	
	@Column( name = "WBranchrate"  , length = 10  )
	public Float getWbranchrate() {
		return  wbranchrate;
	}

	public void setWbranchrate(Float wbranchrate) {
		this.wbranchrate = wbranchrate;
	}

	
	 @Temporal(TemporalType.TIMESTAMP) @Column( name = "WCreatedate"  , length = 10  )
	public Date getWcreatedate() {
		return  wcreatedate;
	}

	public void setWcreatedate(Date wcreatedate) {
		this.wcreatedate = wcreatedate;
	}

}
