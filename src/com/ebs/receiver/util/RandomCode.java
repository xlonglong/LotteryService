package com.ebs.receiver.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class RandomCode {
	 private  final static int MAXRANDOMNUM = 999999;
    private Random random = new Random();
   private static  List<Integer> randomList = new ArrayList();
   private static  Date lastRandomDate = new Date();

   public RandomCode() {
       refreshArray();
   }

   private static  void refreshArray() {
       randomList.clear();
       for (int i = 0; i < MAXRANDOMNUM; i++) {
           randomList.add(i);
       }
          lastRandomDate = new Date();
   }

   private static  boolean isNeedToRefresh() {
       Calendar cal = Calendar.getInstance();
       cal.set(Calendar.HOUR_OF_DAY, 0);
       cal.set(Calendar.MINUTE, 0);
       cal.set(Calendar.SECOND, 0);
       cal.set(Calendar.MILLISECOND, 0);

       long startOfDay = cal.getTimeInMillis();

       if (startOfDay > lastRandomDate.getTime()) {
           return true;
       } else  if (randomList.isEmpty()) {
           return true;
       } else {
           return false;
       }
   }

   public synchronized static  String getRandomCode() {
       if (isNeedToRefresh()) {
           refreshArray();
       }
       int temp = 0;
       int randomNumber;
      // temp = random.nextInt(randomList.size());
       randomNumber = randomList.get(temp);
       randomList.remove(temp);

       return String.valueOf(randomNumber + 100001);

   }
   
   public static void main(String[] args) {
	
		String str = getRandomCode();
		System.out.println(str);
	}
}
