package com.example.apitesting.domain;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

@Component
public class NewClass {

    @Async
    public void parallelTask(CountDownLatch latch) throws InterruptedException {
        System.out.println("-------------Child Thread :"+Thread.currentThread().getId()+" started--------------" );
        Thread.sleep(1000);
        System.out.println("-------------Child Thread :"+Thread.currentThread().getId()+" ended--------------" );
        latch.countDown();
    }

    @Async
    public void exceptionThrower(CountDownLatch latch) {
        try{
            throw new InterruptedException("Threading exception");
        }
        catch (Exception ex)
        {
            System.out.println(ex.getMessage());
        }
        finally {
            latch.countDown();
        }
    }

}
