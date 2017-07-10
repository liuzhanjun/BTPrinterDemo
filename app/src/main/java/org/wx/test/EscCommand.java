package org.wx.test;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.wx.test.LabelCommand.FOOT;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.util.Log;

/**
 * EscCommand.java
 *
 * @author Mr.阿拉丁
 * @data 2014-11-7
 * @Company Gprinter
 */
public class EscCommand {
	private final static String DEBUG_TAG = "EscCommand";
	Vector<Byte> Command = null;

	public static enum STATUS {
		PRINTER_STATUS(1), PRINTER_OFFLINE(2), PRINTER_ERROR(3), PRINTER_PAPER(4);
		private final int value;

		private STATUS(int value) {
			this.value = value;
		}

		public byte getValue() {
			return (byte) this.value;
		}
	}

	public static enum ENABLE {
		OFF(0), ON(1);
		private final int value;

		private ENABLE(int value) {
			this.value = value;
		}

		public byte getValue() {
			return (byte) this.value;
		}
	}

	public static enum UNDERLINE_MODE {
		OFF(0), UNDERLINE_1DOT(1), UNDERLINE_2DOT(2);
		private final int value;

		private UNDERLINE_MODE(int value) {
			this.value = value;
		}

		public byte getValue() {
			return (byte) this.value;
		}
	}

	public static enum FONT {
		FONTA(0), FONTB(1);
		private final int value;

		private FONT(int value) {
			this.value = value;
		}

		public byte getValue() {
			return (byte) this.value;
		}
	}

	public static enum CHARACTER_SET {
		USA(0), FRANCE(1), GERMANY(2), UK(3), DENMARK_I(4), SWEDEN(5), ITALY(6), SPAIN_I(7), JAPAN(8), NORWAY(
				9), DENMARK_II(10), SPAIN_II(11), LATIN_AMERCIA(12), KOREAN(13), SLOVENIA(14), CHINA(15);
		private final int value;

		private CHARACTER_SET(int value) {
			this.value = value;
		}

		public byte getValue() {
			return (byte) this.value;
		}
	}

	public static enum JUSTIFICATION {
		LEFT(0), CENTER(1), RIGHT(2);
		private final int value;

		private JUSTIFICATION(int value) {
			this.value = value;
		}

		public byte getValue() {
			return (byte) this.value;
		}
	}

	public static enum CODEPAGE {
		PC437(0), KATAKANA(1), PC850(2), PC860(3), PC863(4), PC865(5), WEST_EUROPE(6), GREEK(7), HEBREW(8), EAST_EUROPE(
				9), IRAN(10), WPC1252(16), PC866(17), PC852(18), PC858(19), IRANII(20), LATVIAN(21), ARABIC(22), PT151(
						23), PC747(24), WPC1257(25), VIETNAM(27), PC864(28), PC1001(29), UYGUR(30), THAI(255),;
		private final int value;

		private CODEPAGE(int value) {
			this.value = value;
		}

		public byte getValue() {
			return (byte) this.value;
		}
	}

	public static enum WIDTH_ZOOM {
		MUL_1(0x00), MUL_2(0x10), MUL_3(0x20), MUL_4(0x30), MUL_5(0x40), MUL_6(0x50), MUL_7(0x60), MUL_8(0x70);
		private final int value;

		private WIDTH_ZOOM(int value) {
			this.value = value;
		}

		public byte getValue() {
			return (byte) this.value;
		}
	}

	public static enum HEIGHT_ZOOM {
		MUL_1(0x00), MUL_2(0x01), MUL_3(0x02), MUL_4(0x03), MUL_5(0x4), MUL_6(0x5), MUL_7(0x6), MUL_8(0x7);
		private final int value;

		private HEIGHT_ZOOM(int value) {
			this.value = value;
		}

		public byte getValue() {
			return (byte) this.value;
		}
	}

	public static enum HRI_POSITION {
		NO_PRINT(0), ABOVE(1), BELOW(2), ABOVE_AND_BELOW(3);
		private final int value;

		private HRI_POSITION(int value) {
			this.value = value;
		}

		public byte getValue() {
			return (byte) this.value;
		}
	}

	public EscCommand() {
		Command = new Vector<Byte>(4096, 1024);
	}

	/**
	 * 方法说明：将字符串转成十六进制码
	 *
	 * @param str
	 *            命令字符串
	 * @return void
	 */
	private void addArrayToCommand(byte[] array) {
		for (int i = 0; i < array.length; i++) {
			Command.add(array[i]);
		}
	}

	/**
	 * 方法说明：将字符串转成十六进制码
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

	private void addStrToCommand(String str, String charset) {
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

	private void addStrToCommandUTF8Encoding(String str, int length) {
		byte[] bs = null;
		if (!str.equals("")) {
			try {
				bs = str.getBytes("UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Log.d(DEBUG_TAG, "bs.length" + bs.length);
			if (length > bs.length)
				length = bs.length;
			Log.d(DEBUG_TAG, "length" + length);
			for (int i = 0; i < length; i++) {
				Command.add(bs[i]);
			}
		}
	}

	private void addStrToCommand(String str, int length) {
		byte[] bs = null;
		if (!str.equals("")) {
			try {
				bs = str.getBytes("GB2312");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Log.d(DEBUG_TAG, "bs.length" + bs.length);
			if (length > bs.length)
				length = bs.length;
			Log.d(DEBUG_TAG, "length" + length);
			for (int i = 0; i < length; i++) {
				Command.add(bs[i]);
			}
		}
	}

	/**
	 * 方法说明：插入水平跳格
	 */
	public void addHorTab() {
		byte command[] = { 0x09 };
		addArrayToCommand(command);
	}

	/**
	 * 方法说明：插入文字
	 *
	 * @param text
	 */
	public void addText(String text) {
		addStrToCommand(text);
	}

	/**
	 * 方法说明：插入文字
	 *
	 * @param text
	 */
	public void addText(String text, String charsetName) {
		addStrToCommand(text, charsetName);
	}

	/**
	 * 方法说明：插入阿拉伯文字
	 *
	 * @param text
	 */
	public void addArabicText(String text) {
		text = GpUtils.reverseLetterAndNumber(text);
		text = GpUtils.splitArabic(text);
		String[] fooInput = text.split("\\n");
		for (String in : fooInput) {
			byte[] output;
			output = GpUtils.string2Cp864(in);
			for (int i = 0; i < output.length; i++) {
				if (output[i] == (byte) 0xF0) {
					addArrayToCommand(new byte[] { 0x1b, 0x74, 0x1d, (byte) 0x84, 0x1b, 0x74, 0x16 });
				} else if (output[i] == 127) {
					Command.add((byte) 0xd7);
				} else {
					Command.add(output[i]);
				}
			}
		}
	}

	/**
	 * 方法说明：进纸一行
	 */
	public void addPrintAndLineFeed() {
		byte command[] = { 0x0a };
		addArrayToCommand(command);
	}

	/**
	 * 方法说明：打印机实时状态请求
	 *
	 * @param status
	 *            PRINTER_STATUS 打印机状态 PRINTER_OFFLINE 脱机状态 PRINTER_ERROR 错误状态
	 *            PRINTER_PAPER 纸张状态
	 */
	public void RealtimeStatusTransmission(STATUS status) {
		byte command[] = { 0x10, 0x04, 0 };
		command[2] = status.getValue();
		addArrayToCommand(command);
	}

	/**
	 * 方法说明：立即开启钱箱
	 *
	 * @param foot
	 *            钱箱 2脚 F2 钱箱 5脚 F5
	 * @param t
	 *            脉冲时间 tx100ms
	 */
	public void addGeneratePluseAtRealtime(FOOT foot, byte t) {
		byte command[] = { 0x10, 0x14, 1, 0, 0 };
		command[3] = (byte) foot.getValue();
		if (t > 8)
			t = 8;
		command[4] = t;
		addArrayToCommand(command);
	}

	/**
	 * 蜂鸣器
	 * 
	 * @param n
	 *            n是指蜂鸣器鸣叫次数。
	 * @param t
	 *            t 是指蜂鸣器鸣每次数鸣叫时间为(t × 50)毫秒
	 */
	public void addSound(byte n, byte t) {
		byte command[] = { 0x1b, 0x42, 0, 0 };
		if (n < 0) {
			n = 1;
		} else if (n > 9) {
			n = 9;
		}
		if (t < 0) {
			t = 1;
		} else if (t > 9) {
			t = 9;
		}

		command[2] = n;
		command[3] = t;
		addArrayToCommand(command);
	}

	/**
	 * 方法说明：设置字符右间距
	 *
	 * @param n
	 *            间距长度
	 */
	public void addSetRightSideCharacterSpacing(byte n) {
		byte command[] = { 0x1b, 0x20, 0 };
		command[2] = n;
		addArrayToCommand(command);
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
	 * 方法说明：设置打印模式
	 *
	 * @param font
	 *            选择FONTA or FONTB
	 * @param emphasized
	 *            是否加粗
	 * @param doubleheight
	 *            是否倍高
	 * @param doublewidth
	 *            是否倍宽
	 * @param underline
	 *            是否下划线
	 */
	public void addSelectPrintModes(FONT font, ENABLE emphasized, ENABLE doubleheight, ENABLE doublewidth,
			ENABLE underline) {
		byte temp = 0;
		if (font == FONT.FONTB) {
			temp = 0x01;
		}
		if (emphasized == ENABLE.ON) {
			temp |= 0x08;
		}
		if (doubleheight == ENABLE.ON) {
			temp |= 0x10;
		}
		if (doublewidth == ENABLE.ON) {
			temp |= 0x20;
		}
		if (underline == ENABLE.ON) {
			temp |= 0x80;
		}
		byte command[] = { 0x1b, 0x21, 0 };
		command[2] = temp;
		addArrayToCommand(command);
	}

	/**
	 * 方法说明：设置绝对打印位置
	 *
	 * @param n
	 *            与起始打印位置距离
	 */
	public void addSetAbsolutePrintPosition(short n) {
		byte command[] = { 0x1b, 0x24, 0, 0 };
		byte nl = (byte) (n % 256);
		byte nh = (byte) (n / 256);
		command[2] = nl;
		command[3] = nh;
		addArrayToCommand(command);
	}

	/**
	 * 方法说明：设置是否取消自定义字符
	 *
	 * @param enable
	 *            是否取消
	 */
	public void addSelectOrCancelUserDefineCharacter(ENABLE enable) {
		byte command[] = { 0x1b, 0x25, 0 };
		if (enable == ENABLE.ON)
			command[2] = 1;
		else
			command[2] = 0;
		addArrayToCommand(command);
	}

	/**
	 * 方法说明：设置下划线
	 *
	 * @param underline
	 *            下划线类型
	 */
	public void addTurnUnderlineModeOnOrOff(UNDERLINE_MODE underline) {
		byte command[] = { 0x1b, 0x2D, 0 };
		command[2] = underline.getValue();
		addArrayToCommand(command);
	}

	/**
	 * 方法说明：设置为默认行间距
	 */
	public void addSelectDefualtLineSpacing() {
		byte command[] = { 0x1b, 0x32 };
		addArrayToCommand(command);
	}

	/**
	 * 方法说明：设置行间距
	 *
	 * @param n
	 *            行间距高度，包含文字
	 */
	public void addSetLineSpacing(byte n) {
		byte command[] = { 0x1b, 0x33, 0 };
		command[2] = n;
		addArrayToCommand(command);
	}

	/**
	 * 方法说明：设置取消自定义字符
	 *
	 * @param n
	 *            字符编号
	 */
	public void addCancelUserDefinedCharacters(byte n) {
		byte command[] = { 0x1b, 0x3F, 0 };
		if (n >= 32 && n <= 126) {
			command[2] = n;
		} else
			command[2] = 32;
		addArrayToCommand(command);
	}

	/**
	 * 方法说明：打印机初始化
	 */
	public void addInitializePrinter() {
		byte command[] = { 0x1b, 0x40 };
		addArrayToCommand(command);
	}

	/**
	 * 方法说明：设置加粗模式
	 *
	 * @param enabel
	 *            是否加粗
	 */
	public void addTurnEmphasizedModeOnOrOff(ENABLE enabel) {
		byte command[] = { 0x1b, 0x45, 0 };
		command[2] = enabel.getValue();
		addArrayToCommand(command);
	}

	/**
	 * 方法说明：设置加重模式
	 *
	 * @param enabel
	 *            是否加重
	 */
	public void addTurnDoubleStrikeOnOrOff(ENABLE enabel) {
		byte command[] = { 0x1b, 0x47, 0 };
		command[2] = enabel.getValue();
		addArrayToCommand(command);
	}

	/**
	 * 方法说明：设置打印后走纸距离
	 *
	 * @param dot
	 *            走纸长度
	 */
	public void addPrintAndFeedPaper(byte n) {
		byte command[] = { 0x1b, 0x4A, 0 };
		command[2] = n;
		addArrayToCommand(command);
	}

	/**
	 * 方法说明：设置字符类型
	 *
	 * @param font
	 *            FONTA or FONTB
	 */
	public void addSelectCharacterFont(FONT font) {
		byte command[] = { 0x1b, 0x4D, 0 };
		command[2] = font.getValue();
		addArrayToCommand(command);
	}

	/**
	 * 方法说明：设置国际字符集
	 *
	 * @param set
	 *            字符集编号
	 */
	public void addSelectInternationalCharacterSet(CHARACTER_SET set) {
		byte command[] = { 0x1b, 0x52, 0 };
		command[2] = set.getValue();
		addArrayToCommand(command);
	}

	/**
	 * 方法说明：设置字符是否旋转90°
	 *
	 * @param enabel
	 *            是否旋转
	 */
	public void addTurn90ClockWiseRotatin(ENABLE enabel) {
		byte command[] = { 0x1b, 0x56, 0 };
		command[2] = enabel.getValue();
		addArrayToCommand(command);
	}

	/**
	 * 方法说明：设置相对打印位置
	 *
	 * @param n
	 *            与上一字符的相对位置
	 */
	public void addSetRelativePrintPositon(short n) {
		byte command[] = { 0x1b, 0x5C, 0, 0 };
		byte nl = (byte) (n % 256);
		byte nh = (byte) (n / 256);
		command[2] = nl;
		command[3] = nh;
		addArrayToCommand(command);
	}

	/**
	 * 方法说明：设置对齐方式
	 *
	 * @param just
	 *            左 中 右对齐
	 */
	public void addSelectJustification(JUSTIFICATION just) {
		byte command[] = { 0x1b, 0x61, 0 };
		command[2] = just.getValue();
		addArrayToCommand(command);
	}

	/**
	 * 方法说明：打印并且走纸多少行
	 *
	 * @param n
	 *            行数
	 */
	public void addPrintAndFeedLines(byte n) {
		byte command[] = { 0x1b, 0x64, 0 };
		command[2] = n;
		addArrayToCommand(command);
	}

	/**
	 * 方法说明：开钱箱
	 *
	 * @param foot
	 *            钱箱引脚号
	 * @param t1
	 *            高电平时间
	 * @param t2
	 *            低电平时间
	 */
	public void addGeneratePlus(FOOT foot, byte t1, byte t2) {
		byte command[] = { 0x1b, 0x70, 0, 0, 0 };
		command[2] = (byte) foot.getValue();
		command[3] = t1;
		command[4] = t2;
		addArrayToCommand(command);
	}

	/**
	 * 方法说明：设置代码页
	 *
	 * @param page
	 *            代码页编号
	 */
	public void addSelectCodePage(CODEPAGE page) {
		byte command[] = { 0x1b, 0x74, 0, };
		command[2] = (byte) page.getValue();
		addArrayToCommand(command);
	}

	/**
	 * 方法说明：设置倒置模式
	 *
	 * @param enable
	 *            是否倒置
	 */
	public void addTurnUpsideDownModeOnOrOff(ENABLE enable) {
		byte command[] = { 0x1b, 0x7B, 0, };
		command[2] = (byte) enable.getValue();
		addArrayToCommand(command);
	}

	/**
	 * 方法说明：设置字符放大
	 *
	 * @param width
	 *            宽度放大倍数
	 * @param height
	 *            高度放大倍数
	 */
	public void addSetCharcterSize(WIDTH_ZOOM width, HEIGHT_ZOOM height) {
		byte command[] = { 0x1d, 0x21, 0, };
		byte temp = 0;
		temp |= width.getValue();
		temp |= height.getValue();
		command[2] = temp;
		addArrayToCommand(command);
	}

	/**
	 * 方法说明：设置反白模式
	 *
	 * @param enable
	 *            是否反白
	 */
	public void addTurnReverseModeOnOrOff(ENABLE enable) {
		byte command[] = { 0x1d, 0x42, 0, };
		command[2] = enable.getValue();
		addArrayToCommand(command);
	}

	/**
	 * 方法说明：设置条码可识别字符
	 *
	 * @param position
	 *            可识别字符位置
	 */
	public void addSelectPrintingPositionForHRICharacters(HRI_POSITION position) {
		byte command[] = { 0x1d, 0x48, 0, };
		command[2] = position.getValue();
		addArrayToCommand(command);
	}

	/**
	 * 方法说明：设置打印左间距
	 *
	 * @param n
	 *            左间距
	 */
	public void addSetLeftMargin(short n) {
		byte command[] = { 0x1d, 0x4C, 0, 0 };
		byte nl = (byte) (n % 256);
		byte nh = (byte) (n / 256);
		command[2] = nl;
		command[3] = nh;
		addArrayToCommand(command);
	}

	/**
	 * 方法说明：设置水平和垂直单位距离
	 *
	 * @param x
	 *            水平单位
	 * @param y
	 *            垂直单位
	 */
	public void addSetHorAndVerMotionUnits(byte x, byte y) {
		byte command[] = { 0x1d, 0x50, 0, 0 };
		command[2] = x;
		command[3] = y;
		addArrayToCommand(command);
	}

	/**
	 * 方法说明：设置切纸后走纸
	 *
	 * @param length
	 *            走纸距离
	 */
	public void addCutAndFeedPaper(byte length) {
		byte command[] = { 0x1d, 0x56, 0x42, 0 };
		command[3] = length;
		addArrayToCommand(command);
	}

	/**
	 * 方法说明：切纸
	 */
	public void addCutPaper() {
		byte command[] = { 0x1d, 0x56, 0x01 };
		addArrayToCommand(command);
	}

	/**
	 * 方法说明：设置打印宽度
	 *
	 * @param width
	 *            打印宽度
	 */
	public void addSetPrintingAreaWidth(short width) {
		byte nl = (byte) (width % 256);
		byte nh = (byte) (width / 256);
		byte command[] = { 0x1d, 0x57, 0, 0 };
		command[2] = nl;
		command[3] = nh;
		addArrayToCommand(command);
	}

	/**
	 * 方法说明：设置打印机是否自动返回状态
	 *
	 * @param enable
	 *            是否返回
	 */
	public void addSetAutoSatusBack(ENABLE enable) {
		byte command[] = { 0x1d, 0x61, 0 };
		if (enable == ENABLE.OFF) {
			command[2] = 0x00;
		} else
			command[2] = (byte) 0xff;
		addArrayToCommand(command);
	}

	/**
	 * 方法说明：设置条码字符种类
	 *
	 * @param font
	 *            FONTA or FONTB
	 */
	public void addSetFontForHRICharacter(FONT font) {
		byte command[] = { 0x1d, 0x66, 0 };
		command[2] = font.getValue();
		addArrayToCommand(command);
	}

	/**
	 * 方法说明：设置条码高度
	 *
	 * @param height
	 *            高度
	 */
	public void addSetBarcodeHeight(byte height) {
		byte command[] = { 0x1d, 0x68, 0 };
		command[2] = height;
		addArrayToCommand(command);
	}

	/**
	 * 方法说明：设置条码单元宽度
	 *
	 * @param width
	 *            条码宽度
	 */
	public void addSetBarcodeWidth(byte width) {
		byte command[] = { 0x1d, 0x77, 0 };
		if (width > 6)
			width = 6;
		if (width < 2)
			width = 1;
		command[2] = width;
		addArrayToCommand(command);
	}

	/**
	 * 方法说明：设置是否汉字字体
	 *
	 * @param DoubleWidth
	 *            是否倍宽
	 * @param DoubleHeight
	 *            是否倍高
	 * @param Underline
	 *            是否下划线
	 */
	public void addSetKanjiFontMode(ENABLE DoubleWidth, ENABLE DoubleHeight, ENABLE Underline) {
		byte command[] = { 0x1C, 0x21, 0 };
		byte temp = 0;
		if (DoubleWidth == ENABLE.ON)
			temp |= 0x04;
		if (DoubleHeight == ENABLE.ON)
			temp |= 0x08;
		if (Underline == ENABLE.ON)
			temp |= 0x80;
		command[2] = temp;
		addArrayToCommand(command);
	}

	/**
	 * 方法说明：设置汉字有效
	 */
	public void addSelectKanjiMode() {
		byte command[] = { 0x1C, 0x26 };
		addArrayToCommand(command);
	}

	/**
	 * 方法说明：设置汉字下划线
	 *
	 * @param underline
	 */
	public void addSetKanjiUnderLine(UNDERLINE_MODE underline) {
		byte command[] = { 0x1C, 0x2d, 0 };
		command[3] = underline.getValue();
		addArrayToCommand(command);
	}

	/**
	 * 方法说明：设置汉字无效
	 */
	public void addCancelKanjiMode() {
		byte command[] = { 0x1C, 0x2E };
		addArrayToCommand(command);
	}

	/**
	 * 方法说明：设置汉字左右间距
	 *
	 * @param left
	 *            左间距
	 * @param right
	 *            右间距
	 */
	public void addSetKanjiLefttandRightSpace(byte left, byte right) {
		byte command[] = { 0x1C, 0x53, 0, 0 };
		command[2] = left;
		command[3] = right;
		addArrayToCommand(command);
	}

	/**
	 * 方法说明：设置汉字倍宽倍高
	 *
	 * @param enable
	 */
	public void addSetQuadrupleModeForKanji(ENABLE enable) {
		byte command[] = { 0x1C, 0x57, 0 };
		command[2] = enable.getValue();
		addArrayToCommand(command);
	}

	/**
	 * 方法说明： 打印光栅位图
	 *
	 * @param bmp
	 *            位图数据
	 */

	public void addRastBitImage(Bitmap bitmap, int nWidth, int nMode) {
		if (bitmap != null) {
			int width = (nWidth + 7) / 8 * 8;
			int height = bitmap.getHeight() * width / bitmap.getWidth();
			Bitmap grayBitmap = GpUtils.toGrayscale(bitmap);
			Bitmap rszBitmap = GpUtils.resizeImage(grayBitmap, width, height);
			byte[] src = GpUtils.bitmapToBWPix(rszBitmap);
			byte command[] = new byte[8];
			height = src.length / width;
			command[0] = 29;
			command[1] = 118;
			command[2] = 48;
			command[3] = (byte) (nMode & 0x1);
			command[4] = (byte) (width / 8 % 256);
			command[5] = (byte) (width / 8 / 256);
			command[6] = (byte) (height % 256);
			command[7] = (byte) (height / 256);
			addArrayToCommand(command);
			byte[] codecontent = GpUtils.pixToEscRastBitImageCmd(src);
			for (int k = 0; k < codecontent.length; k++) {
				Command.add(codecontent[k]);
			}
		} else {
			Log.d("BMP", "bmp.  null ");
		}
	}

	public void addOriginRastBitImage(Bitmap bitmap, int nWidth, int nMode) {
		if (bitmap != null) {
			int width = (nWidth + 7) / 8 * 8;
			int height = bitmap.getHeight() * width / bitmap.getWidth();
			Bitmap rszBitmap = GpUtils.resizeImage(bitmap, width, height);
			byte[] data = GpUtils.printEscDraw(rszBitmap);
			addArrayToCommand(data);
		} else {
			Log.d("BMP", "bmp.  null ");
		}
	}

	/**
	 * 打印光栅位图 需要选择打印算法，可通过GpUtils.
	 * 
	 * @param bitmap
	 *            位图
	 * @param nWidth
	 *            宽度
	 * @param nMode
	 *            模式
	 * @param method
	 *            算法方法，GpUtils.FLOYD_STEINBERG_DITHER与GpUtils.ATKINSON_DITHER
	 */

	public void addRastBitImageWithMethod(Bitmap bitmap, int nWidth, int nMode, int method) {
		if (bitmap != null) {
			int width = (nWidth + 7) / 8 * 8;
			int height = bitmap.getHeight() * width / bitmap.getWidth();
			Bitmap resizeImage = GpUtils.resizeImage(bitmap, width, height);
			Bitmap rszBitmap = GpUtils.filter(resizeImage, resizeImage.getWidth(), resizeImage.getHeight());

			byte[] src = GpUtils.bitmapToBWPix(rszBitmap);
			byte command[] = new byte[8];
			height = src.length / width;
			command[0] = 29;
			command[1] = 118;
			command[2] = 48;
			command[3] = (byte) (nMode & 0x1);
			command[4] = (byte) (width / 8 % 256);
			command[5] = (byte) (width / 8 / 256);
			command[6] = (byte) (height % 256);
			command[7] = (byte) (height / 256);
			addArrayToCommand(command);
			byte[] codecontent = GpUtils.pixToEscRastBitImageCmd(src);
			for (int k = 0; k < codecontent.length; k++) {
				Command.add(codecontent[k]);
			}
		} else {
			Log.d("BMP", "bmp.  null ");
		}
	}

	/**
	 * 方法说明： 打印下载NV位图
	 *
	 * @param bmp
	 *            位图数据
	 */
	public void addDownloadNvBitImage(Bitmap[] bitmap) {
		if (bitmap != null) {
			Log.d("BMP", "bitmap.length " + bitmap.length);
			int n = bitmap.length;
			if (n > 0) {
				byte command[] = new byte[3];
				command[0] = 0x1c;
				command[1] = 0x71;
				command[2] = (byte) n;
				addArrayToCommand(command);
				for (int i = 0; i < n; i++) {
					int height = (bitmap[i].getHeight() + 7) / 8 * 8;
					int width = bitmap[i].getWidth() * height / bitmap[i].getHeight();
					Bitmap grayBitmap = GpUtils.toGrayscale(bitmap[i]);
					Bitmap rszBitmap = GpUtils.resizeImage(grayBitmap, width, height);
					byte[] src = GpUtils.bitmapToBWPix(rszBitmap);
					height = src.length / width;
					Log.d("BMP", "bmp  Width " + width);
					Log.d("BMP", "bmp  height " + height);
					byte[] codecontent = GpUtils.pixToEscNvBitImageCmd(src, width, height);
					for (int k = 0; k < codecontent.length; k++) {
						Command.add(codecontent[k]);
					}
				}
			}
		} else {
			Log.d("BMP", "bmp.  null ");
			return;
		}
	}

	public void addPrintNvBitmap(byte n, byte mode) {
		byte command[] = { 0x1C, 0x70, 0, 0 };
		command[2] = n;
		command[3] = mode;
		addArrayToCommand(command);
	}

	/**
	 * 方法说明：打印UPCA条码
	 *
	 * @param content
	 *            数据范围0-9，长度为11位
	 */
	public void addUPCA(String content) {
		byte command[] = new byte[4];
		command[0] = 0x1d;
		command[1] = 0x6b;
		command[2] = 65;
		command[3] = 11;
		if (content.length() < command[3])
			return;
		addArrayToCommand(command);
		addStrToCommand(content, 11);
	}

	/**
	 * 方法说明 打印UPCE条码
	 *
	 * @param content
	 *            数据范围0-9，长度为11位，必须以0开头
	 */
	public void addUPCE(String content) {
		byte command[] = new byte[4];
		command[0] = 0x1d;
		command[1] = 0x6b;
		command[2] = 66;
		command[3] = 11;
		if (content.length() < command[3])
			return;
		addArrayToCommand(command);
		addStrToCommand(content, command[3]);
	}

	/**
	 * 方法说明：打印EAN13码
	 *
	 * @param content
	 *            数据范围0-9，长度为12位
	 */
	public void addEAN13(String content) {
		byte command[] = new byte[4];
		command[0] = 0x1d;
		command[1] = 0x6b;
		command[2] = 67;
		command[3] = 12;
		if (content.length() < command[3])
			return;
		addArrayToCommand(command);
		Log.d(DEBUG_TAG, "content.length" + content.length());
		addStrToCommand(content, command[3]);
	}

	/**
	 * 方法说明：打印EAN8码
	 *
	 * @param content
	 *            数据范围0-9，长度为7位
	 */
	public void addEAN8(String content) {
		byte command[] = new byte[4];
		command[0] = 0x1d;
		command[1] = 0x6b;
		command[2] = 68;
		command[3] = 7;
		if (content.length() < command[3])
			return;
		addArrayToCommand(command);
		addStrToCommand(content, command[3]);
	}

	/**
	 * 方法说明:打印UPCE条码
	 *
	 * @param content
	 *            数据范围0-9 A-Z SP $ % + - . /
	 */
	@SuppressLint("DefaultLocale")
	public void addCODE39(String content) {
		byte command[] = new byte[4];
		command[0] = 0x1d;
		command[1] = 0x6b;
		command[2] = 69;
		command[3] = (byte) content.length();
		content = content.toUpperCase();
		addArrayToCommand(command);
		addStrToCommand(content, command[3]);
	}

	/**
	 * 方法说明:打印ITF条码
	 *
	 * @param content
	 *            数据范围 0-9 数据长度 偶数个
	 */
	public void addITF(String content) {
		byte command[] = new byte[4];
		command[0] = 0x1d;
		command[1] = 0x6b;
		command[2] = 70;
		command[3] = (byte) content.length();
		addArrayToCommand(command);
		addStrToCommand(content, command[3]);
	}

	/**
	 * 方法说明:打印CODABAR码
	 *
	 * @param content
	 *            数据范围 0-9 $ + - . / : 数据前后需插入A-D
	 */
	public void addCODABAR(String content) {
		byte command[] = new byte[4];
		command[0] = 0x1d;
		command[1] = 0x6b;
		command[2] = 71;
		command[3] = (byte) content.length();
		addArrayToCommand(command);
		addStrToCommand(content, command[3]);
	}

	/**
	 * 方法说明：打印CODE93码
	 *
	 * @param content
	 *            数据范围0x00-0x7f
	 */
	public void addCODE93(String content) {
		byte command[] = new byte[4];
		command[0] = 0x1d;
		command[1] = 0x6b;
		command[2] = 72;
		command[3] = (byte) content.length();
		addArrayToCommand(command);
		addStrToCommand(content, command[3]);
	}

	/**
	 * 方法说明：打印CODE128码
	 *
	 * @param content
	 *            数据范围0x00-0x7f
	 */
	public void addCODE128(String content) {
		byte command[] = new byte[4];
		command[0] = 0x1d;
		command[1] = 0x6b;
		command[2] = 73;
		command[3] = (byte) content.length();
		addArrayToCommand(command);
		addStrToCommand(content, command[3]);
	}

	public String genCodeC(String content) {
		List<Byte> bytes = new ArrayList<Byte>(20);
		int len = content.length();
		bytes.add((byte) '{');
		bytes.add((byte) 'C');
		for (int i = 0; i < len; i += 2) {
			int ken = (content.charAt(i) - '0') * 10;
			int bits = content.charAt(i + 1) - '0';
			int current = ken + bits;
			bytes.add((byte) current);
		}
		byte[] bb = new byte[bytes.size()];
		for (int i = 0; i < bb.length; i++) {
			bb[i] = bytes.get(i);
		}

		return new String(bb, 0, bb.length);
	}

	public String genCodeB(String content) {
		return String.format("{B%s", content);
	}

	public String genCode128(String content) {
		String regex = "([^0-9])";
		String[] str = content.split(regex);

		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(content);

		String splitString = null;
		int strlen = str.length;

		if (strlen > 0) {
			if (matcher.find()) {
				splitString = matcher.group(0);
			}
		}

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < strlen; i++) {
			String first = str[i];
			int len = first.length();
			int result = len % 2;
			if (result == 0) {
				String codeC = genCodeC(first);
				sb.append(codeC);
			} else {
				sb.append(genCodeB(String.valueOf(first.charAt(0))));
				sb.append(genCodeC(first.substring(1, first.length())));
			}
			if (splitString != null) {
				sb.append(genCodeB(splitString));
				splitString = null;
			}

		}
		return sb.toString();
	}

	/**
	 * 方法说明：设置QRCode单元模块大小
	 *
	 * @param n
	 */

	public void addSelectSizeOfModuleForQRCode(byte n) {
		byte command[] = { 0x1d, 0x28, 0x6b, 0x03, 0x00, 0x31, 0x43, 3 };
		command[7] = n;
		addArrayToCommand(command);
	}

	/**
	 * 方法说明：设置QRCode纠错级别
	 *
	 * @param
	 */
	public void addSelectErrorCorrectionLevelForQRCode(byte n) {
		byte command[] = { 0x1d, 0x28, 0x6b, 0x03, 0x00, 0x31, 0x45, 00 };
		command[7] = n;
		addArrayToCommand(command);
	}

	/**
	 * 方法说明：存入QRCode数据在打印机
	 *
	 * @param
	 */
	public void addStoreQRCodeData(String content) {
		byte command[] = { 0x1d, 0x28, 0x6b, 0, 0, 0x31, 0x50, 0x30 };
		command[3] = (byte) ((content.getBytes().length + 3) % 256);
		command[4] = (byte) ((content.getBytes().length + 3) / 256);
		addArrayToCommand(command);
		// addStrToCommand(content, content.length());
		byte[] bs = null;
		if (!content.equals("")) {
			try {
				bs = content.getBytes("utf-8");
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
	 * 方法说明：打印存储的QRCode
	 *
	 * @param
	 */
	public void addPrintQRCode() {
		byte command[] = { 0x1d, 0x28, 0x6b, 0x03, 0x00, 0x31, 0x51, 48 };
		addArrayToCommand(command);
	}

	/**
	 * 此方法一般在一张订单的最后加入， 打印机打印完成时， 会接收到一条GpCom.ACTION_DEVICE_STATUS广播，
	 * 可以在此广播里发送下一条订单给打印机
	 */
	public void addQueryPrinterStatus() {
		byte[] command = { 0x1d, 0x72, 0x01 };
		addArrayToCommand(command);
	}

	public void addUserCommand(byte[] command) {
		addArrayToCommand(command);
	}
}
