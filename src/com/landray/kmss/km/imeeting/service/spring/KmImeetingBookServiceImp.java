package com.landray.kmss.km.imeeting.service.spring;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.landray.kmss.constant.SysOrgConstant;
import org.apache.commons.beanutils.BeanUtils;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.component.locker.interfaces.IComponentLockService;
import com.landray.kmss.constant.SysAuthConstant;
import com.landray.kmss.constant.SysNotifyConstant;
import com.landray.kmss.km.imeeting.model.KmImeetingBook;
import com.landray.kmss.km.imeeting.model.KmImeetingRes;
import com.landray.kmss.km.imeeting.model.KmImeetingResLock;
import com.landray.kmss.km.imeeting.service.IKmImeetingBookService;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.sys.notify.interfaces.NotifyContext;
import com.landray.kmss.sys.notify.interfaces.NotifyReplace;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.util.CriteriaValue;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.IDGenerator;
import com.landray.kmss.util.RecurrenceUtil;
import com.landray.kmss.util.RecurrenceUtil.RecurrenceEndType;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;

/**
 * 会议室预约业务接口实现
 * 
 * @author 
 * @version 1.0 2014-07-21
 */
public class KmImeetingBookServiceImp extends BaseServiceImp
		implements IKmImeetingBookService {
	
	private ISysNotifyMainCoreService sysNotifyMainCoreService;

	public void setSysNotifyMainCoreService(
			ISysNotifyMainCoreService sysNotifyMainCoreService) {
		this.sysNotifyMainCoreService = sysNotifyMainCoreService;
	}

	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map getDataById(String bookId) throws Exception {
		KmImeetingBook kmImeetingBook = (KmImeetingBook) this.findByPrimaryKey(bookId, null, true);
		Map map = new HashMap();
		if(kmImeetingBook!=null){
			map.put("fdName", kmImeetingBook.getFdName());
			long fdHoldDuration = Math.round(kmImeetingBook.getFdHoldDuration());
			Double fdHoldDurationLast=(double) (1.0f * fdHoldDuration/1000/3600);
			map.put("fdHoldDuration",fdHoldDurationLast);
			map.put("fdHoldDate", kmImeetingBook.getFdHoldDate());
			map.put("fdFinishDate", kmImeetingBook.getFdFinishDate());
		}
		return map;
	}

	IComponentLockService componentLockService = null;

	private IComponentLockService getComponentLockService() {
		if (componentLockService == null) {
			componentLockService = (IComponentLockService) SpringBeanUtil.getBean("componentLockService");
		}
		return componentLockService;
	}

	@Override
	public String add(IBaseModel modelObj) throws Exception {
		KmImeetingBook bookModel = (KmImeetingBook) modelObj;
		String fdPlaceId = bookModel.getFdPlace() != null ? bookModel.getFdPlace().getFdId() : "";
		String fdId = "";
		KmImeetingResLock kmImeetingResLock = new KmImeetingResLock();
		try {
			if (StringUtil.isNotNull(fdPlaceId)) {
				getComponentLockService().tryLock(kmImeetingResLock, fdPlaceId);
			}
			addSend(bookModel);
			handleRecurrence(bookModel);
			fdId = super.add(modelObj);
			getComponentLockService().unLock(kmImeetingResLock);
		} catch (Exception e) {
			getComponentLockService().unLock(kmImeetingResLock);
			throw e;
		} finally {
			getComponentLockService().unLock(kmImeetingResLock);
		}
		return fdId;
	}

	private void addSend(KmImeetingBook bookModel) throws Exception {
		KmImeetingRes fdPlace = bookModel.getFdPlace();
		Boolean fdNeedExamFlag = fdPlace.getFdNeedExamFlag();
		if (fdNeedExamFlag != null && fdNeedExamFlag.booleanValue()) {
			send(bookModel, fdPlace);
			bookModel.setIsNotify(new Boolean(true));
			bookModel.setFdExamer(fdPlace.getDocKeeper());
		} else {
			bookModel.setFdHasExam(new Boolean(true));
			bookModel.setIsNotify(new Boolean(false));
		}
	}

	@Override
	public void update(IBaseModel modelObj) throws Exception {
		KmImeetingBook bookModel = (KmImeetingBook) modelObj;
		sysNotifyMainCoreService.getTodoProvider().remove(bookModel,
				"kmImeetingBook");
		bookModel.setFdHasExam(null);
		bookModel.setFdExamRemark(null);
		String fdPlaceId = bookModel.getFdPlace() != null ? bookModel.getFdPlace().getFdId() : "";
		KmImeetingResLock kmImeetingResLock = new KmImeetingResLock();
		try {
			if (StringUtil.isNotNull(fdPlaceId)) {
				getComponentLockService().tryLock(kmImeetingResLock, fdPlaceId);
			}
			addSend(bookModel);
			handleRecurrence(bookModel);
			super.update(modelObj);
			getComponentLockService().unLock(kmImeetingResLock);
		} catch (Exception e) {
			getComponentLockService().unLock(kmImeetingResLock);
			throw e;
		} finally {
			getComponentLockService().unLock(kmImeetingResLock);
		}
	}

	/**
	 * 周期性会议预定处理
	 */
	private void handleRecurrence(KmImeetingBook bookModel)
			throws ParseException {
		String recurrenceStr = bookModel.getFdRecurrenceStr();
		if (StringUtil.isNotNull(recurrenceStr)) {
			Date recurrenceLastStart = RecurrenceUtil.getLastedExecuteDate(
					recurrenceStr, bookModel.getFdHoldDate());
			if (recurrenceLastStart != null) {
				Long recurrenceLastEndLong = recurrenceLastStart.getTime()
						+ bookModel.getFdFinishDate().getTime()
						- bookModel.getFdHoldDate().getTime();
				bookModel.setFdRecurrenceLastStart(recurrenceLastStart);
				bookModel.setFdRecurrenceLastEnd(
						new Date(recurrenceLastEndLong));
			} else {
				bookModel.setFdRecurrenceLastStart(null);
				bookModel.setFdRecurrenceLastEnd(null);
			}
		} else {
			bookModel.setFdRecurrenceStr(null);
			bookModel.setFdRecurrenceLastStart(null);
			bookModel.setFdRecurrenceLastEnd(null);
		}
	}

	private void send(KmImeetingBook bookModel, KmImeetingRes fdPlace)
			throws Exception {
		List<SysOrgElement> targets = new ArrayList<SysOrgElement>();
		SysOrgElement keeper = fdPlace.getDocKeeper();
		if (keeper != null) {
            targets.add(keeper);
        }
		if (!targets.isEmpty()) {
			NotifyContext notifyContext = sysNotifyMainCoreService
					.getContext("km-imeeting:kmImeetingBook.exam.notify");
			notifyContext.setKey("kmImeetingBook");
			notifyContext.setFlag(SysNotifyConstant.NOTIFY_TODOTYPE_MANUAL);
			notifyContext.setNotifyType("todo");
			notifyContext.setNotifyTarget(targets);
			notifyContext.setDocCreator(
					UserUtil.getUser(bookModel.getDocCreator().getFdId()));
			NotifyReplace notifyReplace = new NotifyReplace();
			notifyReplace.addReplaceText(
					"km-imeeting:kmImeetingBook.docCreator",
					bookModel.getDocCreator().getFdName());
			notifyReplace.addReplaceText("km-imeeting:kmImeetingBook.fdPlace",
					fdPlace.getFdName());
			notifyReplace.addReplaceText("km-imeeting:kmImeetingBook.fdName",
					bookModel.getFdName());
			sysNotifyMainCoreService.sendNotify(bookModel, notifyContext,
					notifyReplace);
		}
	}

	@Override
	public void addExam(KmImeetingBook meetingBook) throws Exception {
		// 将待办置为已办
		// #152496 由于meetingBook.getFdExamer()取的会议室保管人可能是岗位或人员
		if(meetingBook.getFdExamer().getFdOrgType().equals(SysOrgConstant.ORG_TYPE_PERSON)){
			sysNotifyMainCoreService.getTodoProvider().removePerson(meetingBook,
				"kmImeetingBook",
				UserUtil.getUser(meetingBook.getFdExamer().getFdId()));
		}else {//如果保管人不是人员，取当前登录人
			sysNotifyMainCoreService.getTodoProvider().removePerson(meetingBook,
					"kmImeetingBook",
					UserUtil.getUser(UserUtil.getKMSSUser().getUserId()));
		}
		super.update(meetingBook);
		sendReply(meetingBook);
	}

	private void sendReply(KmImeetingBook meetingBook) throws Exception {
		List<SysOrgElement> targets = new ArrayList<SysOrgElement>();
		targets.add(meetingBook.getDocCreator());
		if (!targets.isEmpty()) {
			NotifyContext notifyContext = sysNotifyMainCoreService
					.getContext("km-imeeting:kmImeetingBook.examReply.notify");
			notifyContext.setKey("kmImeetingBook2");
			notifyContext.setFlag(SysNotifyConstant.NOTIFY_TODOTYPE_ONCE);
			notifyContext.setNotifyType("todo");
			notifyContext.setNotifyTarget(targets);
			// #152496 如果保管人是人员
			if(meetingBook.getFdExamer().getFdOrgType().equals(SysOrgConstant.ORG_TYPE_PERSON)){
				notifyContext.setDocCreator(
						UserUtil.getUser(meetingBook.getFdExamer().getFdId()));
			}else {//如果保管人不是人员（可能是岗位），取当前登录人为待阅的发送人
				notifyContext.setDocCreator(
						UserUtil.getUser(UserUtil.getKMSSUser().getUserId()));
			}
			NotifyReplace notifyReplace = new NotifyReplace();
			notifyReplace.addReplaceText("km-imeeting:kmImeetingRes.docKeeper",
					meetingBook.getFdPlace().getDocKeeper().getFdName());
			if (meetingBook.getFdHasExam()) {
				notifyReplace.addReplaceText("km-imeeting:kmImeetingBook.exam",
						ResourceUtil.getString("kmImeetingBook.exam.true",
								"km-imeeting"));
			} else {
				notifyReplace.addReplaceText("km-imeeting:kmImeetingBook.exam",
						ResourceUtil.getString("kmImeetingBook.exam.false",
								"km-imeeting"));
			}
			notifyReplace.addReplaceText("km-imeeting:kmImeetingBook.fdPlace",
					meetingBook.getFdPlace().getFdName());
			sysNotifyMainCoreService.sendNotify(meetingBook, notifyContext,
					notifyReplace);
		}
	}

	private HQLInfo buildBookHql(RequestContext request) {
		HQLInfo hql = new HQLInfo();
		String exceptBookId = request.getParameter("exceptBookId"); // 不包含的预约Id
		String placeId = request.getParameter("placeId");
		String showMe = request.getParameter("showMe"); // 是否只显示我创建的
		String hasExam = request.getParameter("hasExam"); // 是否只显示审核通过的(包括未审核的)
		if ("true".equals(showMe)) {
			hql.setWhereBlock(" kmImeetingBook.docCreator.fdId=:userid ");
			hql.setParameter("userid", UserUtil.getUser().getFdId());
		}
		if ("true".equals(hasExam)) {
			hql.setWhereBlock(StringUtil.linkString(hql.getWhereBlock(),
					" and ",
					" (kmImeetingBook.fdHasExam is null or kmImeetingBook.fdHasExam=:fdHasExam) "));
			hql.setParameter("fdHasExam", Boolean.TRUE);
		}
		if (StringUtil.isNotNull(exceptBookId)) {
			hql.setWhereBlock(StringUtil.linkString(hql.getWhereBlock(),
					" and ", " kmImeetingBook.fdId<>:exceptBookId  "));
			hql.setParameter("exceptBookId", exceptBookId);
		}
		if (StringUtil.isNotNull(placeId)) {
			hql.setWhereBlock(hql.getWhereBlock()
					+ " and kmImeetingBook.fdPlace.fdId = :placeId");
			hql.setParameter("placeId", placeId);
		}
		hql.setOrderBy(" kmImeetingBook.fdHoldDate ");
		return hql;
	}

	@Override
	public HQLInfo buildNormalBookHql(RequestContext requestContext)
			throws Exception {
		String format = ResourceUtil.getString("date.format.date");
		if (StringUtil.isNotNull(requestContext.getParameter("format"))) {
			format = requestContext.getParameter("format");
		}
		String fdStart = requestContext.getParameter("fdStart");// 开始时间
		String fdEnd = requestContext.getParameter("fdEnd");// 结束时间
		String viewType = requestContext.getParameter("viewType");
		Date startDateTime = null;
		if (StringUtil.isNotNull(fdStart)) {
			if (requestContext.isCloud()) {
                startDateTime = new Date(Long.parseLong(fdStart));
            } else {
                startDateTime = DateUtil.convertStringToDate(fdStart, format);
            }
		} else {
			if ("week".equals(viewType)) {
                startDateTime = DateUtil.getBeginDayOfWeek();
            } else {
                startDateTime = DateUtil.getBeginDayOfMonth();
            }
		}
		Date endDateTime = null;
		if (StringUtil.isNotNull(fdEnd)) {
			if (requestContext.isCloud()) {
                endDateTime = new Date(Long.parseLong(fdEnd));
            } else {
                endDateTime = DateUtil.convertStringToDate(fdEnd, format);
            }
		} else {
			if ("week".equals(viewType)) {
                endDateTime = DateUtil.getEndDayOfWeek();
            } else {
                endDateTime = DateUtil.getEndDayOfMonth();
            }
		}
		HQLInfo hql = buildBookHql(requestContext);
		String whereBlock = hql.getWhereBlock();
		whereBlock = StringUtil.linkString(whereBlock, " and ",
				" (kmImeetingBook.fdRecurrenceStr is null or kmImeetingBook.fdRecurrenceStr='NO') ");
		whereBlock = StringUtil.linkString(whereBlock, " and ",
				" kmImeetingBook.fdHoldDate<=:fdEnd and kmImeetingBook.fdFinishDate>:fdStart ");
		hql.setParameter("fdStart", startDateTime);
		hql.setParameter("fdEnd", endDateTime);
		hql.setWhereBlock(whereBlock);
		hql.setOrderBy(" kmImeetingBook.fdHoldDate asc");
		return hql;
	}

	

	/**
	 * 查找非周期性会议预定
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<KmImeetingBook> findNormalBook(RequestContext requestContext)
			throws Exception {
		HQLInfo hql = buildNormalBookHql(requestContext);
		// 增加场所过滤
		hql.setCheckParam(SysAuthConstant.CheckType.AreaCheck, SysAuthConstant.AllCheck.DEFAULT);
		List<KmImeetingBook> bookModels = findList(hql);
		return bookModels;
	}

	@Override
	public HQLInfo buildRangeBookHql(RequestContext requestContext)
			throws Exception {
		String fdStart = requestContext.getParameter("fdStart");// 开始时间
		String fdEnd = requestContext.getParameter("fdEnd");// 结束时间
		String format = ResourceUtil.getString("date.format.date");
		if (StringUtil.isNotNull(requestContext.getParameter("format"))) {
			format = requestContext.getParameter("format");
		}
		String viewType = requestContext.getParameter("viewType");
		Date startDateTime = null;
		if (StringUtil.isNotNull(fdStart)) {
			if (requestContext.isCloud()) {
                startDateTime = new Date(Long.parseLong(fdStart));
            } else {
                startDateTime = DateUtil.convertStringToDate(fdStart, format);
            }
		} else {
			if ("week".equals(viewType)) {
                startDateTime = DateUtil.getBeginDayOfWeek();
            } else {
                startDateTime = DateUtil.getBeginDayOfMonth();
            }
		}
		Date endDateTime = null;
		if (StringUtil.isNotNull(fdEnd)) {
			if (requestContext.isCloud()) {
                endDateTime = new Date(Long.parseLong(fdEnd));
            } else {
                endDateTime = DateUtil.convertStringToDate(fdEnd, format);
            }
		} else {
			if ("week".equals(fdEnd)) {
                endDateTime = DateUtil.getEndDayOfWeek();
            } else {
                endDateTime = DateUtil.getEndDayOfMonth();
            }
		}
		HQLInfo hql = buildBookHql(requestContext);
		String whereBlock = hql.getWhereBlock();
		whereBlock = StringUtil.linkString(whereBlock, " and ",
				"kmImeetingBook.fdRecurrenceStr is not null and kmImeetingBook.fdRecurrenceStr!='NO'");
		whereBlock = StringUtil.linkString(whereBlock, " and ",
				" kmImeetingBook.fdRecurrenceLastEnd > :rangeStart and kmImeetingBook.fdHoldDate <= :rangeEnd ");
		hql.setParameter("rangeStart", startDateTime);
		hql.setParameter("rangeEnd", endDateTime);
		hql.setWhereBlock(whereBlock);
		hql.setOrderBy(" kmImeetingBook.fdHoldDate ");
		return hql;
	}

	/**
	 * 查找周期性会议预定
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<KmImeetingBook> findRangeBook(RequestContext requestContext)
			throws Exception {
		String fdStart = requestContext.getParameter("fdStart");// 开始时间
		String fdEnd = requestContext.getParameter("fdEnd");// 结束时间
		String viewType = requestContext.getParameter("viewType");
		Date startDateTime = null;
		if (StringUtil.isNotNull(fdStart)) {
			if (requestContext.isCloud()) {
                startDateTime = new Date(Long.parseLong(fdStart));
            } else {
                startDateTime = DateUtil.convertStringToDate(fdStart,
                        DateUtil.TYPE_DATE, requestContext.getLocale());
            }
		} else {
			if ("week".equals(viewType)) {
                startDateTime = DateUtil.getBeginDayOfWeek();
            } else {
                startDateTime = DateUtil.getBeginDayOfMonth();
            }
		}
		Date endDateTime = null;
		if (StringUtil.isNotNull(fdEnd)) {
			if (requestContext.isCloud()) {
                endDateTime = new Date(Long.parseLong(fdEnd));
            } else {
                endDateTime = DateUtil.convertStringToDate(fdEnd,
                        DateUtil.TYPE_DATE, requestContext.getLocale());
            }
		} else {
			if ("week".equals(viewType)) {
                endDateTime = DateUtil.getEndDayOfWeek();
            } else {
                endDateTime = DateUtil.getEndDayOfMonth();
            }
		}
		HQLInfo hql = buildRangeBookHql(requestContext);
		List<KmImeetingBook> result = new ArrayList<KmImeetingBook>();
		// 增加场所过滤
		hql.setCheckParam(SysAuthConstant.CheckType.AreaCheck, SysAuthConstant.AllCheck.DEFAULT);
		List<KmImeetingBook> matchBookModels = findList(hql);
		for (KmImeetingBook book : matchBookModels) {
			String recurrenceStr = book.getFdRecurrenceStr();
			Date bookStartDateTime = book.getFdHoldDate();
			Date bookEndDateTime = book.getFdFinishDate();
			List<Date> dates = RecurrenceUtil.getExcuteDateList(recurrenceStr,
					bookStartDateTime, startDateTime,
					endDateTime.getTime() > bookEndDateTime.getTime()
							? endDateTime : bookEndDateTime);
			for (int i = 0; i < dates.size(); i++) {
				Date date = dates.get(i);
				if (i == 0
						&& startDateTime.getTime() < bookStartDateTime.getTime()
						&& date.getTime() != bookStartDateTime.getTime()) {
					if (bookEndDateTime.getTime() > book
							.getFdRecurrenceLastEnd()
							.getTime()) {
						break;
					}
					KmImeetingBook newbook = (KmImeetingBook) BeanUtils
							.cloneBean(book);
					newbook.setFdHoldDate(bookStartDateTime);
					newbook.setFdFinishDate(bookEndDateTime);
					result.add(newbook);
				}

				Date newStartDate = date;
				Date newEndDate = new Date(
						date.getTime() + bookEndDateTime.getTime()
								- bookStartDateTime.getTime());
				if (newEndDate.getTime() > book.getFdRecurrenceLastEnd()
						.getTime()) {
					break;
				}
				KmImeetingBook newbook = (KmImeetingBook) BeanUtils
						.cloneBean(book);
				newbook.setFdHoldDate(newStartDate);
				newbook.setFdFinishDate(newEndDate);
				result.add(newbook);
			}
		}
		return result;
	}



	@Override
	public void delete(String id, RequestContext requestContext)
			throws Exception {
		String deleteType = requestContext.getParameter("deleteType");// 删除方式: all:删除全部、cur:删除当前、after:删除当前及其后续；默认为all
		String deleteDateStr = requestContext.getParameter("deleteDate"); // 基准日，以此日期为基准，删除当前预定  or 删除当前及其后续预定
		Date deleteDate = DateUtil.convertStringToDate(deleteDateStr,
				DateUtil.TYPE_DATETIME, null);
		KmImeetingBook book = (KmImeetingBook) findByPrimaryKey(id, null, true);
		if(book == null){
			return;
		}
		if (StringUtil.isNull(deleteType) || "all".equals(deleteType)
				|| StringUtil.isNull(book.getFdRecurrenceStr())) {
			// 删除全部
			delete(id);
		} else if ("cur".equals(deleteType)) {
			Date matchDate = RecurrenceUtil
					.getNextExecuteDate(book.getFdRecurrenceStr(),
							book.getFdHoldDate(), deleteDate);
			if (matchDate != null) {
				// 生成一份新的预定代表未来的预定
				KmImeetingBook newBook = (KmImeetingBook) BeanUtils
						.cloneBean(book);
				// 更新日历重复信息
				Map<String, String> params = RecurrenceUtil
						.parseRecurrenceStr(newBook.getFdRecurrenceStr());
				if (RecurrenceEndType.COUNT.name()
						.equals(params.get("ENDTYPE"))) {
					int count = new Integer(params.get("COUNT"));
					int hasCount = RecurrenceUtil.getExecuteCount(
							newBook.getFdRecurrenceStr(),
							newBook.getFdHoldDate(),
							newBook.getFdHoldDate(), deleteDate);
					if (hasCount < count) {
						params.put("COUNT", Integer.toString(count - hasCount));
						newBook.setFdRecurrenceStr(
								RecurrenceUtil.buildRecurrenceStr(params));
					}
				}
				newBook.setFdId(IDGenerator.generateID());
				newBook.setFdHoldDate(matchDate);
				newBook.setFdFinishDate(new Date(
						matchDate.getTime() + book.getFdFinishDate().getTime()
								- book.getFdHoldDate().getTime()));
				add(newBook);
			}
		}
		if ("after".equals(deleteType) || "cur".equals(deleteType)) {
			// 更新原定的最后开始时间
			book.setFdRecurrenceLastStart(new Date(deleteDate.getTime() - 1));
			book.setFdRecurrenceLastEnd(new Date(
					deleteDate.getTime() - 1 + book.getFdFinishDate().getTime()
							- book.getFdHoldDate().getTime()));
			if (book.getFdHoldDate().getTime() > book.getFdRecurrenceLastStart()
					.getTime()) {
				super.delete(id);
			} else {
				int hasCount = RecurrenceUtil.getExecuteCount(
						book.getFdRecurrenceStr(), book.getFdHoldDate(),
						book.getFdHoldDate(), deleteDate);
				if (hasCount == 1) {
					super.delete(id);
					return;
				}
				// 更新日历重复信息
				Map<String, String> params = RecurrenceUtil
						.parseRecurrenceStr(book.getFdRecurrenceStr());
				if (RecurrenceEndType.COUNT.name()
						.equals(params.get("ENDTYPE"))) {
					params.put("COUNT", Integer.toString(hasCount - 1));
				} else {
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(deleteDate);
					calendar.add(Calendar.DAY_OF_YEAR, -1);
					params.put("ENDTYPE", RecurrenceEndType.UNTIL.name());
					params.put("UNTIL", new SimpleDateFormat("yyyyMMdd")
							.format(calendar.getTime()));
				}
				book.setFdRecurrenceStr(
						RecurrenceUtil.buildRecurrenceStr(params));
				super.update(book);
			}
		}
	}

	@Override
	public List<KmImeetingBook> findKmImeetingBook(RequestContext request,
			boolean showMy) throws Exception {
		String showType = request.getParameter("showType");
		request.setParameter("showMe",
				"my".equals(showType) || showMy ? "true" : "false");
		List<KmImeetingBook> list = new ArrayList<KmImeetingBook>();
		list.addAll(findNormalBook(request));
		list.addAll(findRangeBook(request));
		UserOperHelper.logFindAll(list, getModelName());
		return list;
	}

	@Override
	public List<KmImeetingBook> findKmImeetingListBook(RequestContext request)
			throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		buildMyBookHql(hqlInfo, request);
		List<KmImeetingBook> books = findList(hqlInfo);
		return books;
	}

	private void buildMyBookHql(HQLInfo hqlInfo, RequestContext request) {
		String whereBlock = hqlInfo.getWhereBlock();
		CriteriaValue cv = new CriteriaValue(request.getRequest());
		String mydoc = cv.poll("mymeeting");
		if ("myAttend".equals(mydoc)) { // 我要参加
			whereBlock = StringUtil.linkString(whereBlock, " and ",
					" kmImeetingBook.docCreator.fdId=:docCreator");
			hqlInfo.setParameter("docCreator", UserUtil.getUser().getFdId());

			String mydate = cv.poll("datemeeting");
			if (StringUtil.isNotNull(mydate)) {
				Calendar current = Calendar.getInstance();
				Date startDate = null;
				Date endDate = null;
				if ("all".equals(mydate)) {
					whereBlock = StringUtil.linkString(whereBlock, " and ",
							" kmImeetingBook.fdFinishDate>:attendDate ");
					hqlInfo.setParameter("attendDate", current.getTime());
					hqlInfo.setWhereBlock(whereBlock);
				} else {
					if ("today".equals(mydate)) {// 今天
						startDate = current.getTime();
						current.set(Calendar.HOUR_OF_DAY, 0);
						current.set(Calendar.MINUTE, 0);
						current.set(Calendar.SECOND, 0);
						current.add(Calendar.DATE, 1);
						endDate = current.getTime();
					} else if ("tomorrow".equals(mydate)) {
						current.set(Calendar.HOUR_OF_DAY, 23);
						current.set(Calendar.MINUTE, 59);
						current.set(Calendar.SECOND, 59);
						startDate = current.getTime();
						current.add(Calendar.DATE, 1);
						endDate = current.getTime();
					} else if ("week".equals(mydate)) {
						startDate = current.getTime();
						endDate = DateUtil.getEndDayOfWeek();
					}
					whereBlock = StringUtil.linkString(whereBlock, " and ",
							" kmImeetingBook.fdFinishDate>=:startDate ");
					whereBlock = StringUtil.linkString(whereBlock, " and ",
							" kmImeetingBook.fdHoldDate<:endDate ");
					hqlInfo.setParameter("startDate", startDate);
					hqlInfo.setParameter("endDate", endDate);
				}

			}

			hqlInfo.setWhereBlock(whereBlock);
			hqlInfo.setOrderBy("kmImeetingBook.fdHoldDate desc");
		} else if ("myHaveAttend".equals(mydoc)) { // 我已参加
			whereBlock = StringUtil.linkString(whereBlock, " and ",
					" kmImeetingBook.docCreator.fdId=:docCreator");
			hqlInfo.setParameter("docCreator", UserUtil.getUser().getFdId());

			String mydate = cv.poll("datemeeting");
			if (StringUtil.isNotNull(mydate)) {
				Calendar current = Calendar.getInstance();
				Date startDate = null;
				Date endDate = null;
				if ("month".equals(mydate)) {
					endDate = current.getTime();
					current.add(Calendar.MONTH, -1);
					startDate = current.getTime();
				} else if ("threeMonth".equals(mydate)) {
					endDate = current.getTime();
					current.add(Calendar.MONTH, -3);
					startDate = current.getTime();
				} else if ("halfYear".equals(mydate)) {
					endDate = current.getTime();
					current.add(Calendar.MONTH, -6);
					startDate = current.getTime();
				}
				whereBlock = StringUtil.linkString(whereBlock, " and ",
						" kmImeetingBook.fdFinishDate<=:endDate and kmImeetingBook.fdFinishDate >=:startDate ");
				hqlInfo.setParameter("startDate", startDate);
				hqlInfo.setParameter("endDate", endDate);
			}

			hqlInfo.setWhereBlock(whereBlock);
			hqlInfo.setOrderBy("kmImeetingBook.fdHoldDate desc");
		} else if ("myCreate".equals(mydoc)) {// 我发起的
			whereBlock = StringUtil.linkString(whereBlock, " and ",
					" kmImeetingBook.docCreator.fdId=:docCreator");
			hqlInfo.setParameter("docCreator", UserUtil.getUser().getFdId());

			String myCreateDate = cv.poll("createdatemeeting");
			if (StringUtil.isNotNull(myCreateDate)) {
				Calendar current = Calendar.getInstance();
				current.setTime(new Date());
				if ("month".equals(myCreateDate)) {
					current.add(Calendar.MONTH, -1);
				} else if ("threeMonth".equals(myCreateDate)) {
					current.add(Calendar.MONTH, -3);
				} else if ("halfYear".equals(myCreateDate)) {
					current.add(Calendar.MONTH, -6);
				}
				whereBlock = StringUtil.linkString(whereBlock, " and ",
						" kmImeetingBook.fdHoldDate>:createDate ");
				hqlInfo.setParameter("createDate", current.getTime());
			}

			hqlInfo.setWhereBlock(whereBlock);
			hqlInfo.setOrderBy("kmImeetingBook.fdHoldDate desc");
			hqlInfo.setCheckParam(SysAuthConstant.CheckType.AllCheck,
					SysAuthConstant.AllCheck.NO);
		}
	}

}
