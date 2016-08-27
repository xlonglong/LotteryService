package com.ebs.receiver.init;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.log4j.Logger;


public class Mapx  extends LinkedHashMap{
	private static final float DEFAULT_LOAD_FACTOR = 0.75f;

	private static final int DEFAULT_INIT_CAPACITY = 16;

	private static final long serialVersionUID = 200904201752L;

	private final int maxCapacity;

	private final boolean maxFlag;

	private int hitCount = 0;

	private int missCount = 0;

	private long lastWarnTime = 0;

	private ExitEventListener listener;
	
	private static Logger logger = Logger.getLogger(Mapx.class);

	/**
	 * ������������Ƶ�HashMap,��LRUFlagΪtrueʱ��LRU�㷨���뻻��,Ϊfalseʱ��FIFO�㷨���뻻��
	 */
	public Mapx(int maxCapacity, boolean LRUFlag) {
		super(maxCapacity, DEFAULT_LOAD_FACTOR, LRUFlag);
		this.maxCapacity = maxCapacity;
		maxFlag = true;
	}

	/**
	 * ��ֵ��˳��,���������,ʹ��LRU�㷨���뻻����HashMap
	 */
	public Mapx(int maxCapacity) {
		this(maxCapacity, true);
	}

	/**
	 * ��ֵ�������Ⱥ�˳�������HashMap,û����������,��֧�ֻ��뻻��
	 */
	public Mapx() {
		super(DEFAULT_INIT_CAPACITY, DEFAULT_LOAD_FACTOR, false);
		maxCapacity = 0;
		maxFlag = false;
	}

	/**
	 * �ݹ�Clone,��ֹMapx����Mapxʱ����ͬ������
	 */
	public Object clone() {
		Mapx map = (Mapx) super.clone();
		Object[] ks = keyArray();
		Object[] vs = valueArray();
		for (int i = 0; i < ks.length; i++) {
			Object v = vs[i];
			if (v instanceof Mapx) {
				map.put(ks[i], ((Mapx) v).clone());
			}
		}
		return map;
	}

	protected boolean removeEldestEntry(Map.Entry eldest) {
		boolean flag = maxFlag && size() > maxCapacity;
		if (flag && listener != null) {
			listener.onExit(eldest.getKey(), eldest.getValue());
		}
		return flag;
	}

	/**
	 * ���û����¼�������,����ֵ�ӻ�������
	 */
	public void setExitEventListener(ExitEventListener listener) {
		this.listener = listener;
	}

	public Object[] keyArray() {
		if (size() == 0) {
			return new Object[0];
		}
		Object[] arr = new Object[size()];
		int i = 0;
		for (Iterator iter = this.keySet().iterator(); iter.hasNext();) {
			arr[i++] = iter.next();
		}
		return arr;
	}

	public Object[] valueArray() {
		if (size() == 0) {
			return new Object[0];
		}
		Object[] arr = new Object[size()];
		int i = 0;
		for (Iterator iter = this.values().iterator(); iter.hasNext();) {
			arr[i++] = iter.next();
		}
		return arr;
	}

	public String getString(Object key) {
		Object o = get(key);
		if (o == null) {
			return null;
		} else {
			return o.toString();
		}
	}

	public void put(Object key, int num) {
		put(key, new Integer(num));
	}

	public void put(Object key, long num) {
		put(key, new Long(num));
	}

	public int getInt(Object key) {
		Integer obj = (Integer) get(key);
		if (obj == null) {
			return 0;
		}
		return obj.intValue();
	}

	public long getLong(Object key) {
		Long obj = (Long) get(key);
		if (obj == null) {
			return 0;
		}
		return obj.longValue();
	}

	/**
	 * Ϊʵ��������ͳ��,���Ǵ˷���.
	 */
	public Object get(Object key) {
		Object o = super.get(key);
		if (maxFlag) {
			if (o == null) {
				missCount++;
			} else {
				hitCount++;
			}
			if (missCount > 1000 && hitCount * 1.0 / missCount < 0.5) {// �����ʵ���1/3ʱÿСʱ����
				if (System.currentTimeMillis() - lastWarnTime > 1000000) {
					lastWarnTime = System.currentTimeMillis();
					StackTraceElement stack[] = (new Throwable()).getStackTrace();
					StringBuffer sb = new StringBuffer();
					for (int i = 0; i < stack.length; i++) {
						StackTraceElement ste = stack[i];
						if (ste.getClassName().indexOf("DBConnPoolImpl") == -1) {
							sb.append("\t");
							sb.append(ste.getClassName());
							sb.append(".");
							sb.append(ste.getMethodName());
							sb.append("(),�к�:");
							sb.append(ste.getLineNumber());
							sb.append("\n");
						}
					}
					logger.warn("���������ʹ���!");
					logger.warn(sb);
				}
			}
		}
		return o;
	}

	public static Mapx convertToMapx(Map map) {
		Mapx mapx = new Mapx();
		mapx.putAll(map);
		return mapx;
	}
}
