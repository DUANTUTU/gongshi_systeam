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
 * @功能说明：项目计划
 * @作者： zm
 * @创建日期：2020-04-11
 * @版本号：V1.0
 */
@Entity
@Table(name = "t_app_project", schema = "")
public class TAppProject extends BasePageForLayUI implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	
	private String id;//ID

	private String pname;//项目名称

	private String pdetails;//项目描述

	private Integer pmanhourplan;//工时预估

	private String ptype;//项目类型

	private String popercd;//发起人

	private String pinscd;//发起人部门

	private Integer pmanhour;//确认工时

	private String pisappoint;//是否委派

	private String piscomplete;//是否完成

	private Date pcreatedate;//创建时间

	private Date pcompletedate;//完成时间


	//构造方法
	public TAppProject() {
	}
	
	
	@Id @Column( name = "ID" , unique = true  ,nullable = false  , length = 36  )
	public String getId() {
		return  id;
	}

	public void setId(String id) {
		this.id = id;
	}

	
	@Column( name = "PName"  , length = 500  )
	public String getPname() {
		return  pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}

	
	@Column( name = "PDetails"  , length = 1000  )
	public String getPdetails() {
		return  pdetails;
	}

	public void setPdetails(String pdetails) {
		this.pdetails = pdetails;
	}

	
	@Column( name = "PManhourplan"  , length = 10  )
	public Integer getPmanhourplan() {
		return  pmanhourplan;
	}

	public void setPmanhourplan(Integer pmanhourplan) {
		this.pmanhourplan = pmanhourplan;
	}

	
	@Column( name = "PType"  , length = 1  )
	public String getPtype() {
		return  ptype;
	}

	public void setPtype(String ptype) {
		this.ptype = ptype;
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

	
	@Column( name = "PManhour"  , length = 10  )
	public Integer getPmanhour() {
		return  pmanhour;
	}

	public void setPmanhour(Integer pmanhour) {
		this.pmanhour = pmanhour;
	}

	
	@Column( name = "PIsappoint"  , length = 1  )
	public String getPisappoint() {
		return  pisappoint;
	}

	public void setPisappoint(String pisappoint) {
		this.pisappoint = pisappoint;
	}

	
	@Column( name = "PIscomplete"  , length = 1  )
	public String getPiscomplete() {
		return  piscomplete;
	}

	public void setPiscomplete(String piscomplete) {
		this.piscomplete = piscomplete;
	}

	
	 @Temporal(TemporalType.TIMESTAMP) @Column( name = "PCreatedate"  , length = 10  )
	public Date getPcreatedate() {
		return  pcreatedate;
	}

	public void setPcreatedate(Date pcreatedate) {
		this.pcreatedate = pcreatedate;
	}

	
	 @Temporal(TemporalType.TIMESTAMP) @Column( name = "PCompletedate"  , length = 10  )
	public Date getPcompletedate() {
		return  pcompletedate;
	}

	public void setPcompletedate(Date pcompletedate) {
		this.pcompletedate = pcompletedate;
	}

}
