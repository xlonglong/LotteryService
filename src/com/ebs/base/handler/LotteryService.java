package com.ebs.base.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.RowSet;

import org.apache.log4j.Logger;

import com.ebs.receiver.conn.DBUtilMysql;
import com.ebs.receiver.domain.LotteryInfo;
import com.ebs.receiver.domain.PageInfo;
import com.ebs.receiver.domain.WinInfoLevel;
import com.ebs.receiver.util.RedisUtil;
import com.junbao.hf.utils.common.StringUtils;

public class LotteryService {

	private Logger logger = Logger.getLogger(LotteryService.class);

	public List<WinInfoLevel> getWinInfoLevel(String lotteryCode,
			String periodCode, PageInfo pageInfo) {
		List<WinInfoLevel> list = new ArrayList<WinInfoLevel>();

		try {
			StringBuffer sql = new StringBuffer(
					"select gameName,number,bonusCode,salesMoney,bonusMoney,stopTime,startTime from caipiao_award where gameName=\"");
			sql.append(lotteryCode);
			sql.append("\"");
			if (StringUtils.isNotEmpty(periodCode)) {
				sql.append(" and number=\"");
				sql.append(periodCode);
				sql.append("\"");
			}
			sql.append(" and status>=2 order by number desc limit ");
			sql.append((Integer.parseInt(pageInfo.getBegin()) - 1)
					* Integer.parseInt(pageInfo.getNum()));
			sql.append(",");
			sql.append(pageInfo.getNum());
			sql.append(";");

			logger.info(sql);
			RowSet rs = DBUtilMysql.execQuery(sql.toString());
			while (rs != null & rs.next()) {
				WinInfoLevel winLevel = new WinInfoLevel();
				winLevel.setLotteryCode(rs.getString(1));
				winLevel.setPeriodCode(rs.getString(2));
				winLevel.setWinCode(rs.getString(3));
				winLevel.setSales(rs.getString(4));
				winLevel.setBonus(rs.getString(5));
				winLevel.setEndTime(rs.getString(6));
				winLevel.setStartTime(rs.getString(7));
				list.add(winLevel);
			}

		} catch (Exception e) {
			logger.error(e);
		}
		return list;

	}

	public int getWinInfoLevelNum(String lotteryCode, String periodCode) {

		try {
			StringBuffer sql = new StringBuffer(
					"select count(*) from caipiao_award where gameName=\"");
			sql.append(lotteryCode);
			sql.append("\"");
			if (StringUtils.isNotEmpty(periodCode)) {
				sql.append(" and number=\"");
				sql.append(periodCode);
				sql.append("\"");
			}

			sql.append("  and status>=2;");

			logger.info(sql);
			RowSet rs = DBUtilMysql.execQuery(sql.toString());
			while (rs != null & rs.next()) {
				return rs.getInt(1);
			}

		} catch (Exception e) {
			logger.error(e);
		}
		return 0;

	}
	public LotteryInfo getLotteryInfo(String lotteryCode, String periodCode) {
	
		LotteryInfo lotteryInfo=null;
		//通过缓存查询
		lotteryInfo=getLotteryInfoByCache(lotteryCode,periodCode);
		if(lotteryInfo==null)
		{
			//通过数据库查询
			lotteryInfo=getLotteryInfoByDataBase(lotteryCode,periodCode);
		}
		return lotteryInfo;
	}
	public LotteryInfo getLotteryInfoByDataBase(String lotteryCode,String periodCode)
	{
		//通过数据库查询奖期信息
		try {
			StringBuffer sql = new StringBuffer(
					"select gameName,number,startTime,stopTime,status,bonusCode ,addbonusmoney,poolMoney from caipiao_award where gameName=\"");
			sql.append(lotteryCode);
			sql.append("\"");
			if (StringUtils.isNotEmpty(periodCode)) {
				sql.append(" and number=\"");
				sql.append(periodCode);
				sql.append("\"");
			}
			sql.append(" order by number desc limit 1 ;");
			logger.info(sql);
			RowSet rs = DBUtilMysql.execQuery(sql.toString());
			while (rs != null & rs.next()) {
				LotteryInfo lotteryInfo = new LotteryInfo();

				lotteryInfo.setLotteryCode(rs.getString(1));
				lotteryInfo.setPeriodCode(rs.getString(2));
				lotteryInfo.setStartTime(rs.getString(3));
				lotteryInfo.setEndTime(rs.getString(4));
				lotteryInfo.setStatus(rs.getString(5));
				lotteryInfo.setWinNum(rs.getString(6));
				lotteryInfo.setIsPlusAward(rs.getString(7));
				lotteryInfo.setPoolMoney(rs.getString(8));
				return lotteryInfo;
			}

		} catch (Exception e) {
			logger.error(e);
		}

		return null;
	}
	public LotteryInfo getLotteryInfoByCache(String lotteryCode,
			String periodCode) {
		try {
			Map<String, String> map = null;
			if (StringUtils.isNotEmpty(periodCode)) {
				// 获取最新期次
				map = RedisUtil.getMap(lotteryCode);
			} else {
				// 获取指定期次
				String key = lotteryCode + "_" + periodCode;
				map = RedisUtil.getMap(key);
			}
			if (map != null) {
				LotteryInfo lotteryInfo = new LotteryInfo();

				lotteryInfo.setLotteryCode(map.get("lotteryCode"));
				lotteryInfo.setPeriodCode(map.get("periodCode"));
				lotteryInfo.setStartTime(map.get("startTime"));
				lotteryInfo.setEndTime(map.get("endTime"));
				lotteryInfo.setStatus(map.get("status"));
				lotteryInfo.setWinNum(map.get("winNum"));
				lotteryInfo.setIsPlusAward(map.get("isPlusAward"));
				lotteryInfo.setPoolMoney(map.get("poolMoney"));
			}

		} catch (Exception e) {
			logger.error(e);
		}
		return null;
	}
}
