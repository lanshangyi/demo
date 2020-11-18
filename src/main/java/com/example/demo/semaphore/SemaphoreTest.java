package com.example.demo.semaphore;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * 信号量实现线程同步
 * @author LShangyi
 *
 */
public class SemaphoreTest {
	
	public static void main(String[] args) {
		
		ExecutorService executorService = Executors.newCachedThreadPool();
        
        //信号量，只允许 1个线程同时访问
        Semaphore semaphore = new Semaphore(2);

        for (int i=0;i<10;i++){
            final long num = i;
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        //获取许可
                        semaphore.acquire();
                        //执行
                        System.out.println("循环体编号: " + num + " 获得了令牌许可，进入执行");
                        Thread.sleep(2000); // 模拟随机执行时长
                        //释放
                        semaphore.release();
                        System.out.println("循环体编号: " + num + " 执行完毕，释放令牌");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        executorService.shutdown();
		
	}
}
