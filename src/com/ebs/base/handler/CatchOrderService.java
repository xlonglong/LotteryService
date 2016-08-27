package com.ebs.base.handler;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
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

public class CatchOrderService {

	private Logger logger = Logger.getLogger(CatchOrderService.class);

	public boolean insertLottInfo(Lott lott, Header header) {
		Connection conn = null;
		try {
			String machineid = "T0004";
			String batchId="ZNK"+DateTool.parseDate10(new Date())+machineid+DateTool.parseDate9(new Date())+RandomCode.getRandomCode();
			conn = DBUtilMysql.getConnection();
			Statement st = conn.createStatement();
			// 更新追号订单表
			InsertOrder(conn, st, batchId,lott.getOrderId(), lott.getUserid(),
					lott.getLotteryCode(), lott.getPeriodCode(),
					lott.getOrderMoney(), lott.getOrderTime(),
					lott.getNumber(), header, lott.getType(),lott.getUserName(),lott.getProfitScale(),lott.getProfitMoney());
			// 更新订单详情
			InsertCatchOrderDetail(conn, st,batchId, lott.getOrderId(),
					lott.getLottList(), lott.getList(), lott.getUserid(),
					lott.getLotteryCode(),lott.getPeriodCode());
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
	public void InsertOrder(Connection conn, Statement st,String batchId,String orderId,
			String userId, String lotteryCode, String periodCode, String money,
			String orderTime, String count, Header header, String type,String userName,String profitScale,String profitMoney)
			throws SQLException {
		/*String machineid = "T0004";
		String batchId="ZNK"+DateTool.parseDate10(new Date())+machineid+DateTool.parseDate9(new Date());*/
		StringBuffer sql = new StringBuffer(
				"insert into catchorders(orderNumber,userId,lotteryCode,periodCode,orderMoney,orderTime,orderStatus,betSource,channel,sale_channel_no,orderCount,catchCount,Type,ticketId,userName,profitScale,profitMoney)values(\"");
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
		sql.append(count);
		sql.append("\",\"");
		sql.append(0);
		sql.append("\",\"");
		sql.append(type);
		sql.append("\",\"");
		sql.append(batchId);
		sql.append("\",\"");
		sql.append(userName);
		sql.append("\",\"");
		sql.append(profitScale);
		sql.append("\",\"");
		sql.append(profitMoney);
		sql.append("\");");

		logger.info(sql.toString());

		DBUtilMysql.execUpdateNoCommit(sql.toString(), conn, st);

	}

	// 记录追号详情表

	public void InsertCatchOrderDetail(Connection conn, Statement st,
			String batchId,String orderId, List<OrderInfo> list,
			List<OrderInfo> catchInfoList, String userid, String lotteryCode, String periodCode)
			throws SQLException, IllegalAccessException,
			InstantiationException, InvocationTargetException,
			IntrospectionException {

		StringBuffer sql = new StringBuffer(
				"insert catchorderdetail(orderNumber,lotteryCode,periodCode,number,count,money,multiple,playType,userid,ticketId,margins,totalCastMoney,profitAmount,awardFee,totalTicketId) value ");
		long l = Long.parseLong(periodCode)-1;
		periodCode = Long.toString(l);
		for (int i = 0; i < catchInfoList.size(); i++) {
			for (int j = 0; j < list.size(); j++) {
				OrderInfo info = new OrderInfo();
				periodCode = getPCode(periodCode);
				info.setPeriodCode(periodCode);
				info.setNumber(list.get(j).getNumber());
				info.setPlayType(list.get(j).getPlayType());
				info.setMultiple(catchInfoList.get(i).getMultiple());
				info.setMargins(catchInfoList.get(i).getMargins());
				info.setTotalCastMoney(catchInfoList.get(i).getTotalCastMoney());
				info.setProfitAmount(catchInfoList.get(i).getProfitAmount());
				info.setAwardFee(catchInfoList.get(i).getAwardFee());
				info.setLotteryCode(lotteryCode);
				List<OrderInfo> LottList = new BetCodeService()
						.getCodeList(info);
				sql = getOrderInfoSql(sql, LottList, orderId, lotteryCode,
						userid,info,batchId);
			} 
		}
		sql.deleteCharAt(sql.length() - 1);
		sql.append(";");
		logger.info(sql.toString());
		DBUtilMysql.execUpdateNoCommit(sql.toString(), conn, st);
	}

	// //注码拆分
	// List<OrderInfo> ListInfo=new
	// BetCodeService().getTicketInfoList(lotteryCode,list);
	// for (int i = 0; i < catchInfoList.size(); i++) {
	// for (int j = 0; j < ListInfo.size(); j++) {
	// String ticketId=new Random().nextInt(999999)+DateTool.parseDate9(new
	// Date());
	// StringBuffer sql = new StringBuffer(
	// "insert catchorderdetail(orderNumber,lotteryCode,periodCode,number,count,money,multiple,playType,userid,ticketId)values (\"");
	// sql.append(orderId);
	// sql.append("\",\"");
	// sql.append(lotteryCode);
	// sql.append("\",\"");
	// sql.append(catchInfoList.get(i).getCatchNumber());
	// sql.append("\",\"");
	// sql.append(ListInfo.get(j).getNumber());//注码
	// sql.append("\",\"");
	// sql.append(catchInfoList.get(i).getCount());
	// sql.append("\",\"");
	// sql.append(catchInfoList.get(i).getMoney());
	// sql.append("\",\"");
	// sql.append(catchInfoList.get(i).getMultiple());
	// sql.append("\",\"");
	// sql.append(ListInfo.get(j).getPlayType());
	// sql.append("\", ");
	// sql.append(userid);
	// sql.append("\",\"");
	// sql.append(ticketId);
	// sql.append(" );");
	// logger.info(sql.toString());
	// DBUtilMysql.execUpdateNoCommit(sql.toString(), conn, st);
	// }

	// }

	// }

	public List<OrderInfo> getOrderInfoList(Lott lott, PageInfo pageInfo) {
		List<OrderInfo> list = new ArrayList<OrderInfo>();
		try {

			StringBuffer sql = new StringBuffer(
					"select orderNumber,lotteryCode,periodCode,orderCount,catchCount,orderMoney,orderTime,orderStatus,winningStatus,orderBonus,type from catchorders  where orderStatus NOT in (0,5) and userId=\"");
			sql.append(lott.getUserid());
			sql.append("\"");

			if (StringUtils.isNotEmpty(lott.getLotteryCode())) {
				sql.append(" and lotteryCode=\"");
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
			sql.append("  order by id desc limit ");
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

				orderInfo.setNumber(rs.getString(4));
				orderInfo.setCatchNumber(rs.getString(5));
				orderInfo.setMoney(rs.getString(6));
				orderInfo.setOrderTime(rs.getString(7));
				orderInfo.setOrderStatus(rs.getString(8));
				orderInfo.setWinningStatus(rs.getString(9));
				orderInfo.setWinMoney(rs.getString(10));
				orderInfo.setType(rs.getString(11));
				String lotteryName = "LotteryName_" + rs.getString(2);
				orderInfo.setLotteryName(Configuration
						.getGlobalMsg(lotteryName));
				String orderType = "OrderType_" + rs.getString(1).split("-")[0];
				orderInfo.setOrderType(Configuration.getGlobalMsg(orderType));
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
					"select count(*) from catchorders where orderStatus NOT in (0,5) and userId=\"");
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
					//"select lotteryType,issueNumber,number,playType,multiple,count,bonus ,orderStatus,bonusMoney,lottnumbers,winningStatus,orderTime from lotterynumbers where orderNumber=\"");
					  "select lotteryCode,periodCode,number,playType,multiple,count,money,orderStatus,bonusMoney,lottnumbers,winningStatus,orderTime from catchorderdetail where orderNumber=\"");
			sql.append(orderid);
			sql.append("\" order by lotteryCode ;");
			logger.info("sql:" + sql.toString());

			RowSet rs = DBUtilMysql.execQuery(sql.toString());
			while (rs != null & rs.next()) {
				OrderInfo orderInfo = new OrderInfo();
				orderInfo.setLotteryCode(rs.getString(1));
				orderInfo.setPeriodCode(rs.getString(2));
				orderInfo.setNumber(rs.getString(3));
				orderInfo.setPlayType(rs.getString(4));
				orderInfo.setMultiple(rs.getString(5));
				orderInfo.setCount(rs.getString(6));
				orderInfo.setMoney(rs.getString(7));
				orderInfo.setOrderStatus(rs.getString(8));
				orderInfo.setWinMoney(rs.getString(9));
				orderInfo.setWinCode(rs.getString(10));
				orderInfo.setWinningStatus(rs.getString(11));
				orderInfo.setOrderTime(rs.getString(12));
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

	public StringBuffer getOrderInfoSql(StringBuffer sql, List<OrderInfo> list,
			String orderId, String lotteryCode, String userid,OrderInfo info,String batchId) {
		/*long l = Long.parseLong(periodCode)-1;
		periodCode = Long.toString(l);*/
		for (int i = 0; i < list.size(); i++) {
			/*String ticketId = new Random().nextInt(999999)
					+ DateTool.parseDate9(new Date());*/
			String machineid = "T0004";
			String ticketId = "ZNKX"+DateTool.parseDate10(new Date())+machineid+DateTool.parseDate9(new Date())+RandomCode.getRandomCode();;
			//periodCode = getPCode(periodCode);
			sql.append("(\"");
			sql.append(orderId);
			sql.append("\",\"");
			sql.append(lotteryCode);
			sql.append("\",\"");
			sql.append(list.get(i).getPeriodCode());
			//sql.append(periodCode);
			sql.append("\",\"");
			sql.append(list.get(i).getNumber());// 注码
			sql.append("\",\"");
			sql.append(list.get(i).getCount());
			sql.append("\",\"");
			sql.append(list.get(i).getMoney());
			sql.append("\",\"");
			sql.append(list.get(i).getMultiple());
			sql.append("\",\"");
			sql.append(list.get(i).getPlayType());
			sql.append("\",\"");
			sql.append(userid);
			sql.append("\",\"");
			sql.append(ticketId);
			sql.append("\",\"");
			sql.append(info.getMargins());
			sql.append("\",\"");
			sql.append(info.getTotalCastMoney());
			sql.append("\",\"");
			sql.append(info.getProfitAmount());
			sql.append("\",\"");
			sql.append(info.getAwardFee());
			sql.append("\",\"");
			sql.append(batchId);
			sql.append("\" ),");
		}
		return sql;
	}

	public OrderInfo getCatchInfo(String orderid) {

		StringBuffer sql = new StringBuffer(
				"select orderNumber,lotteryCode,periodCode,orderCount,catchCount,orderMoney,orderTime,orderStatus,winningStatus,orderBonus,type from catchorders  where orderNumber=\"");
		sql.append(orderid);
		sql.append("\" ;");

		logger.info("sql:" + sql.toString());
		try {
			RowSet rs = DBUtilMysql.execQuery(sql.toString());
			while (rs != null & rs.next()) {
				OrderInfo orderInfo = new OrderInfo();
				orderInfo.setOrderId(rs.getString(1));
				orderInfo.setLotteryCode(rs.getString(2));
				orderInfo.setPeriodCode(rs.getString(3));
				orderInfo.setNumber(rs.getString(4));
				orderInfo.setCatchNumber(rs.getString(5));
				orderInfo.setMoney(rs.getString(6));
				orderInfo.setOrderTime(rs.getString(7));
				orderInfo.setOrderStatus(rs.getString(8));
				orderInfo.setWinningStatus(rs.getString(9));
				orderInfo.setWinMoney(rs.getString(10));
				orderInfo.setType(rs.getString(11));
				return orderInfo;
			}

		} catch (Exception e) {
			logger.error(e);
		}

		return null;
	}
	
	public static void main (String [] a){
		String code = "20160614008" ;
		long l = Long.parseLong(code)-1;
		code = Long.toString(l);
		for(int i=0;i<3;i++){
			code = getPCode(code);
			//System.out.println(code);
		}
	}
	
	public static String getPCode(String p){
		long d = 0;
		long e = 0;
		int c = Integer.parseInt(p.substring(8));
			if(c<84){
				d= Long.parseLong(p)+1;
				System.out.println(d);
				return Long.toString(d);
			}
			else if(c>=84){
				e=Long.parseLong(DateTool.getTomorrow()+"001");
				System.out.println(e);
				return Long.toString(e);
			}
		return "";
	}

}
