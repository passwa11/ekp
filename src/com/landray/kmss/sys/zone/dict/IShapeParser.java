package com.landray.kmss.sys.zone.dict;

import java.awt.Graphics2D;
import java.io.InputStream;

public interface IShapeParser {
	public  void  draw(Graphics2D g2d, 
			SysZonePhotoArea area, String attId) throws Exception;
	
	public void draw(Graphics2D g2d, 
			SysZonePhotoArea area, InputStream in) throws Exception;
}
