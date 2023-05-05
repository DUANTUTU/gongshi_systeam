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
 * @功能说明：测试
 * @作者： liutaok
 * @创建日期：2018-12-17
 * @版本号：V1.0
 */
@Entity
@Table(name = "test3", schema = "")
public class Test3 extends BasePageForLayUI implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer id;//编码

	private String name;//姓名

	private String sex;//性别


	//构造方法
	public Test3() {
	}
	
	
	@Id @Column( name = "ID" , unique = true  ,nullable = false  , length = 10  )
	public Integer getId() {
		return  id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	
	@Column( name = "NAME"  , length = 255  )
	public String getName() {
		return  name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	@Column( name = "SEX"  , length = 1  )
	public String getSex() {
		return  sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

}
