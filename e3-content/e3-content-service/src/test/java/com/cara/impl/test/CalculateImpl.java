package com.cara.impl.test;

import org.junit.Test;

import com.cara.interfa.test.Calculate;

public class CalculateImpl implements Calculate {

	@Override
	public int add(int a, int b) {
		// TODO Auto-generated method stub
		int c = a+b;
		return c;
	}

	@Test
	public void testAdd()
	{
		Calculate cal = new CalculateImpl();
		cal.add(1,2);
	}
}
