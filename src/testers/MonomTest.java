package testers;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import myMath.Monom;

class MonomTest {

	@Test
	void testMonomDoubleInt() {
		double[] coef = {3.1, 0, -4.3};
		int[] power = {2, 0, 1, 7};
		for (int i=0;i<coef.length;i++) {
			for (int j=0;j<power.length;j++) {
				Monom m = new Monom(coef[i], power[j]);
				String s = coef[i]+"x^"+power[j];
				Monom expected = new Monom(s);
				if (!m.equals(expected)) {
					fail("Error: monoms should be equals. "
							+ "got: m="+m.toString()+", exp="+expected);
				}
			}
		}
	}

	@Test
	void testGet_coefficient() {
		String[] s = {"3.3x", "-8", "0", ""};
		double[] d = {3.3, -8, 0, 0};
		for (int i=0;i<s.length;i++) {
			Monom m = new Monom(s[i]);
			double c = m.get_coefficient(), expected = d[i];
			if (c!=expected) {
				fail("Error: c and d should be equals."
						+ "got: c="+c+", exp="+expected);
			}
		}
	}

	@Test
	void testGet_power() {
		String[] s = {"", "0", "x", "x^1", "x^3"};
		int[] i = {0, 0, 1, 1, 3};
		for (int j=0;j<s.length;j++) {
			Monom m = new Monom(s[j]);
			int p = m.get_power(), expected = i[j];
			if (p!=expected) {
				fail("Error: integers should be equals. "
						+ "got: p="+p+", exp="+expected);
			}
		}
	}

	@Test
	void testDerivative() {
		double[] coefs = {3.2, 1, 0, -2.4};
		int[] powers = {0, 1, 3, 8};
		for (int i=0;i<coefs.length;i++) {
			for (int j=0;j<powers.length;j++) {
				Monom m = new Monom(coefs[i], powers[j]);
				Monom d = m.derivative();
				double c = m.get_coefficient()*m.get_power();
				int p = m.get_power()-1;
				Monom expected;
				if (m.get_power()==0) {
					expected = new Monom(0, 0);
				}else expected = new Monom(m.get_coefficient()*m.get_power(), m.get_power()-1);
				if (!d.equals(expected)) {
					fail("Error: derivatives monoms should be equals. "
							+ "got: d="+d+", exp="+expected);
				}
			}
		}
	}

	@Test
	void testF() {
		String[] s = {"3.2x", "3", "2x^2"};
		double[] f2 = {6.4, 3, 8};
		double[] fn1 = {-3.2, 3, 2};
		for (int i=0;i<s.length;i++) {
			Monom m = new Monom(s[i]);
			if (m.f(2)!=f2[i]) {
				fail("Error: unexpected result for f(2). got:"+m.f(2)+", expected:"+f2[i]);
			}
			if (m.f(-1)!=fn1[i]) {
				fail("Error: unexpected result for f(-1). got:"+m.f(-1)+", expected:"+fn1[i]);
			}
		}
	}

	@Test
	void testIsZero() {
		String[] s = {"", "0", "0x", "0x^4"};
		int[] powers = {0, 1, 2, 3, 4, 5};
		for (int i=0;i<s.length;i++) {
			Monom m = new Monom(s[i]);
			if (!m.isZero()) {
				fail("Error: m should be equal 0. got:"+m.toString());
			}
		}
		for (int i=0;i<powers.length;i++) {
			Monom m = new Monom(0, powers[i]);
			if (!m.isZero()) {
				fail("Error: m should be equal 0. got:"+m.toString());
			}
		}
	}

	@Test
	void testMonomString() {
		String s = "3x^2";
		Monom m = new Monom(s);
		Monom expected = new Monom(3, 2);
		if (!m.equals(expected)) {
			fail("Error: the Monoms should be equal. "
					+ "got: m=" + m.toString() + ", exp=" + expected);
		}
	}

	@Test
	void testAdd() {
		double[] coefs = {3.2, 0, -1.4, 2};
		int[] powers = {0, 1, 2, 3, 4};
		for (int i=0;i<powers.length;i++) {
			for (int j=0;j<coefs.length;j++) {
				for (int k=0;k<coefs.length;k++) {
					Monom m1 = new Monom(coefs[j],powers[i]);
					Monom m2 = new Monom(coefs[k],powers[i]);
					m1.add(m2);
					Monom expected = new Monom((coefs[j]+coefs[k]), powers[i]);
					if (!m1.equals(expected)) {
						fail("Error: unexpected result. got: m1="+m1.toString()+", exp="+expected.toString());
					}
				}
			}
		}
	}

	@Test
	void testMultipy() {
		double[] coefs = {3.2, 0, -2.1, 7.1};
		int[] powers = {0, 1, 2, 3, 4};
		for (int i=0;i<coefs.length;i++) {
			for (int j=0;j<powers.length;j++) {
				for (int l=0;l<coefs.length;l++) {
					for (int k=0;k<powers.length;k++) {
						Monom m1 = new Monom(coefs[i], powers[j]);
						Monom m2 = new Monom(coefs[l], powers[k]);
						Monom expected = new Monom(coefs[i]*coefs[l], powers[j]+powers[k]);
						m1.multipy(m2);
						if (!m1.equals(expected)) {
							fail("Error: Monoms should be equals. got: m1="+m1.toString()+", exp="+expected.toString());
						}
					}
				}
			}
		}
	}

	@Test
	void testToString() {
		String[] s = {"", "0", "0x^3", "2x", "-3.1x^2"};
		String[] exp = {"0", "0", "0", "2x", "-3.1x^2"};
		for (int i=0;i<s.length;i++) {
			Monom m = new Monom(s[i]);
			if (!m.toString().equals(exp[i])){
				fail("Error: string should be the same. got:"+m.toString()+", exp:"+exp[i]);
			}
		}
	}

	@Test
	void testEqualsMonom() {
		double[] coefs = {3, 3.0000001, 2.9999999, 0, 0.00000001, -0.000000001, -2, -2.00000000001, -1.999999999};
		int[] powers = {0, 1, 2, 3, 4};
		for (int j=0;j<powers.length;j++) {
			for (int i=0;i<coefs.length;i=i+3) {
				Monom m1 = new Monom(coefs[i],powers[j]);
				Monom m2 = new Monom(coefs[i+1],powers[j]);
				Monom m3 = new Monom(coefs[i+2],powers[j]);
				if ((!m1.equals(m2))) {
					fail("Error: Monoms should be equals. got: m1="+m1.toString()+", m2="+m2.toString());
				}
				if ((!m1.equals(m3))) {
					fail("Error: Monoms should be equals. got: m1="+m1.toString()+", m3="+m3.toString());
				}
			}
		}
	}

}
