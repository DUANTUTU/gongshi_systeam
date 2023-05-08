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
 * @功能说明：工时提报
 * @作者： zm
 * @创建日期：2020-04-12
 * @版本号：V1.0
 */
@Entity
@Table(name = "t_app_manhour_confirm", schema = "")
public class TAppManhourConfirm extends BasePageForLayUI implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	
	private String id;//ID

	private String mprojectid;//项目ID

	private String mprojectplanid;//项目里程碑ID

	private String mopercd;//人员id

	private String mworkdetails;//工作内容

	private Integer mmanhour;//工时

	private String mcheckstatus;//审核状态

	private Date mcreatedate;//创建时间

	private Date mcheckdate;//审核时间

	private String mremark;//拒绝理由
	private Integer debugLeave;//缺陷等级
	private Integer debugID;//缺陷ID
	//缺陷内容
	private String debugContent;


	//预计完成时间
	private Integer debugFinishDate;
	@Column( name = "debug_finish_date"  , length = 2  )
	public Integer getDebugFinishDate() {
		return debugFinishDate;
	}

	public void setDebugFinishDate(Integer debugFinishDate) {
		this.debugFinishDate = debugFinishDate;
	}

	@Column( name = "debug_leave"  , length = 100  )
	public Integer getDebugLeave() {
		return debugLeave;
	}

	public void setDebugLeave(Integer debugLeave) {
		this.debugLeave = debugLeave;
	}
@Column( name = "debug_id"  , length = 100  )
	public Integer getDebugID() {
		return debugID;
	}

	public void setDebugID(Integer debugID) {
		this.debugID = debugID;
	}
	@Column( name = "debug_content"  , length = 100  )
	public String getDebugContent() {
		return debugContent;
	}

	public void setDebugContent(String debugContent) {
		this.debugContent = debugContent;
	}

	//构造方法
	public TAppManhourConfirm() {
	}
	
	
	@Id @Column( name = "ID" , unique = true  ,nullable = false  , length = 36  )
	public String getId() {
		return  id;
	}

	public void setId(String id) {
		this.id = id;
	}

	
	@Column( name = "MProjectid"  , length = 36  )
	public String getMprojectid() {
		return  mprojectid;
	}

	public void setMprojectid(String mprojectid) {
		this.mprojectid = mprojectid;
	}

	
	@Column( name = "MProjectplanid"  , length = 36  )
	public String getMprojectplanid() {
		return  mprojectplanid;
	}

	public void setMprojectplanid(String mprojectplanid) {
		this.mprojectplanid = mprojectplanid;
	}

	
	@Column( name = "MOpercd"  , length = 30  )
	public String getMopercd() {
		return  mopercd;
	}

	public void setMopercd(String mopercd) {
		this.mopercd = mopercd;
	}

	
	@Column( name = "MWorkdetails"  , length = 1000  )
	public String getMworkdetails() {
		return  mworkdetails;
	}

	public void setMworkdetails(String mworkdetails) {
		this.mworkdetails = mworkdetails;
	}

	
	@Column( name = "MManhour"  , length = 10  )
	public Integer getMmanhour() {
		return  mmanhour;
	}

	public void setMmanhour(Integer mmanhour) {
		this.mmanhour = mmanhour;
	}

	
	@Column( name = "MCheckstatus"  , length = 1  )
	public String getMcheckstatus() {
		return  mcheckstatus;
	}

	public void setMcheckstatus(String mcheckstatus) {
		this.mcheckstatus = mcheckstatus;
	}

	
	 @Temporal(TemporalType.TIMESTAMP) @Column( name = "MCreatedate"  , length = 10  )
	public Date getMcreatedate() {
		return  mcreatedate;
	}

	public void setMcreatedate(Date mcreatedate) {
		this.mcreatedate = mcreatedate;
	}

	
	 @Temporal(TemporalType.TIMESTAMP) @Column( name = "MCheckdate"  , length = 10  )
	public Date getMcheckdate() {
		return  mcheckdate;
	}

	public void setMcheckdate(Date mcheckdate) {
		this.mcheckdate = mcheckdate;
	}

	
	@Column( name = "MRemark"  , length = 1000  )
	public String getMremark() {
		return  mremark;
	}

	public void setMremark(String mremark) {
		this.mremark = mremark;
	}

}
