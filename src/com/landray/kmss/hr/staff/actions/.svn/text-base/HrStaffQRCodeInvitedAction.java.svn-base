package com.landray.kmss.hr.staff.actions;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.hr.staff.model.HrStaffEntry;
import com.landray.kmss.hr.staff.service.IHrStaffEntryService;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionMapping;
import net.sf.json.JSONObject;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.PrintWriter;

/**
 * <P>人事档案-入职扫码</P>
 * @author sunj
 * @version 1.0 2019年8月13日
 */
public class HrStaffQRCodeInvitedAction extends ExtendAction {
	private static final Logger logger = LoggerFactory.getLogger(HrStaffQRCodeInvitedAction.class);

	private IHrStaffEntryService hrStaffEntryService;

	@Override
	protected IHrStaffEntryService getServiceImp(HttpServletRequest request) {
		if (hrStaffEntryService == null) {
			hrStaffEntryService = (IHrStaffEntryService) getBean("hrStaffEntryService");
		}
		return hrStaffEntryService;
	}


	private final static String[] IMG_LIST = { "0", "1", "2", "3", "4", "5" };
	private final static String IMG_BACK = "./img/back.png";
	private final static int[] IMG_X = { 1, 2, 1, 2, 1, 2 };
	private final static int[] IMG_Y = { 0, 0, 1, 1, 2, 2 };
	private final static String SALT = "pUwqTkVSfeTbltYU";

	/**
	 * 获取验证图片
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws Exception
	 */

	public void getValidateImg(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Long millis = System.currentTimeMillis();
		try {
			String fdId = request.getParameter("fdId");

			request.getSession().setAttribute("millis", millis);
			request.getSession().setAttribute("fdId", fdId);
			int imgIdx = (int) getImgIdx(millis, fdId, 8);
			String imga = getImg(imgIdx, true);
			String imgb = getImg(imgIdx, false);

			JSONObject json = new JSONObject();
			json.put("imga", imga);
			json.put("imgb", imgb);
			json.put("y", IMG_Y[imgIdx]);
			json.put("back", IMG_BACK);
			response.setContentType("application/json");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.println(json);
		} catch (Exception e) {
			logger.error("error:", e);
		}
	}

	/**
	 * 验证图片
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void validate(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Long m = (Long) request.getSession().getAttribute("millis");
		String f = (String) request.getSession().getAttribute("fdId");
		String x = request.getParameter("x").replaceAll("px", "");
		String w = request.getParameter("w").replaceAll("px", "");
		try {
			int imgIdx = (int) getImgIdx(m, null, 8);
			int true_x = IMG_X[imgIdx];
			double xd = Double.parseDouble(x);
			double wd = Double.parseDouble(w);
			JSONObject json = new JSONObject();
			if (judge((int) wd, true_x, (int) xd)) {
				json.put("status", "success");
			}

			response.setContentType("application/json");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.println(json);
		} catch (Exception e) {
			logger.error("error:", e);
		}
	}

	/**
	 * 验证手机号
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void toDetail(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		try {
			String p = request.getParameter("phone");
			HrStaffEntry hrStaffEntry = getServiceImp(request).findByFdMobileNo(p);
			
			JSONObject json = new JSONObject();
			if (null != hrStaffEntry) {
				json.put("status", "success");
				json.put("url", "/resource/hr/staff/hr_staff_entry_anonymous/hrStaffEntry.do?method=findByMobile&fdId="
						+ hrStaffEntry.getFdId());
			}
			response.setContentType("application/json");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.println(json);
		} catch (Exception e) {
			logger.error("error:", e);
		}
	}

	private long getImgIdx(long millis, String fdId, long key) {
		key = key <= 0 ? 1 : key;
		long fdIdNumber = 0;
		String str2 = "";
		if (fdId != null && !"".equals(fdId)) {
			String str = fdId.trim();
			for (int i = 0; i < str.length(); i++) {
				if (str.charAt(i) >= 48 && str.charAt(i) <= 57) {
					str2 += str.charAt(i);
				}
			}
		}
		if (str2.length() <= 0) {
			fdIdNumber = 1234L;
		} else {
			if (str2.length() > 4) {
				str2 = str2.substring(0, 4);
			}
			fdIdNumber = Long.parseLong(str2);
		}
		long k = (millis / 60000 + fdIdNumber) / key;
		return k % IMG_LIST.length;
	}

	private String getImg(int idx, boolean type) {
		String img1 = IMG_LIST[idx];
		String img2 = type ? "a.png" : "b.png";
		String img = "/img/" + img1 + "/" + img2;

		InputStream in = null;
		byte[] data = null;
		try {
			String realPath = this.getServletContext().getRealPath("/hr/ratify/mobile/entry/invite_qr_code" + img);

			in = new FileInputStream(realPath);

			data = new byte[in.available()];
			int count = in.read(data);
			if (count <= 0) {
				data = new byte[in.available()];
			}
			in.close();
		} catch (Exception e) {
			logger.error("error:", e);
		}
		return "data:image/jpeg;base64," + Base64.encodeBase64String(data);
	}

	private boolean judge(int width, int true_x, int x) {
		int c = width * true_x / 3;
		int l = c - 10;
		int r = c + 10;
		return x > l && x < r;
	}

}
