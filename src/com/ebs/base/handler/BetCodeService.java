package com.ebs.base.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.ebs.receiver.domain.OrderInfo;

//彩票投注号码处理
public class BetCodeService {

	public List<OrderInfo> getTicketInfoList(String lotteryCode,
			List<OrderInfo> orderInfoList) {
		List<OrderInfo> list = new ArrayList<OrderInfo>();
		// String lotteryCode=orderInfoList.get(0).getLotteryCode();
		if (!lotteryCode.equals("k3")) {
			return orderInfoList;
		}
		for (int i = 0; i < orderInfoList.size(); i++) {
			OrderInfo orderInfo = orderInfoList.get(i);
			if (orderInfo.getPlayType().equals("101")) {
				String anteCode[] = orderInfo.getNumber().split("&");
				List<String> codeList = new ArrayList<String>();
				List<String> anteList = new ArrayList<String>();
				for (int j = 0; j < anteCode.length; j++) {
					if (Integer.parseInt(anteCode[j]) == 3
							|| Integer.parseInt(anteCode[j]) == 18) {
						codeList.add(anteCode[j]);
					} else {
						anteList.add(anteCode[j]);
					}
				}
				if (codeList.isEmpty()) {

					list.add(orderInfo);
					// list.add(getOrderInfo(orderInfo, anteList));
					// 无需特殊处理
				} else {
					if (!anteList.isEmpty()) {
						list.add(getOrderInfo(orderInfo, anteList));
					}
					String c = "";
					for (int h = 0; h < codeList.size(); h++) {

						if (Integer.parseInt(codeList.get(h)) == 3) {
							c += "1,1,1" + "&";
						}
						if (Integer.parseInt(codeList.get(h)) == 18) {
							c += "6,6,6" + "&";
						}
					}
					c = c.substring(0, c.length() - 1);
					orderInfo.setPlayType("103");
					int count = codeList.size()
							* Integer.parseInt(orderInfo.getMultiple());
					orderInfo.setCount(String.valueOf(count));
					orderInfo.setNumber(c);
					orderInfo.setMoney(String.valueOf(count * 2));
					// double amount = codeList.size()*
					// Double.parseDouble(orderInfo.getMultiple()) * 2;
					// orderInfo.setCount(String.valueOf(codeList.size()));
					// orderInfo.setMoney(String.valueOf(amount));
					// orderInfo.setNumber(c);
					list.add(orderInfo);
				}
			} else {
				list.add(orderInfo);
			}
		}
		return list;

	}

	public OrderInfo getOrderInfo(OrderInfo orderInfo, List<String> codeList) {
		String code = "";

		if (codeList.isEmpty()) {
			return orderInfo;
		}
		OrderInfo info = new OrderInfo();
		for (int i = 0; i < codeList.size(); i++) {
			code += codeList.get(i) + "&";

		}
		code = code.substring(0, code.length() - 1);

		info.setNumber(code);
		double money = codeList.size()
				* Double.parseDouble((orderInfo.getMultiple())) * 2;
		info.setMoney(String.valueOf(money));
		info.setCount(String.valueOf(codeList.size()));
		info.setLotteryCode(orderInfo.getLotteryCode());
		info.setPeriodCode(orderInfo.getPeriodCode());
		info.setPlayType(orderInfo.getPlayType());
		info.setMultiple(orderInfo.getMultiple());
		return info;
	}

	public List<OrderInfo> getCodeList(OrderInfo orderInfo) {
		List<OrderInfo> list = new ArrayList<OrderInfo>();
		if (orderInfo.getLotteryCode().equals("k3")) {
			switch (orderInfo.getPlayType()) {
			case "101":// 和值处理
				return getList_K3_101(orderInfo);
			case "102":
			case "103":
			case "105":// 三同号通选 三同号单选 三连号通选
			{
				String code[] = orderInfo.getNumber().split("\\&");
				int count = code.length
						* Integer.parseInt(orderInfo.getMultiple());
				orderInfo.setCount(String.valueOf(count));
				orderInfo.setMoney(String.valueOf(count * 2));
				//orderInfo.setMargins(orderInfo.getMargins());
				//orderInfo.setTotalCastMoney(orderInfo.getTotalCastMoney());
				list.add(orderInfo);
				return list;
			}
			case "104": {
				String code[] = orderInfo.getNumber().split("\\&");
				if (code.length == 1) {
					String c[] = code[0].split(",");
					if (c.length == 3) {
						int count = code.length
								* Integer.parseInt(orderInfo.getMultiple());
						orderInfo.setCount(String.valueOf(count));
						orderInfo.setMoney(String.valueOf(count * 2));
						list.add(orderInfo);
					} else {
						int count = combination(c.length, 3);
						orderInfo.setCount(String.valueOf(count));
						orderInfo.setCount(String.valueOf(count));
						orderInfo.setMoney(String.valueOf(count * 2));
						list.add(orderInfo);
					}

				} else {
					int count = code.length
							* Integer.parseInt(orderInfo.getMultiple());
					orderInfo.setCount(String.valueOf(count));
					orderInfo.setMoney(String.valueOf(count*2));
				   list.add(orderInfo);	
				}
				return list;
			}
			case "106": {
				String code[] = orderInfo.getNumber().split("\\&");
				if (code.length == 1) {
					String c[] = code[0].split(",");
					if (c.length == 1) {
						int count = code.length
								* Integer.parseInt(orderInfo.getMultiple());
						orderInfo.setCount(String.valueOf(count));
						orderInfo.setMoney(String.valueOf(count * 2));
						list.add(orderInfo);
					} else {
						int count = combination(c.length, 1);
						orderInfo.setCount(String.valueOf(count));
						orderInfo.setCount(String.valueOf(count));
						orderInfo.setMoney(String.valueOf(count * 2));
						list.add(orderInfo);
					}

				} else {
					int count = code.length
							* Integer.parseInt(orderInfo.getMultiple());
					orderInfo.setCount(String.valueOf(count));
					orderInfo.setMoney(String.valueOf(count * 2));
					list.add(orderInfo);
				}
				return list;

			}
			case "107": {
				String code[] = orderInfo.getNumber().split("\\#");
				if (code.length == 1) {
					String c[] = code[0].split("\\|")[0].split(",");
					if (c.length == 1) {
						int count = code.length
								* Integer.parseInt(orderInfo.getMultiple());
						orderInfo.setCount(String.valueOf(count));
						orderInfo.setMoney(String.valueOf(count * 2));
						list.add(orderInfo);
					} else {
						int count = combination(c.length, 1);
						orderInfo.setCount(String.valueOf(count));
						orderInfo.setCount(String.valueOf(count));
						orderInfo.setMoney(String.valueOf(count * 2));
						list.add(orderInfo);
					}

				} else {
					int count = code.length
							* Integer.parseInt(orderInfo.getMultiple());
					orderInfo.setCount(String.valueOf(count));
					orderInfo.setMoney(String.valueOf(count * 2));
					list.add(orderInfo);
				}
				return list;

			}
			case "108": {
				String code[] = orderInfo.getNumber().split("\\&");
				if (code.length == 1) {
					String c[] = code[0].split(",");
					if (c.length == 2) {
						int count = code.length
								* Integer.parseInt(orderInfo.getMultiple());
						orderInfo.setCount(String.valueOf(count));
						orderInfo.setMoney(String.valueOf(count * 2));
						list.add(orderInfo);
					} else {
						int count = combination(c.length, 2);
						orderInfo.setCount(String.valueOf(count));
						orderInfo.setCount(String.valueOf(count));
						orderInfo.setMoney(String.valueOf(count * 2));
						list.add(orderInfo);
					}

				} else {
					int count = code.length
							* Integer.parseInt(orderInfo.getMultiple());
					orderInfo.setCount(String.valueOf(count));
					orderInfo.setMoney(String.valueOf(count * 2));
					list.add(orderInfo);
				}
				return list;
			}
			}

		} else if (orderInfo.getLotteryCode().equals("ssq")) {
			switch (orderInfo.getPlayType()) {
			case "101":// 单式
			{
				int count = Integer.parseInt(orderInfo.getMultiple());
				orderInfo.setCount(String.valueOf(count));
				orderInfo.setMoney(String.valueOf(count * 2));
				list.add(orderInfo);
			}
			case "102":// 复式
			{
				String code[] = orderInfo.getNumber().split("\\#");
				if (code.length == 2) {
					String c[] = code[0].split(",");
					String b[] = code[1].split(",");
					int count = combination(c.length, 6);
					int countb = combination(b.length, 1);
					orderInfo
							.setCount(String.valueOf(Integer.parseInt(orderInfo
									.getMultiple()) * count * countb));
					orderInfo.setMoney(String.valueOf(count * countb * 2));
					list.add(orderInfo);
				}
			}
			case "103":// 胆拖
			{
				String code[] = orderInfo.getNumber().split("\\#");
				if (code.length == 2) {
					String c[] = code[0].split("\\$");
					String b[] = code[1].split(",");
					int count = Integer.parseInt(orderInfo.getMultiple())
							* combination(c[1].split(",").length,
									(6 - c[0].split(",").length))
							* combination(b.length, 1);
					orderInfo.setCount(String.valueOf(count));
					orderInfo.setMoney(String.valueOf(count * 2));
					list.add(orderInfo);
				}
			}
			}
		} else if (orderInfo.getLotteryCode().equals("3d")) {

			switch (orderInfo.getPlayType()) {
			case "101":// 单式
			{
				int count = Integer.parseInt(orderInfo.getMultiple());
				orderInfo.setCount(String.valueOf(count));
				orderInfo.setMoney(String.valueOf(count * 2));
				list.add(orderInfo);
			}
			case "102":// 复式
			{
				String code[] = orderInfo.getNumber().split("\\#");
				int count = Integer.parseInt(orderInfo.getMultiple())
						* combination(code[0].split(",").length, 1)
						* combination(code[1].split(",").length, 1)
						* combination(code[2].split(",").length, 1);
				orderInfo.setCount(String.valueOf(count));
				orderInfo.setMoney(String.valueOf(count * 2));
				list.add(orderInfo);
			}

			case "103":// 和值
			{
				String code[] = orderInfo.getNumber().split(",");
				int count = 0;
				for (int i = 0; i < code.length; i++) {
					count += get_zhi_3d_count(code[i]);
				}
				// int count = combination(code[1].split(",").length, (7 -
				// code[0].split(",").length)) ;
				orderInfo.setCount(String.valueOf(Integer.parseInt(orderInfo
						.getMultiple()) * count));
				orderInfo.setMoney(String.valueOf(count * 2));
				list.add(orderInfo);
			}
			case "201":// 组三单式
			{
				int count = Integer.parseInt(orderInfo.getMultiple());
				orderInfo.setCount(String.valueOf(count));
				orderInfo.setMoney(String.valueOf(count * 2));
				list.add(orderInfo);
			}
			case "202":// 组三复式
			{
				String code[] = orderInfo.getNumber().split(",");
				int count = combination(code.length, 2)
						* Integer.parseInt(orderInfo.getMultiple()) * 2;
				orderInfo.setCount(String.valueOf(count));
				orderInfo.setMoney(String.valueOf(count * 2));
				list.add(orderInfo);
			}
			case "203":// 组三和值
			{
				String code[] = orderInfo.getNumber().split(",");
				int count = 0;
				for (int i = 0; i < code.length; i++) {
					count += get_3_3d_count(code[i]);
				}
				orderInfo.setCount(String.valueOf(Integer.parseInt(orderInfo
						.getMultiple()) * count));
				orderInfo.setMoney(String.valueOf(count * 2));
				list.add(orderInfo);
			}
			case "301":// 组六单式
			{
				int count = Integer.parseInt(orderInfo.getMultiple());
				orderInfo.setCount(String.valueOf(count));
				orderInfo.setMoney(String.valueOf(count * 2));
				list.add(orderInfo);
			}
			case "302":// 组六复式
			{
				String code[] = orderInfo.getNumber().split(",");
				int count = combination(code.length, 3)
						* Integer.parseInt(orderInfo.getMultiple());
				orderInfo.setCount(String.valueOf(count));
				orderInfo.setMoney(String.valueOf(count * 2));
				list.add(orderInfo);
			}
			case "303":// 组六和值
			{
				String code[] = orderInfo.getNumber().split(",");
				int count = 0;
				for (int i = 0; i < code.length; i++) {
					count += get_6_3d_count(code[i]);
				}
				orderInfo.setCount(String.valueOf(Integer.parseInt(orderInfo
						.getMultiple()) * count));
				orderInfo.setMoney(String.valueOf(count * 2));
				list.add(orderInfo);
			}
			}
		} else if (orderInfo.getLotteryCode().equals("307")) {
			switch (orderInfo.getPlayType()) {
			case "101":// 单式
			{
				int count = Integer.parseInt(orderInfo.getMultiple());
				orderInfo.setCount(String.valueOf(count));
				orderInfo.setMoney(String.valueOf(count * 2));
				list.add(orderInfo);
			}
			case "102":// 复式
			{
				String code[] = orderInfo.getNumber().split(",");
				int count = Integer.parseInt(orderInfo.getMultiple())
						* combination(code.length, 7);
				orderInfo.setCount(String.valueOf(count));
				orderInfo.setMoney(String.valueOf(count * 2));
				list.add(orderInfo);
			}

			case "103":// 胆拖
			{
				String code[] = orderInfo.getNumber().split("\\$");
				if (code.length == 2) {
					int count = Integer.parseInt(orderInfo.getMultiple())
							* combination(code[1].split(",").length,
									(7 - code[0].split(",").length));
					orderInfo.setCount(String.valueOf(count));
					orderInfo.setMoney(String.valueOf(count * 2));
					list.add(orderInfo);
				}
			}
			}
		}

		return list;

	}

	// 快3和值拆分
	public List<OrderInfo> getList_K3_101(OrderInfo orderInfo) {
		List<OrderInfo> list = new ArrayList<OrderInfo>();
		String anteCode[] = orderInfo.getNumber().split("&");
		List<String> codeList = new ArrayList<String>();
		List<String> anteList = new ArrayList<String>();
		for (int j = 0; j < anteCode.length; j++) {
			if (Integer.parseInt(anteCode[j]) == 3
					|| Integer.parseInt(anteCode[j]) == 18) {
				codeList.add(anteCode[j]);
			} else {
				anteList.add(anteCode[j]);
			}
		}
		if (codeList.isEmpty()) {

			list.add(getOrderInfo(orderInfo, anteList));
			// 无需特殊处理
		} else {
			if (!anteList.isEmpty()) {
				list.add(getOrderInfo(orderInfo, anteList));
			}
			String c = "";
			for (int h = 0; h < codeList.size(); h++) {

				if (Integer.parseInt(codeList.get(h)) == 3) {
					c += "1,1,1" + "&";
				}
				if (Integer.parseInt(codeList.get(h)) == 18) {
					c += "6,6,6" + "&";
				}
			}
			c = c.substring(0, c.length() - 1);
			orderInfo.setPlayType("103");
			int count = codeList.size()
					* Integer.parseInt(orderInfo.getMultiple());
			orderInfo.setCount(String.valueOf(count));
			orderInfo.setNumber(c);
			orderInfo.setMoney(String.valueOf(count * 2));
			list.add(orderInfo);
		}

		return list;
	}

	public String getBetMoney(int num, String multiple) {
		Double amount = Double.parseDouble(multiple) * num * 2;
		return String.valueOf(amount);
	}

	public static int combination(int num1, int num2) // num1>num2 排列组合
	{
		int sum1 = num1;
		int sum2 = num2;
		while (num2 > 1) {
			num1--;
			num2--;
			sum1 *= num1;
			sum2 *= num2;
		}
		return sum1 / sum2;
	}

	// 3D直选和值
	public static int get_zhi_3d_count(String code) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("0", "1");
		map.put("1", "3");
		map.put("2", "6");
		map.put("3", "10");
		map.put("4", "15");
		map.put("5", "21");
		map.put("6", "28");
		map.put("7", "36");
		map.put("8", "45");
		map.put("9", "55");
		map.put("10", "63");
		map.put("11", "69");
		map.put("12", "73");
		map.put("13", "75");
		map.put("14", "75");
		map.put("15", "73");
		map.put("16", "69");
		map.put("17", "63");
		map.put("18", "55");
		map.put("19", "45");
		map.put("20", "36");
		map.put("21", "28");
		map.put("22", "21");
		map.put("23", "15");
		map.put("24", "10");
		map.put("25", "6");
		map.put("26", "3");
		map.put("27", "1");
		Set<String> set = map.keySet();
		for (Iterator<String> iter = set.iterator(); iter.hasNext();) {
			String key = (String) iter.next();
			if (code.equals(key)) {
				String value = (String) map.get(key);
				return Integer.parseInt(value);
			}
		}
		return 0;
	}

	// 组三和值
	public static int get_3_3d_count(String code) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("1", "1");
		map.put("2", "2");
		map.put("3", "1");
		map.put("4", "3");
		map.put("5", "3");
		map.put("6", "3");
		map.put("7", "4");
		map.put("8", "5");
		map.put("9", "4");
		map.put("10", "5");
		map.put("11", "5");
		map.put("12", "4");
		map.put("13", "5");
		map.put("14", "5");
		map.put("15", "4");
		map.put("16", "10");
		map.put("17", "10");
		map.put("18", "4");
		map.put("19", "5");
		map.put("20", "4");
		map.put("21", "3");
		map.put("22", "3");
		map.put("23", "3");
		map.put("24", "2");
		map.put("25", "2");
		map.put("26", "1");
		Set<String> set = map.keySet();
		for (Iterator<String> iter = set.iterator(); iter.hasNext();) {
			String key = (String) iter.next();
			if (code.equals(key)) {
				String value = (String) map.get(key);
				return Integer.parseInt(value);
			}
		}
		return 0;
	}

	// 组六和值
	public static int get_6_3d_count(String code) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("3", "1");
		map.put("4", "1");
		map.put("5", "2");
		map.put("6", "3");
		map.put("7", "4");
		map.put("8", "5");
		map.put("9", "7");
		map.put("10", "8");
		map.put("11", "9");
		map.put("12", "10");
		map.put("13", "10");
		map.put("14", "10");
		map.put("15", "10");
		map.put("16", "9");
		map.put("17", "8");
		map.put("18", "7");
		map.put("19", "5");
		map.put("20", "4");
		map.put("21", "3");
		map.put("22", "2");
		map.put("23", "1");
		map.put("24", "1");
		Set<String> set = map.keySet();
		for (Iterator<String> iter = set.iterator(); iter.hasNext();) {
			String key = (String) iter.next();
			if (code.equals(key)) {
				String value = (String) map.get(key);
				return Integer.parseInt(value);
			}
		}
		return 0;
	}
}
