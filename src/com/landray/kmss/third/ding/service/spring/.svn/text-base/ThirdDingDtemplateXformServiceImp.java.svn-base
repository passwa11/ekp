package com.landray.kmss.third.ding.service.spring;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.springframework.transaction.TransactionStatus;

import com.dingtalk.api.response.OapiProcessDeleteResponse;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.model.SysOrgPost;
import com.landray.kmss.sys.organization.service.ISysOrgPostService;
import com.landray.kmss.third.ding.model.DingConfig;
import com.landray.kmss.third.ding.model.ThirdDingDtemplateXform;
import com.landray.kmss.third.ding.model.ThirdDingTemplateXDetail;
import com.landray.kmss.third.ding.provider.DingNotifyUtil;
import com.landray.kmss.third.ding.service.IOmsRelationService;
import com.landray.kmss.third.ding.service.IThirdDingDtemplateXformService;
import com.landray.kmss.third.ding.util.DingUtil;
import com.landray.kmss.third.ding.util.DingUtils;
import com.landray.kmss.third.ding.util.ThirdDingUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.TransactionUtils;
import com.landray.kmss.util.UserUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class ThirdDingDtemplateXformServiceImp extends ExtendDataServiceImp implements IThirdDingDtemplateXformService {

    private ISysNotifyMainCoreService sysNotifyMainCoreService;

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(ThirdDingDtemplateXformServiceImp.class);

    @Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof ThirdDingDtemplateXform) {
            ThirdDingDtemplateXform thirdDingDtemplateXform = (ThirdDingDtemplateXform) model;
        }
        return model;
    }

    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        ThirdDingDtemplateXform thirdDingDtemplateXform = new ThirdDingDtemplateXform();
        thirdDingDtemplateXform.setFdIsAvailable(Boolean.valueOf("true"));
        thirdDingDtemplateXform.setDocCreateTime(new Date());
        ThirdDingUtil.initModelFromRequest(thirdDingDtemplateXform, requestContext);
        return thirdDingDtemplateXform;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        ThirdDingDtemplateXform thirdDingDtemplateXform = (ThirdDingDtemplateXform) model;
    }

    public ISysNotifyMainCoreService getSysNotifyMainCoreService() {
        if (sysNotifyMainCoreService == null) {
            sysNotifyMainCoreService = (ISysNotifyMainCoreService) SpringBeanUtil.getBean("sysNotifyMainCoreService");
        }
        return sysNotifyMainCoreService;
    }

	@Override
	public String addCommonTemplate(ThirdDingDtemplateXform temp, String fdLang,
			List<String> titleList, JSONObject param, List allReader)
			throws Exception {

		String code = null;
		String origin_dir_id = "";
		if (temp != null && StringUtil.isNotNull(temp.getFdDirid())) {
			origin_dir_id = temp.getFdDirid();
		}
		if (param != null) {
			param.put("origin_dir_id", origin_dir_id);
		}
		DingConfig config = DingConfig.newInstance();
		String name;
		if (StringUtil.isNotNull(config.getDingAgentid())) {
			String token = DingUtils.getDingApiService().getAccessToken();
			if (temp != null && StringUtil.isNotNull(temp.getFdProcessCode())) {
				code = temp.getFdProcessCode();
			}
			if (param != null && param.containsKey("name")) {
				name = param.getString("name");
			} else {
				name = "通用待办模板" + System.currentTimeMillis();
			}

			String desc = "来自蓝凌EKP流程管理";
			if (param != null && param.containsKey("desc")
					&& StringUtil.isNotNull(param.getString("desc"))) {
				desc = param.getString("desc");
			}

			JSONObject response = DingNotifyUtil
					.createTemplateXform(
					token,
							Long.valueOf(config.getDingAgentid()), name, desc,
							true,
							code, fdLang, titleList, param);
			logger.debug("response:" + response);

			if (response != null && response.getInt("errcode") == 0) {
				logger.debug("创建模版   response:" + response);
				
				String fdAllReaders = "";

				//可见范围
				String readers = updateAuthReader(allReader,
						Long.valueOf(config.getDingAgentid()),
						response.getJSONObject("result")
								.getString("process_code"));
				
				if (temp == null) {
					code = response.getJSONObject("result")
							.getString("process_code");
					addDtemplateInfo(name, desc, code, fdLang, readers,
							titleList, param, temp, "add", null);

				} else {
					if (temp.getDocCreateTime() == null) {
                        temp.setDocCreateTime(new Date());
                    }
					addDtemplateInfo(name, desc, code, fdLang, readers,
							titleList, param, temp, "update", null);
				}
			} else {
				logger.error("创建待办模板失败，详细错误：" + response == null ? "返回结果为null"
						: response + ",name="
						+ name);
				addDtemplateInfo(name, desc, code, fdLang, "",
						titleList, param, null, "error", "创建待办模板失败，详细错误："
								+ (response == null ? "返回结果为null" : response));

			}
		} else {
			logger.warn("钉钉集成的应用Id为空，无法创建待办模板，导致无法发送待办");
		}
		return code;
	}

	private void addDtemplateInfo(String name, String desc, String code,
			String fdLang, String readers, List<String> titleList,
			JSONObject param, ThirdDingDtemplateXform template, String stauts,
			String fdErrMsg) throws Exception {

		if (template == null) {
			template = new ThirdDingDtemplateXform();
		}
		template.setFdAgentId(DingConfig.newInstance().getDingAgentid());
		template.setFdName(name);
		template.setFdDesc(desc);
		template.setFdDisableFormEdit("1");
		template.setFdProcessCode(code);
		template.setFdCorpId(DingUtil.getCorpId());
		template.setFdType("1");
		if ("error".equals(stauts)) {
			template.setFdIsAvailable(false);
		} else {
			template.setFdIsAvailable(true);
		}
		template.setFdLang(fdLang);
		template.setFdAllReaders(readers);
		if (param != null && param.containsKey("fdId")) {
			template.setFdTemplateId(param.getString("fdId"));
		}
		List<ThirdDingTemplateXDetail> details;
		if ("update".equals(stauts)) {
			details = template.getFdDetail();
			details.clear();
		} else {
			details = new ArrayList<ThirdDingTemplateXDetail>();
		}

		if (titleList != null && titleList.size() > 0) {
			for (int i = 0; i < titleList.size(); i++) {
				ThirdDingTemplateXDetail detail = new ThirdDingTemplateXDetail();
				detail.setFdName(titleList.get(i));
				if ("请假类型".equals(titleList.get(i))) {
					detail.setFdType("DDSelectField");
				} else if ("时长".equals(titleList.get(i))) {
					detail.setFdType("NumberField");
				} else if ("开始时间".equals(titleList.get(i))
						|| "结束时间".equals(titleList.get(i))
						|| "补卡时间".equals(titleList.get(i))) {
					detail.setFdType("DDDateField");
				} else {
					detail.setFdType("TextField");
				}
				details.add(detail);
			}
		} else {
			ThirdDingTemplateXDetail detail = new ThirdDingTemplateXDetail();
			detail.setFdName("标题");
			detail.setFdType("TextField");
			details.add(detail);
			detail = new ThirdDingTemplateXDetail();
			detail.setFdName("创建者");
			detail.setFdType("TextField");
			details.add(detail);
			detail = new ThirdDingTemplateXDetail();
			detail.setFdName("创建时间");
			detail.setFdType("TextField");
			details.add(detail);
		}
		template.setFdDetail(details);
		template.setDocCreateTime(new Date());
		template.setFdFlow(param.containsKey("type")
				? param.getString("type") : null);
		template.setFdDirid(param.getJSONObject("save_config")
				.getString("dir_id"));
		if (param.containsKey("origin_dir_id")) {
			template.setFdOriginDirid(param.getString("origin_dir_id"));
		}
		template.setFdIcon(param.getJSONObject("save_config")
				.getString("icon"));
		template.setFdPcUrl(param.getJSONObject("save_config")
				.getString("create_instance_pc_url"));
		template.setFdMobileUrl(param.getJSONObject("save_config")
				.getString("create_instance_mobile_url"));
		template.setFdProcessConfig(param
				.getJSONObject("save_config")
				.getJSONObject("process_config").toString());

		if ("add".equals(stauts)) {
			super.add(template);
		} else if ("update".equals(stauts)) {
			super.update(template);
		} else if ("error".equals(stauts)) {
			template.setFdErrMsg(fdErrMsg);
			super.add(template);
		}

	}

	private String updateAuthReader(List allReader, Long agentId,
			String pcode) {

		try {
			String rs = "";
			if (allReader != null && allReader.size() > 0) {
				if (allReader.size() == 1 && "everyone".equals(
						((SysOrgElement) allReader.get(0)).getFdName())) {
					return "所有人";
				} else {
					JSONArray visible_list = new JSONArray();
					JSONObject visi = new JSONObject();
					IOmsRelationService omsRelationService = (IOmsRelationService) SpringBeanUtil
							.getBean("omsRelationService");
					ISysOrgPostService sysOrgPostService = (ISysOrgPostService) SpringBeanUtil
							.getBean("sysOrgPostService");
					Map<String, String> readerMap = new HashMap<String, String>();
					String ekpUserId = null;
					for (int i = 0; i < allReader.size(); i++) {
						SysOrgElement reader = (SysOrgElement) allReader
								.get(i);
						if (reader.getFdOrgType()
								.equals(SysOrgElement.ORG_TYPE_DEPT)
								|| reader.getFdOrgType().equals(
										SysOrgElement.ORG_TYPE_ORG)) {
							String dept_dingId = omsRelationService
									.getDingUserIdByEkpUserId(
											reader.getFdId());
							if (StringUtil.isNotNull(dept_dingId)) {
								readerMap.put(reader.getFdId(),
										dept_dingId);
								ekpUserId = reader.getFdId();
								if (StringUtil.isNotNull(rs)) {
									rs += ";" + reader.getFdName();
								} else {
									rs = reader.getFdName();
								}
								visi = new JSONObject();
								visi.put("visible_value", dept_dingId);
								visi.put("visible_type", "0");
								visible_list.add(visi);
							} else {
								logger.warn("部门   " + reader.getFdName()
										+ "  在对照表中无关系，请先维护对照表信息！");
							}

						} else if (reader.getFdOrgType()
								.equals(SysOrgElement.ORG_TYPE_POST)) {
							SysOrgPost sysOrgPost = (SysOrgPost) sysOrgPostService
									.findByPrimaryKey(reader.getFdId());
							if (sysOrgPost != null
									&& sysOrgPost.getFdPersons() != null
									&& sysOrgPost.getFdPersons()
											.size() > 0) {
								for (int k = 0; k < sysOrgPost
										.getFdPersons().size(); k++) {
									SysOrgPerson per = (SysOrgPerson) sysOrgPost
											.getFdPersons().get(i);
									String user_dingId = omsRelationService
											.getDingUserIdByEkpUserId(
													per.getFdId());
									if (StringUtil.isNotNull(user_dingId)) {
										readerMap.put(per.getFdId(),
												user_dingId);
										ekpUserId = per.getFdId();
										if (StringUtil.isNotNull(rs)) {
											rs += ";" + per.getFdName();
										} else {
											rs = per.getFdName();
										}
										visi = new JSONObject();
										visi.put("visible_value",
												user_dingId);
										visi.put("visible_type", "1");
										visible_list.add(visi);
									} else {
										logger.warn(
												"人员   " + reader.getFdName()
														+ "  在对照表中无关系，请先维护对照表信息！");
									}
								}

							}

						} else if (reader.getFdOrgType()
								.equals(SysOrgElement.ORG_TYPE_PERSON)) {
							String user_dingId = omsRelationService
									.getDingUserIdByEkpUserId(
											reader.getFdId());
							if (StringUtil.isNotNull(user_dingId)) {
								readerMap.put(reader.getFdId(),
										user_dingId);
								ekpUserId = reader.getFdId();
								if (StringUtil.isNotNull(rs)) {
									rs += ";" + reader.getFdName();
								} else {
									rs = reader.getFdName();
								}
								visi = new JSONObject();
								visi.put("visible_value", user_dingId);
								visi.put("visible_type", "1");
								visible_list.add(visi);
							} else {
								logger.warn("人员   " + reader.getFdName()
										+ "  在对照表中无关系，请先维护对照表信息！");
							}
						}

					}

					// 判断map是否含有创建者
					String userid = "";
					if (!readerMap.isEmpty() && !readerMap
							.containsKey(UserUtil.getUser().getFdId())) {
						String user_dingId = omsRelationService
								.getDingUserIdByEkpUserId(
										UserUtil.getUser().getFdId());
						if (StringUtil.isNotNull(user_dingId)) {
							readerMap.put(UserUtil.getUser().getFdId(),
									user_dingId);
							userid = user_dingId;
							visi = new JSONObject();
							visi.put("visible_value", user_dingId);
							visi.put("visible_type", "1");
							visible_list.add(visi);
						} else {
							logger.warn(
									"人员   " + UserUtil.getUser().getFdName()
											+ "  在对照表中无关系，请先维护对照表信息！");
						}

					}

					JSONObject save_config = new JSONObject();
					save_config.put("ekpUserId", ekpUserId);
					save_config.put("visible_list", visible_list);
					if (StringUtil.isNull(userid) && !readerMap.isEmpty()) {
						Collection<String> co = readerMap.values();
						for (Iterator<String> iterator = co
								.iterator(); iterator.hasNext();) {
							userid = (String) iterator.next();
							break;
						}
					}
					save_config.put("userid", userid);

					JSONObject procvisible_response = DingNotifyUtil
							.procvisibleSave(
									DingUtils.getDingApiService()
											.getAccessToken(),
									agentId,
									pcode,
									save_config);
					logger.debug(
							"procvisible_response:" + procvisible_response);
					if (procvisible_response != null
							&& procvisible_response.getInt("errcode") == 0) {
						return rs;
					} else {
						return procvisible_response == null ? "钉钉返回为空"
								: procvisible_response.toString();
					}
				}

			}
		} catch (Exception e) {
			logger.error("同步模版可见范围失败！" + e.getMessage(), e);
			return "同步模版可见范围失败！" + e.getMessage();
		}

		return null;
	}

	@Override
	public void deleteTemplate(ThirdDingDtemplateXform temp) throws Exception {
		try {
			if (temp == null) {
				return;
			}
			OapiProcessDeleteResponse response = DingNotifyUtil.deleteTemplate(
					temp.getFdCorpId(), temp.getFdProcessCode(),
					DingUtils.getDingApiService().getAccessToken());
			if (response.getErrcode() == 0) {
				temp.setFdIsAvailable(false);
				super.update(temp);
			} else if (response.getErrcode() == 830001) {
				logger.warn("-----钉钉模板可能不存在，更新ekp模板状态-----");
				temp.setFdIsAvailable(false);
				super.update(temp);
			} else {
				logger.warn("删除钉钉模板失败！" + response.getBody());
			}
		} catch (Exception e) {
			logger.error("删除钉钉模板失败", e);
		}
	}

	private IBaseService kmReviewTemplateService;

	public IBaseService getKmReviewTemplateService() {
		if (kmReviewTemplateService == null) {
			kmReviewTemplateService = (IBaseService) SpringBeanUtil
					.getBean("kmReviewTemplateService");
		}
		return kmReviewTemplateService;
	}

	private IThirdDingDtemplateXformService thirdDingDtemplateXformService = null;

	public IThirdDingDtemplateXformService getThirdDingDtemplateXformService() {
		if (thirdDingDtemplateXformService == null) {
			thirdDingDtemplateXformService = (IThirdDingDtemplateXformService) SpringBeanUtil
					.getBean("thirdDingDtemplateXformService");
		}
		return thirdDingDtemplateXformService;
	}

	@Override
	public void updateTemplateInfo() throws Exception {
		// 判断是否开启高级审批
		String attendanceEnabled = DingConfig.newInstance()
				.getAttendanceEnabled();
		if (StringUtil.isNotNull(attendanceEnabled)
				&& "true".equalsIgnoreCase(attendanceEnabled)) {
			logger.warn("高级审批的开关已开启！");
			// 获取ekp的所有可用的流程模板
			logger.warn("----------获取ekp的所有可用的流程模板----------");
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setWhereBlock("fdIsAvailable=:fdIsAvailable");
			hqlInfo.setParameter("fdIsAvailable", true);
			List<IBaseModel> list = getKmReviewTemplateService()
					.findList(hqlInfo);
			for (IBaseModel temp : list) {
				Class clazz = temp.getClass();
				Method method = clazz.getMethod("getFdName");
				String name = (String) method.invoke(temp);
				logger.warn("fdId:" + temp.getFdId() + "  名称：" + name);
				// 先查钉钉组件有没有这个记录
				hqlInfo = new HQLInfo();
				hqlInfo.setWhereBlock(
						"fdTemplateId=:fdTemplateId and fdIsAvailable=:fdIsAvailable");
				hqlInfo.setParameter("fdTemplateId", temp.getFdId());
				hqlInfo.setParameter("fdIsAvailable", true);
				List<ThirdDingDtemplateXform> tempList = getThirdDingDtemplateXformService()
						.findList(hqlInfo);
				if (tempList != null && tempList.size() > 0) {
					logger.warn("---已有模板对照关系---" + name);
				} else {
					String rs = DingUtils.dingApiService
							.getProcessCodeByName(name);
					logger.warn("查询模板结果：" + rs);
					if (StringUtil.isNotNull(rs)) {
						JSONObject rsJSON = JSONObject.fromObject(rs);
						if (rsJSON.containsKey("errcode")
								&& rsJSON.getInt("errcode") == 0) {
							String code = rsJSON.getString("process_code");


							TransactionStatus status = null;
							try {
								status = TransactionUtils.beginNewTransaction();
								ThirdDingDtemplateXform tempXform = new ThirdDingDtemplateXform();
								tempXform.setFdProcessCode(code);
								tempXform.setFdName(name);
								tempXform.setFdTemplateId(temp.getFdId());
								tempXform.setFdIsAvailable(true);
								tempXform.setDocCreateTime(new Date());
								tempXform.setFdErrMsg("初始化创建的对照关系");
								getThirdDingDtemplateXformService()
										.add(tempXform);
								getThirdDingDtemplateXformService()
										.flushHibernateSession();
								TransactionUtils.getTransactionManager()
										.commit(status);
							} catch (Exception e) {
								if (status != null) {
									try {
										TransactionUtils.getTransactionManager()
												.rollback(status);
									} catch (Exception ex) {
										logger.error("---事务回滚出错---", ex);
									}
								}
							}

							// getKmReviewTemplateService().update(temp);
							// 触发流程模块的更新模板
							TransactionStatus status2 = null;
							try {
								status2 = TransactionUtils
										.beginNewTransaction();
								IBaseModel _temp = getKmReviewTemplateService()
										.findByPrimaryKey(temp.getFdId(), null,
												true);
								getKmReviewTemplateService().update(_temp);
								TransactionUtils.getTransactionManager()
										.commit(status2);
							} catch (Exception e) {
								logger.error(e.getMessage(), e);
								if (status2 != null) {
									try {
										TransactionUtils.getTransactionManager()
												.rollback(status2);
									} catch (Exception ex) {
										logger.error("---事务回滚出错---", ex);
									}
								}
							}
						}

					}
				}
			}

		} else {
			logger.warn("高级审批的开关未开启！");
		}

	}

}
