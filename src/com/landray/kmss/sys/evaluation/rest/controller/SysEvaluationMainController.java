package com.landray.kmss.sys.evaluation.rest.controller;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.dto.HttpRequestParameterWrapper;
import com.landray.kmss.common.dto.PageVO;
import com.landray.kmss.common.dto.PageVO.SigleFild;
import com.landray.kmss.common.dto.QueryRequest;
import com.landray.kmss.common.rest.controller.BaseController;
import com.landray.kmss.common.rest.convertor.PageVOConvertorUtil;
import com.landray.kmss.common.rest.util.ControllerHelper;
import com.landray.kmss.sys.evaluation.actions.SysEvaluationMainAction;
import com.landray.kmss.sys.evaluation.forms.SysEvaluationMainForm;
import com.landray.kmss.sys.evaluation.model.SysEvaluationMain;
import com.landray.kmss.sys.evaluation.service.ISysEvaluationMainService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.person.interfaces.PersonInfoServiceGetter;
import com.landray.kmss.sys.praise.service.ISysPraiseMainService;
import com.landray.kmss.util.*;
import com.landray.kmss.web.RestResponse;
import com.landray.kmss.web.action.ActionMapping;
import com.landray.kmss.web.config.ActionConfig;
import com.sunbor.web.tag.Page;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Controller
@RequestMapping(value = "/data/sys-evaluation/sysEvaluationMain", method = RequestMethod.POST)
public class SysEvaluationMainController extends BaseController {

	private final Log log = LogFactory
			.getLog(SysEvaluationMainController.class);

	private final SysEvaluationMainAction action = new SysEvaluationMainAction();

	/**
	 * 删除
	 */
	@ResponseBody
	@RequestMapping("delete")
	public RestResponse<String> delete(@RequestBody Map<String, Object> vo,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HttpRequestParameterWrapper wrapper = ControllerHelper
				.buildRequestParameterWrapper(request, vo);
		wrapper.setMethod("GET");
		action.delete(new ActionMapping(new ActionConfig()), null, wrapper,
				response);
		return result(wrapper);
	}

    /**
     * 新建暂存/提交
     */
    @ResponseBody
    @RequestMapping("save")
	public RestResponse<?> save(@RequestBody SysEvaluationMainForm vo,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HttpRequestParameterWrapper wrapper = ControllerHelper
				.buildRequestParameterWrapper(request, vo);
		String fdModelId = vo.getFdModelId();
		String fdModelName = vo.getFdModelName();
		wrapper.putParameter("fdModelId", fdModelId);
		wrapper.putParameter("fdModelName", fdModelName);
		ControllerHelper.VOConvertPostHandler(vo);
		action.save(new ActionMapping(new ActionConfig()), vo, wrapper,
				response);
		return result(wrapper, wrapper.getAttribute("lui-source"));
    }

    /**
     * 获取点评平均分数
     */
    @ResponseBody
    @RequestMapping("score")
    public RestResponse<String> score(@RequestBody Map<String, Object> vo, HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpRequestParameterWrapper wrapper = ControllerHelper
                .buildRequestParameterWrapper(request, vo);
        wrapper.setMethod("GET");
        action.score(new ActionMapping(new ActionConfig()), null, wrapper,
                response);
		String score = (String) wrapper.getAttribute("score");
		return result(wrapper, score);
    }

	/**
	 * 获取点评详细评分
	 */
	@ResponseBody
	@RequestMapping("dataScore")
	public RestResponse<Object> dataScore(@RequestBody Map<String, Object> vo, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpRequestParameterWrapper wrapper = ControllerHelper
				.buildRequestParameterWrapper(request, vo);
		wrapper.setMethod("GET");
		action.dataScore(new ActionMapping(new ActionConfig()), null, wrapper, response);
		return result(wrapper, ControllerHelper.standardizeResult(wrapper.getAttribute("lui-source")));
	}

    /**
     * 获取我的全部点评
     */
    @ResponseBody
    @RequestMapping("listMyEva")
    public RestResponse<?> listMyEva(@RequestBody QueryRequest query,
                                    HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        // 统一转parameter
        HttpRequestParameterWrapper requestWrapper = ControllerHelper
                .buildRequestParameterWrapper(request, query);

		// 我的评论和追加的评论
        action.listMyEva(new ActionMapping(new ActionConfig()), null,
                requestWrapper, response);
        Page queryPage = (Page) requestWrapper.getAttribute("queryPage");
        RestResponse<Object> checkActionResult = result(requestWrapper);
        if (!checkActionResult.isSuccess()) {
            return checkActionResult;
        }
		PageVO pageVO = convert(queryPage);
        return result(requestWrapper, pageVO);
    }


    /**
     * 获取评论列表（不含我的评论）
     */
    @ResponseBody
    @RequestMapping("list")
    public RestResponse<?> list(@RequestBody QueryRequest query,
                                HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        // 统一转parameter
        HttpRequestParameterWrapper requestWrapper = ControllerHelper
                .buildRequestParameterWrapper(request, query);

        // 调用实际业务Action
        action.list(new ActionMapping(new ActionConfig()), null,
                requestWrapper, response);
        Page queryPage = (Page) requestWrapper.getAttribute("queryPage");
        RestResponse<Object> checkActionResult = result(requestWrapper);
        if (!checkActionResult.isSuccess()) {
            return checkActionResult;
        }
		PageVO pageVO = convert(queryPage);
        return result(requestWrapper, pageVO);
    }

	private Map<String, List>
			findAdditionEvalsById(List<String> topEvalIds) {
		Map<String, List> additionEvals = new HashMap<String, List>();
		if (!ArrayUtil.isEmpty(topEvalIds)) {
			HQLInfo hqlInfo = new HQLInfo();
			String whereBlock = hqlInfo.getWhereBlock();
			if (StringUtil.isNull(whereBlock)) {
				whereBlock = "1=1 ";
			}
			ISysEvaluationMainService sysEvaluationMainService = (ISysEvaluationMainService) SpringBeanUtil
					.getBean("sysEvaluationMainService");
			whereBlock = StringUtil.linkString(whereBlock, " and ",
					" fdParentId in (:fdEvalId)");
			hqlInfo.setParameter("fdEvalId", topEvalIds);
			hqlInfo.setWhereBlock(whereBlock);
			try {
				List evals = sysEvaluationMainService
						.findList(hqlInfo);
				for (Object obj : evals) {
					SysEvaluationMain eval = (SysEvaluationMain) obj;
					String fdParentId = eval.getFdParentId();
					List<SysEvaluationMain> list = additionEvals
							.get(fdParentId);
					if (ArrayUtil.isEmpty(list)) {
						list = new ArrayList<SysEvaluationMain>();
					}
					list.add(eval);
					additionEvals.put(fdParentId, list);

				}
			} catch (Exception e) {
				log.error("无法获取追加评论,ids：" + topEvalIds, e);
			}

		}
		return additionEvals;
	}

	@SuppressWarnings("unchecked")
	private PageVO convert(Page queryPage) {
		PageVO pageVO = new PageVO();
		List<String> fields = ArrayUtil
				.asList(new String[] { "fdId", "fdEvaluationContent",
						"fdEvaluationScore", "fdEvaluatorName",
						"fdEvaluationTime", "fdReplyCount", "docPraiseCount",
						"imgUrl", "top", "isPraise", "additionEvals" });
		// 表头信息
		Set<PageVO.ColumnInfo> columnInfoSet = PageVOConvertorUtil
				.buildColumnInfos(fields);
		pageVO.getColumns().addAll(columnInfoSet);
		// 行数据信息
		List<List<SigleFild>> allRowDatas = buildAllRowDatas(
				queryPage.getList(), true);
		pageVO.setDatas(allRowDatas);

		// 分页信息
		pageVO.setPaging(queryPage.getPageno(), queryPage.getRowsize(),
				queryPage.getTotalrows());
		return pageVO;
	}

	@SuppressWarnings("rawtypes")
	private List<List<SigleFild>>
			buildAllRowDatas(List<Object> results, boolean isTop) {
		if (ArrayUtil.isEmpty(results)) {
			return new ArrayList<List<SigleFild>>();
		}
		// 评论id跟评论映射
		Map<String, List<SigleFild>> evalMap = new HashMap<String, List<SigleFild>>(
				results.size());
		// 最终返回的评论
		List<List<SigleFild>> rtnEvals = new ArrayList<List<SigleFild>>(
				results.size());
		List<String> ids = new ArrayList<String>();

		for (int i = 0; i < results.size(); i++) {
			SysEvaluationMain sysEvaluationMain = (SysEvaluationMain) results
					.get(i);
			String fdId = sysEvaluationMain.getFdId();
			ids.add(fdId);
			StringBuffer sb = new StringBuffer(
					"/sys/evaluation/sys_evaluation_main/sysEvaluationMain.do?method=delete");
			sb.append("&fdModelName=");
			sb.append(ModelUtil.getModelClassName(sysEvaluationMain));
			sb.append("fdModelId=");
			sb.append(fdId);
			// 是否可以删除评论
			boolean canDelete = UserUtil.checkAuthentication(sb.toString(),
					"get");
			sb.setLength(0);
			List<SigleFild> rowData = new ArrayList<>();
			rowData.add(new SigleFild("fdId",
					sysEvaluationMain.getFdId()));
			rowData.add(new SigleFild("fdModelName",
					ModelUtil.getModelClassName(sysEvaluationMain)));
			rowData.add(new SigleFild("canDelete",
					canDelete));
			rowData.add(new SigleFild("fdEvaluationContent",
					sysEvaluationMain.getFdEvaluationContent()));
			rowData.add(new SigleFild("fdEvaluationScore",
					sysEvaluationMain.getFdEvaluationScore()));
			SysOrgElement fdEvaluator = sysEvaluationMain.getFdEvaluator();
			String fdEvaluationName = "";
			if (fdEvaluator != null) {
				fdEvaluationName = fdEvaluator.getFdName();
			}
			rowData.add(new SigleFild("fdEvaluatorName",
					fdEvaluationName));
			rowData.add(new SigleFild("fdEvaluationTime",
					fdEvaluator.getFdCreateTime()));
			rowData.add(new SigleFild("fdReplyCount",
					sysEvaluationMain.getFdReplyCount()));
			rowData.add(new SigleFild("docPraiseCount",
					sysEvaluationMain.getDocPraiseCount()));
			boolean isPraise = checkPraiseById(fdId,
					ModelUtil.getModelClassName(sysEvaluationMain));
			rowData.add(new SigleFild("isPraise",
					isPraise));
			String headImgPath = "";
			if (fdEvaluator != null) {
				headImgPath = PersonInfoServiceGetter.getPersonHeadimageUrl(
						fdEvaluator.getFdId(), "m");
			}
			rowData.add(new SigleFild("imgUrl",
					headImgPath));
			evalMap.put(fdId, rowData);
			rtnEvals.add(rowData);
		}

		if (isTop) {
			Map<String, List> additionEvalsMap = findAdditionEvalsById(
					ids);
			// 将追加评论放在所属的评论下面
			for (Map.Entry<String, List<SigleFild>> topEvalEntry : evalMap
					.entrySet()) {
				String topEvalId = topEvalEntry.getKey();
				List<SigleFild> topEval = topEvalEntry.getValue();
				List list = additionEvalsMap.get(topEvalId);
				List<List<SigleFild>> additionEvals = buildAllRowDatas(
						list, false);
				topEval.add(new SigleFild("additionEvals",
						additionEvals));
			}
		}

		// 将附件信息放在评论下面
		JSONObject attrs = getAttrs(ids);
		if (attrs != null) {
			for (Object attrEntry : attrs.entrySet()) {
				Map.Entry entry = (Map.Entry) attrEntry;
				// 评论Id
				String evalId = (String) entry.getKey();
				JSONArray evalAttrs = (JSONArray) entry.getValue();
				List<SigleFild> rowData = evalMap.get(evalId);
				rowData.add(new SigleFild("attrs",
						evalAttrs));
			}
		}
		return rtnEvals;
	}

	/**
	 * 获取指定评论相关的附件信息
	 * 
	 * @param ids
	 * @return
	 */
	private JSONObject getAttrs(List<String> ids) {
		if (!ArrayUtil.isEmpty(ids)) {
			String[] idArr = new String[ids.size()];
			ids.toArray(idArr);
			ISysEvaluationMainService sysEvaluationMainService = (ISysEvaluationMainService) SpringBeanUtil
					.getBean("sysEvaluationMainService");
			try {
				JSONObject attObj = sysEvaluationMainService.getListAtt(idArr,
						SysEvaluationMain.class.getName());
				return attObj;
			} catch (Exception e) {
				log.error("无法获取附件信息, ids: " + ids, e);
			}
		}
		return null;
	}

	/**
	 * 判断当前用户是否有点赞此评论
	 * 
	 * @param fdModelId
	 * @param fdModelName
	 * @return
	 */
	private boolean checkPraiseById(String fdModelId, String fdModelName) {
		ISysPraiseMainService sysPraiseMainService = (ISysPraiseMainService) SpringBeanUtil
				.getBean("sysPraiseMainService");
		try {
			List praiseIds = sysPraiseMainService.checkPraisedByIds(
					UserUtil.getUser().getFdId(), fdModelId, fdModelName);
			if (ArrayUtil.isEmpty(praiseIds)) {
				return false;
			}
		} catch (Exception e) {
			log.error("无法判断是否有点赞", e);
		}
		return true;
	}

	/**
	 * 根据id获取评论
	 */
	@ResponseBody
	@RequestMapping("findEvalById")
	public RestResponse<?> findEvalById(@RequestBody Map<String, Object> vo,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String fdId = (String) vo.get("fdId");
		ISysEvaluationMainService sysEvaluationMainService = (ISysEvaluationMainService) SpringBeanUtil
				.getBean("sysEvaluationMainService");
		SysEvaluationMain evalMain = (SysEvaluationMain) sysEvaluationMainService
				.findByPrimaryKey(fdId,
				SysEvaluationMain.class.getName(), true);
		List results = new ArrayList();
		results.add(evalMain);
		List<List<SigleFild>> rowData = buildAllRowDatas(
				results, true);
		return result(request, rowData);
	}
}
