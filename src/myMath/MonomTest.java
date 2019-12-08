package myMath;
import java.util.ArrayList;
/**
 * This class represents a simple (naive) tester for the Monom class, 
 * Note: <br>
 * (i) The class is NOT a JUNIT - (i.e., educational reasons) - should be changed to a proper JUnit in Ex1. <br>
 * (ii) This tester should be extend in order to test ALL the methods and functionality of the Monom class.  <br>
 * (iii) Expected output:  <br>
 * *****  Test1:  *****  <br>
0) 2.0    	isZero: false	 f(0) = 2.0  <br>
1) -1.0x    	isZero: false	 f(1) = -1.0  <br>
2) -3.2x^2    	isZero: false	 f(2) = -12.8  <br>
3) 0    	isZero: true	 f(3) = 0.0  <br>
*****  Test2:  *****  <br>
0) 0    	isZero: true  	eq: true  <br>
1) -1.0    	isZero: false  	eq: true  <br>
2) -1.3x    	isZero: false  	eq: true  <br>
3) -2.2x^2    	isZero: false  	eq: true  <br>
 */
public class MonomTest {
	public static void main(String[] args) {
		test1();
		test2();
		conTest();
		addTest();
		multiTest();
		equalsTest();
	}
	// test 'toString' and constructor that gets String
	private static void test1() {
		System.out.println("*****  Test1:  *****");
		String[] monoms = {"2", "-x","-3.2x^2","0"};
		for(int i=0;i<monoms.length;i++) {
			Monom m = new Monom(monoms[i]);
			String s = m.toString();
			m = new Monom(s);
			double fx = m.f(i);
			System.out.println(i+") "+m +"    \tisZero: "+m.isZero()+"\t f("+i+") = "+fx);
		}
	}

	private static void test2() {
		System.out.println("*****  Test2:  *****");
		ArrayList<Monom> monoms = new ArrayList<Monom>();
		monoms.add(new Monom(0,5));
		monoms.add(new Monom(-1,0));
		monoms.add(new Monom(-1.3,1));
		monoms.add(new Monom(-2.2,2));
		
		for(int i=0;i<monoms.size();i++) {
			Monom m = new Monom(monoms.get(i));
			String s = m.toString();
			Monom m1 = new Monom(s);
			boolean e = m.equals(m1);
			System.out.println(i+") "+m +"    \tisZero: "+m.isZero()+"  \teq: "+e);
		}
	}
	
	private static void conTest() {
		System.out.println("**** constractor test ****");
		String[] arr = {"3.2x^2", "-2.1x", "3", "0", ""};
		for (int i=0;i<arr.length;i++) {
			Monom m = new Monom(arr[i]);
			System.out.println(m.toString());
		}
	}
	//check 'add' 'toString' fuctions
	private static void addTest() {
		System.out.println("*****  addtion test:  *****");
		String[] monoms = {"2", "-x","-3.2x^2","0", "-1.6x^3", "-1.2x", "x^2"};
		String[] monoms1 = {"4", "-2x","-3x^2","2x", "x^3", "-1.2x", "x^2"};

		for(int i=0;i<monoms.length;i++) {
			Monom m = new Monom(monoms[i]);
			Monom k = new Monom(monoms1[i]);
			System.out.println("m is: "+ m.toString());
			System.out.println("k is: "+ k.toString());
			m.add(k);
			System.out.println("m+k is: "+ m.toString());
			}
	}
	
	private static void multiTest() {
		System.out.println("*****  multiply test:  *****");
		String[] monoms = {"2", "-x","-3.2x^2","0", "-1.6x^3", "-1.2x", "x^2"};
		String[] monoms1 = {"4", "-2x","-3x^2","2x", "x^3", "-1.2x", "x^2"};
		for(int i=0;i<monoms.length;i++) {
			Monom m = new Monom(monoms[i]);
			Monom k = new Monom(monoms1[i]);
			System.out.println("m is: "+ m.toString());
			System.out.println("k is: "+ k.toString());
			m.multipy(k);
			System.out.println("m*k is: "+ m.toString());
		}
	}
	
	private static void equalsTest() {
		System.out.println("**** equals test ****");
		String[] arr = {"-x^2", "3x", "3", "0", ""};
		for (int i=0;i<arr.length;i++) {
			Monom m = new Monom(arr[i]);
			if (m.equals(m)) {
				System.out.println(m.toString()+" equals "+m.toString());
			}else System.out.println(m.toString()+" isn't equals "+m.toString());
			Monom n = new Monom("3x");
			if (m.equals(n)) {
				System.out.println(m.toString()+" equals "+n.toString());
			}else System.out.println(m.toString()+" isn't equals "+n.toString());
		}
	}
}
