package com.example.demo.lockTest;

import com.example.demo.utils.KeyLock;

public class LockTest {
	
	public static void main(String[] args) {
		
		TestClass target = new TestClass("LShangyi");
		
		TestClass target2 = new TestClass("LJunming");
		
		for(int i=0;i<5;i++) {
			
			Thread thread = new Thread(target);
			thread.start();
			
			Thread thread2 = new Thread(target2);
			thread2.start();
		}
		
	}
}

class TestClass implements Runnable{
	
	private KeyLock<String> lock = new KeyLock<String>();
	private String name;
	
	TestClass(String name){
		this.name = name;
	}
	
	public void run() {
		
		System.out.println(name + " 程序等待");
		
		lock.lock(name);  //上锁
		
		lock.getLockCount(name); //查看等待执行的key线程数量
		
		System.out.println(name + " 程序执行中");
		
		try {
			Thread.sleep(2000); //休眠2秒
			
			System.out.println(name + " 执行完毕");
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			lock.unlock(name);  //解锁
		}
		
	}
}
