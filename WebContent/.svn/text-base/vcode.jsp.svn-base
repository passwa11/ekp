<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="java.io.OutputStream"%>
<%@ page import="javax.imageio.ImageIO"%>
<%@ page import="java.awt.Color"%>
<%@ page import="java.awt.Font"%>
<%@ page import="java.awt.Graphics"%>
<%@ page import="java.awt.Graphics2D"%>
<%@ page import="java.awt.image.BufferedImage"%>
<%@ page import="java.util.Random"%>
<%
	response.reset();
	response.setHeader("Cache-Control", "no-cache");
	response.setHeader("Content-Type", "image/jpeg");
	response.setHeader("Pragma", "no-cache");
	response.setDateHeader("Expires", -1);

	BufferedImage img = new BufferedImage(68, 22, BufferedImage.TYPE_INT_RGB);
	// 得到该图片的绘图对象

	Graphics g = img.getGraphics();
	Random r = new Random();
	Color c = new Color(255, 255, 255); // 创建背景颜色
	g.setColor(c);
	// 填充整个图片的颜色
	g.fillRect(0, 0, 68, 22);
	// 向图片中输出数字和字母
	StringBuffer sb = new StringBuffer();
	char[] ch = "ABCDEFGHJKLMNPQRSTUVWXYZabcdefghjklmnpqrstuvwxyz23456789".toCharArray();
	int index, len = ch.length;
	g.setColor(new Color(0, 0, 255)); // 设置字体颜色
	g.setFont(new Font("Arial", Font.ITALIC, 20));// 输出的字体属性
	for (int i = 0; i < 4; i++) {
		index = r.nextInt(len); // 获取随机字符序号
		g.drawString("" + ch[index], (i * 15) + 3, 18);// 写什么数字，在图片
		sb.append(ch[index]);
	}
	//输出干扰线和点
	for (int i = 0; i < 3; i++) {
		g.drawLine(r.nextInt(68), r.nextInt(22), r.nextInt(68), r.nextInt(22));
	}
	for (int i = 0; i < 15; i++) {
		g.drawOval(r.nextInt(68), r.nextInt(22), 1, 1);
	}
	request.getSession().setAttribute("VALIDATION_CODE", sb.toString());

	OutputStream outStream = response.getOutputStream();
	ImageIO.write(img, "JPG", outStream);
	out.clear();
	out = pageContext.pushBody();
%>