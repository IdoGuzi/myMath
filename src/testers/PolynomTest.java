package testers;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Iterator;

import org.junit.jupiter.api.Test;

import myMath.Monom;
import myMath.Polynom;
import myMath.Polynom_able;

class PolynomTest {

	@Test
	void testPolynomString() {
		String[] s = {"3x^2+2.1x-1", "3-2.3x", "4", ""};
		for (int i=0;i<s.length;i++) {
			try {
				Polynom p = new Polynom(s[i]);
			}catch(Exception e) {
				fail("Error: failed to create Polynom.");
			}
			
		}
	}

	@Test
	void testF() {
		String[] s = {"-3.2x^2+x-1", "2.3+4x^2", "2x", "4", "0", ""};
		double[] f2 = {-11.8, 18.3, 4, 4, 0, 0};
		double[] fn1 = {-5.2, 6.3, -2, 4, 0, 0};
		for (int i=0;i<s.length;i++) {
			Polynom p = new Polynom(s[i]);
			if (p.f(2)!=f2[i]) {
				fail("Error: unexpected result. got: f(2)="+p.f(2)+", exp="+f2[i]);
			}
			if (p.f(-1)!=fn1[i]) {
				fail("Error: unexpected result. got: f(-1)="+p.f(-1)+", exp="+fn1[i]);
			}
		}
	}

	@Test
	void testAddPolynom_able() {
		String[] s1 = {"x^2+5.1x-1", "4.2x^2-2x+5", "5.2x^2+3.1x+4"};
		String[] s2 = {"2.4x^4-x-3.45", "-2.3+5x-x^2", "2.4x^4-x^2+4x-5.75"};
		Polynom p1 = new Polynom(s1[0]);
		Polynom p2 = new Polynom(s1[1]);
		Polynom expected = new Polynom(s1[2]);
		p1.add(p2);
		if (!p1.equals(expected)) {
			fail("Error: polynoms should be equals. got: p1="+p1.toString()+", exp="+expected.toString());
		}
		p1 = new Polynom(s2[0]);
		p2 = new Polynom(s2[1]);
		expected = new Polynom(s2[2]);
		p1.add(p2);
		if (!p1.equals(expected)) {
			fail("Error: polynoms should be equals. got: p1="+p1.toString()+", exp="+expected.toString());
		}
	}

	@Test
	void testAddMonom() {
		String[][] s = {{"3x","-1","x^2","2.1x"},{"4.2x^2","-2x","5"},{"5.2x^2","3.1x","4"}};
		String[] shouldBe = {"x^2+5.1x-1", "4.2x^2-2x+5", "5.2x^2+3.1x+4"};
		for (int i=0;i<s.length;i++) {
			Polynom p = new Polynom();
			Polynom expected = new Polynom(shouldBe[i]);
			for (int j=0;j<s[i].length;j++) {
				Monom m = new Monom(s[i][j]);
				p.add(m);
			}
			if (!p.equals(expected)) {
				fail("Error: unexpected result. got: p="+p.toString()+", exp="+expected.toString());
			}
		}
	}

	@Test
	void testSubstract() {
		String[] s1 = {"x^2+5.1x-1", "4.2x^2-2x+5", "5.2x^2+3.1x+4"};
		String[] s2 = {"2.4x^4-x-3.45", "-2.3+5x-x^2", "2.4x^4-x^2+4x-5.75"};
		Polynom p1 = new Polynom(s1[2]);
		Polynom p2 = new Polynom(s1[1]);
		Polynom expected = new Polynom(s1[0]);
		p1.substract(p2);
		if (!p1.equals(expected)) {
			fail("Error: polynoms should be equals. got: p1="+p1.toString()+", exp="+expected.toString());
		}
		p1 = new Polynom(s2[2]);
		p2 = new Polynom(s2[1]);
		expected = new Polynom(s2[0]);
		p1.substract(p2);
		if (!p1.equals(expected)) {
			fail("Error: polynoms should be equals. got: p1="+p1.toString()+", exp="+expected.toString());
		}
	}

	@Test
	void testMultiplyPolynom_able() {
		String[] s1 = {"2x^3-5x", "3x-8", "6x^4-16x^3-15x^2+40x"};
		String[] s2 = {"2.1x-3", "3x^2+3.5", "6.3x^3+7.35x-9x^2-10.5"};
		Polynom p1 = new Polynom(s1[0]);
		Polynom p2 = new Polynom(s1[1]);
		Polynom expected = new Polynom(s1[2]);
		p1.multiply(p2);
		if (!p1.equals(expected)) {
			fail("Error: polynoms should be equals. got: p1="+p1.toString()+", exp="+expected.toString());
		}
		p1 = new Polynom(s2[0]);
		p2 = new Polynom(s2[1]);
		expected = new Polynom(s2[2]);
		p1.multiply(p2);
		if (!p1.equals(expected)) {
			fail("Error: polynoms should be equals. got: p1="+p1.toString()+", exp="+expected.toString());
		}
	}

	@Test
	void testEqualsPolynom_able() {
		String[] s1 = {"3x^3-x^2+1", "0.99999999-1.00000001x^2+2.99999999x^3", "3.000000001x^3-0.99999999x^2+1.000000001"};
		String[] s2 = {"4x^2-3.2+x", "-3.200000001+1.0000000001x+3.99999999x^2", "4.000000001x^2+0.999999999x-3.1999999999"};
		Polynom p1 = new Polynom(s1[0]);
		Polynom p2 = new Polynom(s1[1]);
		Polynom p3 = new Polynom(s1[2]);
		if (!p1.equals(p2)) {
			fail("Error: Polynoms should be equals. got: p1="+p1.toString()+", p2="+p2.toString());
		}
		if (!p1.equals(p3)) {
			fail("Error: Polynoms should be equals. got: p1="+p1.toString()+", p3="+p3.toString());
		}
		p1 = new Polynom(s2[0]);
		p2 = new Polynom(s2[1]);
		p3 = new Polynom(s2[2]);
		if (!p1.equals(p2)) {
			fail("Error: Polynoms should be equals. got: p1="+p1.toString()+", p2="+p2.toString());
		}
		if (!p1.equals(p3)) {
			fail("Error: Polynoms should be equals. got: p1="+p1.toString()+", p3="+p3.toString());
		}
	}

	@Test
	void testIsZero() {
		String s = "0+0x-0x^2+0x^5";
		Polynom p = new Polynom(s);
		if (!p.isZero()) {
			fail("Error: unexpected result p should be 0. got: p="+p.toString());
		}
	}

	@Test
	void testRoot() {
		String[] s = {"3x^2-2.5x-5", "6x^3-3.4x^2+2x+1.5", "5.2x^2-9x-1"};
		double[] r = {-0.9399017163, -0.36865, -0.104769};
		for (int i=0;i<s.length;i++) {
			Polynom p = new Polynom(s[i]);
			double root = p.root(-1, 1, 0.0000001);
			// if- p.root-expected root > epsilon: fail
			if (Math.abs(root-r[i])>=0.00001) {
				fail("Error: unexpected result. got: root="+root+", exp="+r[i]);
			}
		}
	}

	@Test
	void testCopy() {
		String[] s = {"2x^4-3.1x+1", "-x^2+4.8", "2.6x-9.4+0.5x^2"};
		Polynom sub = new Polynom("3x^2-3.2");
		for (int i=0;i<s.length;i++) {
			Polynom p = new Polynom(s[i]);
			Polynom_able copy = p.copy();
			copy.substract(sub);
			if (p.equals(copy)) {
				fail("Error: copy shouldn't be euqal p. got:"+p.toString()+", copy="+copy.toString());
			}
		}
	}

	@Test
	void testDerivative() {
		String[] s = {"3x^2-5.3x-1", "1.3x^3-3x+5", "8.3x^4+1.8x^2+x"};
		String[] d = {"6x-5.3", "3.9x^2-3", "33.2x^3+3.6x+1"};
		for (int i = 0; i < s.length; i++) {
			Polynom p = new Polynom(s[i]);
			Polynom_able der = p.derivative();
			Polynom expected = new Polynom(d[i]);
			if (!der.equals(expected)) {
				fail("Error: derivatives should be equals. got: der="+der.toString()+", exp="+expected.toString());
			}
		}
	}

	@Test
	void testArea() {
		String[] s = {"3.4x^2+3x-1", "1.6x^2-2.1x+3", "x^3+3x-2"};
		double[] a = {1.63333333, 2.48333333, -0.25};
		for (int i=0;i<a.length;i++) {
			Polynom p = new Polynom(s[i]);
			double area = p.area(0, 1, 0.000001);
			// if p.area-expected area >= epsilon: fail
			if (Math.abs(area-a[i])>=0.00001) {
				fail("Error: unexpected result. got: "+area+", exp="+a[i]);
			}
		}
	}

	@Test
	void testIteretor() {
		String[] s = {"3x", "4.1x^3", "5"};
		Polynom p = new Polynom("3x+4.1x^3+5");
		Iterator<Monom> iter = p.iteretor();
		Monom m1 = new Monom(s[0]), m2 = new Monom(s[1]), m3 = new Monom(s[2]);
		boolean flag = false;
		while(iter.hasNext()) {
			Monom m = iter.next();
			if ((!m.equals(m1)) && (!m.equals(m2)) && (!m.equals(m3))) {
				fail("Error: iterator didn't saw all elements.");
			}
		}
	}

	@Test
	void testMultiplyMonom() {
		String[] s = {"3x^2-2.1x+1", "4.2x^3-2x+1.5", "2.7x^2+x-5.3"};
		String[] mul = {"9x^4-6.3x^3+3x^2", "12.6x^5-6x^3+4.5x^2", "8.1x^4+3x^3-15.9x^2"};
		Monom m = new Monom(3,2);
		for (int i=0;i<mul.length;i++) {
			Polynom p = new Polynom(s[i]);
			Polynom expected = new Polynom(mul[i]);
			p.multiply(m);
			if (!p.equals(expected)) {
				fail("Error: Polynoms should be equals. got: p="+p.toString()+", exp="+expected.toString());
			}
		}
	}

	@Test
	void testToString() {
		String[] s = {"3.4x^3-2x+3", "4.3x^2+8x-5.1", "7.5-3x^4+x"};
		for (int i=0;i<s.length;i++) {
			Polynom p = new Polynom(s[i]);
			String ts = p.toString();
			if (!ts.equals(s[i])) {
				fail("Error: strings should incloud the same elements. got: ts="+ts+", exp"+s[i]);
			}
		}
	}

}
