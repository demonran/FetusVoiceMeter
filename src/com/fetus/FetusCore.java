package com.fetus;

public class FetusCore {
	static
	{
		System.loadLibrary("fetus_jni");
	}

	/*
	��������ʱ���øú�����������ʼ�����ֱ���
	*/
	public native static void init();


	/*
	�������ݻ���������ݣ���Ϊ���õ���44100����������16λ������lenÿ��Ӧ����ż��
	����1�������е�len���Ӧ��Ϊ44100*2 = 88200
	*/
	public native static void put(byte[] buf,int len);


	/*
	�ú�����Ҫ���ֻ�����������һ���߳������á�
	 */
	public native static void deal();

	/*
	�ú���������ȡ���������ֵ
	*/
	public native static  int get();




	//�ú���ֻ��һ�����Ժ����������Ǽ򵥵���ӡ���ͨ�������������
	public native static int add(int a,int b);
}
