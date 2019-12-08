package myMath;

import java.util.ArrayList;

public class PolynomTest {
	public static void main(String[] args) {
		test1();
		test2();
		constractorTest();
		copyTest();
		equalsTest();
		addTest();
		derivativeTest();
		isZeroTest();
		multiTest();
		fTest();
		rootTest();
		areaTest();
	}
	public static void test1() {
		System.out.println("\n**** test1 ****");
		Polynom p1 = new Polynom();
		String[] monoms = {"1","x","x^2", "0.5x^2"};
		for(int i=0;i<monoms.length;i++) {
			Monom m = new Monom(monoms[1]);
			p1.add(m);
			double aa = p1.area(0, 1, 0.0001);
			p1.substract(p1);
			System.out.println(p1);
		}
	}
	public static void test2() {
		System.out.println("\n**** test2 ****");
		Polynom p1 = new Polynom(), p2 =  new Polynom();
		String[] monoms1 = {"2", "-x","-3.2x^2","4","-1.5x^2"};
		String[] monoms2 = {"5", "1.7x","3.2x^2","-3","-1.5x^2"};
		for(int i=0;i<monoms1.length;i++) {
			Monom m = new Monom(monoms1[i]);
			p1.add(m);
		}
		for(int i=0;i<monoms2.length;i++) {
			Monom m = new Monom(monoms2[i]);
			p2.add(m);
		}
		System.out.println("p1: "+p1);
		System.out.println("p2: "+p2);
		p1.add(p2);
		System.out.println("p1+p2: "+p1);
		p1.multiply(p2);
		System.out.println("(p1+p2)*p2: "+p1);
		String s1 = p1.toString();
	}
	
	public static void constractorTest() {
		System.out.println("\n**** constractor test ****");
		String[] good = {"3x+4x", "-5.2-21.3x+x^6", "3x^2+5x+3", "0", ""};
		String[] bad = {"(23+x)*x^2", "abc", "a5x^b", "3x*2"};
		Polynom p;
		for (int i=0;i<good.length;i++) {
			String s = good[i];
			p = new Polynom(s);
			System.out.println(p.toString());
		}
		for (int i=0;i<bad.length;i++) {
			String s = bad[i];
			try {
			p = new Polynom(s);
			System.out.println(p.toString());
			}catch (Exception e) {
				System.out.println(s+" is a bad input.");
				continue;
			}
		}
	}
	
	public static void equalsTest() {
		System.out.println("\n**** equals test ****");
		String[] first = {"9+x-3.2x^3", "-3+4x^2", "3x^2-x^2", "0", ""};
		String[] second = {"4x^2-3", "2x^2", "3x^4+1.5", "0", ""};
		for (int i=0;i<first.length;i++) {
			for(int j=0;j<second.length;j++) {
				Polynom p1 = new Polynom(first[i]);
				Polynom p2 = new Polynom(second[j]);
				if (p1.equals(p2)) {
					System.out.println(p1.toString()+"="+p2.toString());
				}else System.out.println(p1.toString()+" isn't equals "+p2.toString());
			}
		}
	}
	
	public static void copyTest() {
		System.out.println("\n**** copy test ****");
		String[] polynoms = {"4x-3.5x", "3x+5.2x^6-9", "2","0", ""};
		for (int i=0;i<polynoms.length;i++) {
			Polynom p = new Polynom(polynoms[i]);
			if (p.equals(p.copy())) {
				System.out.println("copy worked");
			}else System.out.println("error");
		}
	}
	
	public static void addTest() {
		System.out.println("\n**** add & subtract test ****");
		String[] arr1 = {"3x-2.3", "-1+x^2+2.2x^3", ""};
		String[] arr2 = {"3x^2-5x", "-3+x", "5x-3x^2", ""};
		for (int i=0;i<arr1.length;i++) {
			for (int j=0;j<arr2.length;j++) {
				Polynom p1 = new Polynom(arr1[i]);
				Polynom p2 = new Polynom(arr2[j]);
				System.out.print(p1.toString()+"+("+p2.toString()+")=");
				p1.add(p2);
				System.out.println(p1);
				System.out.print(p1.toString()+"-("+p2.toString()+")=");
				p1.substract(p2);
				System.out.println(p1);
			}
		}
	}
	
	public static void derivativeTest() {
		System.out.println("\n**** derivative test ****");
		String[] arr = {"4+2+x^2", "5x-2", ""};
		for (int i=0;i<arr.length;i++) {
			Polynom p = new Polynom(arr[i]);
			System.out.print("the derivative of "+p.toString()+" is: ");
			Polynom der = new Polynom();
			der =(Polynom) p.derivative();
			System.out.println(der.toString());
		}
	}
	
	public static void isZeroTest() {
		String[] arr = {"3x-5.1", "2-x^2", "3x", "-0", "0",  ""}; // fix!!!!!!!!!!
		for (int i=0;i<arr.length;i++) {
			Polynom p = new Polynom(arr[i]);
			System.out.println(p.toString());
			if (p.isZero()) {
				System.out.println("true");
			}else System.out.println("false");
		}
	}
	
	public static void multiTest() {
		System.out.println("\n**** multiply test ****");
		String[] arr1 = {"3x^2-2", "2x", "0", ""};
		String[] arr2 = {"x-x^2", "3-x^2", "0", ""};
		for (int i=0;i<arr1.length;i++) {
			for (int j=0;j<arr2.length;j++) {
				Polynom p1 = new Polynom(arr1[i]);
				Polynom p2 = new Polynom(arr2[j]);
				Polynom_able pCopy = p1.copy();
				pCopy.multiply(p2);
				System.out.println(p1.toString()+"*"+p2.toString()+"="+pCopy.toString());
				for (int k=0;k<500;k++) {
					if (p1.f(k)*p2.f(k) != pCopy.f(k)) System.out.println("error");
				}
			}
		}
	}
	
	public static void fTest() {
		System.out.println("\n**** function test ****");
		String[] arr = {"x^2+2x+1", "3x-4", "5", "0", ""};
		for (int i=0;i<arr.length;i++) {
			Polynom p = new Polynom(arr[i]);
			System.out.println(p.toString()+" when x=5 equals:"+p.f(5));
		}
	}
	
	public static void rootTest() {
		System.out.println("\n**** root test ****");
		String[] good = {"4-3x", "x-1", "0", ""};
		String[] bad = {"2", "-1"}; //all Polynom in degree 1 (constants) that not equal 0 don't have a root.
		String[] tricky = {"x^2", "x^2-2x+1", "7.2x^13+2x^2+1"}; // the range can be problematic or don't have root. 
		for (int i=0;i<good.length;i++) {
			Polynom p = new Polynom(good[i]);
			double root = p.root(Integer.MAX_VALUE, Integer.MIN_VALUE, 0.000001);
			//x0 is bigger than x1 in the line above for check it's still working;
			System.out.println("approximate root of: "+ p.toString()+" equals "+root);
		}
		for (int i=0;i<bad.length;i++) {
			Polynom p = new Polynom(bad[i]);
			try {
			double root = p.root(Integer.MIN_VALUE, Integer.MAX_VALUE, 0.000001);
			System.out.println("approximate root of: "+ p.toString()+" equals "+root);
			}catch (RuntimeException e) {
				System.out.println(p.toString()+" don't have a root in the range [x0,x1]");
			}
		}
		Polynom p1 = new Polynom(tricky[0]);
		Polynom p2 = new Polynom(tricky[1]);
		Polynom p3 = new Polynom(tricky[2]);
		double root1 = p1.root(0, 0, 0.00001);
		double root2 = p2.root(-1, 1, 0.00001);
		double root3 = p3.root(Integer.MIN_VALUE, Integer.MAX_VALUE, 0.00001);
		System.out.println("root of: "+p1.toString()+" is "+root1);
		System.out.println("root of: "+p2.toString()+" is "+root2);
		System.out.println("root of: "+p3.toString()+" is "+root3);
	}
	
	public static void areaTest() {
		System.out.println("\n**** area test ****");
		String[] arr = {"2x^2-4x+1", "x-2", "5", "0", ""};
		for (int i=0;i<arr.length;i++) {
			Polynom p = new Polynom(arr[i]);
			double area = p.area(0, 1, 0.000001);
			System.out.println("the approximated area from 0 to 1 and between"
					+ " the x axle and the polynom: "+p.toString()+" is: "+area);
		}
	}
	
}
