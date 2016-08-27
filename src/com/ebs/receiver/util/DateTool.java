package com.ebs.receiver.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
	/**    
	 * ʱ��ؼ�
	 * @author��RIK 
	 * @since��2012-7-13 ����09:20:46 
	 * @version:   
	 */ 
	@SuppressWarnings({"unused","unchecked","deprecation"})
	public class DateTool {
		private static ThreadLocal fmt = new ThreadLocal() {  
	        protected synchronized Object initialValue() {  
	            return new SimpleDateFormat("yyyy-MM-dd");  
	        }  
	    };  
		
	    private static ThreadLocal fmt2 = new ThreadLocal() {  
	        protected synchronized Object initialValue() {  
	            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
	        }  
	    };  
	    
	    private static ThreadLocal fmt3 = new ThreadLocal() {  
	        protected synchronized Object initialValue() {  
	            return new SimpleDateFormat("yyyyMMddHHmmss");  
	        }  
	    };  
	    
	    private static ThreadLocal fmt4 = new ThreadLocal() {  
	        protected synchronized Object initialValue() {  
	            return new SimpleDateFormat("HH:mm:ss");  
	        }  
	    };  
	    
	    private static ThreadLocal fmt5 = new ThreadLocal() {  
	        protected synchronized Object initialValue() {  
	            return new SimpleDateFormat("yyyyMMdd");  
	        }  
	    };  
	    

	    private static ThreadLocal fmt6 = new ThreadLocal() {  
	        protected synchronized Object initialValue() {  
	            return new SimpleDateFormat("HHmmsssss");  
	        }  
	    };  
	    
	    private static ThreadLocal fmt7 = new ThreadLocal() {  
	        protected synchronized Object initialValue() {  
	            return new SimpleDateFormat("yyyyMM");  
	        }  
	    }; 
	    
		private static ThreadLocal fmt8 = new ThreadLocal() {  
	        protected synchronized Object initialValue() {  
	            return new SimpleDateFormat("yyyy-MM-dd");  
	        }  
	    }; 
	    private static ThreadLocal fmt9 = new ThreadLocal() {  
	        protected synchronized Object initialValue() {  
	            return new SimpleDateFormat("MMddhhmmssSSS");  
	        }  
	    }; 
	    
	    private static ThreadLocal fmt10 = new ThreadLocal() {  
	        protected synchronized Object initialValue() {  
	            return new SimpleDateFormat("yyyy");  
	        }  
	    };
	    
	   
		/**    
		 * yyyy-MM-dd
		 * @return    
		*/
		public static String getToday() {
			Date dt = new java.util.Date();
			DateFormat sdf = (DateFormat) fmt.get();
			return sdf.format(dt);
		}
		
		/**    
		 * yyyy-MM-dd
		 * @param dt
		 * @return    
		*/
		public static String parseDate(Date dt) {
			DateFormat sdf = (DateFormat) fmt.get();
			return sdf.format(dt);
		}

		/**    
		 * yyyy-MM-dd HH:mm:ss
		 * @param dt
		 * @return    
		*/
		public static String parseDate2(Date dt) {
			DateFormat sdf = (DateFormat) fmt2.get();
			return sdf.format(dt);
		}

		/**    
		 * yyyyMMddHHmmss
		 * @param dt
		 * @return    
		*/
		public static String parseDate3(Date dt) {
			DateFormat sdf = (DateFormat) fmt3.get();
			return sdf.format(dt);
		}

		/**   
		 * HH:mm:ss
		 * @param dt
		 * @return    
		*/
		public static String parseDate4(Date dt) {
			DateFormat sdf = (DateFormat) fmt4.get();
			return sdf.format(dt);
		}
		
		/**    
		 * yyyyMMdd
		 * @param dt
		 * @return    
		*/
		public static String parseDate5(Date dt) {
			DateFormat sdf = (DateFormat) fmt5.get();
			return sdf.format(dt);
		}

		/**    
		 * HHmmsssss
		 * @param dt
		 * @return    
		*/
		public static String parseDate6(Date dt) {
			DateFormat sdf = (DateFormat) fmt6.get();
			return sdf.format(dt);
		}
		
		/**    
		 * yyyyMM
		 * @param dt
		 * @return    
		*/
		public static String parseDate7(Date dt) {
			DateFormat sdf = (DateFormat) fmt7.get();
			return sdf.format(dt);
		}
		

		/**  
		 * yyyy��MM��dd��  
		 * @param dt
		 * @return    
		*/
		public static String parseDate8(Date dt) {
			DateFormat sdf = (DateFormat) fmt8.get();
			return sdf.format(dt);
		}
		
		public static String parseDate9(Date dt) {
			DateFormat sdf = (DateFormat) fmt9.get();
			return sdf.format(dt);
		}

		 public static String parseDate10(Date dt) {
				DateFormat sdf = (DateFormat) fmt10.get();
				return sdf.format(dt);
			}
		    
		/**    
		 * yyyy-MM-dd
		 * @param ds
		 * @return
		 * @throws ParseException    
		*/
		public static Date strintToDate(String ds) throws ParseException {
			DateFormat sdf = (DateFormat) fmt.get();
			Date d = sdf.parse(ds);
			return d;
		}
		
		/**    
		 * yyyy-MM-dd HH:mm:ss
		 * @param ds
		 * @return
		 * @throws ParseException    
		*/
		public static Date strintToDate2(String ds) throws ParseException {
			DateFormat sdf = (DateFormat) fmt2.get();
			Date d = sdf.parse(ds);
			return d;
		}

		/**    
		 * yyyyMMddHHmmss
		 * @param ds
		 * @return
		 * @throws ParseException    
		*/
		public static Date strintToDate3(String ds) throws ParseException {
			DateFormat sdf = (DateFormat) fmt3.get();
			Date d = sdf.parse(ds);
			return d;
		}
		
		/**    
		 * HH:mm:ss
		 * @param ds
		 * @return
		 * @throws ParseException    
		*/
		public static Date strintToDate4(String ds) throws ParseException {
			DateFormat sdf = (DateFormat) fmt4.get();
			Date d = sdf.parse(ds);
			return d;
		}
		
		/**    
		 * yyyyMMdd
		 * @param ds
		 * @return
		 * @throws ParseException    
		*/
		public static Date strintToDate5(String ds) throws ParseException {
			DateFormat sdf = (DateFormat) fmt5.get();
			Date d = sdf.parse(ds);
			return d;
		}

		/**    
		 * HHmmsssss
		 * @param ds
		 * @return
		 * @throws ParseException    
		*/
		public static Date strintToDate6(String ds) throws ParseException {
			DateFormat sdf = (DateFormat) fmt6.get();
			Date d = sdf.parse(ds);
			return d;
		}
		
		/**    
		 * yyyyMM
		 * @param ds
		 * @return
		 * @throws ParseException    
		*/
		public static Date strintToDate7(String ds) throws ParseException {
			DateFormat sdf = (DateFormat) fmt7.get();
			Date d = sdf.parse(ds);
			return d;
		}
		
		/**
		 * �����ַ����¸�ʽ��
		 * @param ds
		 * @param format
		 * @return
		 * @throws ParseException
		 */
		public static String strSwapFormat(String ds,String oldFormat,String newFormat) throws ParseException {
			SimpleDateFormat osdf = new java.text.SimpleDateFormat(oldFormat);
			Date d = osdf.parse(ds);
			SimpleDateFormat nsdf = new java.text.SimpleDateFormat(newFormat);
			return nsdf.format(d);
		}
		
		/**
		 * ��Խ��������sAfter���ʱ�䣨yyyy-MM-dd��
		 * @param d
		 * @param sAfter
		 * @return
		 * @throws ParseException
		 */
		public static Date getSAfterToD(Date d, int sAfter)
				throws ParseException {
			Calendar c = Calendar.getInstance();
			c.setTime(d);
			c.add(Calendar.SECOND, sAfter);
			Date dt = new Date(c.get(Calendar.YEAR) - 1900, c.get(Calendar.MONTH),
					c.get(Calendar.DAY_OF_MONTH),c.get(Calendar.HOUR_OF_DAY),c.get(Calendar.MINUTE),c.get(Calendar.SECOND));
			return dt;
		}

		/**
		 * ��Խ��������sAfter���ʱ�䣨yyyy-MM-dd��
		 * @param d
		 * @param sAfter
		 * @return
		 * @throws ParseException
		 */
		public static String getSAfterToS(Date d, int sAfter)
				throws ParseException {
			Calendar c = Calendar.getInstance();
			c.setTime(d);
			c.add(Calendar.SECOND, sAfter);
			Date dt = new Date(c.get(Calendar.YEAR) - 1900, c.get(Calendar.MONTH),
					c.get(Calendar.DAY_OF_MONTH),c.get(Calendar.HOUR_OF_DAY),c.get(Calendar.MINUTE),c.get(Calendar.SECOND));
			return DateTool.parseDate2(dt);
		}
		
		/**
		 * ��Խ��������dayAfter���ʱ�䣨yyyy-MM-dd��
		 * @param d
		 * @param dayAfter
		 * @return
		 * @throws ParseException
		 */
		public static String getDayAfter(Date d, int dayAfter)
				throws ParseException {
			Calendar c = Calendar.getInstance();
			c.setTime(d);
			c.add(Calendar.DAY_OF_MONTH, dayAfter);
			Date dt = new Date(c.get(Calendar.YEAR) - 1900, c.get(Calendar.MONTH),
					c.get(Calendar.DAY_OF_MONTH));
			return DateTool.parseDate(dt);
		}

		/**
		 *  ��Խ��������dayAfter���ӵ�ʱ���ַ�yyyy-MM-dd hh:mm:ss��
		 * @param d
		 * @param dayAfter
		 * @return
		 * @throws ParseException
		 */
		public static String getminuteAfter(Date d, int dayAfter)
				throws ParseException {
			Calendar c = Calendar.getInstance();
			c.setTime(d);
			c.add(Calendar.MINUTE, dayAfter);
			Date dt = new Date(c.get(Calendar.YEAR) - 1900, c.get(Calendar.MONTH),
					c.get(Calendar.DAY_OF_MONTH), c.get(Calendar.HOUR_OF_DAY), c
							.get(Calendar.MINUTE), c.get(Calendar.SECOND));
			return DateTool.parseDate2(dt);
		}

		/**
		 * ��Խ��������dayAfter���ӵ�ʱ��
		 * @param d
		 * @param dayAfter
		 * @return
		 * @throws ParseException
		 */
		public static Date getminuteAfters(Date d, int dayAfter)
				throws ParseException {
			Calendar c = Calendar.getInstance();
			c.setTime(d);
			c.add(Calendar.MINUTE, dayAfter);
			Date dt = new Date(c.get(Calendar.YEAR) - 1900, c.get(Calendar.MONTH),
					c.get(Calendar.DAY_OF_MONTH), c.get(Calendar.HOUR_OF_DAY), c
							.get(Calendar.MINUTE), c.get(Calendar.SECOND));
			return dt;
		}

		/**
		 * ��Խ��������dayAfter���µ�ʱ�䣨yyyy-MM-dd��
		 * @param d
		 * @param monthAfter
		 * @return
		 */
		public static String getDayAfterMonth(Date d, int monthAfter) {
			Calendar c = Calendar.getInstance();
			c.setTime(d);
			c.add(Calendar.MONTH, monthAfter);
			Date dt = new Date(c.get(Calendar.YEAR) - 1900, c.get(Calendar.MONTH),
					c.get(Calendar.DAY_OF_MONTH));
			return DateTool.parseDate(dt);
		}


		/**
		 * ��Խ��������dayAfter���µ�ʱ�䣨yyyy-MM-dd��
		 * @param d
		 * @param monthAfter
		 * @return
		 */
		public static String getDayAfterMonth2(Date d, int monthAfter) {
			Calendar c = Calendar.getInstance();
			c.setTime(d);
			c.add(Calendar.MONTH, monthAfter);
			Date dt = new Date(c.get(Calendar.YEAR) - 1900, c.get(Calendar.MONTH),
					c.get(Calendar.DAY_OF_MONTH));
			return DateTool.parseDate2(dt);
		}

		/**
		 * ��Խ��������dayAfter���µ�ʱ�䣨yyyyMM��
		 * @param d
		 * @param monthAfter
		 * @return
		 */
		public static String getDayAfterMonth7(Date d, int monthAfter) {
			Calendar c = Calendar.getInstance();
			c.setTime(d);
			c.add(Calendar.MONTH, monthAfter);
			Date dt = new Date(c.get(Calendar.YEAR) - 1900, c.get(Calendar.MONTH),
					c.get(Calendar.DAY_OF_MONTH));
			return DateTool.parseDate7(dt);
		}
		
		/**
		 * @param d
		 * @return
		 */
		public static String getFirstDayOfMonth(Date d) {
			Calendar c = Calendar.getInstance();
			c.setTime(d);
			c.set(Calendar.DAY_OF_MONTH, 1);
			Date dt = new Date(c.get(Calendar.YEAR) - 1900, c.get(Calendar.MONTH),
					c.get(Calendar.DAY_OF_MONTH));
			return DateTool.parseDate(dt);
		}

		public static int getDay(String d) {
			int day = 1;
			if (d.length() >= 10) {
				day = Integer.parseInt(d.substring(8, 10));
			}
			return day;
		}

		public static int getMonth(String d) {
			int m = 1;
			if (d.length() >= 10) {
				m = Integer.parseInt(d.substring(5, 7));
			}
			return m;
		}

		public static int getYear(String d) {
			int y = 1;
			if (d.length() >= 10) {
				y = Integer.parseInt(d.substring(0, 4));
			}
			return y;
		}

		// ��ݴ�����������ڼ����������
		public static int getDayBetween(Calendar c1, Calendar c2) {
			int iReturn = 0;
			if (c1 != null && c2 != null) {
				iReturn = (int) ((c2.getTimeInMillis() - c1.getTimeInMillis()) / (24 * 60 * 60 * 1000));
			}
			return iReturn;
		}

		// ��ݴ�����������ڼ����������
		public static int getYearBetween(Calendar c1, Calendar c2) {
			int iReturn = 0;
			if (c1 != null && c2 != null) {
				iReturn = (int) (getDayBetween(c1, c2) / 365);
			}
			return iReturn;
		}

		/**
		 * ��2005-02-5��������ڸ�ʽת��ΪCalendar����
		 * 
		 * @param stringTo
		 * @return
		 * @throws ParseException
		 */
		public static Calendar stringToCalendar(String stringTo)
				throws ParseException {
			Calendar c = Calendar.getInstance();
			if (stringTo != null) {
				SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
				Date d = sdf.parse(stringTo);
				c.setTime(d);
			}
			return c;
		}

		public static String getNextMonday(String s) throws ParseException {
			Calendar c = DateTool.stringToCalendar(s);
			return (DateTool.getDayAfter(DateTool.strintToDate(s), 7 - c
					.get(Calendar.DAY_OF_WEEK) + 2));
		}

		public static String getNextSunday(String s) throws ParseException {
			Calendar c = DateTool.stringToCalendar(s);
			return (DateTool.getDayAfter(DateTool.strintToDate(s), 7 - c
					.get(Calendar.DAY_OF_WEEK) + 2 + 7));
		}

		public static String getThisSaturday(String s) throws ParseException {
			Calendar c = DateTool.stringToCalendar(s);
			return (DateTool.getDayAfter(DateTool.strintToDate(s), 7 - c
					.get(Calendar.DAY_OF_WEEK)));
		}

		public static String getWeekOfYear(String s) throws ParseException {
			Calendar c = DateTool.stringToCalendar(s);
			return String.valueOf(c.get(Calendar.WEEK_OF_YEAR));

		}


		// ��ݴ����type����ת�����ڣ�type������ѭDateת���Ĺ���
		public static String getTime(String type) {
			Date dt = new java.util.Date();
			SimpleDateFormat sdf = new java.text.SimpleDateFormat(type);
			return sdf.format(dt);
		}
		
		/**    
		 * yyyy-MM-dd
		 * @return    
		*/
		public static String getNow() {
			Date dt = new java.util.Date();
			DateFormat sdf = (DateFormat) fmt.get();
			return sdf.format(dt);
		}
		
		/**    
		 * yyyy-MM-dd HH:mm:ss
		 * @return    
		*/
		public static String getNow2() {
			Date dt = new java.util.Date();
			DateFormat sdf = (DateFormat) fmt2.get();
			return sdf.format(dt);
		}
		
		/**    
		 * yyyyMMddHHmmss
		 * @return    
		*/
		public static String getNow3() {
			Date dt = new java.util.Date();
			DateFormat sdf = (DateFormat) fmt3.get();
			return sdf.format(dt);
		}

		/**    
		 * HH:mm:ss
		 * @return    
		*/
		public static String getNow4() {
			Date dt = new java.util.Date();
			DateFormat sdf = (DateFormat) fmt4.get();
			return sdf.format(dt);
		}

		/**    
		 * yyyyMMdd
		 * @return    
		*/
		public static String getNow5() {
			Date dt = new java.util.Date();
			DateFormat sdf = (DateFormat) fmt5.get();
			return sdf.format(dt);
		}
		
		/**    
		 * HHmmsssss
		 * @return    
		*/
		public static String getNow6() {
			Date dt = new java.util.Date();
			DateFormat sdf = (DateFormat) fmt6.get();
			return sdf.format(dt);
		}

		/**    
		 * yyyyMM
		 * @return    
		*/
		public static String getNow7() {
			Date dt = new java.util.Date();
			DateFormat sdf = (DateFormat) fmt7.get();
			return sdf.format(dt);
		}
		public static String getStreamId() {
			// TODO
			String machineName = "000";// Configuration.getGlobalMsg("sys.machineName");//��λ������
			Date dt = new Date();
			String time = DateTool.parseDate6(dt);// ��λʱ��HHmmsssss
			String detail = String.valueOf((int) (Math.random() * 9000 + 1000));// ��λ�����
			return new StringBuffer().append(machineName).append(time).append(
					detail).toString();
		}
		
		public static String getTomorrow(){  
			String nextDate_1 = "";
	        try {  
	            //获取当前日期  
	            Date date = new Date();  
	            SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");  
	            String nowDate = sf.format(date);  
	           // System.out.println(nowDate);  
	            //通过日历获取下一天日期  
	            Calendar cal = Calendar.getInstance();  
	            cal.setTime(sf.parse(nowDate));  
	            cal.add(Calendar.DAY_OF_YEAR, +1);  
	            nextDate_1 = sf.format(cal.getTime());  
	            //System.out.println(nextDate_1);  
	        } catch (Exception e) {  
	        	e.printStackTrace();
	        } 
	        return nextDate_1;
	    }  
		
		
		
		public static void main(String[] args) throws ParseException {
			String date= DateTool.getNow3();
			System.out.println(date);
		}

}
