package com.landray.kmss.km.imeeting.handover;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.constant.SysOrgConstant;
import com.landray.kmss.km.imeeting.model.KmImeetingRes;
import com.landray.kmss.km.imeeting.service.IKmImeetingResService;
import com.landray.kmss.sys.handover.interfaces.config.HandoverExecuteContext;
import com.landray.kmss.sys.handover.support.config.item.AbstractItemHandler;
import com.landray.kmss.sys.handover.support.config.item.ItemDetailPage;
import com.landray.kmss.sys.handover.support.config.item.ItemDetailPage.ItemDetail;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.sunbor.web.tag.Page;

/**
 * 工作交接处理类
 * 
 * @author 潘永辉 2019年2月28日
 *
 */
public class KmImeetingResItemHandler extends AbstractItemHandler {

	private IKmImeetingResService kmImeetingResService;

	public IKmImeetingResService getKmImeetingResService() {
		if (kmImeetingResService == null) {
			kmImeetingResService = (IKmImeetingResService) SpringBeanUtil.getBean("kmImeetingResService");
		}
		return kmImeetingResService;
	}

	public KmImeetingResItemHandler(String fdAttribute) {
		super(fdAttribute);
	}
	
	@Override
	protected boolean isIgnore(HandoverExecuteContext context, IBaseModel model) {
		// 如果交接的是会议室保管员，接收者只能是人员
		// 交接的属性
		String property = context.getItem();
		// 接收人
		SysOrgElement toOrg = context.getTo();
		if ("docKeeper".equals(property)) {
			// 如果有接收人，但是接收人不是人员，此次交接忽略
			if (toOrg != null && !toOrg.getFdOrgType().equals(SysOrgConstant.ORG_TYPE_PERSON)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public String getSubject(IBaseModel model) throws Exception {
		return ((KmImeetingRes) model).getFdName();
	}

	@Override
	public ItemDetailPage detail(HQLInfo hqlInfo, String fromOrgId,
			String toOrgId, String moduleName, String item, RequestContext requestContext) throws Exception {
		// 构建查询语句
		StringBuffer whereBlock = new StringBuffer();
		hqlInfo.setModelName(moduleName);
		whereBlock.append(item).append(".fdId = :userId ");
		hqlInfo.setParameter("userId", fromOrgId);
	    String titleSearchText = requestContext.getParameter("q.docSubject"); // 前端搜索框输入的模糊搜索关键字
		if(StringUtil.isNotNull(titleSearchText)){
			whereBlock.append("and fdName like :titleSearchText ");
			hqlInfo.setParameter("titleSearchText", "%"+titleSearchText.trim()+"%");
		}
		hqlInfo.setWhereBlock(whereBlock.toString());
		// 查询明细列表
		Page page = getKmImeetingResService().findPage(hqlInfo);
		// 构建一个事项交接的专属列表
		ItemDetailPage detailPage = new ItemDetailPage(page);
		// 构建需要在明细列表中显示的属性
		for (Object obj : page.getList()) {
			KmImeetingRes doc = (KmImeetingRes) obj;
			// 必须：增加明细数据（明细数据基本的属性有：ID，标题，url）
			ItemDetail itemDetail = detailPage.addDetail(doc.getFdId(),
					doc.getFdName(), "/km/imeeting/km_imeeting_res/kmImeetingRes.do?method=view&fdId=" + doc.getFdId());

			// 可选：以下增加的信息只会在明细列表中显示，不参与后续的交接逻辑
			// 地点楼层
			itemDetail.addItem(
					ResourceUtil.getString("km-imeeting:kmImeetingRes.fdAddressFloor"),
					doc.getFdAddressFloor());
			// 保管员
			itemDetail.addItem(
					ResourceUtil.getString("km-imeeting:kmImeetingRes.docKeeper"),
					doc.getDocKeeper().getFdName());
			// 类别
			itemDetail.addItem(
					ResourceUtil.getString("km-imeeting:kmImeetingRes.docCategory"),
					doc.getDocCategory().getFdName());
			// 容纳人数
			itemDetail.addItem(
					ResourceUtil.getString("km-imeeting:kmImeetingRes.fdSeats"),
					doc.getFdSeats());
			// 创建时间
			itemDetail.addItem(ResourceUtil.getString("model.fdCreateTime"),
					DateUtil.convertDateToString(doc.getDocCreateTime(),
							DateUtil.PATTERN_DATE));
		}
		return detailPage;
	}

}
