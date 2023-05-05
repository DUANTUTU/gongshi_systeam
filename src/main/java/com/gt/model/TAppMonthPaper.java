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
 * @功能说明：月报
 * @作者： zm
 * @创建日期：2020-04-12
 * @版本号：V1.0
 */
@Entity
@Table(name = "t_app_month_paper", schema = "")
public class TAppMonthPaper extends BasePageForLayUI implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	
	private String id;//ID

	private String mopercd;//人员ID

	private Integer msummanhour;//工时总计

	private Integer mmaster;//主线工时

	private Integer mbranch;//支线工时

	private Float mmasterrate;//主线工时权重

	private Float mbranchrate;//支线工时权重

	private Date mcreatedate;//创建时间


	//构造方法
	public TAppMonthPaper() {
	}
	
	
	@Id @Column( name = "ID" , unique = true  ,nullable = false  , length = 36  )
	public String getId() {
		return  id;
	}

	public void setId(String id) {
		this.id = id;
	}

	
	@Column( name = "MOpercd"  , length = 36  )
	public String getMopercd() {
		return  mopercd;
	}

	public void setMopercd(String mopercd) {
		this.mopercd = mopercd;
	}

	
	@Column( name = "MSummanhour"  , length = 10  )
	public Integer getMsummanhour() {
		return  msummanhour;
	}

	public void setMsummanhour(Integer msummanhour) {
		this.msummanhour = msummanhour;
	}

	
	@Column( name = "MMaster"  , length = 10  )
	public Integer getMmaster() {
		return  mmaster;
	}

	public void setMmaster(Integer mmaster) {
		this.mmaster = mmaster;
	}

	
	@Column( name = "MBranch"  , length = 10  )
	public Integer getMbranch() {
		return  mbranch;
	}

	public void setMbranch(Integer mbranch) {
		this.mbranch = mbranch;
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
