package com.landray.kmss.km.imeeting.handover;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.km.imeeting.model.KmImeetingMain;
import com.landray.kmss.km.imeeting.service.IKmImeetingMainService;
import com.landray.kmss.sys.handover.interfaces.config.HandoverSearchContext;
import com.landray.kmss.sys.handover.support.config.item.AbstractItemHandler;
import com.landray.kmss.sys.handover.support.config.item.ItemDetailPage;
import com.landray.kmss.sys.handover.support.config.item.ItemDetailPage.ItemDetail;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.RecurrenceUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.sunbor.web.tag.Page;

public class KmImeetingMainItemHandler extends AbstractItemHandler {

	private IKmImeetingMainService kmImeetingMainService;

	public IKmImeetingMainService getKmImeetingMainService() {
		if (kmImeetingMainService == null) {
			kmImeetingMainService = (IKmImeetingMainService) SpringBeanUtil
					.getBean("kmImeetingMainService");
		}
		return kmImeetingMainService;
	}

	public KmImeetingMainItemHandler(String fdAttribute) {
		super(fdAttribute);
		// 往父类传入本模块的Service，如果不传入Service，父类将取baseService
		super.setBaseService(getKmImeetingMainService());
	}
	
	@Override
    public List<String> getHandoverIds(String fromOrgId, String toOrgId,
                                       String moduleName, String item) throws Exception {
		// 构建查询语句
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setSelectBlock("fdId");
		hqlInfo.setModelName(moduleName);
		String whereBlock = hqlInfo.getWhereBlock();
		whereBlock = StringUtil.linkString(whereBlock, " and ",
				" kmImeetingMain.fdRecurrenceStr is not null ");
		whereBlock = StringUtil.linkString(whereBlock, " and ",
				" kmImeetingMain.fdFinishDate>:nowDate ");
		whereBlock = StringUtil.linkString(whereBlock, " and ",
				" kmImeetingMain.docStatus not in ('10','00','41') ");
		whereBlock = StringUtil.linkString(whereBlock, " and ",
				item + ".fdId = :userId");
		hqlInfo.setParameter("nowDate", new Date());
		hqlInfo.setParameter("userId", fromOrgId);
		hqlInfo.setWhereBlock(whereBlock);
		// 查询文档ID
		return getBaseService().findList(hqlInfo);
	}

	/**
	 * 查询交接数量
	 */
	@Override
	public void search(HandoverSearchContext context) throws Exception {
		// 获取当前查询的交接属性（这里的属性是在SysTaskProvider定义的）
		String property = getFdAttribute();
		// 获取当前查询的交接modelName
		String moduleName = context.getModule();
		// 构建查询语句
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setGetCount(true);
		hqlInfo.setGettingCount(true);
		hqlInfo.setModelName(moduleName);
		String whereBlock = hqlInfo.getWhereBlock();
		whereBlock = StringUtil.linkString(whereBlock, " and ",
				" kmImeetingMain.fdRecurrenceStr is not null ");
		whereBlock = StringUtil.linkString(whereBlock, " and ",
				" kmImeetingMain.fdFinishDate>:nowDate ");
		whereBlock = StringUtil.linkString(whereBlock, " and ",
				" kmImeetingMain.docStatus not in ('10','00','41') ");
		whereBlock = StringUtil.linkString(whereBlock, " and ",
				property + ".fdId = :userId");
		hqlInfo.setParameter("nowDate", new Date());
		hqlInfo.setParameter("userId", context.getHandoverOrg().getFdId());
		hqlInfo.setWhereBlock(whereBlock);
		List<Long> list = getBaseService().findList(hqlInfo);
		// 返回查询到的记录数
		context.setTotal(Long.valueOf(list.get(0).toString()));
	}

	@Override
	public ItemDetailPage detail(HQLInfo hqlInfo, String fromOrgId,
			String toOrgId, String moduleName, String item,
			RequestContext requestContext) throws Exception {
		// 构建查询语句
		hqlInfo.setModelName(moduleName);
		String whereBlock = hqlInfo.getWhereBlock();
		whereBlock = StringUtil.linkString(whereBlock, " and ",
				" kmImeetingMain.fdRecurrenceStr is not null ");
		whereBlock = StringUtil.linkString(whereBlock, " and ",
				" kmImeetingMain.fdFinishDate>:nowDate ");
		whereBlock = StringUtil.linkString(whereBlock, " and ",
				" kmImeetingMain.docStatus not in ('10','00','41') ");
		whereBlock = StringUtil.linkString(whereBlock, " and ",
				item + ".fdId = :userId");
		hqlInfo.setParameter("nowDate", new Date());
		hqlInfo.setParameter("userId", fromOrgId);
		hqlInfo.setWhereBlock(whereBlock);
		// 构建前端筛选器需要拼装的通用过滤HQL(例如：通过文档标题进行搜索)
		super.buildDetailSearchFilterWhereBlock(hqlInfo, moduleName,
				requestContext);
		// 查询明细列表
		Page page = getKmImeetingMainService().findPage(hqlInfo);
		// 构建一个事项交接的专属列表
		ItemDetailPage detailPage = new ItemDetailPage(page);
		// 构建需要在明细列表中显示的属性
		for (Object obj : page.getList()) {
			KmImeetingMain main = (KmImeetingMain) obj;
			// 必须：增加明细数据（明细数据基本的属性有：ID，标题，url）
			ItemDetail itemDetail = detailPage.addDetail(main.getFdId(),
					main.getFdName(),
					"/km/imeeting/km_imeeting_main/kmImeetingMain.do?method=view&fdId="
							+ main.getFdId());

			// 可选：以下增加的信息只会在明细列表中显示，不参与后续的交接逻辑
			// 发起人
			SysOrgPerson docCreator = main.getDocCreator();
			itemDetail.addItem(
					ResourceUtil.getString(
							"km-imeeting:kmImeetingMain.docCreator"),
					docCreator.getFdName());
			// 开始时间
			itemDetail.addItem(
					ResourceUtil.getString(
							"km-imeeting:kmImeetingMain.fdHoldDate"),
					DateUtil.convertDateToString(main.getFdHoldDate(),
							DateUtil.PATTERN_DATETIME));
			// 结束时间
			itemDetail.addItem(
					ResourceUtil.getString(
							"km-imeeting:kmImeetingMain.fdFinishDate"),
					DateUtil.convertDateToString(main.getFdFinishDate(),
							DateUtil.PATTERN_DATETIME));

			// 重复信息
			Map<String, String> infos = RecurrenceUtil
					.getRepeatInfo(main.getFdRecurrenceStr(),
							main.getFdHoldDate());
			StringBuilder sb = new StringBuilder();
			if (StringUtil.isNotNull(infos.get("INTERVAL"))) {
				sb.append(infos.get("INTERVAL")).append(" ");
			}
			if (StringUtil.isNotNull(infos.get("BYDAY"))) {
				sb.append(infos.get("BYDAY")).append(" ");
			}
			if (StringUtil.isNotNull(infos.get("COUNT"))) {
				sb.append(infos.get("COUNT"));
			} else if (StringUtil.isNotNull(infos.get("UNTIL"))) {
				sb.append(infos.get("UNTIL"));
			}
			itemDetail.addItem(
					ResourceUtil.getString(
							"km-imeeting:kmImeetingMain.fdRepeatInfo"),
					sb.toString());
		}
		return detailPage;
	}

}
