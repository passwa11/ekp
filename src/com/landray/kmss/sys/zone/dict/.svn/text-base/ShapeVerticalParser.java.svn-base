package com.landray.kmss.sys.zone.dict;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;

import com.landray.kmss.sys.attachment.service.ISysAttMainCoreInnerService;
import com.landray.kmss.util.StringUtil;


/**
 * 竖直头像位置、大小缩放解析
 */
public class ShapeVerticalParser implements IShapeParser {
	
	private ISysAttMainCoreInnerService sysAttMainService = null;
	
	public void setSysAttMainService(ISysAttMainCoreInnerService sysAttMainService) {
		this.sysAttMainService = sysAttMainService;
	}

	
	@Override
	public void draw(Graphics2D g2d,
					 SysZonePhotoArea area, String attId) throws Exception {
		ByteArrayOutputStream out = null;
		try {
			if (StringUtil.isNull(attId)) {
	    		return;
	    	}
	    	out = new ByteArrayOutputStream();
	    	sysAttMainService.findData(attId, out);
	    	ByteArrayInputStream  in = new ByteArrayInputStream (out.toByteArray());
	    	draw(g2d, area, in);
		} catch (Exception e) {
			throw e;
		} finally {
			IOUtils.closeQuietly(out);
		}
	}
	
	
	@Override
	public void draw(Graphics2D g2d,
					 SysZonePhotoArea area, InputStream in) throws Exception {
		try {
			if (in == null) {
	    		return;
	    	}
			String shape = area.getShape();
			if(!"rect".equals(shape)) {
                return;
            }
			String[] pos = area.getCoords().split(",");
	    	int startX = Integer.valueOf(pos[0]);
	    	int startY = Integer.valueOf(pos[1]);
	    	int width = Integer.valueOf(pos[2])- startX;
	    	int height = Integer.valueOf(pos[3]) - startY;
	    	Image logoImg = javax.imageio.ImageIO.read(in);
	    	if(logoImg == null) {
                return;
            }
	    	//缩放
	    	logoImg = logoImg.getScaledInstance(width , height , Image.SCALE_SMOOTH);
	    	BufferedImage _image = new BufferedImage(width, height,
					BufferedImage.TYPE_INT_RGB);
	    	Graphics2D lg2d = _image.createGraphics();
	    	lg2d.drawImage(logoImg,
	    			0, 0, width, height,null);
	    	lg2d.setColor(Color.WHITE);
	    	//边框
	    	lg2d.drawRect(0, 0, width - 1, height - 1);  
	    	//lg2d.drawRect(1, 1, width - 1, height - 1);  
	    	//lg2d.drawRect(0, 0, width - 2, height - 2); 
	    	g2d.drawImage(_image, startX, startY, width, height,  null);
	    	lg2d.dispose();
		} catch (Exception e) {
			throw e;
		} finally {
			IOUtils.closeQuietly(in);
		}
	}
}
