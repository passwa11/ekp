package com.landray.kmss.km.imeeting.actions;

import com.landray.kmss.common.actions.CategoryNodeAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.module.core.proxy.IDynamicProxy;
import com.landray.kmss.common.module.util.ModuleCenter;
import com.landray.kmss.common.service.ICoreOuterService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.constant.SysAuthConstant;
import com.landray.kmss.constant.SysDocConstant;
import com.landray.kmss.km.imeeting.ImeetingConstant;
import com.landray.kmss.km.imeeting.forms.KmImeetingAgendaForm;
import com.landray.kmss.km.imeeting.forms.KmImeetingMainForm;
import com.landray.kmss.km.imeeting.model.KmImeetingAgenda;
import com.landray.kmss.km.imeeting.model.KmImeetingBook;
import com.landray.kmss.km.imeeting.model.KmImeetingConfig;
import com.landray.kmss.km.imeeting.model.KmImeetingMain;
import com.landray.kmss.km.imeeting.model.KmImeetingMainFeedback;
import com.landray.kmss.km.imeeting.model.KmImeetingMapping;
import com.landray.kmss.km.imeeting.model.KmImeetingRes;
import com.landray.kmss.km.imeeting.model.KmImeetingSeatPlan;
import com.landray.kmss.km.imeeting.model.KmImeetingSummary;
import com.landray.kmss.km.imeeting.model.KmImeetingTemplate;
import com.landray.kmss.km.imeeting.model.KmImeetingVote;
import com.landray.kmss.km.imeeting.service.IKmImeetingAgendaService;
import com.landray.kmss.km.imeeting.service.IKmImeetingBookService;
import com.landray.kmss.km.imeeting.service.IKmImeetingMainFeedbackService;
import com.landray.kmss.km.imeeting.service.IKmImeetingMainService;
import com.landray.kmss.km.imeeting.service.IKmImeetingMappingService;
import com.landray.kmss.km.imeeting.service.IKmImeetingOutVedioService;
import com.landray.kmss.km.imeeting.service.IKmImeetingResService;
import com.landray.kmss.km.imeeting.service.IKmImeetingSeatPlanService;
import com.landray.kmss.km.imeeting.service.IKmImeetingSummaryService;
import com.landray.kmss.km.imeeting.service.IKmImeetingTemplateService;
import com.landray.kmss.km.imeeting.service.IKmImeetingTopicService;
import com.landray.kmss.km.imeeting.service.IKmImeetingVoteService;
import com.landray.kmss.km.imeeting.util.AliMeetingUtil;
import com.landray.kmss.km.imeeting.util.BoenUtil;
import com.landray.kmss.km.imeeting.util.KKUtil;
import com.landray.kmss.km.imeeting.util.KmImeetingConfigUtil;
import com.landray.kmss.km.imeeting.util.RecurrenceUtil;
import com.landray.kmss.sys.agenda.interfaces.ISysAgendaMainForm;
import com.landray.kmss.sys.appconfig.service.ISysAppConfigService;
import com.landray.kmss.sys.authentication.background.IBackgroundAuthService;
import com.landray.kmss.sys.category.model.SysCategoryBaseModel;
import com.landray.kmss.sys.category.model.SysCategoryMain;
import com.landray.kmss.sys.config.model.SysConfigParameters;
import com.landray.kmss.sys.lbpm.engine.persistence.model.LbpmProcess;
import com.landray.kmss.sys.lbpm.engine.persistence.model.LbpmWorkitem;
import com.landray.kmss.sys.lbpm.engine.persistence.model.TempDataAccess;
import com.landray.kmss.sys.lbpmservice.interfaces.LbpmProcessForm;
import com.landray.kmss.sys.lbpmservice.support.service.ILbpmProcessRestartService;
import com.landray.kmss.sys.lbpmservice.support.service.ILbpmProcessService;
import com.landray.kmss.sys.lbpmservice.support.service.spring.InternalLbpmProcessForm;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.log.util.oper.UserOperContentHelper;
import com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.unit.model.KmImissiveUnit;
import com.landray.kmss.sys.unit.service.IKmImissiveUnitService;
import com.landray.kmss.sys.workflow.interfaces.SysFlowUtil;
import com.landray.kmss.sys.workflow.interfaces.SysWfBusinessForm;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.CriteriaUtil;
import com.landray.kmss.util.CriteriaValue;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.HQLUtil;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.Runner;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import com.sunbor.web.tag.Page;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 会议安排 Action
 */
public class KmImeetingMainAction extends CategoryNodeAction {

	private final SimpleDateFormat format2 = new SimpleDateFormat(
			"yyyy-MM-dd");
	private static final String[] weeks = { "SU", "MO", "TU", "WE", "TH", "FR",
			"SA" };

	private IKmImeetingTemplateService kmImeetingTemplateService;
	protected IKmImeetingMainService kmImeetingMainService;
	private IKmImeetingAgendaService kmImeetingAgendaService;
	private IKmImeetingResService kmImeetingResService;
	private IKmImeetingBookService kmImeetingBookService;
	private IKmImeetingMainFeedbackService kmImeetingMainFeedbackService;
	private IKmImeetingOutVedioService kmImeetingOutVedioService;
	private ILbpmProcessRestartService lbpmProcessRestartServiceImp;

	public ILbpmProcessRestartService getLbpmProcessRestartServiceImp() {
		if (lbpmProcessRestartServiceImp == null) {
            lbpmProcessRestartServiceImp = (ILbpmProcessRestartService) getBean(
                    "lbpmProcessRestartService");
        }
		return lbpmProcessRestartServiceImp;
	}
	private IKmImeetingMappingService kmImeetingMappingService;

	private IKmImeetingSeatPlanService kmImeetingSeatPlanService;

	public IKmImeetingSeatPlanService getKmImeetingSeatPlanService() {
		if (kmImeetingSeatPlanService == null) {
            kmImeetingSeatPlanService = (IKmImeetingSeatPlanService) getBean(
                    "kmImeetingSeatPlanService");
        }
		return kmImeetingSeatPlanService;
	}

	protected IKmImeetingMappingService getKmImeetingMappingService() {
		if (kmImeetingMappingService == null) {
            kmImeetingMappingService = (IKmImeetingMappingService) getBean("kmImeetingMappingService");
        }
		return kmImeetingMappingService;
	}

	private ISysOrgCoreService sysOrgCoreService;

	private ICoreOuterService dispatchCoreService;

	@Override
	protected IKmImeetingMainService getServiceImp(HttpServletRequest request) {
		if (kmImeetingMainService == null) {
            kmImeetingMainService = (IKmImeetingMainService) getBean("kmImeetingMainService");
        }
		return kmImeetingMainService;
	}

	protected IKmImeetingTemplateService getKmImeetingTemplateService(
			HttpServletRequest request) {
		if (kmImeetingTemplateService == null) {
			kmImeetingTemplateService = (IKmImeetingTemplateService) getBean("kmImeetingTemplateService");
		}
		return kmImeetingTemplateService;
	}

	protected IKmImeetingAgendaService getKmImeetingAgendaService(
			HttpServletRequest request) {
		if (kmImeetingAgendaService == null) {
			kmImeetingAgendaService = (IKmImeetingAgendaService) getBean("kmImeetingAgendaService");
		}
		return kmImeetingAgendaService;
	}

	protected IKmImeetingResService getKmImeetingResService(
			HttpServletRequest request) {
		if (kmImeetingResService == null) {
			kmImeetingResService = (IKmImeetingResService) getBean("kmImeetingResService");
		}
		return kmImeetingResService;
	}

	public IKmImeetingBookService getKmImeetingBookService(
			HttpServletRequest request) {
		if (kmImeetingBookService == null) {
			kmImeetingBookService = (IKmImeetingBookService) getBean("kmImeetingBookService");
		}
		return kmImeetingBookService;
	}

	protected IKmImeetingMainFeedbackService getKmImeetingMainFeedbackService(
			HttpServletRequest request) {
		if (kmImeetingMainFeedbackService == null) {
			kmImeetingMainFeedbackService = (IKmImeetingMainFeedbackService) getBean("kmImeetingMainFeedbackService");
		}
		return kmImeetingMainFeedbackService;
	}

	protected ISysOrgCoreService getSysOrgCoreService(HttpServletRequest request) {
		if (sysOrgCoreService == null) {
			sysOrgCoreService = (ISysOrgCoreService) getBean("sysOrgCoreService");
		}
		return sysOrgCoreService;
	}

	public IKmImeetingOutVedioService getKmImeetingOutVedioService() {
		if (kmImeetingOutVedioService == null) {
			kmImeetingOutVedioService = (IKmImeetingOutVedioService) getBean("kmImeetingOutVedioService");
		}
		return kmImeetingOutVedioService;
	}

	protected ICoreOuterService getDispatchCoreService() {
		if (dispatchCoreService == null) {
			dispatchCoreService = (ICoreOuterService) getBean("dispatchCoreService");
		}
		return dispatchCoreService;
	}

	private IKmImeetingSummaryService kmImeetingSummaryService;

	public IKmImeetingSummaryService
			getKmImeetingSummaryService(HttpServletRequest request) {
		if (kmImeetingSummaryService == null) {
			kmImeetingSummaryService = (IKmImeetingSummaryService) getBean(
					"kmImeetingSummaryService");
		}
		return kmImeetingSummaryService;
	}

	private IKmImeetingTopicService kmImeetingTopicService;

	public IKmImeetingTopicService
			getKmImeetingTopicService(HttpServletRequest request) {
		if (kmImeetingTopicService == null) {
			kmImeetingTopicService = (IKmImeetingTopicService) getBean(
					"kmImeetingTopicService");
		}
		return kmImeetingTopicService;
	}

	private IKmImeetingVoteService kmImeetingVoteService;

	public IKmImeetingVoteService getKmImeetingVoteService() {
		if (kmImeetingVoteService == null) {
			kmImeetingVoteService = (IKmImeetingVoteService) getBean(
					"kmImeetingVoteService");
		}
		return kmImeetingVoteService;
	}
	
	@Override
	public ActionForward add(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-add", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			ActionForm newForm = createNewForm(mapping, form, request, response);
			if (newForm != form) {
                request.setAttribute(getFormName(newForm, request), newForm);
            }
		} catch (Exception e) {
			messages.addError(e);
		}
		
		TimeCounter.logCurrentTime("Action-add", false, getClass());
		//add1(mapping, form, request, response);
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			Object isAvailable = request.getAttribute("fdIsAvailable");
			if (isAvailable != null && !Boolean.parseBoolean(isAvailable.toString())) {
				return new ActionForward("/km/imeeting/category_invalid.jsp");
			}
			/*else{
				return getActionForward("add", mapping, form, request, response);
			}*/
			return getActionForward("add", mapping, form, request, response);
		}
	}
	
	@Override
	protected ActionForm createNewForm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String templateId = request.getParameter("fdTemplateId");
		String resId = request.getParameter("resId");
		String bookId = request.getParameter("bookId");// 会议预约ID，用于预约转会议安排时使用#21977
		String fdHoldDate = request.getParameter("startDate");
		String fdFinishDate = request.getParameter("endDate");
		// bam2集成
		String fdWorkId = request.getParameter("fdWorkId");
		String fdModelId = request.getParameter("fdModelId");
		String fdPhaseId = request.getParameter("fdPhaseId");
		String fdModelName = request.getParameter("fdModelName");
		String isCycle = request.getParameter("isCycle");
		String changeType = request.getParameter("changeType");

		KmImeetingMainForm kmImeetingMainForm = (KmImeetingMainForm) super
				.createNewForm(mapping, form, request, response);

		// 周期性会议
		if ("true".equals(isCycle)) {
			kmImeetingMainForm.setIsCycle(isCycle);
		}

		// bam2集成
		kmImeetingMainForm.setFdWorkId(fdWorkId);
		kmImeetingMainForm.setFdModelId(fdModelId);
		kmImeetingMainForm.setFdPhaseId(fdPhaseId);
		kmImeetingMainForm.setFdModelName(fdModelName);

		// 设置组织部门
		SysOrgElement docDept = UserUtil.getUser().getFdParent();
		if (docDept != null) {
			kmImeetingMainForm.setDocDeptId(docDept.getFdId());
			kmImeetingMainForm.setDocDeptName(docDept.getDeptLevelNames());
		}
		if ("true".equals(request.getParameter("copyMeeting"))
				|| "after".equals(changeType)) {
			// 会议复制
			request.setAttribute("copyMeeting", true);
			String meetingId = request.getParameter("meetingId");
			if (StringUtil.isNull(meetingId)) {
				meetingId = request.getParameter("fdOriginalId");
			}
			if (StringUtil.isNotNull(meetingId)) {
				KmImeetingMain kmImeetingMain = (KmImeetingMain) getServiceImp(
						request).findByPrimaryKey(meetingId);
				KmImeetingTemplate fdTemplate = kmImeetingMain.getFdTemplate();
				if (fdTemplate != null) {
                    request.setAttribute("fdNeedMultiRes",
                            fdTemplate.getFdNeedMultiRes());
                }
				KmImeetingRes res = kmImeetingMain.getFdPlace();
				KmImeetingMainForm copyForm = new KmImeetingMainForm();
				copyForm = (KmImeetingMainForm) getServiceImp(request)
						.cloneModelToForm(kmImeetingMainForm, kmImeetingMain,
								new RequestContext(request));
				// #24394 如果复制会议时发现该会议室已失效,清空会议室的选择
				if (res != null && res.getFdIsAvailable() != null
						&& res.getFdIsAvailable() == false) {
					copyForm.setFdPlaceId("");
					copyForm.setFdPlaceName("");
				}
				// 重新设置会议发起人、发起时间
				copyForm.setDocCreateTime(DateUtil.convertDateToString(
						new Date(), DateUtil.TYPE_DATETIME, request.getLocale()));
				copyForm.setDocCreatorId(UserUtil.getUser().getFdId());
				copyForm.setDocCreatorName(UserUtil.getUser().getFdName());
				copyForm.setFdNotifyerId("");
				copyForm.setFdNotifyerName("");
				// 判断召开时间是否小于当前，如果是将召开时间置空
				if (kmImeetingMain.getFdHoldDate().getTime() < new Date()
						.getTime()) {
					copyForm.setFdHoldDate("");
					copyForm.setFdFinishDate("");
				}
				// 初始化一些状态，如果是否已纪要、是否会议变更
				copyForm
						.setFdChangeMeetingFlag(ImeetingConstant.IS_CHANGEMEETING_NO);
				copyForm
						.setFdSummaryFlag(ImeetingConstant.MAIN_CONTAINS_SUMMARY_NO);
				copyForm.setFdSummaryCompleteTime("");
				copyForm.setFdIsHurrySummary("false");
				copyForm.setFdHurryDate("");
				copyForm.setIsNotify(ImeetingConstant.IS_NOTIFY_NO);
				// 状态置为10
				copyForm.setDocStatus(SysDocConstant.DOC_STATUS_DRAFT);
				// 日期转换
				copyForm.setFdSummaryCompleteTime(DateUtil.convertDateToString(
						DateUtil.convertStringToDate(copyForm
								.getFdSummaryCompleteTime(),
								DateUtil.TYPE_DATE, request.getLocale()),
						DateUtil.TYPE_DATE, request.getLocale()));
				setRepeat(copyForm, request);
				return copyForm;
			}
		} else {
			// 用于调用无参 add方法
			String noTemplate = request.getParameter("noTemplate");
			if (StringUtil.isNull(templateId) && "true".equals(noTemplate)) {
				request.setAttribute("noTemplate", "true");
				kmImeetingMainForm.setIsCloud("false");
				kmImeetingMainForm.setFdNeedFeedback("false");// 简单模式下默认不需要回执
				kmImeetingMainForm.setFdIsVideo("true");
				kmImeetingMainForm.setFdNeedPlace("false");
				kmImeetingMainForm.setFdEmceeId(UserUtil.getUser().getFdId());
				kmImeetingMainForm.setFdEmceeName(UserUtil.getUser().getFdName());
			} else {
				kmImeetingMainForm.setFdNeedFeedback("true");
				kmImeetingMainForm.setFdIsVideo("false");
				kmImeetingMainForm.setFdNeedPlace("true");
			}
			kmImeetingMainForm.setFdHostId(UserUtil.getUser().getFdId());
			kmImeetingMainForm.setFdHostName(UserUtil.getUser().getFdName());// 主持人默认为创建者

			// 会议发起人
			kmImeetingMainForm.setDocCreatorId(UserUtil.getUser().getFdId());
			kmImeetingMainForm
					.setDocCreatorName(UserUtil.getUser().getFdName());
			kmImeetingMainForm.setDocCreateTime(DateUtil.convertDateToString(
					new Date(), DateUtil.TYPE_DATETIME, request.getLocale()));
			// 默认带出当前时间
			if (StringUtil.isNull(fdHoldDate)) {
				fdHoldDate = DateUtil.convertDateToString(new Date(),
						DateUtil.TYPE_DATE, request.getLocale());
			}
			if (StringUtil.isNull(fdFinishDate)) {
				fdFinishDate = fdHoldDate;
			}
			// 初始化召开时间、结束时间
			// #9159 没有时分默认规则：召开时间为当前时间的下一个整点
			if (StringUtil.isNotNull(fdHoldDate)) {
				if (!isDateTime(fdHoldDate)) {
					Date _fdHoldDate = DateUtil.convertStringToDate(fdHoldDate,
							DateUtil.TYPE_DATE,request.getLocale());
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(_fdHoldDate);
					calendar.set(Calendar.HOUR_OF_DAY, Calendar.getInstance()
							.get(Calendar.HOUR_OF_DAY) + 1);// 设置为9点
					fdHoldDate = DateUtil.convertDateToString(calendar.getTime(), DateUtil.TYPE_DATETIME,
							request.getLocale());
				}
				kmImeetingMainForm.setFdHoldDate(fdHoldDate);
			}
			if (StringUtil.isNotNull(fdFinishDate)) {
				if (!isDateTime(fdFinishDate)) {
					Date _fdFinishDate = DateUtil.convertStringToDate(
							fdFinishDate, DateUtil.TYPE_DATE,request.getLocale());
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(_fdFinishDate);
					calendar.set(Calendar.HOUR_OF_DAY,
							Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
									+ 2);// 设置为10点
					fdFinishDate = DateUtil.convertDateToString(calendar
							.getTime(), DateUtil.TYPE_DATETIME,request.getLocale());
				}
				kmImeetingMainForm.setFdFinishDate(fdFinishDate);
			}

			String fdTime = request.getParameter("fdTime");
			String fdStratTime = request.getParameter("fdStratTime");
			if (!StringUtil.isNull(fdTime) && !StringUtil.isNull(fdStratTime)) {
				String[] fdStartTimeArr = fdStratTime.split(":");
				if (fdStartTimeArr.length > 1) {
					String fdStratTimeA = fdStartTimeArr[0], fdStratTimeB = fdStartTimeArr[1];
					if (fdStartTimeArr[0].length() < 2) {
                        fdStratTimeA = "0" + fdStartTimeArr[0];
                    }
					if (fdStartTimeArr[1].length() < 2) {
                        fdStratTimeB = "0" + fdStartTimeArr[1];
                    }
					kmImeetingMainForm.setFdHoldDate(fdTime + " " + fdStratTimeA + ":" + fdStratTimeB);
				} else {
					if (fdStratTime.length() < 2) {
                        fdStratTime = "0" + fdStratTime;
                    }
					kmImeetingMainForm.setFdHoldDate(fdTime + " " + fdStratTime + ":00");
				}
			}
			String fdFinishTime = request.getParameter("fdFinishTime");
			if (!StringUtil.isNull(fdTime) && !StringUtil.isNull(fdFinishTime)) {
				String[] fdFinishTimeArr = fdFinishTime.split(":");
				if (fdFinishTimeArr.length > 1) {
					String fdFinishTimeA = fdFinishTimeArr[0], fdFinishTimeB = fdFinishTimeArr[1];
					if (fdFinishTimeArr[0].length() < 2) {
                        fdFinishTimeA = "0" + fdFinishTimeArr[0];
                    }
					if (fdFinishTimeArr[1].length() < 2) {
                        fdFinishTimeB = "0" + fdFinishTimeArr[1];
                    }
					kmImeetingMainForm.setFdHoldDate(fdTime + " " + fdFinishTimeA + ":" + fdFinishTimeB);
				} else {
					if (fdFinishTime.length() < 2) {
                        fdFinishTime = "0" + fdFinishTime;
                    }
					kmImeetingMainForm.setFdFinishDate(fdTime + " " + fdFinishTime + ":00");
				}
			}

			if (!StringUtil.isNull(fdTime) && !StringUtil.isNull(fdStratTime) && !StringUtil.isNull(fdFinishTime)) {
				String fdStratTimeA = fdStratTime;
				String fdFinishTimeA = fdFinishTime;
				String[] fdStartTimeArr = fdStratTime.split(":");
				if (fdStartTimeArr.length > 1) {
					fdStratTimeA = fdStartTimeArr[0];
				}
				String[] fdFinishTimeArr = fdFinishTime.split(":");
				if (fdFinishTimeArr.length > 1) {
					fdFinishTimeA = fdFinishTimeArr[0];
				}
				String fdHoldDurationHour = Integer
						.toString((Integer.parseInt(fdFinishTimeA) - Integer.parseInt(fdStratTimeA)));
				kmImeetingMainForm.setFdHoldDuration(fdHoldDurationHour + ".0");
			} else {
				kmImeetingMainForm.setFdHoldDuration("0.5");
			}

			// 取出资源
			if (StringUtil.isNotNull(resId)) {
				KmImeetingRes kmImeetingRes = (KmImeetingRes) getKmImeetingResService(
						request).findByPrimaryKey(resId);
				if (kmImeetingRes != null) {
					kmImeetingMainForm.setFdPlaceId(kmImeetingRes.getFdId());
					kmImeetingMainForm
							.setFdPlaceName(kmImeetingRes.getFdName());
					if (kmImeetingRes.getFdUserTime() != null) {
						kmImeetingMainForm.setFdUserTime(kmImeetingRes
								.getFdUserTime().toString());
					}
				}
			}
			// 从会议模板中继承
			if (StringUtil.isNotNull(templateId)) {
				KmImeetingTemplate kmImeetingTemplate = (KmImeetingTemplate) getKmImeetingTemplateService(
						request).findByPrimaryKey(templateId);
				request.setAttribute("fdIsAvailable",
						kmImeetingTemplate.getFdIsAvailable());
				request.setAttribute("fdNeedMultiRes",
						kmImeetingTemplate.getFdNeedMultiRes());
				kmImeetingMainForm.setFdTemplateId(templateId);
				kmImeetingMainForm.setFdTemplateName(kmImeetingTemplate
						.getFdName());
				// 会议名称
				kmImeetingMainForm
						.setFdName(kmImeetingTemplate.getDocSubject());
				// 继承组织人
				if (kmImeetingTemplate.getFdEmcee() != null) {
					kmImeetingMainForm.setFdEmceeId(kmImeetingTemplate
							.getFdEmcee().getFdId());
					kmImeetingMainForm.setFdEmceeName(kmImeetingTemplate
							.getFdEmcee().getFdName());
				}
				// 没有组织人，默认为当前人
				if (StringUtil.isNull(kmImeetingMainForm.getFdEmceeId())) {
					kmImeetingMainForm.setFdEmceeId(UserUtil.getUser()
							.getFdId());
					kmImeetingMainForm.setFdEmceeName(UserUtil.getUser()
							.getFdName());
				}
				// 继承组织部门
				if (kmImeetingTemplate.getDocDept() != null) {
					kmImeetingMainForm.setDocDeptId(kmImeetingTemplate
							.getDocDept().getFdId());
					kmImeetingMainForm.setDocDeptName(kmImeetingTemplate
							.getDocDept().getDeptLevelNames());
				}
				// 继承会议主持人
				if (kmImeetingTemplate.getFdHost() != null) {
					kmImeetingMainForm.setFdHostId(kmImeetingTemplate
							.getFdHost().getFdId().toString());
					kmImeetingMainForm.setFdHostName(kmImeetingTemplate
							.getFdHost().getFdName());
				}
				// 纪要录入人
				if (kmImeetingTemplate.getFdSummaryInputPerson() != null) {
					kmImeetingMainForm
							.setFdSummaryInputPersonId(kmImeetingTemplate
									.getFdSummaryInputPerson().getFdId());
					kmImeetingMainForm
							.setFdSummaryInputPersonName(kmImeetingTemplate
									.getFdSummaryInputPerson().getFdName());
				}
				// 继承同步时机
				if (kmImeetingMainForm instanceof ISysAgendaMainForm) {
					kmImeetingMainForm
							.setSyncDataToCalendarTime(kmImeetingTemplate
									.getSyncDataToCalendarTime());
				}
				kmImeetingMainForm
						.setFdChangeMeetingFlag(ImeetingConstant.IS_CHANGEMEETING_NO);
				kmImeetingMainForm
						.setFdSummaryFlag(ImeetingConstant.MAIN_CONTAINS_SUMMARY_NO);
				kmImeetingMainForm.setIsNotify(ImeetingConstant.IS_NOTIFY_NO);
				kmImeetingMainForm
						.setFdRemark(kmImeetingTemplate.getFdRemark());
				getDispatchCoreService().initFormSetting(kmImeetingMainForm,
						"ImeetingMain", kmImeetingTemplate, "ImeetingMain",
						new RequestContext(request));
			}
			// 从会议预约中取数据
			if (StringUtil.isNotNull(bookId)) {
				copyMeetingInfoFromBook(request, kmImeetingMainForm, bookId);
			}
		}
		if (StringUtil.isNull(kmImeetingMainForm.getFdHurryDate())) {
			kmImeetingMainForm.setFdHurryDate("1");
		}
		return kmImeetingMainForm;
	}

	/**
	 * #21977 会议预约单转会议安排单时将预约信息带到会议中
	 */
	private void copyMeetingInfoFromBook(HttpServletRequest request,
			KmImeetingMainForm kmImeetingMainForm,
			String bookId) throws Exception {
		KmImeetingBook kmImeetingBook = (KmImeetingBook) getServiceImp(request)
				.findByPrimaryKey(
				bookId, KmImeetingBook.class, false);
		kmImeetingMainForm.setFdName(kmImeetingBook.getFdName());
		kmImeetingMainForm.setFdHoldDate(DateUtil.convertDateToString(
				kmImeetingBook.getFdHoldDate(), DateUtil.TYPE_DATETIME, null));
		kmImeetingMainForm
				.setFdFinishDate(DateUtil.convertDateToString(
						kmImeetingBook.getFdFinishDate(),
						DateUtil.TYPE_DATETIME, null));
		if (StringUtil.isNotNull(kmImeetingBook.getFdRemark())) {
			kmImeetingMainForm.setFdRemark(kmImeetingBook.getFdRemark());
		}
		kmImeetingMainForm.setFdPlaceId(kmImeetingBook.getFdPlace().getFdId());
		kmImeetingMainForm.setFdPlaceName(kmImeetingBook.getFdPlace()
				.getFdName());
	}

	@Override
	protected void loadActionForm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.loadActionForm(mapping, form, request, response);
		KmImeetingMainForm kmImeetingMainForm = (KmImeetingMainForm) form;
		// 如果是周期，且已取消会议，在流程引用时，需要获取原会议的信息
		if (StringUtil.isNotNull(kmImeetingMainForm.getFdRecurrenceStr()) // 周期会议
				&& StringUtil.isNotNull(kmImeetingMainForm.getCancelMeetingReason())) { // 已取消
			// 原始会议ID
			String oriId = kmImeetingMainForm.getFdOriId();
			if (StringUtil.isNotNull(oriId)) {
				request.setAttribute("isCancelMeeting", "true");
				KmImeetingMain model = (KmImeetingMain) getServiceImp(request).findByPrimaryKey(oriId);
				KmImeetingMainForm oriForm = new KmImeetingMainForm();
				getServiceImp(request).convertModelToForm(oriForm, model, new RequestContext());
				request.setAttribute("kmImeetingMainFormOri", oriForm);
			}
		}
		String id = request.getParameter("fdId");
		KmImeetingMain model = (KmImeetingMain) getServiceImp(request).findByPrimaryKey(id);
		
		if (model != null && model.getFdIsTopic() == null) {
			List fdAgendas = model.getKmImeetingAgendas();
			if (fdAgendas != null && fdAgendas.size() > 0) {
				for (int i = 0; i < fdAgendas.size(); i++) {
					KmImeetingAgenda kmImeetingAgenda = (KmImeetingAgenda) fdAgendas.get(i);
					if (StringUtil.isNotNull(kmImeetingAgenda.getFdFromTopicId())) {
						model.setFdIsTopic(true);
						getServiceImp(request).update(model);
						kmImeetingMainForm.setFdIsTopic("true");
						break;
					}
				}
			}
		}

		if (model.getFdIsVideo() != null && model.getFdIsVideo()) {
			Boolean canEnterVedio = false;
			String roomId = "";
			if ("0".equals(AliMeetingUtil.getServiceType())) {
				IKmImeetingMappingService kmImeetingMappingService = (IKmImeetingMappingService) SpringBeanUtil
						.getBean("kmImeetingMappingService");
				roomId = kmImeetingMappingService.getThirdIdByModel(model.getFdId(), KmImeetingMain.class.getName(), "kk");
				if (StringUtil.isNotNull(roomId)) { // roomId不为空，说明已经同步到了kk
					canEnterVedio = getServiceImp(request).canEnterKkVedio(model);
				}
			} else {
				canEnterVedio = getServiceImp(request).canEnterAliMeeting(model);
			}
			
			request.setAttribute("canEnterAliMeeting", canEnterVedio);
			request.setAttribute("canEnterKkVedio", canEnterVedio);
			request.setAttribute("roomId", roomId);
		}

		kmImeetingMainForm.setFdSummaryCompleteTime(DateUtil
				.convertDateToString(DateUtil.convertStringToDate(
						kmImeetingMainForm.getFdSummaryCompleteTime(),
						DateUtil.TYPE_DATE, request.getLocale()),
						DateUtil.TYPE_DATE, request.getLocale()));
		if (StringUtil.isNotNull(kmImeetingMainForm.getFdTemplateId())) {
			KmImeetingTemplate kmImeetingTemplate = (KmImeetingTemplate) getKmImeetingTemplateService(
					request).findByPrimaryKey(
					kmImeetingMainForm.getFdTemplateId());
			kmImeetingMainForm
					.setFdTemplateName(kmImeetingTemplate.getFdName());
			request.setAttribute("isTempAvailable",
					kmImeetingTemplate.getFdIsAvailable());
			request.setAttribute("fdNeedMultiRes",
					kmImeetingTemplate.getFdNeedMultiRes());
		}
		if (StringUtil.isNull(kmImeetingMainForm.getFdHurryDate())) {
			kmImeetingMainForm.setFdHurryDate("1");
		}
		// 获取会议ID用于按钮权限判断
		if ("true".equals(kmImeetingMainForm.getFdSummaryFlag())) {
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setWhereBlock(" kmImeetingSummary.fdMeeting.fdId=:fdId ");
			hqlInfo.setModelName(KmImeetingSummary.class.getName());
			hqlInfo.setParameter("fdId", kmImeetingMainForm.getFdId());
			List<KmImeetingSummary> list = getServiceImp(request).findList(
					hqlInfo);
			if (list != null && !list.isEmpty()) {
				request.setAttribute("summaryId", list.get(0).getFdId());
			}
		}
		
		// 阿里云视频会议口令
		if ("true".equals(kmImeetingMainForm.getFdIsVideo())
				&& AliMeetingUtil.isAliyunEnable()) {
			String fdMeetingCode = AliMeetingUtil.getAliMeetingCode(kmImeetingMainForm.getFdId());
			if (StringUtil.isNotNull(fdMeetingCode)) {
				request.setAttribute("fdMeetingCode", fdMeetingCode);
			}
		} else {
			// 云会议读取视频会议链接
			if ("true".equals(kmImeetingMainForm.getIsCloud())) {
				String url = getKmImeetingOutVedioService()
						.getVideoMeetingUrl(
								kmImeetingMainForm.getFdId());
				request.setAttribute("vedioUrl", url);
			}
		}
		
		setRepeat(kmImeetingMainForm, request);
		// 获取坐席安排座位号
		getSeatNum(kmImeetingMainForm, request);
	}

	private void getSeatNum(KmImeetingMainForm kmImeetingMainForm,
			HttpServletRequest request)
			throws Exception {
		String fdSeatPlanId = kmImeetingMainForm.getFdSeatPlanId();
		if(StringUtil.isNotNull(fdSeatPlanId)){
			KmImeetingSeatPlan seatPlan = (KmImeetingSeatPlan) getKmImeetingSeatPlanService()
					.findByPrimaryKey(fdSeatPlanId);
			KmImeetingMain kmImeetingMain = (KmImeetingMain) getServiceImp(
					request).findByPrimaryKey(kmImeetingMainForm.getFdId());
			String seatDetail = seatPlan.getFdSeatDetail();
			List<String> seatNumbers = new ArrayList<>();
			if (seatPlan.getFdIsTopicPlan()) {
				List<SysOrgElement> publicPerson = new ArrayList<>();
				if (kmImeetingMain.getFdHost() != null) {
					publicPerson.add(kmImeetingMain.getFdHost());
				}
				if (kmImeetingMain.getFdAttendPersons() != null) {
					publicPerson.addAll(kmImeetingMain.getFdAttendPersons());
				}
				if (kmImeetingMain.getFdSummaryInputPerson() != null) {
					publicPerson
							.add(kmImeetingMain.getFdSummaryInputPerson());
				}
				if (kmImeetingMain.getFdParticipantPersons() != null) {
					publicPerson
							.addAll(kmImeetingMain.getFdParticipantPersons());
				}
				JSONArray array = JSONArray.fromObject(seatDetail);
				if (publicPerson.contains(UserUtil.getUser())) {
					JSONObject obj = array.getJSONObject(1);
					JSONArray data = obj.getJSONArray("data");
					for (int j = 0; j < data.size(); j++) {
						JSONObject object = data.getJSONObject(j);
						JSONObject clazz = object
								.getJSONObject("clazz");
						String type = clazz.getString("type");
						if ("1".equals(type) || "0".equals(type)) {
							if (clazz.containsKey("elementId")) {
								String elementId = clazz
										.getString("elementId");
								if (UserUtil.getUser().getFdId()
										.equals(elementId)) {
									int num = object.getInt("number");
									seatNumbers.add(num + "号");
								}
							}
						}
					}
				} else {
					for (int i = 1; i < array.size(); i++) {
						JSONObject obj = array.getJSONObject(i);
						String fdId = obj.getString("fdId");
						IBaseModel model = getKmImeetingAgendaService(
								request).findByPrimaryKey(fdId, null, true);
						if (model != null
								&& model instanceof KmImeetingAgenda) {
							String subject = ((KmImeetingAgenda) model)
									.getDocSubject();
							JSONArray data = obj.getJSONArray("data");
							for (int j = 0; j < data.size(); j++) {
								JSONObject object = data.getJSONObject(j);
								JSONObject clazz = object
										.getJSONObject("clazz");
								String type = clazz.getString("type");
								if ("1".equals(type) || "0".equals(type)) {
									if (clazz.containsKey("elementId")) {
										String elementId = clazz
												.getString("elementId");
										if (UserUtil.getUser().getFdId()
												.equals(elementId)) {
											int num = object.getInt("number");
											seatNumbers.add(subject + "("
													+ num + "号)");
										}
									}
								}
							}
						}
					}

				}
			} else {
				JSONArray array = JSONArray.fromObject(seatDetail);
				for (int i = 0; i < array.size(); i++) {
					JSONObject obj = array.getJSONObject(i);
					JSONObject clazz = obj.getJSONObject("clazz");
					String type = clazz.getString("type");
					if ("1".equals(type) || "0".equals(type)) {
						if (clazz.containsKey("elementId")) {
							String elementId = clazz.getString("elementId");
							if (UserUtil.getUser().getFdId()
									.equals(elementId)) {
								int num = obj.getInt("number");
								seatNumbers.add(num + "号");
							}
						}
					}
				}
			}
			if (seatNumbers.size() > 0) {
				request.setAttribute("seatNumbers", seatNumbers);
			}
		}

	}

	private void setRepeat(KmImeetingMainForm kmImeetingMainForm,
			HttpServletRequest request) {
		String fdRecurrenceStr = kmImeetingMainForm.getFdRecurrenceStr();
		if (StringUtil.isNotNull(fdRecurrenceStr)) {
			Date fdHoldDate = DateUtil.convertStringToDate(
					kmImeetingMainForm.getFdHoldDate(),
					DateUtil.TYPE_DATETIME, request.getLocale());
			Map<String, String> infos = RecurrenceUtil
					.getRepeatInfo(fdRecurrenceStr, fdHoldDate);
			if (StringUtil.isNotNull(infos.get("FREQ"))) {
				kmImeetingMainForm.setFdRepeatType(infos.get("FREQ"));
			}
			if (StringUtil.isNotNull(infos.get("INTERVAL"))) {
				kmImeetingMainForm.setFdRepeatFrequency(infos.get("INTERVAL"));
			}
			if (StringUtil.isNotNull(infos.get("BYDAY"))) {
				kmImeetingMainForm.setFdRepeatTime(infos.get("BYDAY"));
			}
			if (StringUtil.isNotNull(infos.get("COUNT"))) {
				kmImeetingMainForm.setFdRepeatUtil(infos.get("COUNT"));
			} else if (StringUtil.isNotNull(infos.get("UNTIL"))) {
				kmImeetingMainForm.setFdRepeatUtil(infos.get("UNTIL"));
			}

			Map<String, String> params = RecurrenceUtil
					.parseRecurrenceStr(fdRecurrenceStr);
			String freq = params
					.get(ImeetingConstant.RECURRENCE_FREQ);
			String interval = params
					.get(ImeetingConstant.RECURRENCE_INTERVAL);
			String endType = params
					.get(ImeetingConstant.RECURRENCE_END_TYPE);
			kmImeetingMainForm.setRECURRENCE_FREQ(freq);
			if (ImeetingConstant.RECURRENCE_FREQ_WEEKLY.equals(freq)) {
				kmImeetingMainForm.setRECURRENCE_WEEKS(params
						.get(ImeetingConstant.RECURRENCE_BYDAY)
						.replaceAll(",", ";"));
			} else if (ImeetingConstant.RECURRENCE_FREQ_MONTHLY
					.equals(freq)) {
				String monthType = params
						.get(ImeetingConstant.RECURRENCE_MONTH_TYPE);
				kmImeetingMainForm.setRECURRENCE_MONTH_TYPE(monthType);

			}
			kmImeetingMainForm.setRECURRENCE_INTERVAL(interval);

			kmImeetingMainForm.setRECURRENCE_END_TYPE(endType);
			if (ImeetingConstant.RECURRENCE_END_TYPE_COUNT
					.equals(endType)) {
				kmImeetingMainForm.setRECURRENCE_COUNT(params
						.get(ImeetingConstant.RECURRENCE_COUNT));
			} else if (ImeetingConstant.RECURRENCE_END_TYPE_UNTIL
					.equals(endType)) {
				String until = params
						.get(ImeetingConstant.RECURRENCE_UNTIL);
				until = until.substring(0, 4) + "-"
						+ until.substring(4, 6) + "-"
						+ until.substring(6);
				kmImeetingMainForm.setRECURRENCE_UNTIL(until);
			}
		}
	}

	@Override
	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		KmImeetingMainForm kmImeetingMainForm = (KmImeetingMainForm) form;
		// 移动端创建周期性会议 重复规则处理
		initMobileRepeatInfo(kmImeetingMainForm);
		ActionForward actionForward = super.save(mapping, form, request,
				response);

		// 草稿状态跳到编辑页面
		if (ImeetingConstant.DOC_STATUS_DRAFT.equals(kmImeetingMainForm
				.getDocStatus())) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(
							"button.back",
							"kmImeetingMain.do?method=edit&fdId="
									+ kmImeetingMainForm.getFdId(), false)
					.save(request);
		}
		String bookId = request.getParameter("bookId");
		// #21977 使用会议预约转会议安排后删除对应的会议预约
		if (StringUtil.isNotNull(bookId)) {
			getKmImeetingBookService(request).delete(bookId);
		}
		return actionForward;
	}

	private void initMobileRepeatInfo(KmImeetingMainForm kmImeetingMainForm)
			throws Exception {
		String freq = kmImeetingMainForm.getRECURRENCE_FREQ();
		if (!ImeetingConstant.RECURRENCE_FREQ_NO.equals(freq)) {
			String byday = null;
			if (ImeetingConstant.RECURRENCE_FREQ_WEEKLY.equals(freq)) {
				byday = kmImeetingMainForm.getRECURRENCE_WEEKS()
						.replaceAll(";", ",");
			} else if (ImeetingConstant.RECURRENCE_FREQ_MONTHLY
					.equals(freq)) {
				if (ImeetingConstant.RECURRENCE_MONTH_TYPE_WEEK
						.equals(kmImeetingMainForm
								.getRECURRENCE_MONTH_TYPE())) {
					Date _startDate = format2
							.parse(kmImeetingMainForm.getFdHoldDate());
					Calendar c = Calendar.getInstance();
					c.setFirstDayOfWeek(Calendar.MONDAY);
					c.setTime(_startDate);
					int weekOfMonth = c.get(Calendar.DAY_OF_WEEK_IN_MONTH);
					int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
					String weekStr = weeks[dayOfWeek - 1];
					byday = weekOfMonth + weekStr;
				}
			}
			Map<String, String> params = new HashMap<>();
			params.put("FREQ", kmImeetingMainForm.getRECURRENCE_FREQ());
			params.put("ENDTYPE", kmImeetingMainForm.getRECURRENCE_END_TYPE());
			params.put("COUNT", kmImeetingMainForm.getRECURRENCE_COUNT());
			params.put("UNTIL", kmImeetingMainForm.getRECURRENCE_UNTIL());
			params.put("INTERVAL", kmImeetingMainForm.getRECURRENCE_INTERVAL());
			params.put("BYDAY", byday);
			String recurrenceStr = RecurrenceUtil.buildRecurrenceStr(params);
			kmImeetingMainForm.setFdRecurrenceStr(recurrenceStr);
		}
	}

	@Override
	public ActionForward update(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		KmImeetingMainForm kmImeetingMainForm = (KmImeetingMainForm) form;
		// 移动端周期性会议 重复规则处理
		initMobileRepeatInfo(kmImeetingMainForm);
		ActionForward actionForward = super.update(mapping, form, request,
				response);
		// 草稿状态跳到编辑页面
		if (ImeetingConstant.DOC_STATUS_DRAFT.equals(kmImeetingMainForm
				.getDocStatus())) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(
							"button.back",
							"kmImeetingMain.do?method=edit&fdId="
									+ kmImeetingMainForm.getFdId(), false)
					.save(request);
		}
		return actionForward;
	}


	public ActionForward updateChange(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmImeetingMainForm kmImeetingMainForm = (KmImeetingMainForm) form;
		// 如果会议组织人有变更，需要重置"承接工作"
		HQLInfo info = new HQLInfo();
		info.setSelectBlock("kmImeetingMain.fdEmcee.fdId");
		info.setWhereBlock("kmImeetingMain.fdId = :fdId");
		info.setParameter("fdId", kmImeetingMainForm.getFdId());
		List<String> list = getServiceImp(request).getBaseDao().findValue(info);
		if (CollectionUtils.isNotEmpty(list)) {
			String fdEmceeId = list.get(0);
			if (fdEmceeId == null || !fdEmceeId.equals(kmImeetingMainForm.getFdEmceeId())) {
				kmImeetingMainForm.setEmccType("UnEmccDone");
			}
		}
		ActionForward actionForward = this.update(mapping, form, request, response);
		//会议变更
		if(!UserUtil.getUser().getFdId().equals(kmImeetingMainForm.getDocCreatorId())){
			IBackgroundAuthService backgroundAuthService = (IBackgroundAuthService) SpringBeanUtil.getBean("backgroundAuthService");
			backgroundAuthService.switchUserById(kmImeetingMainForm.getDocCreatorId(), new Runner() {
				@Override
				public Object run(Object form) throws Exception {
					KmImeetingMainForm kmImeetingMainForm = (KmImeetingMainForm) form;
					ILbpmProcessService lbpmProcessService = (ILbpmProcessService) SpringBeanUtil.getBean("lbpmProcessService");
					LbpmProcess process = (LbpmProcess) lbpmProcessService.findByPrimaryKey(kmImeetingMainForm.getFdId());
					SysWfBusinessForm businessForm = kmImeetingMainForm.getSysWfBusinessForm();
					HQLInfo hqlInfo = new HQLInfo();
					hqlInfo.setModelName("com.landray.kmss.sys.lbpm.engine.persistence.model.LbpmWorkitem");
					hqlInfo.setWhereBlock("lbpmWorkitem.fdProcess.fdId = :fdProcess");
					hqlInfo.setParameter("fdProcess",process.getFdId());
					LbpmWorkitem lbpmWorkitem=(LbpmWorkitem)lbpmProcessService.findFirstOne(hqlInfo);
					JSONObject json = new JSONObject();
					json.put("taskId",lbpmWorkitem.getFdId());
					json.put("processId",process.getFdId());
					json.put("activityType","draftWorkitem");
					json.put("operationType","drafter_submit");
					businessForm.setCanStartProcess("true");
					JSONObject param = new JSONObject();
					param.put("operationName","变更会议");
					param.put("auditNote","变更会议");
					json.put("param",param);
					businessForm.setFdParameterJson(json.toString());

					TempDataAccess.removeTempData(lbpmWorkitem);
					lbpmProcessService.updateByPanel((LbpmProcessForm)businessForm);
					return null;
				}
			}, kmImeetingMainForm);
		}
		return actionForward;
	}

	@Override
	protected void changeFindPageHQLInfo(HttpServletRequest request,
			HQLInfo hqlInfo) throws Exception {
		super.changeFindPageHQLInfo(request, hqlInfo);
		CriteriaValue cv = new CriteriaValue(request);
		// 业务导航查询参数
		String status = cv.poll("status");
		String type = request.getParameter("type");
		
		
		String[] meetingCondition = cv.polls("meetingCondition");
		
		if (meetingCondition != null && meetingCondition.length > 0) {
			buildMeetingStatusHql(request, hqlInfo, meetingCondition);
		}
		
		String except = cv.poll("except");
		String[] exceptValue = null;
		if(StringUtil.isNotNull(except)&&except.indexOf(":")>-1){
			exceptValue=except.split(":");
		}
		
		if(exceptValue!=null){
			String whereBlock=hqlInfo.getWhereBlock();
			if (StringUtil.isNull(whereBlock)) {
				whereBlock = " 1=1 ";
			}
			if (exceptValue[1].indexOf("_") > -1) {
				String[] _exceptValue = exceptValue[1].split("_");
				for (int i = 0; i < _exceptValue.length; i++) {
					whereBlock = whereBlock + " and kmImeetingMain.docStatus != :docStatus" + i;
					hqlInfo.setParameter("docStatus" + i, _exceptValue[i]);
				}
			} else {
				whereBlock = whereBlock + " and kmImeetingMain.docStatus != :docStatus";
				hqlInfo.setParameter("docStatus", exceptValue[1]);
			}
			if(StringUtil.isNotNull(whereBlock)){
				hqlInfo.setWhereBlock(whereBlock);
			}
			
		}
		
		if (StringUtil.isNotNull(status)) {
			String whereBlock = hqlInfo.getWhereBlock();
			if (StringUtil.isNull(whereBlock)) {
				whereBlock = " 1=1 ";
			}
			hqlInfo.setWhereBlock(whereBlock
					+ " and kmImeetingMain.docStatus=:status");
			hqlInfo.setParameter("status", status);
		}
		
		String isCloud = request.getParameter("isCloud");
		if (StringUtil.isNotNull(isCloud)) {
			String whereBlock = hqlInfo.getWhereBlock();
			if (StringUtil.isNull(whereBlock)) {
				whereBlock = " 1=1 ";
			}
			hqlInfo.setWhereBlock(whereBlock
					+ " and kmImeetingMain.isCloud=:isCloud");
			hqlInfo.setParameter("isCloud",Boolean.parseBoolean(isCloud));
		}
		
		if (StringUtil.isNotNull(type) && "approval".equals(type)) {
			String whereBlock = hqlInfo.getWhereBlock();
			if (StringUtil.isNull(whereBlock)) {
				hqlInfo.setWhereBlock(" 1=1 ");
			}
			SysFlowUtil.buildLimitBlockForMyApproval("kmImeetingMain", hqlInfo);
			hqlInfo.setCheckParam(SysAuthConstant.CheckType.AllCheck,
					SysAuthConstant.AllCheck.NO);
		}

		// 分类导航查询条件
		String categoryId = cv.poll("fdTemplate");
		if (StringUtil.isNotNull(categoryId)) {
			// 默认 show all
			SysCategoryMain category = (SysCategoryMain) getCategoryMainService()
					.findByPrimaryKey(categoryId, null, true);
			if (category != null) {
				hqlInfo
						.setWhereBlock(StringUtil
								.linkString(hqlInfo.getWhereBlock(), " and ",
										" kmImeetingMain.fdTemplate.docCategory.fdHierarchyId like :category "));
				hqlInfo.setParameter("category", category.getFdHierarchyId()
						+ "%");
			} else {
				hqlInfo.setWhereBlock(StringUtil.linkString(hqlInfo
						.getWhereBlock(), " and ",
						" kmImeetingMain.fdTemplate.fdId = :template "));
				hqlInfo.setParameter("template", categoryId);
			}
		}

		// 我的会议查询条件
		String mydoc = cv.poll("mymeeting");
		if (StringUtil.isNotNull(mydoc)) {
			buildMyMeetingHql(request, hqlInfo, mydoc);
		} else {
			// TA的会议查询条件
			String tadoc = cv.poll("tameeting");
			if (StringUtil.isNotNull(tadoc)) {
				buildTAMeetingHql(request, hqlInfo, tadoc);
			}
		}

		// 会议按召开状态
		String[] meetingStatuses = cv.polls("meetingStatus");
		if (meetingStatuses != null && meetingStatuses.length > 0) {
			buildMeetingStatusHql(request, hqlInfo, meetingStatuses);
		}

		String fdPlace = cv.poll("fdPlace");
		if (StringUtil.isNotNull(fdPlace)) {
			buildMeetingResCateHql(request, hqlInfo, fdPlace);
		}
		String role = cv.poll("role");
		if (StringUtil.isNotNull(role)) {
			buildMeetingRoleHql(request, hqlInfo, role);
		}

		String isAll = request.getParameter("isAll");
		if (!"true".equals(isAll)) {
			// 周期性会议
			String isCycle = request.getParameter("isCycle");
			String cycle = request.getParameter("cycle");
			if (!"true".equals(cycle)) {
				if ("true".equals(isCycle)) {
					String whereBlock = hqlInfo.getWhereBlock();
					whereBlock = StringUtil.linkString(whereBlock, " and ",
							" (kmImeetingMain.fdRecurrenceStr is not null or kmImeetingMain.fdRecurrenceStr!='NO') ");
					hqlInfo.setWhereBlock(whereBlock);
				} else {
					String whereBlock = hqlInfo.getWhereBlock();
					whereBlock = StringUtil.linkString(whereBlock, " and ",
							" (kmImeetingMain.fdRecurrenceStr is null or kmImeetingMain.fdRecurrenceStr='NO') ");
					hqlInfo.setWhereBlock(whereBlock);
				}
			}

			// 视频会议
			String isVideo = request.getParameter("isVideo");
			if (StringUtil.isNotNull(isVideo)) {
				if ("true".equals(isVideo)) {
					String whereBlock = hqlInfo.getWhereBlock();
					whereBlock = StringUtil.linkString(whereBlock, " and ",
							" (kmImeetingMain.fdIsVideo = :isVideo and kmImeetingMain.fdTemplate is null) ");
					hqlInfo.setParameter("isVideo", Boolean.TRUE);
					hqlInfo.setWhereBlock(whereBlock);
				} else {
					String whereBlock = hqlInfo.getWhereBlock();
					whereBlock = StringUtil.linkString(whereBlock, " and ",
							" (kmImeetingMain.fdTemplate is not null) ");
					hqlInfo.setWhereBlock(whereBlock);
				}
			}
		}
		//"我要参加的"页签 默认显示周期会议
		KmImeetingConfig kmImeetingConfig = new KmImeetingConfig();
		if("myAttend".equals(mydoc)){
			String useCyclicity =kmImeetingConfig.getUseCyclicity();
			if("1".equals(useCyclicity)){//1表示不使用
				//拼接非周期性会议条件
				String whereBlock = hqlInfo.getWhereBlock();
				whereBlock = StringUtil.linkString(whereBlock, " and ",
						" (kmImeetingMain.fdRecurrenceStr is null or kmImeetingMain.fdRecurrenceStr='NO') ");
				hqlInfo.setWhereBlock(whereBlock);
			}else if("3".equals(useCyclicity)) {//指定人可以使用
				String UseCyclicityPersonId = kmImeetingConfig.getUseCyclicityPersonId();
				String curId = UserUtil.getUser().getFdId();
				//当指定人为空或当前登录人非指定人时，只能查看普通会议
				if (StringUtil.isNull(UseCyclicityPersonId) || !UseCyclicityPersonId.contains(curId)) {
						//拼接非周期性会议条件
						String whereBlock = hqlInfo.getWhereBlock();
						whereBlock = StringUtil.linkString(whereBlock, " and ",
								" (kmImeetingMain.fdRecurrenceStr is null or kmImeetingMain.fdRecurrenceStr='NO') ");
						hqlInfo.setWhereBlock(whereBlock);
				}
			}
		}
		String cycleMeetingCondition = cv.poll("cycleMeetingCondition");
		if (StringUtil.isNotNull(cycleMeetingCondition)) {
			String whereBlock = hqlInfo.getWhereBlock();
			whereBlock = StringUtil.linkString(whereBlock, " and ",
					" (kmImeetingMain.fdRecurrenceStr like :cycleType) ");
			hqlInfo.setParameter("cycleType",
					"%" + cycleMeetingCondition + "%");
			hqlInfo.setWhereBlock(whereBlock);
		}


		CriteriaUtil.buildHql(cv, hqlInfo, KmImeetingMain.class);

		// 上会材料列表页面，除管理员外，仅显示自己是材料责任人的会议
		String isUploadAtt = request.getParameter("isUploadAtt");
		if (StringUtil.isNotNull(isUploadAtt) && "true".equals(isUploadAtt)) {
			if (UserUtil.getKMSSUser().isAdmin() == false) {
				hqlInfo
						.setJoinBlock(" left join kmImeetingMain.kmImeetingAgendas kmImeetingAgenda");
				String whereBlock = hqlInfo.getWhereBlock();
				whereBlock = StringUtil
						.linkString(
								whereBlock,
								" and ",
								" kmImeetingMain.fdId=kmImeetingAgenda.fdMain.fdId and  kmImeetingAgenda.docRespons.fdId=:docResponsId ");
				hqlInfo.setParameter("docResponsId", UserUtil.getUser()
						.getFdId());
				hqlInfo.setCheckParam(SysAuthConstant.CheckType.AllCheck,
						SysAuthConstant.AllCheck.NO);
				hqlInfo.setWhereBlock(whereBlock);
			}
			// 只显示发布、已取消的文档
			hqlInfo
					.setWhereBlock(StringUtil
							.linkString(hqlInfo.getWhereBlock(), " and ",
									" (kmImeetingMain.docStatus='30' or kmImeetingMain.docStatus='41') and kmImeetingMain.isCloud != :isCloud"));
			hqlInfo.setParameter("isCloud", Boolean.TRUE);
			String orderby = request.getParameter("orderby");
			String ordertype = request.getParameter("ordertype");
			if (StringUtil.isNotNull(orderby)) {
				orderby = "kmImeetingMain." + orderby;
				if (ordertype != null && "down".equalsIgnoreCase(ordertype)) {
					orderby += " desc";
				}
			}
			hqlInfo.setOrderBy(orderby);
		}
		hqlInfo.setDistinctType(HQLInfo.DISTINCT_YES);
	}


	public ActionForward listMySummary(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-listMySummary", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			String s_pageno = request.getParameter("pageno");
			String s_rowsize = request.getParameter("rowsize");
			int pageno = 0;
			int rowsize = SysConfigParameters.getRowSize();
			if (s_pageno != null && s_pageno.length() > 0 && Integer.parseInt(s_pageno) > 0) {
				pageno = Integer.parseInt(s_pageno);
			}
			if (s_rowsize != null && s_rowsize.length() > 0 && Integer.parseInt(s_rowsize) > 0) {
				rowsize = Integer.parseInt(s_rowsize);
			}
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setOrderBy(" kmImeetingMain.fdHoldDate desc");
			hqlInfo.setPageNo(pageno);
			hqlInfo.setRowSize(rowsize);
			String whereBlock = "(kmImeetingMain.fdSummaryInputPerson.fdId=:userid or kmImeetingMain.docCreator.fdId=:userid)  and kmImeetingMain.fdSummaryFlag=:fdSummaryFlag  and kmImeetingMain.docStatus = '30'";
			hqlInfo.setWhereBlock(whereBlock);
			hqlInfo.setParameter("userid", UserUtil.getUser().getFdId());
			hqlInfo.setParameter("fdSummaryFlag", Boolean.FALSE);
			Page page = getServiceImp(request).findPage(hqlInfo);
			// 记录操作日志
			UserOperHelper.logFindAll(page.getList(),
					getServiceImp(request).getModelName());
			request.setAttribute("queryPage", page);
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-listMySummary", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("listMySummary", mapping, form, request, response);
		}
	}

	private List findMyfeedback(HttpServletRequest request,String fdOperateType)
			throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setSelectBlock("kmImeetingMainFeedback.fdMeeting.fdId");
		String where = " kmImeetingMainFeedback.docCreator.fdId=:userId ";
		if("myHaveAttend".equals(fdOperateType)){
			where += " and kmImeetingMainFeedback.fdOperateType='"+ImeetingConstant.MEETING_FEEDBACK_OPT_ATTEND+"' ";
		}
		hqlInfo.setWhereBlock(where);
		hqlInfo.setParameter("userId", UserUtil.getUser().getFdId());
		List fs = getKmImeetingMainFeedbackService(request).findList(hqlInfo);
		return fs;
	}

	/**
	 * 我的会议HQL
	 */
	private void buildMyMeetingHql(HttpServletRequest request, HQLInfo hqlInfo,
			String mydoc) throws Exception {
		String whereBlock = hqlInfo.getWhereBlock();
		List authOrgIds = UserUtil.getKMSSUser().getUserAuthInfo()
				.getAuthOrgIds();

		if ("myAttend".equals(mydoc)) { // 我要参加
			// 去除重复
			hqlInfo.setDistinctType(HQLInfo.DISTINCT_YES);
			hqlInfo.setJoinBlock(" left join kmImeetingMain.fdAttendPersons attendPersons left join kmImeetingMain.fdHost fdHost left join kmImeetingMain.fdSummaryInputPerson fdSummaryInputPerson");
			// 参加的所有会议（包括参与者中有我/TA，我/TA主持，我/TA纪要）
			List feedbackList = findMyfeedback(request,"myHaveAttend");
			if (feedbackList.size() > 0) {
				whereBlock = StringUtil.linkString(whereBlock, " and ",
						" ( fdHost.fdId=:userid or fdSummaryInputPerson.fdId=:userid or "
							+ HQLUtil.buildLogicIN("kmImeetingMain.fdId", feedbackList)
							+ " or (" + HQLUtil.buildLogicIN("attendPersons.fdId", authOrgIds)
							+ "))");
			}else{
				whereBlock = StringUtil.linkString(whereBlock, " and ",
						" ( fdHost.fdId=:userid or fdSummaryInputPerson.fdId=:userid "
								+ " or (" + HQLUtil.buildLogicIN("attendPersons.fdId", authOrgIds)
								+ "))");
			}
			//#151052 保持和会议工作台【我要参加】的会议数量保持一致，参与人中有本人的会议是否需要回执都显示在【我要参与】列表中
			//hqlInfo.setParameter("fdNeedFeedback", Boolean.TRUE);
			hqlInfo.setParameter("userid", UserUtil.getUser().getFdId());
			whereBlock = StringUtil.linkString(whereBlock, " and "," kmImeetingMain.fdFinishDate>:attendDate ");
			hqlInfo.setParameter("attendDate", new Date());
			hqlInfo.setWhereBlock(whereBlock);
		} else if ("myHaveAttend".equals(mydoc)) { // 我已参加
				// 去除重复
				hqlInfo.setDistinctType(HQLInfo.DISTINCT_YES);
				hqlInfo.setJoinBlock(" left join kmImeetingMain.fdAttendPersons attendPersons left join kmImeetingMain.fdHost fdHost left join kmImeetingMain.fdSummaryInputPerson fdSummaryInputPerson");
				// 参加的所有会议（包括参与者中有我/TA，我/TA主持，我/TA纪要）
			List feedbackList = findMyfeedback(request,"myHaveAttend");
			if (feedbackList.size() > 0) {
				whereBlock = StringUtil.linkString(whereBlock, " and ",
						" ( fdHost.fdId=:userid or fdSummaryInputPerson.fdId=:userid or "
								+ HQLUtil.buildLogicIN("kmImeetingMain.fdId", feedbackList)
								+ " or (" + HQLUtil.buildLogicIN(
										"attendPersons.fdId", authOrgIds)
								+ " and  kmImeetingMain.fdNeedFeedback = :fdNeedFeedback ))");
			} else {
				whereBlock = StringUtil.linkString(whereBlock, " and ",
						" ( fdHost.fdId=:userid or fdSummaryInputPerson.fdId=:userid "
								+ " or (" + HQLUtil.buildLogicIN(
										"attendPersons.fdId", authOrgIds)
								+ " and  kmImeetingMain.fdNeedFeedback = :fdNeedFeedback ))");
			}
			hqlInfo.setParameter("fdNeedFeedback", Boolean.TRUE);
			hqlInfo.setParameter("userid", UserUtil.getUser().getFdId());
			whereBlock = StringUtil.linkString(whereBlock, " and "," kmImeetingMain.fdFinishDate<=:attendDate ");
			hqlInfo.setParameter("attendDate", new Date());
			hqlInfo.setWhereBlock(whereBlock);
		} else if ("myCreate".equals(mydoc)) {
			// 我发起的
			whereBlock = StringUtil.linkString(whereBlock, " and ",
					" kmImeetingMain.docCreator.fdId=:docCreator");
			hqlInfo.setWhereBlock(whereBlock);
			hqlInfo.setParameter("docCreator", UserUtil.getUser().getFdId());
			hqlInfo.setCheckParam(SysAuthConstant.CheckType.AllCheck,
					SysAuthConstant.AllCheck.NO);
		} else if ("myApproved".equals(mydoc)) {
			// 我已审的
			SysFlowUtil.buildLimitBlockForMyApproved("kmImeetingMain", hqlInfo);
			hqlInfo.setCheckParam(SysAuthConstant.CheckType.AllCheck,
					SysAuthConstant.AllCheck.NO);
		} else if ("myApproval".equals(mydoc)) {
			// 待我审的
			SysFlowUtil.buildLimitBlockForMyApproval("kmImeetingMain", hqlInfo);
			hqlInfo.setCheckParam(SysAuthConstant.CheckType.AllCheck,
					SysAuthConstant.AllCheck.NO);
		} else if ("mySummary".equals(mydoc)) {
			// 我纪要的
			hqlInfo.setDistinctType(HQLInfo.DISTINCT_YES);
			whereBlock = StringUtil.linkString(whereBlock, " and ",
					"kmImeetingMain.fdSummaryInputPerson.fdId=:userid");
			hqlInfo.setWhereBlock(whereBlock);
			hqlInfo.setParameter("userid", UserUtil.getUser().getFdId());
			hqlInfo.setCheckParam(SysAuthConstant.CheckType.AllCheck,
					SysAuthConstant.AllCheck.NO);
		} else if ("myHost".equals(mydoc)) {
			whereBlock = StringUtil.linkString(whereBlock, " and ",
					" kmImeetingMain.fdHost.fdId = :userid");
			hqlInfo.setWhereBlock(whereBlock);
			hqlInfo.setParameter("userid", UserUtil.getUser().getFdId());
			hqlInfo.setCheckParam(SysAuthConstant.CheckType.AllCheck, SysAuthConstant.AllCheck.NO);
		}
		if ("myEmcc".equals(mydoc)) {
			whereBlock = StringUtil.linkString(whereBlock, " and ", " kmImeetingMain.fdEmcee.fdId = :userid");
			hqlInfo.setWhereBlock(whereBlock);
			hqlInfo.setParameter("userid", UserUtil.getUser().getFdId());
			hqlInfo.setCheckParam(SysAuthConstant.CheckType.AllCheck, SysAuthConstant.AllCheck.NO);
		}

		if ("myAttend".equals(mydoc)) {
			hqlInfo.setOrderBy("kmImeetingMain.fdHoldDate desc");
			hqlInfo.setWhereBlock(hqlInfo.getWhereBlock()
					+ " and kmImeetingMain.docStatus=:status");
			hqlInfo.setParameter("status", SysDocConstant.DOC_STATUS_PUBLISH);
		}
	}

	/**
	 * TA的会议HQL
	 */
	private void buildTAMeetingHql(HttpServletRequest request, HQLInfo hqlInfo,
			String tadoc) throws Exception {
		String whereBlock = hqlInfo.getWhereBlock();
		String userid = request.getParameter("userid");
		if (StringUtil.isNull(userid)) {
			return;
		}
		SysOrgElement p = getSysOrgCoreService(request)
				.findByPrimaryKey(userid);
		List authOrgIds = getSysOrgCoreService(request).getOrgsUserAuthInfo(p)
				.getAuthOrgIds();
		if ("attend".equals(tadoc)) {
			// 去除重复
			hqlInfo.setDistinctType(HQLInfo.DISTINCT_YES);
			// TA参加的会议
			hqlInfo
					.setJoinBlock("left join kmImeetingMain.fdAttendPersons fdAttendPersons");
			whereBlock = StringUtil.linkString(whereBlock, " and ", " ("
					+ HQLUtil.buildLogicIN("fdAttendPersons.fdId", authOrgIds)
					+ ")");
			hqlInfo.setWhereBlock(whereBlock);
		} else if ("create".equals(tadoc)) {
			// 发起
			whereBlock = StringUtil.linkString(whereBlock, " and ",
					" kmImeetingMain.docCreator.fdId=:docCreator");
			hqlInfo.setWhereBlock(whereBlock);
			hqlInfo.setParameter("docCreator", userid);
			// hqlInfo.setCheckParam(SysAuthConstant.CheckType.AllCheck,
			// SysAuthConstant.AllCheck.NO);
		} else if ("host".equals(tadoc)) {
			// 主持
			whereBlock = StringUtil.linkString(whereBlock, " and ",
					" kmImeetingMain.fdHost.fdId=:userid");
			hqlInfo.setWhereBlock(whereBlock);
			hqlInfo.setParameter("userid", userid);
			// hqlInfo.setCheckParam(SysAuthConstant.CheckType.AllCheck,
			// SysAuthConstant.AllCheck.NO);
		} else if ("sum".equals(tadoc)) {
			// 纪要
			whereBlock = StringUtil.linkString(whereBlock, " and ",
					" kmImeetingMain.fdSummaryInputPerson.fdId=:userid ");
			hqlInfo.setWhereBlock(whereBlock);
			hqlInfo.setParameter("userid", userid);
			// hqlInfo.setCheckParam(SysAuthConstant.CheckType.AllCheck,
			// SysAuthConstant.AllCheck.NO);
		} else if ("emcc".equals(tadoc)) {
			// 组织人
			whereBlock = StringUtil.linkString(whereBlock, " and ",
					" kmImeetingMain.fdEmcee.fdId=:userid ");
			hqlInfo.setWhereBlock(whereBlock);
			hqlInfo.setParameter("userid", userid);
			// hqlInfo.setCheckParam(SysAuthConstant.CheckType.AllCheck,
			// SysAuthConstant.AllCheck.NO);
		}
	}

	/**
	 * 会议状态HQL
	 */
	private void buildMeetingStatusHql(HttpServletRequest request,
			HQLInfo hqlInfo, String[] meetingStatuses) throws Exception {
		String statusWhereBlock = "";
		HashMap<String, Object> statusParameters = new HashMap<String, Object>();
		for (String status : meetingStatuses) {
			// 已召开
			if ("hold".equals(status)) {
				statusWhereBlock = StringUtil
						.linkString(
								statusWhereBlock,
								" or ",
								" kmImeetingMain.fdFinishDate<=:afterFdHoldDate and kmImeetingMain.docStatus = '30' ");
				statusParameters.put("afterFdHoldDate", new Date());
			}
			// 进行中的会议
			if ("holding".equals(status)) {
				statusWhereBlock = StringUtil
						.linkString(
								statusWhereBlock,
								" or ",
								" kmImeetingMain.fdHoldDate<=:fdDate and kmImeetingMain.fdFinishDate>=:fdDate and kmImeetingMain.docStatus = '30' ");
				Date now = new Date();
				statusParameters.put("fdDate", now);
			}
			// 未召开会议
			if ("unHold".equals(status)) {
				statusWhereBlock = StringUtil
						.linkString(
								statusWhereBlock,
								" or ",
								" kmImeetingMain.fdHoldDate>=:beforeFdHoldDate and kmImeetingMain.docStatus = '30' ");
				statusParameters.put("beforeFdHoldDate", new Date());
			}
			// 废弃、草稿、待审、发布、取消
			if ("00".equals(status) || "10".equals(status)
					|| "11".equals(status) || "20".equals(status)
					|| "30".equals(status) || "41".equals(status)) {
				statusWhereBlock = StringUtil.linkString(statusWhereBlock,
						" or ", " kmImeetingMain.docStatus=:docStatus" + status
								+ " ");
				statusParameters.put("docStatus" + status, status);
			}
		}
		if (StringUtil.isNotNull(statusWhereBlock)) {
			statusWhereBlock = " ( " + statusWhereBlock + " ) ";
			String whereBlock = hqlInfo.getWhereBlock();
			whereBlock = StringUtil.linkString(whereBlock, " and ",
					statusWhereBlock);
			hqlInfo.setWhereBlock(whereBlock);
			for (String key : statusParameters.keySet()) {
				hqlInfo.setParameter(key, statusParameters.get(key));
			}
		}

	}

	/**
	 * 会议室类别HQL
	 */
	private void buildMeetingResCateHql(HttpServletRequest request,
			HQLInfo hqlInfo, String fdPlace) throws Exception {
		String whereBlock = hqlInfo.getWhereBlock();
		whereBlock = StringUtil.linkString(whereBlock, " and ",
				"fdPlace.docCategory.fdId=:fdPlace");
		hqlInfo.setWhereBlock(whereBlock);
		hqlInfo.setParameter("fdPlace", fdPlace);
	}

	private void buildMeetingRoleHql(HttpServletRequest request,
			HQLInfo hqlInfo, String role) throws Exception {
		String currentUserId = UserUtil.getUser().getFdId();
		String whereBlock = hqlInfo.getWhereBlock();
		switch (role) {
		case "0":
			break;
		case "1":
			whereBlock = StringUtil.linkString(whereBlock, " and ",
					"fdHost.fdId=:fdHostId");
			hqlInfo.setParameter("fdHostId", currentUserId);
			hqlInfo.setCheckParam(SysAuthConstant.CheckType.AllCheck,
					SysAuthConstant.AllCheck.NO);
			break;
		case "2":
			whereBlock = StringUtil.linkString(whereBlock, "and ",
					"fdEmcee.fdId=:fdEmceeId");
			hqlInfo.setParameter("fdEmceeId", currentUserId);
			hqlInfo.setCheckParam(SysAuthConstant.CheckType.AllCheck,
					SysAuthConstant.AllCheck.NO);
			break;
		case "3":
			whereBlock = StringUtil.linkString(whereBlock, " and ",
					"fdSummaryInputPerson.fdId=:fdSummaryId");
			hqlInfo.setParameter("fdSummaryId", currentUserId);
			hqlInfo.setCheckParam(SysAuthConstant.CheckType.AllCheck,
					SysAuthConstant.AllCheck.NO);
			break;
		case "4":
			SysOrgElement p = getSysOrgCoreService(request)
					.findByPrimaryKey(currentUserId);
			List authOrgIds = getSysOrgCoreService(request)
					.getOrgsUserAuthInfo(p).getAuthOrgIds();
			hqlInfo.setDistinctType(HQLInfo.DISTINCT_YES);
			hqlInfo.setJoinBlock(
					"left join kmImeetingMain.fdAttendPersons fdAttendPersons");
			whereBlock = StringUtil.linkString(whereBlock, " and ", " ("
					+ HQLUtil.buildLogicIN("fdAttendPersons.fdId", authOrgIds)
					+ ")");
			break;
		case "5":
			whereBlock = StringUtil.linkString(whereBlock, " and ",
					"docCreator.fdId=:creatorId");
			hqlInfo.setParameter("creatorId", currentUserId);
			hqlInfo.setCheckParam(SysAuthConstant.CheckType.AllCheck,
					SysAuthConstant.AllCheck.NO);
			break;
		}
		hqlInfo.setWhereBlock(whereBlock);
	}

	// 是否日期+时间格式
	private boolean isDateTime(String date) {
		String pattern = ResourceUtil.getString("date.format."
				+ DateUtil.TYPE_DATETIME, null, null);
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		try {
			format.parse(date);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	// 返回会议类别完整路径
	private String getTemplatePath(KmImeetingTemplate kmImeetingTemplate) {
		String templatePath = "";
		if (kmImeetingTemplate != null) {
			SysCategoryMain sysCategoryMain = kmImeetingTemplate
					.getDocCategory();
			SysCategoryBaseModel sysCategoryBaseModel = (SysCategoryBaseModel) sysCategoryMain
					.getFdParent();
			if (sysCategoryBaseModel != null) {
				do {
					templatePath = sysCategoryBaseModel.getFdName() + "/"
							+ templatePath;
					sysCategoryBaseModel = (SysCategoryBaseModel) sysCategoryBaseModel
							.getFdParent();
				} while (sysCategoryBaseModel != null);
				templatePath = templatePath + sysCategoryMain.getFdName() + "/"
						+ kmImeetingTemplate.getFdName();
			} else {
				templatePath = sysCategoryMain.getFdName() + "/"
						+ kmImeetingTemplate.getFdName();
			}
		}
		return templatePath;
	}

	// 分类扩充权限文档维护
	public ActionForward optAllList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-optAllList", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			super.listChildren(mapping, form, request, response);
		} catch (Exception e) {
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("Action-optAllList", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("optAllList", mapping, form, request,
					response);
		}
	}

	// 计算预计与会人员(AJAX形式)
	public ActionForward caculateAttendNumber(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-caculate", true, getClass());
		String personIds = request.getParameter("personIds");
		String fdMeetingId = request.getParameter("fdMeetingId");
		int count = 0;
		List<SysOrgElement> diffUnionList = new ArrayList<SysOrgElement>();
		JSONObject json = new JSONObject();
		if (StringUtil.isNotNull(personIds)) {
			List orgIds = ArrayUtil.convertArrayToList(personIds.split(";"));// 拆成List
			List<SysOrgElement> attend = getSysOrgCoreService(request).expandToPerson(
					orgIds);
			if (attend != null && !attend.isEmpty()) {
				ArrayUtil.concatTwoList(attend, diffUnionList);
			}
		}
		if(StringUtil.isNotNull(fdMeetingId)){
			KmImeetingMain kmImeetingMain = (KmImeetingMain) getServiceImp(request).findByPrimaryKey(fdMeetingId);
			List<SysOrgElement> tmpList = new ArrayList<SysOrgElement>();
			for (KmImeetingAgenda kmImeetingAgenda : kmImeetingMain.getKmImeetingAgendas()) {
				// 汇报人
				if (kmImeetingAgenda.getDocReporter() != null) {
					tmpList.add(kmImeetingAgenda.getDocReporter());
					ArrayUtil.concatTwoList(tmpList, diffUnionList);
				}
				// 建议列席单位的会议联络员
				if (kmImeetingAgenda.getFdAttendUnit() != null && !kmImeetingAgenda.getFdAttendUnit().isEmpty()) {
					List fdAttendUnit = kmImeetingAgenda.getFdAttendUnit();
					for (int i = 0; i < fdAttendUnit.size(); i++) {
						KmImissiveUnit kmImissiveUnit = (KmImissiveUnit) fdAttendUnit.get(i);
						if (kmImissiveUnit.getFdMeetingLiaison() != null) {
							ArrayUtil.concatTwoList(kmImissiveUnit.getFdMeetingLiaison(), diffUnionList);
						}
					}
				}
				// 建议旁听单位的会议联络员
				if (kmImeetingAgenda.getFdListenUnit() != null && !kmImeetingAgenda.getFdListenUnit().isEmpty()) {
					List fdListenUnit = kmImeetingAgenda.getFdListenUnit();
					for (int i = 0; i < fdListenUnit.size(); i++) {
						KmImissiveUnit kmImissiveUnit = (KmImissiveUnit) fdListenUnit.get(i);
						if (kmImissiveUnit.getFdMeetingLiaison() != null) {
							ArrayUtil.concatTwoList(kmImissiveUnit.getFdMeetingLiaison(), diffUnionList);
						}
					}
				}
			}
			if (!diffUnionList.isEmpty() && diffUnionList.size() > 0) {
				List attendIds = getSysOrgCoreService(request).expandToPersonIds(diffUnionList);
				if (attendIds != null && !attendIds.isEmpty()) {
					count += attendIds.size();
				} 
			 }
		} else {
			if (diffUnionList != null && !diffUnionList.isEmpty()) {
				count = diffUnionList.size();
			}
		}
		json.put("number", count);
		response.getWriter().write(json.toString());// 参与人数
		response.setCharacterEncoding("UTF-8");
		TimeCounter.logCurrentTime("Action-caculate", false, getClass());
		return null;
	}

	@Override
	public ActionForward view(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ISysAppConfigService sysAppConfigService = (ISysAppConfigService) SpringBeanUtil.getBean("sysAppConfigService");
		ActionForward actionForward = super.view(mapping, form, request,
				response);
		KmImeetingMainForm kmImeetingMainForm = (KmImeetingMainForm) form;

		// 通知单显示类型(type): admin、attend、assist、cc
		String type = request.getParameter("type");
		if (StringUtil.isNull(type)) {
			type = getRoleType(kmImeetingMainForm, request);
		}
		request.setAttribute("type", type);

		// 回执操作
		String optType = getKmImeetingMainFeedbackService(request)
				.getOptTypeByPerson(kmImeetingMainForm.getFdId(),
						UserUtil.getUser().getFdId());
		if (StringUtil.isNotNull(optType)) {
			request.setAttribute("optType", optType);
		}

		// 组织人标识
		String userId = UserUtil.getUser().getFdId();
		boolean emccFlag = false;
		if (userId.equals(kmImeetingMainForm.getFdEmceeId())) {
			emccFlag = true;
		}
		request.setAttribute("emccFlag", emccFlag);
		// 组织人是否承接工作标识
		String emccOpt = "";
		emccOpt = kmImeetingMainForm.getEmccType();
		if (StringUtil.isNull(emccOpt)) {
			emccOpt = "UnEmccDone";
		}
		request.setAttribute("emccOpt", emccOpt);

		// 是否上会材料负责人
		String docResponsIds = "";
		for (Object obj : kmImeetingMainForm.getKmImeetingAgendaForms()) {
			KmImeetingAgendaForm kmImeetingAgendaForm = (KmImeetingAgendaForm) obj;
			if (StringUtil.isNotNull(kmImeetingAgendaForm.getDocResponsId())) {
				docResponsIds += kmImeetingAgendaForm.getDocResponsId() + ";";
			}
		}
		boolean canUpload = (kmImeetingMainForm.getKmImeetingAgendaForms()
				.size() > 0)
				&& canUpload(kmImeetingMainForm, request)
				&& (docResponsIds.indexOf(UserUtil.getUser().getFdId()) > -1 || UserUtil
						.getKMSSUser().isAdmin());
		request.setAttribute("canUpload", canUpload);// 材料责任人 || 管理员
		request.setAttribute("isAdmin", UserUtil.getKMSSUser().isAdmin());

		if (KmImeetingConfigUtil.isBoenEnable()) {
			// 铂恩监控查看页签权限
			if (userId.equals(kmImeetingMainForm.getFdHostId()) || userId
					.equals(kmImeetingMainForm.getFdControlPersonId())) {
				request.setAttribute("canControl", true);
			}
			// 投票表决页签查看权限
			String fdBallotPersonIds = kmImeetingMainForm
					.getFdBallotPersonIds();
			if (StringUtil.isNotNull(fdBallotPersonIds)) {
				if (fdBallotPersonIds.contains(userId)) {
					request.setAttribute("canBallot", true);
				}
			}
			//开启会议
			if (userId.equals(kmImeetingMainForm.getFdControlPersonId())
					|| userId.equals(kmImeetingMainForm.getDocCreatorId())) {
				request.setAttribute("canBegin", true);
			}
		}
		//判断是否开启企业微信
		Map orgMap = sysAppConfigService.findByKey("com.landray.kmss.third.weixin.work.model.WeixinWorkConfig");
		if (orgMap != null) {
			//直播是否开启
			if(orgMap.containsKey("wxEnabled") && orgMap.containsKey("wxLivingEnabled")) {
				if ("true".equals(orgMap.get("wxEnabled")) && "true".equals(orgMap.get("wxLivingEnabled"))) {
                    request.setAttribute("isCanLiving", true);
                }
			}
		}
		boolean isTrue =false;
		//判断当前登陆人是否是创建人、主持人
		ArrayList<String> list = new ArrayList<>();
		list.add(kmImeetingMainForm.getDocCreatorId()); //创建人
		list.add(kmImeetingMainForm.getFdHostId());//主持人
		for (int i = 0; i < list.size(); i++) {
			String currentId = UserUtil.getUser().getFdId();
			if(currentId.equals(list.get(i))){
				isTrue = true;
				request.setAttribute("canCreatePerson", true);
				break;
			}
		}
		if(!isTrue) {
			//判断当前登陆人是否是参会人
			ArrayList<String> AttendList = new ArrayList<>();
			String fdAttendPersonIds = kmImeetingMainForm.getFdAttendPersonIds(); //参会人
			String[] attendIds = fdAttendPersonIds.split(";");
			AttendList.addAll(Arrays.asList(attendIds));
			for (int i = 0; i < AttendList.size(); i++) {
				String currentId = UserUtil.getUser().getFdId();
				if (currentId.equals(AttendList.get(i))) {
					request.setAttribute("isAttendPerson", true);
					break;
				}
			}
		}
		//检验当前会议是否存在直播
		IDynamicProxy staticProxy = ModuleCenter.getStaticProxy("com.landray.kmss.third.weixin.work.util.WxLivingUtils");
	   	if(staticProxy != null) {
			String result = (String) staticProxy.invoke("getLiving",kmImeetingMainForm.getFdId());
			if(StringUtil.isNotNull(result)){ //说明存在直播
				request.setAttribute("haveLiving", true);
				//企业微信直播ID
				request.setAttribute("wxLivingId",result);
			}
		}
	   	//由于js中使用el表达式获取的参数中一旦有换行符将会导致后续js解析失败，故将js中文本框字段中的换行符转为前端标签<br>
		request.setAttribute("kmImeetingMainFormFdNameFormatNewLine",formatNewLine(kmImeetingMainForm.getFdName()));
		request.setAttribute("kmImeetingMainFormfdRemarkFormatNewLine",formatNewLine(kmImeetingMainForm.getFdRemark()));
		return actionForward;
	}

	//将字符串中的换行符转为前端标签<br>
	public static String formatNewLine(String str) throws Exception {
		if(StringUtil.isNotNull(str)){
			return str.replaceAll("\r\n|\r|\n", "<br>");
		}
		return "";
	}

	// maxhub会议详情首页
	public ActionForward viewIndex(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		this.view(mapping, form, request, response);
		UserOperHelper.setEventType("maxhub会议详情首页");
		return mapping.findForward("viewIndex");
	}
	
	// maxhub会议详情签到
	public ActionForward viewSignIn(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		this.view(mapping, form, request, response);
		UserOperHelper.setEventType("maxhub会议详情签到");
		return mapping.findForward("viewSignIn");
	}

	// maxhub会议详情任务
	public ActionForward viewTask(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		this.view(mapping, form, request, response);
		UserOperHelper.setEventType("maxhub会议详情任务");
		return mapping.findForward("viewTask");
	}
	
	// mobile适配
	private void setPersonInfo4m(KmImeetingMainForm kmImeetingMainForm,
			HttpServletRequest request) throws Exception {
		List<Map<String, String>> fdAttendPerson4m = new ArrayList<Map<String, String>>();
		List<Map<String, String>> fdPaticipate4m = new ArrayList<Map<String, String>>();
		if (StringUtil.isNotNull(kmImeetingMainForm.getFdAttendPersonIds())) {
			List<String> ids = getSysOrgCoreService(request).expandToPersonIds(
					ArrayUtil.convertArrayToList(kmImeetingMainForm
							.getFdAttendPersonIds().split(";")));
			int size = ids.size();
			int max = size > 3 ? 3 : size;
			for (int i = 0; i < max; i++) {
				SysOrgElement person = sysOrgCoreService.findByPrimaryKey(ids
						.get(i));
				Map<String, String> data = new HashMap<String, String>();
				data.put("fdId", person.getFdId());
				data.put("fdName", person.getFdName());
				fdAttendPerson4m.add(data);
			}
			request.setAttribute("fdAttendMore4m", size - max);
		}
		if (StringUtil
				.isNotNull(kmImeetingMainForm.getFdParticipantPersonIds())) {
			List<String> ids = getSysOrgCoreService(request).expandToPersonIds(
					ArrayUtil.convertArrayToList(kmImeetingMainForm
							.getFdParticipantPersonIds().split(";")));
			int size = ids.size();
			int max = size > 3 ? 3 : size;
			for (int i = 0; i < max; i++) {
				SysOrgElement person = sysOrgCoreService.findByPrimaryKey(ids
						.get(i));
				Map<String, String> data = new HashMap<String, String>();
				data.put("fdId", person.getFdId());
				data.put("fdName", person.getFdName());
				fdPaticipate4m.add(data);
			}
			request.setAttribute("fdPaticipateMore4m", size - max);
		}
		request.setAttribute("fdAttendPerson4m", fdAttendPerson4m);
		request.setAttribute("fdPaticipate4m", fdPaticipate4m);
	}

	// mobile适配
	private void setFeedbackInfo4m(KmImeetingMainForm kmImeetingMainForm,
			HttpServletRequest request) throws Exception {
		String meetingId = kmImeetingMainForm.getFdId();
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo
				.setWhereBlock("kmImeetingMainFeedback.fdMeeting.fdId=:meetingId and kmImeetingMainFeedback.docCreator.fdId=:userId");
		hqlInfo.setParameter("meetingId", meetingId);
		hqlInfo.setParameter("userId", UserUtil.getUser().getFdId());
		List<KmImeetingMainFeedback> fs = getKmImeetingMainFeedbackService(
				request).findList(hqlInfo);
		if (!fs.isEmpty()) {
			KmImeetingMainFeedback kmImeetingMainFeedback = fs.get(0);
			request.setAttribute("kmImeetingMainFeedback",
					kmImeetingMainFeedback);
		}
	}

	/**
	 * 返回角色类型<br/>
	 * admin:管理员、组织人、创建人、流程审批人<br/>
	 * attend:主持人、会议参与人、会议列席人<br/>
	 * assist:会议协助人、会议室保管员<br/>
	 * cc:抄送人、可阅读者
	 */
	private String getRoleType(KmImeetingMainForm kmImeetingMainForm,
			HttpServletRequest request) throws Exception {
		String userId = UserUtil.getUser().getFdId();
		String fdIsHander = kmImeetingMainForm.getSysWfBusinessForm()
				.getFdIsHander();// 是否当前审批人

		// 是否是历史审批人
		InternalLbpmProcessForm processForm = (InternalLbpmProcessForm) kmImeetingMainForm.getSysWfBusinessForm()
				.getInternalForm();
		Boolean isHistoryHandler = false;
		if (processForm != null) {
			isHistoryHandler = processForm.getProcessInstanceInfo().isHistoryhandler();
		}

		if (UserUtil.getKMSSUser().isAdmin()
				|| UserUtil.checkRole("ROLE_KMIMEETING_READER")
				|| userId.equals(kmImeetingMainForm.getFdEmceeId())
				|| userId.equals(kmImeetingMainForm.getDocCreatorId())
				|| "true".equals(fdIsHander) || isHistoryHandler) {
			// admin:管理员、会议组织人、会议创建人、流程审批人
			return "admin";
		} else {
			List<String> attendIds = ArrayUtil
					.convertArrayToList(kmImeetingMainForm
							.getFdAttendPersonIds().split(";"));
			List<String> participantIds = ArrayUtil
					.convertArrayToList(kmImeetingMainForm
							.getFdParticipantPersonIds().split(";"));
			if (!participantIds.isEmpty()) {
				attendIds.addAll(participantIds);
			}
			if (StringUtil.isNotNull(kmImeetingMainForm.getFdHostId())) {
				attendIds.add(kmImeetingMainForm.getFdHostId());
			}
			if (StringUtil.isNotNull(kmImeetingMainForm
					.getFdSummaryInputPersonId())) {
				attendIds.add(kmImeetingMainForm.getFdSummaryInputPersonId());
			}
			// attend:主持人、会议参与人、会议列席人、纪要录入人
			if (!attendIds.isEmpty() && UserUtil.checkUserIds(attendIds)) {
				return "attend";
			} else {
				List<String> assistIds = ArrayUtil
						.convertArrayToList(kmImeetingMainForm
								.getFdAssistPersonIds().split(";"));
				if (StringUtil.isNotNull(kmImeetingMainForm.getFdPlaceId())) {
					KmImeetingRes kmImeetingRes = (KmImeetingRes) getKmImeetingResService(
							request).findByPrimaryKey(
							kmImeetingMainForm.getFdPlaceId());
					if (kmImeetingRes != null
							&& kmImeetingRes.getDocKeeper() != null) {
						assistIds.add(kmImeetingRes.getDocKeeper().getFdId());
					}
				}
				// assist:会议协助人、会议室保管员
				if (!assistIds.isEmpty() && UserUtil.checkUserIds(assistIds)) {
					return "assist";
				} else {
					List<String> ccIds = ArrayUtil
							.convertArrayToList(kmImeetingMainForm
									.getFdCopyToPersonIds().split(";"));
					List<String> readerIds = ArrayUtil
							.convertArrayToList(kmImeetingMainForm
									.getAuthReaderIds().split(";"));
					if (!readerIds.isEmpty()) {
						ccIds.addAll(readerIds);
					}
					// cc:抄送人、可阅读者
					if (!ccIds.isEmpty() && UserUtil.checkUserIds(ccIds)) {
						return "cc";
					}
				}
			}
		}
		return "admin";
	}

	// 手动发送会议通知
	public ActionForward sendNotify(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-sendNotify", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			String fdId = request.getParameter("fdId");
			if (StringUtil.isNotNull(fdId)) {
				KmImeetingMain kmImeetingMain = (KmImeetingMain) getServiceImp(
						request).findByPrimaryKey(fdId);
				// 发送会议通知
				((IKmImeetingMainService) getServiceImp(request))
						.saveSendMeetingNotify(kmImeetingMain);
				if (UserOperHelper.allowLogOper("sendNotify",
						getServiceImp(request).getModelName())) {
					UserOperHelper.setEventType("手动发送会议通知");
					UserOperContentHelper.putUpdate(kmImeetingMain).putSimple(
							"isNotify", kmImeetingMain.getIsNotify(), true);
				}
				// 修改是否已发送会议状态
				kmImeetingMain.setIsNotify(true);
				getServiceImp(request).update(kmImeetingMain);
			}
		} catch (Exception e) {
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("Action-sendNotify", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_RETURN).save(request);
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("success", mapping, form, request, response);
		}
	}

	// 会议变更
	public ActionForward changeMeeting(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-changeMeeting", true, getClass());
		KmssMessages messages = new KmssMessages();
		KmImeetingMainForm kmImeetingMainForm = (KmImeetingMainForm) form;
		kmImeetingMainForm.setMethod_GET("edit");
		String fdId = request.getParameter("fdId");
		String changeType = request.getParameter("changeType");
		if (StringUtil.isNotNull(fdId)) {
			try {
				KmImeetingMain kmImeetingMain = (KmImeetingMain) getServiceImp(request).findByPrimaryKey(fdId);
				if (kmImeetingMain.getFdTemplate() !=null) {
					// 流程重启
					getLbpmProcessRestartServiceImp().restart(fdId, null);
					//重启获取新的文档流程信息
					loadActionForm(mapping, kmImeetingMainForm, request, response);
					// #7602 流程重启后覆盖了原先的权限，重新赋值
					inheritAuth(kmImeetingMain, kmImeetingMainForm);
					// 更改提交人身份
					SysWfBusinessForm sysWfBusinessForm =kmImeetingMainForm.getSysWfBusinessForm();
					sysWfBusinessForm.setDraftIdentityID(UserUtil.getKMSSUser( request).getUserId());
					sysWfBusinessForm.setDraftIdentityName(UserUtil.getKMSSUser(request).getUserName());
					kmImeetingMainForm.setSysWfBusinessForm(sysWfBusinessForm);
				} else {
					loadActionForm(mapping, kmImeetingMainForm, request, response);
				}
				if (StringUtil.isNotNull(changeType)) {
					kmImeetingMainForm.setFdChangeType(changeType);
				}
				kmImeetingMainForm.setChangeMeetingReason("");
				// 会议变更标志
				kmImeetingMainForm.setFdChangeMeetingFlag(ImeetingConstant.IS_CHANGEMEETING_YES);
				kmImeetingMainForm.setDocStatus(SysDocConstant.DOC_STATUS_DRAFT);
				kmImeetingMainForm.setIsNotify(ImeetingConstant.IS_NOTIFY_NO);
				//kmImeetingMainForm.setSysWfBusinessForm(new SysWfBusinessForm());
				getServiceImp(request).updateMeetingFlag(kmImeetingMain);
				if (UserOperHelper.allowLogOper("changeMeeting",
						getServiceImp(request).getModelName()) && kmImeetingMain !=null) {
					UserOperContentHelper.putFind(kmImeetingMain);
				}
				// 保存变更前的一些信息（如时间、人员）
				JSONObject beforeChangeContent = genBeforeChangeContent(
						kmImeetingMainForm, request);
				kmImeetingMainForm.setBeforeChangeContent(beforeChangeContent
						.toString());
				setRepeat(kmImeetingMainForm, request);

			} catch (Exception e) {
				messages.addError(e);
			}
			TimeCounter.logCurrentTime("Action-changeMeeting", false,
					getClass());
			if (messages.hasError()) {
				KmssReturnPage.getInstance(request).addMessages(messages)
						.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
				return getActionForward("failure", mapping, form, request,
						response);
			}
		}
		return getActionForward("edit", mapping, form, request, response);
	}

	// 会议提前结束
	public ActionForward earlyEndMeeting(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-earlyEndMeeting", true, getClass());
		KmssMessages messages = new KmssMessages();
		String fdId = request.getParameter("fdId");
		if (StringUtil.isNotNull(fdId)) {
			try {
				String earlyEndTime = request.getParameter("earlyEndTime");
				Date fdEarlyFinishDate = DateUtil.convertStringToDate(
						earlyEndTime, DateUtil.PATTERN_DATETIME);
				KmImeetingMain kmImeetingMain = (KmImeetingMain) getServiceImp(
						request)
								.findByPrimaryKey(fdId);
				kmImeetingMain.setFdEarlyFinishDate(fdEarlyFinishDate);
				String fdHoldDuration = request.getParameter("fdHoldDuration");
				if (!"".equals(fdHoldDuration)) {
					kmImeetingMain
							.setFdHoldDuration(Double.valueOf(fdHoldDuration));
				} else {
					Long holdDuration = fdEarlyFinishDate.getTime()
							- kmImeetingMain.getFdHoldDate().getTime();
					kmImeetingMain
							.setFdHoldDuration(holdDuration.doubleValue());
				}
				IKmImeetingMainService kmImeetingMainServiceImp = (IKmImeetingMainService) getServiceImp(
						request);
				kmImeetingMainServiceImp
						.updateEarlyEndMeeting(kmImeetingMain);
			} catch (Exception e) {
				messages.addError(e);
			}
			if (messages.hasError()) {
				KmssReturnPage.getInstance(request).addMessages(messages)
						.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
				return getActionForward("failure", mapping, form, request,
						response);
			} else {
				return getActionForward("success", mapping, form, request,
						response);
			}
		}
		return getActionForward("failure", mapping, form, request, response);
	}

	/**
	 * 会议提前结束（移动端）
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward earlyEndMeetingMobile(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-earlyEndMeeting", true, getClass());
		String fdId = request.getParameter("fdId");
		JSONObject json = new JSONObject();
		if (StringUtil.isNotNull(fdId)) {
			try {
				String earlyEndTime = request.getParameter("earlyEndTime");
				Date fdEarlyFinishDate = DateUtil.convertStringToDate(earlyEndTime, DateUtil.PATTERN_DATETIME);
				KmImeetingMain kmImeetingMain = (KmImeetingMain) getServiceImp(request).findByPrimaryKey(fdId);
				// 判断提前结束时间是否合法
				if (fdEarlyFinishDate == null) {
					throw new RuntimeException(
							ResourceUtil.getString("kmImeeting.earlyEnd.errorMsg.one", "km-imeeting"));
				}
				if (fdEarlyFinishDate.before(kmImeetingMain.getFdHoldDate())) {
					throw new RuntimeException(
							ResourceUtil.getString("kmImeetingMain.fdEarlyFinishDate.tip2", "km-imeeting"));
				}
				if (fdEarlyFinishDate.after(kmImeetingMain.getFdFinishDate())) {
					throw new RuntimeException(
							ResourceUtil.getString("kmImeetingMain.fdEarlyFinishDate.tip", "km-imeeting"));
				}
				kmImeetingMain.setFdEarlyFinishDate(fdEarlyFinishDate);
				Long holdDuration = fdEarlyFinishDate.getTime() - kmImeetingMain.getFdHoldDate().getTime();
				kmImeetingMain.setFdHoldDuration(holdDuration.doubleValue());
				IKmImeetingMainService kmImeetingMainServiceImp = (IKmImeetingMainService) getServiceImp(request);
				kmImeetingMainServiceImp.updateEarlyEndMeeting(kmImeetingMain);
				json.put("success", true);
			} catch (Exception e) {
				json.put("success", false);
				json.put("msg", e.getMessage());
			}
		} else {
			json.put("success", false);
			json.put("msg", "会议ID不能为空！");
		}

		response.setContentType("application/json;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json.toString());
		TimeCounter.logCurrentTime("Action-earlyEndMeeting", false, getClass());
		return null;
	}

	// 权限赋值
	private void inheritAuth(KmImeetingMain model, KmImeetingMainForm form)
			throws Exception {
		List list = model.getAuthReaders();
		if (list != null && !list.isEmpty()) {
			String[] orgArray = ArrayUtil
					.joinProperty(list, "fdId:fdName", ";");
			form.setAuthReaderIds(orgArray[0]);
			form.setAuthReaderNames(orgArray[1]);
		}
		list = model.getAuthEditors();
		if (list != null && !list.isEmpty()) {
			String[] orgArray = ArrayUtil
					.joinProperty(list, "fdId:fdName", ";");
			form.setAuthEditorIds(orgArray[0]);
			form.setAuthEditorNames(orgArray[1]);
		}
		if (model.getAuthRBPFlag() != null) {
            form.setAuthRBPFlag(model.getAuthRBPFlag().toString());
        }
		form.setAuthChangeReaderFlag(model.getAuthChangeReaderFlag());
		form.setAuthChangeEditorFlag(model.getAuthChangeEditorFlag());
		list = model.getAuthAttCopys();
		if (list != null && !list.isEmpty()) {
			String[] orgArray = ArrayUtil
					.joinProperty(list, "fdId:fdName", ";");
			form.setAuthAttCopyIds(orgArray[0]);
			form.setAuthAttCopyNames(orgArray[1]);
		}
		list = model.getAuthAttDownloads();
		if (list != null && !list.isEmpty()) {
			String[] orgArray = ArrayUtil
					.joinProperty(list, "fdId:fdName", ";");
			form.setAuthAttDownloadIds(orgArray[0]);
			form.setAuthAttDownloadNames(orgArray[1]);
		}
		list = model.getAuthAttPrints();
		if (list != null && !list.isEmpty()) {
			String[] orgArray = ArrayUtil
					.joinProperty(list, "fdId:fdName", ";");
			form.setAuthAttPrintIds(orgArray[0]);
			form.setAuthAttPrintNames(orgArray[1]);
		}
		if (model.getAuthAttNocopy() != null) {
            form.setAuthAttNocopy(model.getAuthAttNocopy().toString());
        }
		if (model.getAuthAttNodownload() != null) {
            form.setAuthAttNodownload(model.getAuthAttNodownload().toString());
        }
		if (model.getAuthAttNoprint() != null) {
            form.setAuthAttNoprint(model.getAuthAttNoprint().toString());
        }
		form.setAuthChangeAtt(model.getAuthChangeAtt());
	}

	@Override
	protected String getParentProperty() {
		return "fdTemplate";
	}

	/**
	 * 会议变更前，生成内容JSON（变更前的内容）
	 */
	private JSONObject genBeforeChangeContent(
			KmImeetingMainForm kmImeetingMainForm, HttpServletRequest request)
			throws Exception {
		JSONObject json = new JSONObject();
		// 时间
		json.put("fdHoldDate", kmImeetingMainForm.getFdHoldDate());
		json.put("fdFinishDate", kmImeetingMainForm.getFdFinishDate());
		// 人员
		json.put("fdHostId", kmImeetingMainForm.getFdHostId());
		json.put("fdHostName", kmImeetingMainForm.getFdHostName());
		if (StringUtil.isNotNull(kmImeetingMainForm.getFdAttendPersonIds())) {
			String[] fdAttendPersonIds = kmImeetingMainForm.getFdAttendPersonIds().split(";");
			List<SysOrgElement> attendPerson = getSysOrgCoreService(request)
					.expandToPerson(getSysOrgCoreService(request).findByPrimaryKeys(fdAttendPersonIds));
			String[] attendPersonArr = ArrayUtil.joinProperty(attendPerson, "fdId:fdName", ";");
			json.put("fdAttendPersonIds", attendPersonArr[0]);
			json.put("fdAttendPersonNames", attendPersonArr[1]);
		} else {
			json.put("fdAttendPersonIds", "");
			json.put("fdAttendPersonNames", "");
		}
		if (StringUtil.isNotNull(kmImeetingMainForm.getFdParticipantPersonIds())) {
			String[] fdParticipantPersonIds = kmImeetingMainForm.getFdParticipantPersonIds().split(";");
			List<SysOrgElement> participantPerson = getSysOrgCoreService(request)
					.expandToPerson(getSysOrgCoreService(request).findByPrimaryKeys(fdParticipantPersonIds));
			String[] participantPersonArr = ArrayUtil.joinProperty(participantPerson, "fdId:fdName", ";");
			json.put("fdParticipantPersonIds", participantPersonArr[0]);
			json.put("fdParticipantPersonNames", participantPersonArr[1]);
		} else {
			json.put("fdParticipantPersonIds", "");
			json.put("fdParticipantPersonNames", "");
		}
		if (StringUtil.isNotNull(kmImeetingMainForm.getFdOtherPersonIds())) {
			String[] fdOtherPersonIds = kmImeetingMainForm.getFdOtherPersonIds().split(";");
			List<SysOrgElement> otherPerson = getSysOrgCoreService(request)
					.expandToPerson(getSysOrgCoreService(request).findByPrimaryKeys(fdOtherPersonIds));
			String[] otherPersonArr = ArrayUtil.joinProperty(otherPerson, "fdId:fdName", ";");
			// #104289 修复 议题相关人员回执时邀请其他人参加会议，被邀请人员不能看到对应议题
			String fdAttendAgendaInfo = "";
			for (int i = 0; i < fdOtherPersonIds.length; i++) {
				HQLInfo hqlInfo = new HQLInfo();
				hqlInfo.setSelectBlock("kmImeetingMainFeedback.fdAttendAgendaId");
				hqlInfo.setWhereBlock("kmImeetingMainFeedback.docCreator.fdId = :feedbackPersonId"
						+ " and (kmImeetingMainFeedback.fdType not like '%06%' or kmImeetingMainFeedback.fdType not like '%07%')"
						+ " and kmImeetingMainFeedback.fdMeeting.fdId = :fdMeetingId");
				hqlInfo.setParameter("feedbackPersonId", fdOtherPersonIds[i]);
				hqlInfo.setParameter("fdMeetingId", kmImeetingMainForm.getFdId());
				List<String> feedbackAgendaIds = getKmImeetingMainFeedbackService(request).findList(hqlInfo);
				fdAttendAgendaInfo += fdOtherPersonIds[i] + ":";
				if (feedbackAgendaIds != null && !feedbackAgendaIds.isEmpty()) {
					for (String agendaId : feedbackAgendaIds) {
						if (StringUtil.isNotNull(agendaId)) {
							fdAttendAgendaInfo += agendaId + ";";
						}
					}
				}
				fdAttendAgendaInfo += ",";
			}
			json.put("fdOtherPersonAgenda", fdAttendAgendaInfo);
			json.put("fdOtherPersonIds", otherPersonArr[0]);
			json.put("fdOtherPersonNames", otherPersonArr[1]);
		} else {
			json.put("fdOtherPersonAgenda", "");
			json.put("fdOtherPersonIds", "");
			json.put("fdOtherPersonNames", "");
		}
		/// 议题信息
		JSONArray jsonArr = new JSONArray();
		List fdAgendaForms = kmImeetingMainForm.getKmImeetingAgendaForms();
		for (int i = 0; i < fdAgendaForms.size(); i++) {
			KmImeetingAgendaForm KmImeetingAgendaForm = (KmImeetingAgendaForm) fdAgendaForms.get(i);
			JSONObject jsonA = new JSONObject();
			jsonA.put("agendaId", KmImeetingAgendaForm.getFdId());
			jsonA.put("agendaName", KmImeetingAgendaForm.getDocSubject());
			jsonA.put("reporterId", KmImeetingAgendaForm.getDocReporterId());
			jsonA.put("reporterName", KmImeetingAgendaForm.getDocReporterName());
			List unitIds = new ArrayList();
			if (StringUtil.isNotNull(KmImeetingAgendaForm.getFdAttendUnitIds())) {
				ArrayUtil.concatTwoList(Arrays.asList(KmImeetingAgendaForm.getFdAttendUnitIds().split(";")), unitIds);
			}
			if (StringUtil.isNotNull(KmImeetingAgendaForm.getFdListenUnitIds())) {
				ArrayUtil.concatTwoList(Arrays.asList(KmImeetingAgendaForm.getFdListenUnitIds().split(";")), unitIds);
			}
			if(unitIds.size()>0&&!unitIds.isEmpty()){
				IKmImissiveUnitService kmImissiveUnitService = (IKmImissiveUnitService)SpringBeanUtil.getBean("kmImissiveUnitService");
				List unitList = kmImissiveUnitService.findByPrimaryKeys(ArrayUtil.toStringArray(unitIds.toArray()));
				List<SysOrgElement> targets = new ArrayList<SysOrgElement>();
				for (int j = 0; j < unitList.size(); j++) {
					KmImissiveUnit kmImissiveUnit = (KmImissiveUnit) unitList.get(j);
					if (kmImissiveUnit.getFdMeetingLiaison() != null) {
						ArrayUtil.concatTwoList(kmImissiveUnit.getFdMeetingLiaison(), targets);
					}
				}
				List feedBackList = getServiceImp(request).findFeedBackByAgenda(kmImeetingMainForm.getFdId(),
						KmImeetingAgendaForm.getFdId(), false);
				if (feedBackList != null && feedBackList.size() > 0) {
					for (int k = 0; k < feedBackList.size(); k++) {
						KmImeetingMainFeedback kf = (KmImeetingMainFeedback) feedBackList.get(k);
						targets.add(kf.getDocAttend());
					}
				}
				List<SysOrgElement> liaisonPerson = getSysOrgCoreService(request).expandToPerson(targets);
				if (liaisonPerson.size() > 0) {
					String[] liaisonPersonArr = ArrayUtil.joinProperty(liaisonPerson, "fdId:fdName", ";");
					jsonA.put("relatedPersonIds", liaisonPersonArr[0]);
					jsonA.put("relatedPersonNames", liaisonPersonArr[1]);
				} else {
					jsonA.put("relatedPersonIds", "");
					jsonA.put("relatedPersonNames", "");
				}
			}else{
				jsonA.put("relatedPersonIds", "");
				jsonA.put("relatedPersonNames", "");
			}
			jsonArr.add(jsonA);
		}
		json.put("agenda", jsonArr);
		// 地点
		json.put("fdPlaceId", kmImeetingMainForm.getFdPlaceId());
		json.put("fdPlaceName", kmImeetingMainForm.getFdPlaceName());
		json.put("fdOtherPlace", kmImeetingMainForm.getFdOtherPlace()); 
		return json;
	}

	public ActionForward publishDraft(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmImeetingMainForm mainForm = (KmImeetingMainForm) form;
		mainForm.setDocStatus(SysDocConstant.DOC_STATUS_EXAMINE);
		return super.update(mapping, form, request, response);
	}

	// ************** 上会材料相关(开始) ******************************//
	// 上会材料查看页面
	public ActionForward viewUpdateAtt(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-viewUpdateAtt", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			loadActionForm(mapping, form, request, response);
			request.setAttribute("currentUser", UserUtil.getUser());// 记录当前用户
			request.setAttribute("isAdmin", UserUtil.getKMSSUser().isAdmin());// 是否管理员
			// 会议召开后不可以提交上会材料
			KmImeetingMainForm kmImeetingMainForm = (KmImeetingMainForm) form;
			request.setAttribute("canUpload",
					canUpload(kmImeetingMainForm, request));

		} catch (Exception e) {
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("Action-viewUpdateAtt", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("viewUpdateAtt", mapping, form, request,
					response);
		}
	}

	// 上会材料编辑页面
	public ActionForward editUpdateAtt(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-editUpdateAtt", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			loadActionForm(mapping, form, request, response);
			KmImeetingMainForm kmImeetingMainForm = (KmImeetingMainForm) form;
			request.setAttribute("canUpload",
					canUpload(kmImeetingMainForm, request));// 是否可上传
			request.setAttribute("currentUser", UserUtil.getUser());// 记录当前用户
			request.setAttribute("isAdmin", UserUtil.getKMSSUser().isAdmin());// 是否管理员
		} catch (Exception e) {
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("Action-editUpdateAtt", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("editUpdateAtt", mapping, form, request,
					response);
		}
	}

	// 会议组织人回复
	public ActionForward editEmcee(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-editEmcee", true, getClass());
		KmssMessages messages = new KmssMessages();
		// loadActionForm(mapping, form, request, response);
		try {
			ActionForm emccForm = null;
			String fdId = request.getParameter("fdId");
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setWhereBlock("kmImeetingMain.fdId=:fdId");
			hqlInfo.setParameter("fdId", fdId);
			KmImeetingMain kmImeetingMain = (KmImeetingMain) getServiceImp(request).findList(hqlInfo).get(0);
			emccForm = (ActionForm) getServiceImp(request).convertModelToForm((IExtendForm) form,
					(IBaseModel) kmImeetingMain, new RequestContext(request));
			loadActionForm(mapping, emccForm, request, response);
			// KmImeetingMainForm kmImeetingMainForm = (KmImeetingMainForm)
			// 通知单显示类型(type): admin、attend、assist、cc
			String type = request.getParameter("type");
			KmImeetingMainForm kmImeetingMainForm = (KmImeetingMainForm) emccForm;
			if (StringUtil.isNull(type)) {
				type = getRoleType(kmImeetingMainForm, request);
			}
			request.setAttribute("type", type);
			request.setAttribute("currentUser", UserUtil.getUser());// 记录当前用户
			request.setAttribute("isAdmin", UserUtil.getKMSSUser().isAdmin());// 是否管理员
			// 组织人标识
			String userId = UserUtil.getUser().getFdId();
			boolean emccFlag = false;
			if (userId.equals(kmImeetingMainForm.getFdEmceeId())) {
				emccFlag = true;
			}
			request.setAttribute("emccFlag", emccFlag);
			// 组织人是否承接工作标识
			String emccOpt = "";
			emccOpt = kmImeetingMainForm.getEmccType();
			if (StringUtil.isNull(emccOpt)) {
				emccOpt = "UnEmccDone";
			}
			request.setAttribute("emccOpt", emccOpt);
		} catch (Exception e) {
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("Action-editEmcee", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("editEmcee", mapping, form, request, response);
		}
	}

	public ActionForward updateEmcc(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-update", true, getClass());
		KmssMessages messages = new KmssMessages();
		KmImeetingMainForm kmImeetingMainForm = (KmImeetingMainForm) form;
		try {
			if (!"POST".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }

			((IKmImeetingMainService) getServiceImp(request)).updateEmcc(kmImeetingMainForm,
					new RequestContext(request));

			// getKmImeetingAgendaService(request).updateEmcc(kmImeetingMainForm);
		} catch (Exception e) {
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("Action-update", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).save(request);
			return getActionForward("editEmcee", mapping, form, request, response);
		} else {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			return getActionForward("success", mapping, form, request, response);
		}
	}

	private Boolean canUpload(KmImeetingMainForm kmImeetingMainForm,
			HttpServletRequest request) {
		Boolean canUpload = true;
		Date fdHoldDate = DateUtil.convertStringToDate(
				kmImeetingMainForm.getFdHoldDate(), DateUtil.TYPE_DATETIME,
				request.getLocale());
		if (!"30".equals(kmImeetingMainForm.getDocStatus())) {
			canUpload = false;
		} else if (fdHoldDate.getTime() < new Date().getTime()) {
			canUpload = false;
		}
		return canUpload;

	}

	// 保存上会材料
	public ActionForward saveUpdateAtt(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-save", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			if (!"POST".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			// 保存上会材料
			KmImeetingMainForm kmImeetingMainForm = (KmImeetingMainForm) form;
			getKmImeetingAgendaService(request).saveUploadAtt(
					kmImeetingMainForm);
			// 记录操作日志
			if (UserOperHelper.allowLogOper("saveUpdateAtt",
					getServiceImp(request).getModelName())) {
				UserOperHelper.setEventType("更新上会材料");
			}
		} catch (Exception e) {
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("Action-save", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).save(
					request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("success", mapping, form, request, response);
		}
	}

	// ************** 上会材料相关(结束) ******************************//

	/**
	 * 校验权限（AJAX）
	 */
	public ActionForward checkViewAuth(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-checkViewAuth", true, getClass());
		KmssMessages messages = new KmssMessages();
		JSONObject auth = new JSONObject();
		try {
			String fdId = request.getParameter("fdId");
			Boolean canView = false;
			// 校验是否有编辑权限
			if (UserUtil.checkAuthentication(
					"/km/imeeting/km_imeeting_main/kmImeetingMain.do?method=view&fdId="
							+ fdId, "get")) {
				canView = true;
			}
			auth.put("canView", canView);
		} catch (Exception e) {
			messages.addError(e);
		}
		response.getWriter().write(auth.toString());// 结果
		response.setCharacterEncoding("UTF-8");
		TimeCounter.logCurrentTime("Action-checkViewAuth", false, getClass());
		return null;
	}

	/**
	 * 分类转移
	 */
	public ActionForward changeTemplate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		String templateId = request.getParameter("templateId");
		String ids = ArrayUtil.concat(request
				.getParameterValues("List_Selected"), ';');
		try {
			((IKmImeetingMainService) getServiceImp(request))
					.updateDucmentTemplate(ids, templateId);
		} catch (Exception e) {
			messages.addError(e);
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			getActionForward("failure", mapping, form, request, response);
		}
		KmssReturnPage.getInstance(request).addMessages(messages).addButton(
				KmssReturnPage.BUTTON_CLOSE).save(request);
		return getActionForward("success", mapping, form, request, response);
	}

	/**
	 * 检查潜在与会者的忙闲状态
	 */
	public ActionForward checkFree(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			List<Map<String, Object>> result = ((IKmImeetingMainService) getServiceImp(
					request))
					.checkFree(new RequestContext(request));
			request.setAttribute("lui-source", JSONArray.fromObject(result));
		} catch (Exception e) {
			e.printStackTrace();
			messages.addError(e);
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			getActionForward("failure", mapping, form, request, response);
		}
		KmssReturnPage.getInstance(request).addMessages(messages).addButton(
				KmssReturnPage.BUTTON_CLOSE).save(request);
		return getActionForward("lui-source", mapping, form, request, response);
	}

	public ActionForward approveDraft(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		KmImeetingMainForm mainForm = (KmImeetingMainForm) form;
		mainForm.setDocStatus(SysDocConstant.DOC_STATUS_EXAMINE);
		return super.update(mapping, form, request, response);
	}
	
	public ActionForward mhuAdd(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		String  echo = "";
		try {
			 	echo = ((IKmImeetingMainService) getServiceImp(request)).putToKmImeetingList(request);
		} catch (Exception e) {
			messages.addError(e);
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			getActionForward("failure", mapping, form, request, response);
		}
		KmssReturnPage.getInstance(request).addMessages(messages).addButton(
				KmssReturnPage.BUTTON_CLOSE).save(request);
		if("success".equals(echo)){
			request.setAttribute("tip", "success");
			return getActionForward("mhusuccess", mapping, form, request, response);
		} else if("exist".equals(echo)) {
			request.setAttribute("tip", "exist");
			return getActionForward("mhusuccess", mapping, form, request, response);
		}
		
		return getActionForward("failure", mapping, form, request, response);
		
	}
	
	public ActionForward mhuGetAttendPersons(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
			 JSONArray jsonArr = new JSONArray();
			 List list = ((IKmImeetingMainService) getServiceImp(request)).getKmImeetingList(request);
			 if(list.size()>0){
				for (int i = 0; i < list.size(); i++) {
					JSONObject obj = new JSONObject();
					String userid = (String) list.get(i);
					SysOrgElement attendPerson = getSysOrgCoreService(request).findByPrimaryKey(userid);
					String attendName = attendPerson.getFdName();
					String imgUrl=request.getContextPath()
							+ "/sys/organization/image.jsp?orgId=" + userid
							+ "&size=m&s_time=" + System.currentTimeMillis();
					obj.accumulate("fdId", userid);
					obj.accumulate("name", attendName);
					obj.accumulate("avatar", imgUrl);
					jsonArr.add(obj);
				}
			 }
			 	response.setCharacterEncoding("UTF-8");
				response.getWriter().write(jsonArr.toString());// 结果
				return null;
		
	}
	
	public ActionForward mhuSave(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			String fdId=request.getParameter("fdId");
			//String isFace=request.getParameter("isFace");
			String fdName = request.getParameter("title");
			String fdHoldDurationHour = request.getParameter("duration");
			SysOrgPerson user = UserUtil.getUser();
			String fdAttendPersonIds = request.getParameter("attendIds");
			KmImeetingMainForm mainForm = new KmImeetingMainForm();
			mainForm.setFdId(fdId);
			mainForm.setIsFace("true");
			mainForm.setFdName(fdName);
			mainForm.setIsCloud("true");
			String fdHoldDate = request.getParameter("fdHoldDate");
			if(StringUtil.isNull(fdHoldDate)){
			Date currentTime = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			String dateString = formatter.format(currentTime);
			mainForm.setFdHoldDate(dateString);
			}else{
			mainForm.setFdHoldDate(fdHoldDate);	
			}
			mainForm.setFdHoldDurationHour(fdHoldDurationHour);
			mainForm.setDocCreatorId(user.getFdId());
			mainForm.setDocCreateTime(DateUtil.convertDateToString(
					new Date(), DateUtil.TYPE_DATETIME, request.getLocale()));
			mainForm.setFdHostId(user.getFdId());
			mainForm.setFdHostName(user.getFdName());
			String placeId = request.getParameter("placeId");
			KmImeetingRes kmImeetingRes = (KmImeetingRes) getKmImeetingResService(
					request).findByPrimaryKey(placeId);
			mainForm.setFdPlaceId(placeId);
			mainForm.setFdPlaceName(kmImeetingRes.getFdName());
			if (StringUtil.isNotNull(fdAttendPersonIds)) {
				mainForm.setFdAttendPersonIds(fdAttendPersonIds);
				String[] fdIds = fdAttendPersonIds.split(";");
				String fdAttendPersonNames = "";
				for (int i = 0; i < fdIds.length; i++) {
					SysOrgElement attendPerson = getSysOrgCoreService(request).findByPrimaryKey(fdIds[i]);
					if (StringUtil.isNull(fdAttendPersonNames)) {
						fdAttendPersonNames = attendPerson.getFdName();
					} else {
						fdAttendPersonNames += ";" + attendPerson.getFdName();
					}
				}
				if (StringUtil.isNotNull(fdAttendPersonNames)) {
					mainForm.setFdAttendPersonNames(fdAttendPersonNames);
				}
				
				String fdFinishDate = request.getParameter("endDate");
				if (StringUtil.isNull(fdFinishDate)) {
					fdFinishDate = getTimeByHour(Double.parseDouble(fdHoldDurationHour));
				}
				mainForm.setDocStatus("30");
				mainForm.setFdNotifyType("1");
				mainForm.setFdNotifyWay("todo");
				mainForm.setSyncDataToCalendarTime("sendNotify");
				mainForm.setFdSummaryFlag("0");
				mainForm.setFdChangeMeetingFlag("0");
				mainForm.setAuthAttNocopy("0");
				mainForm.setAuthAttNodownload("0");
				mainForm.setAuthAttNoprint("0");
				getServiceImp(request).add(mainForm, new RequestContext(request));
				
				String bookId = request.getParameter("bookId");
				// 使用会议预约转面对面会议后删除对应的会议预约
				if (StringUtil.isNotNull(bookId)) {
					getKmImeetingBookService(request).delete(bookId);
				}
			} 
		} catch (Exception e) {
			messages.addError(e);
		}
		response.setCharacterEncoding("UTF-8");
		if (messages.hasError()) {
			response.getWriter().write("failure");// 结果
		} else {
			response.getWriter().write("success");// 结果
		}
		return null;
	}
    public static String getTimeByHour(double hour) {

       /* Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) + hour);

        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime());*/
    	
        long curren = System.currentTimeMillis();
        long currenLast = (long) (curren+hour * 60 * 1000);
        Date da = new Date(currenLast);
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(da);

    }
    
	public ActionForward mhuGetKmImeeting(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
			 JSONArray jsonArrLast = new JSONArray();
			 String fdId = request.getParameter("fdId");
			 KmImeetingMain kmImeeting = (KmImeetingMain) getServiceImp(request).findByPrimaryKey(fdId);
			 JSONObject obj = new JSONObject();
			 obj.accumulate("fdId", kmImeeting.getFdId());
			 obj.accumulate("docSubject", kmImeeting.getDocSubject());
			 obj.accumulate("fdName", kmImeeting.getFdName());
			 obj.accumulate("isCloud", kmImeeting.getIsCloud());
			 obj.accumulate("fdHoldDate", kmImeeting.getFdHoldDate());
			 obj.accumulate("fdEarlyFinishDate", kmImeeting.getFdEarlyFinishDate());
			 obj.accumulate("fdFinishDate", kmImeeting.getFdFinishDate());
			 obj.accumulate("fdHoldDuration", kmImeeting.getFdHoldDuration());
			 obj.accumulate("fdMeetingAim", kmImeeting.getFdMeetingAim());
			 obj.accumulate("fdMeetingNum", kmImeeting.getFdMeetingNum());
			 List list = kmImeeting.getKmImeetingAgendas();
			 if(list.size()>0){
				 JSONArray jsonArr = new JSONArray();
				for (int i = 0; i < list.size(); i++) {
					JSONObject agenda = new JSONObject();
					KmImeetingAgenda kmImeetingAgenda = (KmImeetingAgenda) list.get(i);
					agenda.accumulate("fdId", kmImeetingAgenda.getFdId());
					agenda.accumulate("docSubject", kmImeetingAgenda.getDocSubject());
					agenda.accumulate("docReporterTime", kmImeetingAgenda.getDocReporterTime());
					agenda.accumulate("attachmentName", kmImeetingAgenda.getAttachmentName());
					agenda.accumulate("attachmentId", kmImeetingAgenda.getAttachmentId());
					agenda.accumulate("attachmentSubmitTime", kmImeetingAgenda.getAttachmentSubmitTime());
					agenda.accumulate("fdIsPublic", kmImeetingAgenda.getFdIsPublic());
					agenda.accumulate("docReporterId", kmImeetingAgenda.getDocReporter().getFdId());
					agenda.accumulate("docReporterName", kmImeetingAgenda.getDocReporter().getFdName());
					agenda.accumulate("docResponsId", kmImeetingAgenda.getDocRespons().getFdId());
					agenda.accumulate("docResponsName", kmImeetingAgenda.getDocRespons().getFdName());
					jsonArr.add(agenda);
				}
				obj.accumulate("kmImeetingAgendas", jsonArr);
				jsonArrLast.add(obj);
			 }
			 	response.setCharacterEncoding("UTF-8");
				response.getWriter().write(jsonArrLast.toString());// 结果
				return null;
	}
	public ActionForward mhuInvite(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			String fdId=request.getParameter("fdId");
			String key = request.getParameter("key");
			KmImeetingMain main = (KmImeetingMain)getServiceImp(request).findByPrimaryKey(fdId);
			String fdAttendPersonIds=request.getParameter("attendIds");
			int num=0;
			if (StringUtil.isNotNull(fdAttendPersonIds)&&main!=null) {
				String[] fdIds = fdAttendPersonIds.split(";");
				List sysOrgPersons=main.getFdAttendPersons();
				for (int i = 0; i < fdIds.length; i++) {
					boolean flag = true;
					SysOrgElement attendPerson = getSysOrgCoreService(request).findByPrimaryKey(fdIds[i]);
					for (int j = 0; j < sysOrgPersons.size(); j++) {
						if(attendPerson.getFdId().equals(((SysOrgElement)sysOrgPersons.get(j)).getFdId())){
							flag=false;
							break;
						}
					}
					if(flag){
						num=num+1;
						sysOrgPersons.add(attendPerson);
					}
				}
				main.setFdAttendPersons(sysOrgPersons);
				if(main.getFdAttendNum()!=null){
					main.setFdAttendNum(main.getFdAttendNum()+num);
				}
				getServiceImp(request).update(main);
				((IKmImeetingMainService) getServiceImp(request)).cleanCacheByKey(key+fdId);
			} 
		} catch (Exception e) {
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("Action-save", false, getClass());
		if (messages.hasError()) {
			response.getWriter().write("failure");// 结果
		} else {
			response.getWriter().write("success");// 结果
		}
		return null;
	}
	
	public ActionForward mhuCleanAttendCache(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
			 KmssMessages messages = new KmssMessages();
			 String fdId = request.getParameter("fdId");
			 String key = request.getParameter("key");
			 try {
				((IKmImeetingMainService) getServiceImp(request)).cleanCacheByKey(key+fdId);
			} catch (Exception e) {
				messages.addError(e);
			}
			 response.setCharacterEncoding("UTF-8");
			 if (messages.hasError()) {
				 response.getWriter().write("false");
				} else {
				 response.getWriter().write("true");
				}
				return null;
	}
	
	public ActionForward mhuShowAttendPersons(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
			String fdId = request.getParameter("fdId");
			
			JSONArray jsonArr = new JSONArray();
		
			KmImeetingMain kmImeetingMain=(KmImeetingMain) getServiceImp(request).findByPrimaryKey(fdId);
			List<SysOrgElement> list = kmImeetingMain.getFdAttendPersons();
			 
			 for (SysOrgElement sysOrgElement : list) {
				 JSONObject obj = new JSONObject();
				 String attendId = sysOrgElement.getFdId();
				 String attendName = sysOrgElement.getFdName();
				 String imgUrl=request.getContextPath()
							+ "/sys/organization/image.jsp?orgId=" + attendId
							+ "&size=m&s_time=" + System.currentTimeMillis();
				 	obj.accumulate("attendId", attendId);
					obj.accumulate("attendName", attendName);
					obj.accumulate("imgUrl", imgUrl);
					jsonArr.add(obj);
			}
		
		 	response.setCharacterEncoding("UTF-8");
			response.getWriter().write(jsonArr.toString());// 结果
			return null;
	}
	
	public ActionForward mhuGetCurrentTime(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
			String placeId = request.getParameter("placeId");
			String currentTime = request.getParameter("currentTime");
			
			Date dateTime = DateUtil.convertStringToDate(currentTime, ResourceUtil
					.getString("date.format.date"));
		
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setOrderBy(" kmImeetingMain.fdHoldDate asc");
			hqlInfo.setWhereBlock(" kmImeetingMain.fdHoldDate > :currentTime and kmImeetingMain.fdHoldDate < :date and fdPlace.fdId = :placeId");
			hqlInfo.setParameter("currentTime", dateTime);
			hqlInfo.setParameter("date", getTimesnight(dateTime));
			hqlInfo.setParameter("placeId", placeId);
			
			List<KmImeetingMain> result = getServiceImp(request).findList(hqlInfo);
			response.setCharacterEncoding("UTF-8");
			if(result.size()>0){
				KmImeetingMain kmImeetingMain = result.get(0);
				long interval = kmImeetingMain.getFdHoldDate().getTime() - dateTime.getTime();
				response.getWriter().write(Long.toString(interval));
			}else{
				response.getWriter().write("false");
			}
			
			return null;
	}
	
	public static Date getTimesnight(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MINUTE, 59);
		return  cal.getTime();
		}

	public ActionForward getPersonIds(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String attendIds = request.getParameter("attendIds");
		String participantIds = request.getParameter("participantIds");
		JSONObject json = new JSONObject();
		if (StringUtil.isNotNull(attendIds)) {
			String[] ids = attendIds.split(";");
			List<SysOrgElement> attendPerson = getSysOrgCoreService(request)
					.expandToPerson(getSysOrgCoreService(request)
							.findByPrimaryKeys(ids));
			String[] attendPersonArr = ArrayUtil.joinProperty(attendPerson,
					"fdId:fdName", ";");
			JSONObject obj = new JSONObject();
			obj.put("ids", attendPersonArr[0]);
			obj.put("names", attendPersonArr[1]);
			json.put("attend", obj);
		}
		if (StringUtil.isNotNull(participantIds)) {
			String[] ids = participantIds.split(";");
			List<SysOrgElement> participantPerson = getSysOrgCoreService(
					request).expandToPerson(
							getSysOrgCoreService(request)
									.findByPrimaryKeys(ids));
			String[] attendPersonArr = ArrayUtil.joinProperty(participantPerson,
					"fdId:fdName", ";");
			JSONObject obj = new JSONObject();
			obj.put("ids", attendPersonArr[0]);
			obj.put("names", attendPersonArr[1]);
			json.put("participant", obj);
		}
		String arr = json.toString();
		response.setCharacterEncoding("UTF-8");
		response.getWriter().println(arr);
		return null;
	}

	public ActionForward expand2PersonIds(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String attendIds = request.getParameter("orgIds");
		JSONObject json = new JSONObject();
		if (StringUtil.isNotNull(attendIds)) {
			String[] ids = attendIds.split(";");
			List<SysOrgElement> attendPerson = getSysOrgCoreService(request)
					.expandToPerson(getSysOrgCoreService(request).findByPrimaryKeys(ids));
			String[] attendPersonArr = ArrayUtil.joinProperty(attendPerson, "fdId:fdName", ";");
			json.put("personIds", attendPersonArr[0]);
		}
		String arr = json.toString();
		response.setCharacterEncoding("UTF-8");
		response.getWriter().println(arr);
		return null;
	}

	public ActionForward addSyncToBoen(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		String fdMeetingId = request.getParameter("fdMeetingId");
		try {
			if (StringUtil.isNotNull(fdMeetingId)) {
				KmImeetingMain kmImeetingMain = (KmImeetingMain) getServiceImp(request).findByPrimaryKey(fdMeetingId);
				((IKmImeetingMainService) getServiceImp(request)).addSyncToBoen(kmImeetingMain);
			}

		} catch (Exception e) {
			messages.addError(e);
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			getActionForward("failure", mapping, form, request, response);
		}
		KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
		return getActionForward("success", mapping, form, request, response);
	}

	public ActionForward addAttFromBoen(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		String fdMeetingId = request.getParameter("fdMeetingId");
		try {
			if (StringUtil.isNotNull(fdMeetingId)) {
				KmImeetingMain kmImeetingMain = (KmImeetingMain) getServiceImp(request).findByPrimaryKey(fdMeetingId);
				((IKmImeetingMainService) getServiceImp(request)).addAttFromBoen(kmImeetingMain);
			}

		} catch (Exception e) {
			messages.addError(e);
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			getActionForward("failure", mapping, form, request, response);
		}
		KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
		return getActionForward("success", mapping, form, request, response);
	}

	// public ActionForward addAttFromBoenTest(ActionMapping mapping, ActionForm
	// form, HttpServletRequest request,
	// HttpServletResponse response) throws Exception {
	// TimeCounter.logCurrentTime("Action-addAttFromBoenTest", true,
	// getClass());
	// KmssMessages messages = new KmssMessages();
	// String flag = "success";
	// try {
	// flag = ((IKmImeetingMainService)
	// getServiceImp(request)).addAttFromBoenTest(request);
	// UserOperHelper.setOperSuccess(true);
	// } catch (Exception e) {
	// e.printStackTrace();
	// messages.addError(e);
	// }
	// JSONObject json = new JSONObject();
	// json.put("flag", flag);
	// response.setCharacterEncoding("UTF-8");
	// response.getWriter().append(json.toString());
	// response.getWriter().flush();
	// response.getWriter().close();
	// return null;
	// }

	public ActionForward updateBeginMeeting(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-save", true, getClass());
		KmssMessages messages = new KmssMessages();
		String fdMeetingId = request.getParameter("fdMeetingId");
		String flag = "success";
		try {
			if (StringUtil.isNotNull(fdMeetingId)) {
				KmImeetingMain kmImeetingMain = (KmImeetingMain) getServiceImp(request).findByPrimaryKey(fdMeetingId);
				flag = ((IKmImeetingMainService) getServiceImp(request)).updateBeginMeeting(kmImeetingMain);
			}
			UserOperHelper.setOperSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			messages.addError(e);
		}
		JSONObject json = new JSONObject();
		json.put("flag", flag);
		response.setCharacterEncoding("UTF-8");
		response.getWriter().append(json.toString());
		response.getWriter().flush();
		response.getWriter().close();
		return null;
	}

	public ActionForward updateMeetingAtt(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-save", true, getClass());
		KmssMessages messages = new KmssMessages();
		String fdMeetingId = request.getParameter("fdMeetingId");
		String flag = "success";
		try {
			if (StringUtil.isNotNull(fdMeetingId)) {
				KmImeetingMain kmImeetingMain = (KmImeetingMain) getServiceImp(request).findByPrimaryKey(fdMeetingId);
				flag = ((IKmImeetingMainService) getServiceImp(request)).addAttFromBoen(kmImeetingMain);
			}
			UserOperHelper.setOperSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			messages.addError(e);
		}
		JSONObject json = new JSONObject();
		json.put("flag", flag);
		response.setCharacterEncoding("UTF-8");
		response.getWriter().append(json.toString());
		response.getWriter().flush();
		response.getWriter().close();
		return null;
	}

	public ActionForward getDept(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-getDept", true, getClass());
		KmssMessages messages = new KmssMessages();
		String fdOrgId = request.getParameter("fdOrgId");
		String deptId = "";
		String deptName = "";
		try {
			if (StringUtil.isNotNull(fdOrgId)) {
				SysOrgElement org = getSysOrgCoreService(request).findByPrimaryKey(fdOrgId);
				if (org != null && org.getFdParent() != null) {
					deptId = org.getFdParent().getFdId();
					deptName = org.getFdParent().getFdName();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			messages.addError(e);
		}
		KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_RETURN).save(request);
		TimeCounter.logCurrentTime("Action-getDept", false, getClass());
		JSONObject json = new JSONObject();
		json.put("deptId", deptId);
		json.put("deptName", deptName);
		response.setCharacterEncoding("UTF-8");
		response.getWriter().append(json.toString());
		response.getWriter().flush();
		response.getWriter().close();
		return null;
	}

	public ActionForward updateEnterKkMeeting(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String meetingId = request.getParameter("meetingid");
		Cookie token = null;
		Cookie[] cookies = request.getCookies();
		for (Cookie ck : cookies) {
			if ("LtpaToken".equals(ck.getName())) {
				token = ck;
				break;
			}
		}
		String roomId = "";
		KmImeetingMapping kmImeetingMapping = getKmImeetingMappingService().findByModelId(meetingId,
				KmImeetingMain.class.getName(), "kk");
		if (kmImeetingMapping != null) {
			roomId = kmImeetingMapping.getFdThirdSysId();
		}
		if (StringUtil.isNotNull(roomId) && token != null) {
			response.addCookie(token);
			response.sendRedirect(KKUtil.getKKUrlHttps() + "/serverj/alimeeting/enter.ajax?roomId=" + roomId);
		}else{
			return getActionForward("e404", mapping, form, request, response);
		}
		return null;
	}


	public ActionForward addSyncToKk(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		String fdMeetingId = request.getParameter("fdId");
		String success = "false";
		try {
			if (StringUtil.isNotNull(fdMeetingId)) {
				KmImeetingMain kmImeetingMain = (KmImeetingMain) getServiceImp(request).findByPrimaryKey(fdMeetingId);
				success = ((IKmImeetingMainService) getServiceImp(request)).addSyncToKk(kmImeetingMain);
			}
		} catch (Exception e) {
			e.printStackTrace();
			messages.addError(e);
		}
		KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_RETURN).save(request);
		TimeCounter.logCurrentTime("Action-checkUniqueNum", false, getClass());
		JSONObject json = new JSONObject();
		json.put("success", success);
		response.setHeader("content-type", "application/json;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().append(json.toString());
		response.getWriter().flush();
		response.getWriter().close();
		return null;
	}

	/**
	 * 移动端获取待审批数量
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getAuditCount(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		try {

			Long count = 0L;
			JSONObject json = new JSONObject();
			count += getBookCount(request);
			count += getSummaryCount(request);
			count += getTopicCount(request);
			count += getMainCount(request);
			json.element("count", count);

			request.setAttribute("lui-source", json);
		} catch (Exception e) {
			messages.addError(e);
		}
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			return getActionForward("failure", mapping, form, request,
					response);
		} else {
			return getActionForward("lui-source", mapping, form, request,
					response);
		}
	}

	private Long getBookCount(HttpServletRequest request) throws Exception {
		Long result = 0L;
		HQLInfo hqlInfo = new HQLInfo();
		String whereBlock = hqlInfo.getWhereBlock();
		whereBlock = StringUtil.linkString(whereBlock, " and ",
				"kmImeetingBook.fdExamer.fdId=:fdExamerId");
		hqlInfo.setParameter("fdExamerId", UserUtil.getUser().getFdId());

		whereBlock = StringUtil.linkString(whereBlock, " and ",
				"kmImeetingBook.fdHasExam is null");
		hqlInfo.setWhereBlock(whereBlock);
		hqlInfo.setGettingCount(true);
		List<Long> modelCount = getKmImeetingBookService(request)
				.findValue(hqlInfo);
		result += modelCount.get(0);
		return result;
	}

	private Long getSummaryCount(HttpServletRequest request) throws Exception {
		Long result = 0L;
		HQLInfo hqlInfo = new HQLInfo();
		SysFlowUtil.buildLimitBlockForMyApproval("kmImeetingSummary", hqlInfo);
		hqlInfo.setCheckParam(SysAuthConstant.CheckType.AllCheck,
				SysAuthConstant.AllCheck.NO);
		String whereBlock = hqlInfo.getWhereBlock();
		whereBlock = StringUtil.linkString(whereBlock, " and ",
				"kmImeetingSummary.docStatus not in ('10','11','30','00')");
		hqlInfo.setWhereBlock(whereBlock);
		hqlInfo.setGettingCount(true);
		List<Long> modelCount = getKmImeetingSummaryService(request)
				.findValue(hqlInfo);
		result += modelCount.get(0);
		return result;
	}

	private Long getTopicCount(HttpServletRequest request) throws Exception {
		Long result = 0L;
		HQLInfo hqlInfo = new HQLInfo();
		SysFlowUtil.buildLimitBlockForMyApproval("kmImeetingTopic", hqlInfo);
		hqlInfo.setCheckParam(SysAuthConstant.CheckType.AllCheck,
				SysAuthConstant.AllCheck.NO);
		String whereBlock = hqlInfo.getWhereBlock();
		whereBlock = StringUtil.linkString(whereBlock, " and ",
				"kmImeetingTopic.docStatus not in ('10','11','30','00')");
		hqlInfo.setWhereBlock(whereBlock);
		hqlInfo.setGettingCount(true);
		List<Long> modelCount = getKmImeetingTopicService(request)
				.findValue(hqlInfo);
		result += modelCount.get(0);
		return result;
	}

	private Long getMainCount(HttpServletRequest request) throws Exception {
		Long result = 0L;
		HQLInfo hqlInfo = new HQLInfo();
		SysFlowUtil.buildLimitBlockForMyApproval("kmImeetingMain", hqlInfo);
		hqlInfo.setCheckParam(SysAuthConstant.CheckType.AllCheck,
				SysAuthConstant.AllCheck.NO);
		String whereBlock = hqlInfo.getWhereBlock();
		whereBlock = StringUtil.linkString(whereBlock, " and ", "kmImeetingMain.docStatus not in ('10','11','30','00')");
		hqlInfo.setWhereBlock(whereBlock);
		hqlInfo.setGettingCount(true);
		List<Long> modelCount = getServiceImp(request)
				.findValue(hqlInfo);
		result += modelCount.get(0);
		return result;
	}

	public ActionForward getVotes(ActionMapping mapping, ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			JSONArray array = new JSONArray();
			String fdMainId = request.getParameter("fdMainId");
			KmImeetingMain main = (KmImeetingMain) getServiceImp(request)
					.findByPrimaryKey(fdMainId);
			if (main != null) {
				List<KmImeetingVote> kmImeetingVotes = main
						.getKmImeetingVotes();
				for (KmImeetingVote vote : kmImeetingVotes) {
					JSONObject obj = new JSONObject();
					obj.put("text", vote.getDocSubject());
					obj.put("value", vote.getFdId());
					array.add(obj);
				}
			}
			request.setAttribute("lui-source", array);
		} catch (Exception e) {
			messages.addError(e);
		}
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			return mapping.findForward("failure");
		} else {
			return mapping.findForward("lui-source");
		}
	}

	public ActionForward getVoteResult(ActionMapping mapping, ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			JSONObject object = new JSONObject();
			String fdMainId = request.getParameter("fdMainId");
			String fdVoteId = request.getParameter("fdVoteId");
			if (StringUtil.isNotNull(fdMainId)
					&& StringUtil.isNotNull(fdVoteId)) {
				KmImeetingMain kmImeetingMain = (KmImeetingMain) getServiceImp(
						request).findByPrimaryKey(fdMainId);
				KmImeetingVote kmImeetingVote = (KmImeetingVote) getKmImeetingVoteService()
						.findByPrimaryKey(fdVoteId);
				if (kmImeetingMain != null && kmImeetingVote != null) {
					KmImeetingMapping kimMain = getKmImeetingMappingService()
							.findByModelId(kmImeetingMain.getFdId(),
									KmImeetingMain.class.getName());
					KmImeetingMapping kimVote = getKmImeetingMappingService()
							.findByModelId(kmImeetingVote.getFdId(),
									KmImeetingVote.class.getName());
					if (kimMain != null && kimVote != null) {
						// http://IP:PORT/meeting-web/openapi/statistics/votes/12?meetId=52
						String url = BoenUtil.getBoenUrl()
								+ "/openapi/statistics/votes/"
								+ kimVote.getFdThirdSysId() + "?meetId="
								+ kimMain.getFdThirdSysId();
						String result = BoenUtil.sendGet(url);
						if (StringUtil.isNotNull(result)) {
							JSONObject res = JSONObject.fromObject(result);
							if (res.getInt("status") == 200) {
								String voteResult = res.get("data").toString();
								object = JSONObject.fromObject(voteResult);
								kmImeetingVote.setFdVoteResult(voteResult);
								getKmImeetingVoteService()
										.update(kmImeetingVote);
							} else {
								String message = (String) res.get("message");
								throw new RuntimeException(message);
							}
						}
					}
				}

			}

			request.setAttribute("lui-source", object);
		} catch (Exception e) {
			messages.addError(e);
		}
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			return mapping.findForward("failure");
		} else {
			return mapping.findForward("lui-source");
		}
	}

	public ActionForward getBallots(ActionMapping mapping, ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			JSONArray array = new JSONArray();
			String fdMainId = request.getParameter("fdMainId");
			KmImeetingMain main = (KmImeetingMain) getServiceImp(request)
					.findByPrimaryKey(fdMainId);
			if (main != null) {
				List<KmImeetingAgenda> kmImeetingAgendas = main
						.getKmImeetingAgendas();
				for (KmImeetingAgenda agenda : kmImeetingAgendas) {
					JSONObject obj = new JSONObject();
					obj.put("text", agenda.getDocSubject());
					obj.put("value", agenda.getFdId());
					array.add(obj);
				}
			}
			request.setAttribute("lui-source", array);
		} catch (Exception e) {
			messages.addError(e);
		}
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			return mapping.findForward("failure");
		} else {
			return mapping.findForward("lui-source");
		}
	}

	public ActionForward getBallotResult(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			JSONObject object = new JSONObject();
			String fdBallotId = request.getParameter("fdBallotId");
			if (StringUtil.isNotNull(fdBallotId)) {
				KmImeetingAgenda kmImeetingAgenda = (KmImeetingAgenda) getKmImeetingAgendaService(
						request).findByPrimaryKey(fdBallotId);
				if (kmImeetingAgenda != null) {
					// http://IP:PORT/meeting-web/openapi/statistics/ballots/
					String url = BoenUtil.getBoenUrl()
							+ "/openapi/statistics/ballotByThird/"
							+ kmImeetingAgenda.getFdId();
					String result = BoenUtil.sendGet(url);
					if (StringUtil.isNotNull(result)) {
						JSONObject res = JSONObject.fromObject(result);
						if (res.getInt("status") == 200) {
							String ballotResult = res.get("data").toString();
							object = JSONObject.fromObject(ballotResult);
							kmImeetingAgenda.setFdBallotResult(ballotResult);
							getKmImeetingAgendaService(request)
									.update(kmImeetingAgenda);
						} else {
							String message = (String) res.get("message");
							throw new RuntimeException(message);
						}
					}
				}

			}
			request.setAttribute("lui-source", object);
		} catch (Exception e) {
			messages.addError(e);
		}
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			return mapping.findForward("failure");
		} else {
			return mapping.findForward("lui-source");
		}
	}

	public ActionForward getControlResult(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			JSONObject object = new JSONObject();
			String s_pageno = request.getParameter("pageno");
			String s_rowsize = request.getParameter("rowsize");
			int pageno = 1;
			int rowsize = SysConfigParameters.getRowSize();
			if (s_pageno != null && s_pageno.length() > 0
					&& Integer.parseInt(s_pageno) > 0) {
				pageno = Integer.parseInt(s_pageno);
			}
			if (s_rowsize != null && s_rowsize.length() > 0
					&& Integer.parseInt(s_rowsize) > 0) {
				rowsize = Integer.parseInt(s_rowsize);
			}
			String fdMainId = request.getParameter("fdMainId");
			String fdType = request.getParameter("fdType");

			if (StringUtil.isNotNull(fdMainId)
					&& StringUtil.isNotNull(fdType)) {
				KmImeetingMapping kimMain = getKmImeetingMappingService()
						.findByModelId(fdMainId,
								KmImeetingMain.class.getName());
				if (kimMain != null) {
					String fdPersonName = request
							.getParameter("q.fdPersonName");
					String fdPushType = request.getParameter("q.fdPushType");
					switch (fdType) {
					case "1":
						object = getOnlineResult(kimMain.getFdThirdSysId(),
								pageno, rowsize, fdPersonName);
						break;
					case "2":
						object = getPushResult(kimMain.getFdThirdSysId(),
								pageno, rowsize, fdPersonName, fdPushType);
						break;
					case "3":
						object = getFileDownloadResult(
								kimMain.getFdThirdSysId(),
								pageno, rowsize, fdPersonName);
						break;
					case "4":
						object = getFileUploadResult(kimMain.getFdThirdSysId(),
								pageno, rowsize, fdPersonName);
						break;
					default:
						break;
					}
				}
			}
			request.setAttribute("lui-source", object);
		} catch (Exception e) {
			messages.addError(e);
		}
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			return mapping.findForward("failure");
		} else {
			return mapping.findForward("lui-source");
		}
	}

	private JSONObject getFileUploadResult(String meetId, int pageno,
			int rowsize, String fdPersonName) throws Exception {
		JSONObject object = new JSONObject();
		String url = BoenUtil.getBoenUrl()
				+ "/brower/monitors/commentsback/?meetId=" + meetId;
		if (StringUtil.isNotNull(fdPersonName)) {
			url += "&userName=" + fdPersonName;
		}
		String result = BoenUtil.sendGet(url);
		if (StringUtil.isNotNull(result)) {
			JSONObject res = JSONObject.fromObject(result);
			if (res.getInt("code") == 1) {
				String data = res.get("data").toString();
				object = formartFileUploadJson(data);
			} else {
				String message = (String) res.get("message");
				throw new RuntimeException(message);
			}
		}
		return object;
	}

	private JSONObject formartFileUploadJson(String data) throws Exception {
		JSONObject result = new JSONObject();
		JSONArray dataArr = JSONArray.fromObject(data);

		Map<String, String> map = new HashMap<>();
		map.put("name", "参会人姓名");
		map.put("padIMEI", "padIMEI");
		map.put("start", "开始时间");
		map.put("end", "结束时间");
		map.put("success", "状态");
		map.put("msg", "上传失败日志");
		JSONArray columns = new JSONArray();
		Set<String> kyes = map.keySet();
		for (String key : kyes) {
			JSONObject colObj = getColObj(key, map.get(key));
			columns.add(colObj);
		}
		result.put("columns", columns);
		JSONArray datas = new JSONArray();
		for (int i = 0; i < dataArr.size(); i++) {
			JSONArray dArr = new JSONArray();
			JSONObject obj = dataArr.getJSONObject(i);
			for (String key : kyes) {
				JSONObject dObj = getDataObj(key, map.get(key), obj);
				dArr.add(dObj);
			}
			datas.add(dArr);
		}
		result.put("datas", datas);

		return result;
	}

	private JSONObject getFileDownloadResult(String meetId, int pageno,
			int rowsize, String fdPersonName) throws Exception {
		JSONObject object = new JSONObject();
		String url = BoenUtil.getBoenUrl()
				+ "/brower/monitors/attachmets/?meetId=" + meetId;
		if (StringUtil.isNotNull(fdPersonName)) {
			url += "&userName=" + fdPersonName;
		}
		String result = BoenUtil.sendGet(url);
		if (StringUtil.isNotNull(result)) {
			JSONObject res = JSONObject.fromObject(result);
			if (res.getInt("code") == 1) {
				String data = res.get("data").toString();
				object = formartFileDownloadJson(data);
			} else {
				String message = (String) res.get("message");
				throw new RuntimeException(message);
			}
		}
		return object;
	}

	private JSONObject formartFileDownloadJson(String data) throws Exception {
		JSONObject result = new JSONObject();
		JSONArray dataArr = JSONArray.fromObject(data);

		Map<String, String> map = new HashMap<>();
		map.put("name", "参会人姓名");
		map.put("padIMEI", "padIMEI");
		map.put("start", "开始时间");
		map.put("end", "结束时间");
		map.put("success", "状态");
		map.put("msg", "下载失败日志");
		JSONArray columns = new JSONArray();
		Set<String> kyes = map.keySet();
		for (String key : kyes) {
			JSONObject colObj = getColObj(key, map.get(key));
			columns.add(colObj);
		}
		result.put("columns", columns);

		JSONArray datas = new JSONArray();
		for (int i = 0; i < dataArr.size(); i++) {
			JSONArray dArr = new JSONArray();
			JSONObject obj = dataArr.getJSONObject(i);
			for (String key : kyes) {
				JSONObject dObj = getDataObj(key, map.get(key), obj);
				dArr.add(dObj);
			}
			datas.add(dArr);
		}
		result.put("datas", datas);

		return result;
	}

	private JSONObject getOnlineResult(String meetId, Integer pageno,
			Integer rowsize, String fdPersonName) throws Exception {
		// htp:/IP:ORT/meting-web/openapi/monitors/onlines?pageNum=1&pageSize=1&metId=201
		JSONObject object = new JSONObject();
		String url = BoenUtil.getBoenUrl()
				+ "/openapi/monitors/onlines/?meetId=" + meetId
				+ "&pageNum=" + pageno + "&pageSize=" + rowsize;
		if (StringUtil.isNotNull(fdPersonName)) {
			url += "&userName=" + fdPersonName;
		}
		String result = BoenUtil.sendGet(url);
		if (StringUtil.isNotNull(result)) {
			JSONObject res = JSONObject.fromObject(result);
			if (res.getInt("status") == 200) {
				String data = res.get("data").toString();
				object = formartOnlineJson(data);
			} else {
				String message = (String) res.get("message");
				throw new RuntimeException(message);
			}
		}
		return object;
	}

	private JSONObject formartOnlineJson(String data) throws Exception {
		JSONObject result = new JSONObject();
		JSONObject dataObj = JSONObject.fromObject(data);

		Map<String, String> map = new HashMap<>();
		map.put("name", "参会人姓名");
		map.put("padIMEI", "padIMEI");
		map.put("online", "是否在线");
		JSONArray columns = new JSONArray();
		Set<String> kyes = map.keySet();
		for (String key : kyes) {
			JSONObject colObj = getColObj(key, map.get(key));
			columns.add(colObj);
		}
		result.put("columns", columns);
		JSONArray list = dataObj.getJSONArray("list");
		JSONArray datas = new JSONArray();
		for (int i = 0; i < list.size(); i++) {
			JSONArray dArr = new JSONArray();
			JSONObject obj = list.getJSONObject(i);
			for (String key : kyes) {

				JSONObject dObj = getDataObj(key, map.get(key), obj);
				dArr.add(dObj);
			}
			datas.add(dArr);
		}
		result.put("datas", datas);

		JSONObject page = new JSONObject();
		page.put("currentPage", dataObj.get("pageNum"));
		page.put("pageSize", dataObj.get("pageSize"));
		page.put("totalSize", dataObj.get("total"));
		result.put("page", page);
		return result;
	}

	private JSONObject getPushResult(String meetId, Integer pageno,
			Integer rowsize, String fdPersonName, String fdPushType)
			throws Exception {
		// htp:/IP:ORT/meting-web/openapi/monitors/meetPushes?pageNum=1&pageSize=1&metId=201
		JSONObject object = new JSONObject();
		String url = BoenUtil.getBoenUrl()
				+ "/openapi/monitors/meetPushes?meetId=" + meetId
				+ "&pageNum=" + pageno + "&pageSize=" + rowsize;
		if (StringUtil.isNotNull(fdPersonName)) {
			url += "&userName=" + fdPersonName;
		}
		if (StringUtil.isNotNull(fdPushType)) {
			url += "&msgType=" + fdPushType;
		}
		String result = BoenUtil.sendGet(url);
		if (StringUtil.isNotNull(result)) {
			JSONObject res = JSONObject.fromObject(result);
			if (res.getInt("status") == 200) {
				String data = res.get("data").toString();
				object = formartPushJson(data);
			} else {
				String message = (String) res.get("message");
				throw new RuntimeException(message);
			}
		}
		return object;
	}

	private JSONObject getColObj(String key, String value) {
		JSONObject colObj = new JSONObject();
		if ("padIMEI".equals(key)) {
			colObj.put("title", "序列号");
		} else {
			colObj.put("title", value);
		}

		colObj.put("property", key);
		return colObj;
	}

	private JSONObject getDataObj(String key, String value, JSONObject obj)
			throws Exception {
		JSONObject dObj = new JSONObject();
		dObj.put("col", key);

		if (obj.containsKey(key)) {
			if ("online".equals(key)) {
				String var = obj.getString(key);
				if ("false".equals(var)) {
					dObj.put("value", "否");
				} else if ("true".equals(var)) {
					dObj.put("value", "是");
				} else {
					dObj.put("value", "");
				}
			} else if ("responseStatus".equals(key)) {
				String var = obj.getString(key);
				if ("false".equals(var)) {
					dObj.put("value", "同步失败");
				} else if ("true".equals(var)) {
					dObj.put("value", "同步完成");
				} else {
					dObj.put("value", "");
				}
			} else if ("success".equals(key)) {
				String var = obj.getString(key);
				if ("false".equals(var)) {
					dObj.put("value", "失败");
				} else if ("true".equals(var)) {
					dObj.put("value", "成功");
				} else {
					dObj.put("value", "");
				}
			} else if ("sendStatus".equals(key)) {
				String var = obj.getString(key);
				if ("false".equals(var)) {
					dObj.put("value", "未发送");
				} else if ("true".equals(var)) {
					dObj.put("value", "已发送");
				} else {
					dObj.put("value", "");
				}
			} else if ("sendTime".equals(key) || "receiveTime".equals(key)
					|| "responseTime".equals(key) || "start".equals(key)
					|| "end".equals(key)) {
				// 时间文本
				// 2020-04-13T14:59:12.000+0800
				String dateText = obj.getString(key);
				// 格式
				SimpleDateFormat formatter = new SimpleDateFormat(
						"yyyy-MM-dd'T'HH:mm:ss.SSS");
				Date date = formatter.parse(dateText);
				String val = DateUtil.convertDateToString(date,
						ResourceUtil
								.getString("date.format.time.sec"));
				dObj.put("value", val);
			} else {
				dObj.put("value", obj.getString(key));
			}
		} else {
			dObj.put("value", "");
		}
		return dObj;
	}

	private JSONObject formartPushJson(String data) throws Exception {
		JSONObject result = new JSONObject();
		JSONObject dataObj = JSONObject.fromObject(data);

		Map<String, String> map = new HashMap<>();
		map.put("userName", "参会人姓名");
		map.put("msgType", "即时推送类型");
		map.put("sendTime", "发送时间");
		map.put("sendStatus", "发送状态");
		map.put("receiveTime", "接收时间");
		map.put("responseTime", "处理完成时间");
		map.put("responseStatus", "消息处理状态");
		// map.put("imMsg", "处理描述");
		JSONArray columns = new JSONArray();
		Set<String> kyes = map.keySet();
		for (String key : kyes) {
			columns.add(getColObj(key, map.get(key)));
		}
		result.put("columns", columns);

		JSONArray list = dataObj.getJSONArray("list");
		JSONArray datas = new JSONArray();
		for (int i = 0; i < list.size(); i++) {
			JSONArray dArr = new JSONArray();
			JSONObject obj = list.getJSONObject(i);
			for (String key : kyes) {
				JSONObject dObj = getDataObj(key, map.get(key), obj);
				dArr.add(dObj);
			}
			datas.add(dArr);
		}
		result.put("datas", datas);

		JSONObject page = new JSONObject();
		page.put("currentPage", dataObj.get("pageNum"));
		page.put("pageSize", dataObj.get("pageSize"));
		page.put("totalSize", dataObj.get("total"));
		result.put("page", page);
		return result;
	}
	
}
