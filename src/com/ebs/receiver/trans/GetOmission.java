package com.ebs.receiver.trans;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ebs.base.handler.OmissionService;
import com.ebs.receiver.comm.FuncUtils;
import com.ebs.receiver.conf.Configuration;
import com.ebs.receiver.domain.Status;
import com.ebs.receiver.domain.TYOrder;
import com.ebs.receiver.util.JSONUtils;
import com.ebs.receiver.util.PackUtil;

public class GetOmission {

	private static Logger logger = Logger.getLogger(GetOmission.class);

	public String work(String msg) {
		try {
			logger.info(msg);
			TYOrder tyOrder = JSONUtils.json2pojo(PackUtil.getJsonBode(msg),
					TYOrder.class);
			String lotteryCode = tyOrder.getLotteryInfo().getLotteryCode();

			Map<String, String> map = new OmissionService()
					.getOmission(lotteryCode);

			if (map.isEmpty()) {
				return FuncUtils.getErrorMsg("0001",
						Configuration.getGlobalMsg("MSG_0001"));
			}
			// 查询成功

			tyOrder.getLotteryInfo().setOmissionMap(getMap(map, lotteryCode));
			Status status = new Status();
			status.setErrorCode("0000");
			status.setErrorMsg(Configuration.getGlobalMsg("MSG_0000"));
			tyOrder.setStatus(status);
			tyOrder.setHeader(null);
			return JSONUtils.obj2json(tyOrder);
		} catch (Exception e) {
			logger.error(e);
			return FuncUtils.getErrorMsg("9999",
					Configuration.getGlobalMsg("MSG_9999"));
		}
	}

	public Map<String, Object> getMap(Map<String, String> map,
			String lotteryCode) {
		Map<String, Object> rMap = new HashMap<String, Object>();

		switch (lotteryCode) {
		case "k3": {

			// 和值组包
			Map<String, String> hMap = new HashMap<String, String>();
			for (int i = 4; i <= 17; i++) {
				String key = "H" + i;
				hMap.put(key, map.get(key));
			}
			rMap.put("101", hMap);

			// 三同号
			Map<String, String> aMap = new HashMap<String, String>();
			for (int i = 1; i <= 6; i++) {
				String key = "A" + i + i + i;
				aMap.put(key, map.get(key));
			}
			rMap.put("102", aMap);
			// 三同号
			Map<String, String> bMap = new HashMap<String, String>();
			for (int i = 1; i <= 6; i++) {
				String key = "B" + i + i;
				bMap.put(key, map.get(key));
			}
			rMap.put("103", bMap);
		//	二同号的6个不同号
			Map<String, String> BMap = new HashMap<String, String>();
			for (int i = 1; i <= 6; i++) {
				String key = "B" + i;
				BMap.put(key, map.get(key));
			}
			rMap.put("106", BMap);
			
			// 三同号
			Map<String, String> cMap = new HashMap<String, String>();
			for (int i = 1; i <= 7; i++) {
				String key = "C" + i;
				cMap.put(key, map.get(key));

			}
			rMap.put("104", cMap);

			// 二不同号
			Map<String, String> dMap = new HashMap<String, String>();
			for (int i = 1; i <= 6; i++) {
				String key = "D" + i;
				dMap.put(key, map.get(key));

			}
			rMap.put("105", dMap);
			break;
		}

		case "ssq":
		case "307": {
			rMap.put("107", map);
			break;
		}
		case "3d": {
			// 3d常规号码
			Map<String, String> sMap = new HashMap<String, String>();
			for (int i = 0; i <= 9; i++) {
				String key = "G0" + i;
				sMap.put(key, map.get(key));
				key = "S0" + i;
				sMap.put(key, map.get(key));
				key = "B0" + i;
				sMap.put(key, map.get(key));
			}
			rMap.put("108", sMap);
			// 3d组三或者组六
			Map<String, String> zMap = new HashMap<String, String>();

			for (int i = 0; i <= 9; i++) {
				String key = "Z0" + i;
				zMap.put(key, map.get(key));
			}
			rMap.put("109", zMap);

			Map<String, String> hz6Map = new HashMap<String, String>();

			Map<String, String> hz3Map = new HashMap<String, String>();
			Map<String, String> hzMap = new HashMap<String, String>();

			// 3d直选和值
			for (int i = 0; i <= 27; i++) {
				String key = "H" + i;
				hzMap.put(key, map.get(key));
				hz3Map.put(key, map.get(key));
				hz6Map.put(key, map.get(key));
			}
			rMap.put("110", hzMap);

			// 3d组三和值
			hz3Map.remove("H0");
			hz3Map.remove("H27");
			rMap.put("111", hz3Map);

			// 3d组六和值
			hz6Map.remove("H0");
			hz6Map.remove("H1");
			hz6Map.remove("H2");
			
			hz6Map.remove("H25");
			hz6Map.remove("H26");
			hz6Map.remove("H27");
			
			rMap.put("112", hz6Map);

		}

		}

		return rMap;

	}

}
