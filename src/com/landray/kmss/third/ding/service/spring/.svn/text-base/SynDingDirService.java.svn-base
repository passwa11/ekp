package com.landray.kmss.third.ding.service.spring;

import com.alibaba.fastjson.JSON;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.sys.authorization.model.SysAuthArea;
import com.landray.kmss.sys.authorization.service.ISysAuthAreaService;
import com.landray.kmss.sys.category.model.SysCategoryMain;
import com.landray.kmss.sys.category.service.ISysCategoryMainService;
import com.landray.kmss.third.ding.model.ThirdDingCategoryLog;
import com.landray.kmss.third.ding.model.ThirdDingCategoryXform;
import com.landray.kmss.third.ding.service.IThirdDingCategoryLogService;
import com.landray.kmss.third.ding.service.IThirdDingCategoryXformService;
import com.landray.kmss.third.ding.util.DingUtils;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SynDingDirService {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SynDingDirService.class);

	private final int ORDER = 100;

	public static SynDingDirService newInstance() {
		SynDingDirService synDingDirService = null;
		try {
			synDingDirService = new SynDingDirService();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return synDingDirService;
	}

	private IThirdDingCategoryXformService thirdDingCategoryXformService;

	private IThirdDingCategoryLogService thirdDingCategoryLogService;

	public IThirdDingCategoryLogService getThirdDingCategoryLogService() {
		if (thirdDingCategoryLogService == null) {
			thirdDingCategoryLogService = (IThirdDingCategoryLogService) SpringBeanUtil
					.getBean("thirdDingCategoryLogService");
		}
		return thirdDingCategoryLogService;
	}

	public void setThirdDingCategoryLogService(
			IThirdDingCategoryLogService thirdDingCategoryLogService) {
		this.thirdDingCategoryLogService = thirdDingCategoryLogService;
	}

	private ISysCategoryMainService sysCategoryMainService;

	public ISysCategoryMainService getSysCategoryMainService() {
		if (sysCategoryMainService == null) {
			sysCategoryMainService = (ISysCategoryMainService) SpringBeanUtil
					.getBean("sysCategoryMainService");
		}
		return sysCategoryMainService;
	}

	public void setSysCategoryMainService(
			ISysCategoryMainService sysCategoryMainService) {
		this.sysCategoryMainService = sysCategoryMainService;
	}


	public IThirdDingCategoryXformService getThirdDingCategoryXformService() {
		if (thirdDingCategoryXformService == null) {
			thirdDingCategoryXformService = (IThirdDingCategoryXformService) SpringBeanUtil
					.getBean("thirdDingCategoryXformService");
		}
		return thirdDingCategoryXformService;
	}

	public void setThirdDingCategoryXformService(
			IThirdDingCategoryXformService thirdDingCategoryXformService) {
		this.thirdDingCategoryXformService = thirdDingCategoryXformService;
	}

	public void synCategoryFromDingByCorpIdAndRoomId(String corpId,
			String rootId) throws Exception {
		// synCategoryFromDing(corpId);
	}

	public void synCategoryFromDing(String corpId, String areaId)
			throws Exception {
		logger.debug("同步钉钉分组，corpId:" + corpId + "   areaId:" + areaId);

		try {
			synchronized (this) {
				ThirdDingCategoryLog categoryLog = new ThirdDingCategoryLog();
				categoryLog.setFdCorpId(corpId);
				categoryLog.setFdSynType("all");
				categoryLog.setFdSynTime(new Date());
				JSONObject param = new JSONObject();
				param.put("corpId", corpId);
				JSONObject rs = DingUtils.getDingApiService().getDirFrom(param);
				logger.debug("rs => " + rs);
				String input = "";
				if (rs != null) {
					input = rs.toString();
					if (input.length() > 2000) {
						logger.warn("input字段过程：" + input);
						input = input.substring(0, 2000);
					}
				}
				categoryLog.setFdInput(rs == null ? "钉钉返回为null" : input);
				if (rs != null && rs.getInt("errcode") == 0) {
					categoryLog.setFdSynStatus("1");
					JSONArray result = rs.getJSONArray("result");
					String content = "";
					for (int i = 0; i < result.size(); i++) {

						JSONObject dir = result.getJSONObject(i);
						if (StringUtil.isNotNull(content)) {
							content += ";";
						}

						String dirId = dir.getString("dir_id");
						String name = dir.getString("dir_name");
						if ("other".equals(dirId) && StringUtil.isNull(name)) {
							name = "其他";
						}
						content += name;

						String children = dir.containsKey("children")
								? dir.getString("children") : "";

						HQLInfo hqlInfo = new HQLInfo();
						hqlInfo.setWhereBlock(
								"thirdDingCategoryXform.fdDirid=:fdDirid and fdCorpid=:fdCorpid");
						hqlInfo.setParameter("fdDirid", dirId);
						hqlInfo.setParameter("fdCorpid", corpId);
						ThirdDingCategoryXform thirdDingCategoryXform = (ThirdDingCategoryXform) getThirdDingCategoryXformService()
								.findFirstOne(hqlInfo);
						if (thirdDingCategoryXform != null) {
							// 更新
							thirdDingCategoryXform.setFdName(name);
							thirdDingCategoryXform.setFdChildren(children);
							thirdDingCategoryXform.setFdIsAvailable(true);
							thirdDingCategoryXform.setDocAlterTime(new Date());


							// 更新模板分类的名称
							SysCategoryMain sysCategoryMain = (SysCategoryMain) getSysCategoryMainService()
									.findByPrimaryKey(thirdDingCategoryXform
											.getFdTemplateId(),
											SysCategoryMain.class, true);
							if (sysCategoryMain != null) {
								sysCategoryMain.setFdName(name);
								sysCategoryMain.setFdOrder(i);
								sysCategoryMain.setDocAlterTime(new Date());
								sysCategoryMain.setFdIsFromDing("1");
							} else {
								// 模板被删除了
								logger.warn(
										"ekp上对应的分类已删除！" + name + "(fdId:"
												+ thirdDingCategoryXform
														.getFdTemplateId()
												+ ")，重新建钉钉分类");
								String categoryId = createCategory(name, corpId,
										areaId);
								thirdDingCategoryXform
										.setFdTemplateId(categoryId);

							}
							if (StringUtil.isNotNull(areaId)) {
								ISysAuthAreaService authAreaService = (ISysAuthAreaService) SpringBeanUtil
										.getBean("sysAuthAreaService");
								SysAuthArea authArea = (SysAuthArea) authAreaService
										.findByPrimaryKey(areaId);
								sysCategoryMain.setAuthArea(authArea);
								thirdDingCategoryXform.setFdRoomId(areaId);
							}
							getThirdDingCategoryXformService()
									.update(thirdDingCategoryXform);
						} else {
							// 兼容初始化导入的问题
							String addAppKey = ResourceUtil
									.getKmssConfigString("kmss.ding.addAppKey");
							boolean isISVDemo = false;
							if (StringUtil.isNotNull(addAppKey)
									&& "true".equalsIgnoreCase(addAppKey)) {
								logger.warn("------ISV demo环境-------");
								isISVDemo = true;
							} else {
								logger.warn("------非ISV demo环境-------");
							}
							HQLInfo hql = new HQLInfo();
							hql.setSelectBlock("fdId");
							hql.setWhereBlock(
									"fdDesc like :fdDesc and fdModelName=:fdModelName and fdName=:fdName");
							hql.setParameter("fdDesc", "%来自钉钉分类%");
							hql.setParameter("fdModelName",
									"com.landray.kmss.km.review.model.KmReviewTemplate");
							hql.setParameter("fdName", name);
							String cateId = (String) getSysCategoryMainService()
									.findFirstOne(hql);
							if (isISVDemo || StringUtils.isNotBlank(cateId)) {
								// 新增
								// 添加分类
								SysCategoryMain sysCategoryMain = new SysCategoryMain();
								sysCategoryMain.setFdName(name);
								sysCategoryMain
										.setFdDesc("来自钉钉分类   corpId:" + corpId);
								sysCategoryMain.setFdOrder(i);
								sysCategoryMain.setDocCreateTime(new Date());
								sysCategoryMain.setFdModelName(
										"com.landray.kmss.km.review.model.KmReviewTemplate");
								sysCategoryMain.setFdIsinheritUser(true);
								sysCategoryMain.setFdIsinheritMaintainer(true);
								sysCategoryMain.setAuthReaderFlag(false);
								sysCategoryMain.setFdIsFromDing("1");
								thirdDingCategoryXform = new ThirdDingCategoryXform();
								if (StringUtil.isNotNull(areaId)) {
									ISysAuthAreaService authAreaService = (ISysAuthAreaService) SpringBeanUtil
											.getBean("sysAuthAreaService");
									SysAuthArea authArea = (SysAuthArea) authAreaService
											.findByPrimaryKey(areaId);
									sysCategoryMain.setAuthArea(authArea);
									thirdDingCategoryXform.setFdRoomId(areaId);
								}
								String id = getSysCategoryMainService()
										.add(sysCategoryMain);
								logger.debug("id:" + id);

								thirdDingCategoryXform.setFdName(name);
								thirdDingCategoryXform.setFdChildren(children);
								thirdDingCategoryXform.setFdDirid(dirId);
								thirdDingCategoryXform.setFdCorpid(corpId);
								thirdDingCategoryXform.setFdIsAvailable(true);
								thirdDingCategoryXform
										.setDocCreateTime(new Date());
								thirdDingCategoryXform.setFdTemplateId(id);
								getThirdDingCategoryXformService()
										.add(thirdDingCategoryXform);
							} else {
								// 更新
								logger.warn(
										"----存在钉钉分类，不新建分类，只是建立钉钉分类中间关系------："
												+ name);
								thirdDingCategoryXform = new ThirdDingCategoryXform();
								thirdDingCategoryXform.setFdRoomId(areaId);
								thirdDingCategoryXform.setFdName(name);
								thirdDingCategoryXform.setFdChildren(children);
								thirdDingCategoryXform.setFdDirid(dirId);
								thirdDingCategoryXform.setFdCorpid(corpId);
								thirdDingCategoryXform.setFdIsAvailable(true);
								thirdDingCategoryXform
										.setDocCreateTime(new Date());
								thirdDingCategoryXform
										.setFdTemplateId(cateId);
								getThirdDingCategoryXformService()
										.add(thirdDingCategoryXform);

							}
						}

					}
					categoryLog.setFdContent(content);
					// 排序
					orderDirs(result, corpId);

				} else {
					logger.error("同步钉钉审批分类失败！");
					categoryLog.setFdSynStatus("0");
				}
				getThirdDingCategoryLogService().add(categoryLog);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

	}

	/**
	 * 钉钉审批分组回调处理
	 * 
	 * @param dir
	 * @throws Exception
	 */
	public void dingDirCallBack(JSONObject dir)
			throws Exception {
		logger.debug("钉钉审批分组回调：" + dir);
		try {
			if (dir == null || dir.isEmpty()) {
				return;
			}

			String corpId = dir.getString("corpId");
			String syncAction = dir.getString("syncAction");

			ThirdDingCategoryLog categoryLog = new ThirdDingCategoryLog();
			categoryLog.setFdCorpId(corpId);
			categoryLog.setFdSynType("inc");
			categoryLog.setFdSynTime(new Date());
			String input = JSON.toJSONString(dir);
			if (StringUtil.isNotNull(input) && input.length() > 3000) {
				logger.warn("审批分类回调：" + input);
				input = input.substring(0, 3000);
			}
			categoryLog.setFdInput(input);
			categoryLog.setFdSynStatus("1");
			String name = "";
			if ("bpms_dir_insert".equals(syncAction)
					|| "bpms_dir_update".equals(syncAction)) {
				// 新增/更新 分类
				String op = "新增";
				if ("bpms_dir_update".equals(syncAction)) {
					op = "更新";
				}
				JSONObject dir_data = null;
				try {
					dir_data = dir.getJSONObject("changeData");
				} catch (Exception e) {
					String changeData = dir.getString("changeData");
					logger.debug("changeData:" + changeData);
					dir_data = JSONObject.fromObject(changeData);
				}
				logger.debug("dir_data:" + dir_data);
				name = "【" + op + "】" + dir_data.getString("dirName");
				addOrUpdateDir(dir_data.getString("dirId"),
						dir_data.getString("dirName"), corpId);

			} else if ("bpms_dir_delete".equals(syncAction)) {
				// 删除分类
				JSONObject dir_data = dir.getJSONObject("changeData");
					logger.debug("dir_data:" + dir_data);
				String _name = getDirNameByDirID(dir_data.getString("dirId"));
				name = "【删除】" + _name;
				deleteDir(dir_data.getString("dirId"), _name, corpId);
			} else if ("bpms_dir_orders_changed".equals(syncAction)) {
				name = "【排序变更】";
			}
			categoryLog.setFdContent(name);
			getThirdDingCategoryLogService().add(categoryLog);
			orderDirs(dir.containsKey("dirs") ? dir.getJSONArray("dirs") : null,
					corpId);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		
	}

	private void orderDirs(JSONArray dirs, String corpId) {
		logger.debug("分类排序    dirs：" + dirs);
		if (dirs == null || dirs.isEmpty()) {
			return;
		}
		try {
			// 找出流程的所有分类
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setWhereBlock("fdModelName=:fdModelName");
			hqlInfo.setParameter("fdModelName",
					"com.landray.kmss.km.review.model.KmReviewTemplate");
			List<SysCategoryMain> list = getSysCategoryMainService()
					.findList(hqlInfo);
			// 排序调整（前一百留给钉钉审批分类排序，原分类统一调整到100后）
			Map<String, SysCategoryMain> dingCategory = new HashMap<String, SysCategoryMain>();  //全局分类的钉钉分组map

			for (int i = 0; i < list.size(); i++) {
				SysCategoryMain category = list.get(i);
				if (category.getFdOrder() == null) {
					category.setFdOrder(ORDER);
				} else if (category.getFdOrder() < ORDER
						&& category.getFdOrder() >= 0) {
					category.setFdOrder(ORDER + category.getFdOrder());
				}
				if ("1".equals(category.getFdIsFromDing())) {
					// 钉钉分类
					dingCategory.put(category.getFdId(), category);
				}
				getSysCategoryMainService().update(category);
			}
			
			Map<String, String> dingXformCate = getAllCategoryByCorpId(corpId);  // dirId  -- templateId
			logger.debug("dingXformCate:"+dingXformCate);
			if(dingXformCate == null){
				logger.warn("钉钉分类为空！corpId:" + corpId);
				return;
			}
			// 钉钉分类排序
			for (int i = 0; i < dirs.size(); i++) {
				JSONObject dir = dirs.getJSONObject(i);
				String dirId = "";
				if (dir.containsKey("dirId")) {
					dirId = dir.getString("dirId");
				} else if (dir.containsKey("dir_id")) {
					dirId = dir.getString("dir_id");
				}
				if (StringUtil.isNull(dirId)) {
					logger.warn("dirId为空！" + dir);
					continue;
				}
				if (!dingXformCate.containsKey(dirId)) {
					logger.warn("thirdDingCategoryXform表没有该钉钉分类： dirId:"+dir.getString("dirId"));
					continue;
				}
				String categoryId = dingXformCate.get(dirId);
				if (StringUtil.isNotNull(categoryId)) {
					SysCategoryMain sysCategoryMain = (SysCategoryMain) getSysCategoryMainService()
							.findByPrimaryKey(categoryId, SysCategoryMain.class,
									true);
					if (sysCategoryMain == null) {
                        continue;
                    }
					sysCategoryMain.setFdOrder(i);
					getSysCategoryMainService().update(sysCategoryMain);
				}
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

	}

	// 删除分类
	@SuppressWarnings("unchecked")
	private boolean deleteDir(String dirId, String dirName, String corpId) {
		logger.warn("删除分类   dirId:" + dirId + "   dirName:" + dirName + "   corpId"
				+ corpId);
		try {
			// 先将分类下的模板移到其他分类下，如果没有其他分类则建一个其他分类
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setWhereBlock(
					"thirdDingCategoryXform.fdDirid=:fdDirid and fdCorpid=:fdCorpid");
			hqlInfo.setParameter("fdDirid", "other");
			hqlInfo.setParameter("fdCorpid", corpId);
			ThirdDingCategoryXform other_thirdDingCategoryXform = (ThirdDingCategoryXform) getThirdDingCategoryXformService()
					.findFirstOne(hqlInfo);
			if (other_thirdDingCategoryXform == null) {
				// 没有“其他分类”
				if (addOrUpdateDir("other", "其他", corpId)) {
					logger.debug("新建 ‘其他’分类成功");
					other_thirdDingCategoryXform = (ThirdDingCategoryXform) getThirdDingCategoryXformService()
							.findFirstOne(hqlInfo);
				}
			}
			if (other_thirdDingCategoryXform == null) {
				logger.warn("无法找到或者创建‘其他’分类，无法迁移模版");
				return false;
			}

			String tempId = other_thirdDingCategoryXform.getFdTemplateId();
			logger.debug("other_tempId:" + tempId + "  name:"
					+ other_thirdDingCategoryXform.getFdName());
			SysCategoryMain sysCategoryMain = (SysCategoryMain) getSysCategoryMainService()
					.findByPrimaryKey(tempId);

			String oldTempplateId = getCategoryIdByDirIdAndCorpId(dirId,
					corpId);
			if (StringUtil.isNotNull(oldTempplateId)) {

				Object kmReviewUtil = Class.forName(
						"com.landray.kmss.km.review.util.KmReviewUtil")
						.newInstance();
				Class clazz = kmReviewUtil.getClass();
				Method getXFormTemplateType = clazz.getMethod(
						"moveTemplate2OtherCategory", String.class,
						String.class);
				boolean move_result = (boolean) getXFormTemplateType.invoke(
						kmReviewUtil,
						oldTempplateId,
						other_thirdDingCategoryXform.getFdTemplateId());
				logger.debug("move_result:" + move_result);
				if (move_result) {
					// 删除分类
					getThirdDingCategoryXformService().delete(
							getThirdDingCategoryXformByDirIdAndCorpId(dirId,
									corpId));
					// 删除模板分类
					getSysCategoryMainService().delete(oldTempplateId);
				}

			} else {
				logger.warn(
						"根据dirId：" + dirId + "和corpId:" + corpId + "找到对应的分类信息");
			}

		} catch (Exception e) {
			logger.error("删除分类失败！" + e.getMessage(), e);
			return false;
		}
		return true;

	}

	private Map<String, String> getAllCategoryByCorpId(String corpId) {

		try {
			Map<String, String> rsMap = new HashMap<String, String>();
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setWhereBlock("fdCorpid=:fdCorpid");
			hqlInfo.setParameter("fdCorpid", corpId);
			List<ThirdDingCategoryXform> dirList = getThirdDingCategoryXformService()
					.findList(hqlInfo);
			if (dirList != null && dirList.size() > 0) {
				for (ThirdDingCategoryXform cate : dirList) {
					rsMap.put(cate.getFdDirid(), cate.getFdTemplateId());
				}
				return rsMap;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return null;
	}

	private String getDirNameByDirID(String dirId) {
		try {
			Map<String, String> rsMap = new HashMap<String, String>();
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setWhereBlock("fdDirid=:fdDirid");
			hqlInfo.setParameter("fdDirid", dirId);
			return (String) getThirdDingCategoryXformService()
					.findFirstOne(hqlInfo);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	private String getCategoryIdByDirIdAndCorpId(String dirId, String corpId) {
		try {
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setWhereBlock(
					"thirdDingCategoryXform.fdDirid=:fdDirid and fdCorpid=:fdCorpid");
			hqlInfo.setParameter("fdDirid", dirId);
			hqlInfo.setParameter("fdCorpid", corpId);
			return (String) getThirdDingCategoryXformService()
					.findFirstOne(hqlInfo);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	private ThirdDingCategoryXform getThirdDingCategoryXformByDirIdAndCorpId(
			String dirId, String corpId) {
		try {
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setWhereBlock(
					"thirdDingCategoryXform.fdDirid=:fdDirid and fdCorpid=:fdCorpid");
			hqlInfo.setParameter("fdDirid", dirId);
			hqlInfo.setParameter("fdCorpid", corpId);
			return (ThirdDingCategoryXform) getThirdDingCategoryXformService()
					.findFirstOne(hqlInfo);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	// 新增或者修改
	private boolean addOrUpdateDir(String dirId, String name, String corpId) {
		try {
			logger.debug(
					"dirId:" + dirId + "   dirName:" + name + "   corpId"
							+ corpId);

			if ("other".equals(dirId) && StringUtil.isNull(name)) {
				name = "其他";
			}

			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setWhereBlock(
					"thirdDingCategoryXform.fdDirid=:fdDirid and fdCorpid=:fdCorpid");
			hqlInfo.setParameter("fdDirid", dirId);
			hqlInfo.setParameter("fdCorpid", corpId);
			ThirdDingCategoryXform thirdDingCategoryXform = (ThirdDingCategoryXform) getThirdDingCategoryXformService()
					.findFirstOne(hqlInfo);
			if (thirdDingCategoryXform != null) {
				// 更新
				thirdDingCategoryXform.setFdName(name);
				thirdDingCategoryXform.setFdIsAvailable(true);
				thirdDingCategoryXform.setDocAlterTime(new Date());
				// 更新模板分类名称
				SysCategoryMain sysCategoryMain = (SysCategoryMain) getSysCategoryMainService()
						.findByPrimaryKey(thirdDingCategoryXform
								.getFdTemplateId());
				if (sysCategoryMain != null) {
					sysCategoryMain.setFdName(name);
					sysCategoryMain.setDocAlterTime(new Date());
					getSysCategoryMainService().update(sysCategoryMain);
				} else {
					// 分类被删除或者没有分类时，新建分类，并更新
					String areaId = getAreaIdByCorpId(corpId);
					logger.debug("areaId:" + areaId);
					String categoryId = createCategory(name, corpId, areaId);
					thirdDingCategoryXform.setFdTemplateId(categoryId);
				}
				getThirdDingCategoryXformService()
						.update(thirdDingCategoryXform);
			} else {
				// 添加分类
				String areaId = getAreaIdByCorpId(corpId);
				logger.debug("areaId:" + areaId);
				String categoryId = createCategory(name, corpId, areaId);
				logger.debug("categoryId:" + categoryId);
				thirdDingCategoryXform = new ThirdDingCategoryXform();
				thirdDingCategoryXform.setFdName(name);
				thirdDingCategoryXform.setFdDirid(dirId);
				thirdDingCategoryXform.setFdCorpid(corpId);
				thirdDingCategoryXform.setFdIsAvailable(true);
				thirdDingCategoryXform.setDocCreateTime(new Date());
				thirdDingCategoryXform.setFdTemplateId(categoryId);
				getThirdDingCategoryXformService()
						.add(thirdDingCategoryXform);
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return false;
		}
		return true;
	}

	private String getAreaIdByCorpId(String corpId) {
		// 只有isv环境才返回场所id
		try {
			String addAppKey = ResourceUtil
					.getKmssConfigString("kmss.ding.addAppKey");
			if (StringUtil.isNotNull(addAppKey)
					&& "true".equalsIgnoreCase(addAppKey)) {
				HQLInfo hqlInfo = new HQLInfo();
				hqlInfo.setWhereBlock("fdCorpid=:fdCorpid");
				hqlInfo.setParameter("fdCorpid", corpId);
				List<ThirdDingCategoryXform> dirList = getThirdDingCategoryXformService()
						.findList(hqlInfo);
				Map<String, Integer> areaMap = new HashMap<String, Integer>();
				for (int i = 0; i < dirList.size(); i++) {
					ThirdDingCategoryXform categoryXform = dirList.get(i);
					String tempId = categoryXform.getFdTemplateId();
					SysCategoryMain sysCategoryMain = (SysCategoryMain) getSysCategoryMainService()
							.findByPrimaryKey(tempId);
					if (sysCategoryMain == null) {
						logger.error("钉钉的分组  " + categoryXform.getFdName()
								+ "  id:" + categoryXform.getFdId()
								+ "   对应的分类找不到");
					} else {
						SysAuthArea area = sysCategoryMain.getAuthArea();
						if (area == null) {
							logger.warn("isv模式下钉钉同步的分类  "
									+ sysCategoryMain.getFdName() + "  的场所为空");
						} else {
							if (areaMap.containsKey(area.getFdId())) {
								areaMap.put(area.getFdId(),
										areaMap.get(area.getFdId()) + 1);
							} else {
								areaMap.put(area.getFdId(), 1);
							}

						}
					}
				}

				logger.debug("areaMap:" + areaMap + "  size:" + areaMap.size());
				if (areaMap.isEmpty()) {
					logger.error("corpId：" + corpId + "  对应的场所为空");
					return null;
				}
				if (areaMap.size() > 1) {
					logger.error("corpId：" + corpId + "  对应的场所有"
							+ areaMap.size() + " 个，请检查");
				}
				String max_area_id = null;
				int max = 0;
				for (String areaId : areaMap.keySet()) {
					if (areaMap.get(areaId) >= max) {
						max = areaMap.get(areaId);
						max_area_id = areaId;
					}
				}
				logger.debug(" max_area_id：" + max_area_id + "  数量：" + max);
				return max_area_id;

			} else {
				logger.debug("非isv返回场所为null");
				return null;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	private String createCategory(String name, String corpId, String areaId) {

		try {
			SysCategoryMain sysCategoryMain = new SysCategoryMain();
			sysCategoryMain.setFdName(name);
			sysCategoryMain.setFdDesc("来自钉钉分类   corpId:" + corpId);
			sysCategoryMain.setFdOrder(0);
			sysCategoryMain.setDocCreateTime(new Date());
			sysCategoryMain.setFdModelName(
					"com.landray.kmss.km.review.model.KmReviewTemplate");
			sysCategoryMain.setFdIsinheritUser(true);
			sysCategoryMain.setFdIsinheritMaintainer(true);
			sysCategoryMain.setAuthReaderFlag(false);
			sysCategoryMain.setFdIsFromDing("1");
			if (StringUtil.isNotNull(areaId)) {
				ISysAuthAreaService authAreaService = (ISysAuthAreaService) SpringBeanUtil
						.getBean("sysAuthAreaService");
				SysAuthArea authArea = (SysAuthArea) authAreaService
						.findByPrimaryKey(areaId);
				sysCategoryMain.setAuthArea(authArea);
			}
			String id = getSysCategoryMainService()
					.add(sysCategoryMain);
			logger.debug("id:" + id);
			return id;
		} catch (Exception e) {
			logger.error("添加分类失败！" + e.getMessage(), e);
		}
		return null;
	}

	public static void main(String[] args) {

		JSONObject input = new JSONObject();
		input.put("eventType", "add_dir");
		input.put("corpId", "ding35a7fd308d38a9ee35c2f4657eb6378f");

		JSONObject changeData_item = new JSONObject();
		changeData_item.put("dir_id", "test123");
		changeData_item.put("dir_name", "测试回调");
		JSONArray changeData = new JSONArray();
		changeData.add(changeData_item);
		input.put("changeData", changeData);

		JSONArray ja = new JSONArray();
		JSONObject dir = new JSONObject();
		dir.put("dir_id", "test123");
		dir.put("dir_name", "测试回调");
		ja.add(dir);
		dir = new JSONObject();
		dir.put("dir_id", "other");
		dir.put("dir_name", "其他");
		ja.add(dir);
		input.put("dirs", ja);

		try {
			(new SynDingDirService()).dingDirCallBack(input);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
}
