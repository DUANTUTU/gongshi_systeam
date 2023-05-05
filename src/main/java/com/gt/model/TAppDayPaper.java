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
 * @功能说明：日报
 * @作者： zm
 * @创建日期：2020-04-12
 * @版本号：V1.0
 */
@Entity
@Table(name = "t_app_day_paper", schema = "")
public class TAppDayPaper extends BasePageForLayUI implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	
	private String id;//ID

	private String dopercd;//人员id

	private Integer dsummanhour;//工时总计

	private Integer dmaster;//主线任务工时

	private Integer dbranch;//支线任务工时

	private Float dmasterrate;//主线工时权重

	private Float dbranchrate;//支线工时权重

	private Date dcreatedate;//创建时间


	//构造方法
	public TAppDayPaper() {
	}
	
	
	@Id @Column( name = "ID" , unique = true  ,nullable = false  , length = 36  )
	public String getId() {
		return  id;
	}

	public void setId(String id) {
		this.id = id;
	}

	
	@Column( name = "DOpercd"  , length = 36  )
	public String getDopercd() {
		return  dopercd;
	}

	public void setDopercd(String dopercd) {
		this.dopercd = dopercd;
	}

	
	@Column( name = "DSummanhour"  , length = 10  )
	public Integer getDsummanhour() {
		return  dsummanhour;
	}

	public void setDsummanhour(Integer dsummanhour) {
		this.dsummanhour = dsummanhour;
	}

	
	@Column( name = "DMaster"  , length = 10  )
	public Integer getDmaster() {
		return  dmaster;
	}

	public void setDmaster(Integer dmaster) {
		this.dmaster = dmaster;
	}

	
	@Column( name = "DBranch"  , length = 10  )
	public Integer getDbranch() {
		return  dbranch;
	}

	public void setDbranch(Integer dbranch) {
		this.dbranch = dbranch;
	}

	
	@Column( name = "DMasterrate"  , length = 10  )
	public Float getDmasterrate() {
		return dmasterrate;
	}

	public void setDmasterrate(Float dmasterrate) {
		this.dmasterrate = dmasterrate;
	}

	
	@Column( name = "DBranchrate"  , length = 10  )
	public Float getDbranchrate() {
		return  dbranchrate;
	}

	public void setDbranchrate(Float dbranchrate) {
		this.dbranchrate = dbranchrate;
	}

	
	 @Temporal(TemporalType.TIMESTAMP) @Column( name = "DCreatedate"  , length = 10  )
	public Date getDcreatedate() {
		return  dcreatedate;
	}

	public void setDcreatedate(Date dcreatedate) {
		this.dcreatedate = dcreatedate;
	}

}
