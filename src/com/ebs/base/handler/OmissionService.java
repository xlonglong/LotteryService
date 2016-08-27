package com.ebs.base.handler;

import java.util.HashMap;
import java.util.Map;

import javax.sql.RowSet;

import org.apache.log4j.Logger;

import com.ebs.receiver.conn.DBUtilMysql;
import com.ebs.receiver.util.RedisUtil;

public class OmissionService {
	Logger logger = Logger.getLogger(OmissionService.class);

	public Map<String, String> getOmission(String lotteryCode) {
		String key = "Omission" + lotteryCode;
		Map<String, String> map = RedisUtil.getMap(key);
		if (map != null && !map.isEmpty()) {
			logger.info("缓存命中");
			return map;
		}
		return getOmissionMap(lotteryCode);

	}
	public Map<String, String> getOmissionMap(String lotteryCode) {

		Map<String, String> map = new HashMap<String, String>();
		try {
			logger.info("数据库命中");
			StringBuffer sql = new StringBuffer(
					"select Ball,Num from omission where LotteryCode=\"");
			sql.append(lotteryCode);
			sql.append("\" and PeriodCode = (");
			sql.append("select PeriodCode from omission where LotteryCode=\"");
			sql.append(lotteryCode);
			sql.append("\" order by PeriodCode desc limit 0,1);");

			logger.info(sql);
			RowSet rs = DBUtilMysql.execQuery(sql.toString());

			while (rs != null & rs.next()) {
				map.put(rs.getString(1), rs.getString(2));
			}
		} catch (Exception e) {
			logger.error(e);
		}
		return map;
	}
	
}
