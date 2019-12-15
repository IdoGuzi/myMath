package myMath;

public class ComplexFunction implements complex_function {
	private function f1,f2;
	private Operation op;

	
	public ComplexFunction() {
		this.f1=new Polynom("");
		this.f2=null;
		this.op=Operation.None;
	}
	
	public ComplexFunction(function f1) {
		this.op=Operation.None;
		this.f1=f1;
		this.f2=null;
	}
	
	public ComplexFunction(String s, function f1, function f2) {
		this.op=getOPFromString(s);
		this.f1=f1;
		this.f2=f2;
	}
	
	public ComplexFunction(Operation op, function f1, function f2) {
		this.f1=f1;
		this.f2=f2;
		this.op=op;
	}
	
	public ComplexFunction(String s) {
		s = s.replaceAll("\\s", "");
		ComplexFunction cf = new ComplexFunction(Operation.None,new Polynom(""),new Polynom(""));
		if (this.getOPFromString(s)==Operation.None) {
			if (s.startsWith("none")) s=s.substring(5, s.length()-3);
			this.op = Operation.None;
			this.f1 = new Polynom(s);
			this.f2 = null;
		}else {
			cf = (ComplexFunction)cf.initFromString(s);
			this.op = cf.getOp();
			this.f1 = cf.left();
			this.f2 = cf.right();
		}
	}
	
	/**
	 * calculate the function.
	 * @param x a real number.
	 * @return the value y=f(x).
	 */
	@Override
	public double f(double x) {
		double result=0;
		if (op.equals(Operation.Plus)) {
			result=f1.f(x)+f2.f(x);
		}else if (op.equals(Operation.Times)) {
			result = f1.f(x)*f2.f(x);
		}else if (op.equals(Operation.Divid)) {
			result = f1.f(x)/f2.f(x);
		}else if (op.equals(Operation.Max)) {
			result = Math.max(f1.f(x), f2.f(x));
		}else if (op.equals(Operation.Min)) {
			result = Math.min(f1.f(x), f2.f(x));
		}else if (op.equals(Operation.Comp)) {
			result = f1.f(f2.f(x));
		}else if (op.equals(Operation.None)) {
			result = f1.f(x);
		}else {
			fExit();
		}
		return result;
	}
	
	/**
	 * take a String and creating a 
	 * ComplexFunction object from it. 
	 * @param s: String representing ComplexFunction.
	 * @return the function that s represent.
	 */
	@Override
	public function initFromString(String s) {
		function f = new Polynom();
		char at = s.charAt(0);
		if (at!='p' && at!='m' && at!='d' && at!='c') {
			f = new Polynom(s);
			return f;
		}
		Operation op = Operation.None;
		int comma = findComma(s), opLength = 0;
		if (at=='p') {
			op = Operation.Plus;
			opLength = 4;
		}else if (at=='m') {
			opLength = 3;
			if (s.charAt(1)=='u') {
				op = Operation.Times;
			}else if (s.charAt(1)=='a') {
				op = Operation.Max;
			}else op = Operation.Min;
		}else if (at=='d') {
			opLength = 3;
			op = Operation.Divid;
		}else if (at=='c') {
			opLength = 4;
			op = Operation.Comp;
		}
		f = new ComplexFunction(op,initFromString(s.substring(opLength+1, comma)),initFromString(s.substring(comma+1, s.length()-1)));
		return f;
	}
	
	/**
	 * @return a deep copy of this ComplexFunction.
	 */
	@Override
	public function copy() {
		ComplexFunction cf = new ComplexFunction(this.getOp(), this.left(), this.right());
		return cf;
	}

	/**
	 * add this Object with f1
	 * @param f1 an Object that implement the interface function.
	 */
	@Override
	public void plus(function f1) {
		boolean isNone=isOpNone(), isNull=isF2Null();
		function f = this.copy();
		this.f2 = f1;
		this.op = Operation.Plus;
		if (!isNone && !isNull) {
			this.f1 = f;
		}else if (isNone ^ isNull) {
			throw new RuntimeException("ComplexFunction must have both operation and second funtion.");
		}else;
	}
	
	/**
	 * multiply this Object with f1
	 * @param f1 an Object that implement the interface function.
	 */
	@Override
	public void mul(function f1) {
		boolean isNone=isOpNone(), isNull=isF2Null();
		function f = this.copy();
		this.f2 = f1;
		this.op = Operation.Times;
		if (!isNone && !isNull) {
			this.f1 = f;
		}else if (isNone ^ isNull) {
			throw new RuntimeException("ComplexFunction must have both operation and second funtion.");
		}else;
	}

	/**
	 * divide this Object with f1
	 * @param f1 an Object that implement the interface function.
	 */
	@Override
	public void div(function f1) {
		boolean isNone=isOpNone(), isNull=isF2Null();
		function f = this.copy();
		this.f2 = f1;
		this.op = Operation.Divid;
		if (!isNone && !isNull) {
			this.f1 = f;
		}else if (isNone ^ isNull) {
			throw new RuntimeException("ComplexFunction must have both operation and second funtion.");
		}else;
	}

	/**
	 * when calculating return the bigger value between the two functions.
	 * @param f1 an Object that implement the interface function.
	 */
	@Override
	public void max(function f1) {
		boolean isNone=isOpNone(), isNull=isF2Null();
		function f = this.copy();
		this.f2 = f1;
		this.op = Operation.Max;
		if (!isNone && !isNull) {
			this.f1 = f;
		}else if (isNone ^ isNull) {
			throw new RuntimeException("ComplexFunction must have both operation and second funtion.");
		}else;
	}

	/**
	 * when calculating return the smaller value between the two functions.
	 * @param f1 an Object that implement the interface function.
	 */
	@Override
	public void min(function f1) {
		boolean isNone=isOpNone(), isNull=isF2Null();
		function f = this.copy();
		this.f2 = f1;
		this.op = Operation.Min;
		if (!isNone && !isNull) {
			this.f1 = f;
		}else if (isNone ^ isNull) {
			throw new RuntimeException("ComplexFunction must have both operation and second funtion.");
		}else;
	}

	/**
	 * when calculating f1 uses the value of f2: y=f1(f2(x)).
	 * @param f1 an Object that implement the interface function.
	 */
	@Override
	public void comp(function f1) {
		boolean isNone=isOpNone(), isNull=isF2Null();
		function f = this.copy();
		this.f2 = f1;
		this.op = Operation.Comp;
		if (!isNone && !isNull) {
			this.f1 = f;
		}else if (isNone ^ isNull) {
			throw new RuntimeException("ComplexFunction must have both operation and second funtion.");
		}else;
	}

	/**
	 * @return f1.
	 */
	@Override
	public function left() {
		return f1;
	}

	/**
	 * @return f2. 
	 */
	@Override
	public function right() {
		return f2;
	}

	/**
	 * @return op.
	 */
	@Override
	public Operation getOp() {
		return op;
	}

	/**
	 * @return the representing String of this Object.
	 */
	@Override
	public String toString() {
		String s="";
		if (op.equals(Operation.Plus)) {
			s = "plus("+f1.toString()+","+f2.toString()+")";
		}else if (op.equals(Operation.Times)) {
			s = "mul("+f1.toString()+","+f2.toString()+")";
		}else if (op.equals(Operation.Divid)) {
			s = "div("+f1.toString()+","+f2.toString()+")";
		}else if (op.equals(Operation.Max)) {
			s = "max("+f1.toString()+","+f2.toString()+")";
		}else if (op.equals(Operation.Min)) {
			s = "min("+f1.toString()+","+f2.toString()+")";
		}else if (op.equals(Operation.Comp)) {
			s = "comp("+f1.toString()+","+f2.toString()+")";
		}else {
			if (f2==null) {
				s = "none("+f1.toString()+","+"0)";
			}else s = "none("+f1.toString()+","+f2.toString()+")";
		}
		return s;
	}
	
	/**
	 * @param obj an Object that implement the interface function.
	 * @return true if the two Objects are equals.
	 */
	@Override
	public boolean equals(Object obj) {
		ComplexFunction cf = new ComplexFunction(obj.toString());
		if (this.toString().equals(cf.toString())) return true;
		return false;
	}
	
	private Operation getOPFromString(String s) {
		Operation op = Operation.None;
		if (s.startsWith("plus")) op = Operation.Plus;
		if (s.startsWith("mul")) op = Operation.Times;
		if (s.startsWith("div")) op = Operation.Divid;
		if (s.startsWith("max")) op = Operation.Max;
		if (s.startsWith("min")) op = Operation.Min;
		if (s.startsWith("comp")) op = Operation.Comp;
		return op;
	}
	
	private function getFuncFromString(String s) {
		Polynom p;
		if (s.charAt(0)!='*') {
			p = new Polynom(s);
			return p;
		}
		return null;
	}
	
	private void fExit() {
		System.out.println("Invalid Operation. closing...");
		System.exit(0);
	}
	
	private int findComma(String s) {
		int counter=0;
		for (int i=0;i<s.length();i++) {
			if (s.charAt(i)=='(') counter++;
			if (s.charAt(i)==')') counter--;
			if (counter==1 && s.charAt(i)==',') return i;
		}
		return -1;
	}
	
	private boolean isOpNone() {
		if (op==Operation.None) return true;
		return false;
	}

	private boolean isF2Null() {
		if (this.f2==null) return true;
		return false;
	}
	
}
