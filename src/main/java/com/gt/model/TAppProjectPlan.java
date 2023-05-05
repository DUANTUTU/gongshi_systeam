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
 * @功能说明：项目计划-里程碑
 * @作者： zm
 * @创建日期：2020-04-11
 * @版本号：V1.0
 */
@Entity
@Table(name = "t_app_project_plan", schema = "")
public class TAppProjectPlan extends BasePageForLayUI implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	
	private String id;//ID

	private String pprojectid;//项目ID

	private String pplanname;//里程碑名称

	private Integer pplanmanhour;//里程碑工时预估

	private Integer pplanorder;//里程碑顺序

	private String piscomplete;//是否完成

	private Integer psummanhour;//里程碑确认工时

	private Date pcreatedate;//创建时间

	private Date pcompletedate;//完成时间


	//构造方法
	public TAppProjectPlan() {
	}
	
	
	@Id @Column( name = "ID" , unique = true  ,nullable = false  , length = 36  )
	public String getId() {
		return  id;
	}

	public void setId(String id) {
		this.id = id;
	}

	
	@Column( name = "PProjectid"  , length = 36  )
	public String getPprojectid() {
		return  pprojectid;
	}

	public void setPprojectid(String pprojectid) {
		this.pprojectid = pprojectid;
	}

	
	@Column( name = "PPlanname"  , length = 200  )
	public String getPplanname() {
		return  pplanname;
	}

	public void setPplanname(String pplanname) {
		this.pplanname = pplanname;
	}

	
	@Column( name = "PPlanmanhour"  , length = 10  )
	public Integer getPplanmanhour() {
		return  pplanmanhour;
	}

	public void setPplanmanhour(Integer pplanmanhour) {
		this.pplanmanhour = pplanmanhour;
	}

	
	@Column( name = "PPlanorder"  , length = 10  )
	public Integer getPplanorder() {
		return  pplanorder;
	}

	public void setPplanorder(Integer pplanorder) {
		this.pplanorder = pplanorder;
	}

	
	@Column( name = "PIscomplete"  , length = 1  )
	public String getPiscomplete() {
		return  piscomplete;
	}

	public void setPiscomplete(String piscomplete) {
		this.piscomplete = piscomplete;
	}

	
	@Column( name = "PSummanhour"  , length = 10  )
	public Integer getPsummanhour() {
		return  psummanhour;
	}

	public void setPsummanhour(Integer psummanhour) {
		this.psummanhour = psummanhour;
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
