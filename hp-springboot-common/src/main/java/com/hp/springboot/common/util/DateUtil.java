package com.hp.springboot.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 描述：日期时间转换工具类
 * 作者：黄平
 * 时间：2020-12-23
 */
public class DateUtil {

	private static Logger log = LoggerFactory.getLogger(DateUtil.class);
	
	/**
	   *  年-月-日
	 */
	public static final String DATE_FORMAT = "yyyy-MM-dd";
	
	/**
	   *  时:分:秒
	 */
	public static final String TIME_FORMAT = "HH:mm:ss";

	/**
	   *  完整格式的时间（年-月-日 时:分:秒）
	 */
	public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

	/**
	 * 一天的秒数
	 */
	public static final int ONE_DAY_SECONDS = 86400;
	
	/**
	* @Title: getCurrentTimeMilliSeconds  
	* @Description: 获取当前时间毫秒数
	* @return
	 */
	public static long getCurrentTimeMilliSeconds() {
		return System.currentTimeMillis();
	}
	
	/**
	* @Title: getCurrentTimeSeconds  
	* @Description: 获取当前时间秒数
	* @return
	 */
	public static int getCurrentTimeSeconds() {
		long longTime = getCurrentTimeMilliSeconds();
		return (int) (longTime / 1000);
	}
	
	/**
	 * @Title: getCurrentDateTime
	 * @Description: 获取当前日期
	 * @return
	 */
	public static Date getCurrentDateTime() {
		return new Date();
	}
	
	/**
	 * @Title: getCurrentDateString
	 * @Description: 获取当前日期的String格式
	 * @return
	 */
	public static String getCurrentDateString() {
		return getCurrentString(DATE_FORMAT);
	}
	
	/**
	 * @Title: getCurrentDateTimeString
	 * @Description: 获取当前日期时间的String格式
	 * @return
	 */
	public static String getCurrentDateTimeString() {
		return getCurrentString(DATE_TIME_FORMAT);
	}
	
	/**
	 * @Title: getCurrentTimeString
	 * @Description: 获取当前时间的String格式
	 * @return
	 */
	public static String getCurrentTimeString() {
		return getCurrentString(TIME_FORMAT);
	}
	
	/**
	 * @Title: getCurrentString
	 * @Description: 取当前时间的任意格式
	 * @param format
	 * @return
	 */
	public static String getCurrentString(String... format) {
		return intToString(getCurrentTimeSeconds(), getDateFormatter(format));
	}
	
	/**
	 * @Title: intToDate
	 * @Description: int类型，转为Date类型
	 * @param time
	 * @return
	 */
	public static Date intToDate(Integer time) {
		if (NumberUtil.isNullOrZero(time)) {
			return null;
		}
		return longToDate(time.longValue() * 1000);
	}

	/**
	 * @Title: longToDate
	 * @Description: long类型，转为date
	 * @param time
	 * @return
	 */
	public static Date longToDate(Long time) {
		if (NumberUtil.isNullOrZero(time)) {
			return null;
		}
		return new Date(time);
	}
	
	/**
	 * @Title: intToString
	 * @Description: Integer格式的时间，转为string格式
	 * @param time
	 * @param format
	 * @return
	 */
	public static String intToString(Integer time, String...format) {
		if (NumberUtil.isNullOrZero(time)) {
			return StringUtils.EMPTY;
		}
		return longToString(time.longValue() * 1000, format);
	}
	
	/**
	 * @Title: longToString
	 * @Description: long格式的时间，转为string格式
	 * @param time
	 * @param format
	 * @return
	 */
	public static String longToString(Long time, String...format) {
		if (NumberUtil.isNullOrZero(time)) {
			return StringUtils.EMPTY;
		}
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(getDateFormatter(format));
		return formatter.format(LocalDateTime.ofInstant(Instant.ofEpochMilli(time), ZoneId.systemDefault()));
	}
	
	/**
	 * @Title: dateToString
	 * @Description: Date格式的时间，转为string格式
	 * @param date
	 * @param format
	 * @return
	 */
	public static String dateToString(Date date, String... format) {
		if (date == null) {
			return StringUtils.EMPTY;
		}
		
		return longToString(date.getTime(), format);
	}
	
	/**
	 * @Title: addYear
	 * @Description: 年加减操作
	 * @param time
	 * @param offset
	 * @param format
	 * @return
	 */
	public static String addYear(String time, int offset, String... format) {
		if (StringUtils.isEmpty(time)) {
			return StringUtils.EMPTY;
		}
		
		Date date = DateUtils.addYears(stringToDate(time, format), offset);
		return dateToString(date, format);
	}
	
	/**
	 * @Title: addMonths
	 * @Description: 月份加减操作
	 * @param time
	 * @param offset
	 * @param format
	 * @return
	 */
	public static String addMonths(String time, int offset, String... format) {
		if (StringUtils.isEmpty(time)) {
			return StringUtils.EMPTY;
		}
		
		Date date = DateUtils.addMonths(stringToDate(time, format), offset);
		return dateToString(date, format);
	}
	
	/**
	 * @Title: addWeeks
	 * @Description: 星期加减操作
	 * @param time
	 * @param offset
	 * @param format
	 * @return
	 */
	public static String addWeeks(String time, int offset, String... format) {
		if (StringUtils.isEmpty(time)) {
			return StringUtils.EMPTY;
		}
		
		Date date = DateUtils.addWeeks(stringToDate(time, format), offset);
		return dateToString(date, format);
	}
	
	/**
	 * @Title: addDays
	 * @Description: 天加减操作
	 * @param time
	 * @param offset
	 * @param format
	 * @return
	 */
	public static String addDays(String time, int offset, String... format) {
		if (StringUtils.isEmpty(time)) {
			return StringUtils.EMPTY;
		}
		
		Date date = DateUtils.addDays(stringToDate(time, format), offset);
		return dateToString(date, format);
	}
	
	/**
	 * @Title: addHours
	 * @Description: 小时加减操作
	 * @param time
	 * @param offset
	 * @param format
	 * @return
	 */
	public static String addHours(String time, int offset, String... format) {
		if (StringUtils.isEmpty(time)) {
			return StringUtils.EMPTY;
		}
		
		Date date = DateUtils.addHours(stringToDate(time, format), offset);
		return dateToString(date, format);
	}
	
	/**
	 * @Title: addMinutes
	 * @Description: 分钟加减操作
	 * @param time
	 * @param offset
	 * @param format
	 * @return
	 */
	public static String addMinutes(String time, int offset, String... format) {
		if (StringUtils.isEmpty(time)) {
			return StringUtils.EMPTY;
		}
		
		Date date = DateUtils.addMinutes(stringToDate(time, format), offset);
		return dateToString(date, format);
	}
	
	/**
	 * @Title: addSeconds
	 * @Description: 秒加减操作
	 * @param time
	 * @param offset
	 * @param format
	 * @return
	 */
	public static String addSeconds(String time, int offset, String... format) {
		if (StringUtils.isEmpty(time)) {
			return StringUtils.EMPTY;
		}
		
		Date date = DateUtils.addSeconds(stringToDate(time, format), offset);
		return dateToString(date, format);
	}
	
	/**
	 * @Title: addMilliseconds
	 * @Description: 毫秒加减操作
	 * @param time
	 * @param offset
	 * @param format
	 * @return
	 */
	public static String addMilliseconds(String time, int offset, String... format) {
		if (StringUtils.isEmpty(time)) {
			return StringUtils.EMPTY;
		}
		
		Date date = DateUtils.addMilliseconds(stringToDate(time, format), offset);
		return dateToString(date, format);
	}
	
	/**
	 * @Title: stringToDate
	 * @Description: 字符串格式的转为Date类型
	 * @param time
	 * @param format
	 * @return
	 */
	public static Date stringToDate(String time, String... format) {
		if (StringUtils.isEmpty(time) || "0000-00-00".equals(time)) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(getDateFormatter(format));
		Date date = null;
		try {
			date = sdf.parse(time);
		} catch (ParseException e) {
			log.error("stringToDate error. with time={}, format={}", time, format);
		}
		return date;

	}
	
	/**
	 * @Title: getDateFormatter
	 * @Description: 获取时间格式化格式
	 * @param format
	 * @return
	 */
	private static String getDateFormatter(String... format) {
		return ArrayUtils.isEmpty(format) ? DATE_TIME_FORMAT : format[0];
	}
}
