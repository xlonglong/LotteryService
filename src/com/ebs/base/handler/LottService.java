package com.ebs.base.handler;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.RowSet;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.ebs.receiver.conf.Configuration;
import com.ebs.receiver.conn.DBUtilMysql;
import com.ebs.receiver.domain.Header;
import com.ebs.receiver.domain.Lott;
import com.ebs.receiver.domain.OrderInfo;
import com.ebs.receiver.domain.PageInfo;
import com.ebs.receiver.util.DateTool;
import com.ebs.receiver.util.RandomCode;

public class LottService {

	private Logger logger = Logger.getLogger(LottService.class);

	public boolean insertLottInfo(Lott lott, Header header) {
		Connection conn = null;
		try {
			conn = DBUtilMysql.getConnection();
			Statement st = conn.createStatement();
			// 更新订单表
			InsertOrder(conn, st, lott.getOrderId(), lott.getUserid(), lott
					.getLottList().get(0).getLotteryCode(), lott.getLottList()
					.get(0).getPeriodCode(), lott.getOrderMoney(),
					lott.getOrderTime(), header, lott.getUserName(),
					lott.getOrderMoney());
			// 更新订单详情
			InsertOrderDetail(conn, st, lott.getOrderId(), lott.getLottList(),
					lott.getUserid(), lott.getUserName());
			conn.commit();
			conn.close();
			return true;
		} catch (Exception e) {
			logger.error(e);
			return false;
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	// 更新订单表
	public void InsertOrder(Connection conn, Statement st, String orderId,
			String userId, String lotteryCode, String periodCode, String money,
			String orderTime, Header header, String userName, String orderMonery)
			throws SQLException {
		String machineid = "T0004";
		String ticketId = "NK" + DateTool.parseDate10(new Date()) + machineid
				+ DateTool.parseDate9(new Date())+RandomCode.getRandomCode();
		StringBuffer sql = new StringBuffer(
				"insert into generalorder(orderNumber,userId,lotteryType,issueNumber,orderbonus,orderTime,orderStatus,betSource,channel,sale_channel_no,ticketID,userName,orderMonery)values(\"");

		sql.append(orderId);
		sql.append("\",\"");
		sql.append(userId);
		sql.append("\",\"");
		sql.append(lotteryCode);
		sql.append("\",\"");
		sql.append(periodCode);
		sql.append("\",\"");
		sql.append(money);
		sql.append("\",\"");
		sql.append(orderTime);
		sql.append("\",\"");
		sql.append("0");
		sql.append("\",\"");
		sql.append(header.getBetSource());
		sql.append("\",\"");
		sql.append(header.getChannel());
		sql.append("\",\"");
		sql.append(header.getSaleChannelNo());
		sql.append("\",\"");
		sql.append(ticketId);
		sql.append("\",\"");
		sql.append(userName);
		sql.append("\",\"");
		sql.append(orderMonery);
		sql.append("\");");

		logger.info(sql.toString());

		DBUtilMysql.execUpdateNoCommit(sql.toString(), conn, st);

	}

	// 更新
	public void InsertOrderDetail(Connection conn, Statement st,
			String orderId, List<OrderInfo> list, String userid, String userName)
			throws SQLException {
		// 彩票拆分
		String lotteryCode = list.get(0).getLotteryCode();
		list = new BetCodeService().getTicketInfoList(lotteryCode, list);
		for (int i = 0; i < list.size(); i++) {
			// String ticketId=new
			// Random().nextInt(999999)+DateTool.parseDate9(new Date());
			String machineid = "T0004";
			String ticketId = "NK" + DateTool.parseDate10(new Date())
					+ machineid + DateTool.parseDate9(new Date())
					+ RandomCode.getRandomCode();
			;
			StringBuffer sql = new StringBuffer(
					"insert lotterynumbers(orderNumber,lotteryType,issueNumber,number,count,bonus,multiple,playType,orderStatus,ticketID,userid,userName)values (\"");
			sql.append(orderId);
			sql.append("\",\"");
			sql.append(list.get(i).getLotteryCode());
			sql.append("\",\"");
			sql.append(list.get(i).getPeriodCode());
			sql.append("\",\"");
			sql.append(list.get(i).getNumber());
			sql.append("\",\"");
			sql.append(list.get(i).getCount());
			sql.append("\",\"");
			sql.append(list.get(i).getMoney());
			sql.append("\",\"");
			sql.append(list.get(i).getMultiple());
			sql.append("\",\"");
			sql.append(list.get(i).getPlayType());
			sql.append("\",\"");
			sql.append("0");
			sql.append("\",\"");
			sql.append(ticketId);
			sql.append("\",\"");
			sql.append(userid);
			sql.append("\",\"");
			sql.append(userName);
			sql.append("\");");
			logger.info(sql.toString());
			DBUtilMysql.execUpdateNoCommit(sql.toString(), conn, st);
		}
	}

	public List<OrderInfo> getOrderInfoList(Lott lott, PageInfo pageInfo) {
		List<OrderInfo> list = new ArrayList<OrderInfo>();
		try {

			StringBuffer sql = new StringBuffer(
					"select orderNumber,lotteryType,issueNumber,orderTime,orderStatus,winningStatus,orderbonus,bonus,lotteryNumber from generalorder where userId=\"");
			sql.append(lott.getUserid());
			sql.append("\"");

			if (StringUtils.isNotEmpty(lott.getLotteryCode())) {
				sql.append(" and lotteryType=\"");
				sql.append(lott.getLotteryCode());
				sql.append("\"");
			}
			if (StringUtils.isNotEmpty(lott.getBeginTime())) {
				sql.append(" and orderTime>=\"");
				sql.append(lott.getBeginTime());
				sql.append("\"");
			}
			if (StringUtils.isNotEmpty(lott.getEndTime())) {
				sql.append(" and orderTime<=\"");
				sql.append(lott.getEndTime());
				sql.append("\"");
			}
			if (StringUtils.isNotEmpty(lott.getOrderStatus())) {
				sql.append(" and orderStatus=\"");
				sql.append(lott.getOrderStatus());
				sql.append("\"");
			} else {
				sql.append(" and orderStatus not in (0,5) ");
			}
			if (StringUtils.isNotEmpty(lott.getWinningStatus())) {
				sql.append(" and winningStatus=\"");
				sql.append(lott.getWinningStatus());
				sql.append("\"");
			}

			sql.append("  order by orderTime desc limit ");
			sql.append((Integer.parseInt(pageInfo.getBegin()) - 1)
					* Integer.parseInt(pageInfo.getNum()));
			sql.append(",");
			sql.append(pageInfo.getNum());
			sql.append(";");

			logger.info("sql:" + sql.toString());

			RowSet rs = DBUtilMysql.execQuery(sql.toString());
			while (rs != null & rs.next()) {
				OrderInfo orderInfo = new OrderInfo();
				orderInfo.setOrderId(rs.getString(1));
				orderInfo.setLotteryCode(rs.getString(2));
				orderInfo.setPeriodCode(rs.getString(3));
				orderInfo.setOrderTime(rs.getString(4));
				orderInfo.setOrderStatus(rs.getString(5));
				orderInfo.setWinningStatus(rs.getString(6));
				orderInfo.setMoney(rs.getString(7));
				orderInfo.setWinMoney(rs.getString(8));
				String lotteryName = "LotteryName_" + rs.getString(2);
				orderInfo.setLotteryName(Configuration
						.getGlobalMsg(lotteryName));
				String orderType = "OrderType_" + rs.getString(1).split("-")[0];
				orderInfo.setOrderType(Configuration.getGlobalMsg(orderType));
				orderInfo.setWinCode(rs.getString(9));
				list.add(orderInfo);
			}

		} catch (Exception e) {
			logger.error(e);
		}

		return list;
	}

	public int getOrderInfoListNum(Lott lott) {
		try {

			StringBuffer sql = new StringBuffer(
					"select count(*) from generalorder where userId=\"");
			sql.append(lott.getUserid());
			sql.append("\"");
			if (StringUtils.isNotEmpty(lott.getBeginTime())) {
				sql.append(" and orderTime>=\"");
				sql.append(lott.getBeginTime());
				sql.append("\"");
			}
			if (StringUtils.isNotEmpty(lott.getEndTime())) {
				sql.append(" and orderTime<=\"");
				sql.append(lott.getEndTime());
				sql.append("\"");
			}
			if (StringUtils.isNotEmpty(lott.getOrderStatus())) {
				sql.append(" and orderStatus=\"");
				sql.append(lott.getOrderStatus());
				sql.append("\"");
			} else {
				sql.append(" and orderStatus not in (0,5) ");
			}

			sql.append(" ;");

			logger.info("sql:" + sql.toString());

			RowSet rs = DBUtilMysql.execQuery(sql.toString());
			while (rs != null & rs.next()) {
				return rs.getInt(1);
			}

		} catch (Exception e) {
			logger.error(e);
		}

		return 0;
	}

	public List<OrderInfo> getOrderInfo(String orderid) {
		List<OrderInfo> list = new ArrayList<OrderInfo>();
		try {
			StringBuffer sql = new StringBuffer(
					"select lotteryType,issueNumber,number,playType,multiple,count,bonus ,orderStatus,bonusMoney,lottnumbers,winningStatus,orderTime,userName from lotterynumbers where orderNumber=\"");
			sql.append(orderid);
			sql.append("\" ;");
			logger.info("sql:" + sql.toString());

			RowSet rs = DBUtilMysql.execQuery(sql.toString());
			while (rs != null & rs.next()) {
				OrderInfo orderInfo = new OrderInfo();
				orderInfo.setLotteryCode(rs.getString(1));
				orderInfo.setPeriodCode(rs.getString(2));
				String lotteryType = rs.getString(1);
				String codes= rs.getString(3);
				String playType = rs.getString(4);
				
				if(playType.equals("102") && lotteryType.equals("k3")){
					orderInfo.setPlayType(playType);
					orderInfo.setNumber("三同号通选");
				}else if(playType.equals("105") && lotteryType.equals("k3")){
					orderInfo.setPlayType(playType);
					orderInfo.setNumber("三连号通选");
				}else if(playType.equals("103") && lotteryType.equals("k3")){	//1,1,1&2,2,2
					orderInfo.setPlayType(playType);
					orderInfo.setNumber(codes.replace("&", " ").replace(",", ""));
				}else if(playType.equals("106") && lotteryType.equals("k3")){	//1,2,3
					orderInfo.setPlayType(playType);
					orderInfo.setNumber(getAnteCodes106(codes));
				}else if(playType.equals("107") && lotteryType.equals("k3")){	//1,2|3,4,5
					orderInfo.setPlayType(playType);
					orderInfo.setNumber(getAnteCodes107(codes));
				}else{
					orderInfo.setNumber(rs.getString(3));
					orderInfo.setPlayType(rs.getString(4));
				}
				orderInfo.setMultiple(rs.getString(5));
				orderInfo.setCount(rs.getString(6));
				orderInfo.setMoney(rs.getString(7));
				orderInfo.setOrderStatus(rs.getString(8));
				orderInfo.setWinMoney(rs.getString(9));
				orderInfo.setWinCode(rs.getString(10));
				orderInfo.setWinningStatus(rs.getString(11));
				orderInfo.setOrderTime(rs.getString(12));
				orderInfo.setUserName(rs.getString(13));
				list.add(orderInfo);
			}

		} catch (Exception e) {
			logger.error(e);
		}
		return list;
	}

	// 判断订单是否可支付
	public boolean isOrderPayment(String orderId) {
		try {
			StringBuffer sql = new StringBuffer(
					"select orderStatus from generalorder where orderNumber=\"");
			sql.append(orderId);
			sql.append("\" ;");
			logger.info("sql:" + sql.toString());

			RowSet rs = DBUtilMysql.execQuery(sql.toString());
			while (rs != null & rs.next()) {
				if (rs.getString(1).equals("0")) {
					// 未支付
					return true;
				}
			}
		} catch (Exception e) {
			logger.error(e);
		}
		return false;

	}
	
	public String getAnteCodes106(String codes){
		String ret="";
		String code [] = codes.split("\\,");
		for(int i=0;i<code.length;i++){
			ret+=code[i]+code[i]+"* ";
		}
		System.out.println(ret);
		return ret;
	}
	
	public String getAnteCodes107(String codes){
		String ret="";
		String code [] = codes.split("\\|");
		System.out.println(code[0]);
		String tong [] = code[0].split("\\,");
		for(int i=0;i<tong.length;i++){
			System.out.println(tong[i]);
			ret+=tong[i]+tong[i]+",";
		}
		ret=ret.substring(0, ret.length()-1)+ " | "+code[1];
		System.out.println(ret);
		return ret;
	}
	
	public static void main(String []a ){
		String codes = "1,2,3";
		String ret="";
		String code [] = codes.split("\\,");
		for(int i=0;i<code.length;i++){
			ret+=code[i]+code[i]+"* ";
		}
		System.out.println(ret);
		
	}
	
}