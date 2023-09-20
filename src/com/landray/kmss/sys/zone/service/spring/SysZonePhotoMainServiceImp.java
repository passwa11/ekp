package com.landray.kmss.sys.zone.service.spring;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.sys.attachment.model.SysAttMain;
import com.landray.kmss.sys.attachment.service.ISysAttMainCoreInnerService;
import com.landray.kmss.sys.zone.dict.*;
import com.landray.kmss.sys.zone.model.SysZonePhotoMain;
import com.landray.kmss.sys.zone.service.ISysZonePhotoMainService;
import com.landray.kmss.sys.zone.util.JAXBUtil;
import com.landray.kmss.sys.zone.util.SysZonePhotoUtil;
import com.landray.kmss.util.IDGenerator;
import com.landray.kmss.util.ModelUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import freemarker.template.Configuration;
import freemarker.template.Template;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.springframework.web.context.ServletContextAware;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.List;
import java.util.*;

/**
 * 照片墙业务接口实现
 * 
 * @author
 * @version 1.0
 */
public class SysZonePhotoMainServiceImp extends BaseServiceImp implements
		ISysZonePhotoMainService, ServletContextAware {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SysZonePhotoMainServiceImp.class);
	private ServletContext servletContext;

	@Override
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	private ISysAttMainCoreInnerService sysAttMainService;

	public void setSysAttMainService(
			ISysAttMainCoreInnerService sysAttMainService) {
		this.sysAttMainService = sysAttMainService;
	}

	private static String PHOTO_NAME = "tphoto.png";

	private static String PHOTO_HTML_NAME = "tmap.html";

	private static String TMP_FILE_PATH = "/sys/zone/sys_zone_photo_template";

	private static Map<String, SysZonePhotoTemplate> photoTmps = new HashMap<String, SysZonePhotoTemplate>();

	private Map<String, SysZonePhotoTemplate> getTmps() {
		if(photoTmps.isEmpty() ||null == photoTmps ) {
			synchronized (photoTmps) {
				if (null == photoTmps || photoTmps.isEmpty()) {
					ResourceBundle bundle = ResourceBundle
							.getBundle("com.landray.kmss.sys.zone.dict.TemplateRegister");
					String register = bundle.getString("SysZonePhotoMapTmpls");
					if (StringUtil.isNull(register)) {
						register = "[{\"id\":\"default\",\"name\":\"default\", \"isDefault\":\"1\"}]";
					}
					JSONArray jsArray = JSONArray.fromObject(register);
					for (int i = 0; i < jsArray.size(); i++) {
						String id = ((JSONObject) jsArray.get(i)).getString("id");
						if (StringUtil.isNull(id)) {
							continue;
						}
						String name = ((JSONObject) jsArray.get(i)).getString("name");
						String isDefault = ((JSONObject) jsArray.get(i))
								.getString("isDefault");
						Boolean isDef = Boolean.FALSE;
						if ("1".equals(isDefault) || "true".equals(isDefault)) {
							isDef = Boolean.TRUE;
						}
						SysZonePhotoTemplate tmp = null;
						try {
							tmp = loadTmp(id, name, isDef);
						} catch (Exception e) {
							e.printStackTrace();
							logger.error("获取照片墙模板错误，模板id:" + id, e);
						}
						if (tmp != null) {
							photoTmps.put(id, tmp);
						}
					}
				}
			}
		}
		return photoTmps;
	}

	/**
	 * 将照片墙模板load到内存
	 * 
	 * @param id
	 * @param name
	 * @return
	 * @throws Exception
	 */
	private SysZonePhotoTemplate loadTmp(String id, String name,
			Boolean isDefault) throws Exception {
		String filePath = getTmpPath(id);
		File file = new File(filePath);
		if (file.exists()) {
			FileInputStream is = new FileInputStream(file);
			SysZonePhotoTemplate rtnVal = JAXBUtil.unmarshal(is,
					SysZonePhotoTemplate.class);
			rtnVal.setId(id);
			rtnVal.setName(name);
			if (isDefault) {
				rtnVal.setIsDefault(isDefault);
			}
			return rtnVal;
		} else {
			return null;
		}
	}

	/**
	 * 定时任务生成所有的img和html片段
	 */
	@Override
    public void buildPhotoMap() throws Exception {
		Iterator<Map.Entry<String, SysZonePhotoTemplate>> iter 
			= (Iterator<Map.Entry<String, SysZonePhotoTemplate>>) this.getTmps().entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry<String, SysZonePhotoTemplate> entry 
				= (Map.Entry<String, SysZonePhotoTemplate>) iter.next();
			Iterator<Map.Entry<String, SysZonePhotoSource>> sourceIter 
					= (Iterator<Map.Entry<String, SysZonePhotoSource>>) SysZonePhotoUtil
								.getImgSources(servletContext).entrySet().iterator();
			while (sourceIter.hasNext()) {
				Map.Entry<String, SysZonePhotoSource> sourceEntry 
						= (Map.Entry<String, SysZonePhotoSource>) sourceIter.next();
				this.bulid(entry.getValue(), sourceEntry.getValue());
			}
		}
	}

	/**
	 * 生成照片墙
	 * 
	 * @param template
	 *            照片墙模板
	 * @param source
	 *            照片墙数据
	 */
	private void bulid(SysZonePhotoTemplate template, SysZonePhotoSource source)
			throws Exception {
		String bgPath = getImgPath(template.getName(), template.getImg()
				.getSrc());
		Image bgImg = javax.imageio.ImageIO.read(new File(bgPath));
		int bgwidth = bgImg.getWidth(null);
		int bgheight = bgImg.getHeight(null);
		BufferedImage bgBuffer = new BufferedImage(bgwidth, bgheight,
				BufferedImage.TYPE_INT_RGB);
		Graphics2D g2d = bgBuffer.createGraphics();
		g2d.drawImage(bgImg, 0, 0, bgwidth, bgheight, null);
		//从模板中获取区域的集合
		ArrayList<SysZonePhotoArea> areas = template.getMap().getPhotoAreas();
		IShapeParser verticalParser = (IShapeParser) SpringBeanUtil
				.getBean("shapeVerticalParser");
		ArrayList<SysZonePhotoAreaPreview> areaPreList = new ArrayList<SysZonePhotoAreaPreview>();
		for (int i = 0; i < areas.size() && i < source.getImgs().size(); i++) {
			// 暂时只支持垂直矩形小头像
			if ("rect".equals(areas.get(i).getShape())
					&& verticalParser != null) {
				//根据集合和attId将头像画到画板上去
				if(source.getImgs().get(i).get("attId") != null) {
					verticalParser.draw(g2d, areas.get(i), (String)source.getImgs().get(i)
							.get("attId"));
				}
				else {
					InputStream in = (InputStream)source.getImgs().get(i).get("byteArray");
					verticalParser.draw(g2d, areas.get(i), in);
				}
						
				SysZonePhotoAreaPreview pre = new SysZonePhotoAreaPreview();
				pre.setAreaTemplate(areas.get(i));
				String title = (String)source.getImgs().get(i).get("title");
				pre.setTitle(StringUtil.XMLEscape(title));
				pre.setHref((String)source.getImgs().get(i).get("href"));
				pre.setImgUrl((String)source.getImgs().get(i).get("imgUrl"));
				areaPreList.add(pre);
			}
		}
		g2d.dispose();

		HQLInfo hqlInfo = new HQLInfo();
		String whereBlock = "sysZonePhotoMain.fdTemplateId =:fdTemplateId and sysZonePhotoMain.fdSourceId =:fdSourceId";
		hqlInfo.setWhereBlock(whereBlock);
		hqlInfo.setParameter("fdTemplateId", template.getId());
		hqlInfo.setParameter("fdSourceId", source.getId());
		SysZonePhotoMain sysZonePhotoMain = null;
		Object obj = this.findFirstOne(hqlInfo);
		if (obj!=null) {
			sysZonePhotoMain = (SysZonePhotoMain) obj;
		} else {
			String fdId = IDGenerator.generateID();
			sysZonePhotoMain = new SysZonePhotoMain();
			sysZonePhotoMain.setFdId(fdId);
			if (template.getIsDefault()) {
				sysZonePhotoMain.setFdIsDefault(Boolean.TRUE);
			}
			sysZonePhotoMain.setDocCreateTime(new Date());
			sysZonePhotoMain.setFdTemplateId(template.getId());
			sysZonePhotoMain.setFdTemplateName(template.getName());
			sysZonePhotoMain.setFdName("name");
			sysZonePhotoMain.setFdSourceId(source.getId());
			sysZonePhotoMain.setFdSourceName(source.getName());
			sysZonePhotoMain.setFdTemplateId(template.getId());
			sysZonePhotoMain.setFdTemplateName(template.getName());

		}
		sysZonePhotoMain.setFdLastModifiedTime(new Date());
		this.getBaseDao().update(sysZonePhotoMain);
		/*
		 * 删除原本的附件信息，此时图片和html文件还在，随着附件信息清除的定时任务清除而清除
		 * 默认是一个月后,可指定sys.att.slice.expire
		 */
		sysAttMainService.deleteCoreModels(sysZonePhotoMain);

		// 保存图片附件
		ByteArrayOutputStream imgContent = new ByteArrayOutputStream();
		ImageIO.write(bgBuffer, "png", imgContent);
		sysAttMainService.addAttachment(sysZonePhotoMain, "spic",
				imgContent.toByteArray(), PHOTO_NAME, "byte");
		// 保存html
		Configuration cfg = new Configuration();
		cfg.setClassForTemplateLoading(getClass(), "");
		Template tmpl = cfg.getTemplate("template.ftl", "UTF-8");
		Map<String, Object> root = new HashMap<String, Object>();
		tmpl.setEncoding("UTF-8");
		root.put("areaList", areaPreList);
		root.put("bgWidth", bgwidth);
		root.put("bgHeight", bgheight);
		root.put("name", template.getName());
		root.put("bgSrc", getMapPath(sysZonePhotoMain, "spic"));
		StringWriter out = new StringWriter();
		tmpl.process(root, out);
		sysAttMainService.addAttachment(sysZonePhotoMain, "html", out
				.toString().getBytes("UTF-8"), PHOTO_HTML_NAME, "byte");

	}

	/**
	 * 获取模板xml文件路径，所在文件名以模板id命名
	 */
	private String getTmpPath(String filePrefix) {
		String file = servletContext.getRealPath(TMP_FILE_PATH);
		return file + "/" + filePrefix + "/template.xml";
	}

	private String getImgPath(String filePrefix, String imgName) {
		String file = servletContext.getRealPath(TMP_FILE_PATH);
		return file + "/" + filePrefix + "/" + imgName;
	}

	@Override
	public String getMapPath(SysZonePhotoMain model, String fdKey)
			throws Exception {
		String rtnUrl = null;
		List<SysAttMain> attMainList = sysAttMainService.findByModelKey(
				ModelUtil.getModelClassName(model), model.getFdId(), fdKey);
		if (attMainList.size() > 0) {
			SysAttMain imgAttMain = attMainList.get(0);
			rtnUrl = "/sys/attachment/sys_att_main/sysAttMain.do?method=download&fdId="
					+ imgAttMain.getFdId();
		}
		return rtnUrl;
	}

}
