package com.landray.kmss.sys.attend.service.spring;

import com.landray.kmss.sys.attend.model.SysAttendCategory;
import com.landray.kmss.sys.attend.model.SysAttendCategoryTime;
import com.landray.kmss.sys.attend.service.ISysAttendCategoryJobService;
import com.landray.kmss.sys.attend.service.ISysAttendCategoryService;
import com.landray.kmss.sys.attend.util.AttendUtil;
import com.landray.kmss.sys.quartz.interfaces.SysQuartzJobContext;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.StringUtil;
import net.sf.json.JSONObject;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;


/**
 * 考勤组/签到组 状态维护
 * 
 * @author linxiuxian
 *
 */
public class SysAttendCategoryJobServiceImp
		implements ISysAttendCategoryJobService {
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SysAttendCategoryJobServiceImp.class);

	private ISysAttendCategoryService sysAttendCategoryService;

	public void setSysAttendCategoryService(
			ISysAttendCategoryService sysAttendCategoryService) {
		this.sysAttendCategoryService = sysAttendCategoryService;
	}

	@Override
	public void execute(SysQuartzJobContext jobContext) throws Exception {
		String whereBlock = "(sysAttendCategory.fdPeriodType=2 or sysAttendCategory.fdShiftType=2) and sysAttendCategory.fdStatus=1";
		List<SysAttendCategory> list = sysAttendCategoryService
				.findList(whereBlock, null);
		if (list.isEmpty()) {
			return;
		}
		List<String> cateIds = new ArrayList<String>();
		for (SysAttendCategory cate : list) {
			List<SysAttendCategoryTime> times = cate.getFdTimes();
			Date fdStartTime = cate.getFdStartTime();
			Date fdEndTime = cate.getFdEndTime();


			Collections.sort(times, new Comparator<SysAttendCategoryTime>() {
				@Override
				public int compare(SysAttendCategoryTime o1,
						SysAttendCategoryTime o2) {
					if (o1.getFdTime() == null) {
						return -1;
					}
					if (o2.getFdTime() == null) {
						return 1;
					}
					return o1.getFdTime().compareTo(o2.getFdTime());
				}
			});

			if (times.size() == 0) {
				cateIds.add(cate.getFdId());
				break;
			}
			if (times.size() >= 1) {
				SysAttendCategoryTime time = times.get(times.size() - 1);
				if (time.getFdTime() == null) {
					continue;
				}
				long fdTime = AttendUtil.getDate(time.getFdTime(), 0).getTime();
				if (fdTime >= DateUtil.getDate(1)
						.getTime()) {
					break;
				}
				if (fdTime == DateUtil.getDate(0)
						.getTime()) {
					// 当天
					if (fdStartTime == null || fdEndTime == null) {
						break;
					}
					Date date = new Date();
					int startTime = fdStartTime.getHours() * 60
							+ fdStartTime.getMinutes();
					int endTime = fdEndTime.getHours() * 60
							+ fdEndTime.getMinutes();
					int nowTime = date.getHours() * 60 + date.getMinutes();
					if (nowTime > endTime) {
						cateIds.add(cate.getFdId());
					}
				} else {
					cateIds.add(cate.getFdId());
				}
			}

			// 更新状态
			for (String id : cateIds) {
				SysAttendCategory category = (SysAttendCategory) sysAttendCategoryService
						.findByPrimaryKey(id);
				category.setFdStatus(2);
				sysAttendCategoryService.update(category);
			}
		}
	}

	@Override
	public void updateStatus(SysQuartzJobContext jobContext) throws Exception {
		JSONObject param = JSONObject.fromObject(jobContext.getParameter());
		if (!param.containsKey("fdCategoryId")) {
			return;
		}
		String fdCategoryId = param.getString("fdCategoryId");
		if (StringUtil.isNull(fdCategoryId)) {
			return;
		}
		String fdOperType = null;
		if (param.containsKey("fdOperType")) {
			fdOperType = param.getString("fdOperType");
		}
		if (StringUtil.isNull(fdOperType)) {
			sysAttendCategoryService.updateCategoryOver(fdCategoryId,"0");
			return;
		}
		if (StringUtil.isNotNull(fdOperType)
				&& "fdEffectTime".equals(fdOperType)) {
			sysAttendCategoryService.updateStatus(fdCategoryId, 1);
		}

	}

}
