package testers;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import myMath.ComplexFunction;
import myMath.Monom;
import myMath.Operation;
import myMath.Polynom;
import myMath.function;

class ComplexFunctionTest {

	@Test
	void testComplexFunction() {
		function f1 = new Polynom("3x^4-2.4");
		function f2 = new Polynom("3.1+x^2");
		Operation op = Operation.Plus;
		try {
			ComplexFunction cf = new ComplexFunction(op,f1,f2);
			Polynom f3 = new Polynom("5x-2");
			cf = new ComplexFunction(op,cf,f3);
		}catch (Exception e) {
			fail("Error: couldn't make cf");
		}
	}

	@Test
	void testF() {
		ComplexFunction cf = new ComplexFunction(Operation.Times, new Polynom("3x^2"), new Polynom("5.2x"));
		double f1 = 2, f2 = -1;
		double exp1 = 124.8, exp2 = -15.6;
		if (Math.abs(cf.f(f1)-exp1) >= Monom.EPSILON) {
			fail("Error:unexpected result for x=2. got: "+cf.f(f1)+", exp: "+exp1);
		}
		if (Math.abs(cf.f(f2)-exp2) >= Monom.EPSILON) {
			fail("Error:unexpected result for x=-1. got: "+cf.f(f2)+", exp: "+exp2);
		}
	}

	@Test
	void testInitFromString() {
		String s = "comp(div(plus(3.4x^4,2x),mul(min(12x,2x^2 +2),-1.5)),mul(plus(4x,-2.2x),2x))";
		ComplexFunction cf = new ComplexFunction(s);
		System.out.println(s+"***"+cf.toString());
		if (cf.toString().equals(s)) {
			fail("Error: string aren't equals. got: "+cf.toString()+", exp: "+s);
		}
	}

	@Test
	void testCopy() {
		ComplexFunction cf = new ComplexFunction("mul(3x^2,plus(2,3x))");
		ComplexFunction f = (ComplexFunction)cf.copy();
		f.plus(new Polynom("4.2x"));
		if (cf.equals(f)) {
			fail("Error: not a deep copy.");
		}
		f = (ComplexFunction) cf.copy();
		if (!cf.equals(f)) {
			fail("Error: not a copy.");
		}
	}

	@Test
	void testPlus() {
		ComplexFunction cf = new ComplexFunction("mul(3x,2)");
		ComplexFunction exp = new ComplexFunction("plus(mul(3x,2),4x^2)");
		cf.plus(new Polynom("4x^2"));
		if (!cf.equals(exp)) {
			fail("Error; unexpected result.");
		}
	}

	@Test
	void testMul() {
		ComplexFunction cf = new ComplexFunction("mul(3x,2)");
		ComplexFunction exp = new ComplexFunction("mul(mul(3x,2),4x^2)");
		cf.mul(new Polynom("4x^2"));
		if (!cf.equals(exp)) {
			fail("Error; unexpected result.");
		}
	}

	@Test
	void testDiv() {
		ComplexFunction cf = new ComplexFunction("mul(3x,2)");
		ComplexFunction exp = new ComplexFunction("div(mul(3x,2),4x^2)");
		cf.div(new Polynom("4x^2"));
		if (!cf.equals(exp)) {
			fail("Error; unexpected result.");
		}
	}

	@Test
	void testMax() {
		ComplexFunction cf = new ComplexFunction("mul(3x,2)");
		ComplexFunction exp = new ComplexFunction("max(mul(3x,2),4x^2)");
		cf.max(new Polynom("4x^2"));
		if (!cf.equals(exp)) {
			fail("Error; unexpected result.");
		}
	}

	@Test
	void testMin() {
		ComplexFunction cf = new ComplexFunction("mul(3x,2)");
		ComplexFunction exp = new ComplexFunction("min(mul(3x,2),4x^2)");
		cf.min(new Polynom("4x^2"));
		if (!cf.equals(exp)) {
			fail("Error; unexpected result.");
		}
	}

	@Test
	void testComp() {
		ComplexFunction cf = new ComplexFunction("mul(3x,2)");
		ComplexFunction exp = new ComplexFunction("comp(mul(3x,2),4x^2)");
		cf.comp(new Polynom("4x^2"));
		if (!cf.equals(exp)) {
			fail("Error; unexpected result.");
		}
	}

}
