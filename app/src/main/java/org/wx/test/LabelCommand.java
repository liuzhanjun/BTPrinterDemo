package org.wx.test;

import android.graphics.Bitmap;
import android.util.Log;
import org.wx.test.EscCommand.ENABLE;

import java.io.UnsupportedEncodingException;
import java.util.Vector;

/**
 * LabelCommand.java
 *
 * @author Mr.阿拉丁
 * @data 2014-11-7
 * @Company Gprinter
 */
public class LabelCommand {
	private final static String DEBUG_TAG = "LabelCommand";
	Vector<Byte> Command = null;

	public static enum FOOT {
		F2(0), F5(1);
		private final int value;

		private FOOT(int value) {
			this.value = value;
		}

		public int getValue() {
			return this.value;
		}
	}

	public static enum SPEED {
		SPEED1DIV5(1.5f), SPEED2(2), SPEED3(3), SPEED4(4);
		private final float value;

		private SPEED(float value) {
			this.value = value;
		}

		public float getValue() {
			return this.value;
		}
	}

	public static enum READABEL {
		DISABLE(0), EANBEL(1);
		private final int value;

		private READABEL(int value) {
			this.value = value;
		}

		public int getValue() {
			return this.value;
		}
	}

	public static enum BITMAP_MODE {
		OVERWRITE(0), OR(1), XOR(2);
		private final int value;

		private BITMAP_MODE(int value) {
			this.value = value;
		}

		public int getValue() {
			return this.value;
		}
	}

	public static enum DENSITY {
		DNESITY0(0), DNESITY1(1), DNESITY2(2), DNESITY3(3), DNESITY4(4), DNESITY5(5), DNESITY6(6), DNESITY7(
				7), DNESITY8(8), DNESITY9(
						9), DNESITY10(10), DNESITY11(11), DNESITY12(12), DNESITY13(13), DNESITY14(14), DNESITY15(15);
		private final int value;

		private DENSITY(int value) {
			this.value = value;
		}

		public int getValue() {
			return this.value;
		}
	}

	public static enum DIRECTION {
		FORWARD(0), BACKWARD(1);
		private final int value;

		private DIRECTION(int value) {
			this.value = value;
		}

		public int getValue() {
			return this.value;
		}
	}

	public static enum MIRROR {
		NORMAL(0), MIRROR(1);
		private final int value;

		private MIRROR(int value) {
			this.value = value;
		}

		public int getValue() {
			return this.value;
		}
	}

	public static enum CODEPAGE {
		PC437(437), PC850(850), PC852(852), PC860(860), PC863(863), PC865(865), WPC1250(1250), WPC1252(1252), WPC1253(
				1253), WPC1254(1254);
		private final int value;

		private CODEPAGE(int value) {
			this.value = value;
		}

		public int getValue() {
			return this.value;
		}
	}

	public static enum FONTMUL {
		MUL_1(1), MUL_2(2), MUL_3(3), MUL_4(4), MUL_5(5), MUL_6(6), MUL_7(7), MUL_8(8), MUL_9(9), MUL_10(10);
		private final int value;

		private FONTMUL(int value) {
			this.value = value;
		}

		public int getValue() {
			return this.value;
		}
	}

	public static enum FONTTYPE {
		FONT_1("1"), FONT_2("2"), FONT_3("3"), FONT_4("4"), FONT_5("5"), FONT_6("6"), FONT_7("7"), FONT_8("8"), FONT_9(
				"9"), FONT_10("10"), SIMPLIFIED_CHINESE("TSS24.BF2"), TRADITIONAL_CHINESE("TST24.BF2"), KOREAN("K");
		private final String value;

		private FONTTYPE(String value) {
			this.value = value;
		}

		public String getValue() {
			return this.value;
		}
	}

	public static enum ROTATION {
		ROTATION_0(0), ROTATION_90(90), ROTATION_180(180), ROTATION_270(270);
		private final int value;

		private ROTATION(int value) {
			this.value = value;
		}

		public int getValue() {
			return this.value;
		}
	}

	public static enum EEC {
		LEVEL_L("L"), LEVEL_M("M"), LEVEL_Q("Q"), LEVEL_H("H");
		private final String value;

		private EEC(String value) {
			this.value = value;
		}

		public String getValue() {
			return this.value;
		}
	}

	public static enum BARCODETYPE {
		CODE128("128"), CODE128M("128M"), EAN128("EAN128"), ITF25("25"), ITF25C("25C"), CODE39("39"), CODE39C(
				"39C"), CODE39S("39S"), CODE93("93"), EAN13("EAN13"), EAN13_2("EAN13+2"), EAN13_5("EAN13+5"), EAN8(
						"EAN8"), EAN8_2("EAN8+2"), EAN8_5("EAN8+5"), CODABAR("CODA"), POST("POST"), UPCA(
								"UPCA"), UPCA_2("UPCA+2"), UPCA_5("UPCA+5"), UPCE("UPCE13"), UPCE_2("UPCE13+2"), UPCE_5(
										"UPCE13+5"), CPOST("CPOST"), MSI("MSI"), MSIC(
												"MSIC"), PLESSEY("PLESSEY"), ITF14("ITF14"), EAN14("EAN14"),;
		private final String value;

		private BARCODETYPE(String value) {
			this.value = value;
		}

		public String getValue() {
			return this.value;
		}
	}

	public static enum RESPONSE_MODE {
		ON("ON"), OFF("OFF"), BATCH("BATCH");
		private final String value;

		private RESPONSE_MODE(String value) {
			this.value = value;
		}

		public String getValue() {
			return this.value;
		}
	}

	public LabelCommand() {
		Command = new Vector<Byte>();
	}

	/**
	 * 构造函数说明：初始化标签，设置标签尺寸 单位 mm
	 *
	 * @param width
	 *            标签宽度
	 * @param height
	 *            标签高度
	 * @param gap
	 *            标签间隙
	 */
	public LabelCommand(int width, int height, int gap) {
		Command = new Vector<Byte>(4096, 1024);
		addSize(width, height);
		addGap(gap);
	}

	/**
	 * 方法说明：清除命令缓冲区
	 *
	 * @return void
	 */
	public void clrCommand() {
		Command.clear();
	}

	/**
	 * 方法说明：将字符串转成十六进制码 gb2312编码
	 *
	 * @param str
	 *            命令字符串
	 * @return void
	 */
	private void addStrToCommand(String str) {
		byte[] bs = null;
		if (!str.equals("")) {
			try {
				bs = str.getBytes("GB2312");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			for (int i = 0; i < bs.length; i++) {
				Command.add(bs[i]);
			}
		}
	}
	private void addStrToCommand(String str,FONTTYPE font) {
		byte[] bs = null;
		if (!str.equals("")) {
			try {
				switch (font)
				{			
					case SIMPLIFIED_CHINESE:
						{
							bs = str.getBytes("gb18030");
							break;
						}			
					case TRADITIONAL_CHINESE:
						{
							bs = str.getBytes("big5");
							break;
						}
					case KOREAN:
						{
							bs = str.getBytes("cp949");
							break;
						}
					default :
						{
							bs=str.getBytes("gb2312");
							break;
						}
										
				}
		
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			for (int i = 0; i < bs.length; i++) {
				Command.add(bs[i]);
			}
		}
	}

	/**
	 * 方法说明：设置标签间隙尺寸 单位mm
	 *
	 * @param gap
	 *            间隙长度
	 * @return void
	 */
	public void addGap(int gap) {
		String str = "GAP " + gap + " mm," + 0 + " mm" + "\r\n";
		addStrToCommand(str);
	}

	/**
	 * 方法说明：设置标签尺寸的宽和高
	 *
	 * @param width
	 *            标签宽度
	 * @param height
	 *            标签高度
	 * @return void
	 */
	public void addSize(int width, int height) {
		String str = "SIZE " + width + " mm," + height + " mm" + "\r\n";
		addStrToCommand(str);
	}

	/**
	 * 方法说明：打开钱箱命令
	 *
	 * @param m
	 *            钱箱号
	 * @param t1
	 *            高电平时间
	 * @param t2
	 *            低电平时间
	 * @return void
	 */
	public void addCashdrwer(FOOT m, int t1, int t2) {
		String str = "CASHDRAWER " + m.getValue() + "," + t1 + "," + t2 + "\r\n";
		addStrToCommand(str);
	}

	/**
	 * 方法说明：设置剥离模式下标签停止的位置 单位mm
	 *
	 * @param offset
	 *            偏移量
	 * @return void
	 */
	public void addOffset(int offset) {
		String str = "OFFSET " + offset + " mm" + "\r\n";
		addStrToCommand(str);
	}

	/**
	 * 方法说明：设置打印速度
	 *
	 * @param speed
	 *            打印速度
	 * @return void
	 */
	public void addSpeed(SPEED speed) {
		String str = "SPEED " + speed.getValue() + "\r\n";
		addStrToCommand(str);
	}

	/**
	 * 方法说明：设置打印浓度
	 *
	 * @param density
	 *            浓度
	 * @return void
	 */
	public void addDensity(DENSITY density) {
		String str = "DENSITY " + density.getValue() + "\r\n";
		addStrToCommand(str);
	}

	/**
	 * 方法说明：设置打印方向
	 *
	 * @param direction
	 *            方向
	 * @return void
	 */
	public void addDirection(DIRECTION direction, MIRROR mirror) {
		String str = "DIRECTION " + direction.getValue() + ',' + mirror.getValue() + "\r\n";
		addStrToCommand(str);
	}

	/**
	 * 方法说明：设置标签原点坐标
	 *
	 * @param x
	 *            横坐标
	 * @param y
	 *            纵坐标
	 * @return void
	 */
	public void addReference(int x, int y) {
		String str = "REFERENCE " + x + "," + y + "\r\n";
		addStrToCommand(str);
	}

	/**
	 * 方法说明：设置标签偏移距离 单位mm
	 *
	 * @param shift
	 *            偏移量
	 * @return void
	 */
	public void addShif(int shift) {
		String str = "SHIFT " + shift + "\r\n";
		addStrToCommand(str);
	}

	/**
	 * 方法说明：清除打印缓冲区
	 *
	 * @return void
	 */
	public void addCls() {
		String str = "CLS" + "\r\n";
		addStrToCommand(str);
	}

	/**
	 * 方法说明：进纸 单位为dot 1mm为8dot
	 *
	 * @param dot
	 *            进制距离
	 * @return void
	 */
	public void addFeed(int dot) {
		String str = "FEED " + dot + "\r\n";
		addStrToCommand(str);
	}

	/**
	 * 方法说明：退纸 单位为dot 1mm为8dot
	 *
	 * @param dot
	 *            退纸距离
	 * @return void
	 */
	public void addBackFeed(int dot) {
		String str = "BACKFEED " + dot + "\r\n";
		addStrToCommand(str);
	}

	/**
	 * 方法说明：走一张标签
	 *
	 * @return void
	 */
	public void addFormFeed() {
		String str = "FORMFEED" + "\r\n";
		addStrToCommand(str);
	}

	/**
	 * 方法说明：校准标签纸
	 *
	 * @return void
	 */
	public void addHome() {
		String str = "HOME" + "\r\n";
		addStrToCommand(str);
	}

	/**
	 * 方法说明：执行打印
	 *
	 * @param m
	 * @param n
	 * @return void
	 */
	public void addPrint(int m, int n) {
		String str = "PRINT " + m + "," + n + "\r\n";
		addStrToCommand(str);
	}

	/**
	 * 方法说明：执行打印
	 *
	 * @param m
	 * @param n
	 * @return void
	 */
	public void addPrint(int m) {
		String str = "PRINT " + m + "\r\n";
		addStrToCommand(str);
	}

	/**
	 * 方法说明：设置蜂鸣器
	 *
	 * @param level
	 *            频率
	 * @param interval
	 *            时间
	 * @return void
	 */
	public void addCodePage(CODEPAGE page) {
		String str = "CODEPAGE " + page.getValue() + "\r\n";
		addStrToCommand(str);
	}

	/**
	 * 方法说明：设置蜂鸣器
	 *
	 * @param level
	 *            频率
	 * @param interval
	 *            时间
	 * @return void
	 */
	public void addSound(int level, int interval) {
		String str = "SOUND " + level + "," + interval + "\r\n";
		addStrToCommand(str);
	}

	/**
	 * 方法说明 ：该指令用于设定打印机进纸时,若经过所设定的长度仍无法侦测到垂直间距， 则打印机发生错误,停止进纸
	 *
	 * @param n
	 *            单位为dot
	 * @return void
	 */
	public void addLimitFeed(int n) {
		String str = "LIMITFEED " + n + "\r\n";
		addStrToCommand(str);
	}

	/**
	 * 方法说明:打印自检页
	 *
	 * @return void
	 */
	public void addSelfTest() {
		String str = "SELFTEST" + "\r\n";
		addStrToCommand(str);
	}

	/**
	 * 方法说明:在标签上画线
	 *
	 * @param x
	 *            横坐标
	 * @param y
	 *            纵坐标
	 * @param width
	 *            线宽
	 * @param height
	 *            线高
	 * @return void
	 */
	public void addBar(int x, int y, int width, int height) {// BARCODE
		// 20,0,"39",60,1,0,2,2,"12345678"
		String str = "BAR " + x + "," + y + "," + width + "," + height + "\r\n";
		addStrToCommand(str);
	}

	/**
	 * 方法说明:在标签上绘制文字
	 *
	 * @param x
	 *            横坐标
	 * @param y
	 *            纵坐标
	 * @param font
	 *            字体类型
	 * @param rotation
	 *            旋转角度
	 * @param Xscal
	 *            横向放大
	 * @param Yscal
	 *            纵向放大
	 * @param text
	 *            文字字符串
	 * @return void
	 */
	public void addText(int x, int y, FONTTYPE font, ROTATION rotation, FONTMUL Xscal, FONTMUL Yscal, String text) {
		String str = "TEXT " + x + "," + y + "," + "\"" + font.getValue() + "\"" + "," + rotation.getValue() + ","
				+ Xscal.getValue() + "," + Yscal.getValue() + "," + "\"" + text + "\"" + "\r\n";
		addStrToCommand(str,font);
	}

	/**
	 * 方法说明:在标签上绘制一维条码
	 *
	 * @param x
	 *            横坐标
	 * @param y
	 *            纵坐标
	 * @param type
	 *            条码类型
	 * @param height
	 *            条码高度
	 * @param readable
	 *            是否可识别
	 * @param rotation
	 *            旋转角度
	 * @param content
	 *            条码内容
	 * @return void
	 */
	public void add1DBarcode(int x, int y, BARCODETYPE type, int height, READABEL readable, ROTATION rotation,
			String content) {// BARCODE
		// 20,0,"39",60,1,0,2,2,"12345678"
		int narrow = 2, width = 2;
		String str = "BARCODE " + x + "," + y + "," + "\"" + type.getValue() + "\"" + "," + height + ","
				+ readable.getValue() + "," + rotation.getValue() + "," + narrow + "," + width + "," + "\"" + content
				+ "\"" + "\r\n";
		addStrToCommand(str);
	}

	/**
	 * 方法说明:在标签上绘制一维条码
	 *
	 * @param x
	 *            横坐标
	 * @param y
	 *            纵坐标
	 * @param type
	 *            条码类型
	 * @param height
	 *            条码高度
	 * @param readable
	 *            是否可识别
	 * @param rotation
	 *            旋转角度
	 * @param content
	 *            条码内容
	 * @return void
	 */
	public void add1DBarcode(int x, int y, BARCODETYPE type, int height, READABEL readable, ROTATION rotation,
			int narrow, int width, String content) {// BARCODE
		// 20,0,"39",60,1,0,2,2,"12345678"
		String str;
		str = "BARCODE " + x + "," + y + "," + "\"" + type.getValue() + "\"" + "," + height + "," + readable.getValue()
				+ "," + rotation.getValue() + "," + narrow + "," + width + "," + "\"" + content + "\"" + "\r\n";
		addStrToCommand(str);
	}

	/**
	 * 方法说明:在标签上绘制黑块
	 *
	 * @param x
	 *            起始横坐标
	 * @param y
	 *            起始横坐标
	 * @param xend
	 *            终点横坐标
	 * @param yend
	 *            终点纵坐标
	 * @return void
	 */
	public void addBox(int x, int y, int xend, int yend, int thickness) {// BARCODE

		String str = "BOX " + x + "," + y + "," + xend + "," + yend + "," + thickness + "\r\n";
		addStrToCommand(str);
	}

	/**
	 * 方法说明:在标签上绘制图片
	 *
	 * @param x
	 *            起始横坐标
	 * @param y
	 *            起始纵坐标
	 * @param mode
	 *            模式
	 * @param bmp
	 *            图片数据
	 * @return void
	 */
	public void addBitmap(int x, int y, BITMAP_MODE mode, int nWidth, Bitmap b) {
		if (b != null) {
			int width = (nWidth + 7) / 8 * 8;
			int height = b.getHeight() * width / b.getWidth();
			Log.d("BMP", "bmp.getWidth() " + b.getWidth());
			Bitmap grayBitmap = GpUtils.toGrayscale(b);
			Bitmap rszBitmap = GpUtils.resizeImage(grayBitmap, width, height);
			byte[] src = GpUtils.bitmapToBWPix(rszBitmap);
			height = src.length / width;
			width /= 8;
			String str = "BITMAP " + x + "," + y + "," + width + "," + height + "," + mode.getValue() + ",";
			addStrToCommand(str);
			byte[] codecontent = GpUtils.pixToLabelCmd(src);
			for (int k = 0; k < codecontent.length; k++) {
				Command.add(codecontent[k]);
			}
			Log.d(DEBUG_TAG, "codecontent" + codecontent);
		}
	}

	public void addBitmapByMethod(int x, int y, BITMAP_MODE mode, int nWidth, Bitmap b) {
		if (b != null) {
			int width = (nWidth + 7) / 8 * 8;
			int height = b.getHeight() * width / b.getWidth();
			Log.d("BMP", "bmp.getWidth() " + b.getWidth());
			Bitmap rszBitmap = GpUtils.resizeImage(b, width, height);
			Bitmap grayBitmap = GpUtils.filter(rszBitmap, width, height);
			byte[] src = GpUtils.bitmapToBWPix(grayBitmap);
			height = src.length / width;
			width /= 8;
			String str = "BITMAP " + x + "," + y + "," + width + "," + height + "," + mode.getValue() + ",";
			addStrToCommand(str);
			byte[] codecontent = GpUtils.pixToLabelCmd(src);
			for (int k = 0; k < codecontent.length; k++) {
				Command.add(codecontent[k]);
			}
			Log.d(DEBUG_TAG, "codecontent" + codecontent);
		}
	}

	public void addBitmap(int x, int y, int nWidth, Bitmap bmp) {
		if (bmp != null) {
			int width = (nWidth + 7) / 8 * 8;
			int height = bmp.getHeight() * width / bmp.getWidth();
			Log.d("BMP", "bmp.getWidth() " + bmp.getWidth());
			Bitmap rszBitmap = GpUtils.resizeImage(bmp, width, height);
			byte[] bytes = GpUtils.printTscDraw(x, y, BITMAP_MODE.OVERWRITE, rszBitmap);
			for (int i = 0; i < bytes.length; i++) {
				Command.add(bytes[i]);
			}
			addStrToCommand("\r\n");
		}
	}

	/**
	 * 方法说明:该指令用于清除影像缓冲区部份区域的数据
	 *
	 * @param x
	 *            起始横坐标
	 * @param y
	 *            起始横坐标
	 * @param xend
	 *            终点横坐标
	 * @param yend
	 *            终点纵坐标
	 **/
	public void addErase(int x, int y, int xwidth, int yheight) {
		String str = "ERASE " + x + "," + y + "," + xwidth + "," + yheight + "\r\n";
		addStrToCommand(str);
	}

	/**
	 * 方法说明:该指令用于清除影像缓冲区部份区域的数据
	 *
	 * @param x
	 *            起始横坐标
	 * @param y
	 *            起始横坐标
	 * @param xend
	 *            终点横坐标
	 * @param yend
	 *            终点纵坐标
	 */
	public void addReverse(int x, int y, int xwidth, int yheight) {
		String str = "REVERSE " + x + "," + y + "," + xwidth + "," + yheight + "\r\n";
		addStrToCommand(str);
	}

	/**
	 * 方法说明:该指令用于清除影像缓冲区部份区域的数据
	 *
	 * @param x
	 *            起始横坐标
	 * @param y
	 *            起始横坐标
	 * @param xend
	 *            终点横坐标
	 * @param yend
	 *            终点纵坐标
	 */
	public void addQRCode(int x, int y, EEC level, int cellwidth, ROTATION rotation, String data) {
		String str = "QRCODE " + x + "," + y + "," + level.getValue() + "," + cellwidth + "," + 'A' + ","
				+ rotation.getValue() + "," + "\"" + data + "\"" + "\r\n";
		addStrToCommand(str);
		// addStrToCommandUTF8Encoding(str);
	}

	/**
	 * 方法说明:获得打印命令
	 *
	 * @return Vector<Byte>
	 */
	public Vector<Byte> getCommand() {
		return Command;
	}

	/**
	 * 方法说明 :查询打印机型号
	 *
	 * @return void
	 */
	public void addQueryPrinterType() {
		String str = new String();
		str = "~!T" + "\r\n";
		addStrToCommand(str);
	}

	/**
	 * 方法说明:查询打印机实时状态
	 *
	 * @return void
	 */
	public void addQueryPrinterStatus() {
		Command.add((byte) 0x1b);
		Command.add((byte) '!');
		Command.add((byte) '?');
	}

	/**
	 * 方法说明:打印机复位
	 *
	 * @return void
	 */
	public void addResetPrinter() {
		Command.add((byte) 0x1b);
		Command.add((byte) '!');
		Command.add((byte) 'R');
	}

	/**
	 * 方法说明:查询打印机已打印里程
	 *
	 * @return void
	 */
	public void addQueryPrinterLife() {
		String str = "~!@" + "\r\n";
		addStrToCommand(str);
	}

	/**
	 * 方法说明:查询打印机内存
	 *
	 * @return void
	 */
	public void addQueryPrinterMemory() {
		String str = "~!A" + "\r\n";
		addStrToCommand(str);
	}

	/**
	 * 方法说明:查询打印机存储文档
	 *
	 * @return void
	 */
	public void addQueryPrinterFile() {
		String str = "~!F" + "\r\n";
		addStrToCommand(str);
	}

	/**
	 * 方法说明:查询打印机代码页
	 *
	 * @return void
	 */
	public void addQueryPrinterCodePage() {
		String str = "~!I" + "\r\n";
		addStrToCommand(str);
	}

	/**
	 * 方法说明:设置打印机剥离模式
	 *
	 * @param enable
	 *            是否开启
	 * @return void
	 */
	public void addPeel(ENABLE enable) {
		if (enable.getValue() == 0) {
			String str = "SET PEEL " + enable.getValue() + "\r\n";
			addStrToCommand(str);
		}
	}

	/**
	 * 方法说明:设置打印机撕离模式
	 *
	 * @param enable
	 *            是否开启
	 * @return void
	 */
	public void addTear(ENABLE enable) {
		String str = "SET TEAR " + enable.getValue() + "\r\n";
		addStrToCommand(str);
	}

	/**
	 * 方法说明:设置切刀是否有效
	 *
	 * @param enable
	 *            是否开启
	 * @return void
	 */
	public void addCutter(ENABLE enable) {
		String str = "SET CUTTER " + enable.getValue() + "\r\n";
		addStrToCommand(str);
	}

	/**
	 * 打印到最后一张切纸
	 */
	public void addCutterBatch() {
		String str = "SET CUTTER BATCH\r\n";
		addStrToCommand(str);
	}

	/**
	 * 设置打印几张后切纸,如果调用addPrint的打印份数小于number，则不会进行切纸
	 *
	 * @param number
	 *            打印几张纸后切纸 0 <= number <= 65535
	 */
	public void addCutterPieces(short number) {
		String str = "SET CUTTER " + number + "\r\n";
		addStrToCommand(str);
	}

	/**
	 * 方法说明:设置出现错误时是否打印上一张内容
	 *
	 * @param enable
	 *            是否开启
	 * @return void
	 */
	public void addReprint(ENABLE enable) {
		String str = "SET REPRINT " + enable.getValue() + "\r\n";
		addStrToCommand(str);
	}

	/**
	 * 方法说明:设置是否按键打印有效
	 *
	 * @param enable
	 *            是否开启
	 * @return void
	 */
	public void addPrintKey(ENABLE enable) {
		String str = "SET PRINTKEY " + enable.getValue() + "\r\n";
		addStrToCommand(str);
	}

	/**
	 * 方法说明:设置按键打印份数
	 *
	 * @param enable
	 *            是否开启
	 * @return void
	 */
	public void addPrintKey(int m) {
		String str = "SET PRINTKEY " + m + "\r\n";
		addStrToCommand(str);
	}

	/**
	 * 方法说明:设置切刀半切是否有效
	 *
	 * @param enable
	 *            是否开启
	 * @return void
	 */
	public void addPartialCutter(ENABLE enable) {
		String str = "SET PARTIAL_CUTTER " + enable.getValue() + "\r\n";

		addStrToCommand(str);
	}

	/**
	 * 此方法一般在一张订单的最后加入， 打印机打印完成时， 会接收到一条GpCom.ACTION_DEVICE_STATUS广播，
	 * 可以在此广播里发送下一条订单给打印机
	 * 
	 * RESPONSE_MODE.ON 打开自动返回状态功能,每打印一张返回一次 
	 * RESPONSE_MODE.OFF 关闭自动返回状态功能 
	 * RESPONSE_MODE.BATCH 打开自动返回状态功能,打印完毕后返回一次
	 */
	public void addQueryPrinterStatus(RESPONSE_MODE mode) {
		String str = "SET RESPONSE " + mode.getValue() + "\r\n";
		addStrToCommand(str);
	}

	public void addUserCommand(String command) {
		addStrToCommand(command);
	}
}
