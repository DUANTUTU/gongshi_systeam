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
 * @功能说明：缺陷记录表
 * @作者： liutaok
 * @创建日期：2023-04-27
 * @版本号：V1.0
 */
@Entity
@Table(name = "t_app_buginfo", schema = "")
public class TAppBuginfo extends BasePageForLayUI implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	
	private String id;//

	private String title;//标题

	private String content;//内容

	private String responsiblePerson;//负责人

	private String submitter;//提交人

	private Integer status;//状态

	private Timestamp createdTime;//

	private Timestamp updatedTime;//

	private Long estimatedTime;//预计时间

	private String projectName;//项目名称

	private String projectId;//项目id

	private String projectMilestones;//项目里程碑


	//构造方法
	public TAppBuginfo() {
	}
	
	
	@Id @Column( name = "id"  ,nullable = false  , length = 36  )
	public String getId() {
		return  id;
	}

	public void setId(String id) {
		this.id = id;
	}

	
	@Column( name = "title"  , length = 255  )
	public String getTitle() {
		return  title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	
	@Column( name = "content"  , length = 65535  )
	public String getContent() {
		return  content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	
	@Column( name = "responsible_person"  , length = 255  )
	public String getResponsiblePerson() {
		return  responsiblePerson;
	}

	public void setResponsiblePerson(String responsiblePerson) {
		this.responsiblePerson = responsiblePerson;
	}

	
	@Column( name = "submitter"  , length = 255  )
	public String getSubmitter() {
		return  submitter;
	}

	public void setSubmitter(String submitter) {
		this.submitter = submitter;
	}

	
	@Column( name = "status"  , length = 10  )
	public Integer getStatus() {
		return  status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	
	@Column( name = "created_time"  , length = 10  )
	public Timestamp getCreatedTime() {
		return  createdTime;
	}

	public void setCreatedTime(Timestamp createdTime) {
		this.createdTime = createdTime;
	}

	
	@Column( name = "updated_time"  , length = 10  )
	public Timestamp getUpdatedTime() {
		return  updatedTime;
	}

	public void setUpdatedTime(Timestamp updatedTime) {
		this.updatedTime = updatedTime;
	}

	
	@Column( name = "estimated_time"  , length = 10  )
	public Long getEstimatedTime() {
		return  estimatedTime;
	}

	public void setEstimatedTime(Long estimatedTime) {
		this.estimatedTime = estimatedTime;
	}

	
	@Column( name = "project_name"  , length = 255  )
	public String getProjectName() {
		return  projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	
	@Column( name = "project_id"  , length = 255  )
	public String getProjectId() {
		return  projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	
	@Column( name = "project_milestones"  , length = 255  )
	public String getProjectMilestones() {
		return  projectMilestones;
	}

	public void setProjectMilestones(String projectMilestones) {
		this.projectMilestones = projectMilestones;
	}

}
