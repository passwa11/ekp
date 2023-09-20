package com.landray.kmss.hr.organization.model;

import com.landray.kmss.sys.appconfig.model.BaseAppConfig;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;

/**
 * <P>
 * 编制统计规则
 * </P>
 * 
 * @author 苏琦
 */
public class HrOrganizationCompilingSum extends BaseAppConfig {
	protected String compilationOfficial; // 正式员工
	protected String compilationTrial; // 试用期员工
	protected String compilationPractice; // 实习员工
	protected String compilationTemporary; // 临时员工
	protected String compilationTrialDelay; // 使用延期

	public HrOrganizationCompilingSum() throws Exception {
		super();
		String compilationOfficial = super.getValue("compilationOfficial");
		if (StringUtil.isNull(compilationOfficial)) {
			compilationOfficial = "true";
		}
		super.setValue("compilationOfficial", compilationOfficial);

		String compilationTrial = super.getValue("compilationTrial");
		if (StringUtil.isNull(compilationTrial)) {
			compilationTrial = "true";
		}
		super.setValue("compilationTrial", compilationTrial);

		String compilationPractice = super.getValue("compilationPractice");
		if (StringUtil.isNull(compilationPractice)) {
			compilationPractice = "true";
		}
		super.setValue("compilationPractice", compilationPractice);

		String compilationTemporary = super.getValue("compilationTemporary");
		if (StringUtil.isNull(compilationTemporary)) {
			compilationTemporary = "true";
		}
		super.setValue("compilationTemporary", compilationTemporary);

		String compilationTrialDelay = super.getValue("compilationTrialDelay");
		if (StringUtil.isNull(compilationTrialDelay)) {
			compilationTrialDelay = "true";
		}
		super.setValue("compilationTrialDelay", compilationTrialDelay);

		String compilationRetire = super.getValue("compilationRetire");
		if (StringUtil.isNull(compilationRetire)) {
			compilationRetire = "false";
		}
		super.setValue("compilationRetire", compilationRetire);

	}

	public String getCompilationOfficial() {
		String compilationOfficial = super.getValue("compilationOfficial");
		if (StringUtil.isNull(compilationOfficial)) {
			compilationOfficial = "false";
		}
		return compilationOfficial;
	}
	

	public void setCompilationOfficial(String compilationOfficial) {
		super.setValue("compilationOfficial", compilationOfficial);
	}

	public String getCompilationTrial() {
		String compilationTrial = super.getValue("compilationTrial");
		if (StringUtil.isNull(compilationTrial)) {
			compilationTrial = "false";
		}
		return compilationTrial;
	}

	public void setCompilationTrial(String compilationTrial) {
		super.setValue("compilationTrial", compilationTrial);
	}

	public String getCompilationPractice() {
		String compilationPractice = super.getValue("compilationPractice");
		if (StringUtil.isNull(compilationPractice)) {
			compilationPractice = "false";
		}
		return compilationPractice;
	}

	public void setCompilationPractice(String compilationPractice) {
		super.setValue("compilationPractice", compilationPractice);
	}

	public String getCompilationTemporary() {
		String compilationTemporary = super.getValue("compilationTemporary");
		if (StringUtil.isNull(compilationTemporary)) {
			compilationTemporary = "false";
		}
		return compilationTemporary;

	}

	public void setCompilationTemporary(String compilationTemporary) {
		super.setValue("compilationTemporary", compilationTemporary);
	}

	public String getCompilationTrialDelay() {
		String compilationTrialDelay = super.getValue("compilationTrialDelay");
		if (StringUtil.isNull(compilationTrialDelay)) {
			compilationTrialDelay = "false";
		}
		return compilationTrialDelay;

	}

	public void setCompilationTrialDelay(String compilationTrialDelay) {
		super.setValue("compilationTrialDelay", compilationTrialDelay);
	}

	@Override
	public String getJSPUrl() {
		return "/hr/organization/hr_organization_compiling_sum/index.jsp";
	}
	
	@Override
	public String getModelDesc() {
		return ResourceUtil
				.getString("hr-organization:hr.organization.Compilation.rule");
	}

}
