package com.landray.kmss.km.archives.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.convertor.ModelConvertor_ModelListToFormList;
import com.landray.kmss.common.convertor.ModelConvertor_ModelListToString;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.km.archives.forms.KmArchivesBorrowForm;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.sys.attachment.model.IAttachment;
import com.landray.kmss.sys.ftsearch.interfaces.INeedIndex;
import com.landray.kmss.sys.lbpmservice.interfaces.ISysLbpmMainModel;
import com.landray.kmss.sys.lbpmservice.interfaces.LbpmProcessForm;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.recycle.model.ISysRecycleModel;
import com.landray.kmss.sys.recycle.model.SysRecycleConstant;
import com.landray.kmss.sys.right.interfaces.ExtendAuthModel;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.util.DateUtil;

import com.landray.kmss.sys.hibernate.spi.InterceptFieldEnabled;

/**
  * 档案借阅申请
  */
public class KmArchivesBorrow extends ExtendAuthModel
		implements IAttachment, ISysLbpmMainModel, InterceptFieldEnabled,ISysRecycleModel,INeedIndex {

    private static ModelToFormPropertyMap toFormPropertyMap;

    private String docSubject;

    private Date fdBorrowDate;

    private String fdBorrowReason;

    private String fdRemarks;

    private KmArchivesTemplate docTemplate;

    private SysOrgElement docDept;

    private SysOrgPerson fdBorrower;
    
    private List<KmArchivesDetails> fdBorrowDetails;

    private AutoHashMap attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);

    private LbpmProcessForm sysWfBusinessModel = new LbpmProcessForm();

    @Override
    public Class<KmArchivesBorrowForm> getFormClass() {
        return KmArchivesBorrowForm.class;
    }

    @Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
        if (toFormPropertyMap == null) {
            toFormPropertyMap = new ModelToFormPropertyMap();
            toFormPropertyMap.putAll(super.getToFormPropertyMap());
            toFormPropertyMap.put("docCreateTime", new ModelConvertor_Common("docCreateTime").setDateTimeType(DateUtil.TYPE_DATETIME));
            toFormPropertyMap.put("fdBorrowDate", new ModelConvertor_Common("fdBorrowDate").setDateTimeType(DateUtil.TYPE_DATETIME));
            toFormPropertyMap.addNoConvertProperty("authReaderFlag");
            toFormPropertyMap.addNoConvertProperty("authEditorFlag");
            toFormPropertyMap.put("docCreator.fdName", "docCreatorName");
            toFormPropertyMap.put("docCreator.fdId", "docCreatorId");
            toFormPropertyMap.put("docTemplate.fdName", "docTemplateName");
            toFormPropertyMap.put("docTemplate.fdId", "docTemplateId");
            toFormPropertyMap.put("docDept.fdName", "docDeptName");
            toFormPropertyMap.put("docDept.fdId", "docDeptId");
            toFormPropertyMap.put("fdBorrower.fdName", "fdBorrowerName");
            toFormPropertyMap.put("fdBorrower.fdId", "fdBorrowerId");
            toFormPropertyMap.put("authReaders", new ModelConvertor_ModelListToString("authReaderIds:authReaderNames", "fdId:fdName"));
            toFormPropertyMap.put("authEditors", new ModelConvertor_ModelListToString("authEditorIds:authEditorNames", "fdId:fdName"));
			toFormPropertyMap.put("fdBorrowDetails",
					new ModelConvertor_ModelListToFormList(
							"fdBorrowDetail_Form"));
            toFormPropertyMap.put("authAttCopys", new ModelConvertor_ModelListToString("authAttCopyIds:authAttCopyNames", "fdId:fdName"));
            toFormPropertyMap.put("authAttDownloads", new ModelConvertor_ModelListToString("authAttDownloadIds:authAttDownloadNames", "fdId:fdName"));
            toFormPropertyMap.put("authAttPrints", new ModelConvertor_ModelListToString("authAttPrintIds:authAttPrintNames", "fdId:fdName"));
        }
        return toFormPropertyMap;
    }

    @Override
    public void recalculateFields() {
        super.recalculateFields();
		// 将档案保管员添加到借用单所有可阅读者中
		List keepers = new ArrayList();
		if (null != fdBorrowDetails && fdBorrowDetails.size() > 0) {
			for (KmArchivesDetails details : fdBorrowDetails) {
				keepers.add(details.getFdArchives().getFdStorekeeper());
			}
			ArrayUtil.concatTwoList(keepers, authAllReaders);
		}
        if (!getAuthReaderFlag()) {
        }
    }

    /**
     * 标题
     */
    @Override
    public String getDocSubject() {
        return this.docSubject;
    }

    /**
     * 标题
     */
    public void setDocSubject(String docSubject) {
        this.docSubject = docSubject;
    }

    /**
     * 借阅时间
     */
    public Date getFdBorrowDate() {
        return this.fdBorrowDate;
    }

    /**
     * 借阅时间
     */
    public void setFdBorrowDate(Date fdBorrowDate) {
        this.fdBorrowDate = fdBorrowDate;
    }

    /**
     * 借阅事由
     */
    public String getFdBorrowReason() {
        return this.fdBorrowReason;
    }

    /**
     * 借阅事由
     */
    public void setFdBorrowReason(String fdBorrowReason) {
        this.fdBorrowReason = fdBorrowReason;
    }

    /**
     * 备注
     */
    public String getFdRemarks() {
		return (String) readLazyField("fdRemarks", fdRemarks);
    }

    /**
     * 备注
     */
    public void setFdRemarks(String fdRemarks) {
		this.fdRemarks = (String) writeLazyField("fdRemarks", this.fdRemarks,
				fdRemarks);
    }

    /**
     * 流程
     */
    public KmArchivesTemplate getDocTemplate() {
        return this.docTemplate;
    }

    /**
     * 流程
     */
    public void setDocTemplate(KmArchivesTemplate docTemplate) {
        this.docTemplate = docTemplate;
    }

    /**
     * 所属部门
     */
    public SysOrgElement getDocDept() {
        return this.docDept;
    }

    /**
     * 所属部门
     */
    public void setDocDept(SysOrgElement docDept) {
        this.docDept = docDept;
    }

    /**
     * 借阅人
     */
    public SysOrgPerson getFdBorrower() {
        return this.fdBorrower;
    }

    /**
     * 借阅人
     */
    public void setFdBorrower(SysOrgPerson fdBorrower) {
        this.fdBorrower = fdBorrower;
    }

	/**
     * 档案借阅明细
     */
    public List<KmArchivesDetails> getFdBorrowDetails() {
        return this.fdBorrowDetails;
    }

	/**
     * 档案借阅明细
     */
    public void setFdBorrowDetails(List<KmArchivesDetails> fdBorrowDetails) {
        this.fdBorrowDetails = fdBorrowDetails;
    }

    @Override
    public AutoHashMap getAttachmentForms() {
        return attachmentForms;
    }

    @Override
    public LbpmProcessForm getSysWfBusinessModel() {
        return sysWfBusinessModel;
    }
    
    /*软删除配置*/
	private Integer docDeleteFlag;

	@Override
	public Integer getDocDeleteFlag() {
		return docDeleteFlag;
	}

	@Override
	public void setDocDeleteFlag(Integer docDeleteFlag) {
		this.docDeleteFlag = docDeleteFlag;
	}

	private Date docDeleteTime;

	@Override
	public Date getDocDeleteTime() {
		return docDeleteTime;
	}

	@Override
	public void setDocDeleteTime(Date docDeleteTime) {
		this.docDeleteTime = docDeleteTime;
	}

	private SysOrgPerson docDeleteBy;

	@Override
	public SysOrgPerson getDocDeleteBy() {
		return docDeleteBy;
	}

	@Override
	public void setDocDeleteBy(SysOrgPerson docDeleteBy) {
		this.docDeleteBy = docDeleteBy;
	}

	@Override
	public boolean isNeedIndex() {
		return docDeleteFlag == null || docDeleteFlag == SysRecycleConstant.OPT_TYPE_RECOVER;
	}

	@Override
	public Boolean getAuthReaderFlag() {
		if (authReaderFlag == null) {
			return new Boolean(false);
		}
		return authReaderFlag;
	}
}
