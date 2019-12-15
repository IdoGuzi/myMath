package myMath;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.function.Predicate;

import myMath.Monom;
/**
 * This class represents a Polynom with add, multiply functionality, it also should support the following:
 * 1. Riemann's Integral: https://en.wikipedia.org/wiki/Riemann_integral
 * 2. Finding a numerical value between two values (currently support root only f(x)=0).
 * 3. Derivative
 * 
 * @author Boaz
 *
 */
public class Polynom implements Polynom_able{
	private ArrayList<Monom> Poly;
	/**
	 * Zero (empty polynom)
	 */
	public Polynom() {
		this.Poly = new ArrayList<Monom>();
	}
	/**
	 * init a Polynom from a String such as:
	 *  {"x", "3+1.4X^3-34x", "(2x^2-4)*(-1.2x-7.1)", "(3-3.4x+1)*((3.1x-1.2)-(3X^2-3.1))"};
	 *  the format of Polynom: a(1)x^b(1)+...+a(n)x^b(n).
	 * @param s: is a string represents a Polynom
	 */
	public Polynom(String s) {
		s = s.replaceAll("\\s", "");
		this.Poly = new ArrayList<Monom>();
		if (s.length() != 0) {
			if (s.indexOf('*')!=-1 || s.indexOf('(')!=-1 || s.indexOf(')')!=-1) {
				System.out.println(s);
				throw new IllegalArgumentException("invalid input");
			}
			int checkPoint = 0;
			for (int i=1;i<s.length();i++) {
				if (s.charAt(i) == '+' || s.charAt(i) == '-') {
					Monom m = new Monom(s.substring(checkPoint, i));
					this.add(m);
					checkPoint = i;
				}else continue;
			}
			Monom n = new Monom(s.substring(checkPoint));
			this.add(n);
		}this.add(new Monom(0,0));
	}
	
	@Override
	public function initFromString(String s) {
		// TODO Auto-generated method stub
		Polynom p = new Polynom(s);
		return p;
	}
	
	@Override
	public double f(double x) {
		// TODO Auto-generated method stub
		double ans = 0;
		Iterator<Monom> iter = this.iteretor();
		while (iter.hasNext()) {
			ans += iter.next().f(x);
		}
		return ans;
	}
	
	@Override
	public void add(Polynom_able p1) {
		// TODO Auto-generated method stub
		Iterator<Monom> iter= p1.iteretor();
		while (iter.hasNext()==true) {
			this.add(iter.next());
		}
		this.clearZero();
	}

	@Override
	public void add(Monom m1) {
		// TODO Auto-generated method stub
		Iterator<Monom> iter = this.iteretor();
		while (iter.hasNext()==true) {
			Monom m = iter.next();
			if (m.get_power()==m1.get_power()) {
				m.add(m1);
				return;
			}
		}
		Poly.add(m1);
		this.clearZero();
	}

	@Override
	public void substract(Polynom_able p1) {
		// TODO Auto-generated method stub
		Iterator<Monom> iter = p1.iteretor();
		while (iter.hasNext()) {
			this.subtract(iter.next());
		}
		this.clearZero();
	}
	/**
	 * helper function for subtract(Polynom_able p1).
	 * do subtraction with a monom.
	 * @param m is a monom.
	 */
	private void subtract(Monom m) {
		Monom minus = new Monom(-1*m.get_coefficient(), m.get_power());
		this.add(minus);
	}
	
	@Override
	public void multiply(Polynom_able p1) {
		// TODO Auto-generated method stub
		Polynom results = new Polynom();
		Polynom temp = (Polynom) this.copy();
		Iterator<Monom> iter = p1.iteretor();
		while(iter.hasNext()==true) {
			temp.multiply(iter.next());
			results.add(temp);
			temp = (Polynom) this.copy();
		}
		this.Poly.clear();
		this.Poly.addAll(results.Poly);
		this.clearZero();
	}

	public boolean equalsPoly(Polynom_able p1) {
		// TODO Auto-generated method stub
		boolean flag = false;
		Iterator<Monom> iter1 = p1.iteretor();
		while (iter1.hasNext()) {
			Iterator<Monom> iter2 = this.iteretor();
			Monom m = iter1.next();
			flag = false;
			while (iter2.hasNext()) {
				Monom n = iter2.next();
				if (m.equals(n)) flag=true;
			}
			if (flag == false) return false;
		}
		return true;
	}
	
	@Override
	public boolean equals(Object obj) {
		Polynom p = new Polynom(obj.toString());
		if (this.equalsPoly(p)) return true;
		return false;
	}

	@Override
	public boolean isZero() {
		// TODO Auto-generated method stub
		if (this.Poly.size() == 0) return true;
		Iterator<Monom> iter = this.iteretor();
		while (iter.hasNext()) {
			if (iter.next().get_coefficient() != 0) return false;
		}
		return true;
	}

	@Override
	public double root(double x0, double x1, double eps) {
		// TODO Auto-generated method stub
		if (this.isZero()) return 0;
		if (Math.abs(this.f(x0)) < eps) return x0;
		if (Math.abs(this.f(x1)) < eps) return x1; 
		if (this.f(x0)*this.f(x1)>0) throw new RuntimeException("inputs must satisfy the assumptions.");
		double x = (x0+x1)/2;
		while (Math.abs(this.f(x))>=eps) {
			if (this.f(x0)*this.f(x)>0) {
				x0 = x;
			}else x1 = x;
			x = (x0+x1)/2;
		}
		return x;
	}

	@Override
	public Polynom_able copy() {
		// TODO Auto-generated method stub
		String s = this.toString();
		Polynom p = new Polynom(s);
		return p;
	}

	@Override
	public Polynom_able derivative() {
		// TODO Auto-generated method stub
		Polynom p = new Polynom();
		Iterator<Monom> iter = this.iteretor();
		while (iter.hasNext()) {
			p.add(iter.next().derivative());
		}
		p.clearZero();
		return p;
	}

	@Override
	public double area(double x0, double x1, double eps) {
		// TODO Auto-generated method stub
		double result=0;
		double left = x0;
		double right = x1;
		while (left < x1) {
			double rectangle = this.f(left)*eps;
			result += rectangle;
			left += eps;
		}
		return result;
	}

	@Override
	public Iterator<Monom> iteretor() {
		// TODO Auto-generated method stub
		Iterator<Monom> iter = Poly.iterator();
		return  iter;
	}
	
	@Override
	public void multiply(Monom m1) {
		// TODO Auto-generated method stub
		ArrayList<Monom> temp = new ArrayList<Monom>(Poly.size());
		Iterator<Monom> iter = this.iteretor();
		while (iter.hasNext()==true) {
			int index = Poly.indexOf(iter.next());
			Monom m = Poly.get(index);
			m.multipy(m1);
			temp.add(index, m);
		}
		Poly = temp;
		this.clearZero();
	}
	/**
	 * @return a String representing the Polynom in format a(1)x^b(1)+...+a(n)x^b(n).
	 */
	public String toString() {
		Iterator<Monom> iter = this.iteretor();
		String s = "";
		Monom m;
		if (this.Poly.size()==0) return "0";
		while (iter.hasNext()) {
			m = iter.next();
			if (s.length()!=0 && m.get_coefficient()>=0) {
				s=s.concat("+"+m.toString());
			}else s=s.concat(m.toString());
		}
		return s;
	}
	/**
	 * delete unnecessary 0 from the Polynom.
	 */
	private void clearZero() {
		Iterator<Monom> iter = this.iteretor();
		boolean delZero = false;
		boolean notZero = false;
		while (iter.hasNext()) {
			Monom m = iter.next();
			if (m.get_coefficient()==0 && delZero) iter.remove();
			if (m.get_coefficient()==0) delZero =true;
			if (m.get_coefficient()!=0) {
				notZero = true;
				break;
			}
		}
		if (notZero) {
			iter = this.iteretor();
			while (iter.hasNext()) {
				if (iter.next().get_coefficient()==0) iter.remove();
			}
		}
	}
	
}
