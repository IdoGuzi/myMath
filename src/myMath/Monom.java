
package myMath;

import java.util.Comparator;

/**
 * This class represents a simple "Monom" of shape a*x^b, where a is a real number and a is an integer (summed a none negative), 
 * see: https://en.wikipedia.org/wiki/Monomial 
 * The class implements function and support simple operations as: construction, value at x, derivative, add and multiply. 
 * @author Boaz
 *
 */
public class Monom implements function{
	public static final Monom ZERO = new Monom(0,0);
	public static final Monom MINUS1 = new Monom(-1,0);
	public static final double EPSILON = 0.0000001;
	public static final Comparator<Monom> _Comp = new Monom_Comperator();
	public static Comparator<Monom> getComp() {return _Comp;}
	public Monom(double a, int b){
		this.set_coefficient(a);
		this.set_power(b);
	}
	public Monom(Monom ot) {
		this(ot.get_coefficient(), ot.get_power());
	}
	
	public double get_coefficient() {
		return this._coefficient;
	}
	public int get_power() {
		return this._power;
	}
	/** 
	 * this method returns the derivative monom of this.
	 * @return
	 */
	public Monom derivative() {
		if(this.get_power()==0) {return getNewZeroMonom();}
		return new Monom(this.get_coefficient()*this.get_power(), this.get_power()-1);
	}
	/**
	 * calculate the monom.
	 * @param x is a real number.
	 * @return the evaluation of the monom with x.
	 */
	public double f(double x) {
		double ans=0;
		double p = this.get_power();
		ans = this.get_coefficient()*Math.pow(x, p);
		return ans;
	}
	/**
	 * checks if coefficient is 0
	 * @return whether of not the monom equals 0
	 */
	public boolean isZero() {return this.get_coefficient() == 0;}
	// ***************** add your code below **********************
	@Override
	public function initFromString(String s) {
		// TODO Auto-generated method stub
		Monom m = new Monom(s);
		return m;
	}
	/**
	 * this constructor gets String and cast it to valid math expression.
	 * It support only simple expressions like ax^b. 
	 * @param s is a String format ax^b.
	 */
	public Monom(String s) {
		if (s.length()!=0) {
			s = s.toLowerCase();
			int i = s.indexOf('x');
			this._coefficient = this.getCoefficeintOutOfString(s, i);
			this._power = this.getPowerOutOfString(s, i);
		}else {
			this._coefficient=0;
			this._power=0;
		}
	}
	/**
	 * add monom m if the powers of the
	 * monoms are equals else throws exception.
	 * @param m a monom to add.
	 * @throws IllegalArgumentException
	 */
	public void add(Monom m) {
		if (this.isZero() == true) {
			this._coefficient=m._coefficient;
			this._power = m._power;
			return;
		}else if (m.isZero() == true) {
			return;
		}
		if (this._power == m._power) {
			this._coefficient += m._coefficient;
		}else throw new IllegalArgumentException("can't sum two monoms"
				+ " to a one monom if powers of the monoms aren't equal");
	}
	/**
	 * multiply two monom.
	 * @param d a monom to multiply.
	 */
	public void multipy(Monom d) {
		this._coefficient = this._coefficient * d._coefficient;
		this._power = this._power + d._power;
	}
	/**
	 * return the String representing the monom in the format ax^b
	 */
	public String toString() {
		String ans;
		if (_coefficient == 0) return "0";
		if (_power == 0) {
			ans = Double.toString(_coefficient);
		}else if (_power == 1) {
			if (_coefficient == 1) {
				ans = "x";
				return ans;
			}else if (_coefficient == -1) {
				ans = "-x";
				return ans;
			}else ans = Double.toString(_coefficient)+"x";
		}else { 
			if (_coefficient == 1) {
				ans = "x^"+Integer.toString(_power);
				return ans;
			}else if (_coefficient == -1) {
				ans = "-x^"+Integer.toString(_power);
				return ans;
			}else ans = Double.toString(_coefficient)+"x^"+Integer.toString(_power);
		}
		int index = ans.indexOf('x');
		if (index != -1) {
			if (ans.charAt(index-1) == '0') ans = ans.substring(0, index-2) +ans.substring(index);
		}else {
			if (ans.charAt(ans.length()-1) == '0') ans = ans.substring(0, ans.length()-2);
		}
		return ans;
	}
	// you may (always) add other methods.
	/**
	 * true if the two monoms have the same
	 * power and coefficient, else false.
	 * @param m is a monom. 
	 * @return whether the two monoms are equals or not.
	 */
	public boolean equals(Monom m) {
		if (Math.abs(this.get_power()-m.get_power())<EPSILON && (Math.abs(this.get_coefficient()-m.get_coefficient())<EPSILON)) return true;
		return false;
	}
	
	@Override
	public function copy() {
		// TODO Auto-generated method stub
		double c = _coefficient;
		int p = _power;
		Monom m = new Monom(c, p);
		return m;
	}
	
	//****************** Private Methods and Data *****************
	/**
	 * s is a subString a taken from the constractor input String in format ax^b
	 * and turn it to a Double to represent the coefficient of the monom.
	 * this private method is a "helper" function for the constractor
	 * to get the coefficientof the monom.
	 * @param s subString of everything before x in the constractor input String.
	 * @param i is the index of x in the input String of the constractor.
	 * @return the coefficient of the monom from a String.
	 */
	private double getCoefficeintOutOfString(String s, int i) {
		switch(i) {
		case -1:
			return Double.parseDouble(s);
		case 0:
			return 1;
		case 1:
			if (s.charAt(0) == '-' || s.charAt(0) == '+') {
				return Double.parseDouble(s.charAt(0)+"1");
			}else return Double.parseDouble(s.substring(0, 1));
		default:
			return Double.parseDouble(s.substring(0, i));
		}
	}
	/**
	 * take the sub String b from constractor input in format ax^b
	 * and turn it to a Integer to represent the power of the monom.
	 * @param s subString of everything after x in the constractor input String.
	 * @param i is the index of x in the input String of the constractor.
	 * @return the power of the monom.
	 * @throws IllegalArgumentException
	 */
	private int getPowerOutOfString(String s, int i) throws IllegalArgumentException {
		if (i == -1) {
			return 0;
		}else {
			if (s.length() == i+1) {
				return 1;
			}else if (s.length() > i+2) {
				return Integer.parseInt(s.substring(i+2));
			}else throw new IllegalArgumentException("syntax error, please check your input");
		}
	}

	private void set_coefficient(double a){
		this._coefficient = a;
	}
	private void set_power(int p) {
		if(p<0) {throw new RuntimeException("ERR the power of Monom should not be negative, got: "+p);}
		this._power = p;
	}
	private static Monom getNewZeroMonom() {return new Monom(ZERO);}
	private double _coefficient; 
	private int _power;
	
	
}
