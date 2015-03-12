package com.fetus;

public class FetusCore {
	static
	{
		System.loadLibrary("fetus_jni");
	}

	/*
	程序启动时调用该函数，用来初始化各种变量
	*/
	public native static void init();


	/*
	向处理数据缓存放入数据，因为设置的是44100、单声道、16位，所以len每次应该是偶数
	而在1秒中所有的len相加应该为44100*2 = 88200
	*/
	public native static void put(byte[] buf,int len);


	/*
	该函数需要在手机程序中启动一个线程来调用。
	 */
	public native static void deal();

	/*
	该函数用来获取计算出的数值
	*/
	public native static  int get();




	//该函数只是一个测试函数，里面是简单的相加。先通过测试这个函数
	public native static int add(int a,int b);
}
