package com.landray.kmss.sys.evaluation.rest.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.landray.kmss.common.rest.controller.BaseController;
import com.landray.kmss.common.rest.util.ControllerHelper;
import com.landray.kmss.common.rest.convertor.PageVOConvertorUtil;
import com.landray.kmss.common.dto.HttpRequestParameterWrapper;
import com.landray.kmss.common.dto.PageVO;
import com.landray.kmss.common.dto.PageVO.SigleFild;
import com.landray.kmss.common.dto.QueryRequest;
import com.landray.kmss.sys.evaluation.actions.SysEvaluationReplyAction;
import com.landray.kmss.sys.evaluation.forms.SysEvaluationReplyForm;
import com.landray.kmss.sys.evaluation.model.SysEvaluationReply;
import com.landray.kmss.sys.person.interfaces.PersonInfoServiceGetter;
import com.landray.kmss.sys.praise.service.ISysPraiseMainService;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.ModelUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.web.RestResponse;
import com.landray.kmss.web.action.ActionMapping;
import com.landray.kmss.web.config.ActionConfig;
import com.sunbor.web.tag.Page;

@Controller
@RequestMapping(value = "/data/sys-evaluation/sysEvaluationReply", method = RequestMethod.POST)
public class SysEvaluationReplyController extends BaseController {

	private final Log log = LogFactory
			.getLog(SysEvaluationReplyController.class);

	private final SysEvaluationReplyAction action = new SysEvaluationReplyAction();

    /**
     * 新建暂存/提交
     */
    @ResponseBody
    @RequestMapping("save")
	public RestResponse<?> save(@RequestBody Map<String, Object> vo,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HttpRequestParameterWrapper wrapper = ControllerHelper
				.buildRequestParameterWrapper(request, vo);
		action.save(new ActionMapping(new ActionConfig()),
				new SysEvaluationReplyForm(), wrapper,
				response);
		return result(wrapper, request.getAttribute("lui-source"));
    }

    /**
     * 获取回复列表
     */
    @ResponseBody
    @RequestMapping("listReplyInfo")
	public RestResponse<?> listReplyInfo(@RequestBody QueryRequest query,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
        HttpRequestParameterWrapper wrapper = ControllerHelper
				.buildRequestParameterWrapper(request, query);
		action.findReplyInfo(emptyMapping, null, wrapper, response);
		Page queryPage = (Page) wrapper.getAttribute("queryPage");
		if (queryPage == null) {
			return result(wrapper);
		}
		PageVO pageVO = convert(queryPage);
		return result(wrapper, pageVO);
    }

	@SuppressWarnings("unchecked")
	private PageVO convert(Page queryPage) {
		PageVO pageVO = new PageVO();
		List<String> fields = ArrayUtil
				.asList(new String[] { "fdId", "replyContent", "fdModelName",
						"replyerId", "parentReplyerName",
						"parentReplyerId", "replyerName", "replyTime",
						"imgUrl", "currentUserId", "docPraiseCount",
						"canDelete", "isPraise" });
		// 表头信息
		Set<PageVO.ColumnInfo> columnInfoSet = PageVOConvertorUtil
				.buildColumnInfos(fields);
		pageVO.getColumns().addAll(columnInfoSet);
		// 行数据信息
		List<List<SigleFild>> allRowDatas = buildAllRowDatas(
				queryPage.getList());
		pageVO.setDatas(allRowDatas);

		// 分页信息
		pageVO.setPaging(queryPage.getPageno(), queryPage.getRowsize(),
				queryPage.getTotalrows());
		return pageVO;
	}

	private List<List<SigleFild>>
			buildAllRowDatas(List<Object> results) {
		List<List<SigleFild>> replys = new ArrayList<List<SigleFild>>();
		for (int i = 0; i < results.size(); i++) {
			SysEvaluationReply sysEvaluationReply = (SysEvaluationReply) results
					.get(i);
			List<PageVO.SigleFild> reply = new ArrayList<>();
			String fdId = sysEvaluationReply.getFdId();
			// 是否可以删除评论
			boolean canDelete = canDelete(sysEvaluationReply);
			reply.add(new PageVO.SigleFild("fdId",
					fdId));
			reply.add(new PageVO.SigleFild("fdModelName",
					ModelUtil.getModelClassName(sysEvaluationReply)));
			String replyerId = sysEvaluationReply.getFdReplyer().getFdId();
			reply.add(new PageVO.SigleFild("replyerId",
					replyerId));
			int praiseCount = 0;
			Integer docPraiseCount = sysEvaluationReply.getDocPraiseCount();
			if (docPraiseCount != null) {
				praiseCount = docPraiseCount;
			}
			reply.add(new PageVO.SigleFild("docPraiseCount",
					praiseCount));
			reply.add(new PageVO.SigleFild("replyContent",
					sysEvaluationReply.getDocContent()));
			reply.add(new PageVO.SigleFild("canDelete",
					canDelete));
			boolean isPraise = checkPraiseById(fdId,
					ModelUtil.getModelClassName(sysEvaluationReply));
			reply.add(new PageVO.SigleFild("isPraise",
					isPraise));
			// 被回复者
			if (sysEvaluationReply.getFdParentReplyer() != null) {
				String parentReplyerName = sysEvaluationReply
						.getFdParentReplyer()
						.getFdName();
				String parentReplyerId = sysEvaluationReply.getFdParentReplyer()
						.getFdId();
				reply.add(new PageVO.SigleFild("parentReplyerName",
						parentReplyerName));
				reply.add(new PageVO.SigleFild("parentReplyerId",
						parentReplyerId));
			} else {
				reply.add(new PageVO.SigleFild("parentReplyerName",
						""));
			}
			reply.add(new PageVO.SigleFild("replyerName",
					sysEvaluationReply.getFdReplyer().getFdName()));
			reply.add(new PageVO.SigleFild("replyTime",
					sysEvaluationReply.getFdReplyTime()));
			reply.add(new PageVO.SigleFild("currentUserId",
					UserUtil.getUser().getFdId()));
			String headImgPath = PersonInfoServiceGetter.getPersonHeadimageUrl(
						replyerId, "m");
			reply.add(new PageVO.SigleFild("imgUrl",
					headImgPath));
			replys.add(reply);
		}
		return replys;
	}

	/**
	 * 判断当前用户是否能删除此回复
	 * 
	 * @param evalReply
	 * @return
	 */
	private boolean canDelete(SysEvaluationReply evalReply) {
		StringBuffer sb = new StringBuffer(
				"/sys/evaluation/sys_evaluation_main/sysEvaluationReply.do?method=delete");
		String fdId = evalReply.getFdId();
		sb.append("&fdModelName=");
		sb.append(ModelUtil.getModelClassName(evalReply));
		sb.append("fdModelId=");
		sb.append(fdId);
		// 是否可以删除回复
		boolean canDelete = UserUtil.checkAuthentication(sb.toString(),
				"get");
		return canDelete;
	}

	/**
	 * 判断当前用户是否有点赞此回复
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
}
