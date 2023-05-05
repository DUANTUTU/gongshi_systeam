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
 * @功能说明：项目工时评估
 * @作者： zm
 * @创建日期：2020-04-13
 * @版本号：V1.0
 */
@Entity
@Table(name = "t_app_project_assess", schema = "")
public class TAppProjectAssess extends BasePageForLayUI implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	
	private String id;//ID

	private String pprojectid;//项目ID

	private Float pcompleterate;//项目完成率

	private Integer pfinishhour;//实际工时

	private Float pfinishhourrate;//实际工时比计划工时

	private Integer punfinishhour;//未完成工时

	private Date pcreatedate;//创建时间


	//构造方法
	public TAppProjectAssess() {
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

	
	@Column( name = "PCompleterate"  , length = 10  )
	public Float getPcompleterate() {
		return  pcompleterate;
	}

	public void setPcompleterate(Float pcompleterate) {
		this.pcompleterate = pcompleterate;
	}

	
	@Column( name = "PFinishhour"  , length = 10  )
	public Integer getPfinishhour() {
		return  pfinishhour;
	}

	public void setPfinishhour(Integer pfinishhour) {
		this.pfinishhour = pfinishhour;
	}

	
	@Column( name = "PFinishhourrate"  , length = 10  )
	public Float getPfinishhourrate() {
		return  pfinishhourrate;
	}

	public void setPfinishhourrate(Float pfinishhourrate) {
		this.pfinishhourrate = pfinishhourrate;
	}

	
	@Column( name = "PUnfinishhour"  , length = 10  )
	public Integer getPunfinishhour() {
		return  punfinishhour;
	}

	public void setPunfinishhour(Integer punfinishhour) {
		this.punfinishhour = punfinishhour;
	}

	
	 @Temporal(TemporalType.TIMESTAMP) @Column( name = "PCreatedate"  , length = 10  )
	public Date getPcreatedate() {
		return  pcreatedate;
	}

	public void setPcreatedate(Date pcreatedate) {
		this.pcreatedate = pcreatedate;
	}

}
