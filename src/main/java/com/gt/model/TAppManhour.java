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
 * @功能说明：生效工时
 * @作者： zm
 * @创建日期：2020-04-12
 * @版本号：V1.0
 */
@Entity
@Table(name = "t_app_manhour", schema = "")
public class TAppManhour extends BasePageForLayUI implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	
	private String id;//ID

	private String mmanhourconfimid;//确认工时填报id

	private String mprojectid;//项目id

	private String mprojectplanid;//项目里程碑ID

	private String mopercd;//填报人ID

	private String minscd;//填报人部门

	private Date mcreatedate;//创建时间


	//构造方法
	public TAppManhour() {
	}
	
	
	@Id @Column( name = "ID" , unique = true  ,nullable = false  , length = 36  )
	public String getId() {
		return  id;
	}

	public void setId(String id) {
		this.id = id;
	}

	
	@Column( name = "MManhourconfimid"  , length = 36  )
	public String getMmanhourconfimid() {
		return  mmanhourconfimid;
	}

	public void setMmanhourconfimid(String mmanhourconfimid) {
		this.mmanhourconfimid = mmanhourconfimid;
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

	
	@Column( name = "MInscd"  , length = 36  )
	public String getMinscd() {
		return  minscd;
	}

	public void setMinscd(String minscd) {
		this.minscd = minscd;
	}

	
	 @Temporal(TemporalType.TIMESTAMP) @Column( name = "MCreatedate"  , length = 10  )
	public Date getMcreatedate() {
		return  mcreatedate;
	}

	public void setMcreatedate(Date mcreatedate) {
		this.mcreatedate = mcreatedate;
	}

}
