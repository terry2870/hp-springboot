package com.hp.springboot.admin.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

import javax.imageio.ImageIO;

/**
 * @author chuan
 * @date 2019-05-05
 * @desc 图形验证码生成
 */
public class VerifyCodeUtils {

	// 验证码字符集
	private static final char[] chars = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
			'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U',
			'V', 'W', 'X', 'Y', 'Z' };
	
	
	// 图片上干扰线数量
	private static final int LINES = 5;
	// 图片宽度
	private static final int IMAGE_WIDTH = 80;
	// 图片高度
	private static final int IMAGE_HEIGHT = 40;
	// 图片字体大小
	private static final int FONT_SIZE = 30;

	/**
	 * @Title: createImage
	 * @Description: 生成图形验证码
	 * @param out
	 * @param verifySize
	 * @return
	 */
	public static String createImage(OutputStream out, int verifySize) {
		StringBuffer sb = new StringBuffer();
		// 1.创建空白图片
		BufferedImage image = new BufferedImage(IMAGE_WIDTH, IMAGE_HEIGHT, BufferedImage.TYPE_INT_RGB);
		// 2.获取图片画笔
		Graphics graphic = image.getGraphics();
		// 3.设置画笔颜色
		graphic.setColor(Color.LIGHT_GRAY);
		// 4.绘制矩形背景
		graphic.fillRect(0, 0, IMAGE_WIDTH, IMAGE_HEIGHT);
		// 5.画随机字符
		Random ran = new Random();
		for (int i = 0; i < verifySize; i++) {
			// 取随机字符索引
			int n = ran.nextInt(chars.length);
			// 设置随机颜色
			graphic.setColor(getRandomColor());
			// 设置字体大小
			graphic.setFont(new Font(null, Font.BOLD + Font.ITALIC, FONT_SIZE));
			// 画字符
			graphic.drawString(chars[n] + "", i * IMAGE_WIDTH / verifySize, IMAGE_HEIGHT * 2 / 3);
			// 记录字符
			sb.append(chars[n]);
		}
		// 6.画干扰线
		for (int i = 0; i < LINES; i++) {
			// 设置随机颜色
			graphic.setColor(getRandomColor());
			// 随机画线
			graphic.drawLine(ran.nextInt(IMAGE_WIDTH), ran.nextInt(IMAGE_HEIGHT), ran.nextInt(IMAGE_WIDTH),
					ran.nextInt(IMAGE_HEIGHT));
		}
		// 7.写入到输出流中
		try {
			ImageIO.write(image, "png", out);
		} catch (IOException e) {
		}
		return sb.toString();
	}

	/**
	 * 获取随机背景颜色(RGB)
	 */
	private static Color getRandomColor() {
		Random ran = new Random();
		Color color = new Color(ran.nextInt(256), ran.nextInt(256), ran.nextInt(256));
		return color;
	}
	
	public static void main(String[] args) {
		try (OutputStream out = new FileOutputStream("D:/1234.png")) {
			createImage(out, 4);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}

