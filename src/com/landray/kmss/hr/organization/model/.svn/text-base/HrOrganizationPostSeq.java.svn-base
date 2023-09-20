package com.landray.kmss.hr.organization.model;

import java.util.Date;
import java.util.List;

import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.hr.organization.forms.HrOrganizationPostSeqForm;
import com.landray.kmss.hr.organization.service.IHrOrganizationPostService;
import com.landray.kmss.hr.organization.util.HrOrgUtil;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.SpringBeanUtil;

/**
  * 岗位序列
  */
public class HrOrganizationPostSeq extends BaseModel {

    private static ModelToFormPropertyMap toFormPropertyMap;

    private String fdName;

    private String fdDesc;

    private Date docCreateTime;

    private Date docAlterTime;

    private SysOrgPerson docCreator;

    private SysOrgPerson docAlteror;

	@Override
    public Class<HrOrganizationPostSeqForm> getFormClass() {
		return HrOrganizationPostSeqForm.class;
    }

    @Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
        if (toFormPropertyMap == null) {
            toFormPropertyMap = new ModelToFormPropertyMap();
            toFormPropertyMap.putAll(super.getToFormPropertyMap());
            toFormPropertyMap.put("docCreateTime", new ModelConvertor_Common("docCreateTime").setDateTimeType(DateUtil.TYPE_DATETIME));
            toFormPropertyMap.put("docAlterTime", new ModelConvertor_Common("docAlterTime").setDateTimeType(DateUtil.TYPE_DATETIME));
            toFormPropertyMap.put("docCreator.fdName", "docCreatorName");
            toFormPropertyMap.put("docCreator.fdId", "docCreatorId");
            toFormPropertyMap.put("docAlteror.fdName", "docAlterorName");
            toFormPropertyMap.put("docAlteror.fdId", "docAlterorId");
        }
        return toFormPropertyMap;
    }

    @Override
    public void recalculateFields() {
        super.recalculateFields();
    }

    /**
     * 名称
     */
    public String getFdName() {
        return this.fdName;
    }

    /**
     * 名称
     */
    public void setFdName(String fdName) {
        this.fdName = fdName;
    }

    /**
     * 描述
     */
    public String getFdDesc() {
        return this.fdDesc;
    }

    /**
     * 描述
     */
    public void setFdDesc(String fdDesc) {
        this.fdDesc = fdDesc;
    }

    /**
     * 创建时间
     */
    public Date getDocCreateTime() {
        return this.docCreateTime;
    }

    /**
     * 创建时间
     */
    public void setDocCreateTime(Date docCreateTime) {
        this.docCreateTime = docCreateTime;
    }

    /**
     * 更新时间
     */
    public Date getDocAlterTime() {
        return this.docAlterTime;
    }

    /**
     * 更新时间
     */
    public void setDocAlterTime(Date docAlterTime) {
        this.docAlterTime = docAlterTime;
    }

    /**
     * 创建人
     */
    public SysOrgPerson getDocCreator() {
        return this.docCreator;
    }

    /**
     * 创建人
     */
    public void setDocCreator(SysOrgPerson docCreator) {
        this.docCreator = docCreator;
    }

    /**
     * 修改人
     */
    public SysOrgPerson getDocAlteror() {
        return this.docAlteror;
    }

    /**
     * 修改人
     */
    public void setDocAlteror(SysOrgPerson docAlteror) {
        this.docAlteror = docAlteror;
    }

	//用于列表页面显示

	private List<HrOrganizationPost> posts;

	public List<HrOrganizationPost> getPosts() {
		try {
			if (ArrayUtil.isEmpty(posts)) {
				IHrOrganizationPostService hrOrganizationPostService = (IHrOrganizationPostService) SpringBeanUtil
						.getBean("hrOrganizationPostService");
				posts = hrOrganizationPostService.getPostById(this.fdId);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return posts;
	}

	/**
	 * 获取当前岗位序列下岗位数
	 */
	private int postNum;

	public int getPostNum() {
		if (ArrayUtil.isEmpty(this.getPosts())) {
			return 0;
		} else {
			return this.posts.size();
		}
	}

	/**
	 * 获取在职人数
	 */
	private int personNum;

	public int getPersonNum() {
		if (ArrayUtil.isEmpty(this.posts)) {
			return 0;
		} else {
			return HrOrgUtil.getPersonByPosts(this.posts).size();
		}
	}

}
