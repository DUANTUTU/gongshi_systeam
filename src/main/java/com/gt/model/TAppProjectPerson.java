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
 * @功能说明：项目人员表
 * @作者： zm
 * @创建日期：2020-04-11
 * @版本号：V1.0
 */
@Entity
@Table(name = "t_app_project_person", schema = "")
public class TAppProjectPerson extends BasePageForLayUI implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	
	private String id;//ID

	private String popercd;//人员ID

	private String pinscd;//部门ID

	private String pprojectid;//项目ID

	private String pappointstatus;//委派状态

	private Integer pmanhour;//委派工时

	private Date pcreatedate;//创建时间

	private Date pconfirmdate;//确认时间


	//构造方法
	public TAppProjectPerson() {
	}
	
	
	@Id @Column( name = "ID" , unique = true  ,nullable = false  , length = 36  )
	public String getId() {
		return  id;
	}

	public void setId(String id) {
		this.id = id;
	}

	
	@Column( name = "POpercd"  , length = 30  )
	public String getPopercd() {
		return  popercd;
	}

	public void setPopercd(String popercd) {
		this.popercd = popercd;
	}

	
	@Column( name = "PInscd"  , length = 36  )
	public String getPinscd() {
		return  pinscd;
	}

	public void setPinscd(String pinscd) {
		this.pinscd = pinscd;
	}

	
	@Column( name = "PProjectid"  , length = 36  )
	public String getPprojectid() {
		return  pprojectid;
	}

	public void setPprojectid(String pprojectid) {
		this.pprojectid = pprojectid;
	}

	
	@Column( name = "PAppointstatus"  , length = 1  )
	public String getPappointstatus() {
		return  pappointstatus;
	}

	public void setPappointstatus(String pappointstatus) {
		this.pappointstatus = pappointstatus;
	}

	
	@Column( name = "PManhour"  , length = 10  )
	public Integer getPmanhour() {
		return  pmanhour;
	}

	public void setPmanhour(Integer pmanhour) {
		this.pmanhour = pmanhour;
	}

	
	 @Temporal(TemporalType.TIMESTAMP) @Column( name = "PCreatedate"  , length = 10  )
	public Date getPcreatedate() {
		return  pcreatedate;
	}

	public void setPcreatedate(Date pcreatedate) {
		this.pcreatedate = pcreatedate;
	}

	
	 @Temporal(TemporalType.TIMESTAMP) @Column( name = "PConfirmdate"  , length = 10  )
	public Date getPconfirmdate() {
		return  pconfirmdate;
	}

	public void setPconfirmdate(Date pconfirmdate) {
		this.pconfirmdate = pconfirmdate;
	}

}
