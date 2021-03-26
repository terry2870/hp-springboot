package com.hp.springboot.schedule;

import java.util.function.Consumer;

/**
 * Hello world!
 *
 */
public class App {
	
	public static void main(String[] args) {
		Consumer<Integer> consumer = (x)->{
            int num = x * 1;
            System.out.println(num);        
        };        
        Consumer<Integer> consumer1 = (x) -> {            
            int num = x * 2;            
            System.out.println(num);        
        };        
        consumer.andThen(consumer1).accept(1);
	}
}
