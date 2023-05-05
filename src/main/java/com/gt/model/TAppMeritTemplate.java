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
 * @功能说明：绩效模板管理
 * @作者： zm
 * @创建日期：2020-04-11
 * @版本号：V1.0
 */
@Entity
@Table(name = "t_app_merit_template", schema = "")
public class TAppMeritTemplate extends BasePageForLayUI implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	
	private String id;//ID

	private String mjobid;//职位ID

	private Integer mmanhour;//工时设定

	private String mtarget;//任务目标

	private String mdetails;//任务内容

	private String mislinkproject;//是否关联项目

	private Date mcreatedate;//创建时间

	private Date mupdatedate;//修改时间


	//构造方法
	public TAppMeritTemplate() {
	}
	
	
	@Id @Column( name = "ID" , unique = true  ,nullable = false  , length = 36  )
	public String getId() {
		return  id;
	}

	public void setId(String id) {
		this.id = id;
	}

	
	@Column( name = "MJobid"  , length = 36  )
	public String getMjobid() {
		return  mjobid;
	}

	public void setMjobid(String mjobid) {
		this.mjobid = mjobid;
	}

	
	@Column( name = "MManhour"  , length = 10  )
	public Integer getMmanhour() {
		return  mmanhour;
	}

	public void setMmanhour(Integer mmanhour) {
		this.mmanhour = mmanhour;
	}

	
	@Column( name = "MTarget"  , length = 1000  )
	public String getMtarget() {
		return  mtarget;
	}

	public void setMtarget(String mtarget) {
		this.mtarget = mtarget;
	}

	
	@Column( name = "MDetails"  , length = 1000  )
	public String getMdetails() {
		return  mdetails;
	}

	public void setMdetails(String mdetails) {
		this.mdetails = mdetails;
	}

	
	@Column( name = "MIslinkproject"  , length = 1  )
	public String getMislinkproject() {
		return  mislinkproject;
	}

	public void setMislinkproject(String mislinkproject) {
		this.mislinkproject = mislinkproject;
	}

	
	 @Temporal(TemporalType.TIMESTAMP) @Column( name = "MCreatedate"  , length = 10  )
	public Date getMcreatedate() {
		return  mcreatedate;
	}

	public void setMcreatedate(Date mcreatedate) {
		this.mcreatedate = mcreatedate;
	}

	
	 @Temporal(TemporalType.TIMESTAMP) @Column( name = "MUpdatedate"  , length = 10  )
	public Date getMupdatedate() {
		return  mupdatedate;
	}

	public void setMupdatedate(Date mupdatedate) {
		this.mupdatedate = mupdatedate;
	}

}
