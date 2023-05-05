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
 * @功能说明：职位管理
 * @作者： zm
 * @创建日期：2020-04-11
 * @版本号：V1.0
 */
@Entity
@Table(name = "t_sys_job", schema = "")
public class TSysJob extends BasePageForLayUI implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	
	private String id;//ID

	private String jname;//职位名称

	private String jtype;//职位类型

	private String jstatus;//职位状态

	private Date jcreatedate;//创建时间


	//构造方法
	public TSysJob() {
	}
	
	
	@Id @Column( name = "ID" , unique = true  ,nullable = false  , length = 36  )
	public String getId() {
		return  id;
	}

	public void setId(String id) {
		this.id = id;
	}

	
	@Column( name = "JName"  , length = 100  )
	public String getJname() {
		return  jname;
	}

	public void setJname(String jname) {
		this.jname = jname;
	}

	
	@Column( name = "JType"  , length = 1  )
	public String getJtype() {
		return  jtype;
	}

	public void setJtype(String jtype) {
		this.jtype = jtype;
	}

	
	@Column( name = "JStatus"  , length = 1  )
	public String getJstatus() {
		return  jstatus;
	}

	public void setJstatus(String jstatus) {
		this.jstatus = jstatus;
	}

	
	 @Temporal(TemporalType.TIMESTAMP) @Column( name = "JCreatedate"  , length = 10  )
	public Date getJcreatedate() {
		return  jcreatedate;
	}

	public void setJcreatedate(Date jcreatedate) {
		this.jcreatedate = jcreatedate;
	}

}
