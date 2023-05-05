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
 * @功能说明：绩效评估
 * @作者： zm
 * @创建日期：2020-04-13
 * @版本号：V1.0
 */
@Entity
@Table(name = "t_app_merit_assess", schema = "")
public class TAppMeritAssess extends BasePageForLayUI implements java.io.Serializable {
	private static final Long serialVersionUID = 1L;
	
	private String id;//ID

	private String mopercd;//人员ID

	private Float msumhourrate;//绩效模板工时完成度

	private Float mmasterrate;//主线工时占比绩效模板率

	private Float mbranchrate;//主线工时占比绩效模板率

	private Date mcreatedate;//创建时间


	//构造方法
	public TAppMeritAssess() {
	}
	
	
	@Id @Column( name = "ID" , unique = true  ,nullable = false  , length = 36  )
	public String getId() {
		return  id;
	}

	public void setId(String id) {
		this.id = id;
	}

	
	@Column( name = "MOpercd"  , length = 30  )
	public String getMopercd() {
		return  mopercd;
	}

	public void setMopercd(String mopercd) {
		this.mopercd = mopercd;
	}

	
	@Column( name = "MSumhourrate"  , length = 10  )
	public Float getMsumhourrate() {
		return  msumhourrate;
	}

	public void setMsumhourrate(Float msumhourrate) {
		this.msumhourrate = msumhourrate;
	}

	
	@Column( name = "MMasterrate"  , length = 10  )
	public Float getMmasterrate() {
		return  mmasterrate;
	}

	public void setMmasterrate(Float mmasterrate) {
		this.mmasterrate = mmasterrate;
	}

	
	@Column( name = "MBranchrate"  , length = 10  )
	public Float getMbranchrate() {
		return  mbranchrate;
	}

	public void setMbranchrate(Float mbranchrate) {
		this.mbranchrate = mbranchrate;
	}

	
	 @Temporal(TemporalType.TIMESTAMP) @Column( name = "MCreatedate"  , length = 10  )
	public Date getMcreatedate() {
		return  mcreatedate;
	}

	public void setMcreatedate(Date mcreatedate) {
		this.mcreatedate = mcreatedate;
	}

}
