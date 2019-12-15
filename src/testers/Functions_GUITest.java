package testers;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import myMath.ComplexFunction;
import myMath.Functions_GUI;
import myMath.Monom;
import myMath.Operation;
import myMath.Polynom;
import myMath.Range;
import myMath.function;
import myMath.functions;
/**
 * Note: minor changes (thanks to Amichai!!)
 * The use of "get" was replaced by iterator!
 * 
 * Partial JUnit + main test for the GUI_Functions class, expected output from the main:
 * 0) java.awt.Color[r=0,g=0,b=255]  f(x)= plus(-1.0x^4 +2.4x^2 +3.1,+0.1x^5 -1.2999999999999998x +5.0)
1) java.awt.Color[r=0,g=255,b=255]  f(x)= plus(div(+1.0x +1.0,mul(mul(+1.0x +3.0,+1.0x -2.0),+1.0x -4.0)),2.0)
2) java.awt.Color[r=255,g=0,b=255]  f(x)= div(plus(-1.0x^4 +2.4x^2 +3.1,+0.1x^5 -1.2999999999999998x +5.0),-1.0x^4 +2.4x^2 +3.1)
3) java.awt.Color[r=255,g=200,b=0]  f(x)= -1.0x^4 +2.4x^2 +3.1
4) java.awt.Color[r=255,g=0,b=0]  f(x)= +0.1x^5 -1.2999999999999998x +5.0
5) java.awt.Color[r=0,g=255,b=0]  f(x)= max(max(max(max(plus(-1.0x^4 +2.4x^2 +3.1,+0.1x^5 -1.2999999999999998x +5.0),plus(div(+1.0x +1.0,mul(mul(+1.0x +3.0,+1.0x -2.0),+1.0x -4.0)),2.0)),div(plus(-1.0x^4 +2.4x^2 +3.1,+0.1x^5 -1.2999999999999998x +5.0),-1.0x^4 +2.4x^2 +3.1)),-1.0x^4 +2.4x^2 +3.1),+0.1x^5 -1.2999999999999998x +5.0)
6) java.awt.Color[r=255,g=175,b=175]  f(x)= min(min(min(min(plus(-1.0x^4 +2.4x^2 +3.1,+0.1x^5 -1.2999999999999998x +5.0),plus(div(+1.0x +1.0,mul(mul(+1.0x +3.0,+1.0x -2.0),+1.0x -4.0)),2.0)),div(plus(-1.0x^4 +2.4x^2 +3.1,+0.1x^5 -1.2999999999999998x +5.0),-1.0x^4 +2.4x^2 +3.1)),-1.0x^4 +2.4x^2 +3.1),+0.1x^5 -1.2999999999999998x +5.0)

 * @author boaz_benmoshe
 *
 */
class Functions_GUITest {
	public static void main(String[] a) {
		functions data = FunctionsFactory();
	//	int w=1000, h=600, res=200;
	//	Range rx = new Range(-10,10);
	//	Range ry = new Range(-5,15);
//		data.drawFunctions(w,h,rx,ry,res);
		String file = "function_file.txt";
		String file2 = "function_file2.txt";
		try {
			data.saveToFile(file);
			Functions_GUI data2 = new Functions_GUI();
			data2.initFromFile(file);
			data.saveToFile(file2);
		}
		catch(Exception e) {e.printStackTrace();}
		
		String JSON_param_file = "GUI_params.txt";
		data.drawFunctions(JSON_param_file);
	}
	private functions _data=null;
//	@BeforeAll
//	static void setUpBeforeClass() throws Exception {
//	}

	@BeforeEach
	void setUp() throws Exception {
		_data = FunctionsFactory();
	}

	@Test
	void testFunctions_GUI() {
		ArrayList<function> arr = new ArrayList<function>();
		ComplexFunction c = new ComplexFunction("plus(-1.0x^4 +2.4x^2 +3.1,+0.1x^5 -1.2999999999999998x +5.0)");
		arr.add(c);
		c = new ComplexFunction("plus(div(+1.0x +1.0,mul(mul(+1.0x +3.0,+1.0x -2.0),+1.0x -4.0)),2.0)");
		arr.add(c);
		c = new ComplexFunction("div(plus(-1.0x^4 +2.4x^2 +3.1,+0.1x^5 -1.2999999999999998x +5.0),-1.0x^4 +2.4x^2 +3.1)");
		arr.add(c);
		c= new ComplexFunction("-1.0x^4 +2.4x^2 +3.1");
		arr.add(c);
		Functions_GUI f1 = new Functions_GUI();
		Functions_GUI f2 = new Functions_GUI(arr);
		
	}

	@Test
	void testInitFromFile() {
		ArrayList<function> arr = new ArrayList<function>();
		ComplexFunction c= new ComplexFunction("plus(3.1+2.4x^2-x^4,5-1.2999999999999998x+0.1x^5)");
		arr.add(c);
		c= new ComplexFunction("plus(div(x+1,mul(mul(x+3,x-2),x-4)),2)");
		arr.add(c);
		c= new ComplexFunction("div(plus(3.1+2.4x^2-x^4,5-1.2999999999999998x+0.1x^5),3.1+2.4x^2-x^4)");
		arr.add(c);
		c= new ComplexFunction("3.1+2.4x^2-x^4");
		arr.add(c);
		c= new ComplexFunction("5-1.2999999999999998x+0.1x^5");
		arr.add(c);
		c= new ComplexFunction("max(max(max(max(plus"
				+ "(3.1+2.4x^2-x^4,5-1.2999999999999998x+0.1x^5),plus(div(x+1,mul(mul(x+3,x-2),x-4)),2)),"
				+ "div(plus(3.1+2.4x^2-x^4,5-1.2999999999999998x+0.1x^5)"
				+ ",3.1+2.4x^2-x^4)),3.1+2.4x^2-x^4),5-1.2999999999999998x+0.1x^5)");
		arr.add(c);
		c= new ComplexFunction("min(min(min(min(plus(3.1+2.4x^2-x^4,5-1.2999999999999998x+0.1x^5),"
				+ "plus(div(x+1,mul(mul(x+3,x-2),x-4)),2)),div(plus(3.1+2.4x^2-x^4,5-1.2999999999999998x+0.1x^5),"
				+ "3.1+2.4x^2-x^4)),3.1+2.4x^2-x^4),5-1.2999999999999998x+0.1x^5)");
		arr.add(c);
		Functions_GUI f = new Functions_GUI(arr);
		Functions_GUI fg = new Functions_GUI();
		try {
			f.saveToFile("function_file.txt");
			fg.initFromFile("function_file.txt");
		}catch (IOException e) {
			fail("Error: failed to init from the file.");
		}
		if (!f.equals(fg)) {
			fail("Error: init didn't worked properly.");
		}
		String s = System.getProperty("user.dir");
		Path path = FileSystems.getDefault().getPath(s, "function_file.txt");
		File t = path.toFile();
		t.delete();
	}

	@Test
	void testSaveToFile() {
		ArrayList<function> arr = new ArrayList<function>();
		ComplexFunction c= new ComplexFunction("plus(3.1+2.4x^2-x^4,5-1.2999999999999998x+0.1x^5)");
		arr.add(c);
		c= new ComplexFunction("plus(div(x+1,mul(mul(x+3,x-2),x-4)),2)");
		arr.add(c);
		c= new ComplexFunction("div(plus(3.1+2.4x^2-x^4,5-1.2999999999999998x+0.1x^5),3.1+2.4x^2-x^4)");
		arr.add(c);
		c= new ComplexFunction("3.1+2.4x^2-x^4");
		arr.add(c);
		c= new ComplexFunction("5-1.2999999999999998x+0.1x^5");
		arr.add(c);
		c= new ComplexFunction("max(max(max(max(plus"
				+ "(3.1+2.4x^2-x^4,5-1.2999999999999998x+0.1x^5),plus(div(x+1,mul(mul(x+3,x-2),x-4)),2)),"
				+ "div(plus(3.1+2.4x^2-x^4,5-1.2999999999999998x+0.1x^5)"
				+ ",3.1+2.4x^2-x^4)),3.1+2.4x^2-x^4),5-1.2999999999999998x+0.1x^5)");
		arr.add(c);
		c= new ComplexFunction("min(min(min(min(plus(3.1+2.4x^2-x^4,5-1.2999999999999998x+0.1x^5),"
				+ "plus(div(x+1,mul(mul(x+3,x-2),x-4)),2)),div(plus(3.1+2.4x^2-x^4,5-1.2999999999999998x+0.1x^5),"
				+ "3.1+2.4x^2-x^4)),3.1+2.4x^2-x^4),5-1.2999999999999998x+0.1x^5)");
		arr.add(c);
		Functions_GUI fg = new Functions_GUI(arr);
		try {
			fg.saveToFile("test.txt");
		}catch (IOException e) {
			fail("Error: save to file didn't work.");
		}
		Functions_GUI f = new Functions_GUI();
		try {
			f.initFromFile("test.txt");
		}catch (IOException e) {
			e.printStackTrace();
		}
		if (!fg.equals(f)) {
			fail("Error: Function_GUIs objects shouldbe the same.");
		}
		String s = System.getProperty("user.dir");
		Path path = FileSystems.getDefault().getPath(s, "test.txt");
		File t = path.toFile();
	}

	@Test
	void testDrawFunctions() {
		try {
			_data.drawFunctions("GUI_params.txt");
		}catch (Exception e) {
			fail("Error: didn't draw.");
		}
		try {
			_data.drawFunctions("");
		}catch (Exception e) {
			fail("Error: didn't draw.");
		}
	}

	@Test
	void testDrawFunctionsIntIntRangeRangeInt() {
		try {
			_data.drawFunctions(1000, 600, new Range(-5, 5), new Range(-5,10), 200);
		}catch (Exception e) {
			fail("Error: didn't draw.");
		}
	}
	public static functions FunctionsFactory() {
		functions ans = new Functions_GUI();
		String s1 = "3.1 +2.4x^2 -x^4";
		String s2 = "5 +2x -3.3x +0.1x^5";
		String[] s3 = {"x +3","x -2", "x -4"};
		Polynom p1 = new Polynom(s1);
		Polynom p2 = new Polynom(s2);
		Polynom p3 = new Polynom(s3[0]);
		ComplexFunction cf3 = new ComplexFunction(p3);
		for(int i=1;i<s3.length;i++) {
			cf3.mul(new Polynom(s3[i]));
		}
		
		ComplexFunction cf = new ComplexFunction(Operation.Plus, p1,p2);
		ComplexFunction cf4 = new ComplexFunction("div", new Polynom("x +1"),cf3);
		cf4.plus(new Monom("2"));
		ans.add(cf.copy());
		ans.add(cf4.copy());
		cf.div(p1);
		ans.add(cf.copy());
		String s = cf.toString();
		function cf5 = cf4.initFromString(s1);
		function cf6 = cf4.initFromString(s2);
		ans.add(cf5.copy());
		ans.add(cf6.copy());
		Iterator<function> iter = ans.iterator();
		function f = iter.next();
		ComplexFunction max = new ComplexFunction(f);
		ComplexFunction min = new ComplexFunction(f);
		while(iter.hasNext()) {
			f = iter.next();
			max.max(f);
			min.min(f);
		}
		ans.add(max);
		ans.add(min);		
		return ans;
	}
	
	@Test
	void testSize() {
		Functions_GUI f = new Functions_GUI();
		if (f.size()!=0) {
			fail("Error: Function_GUI should be empty.");
		}
		function f1 = new Polynom("5x");
		f.add(f1);
		if (f.size()!=1) {
			fail("Error: should have 1 function");
		}
	}
	
	@Test
	void testIsEmpty() {
		Functions_GUI f = new Functions_GUI();
		if (!f.isEmpty()) {
			fail("Error: should be empty");
		}
	}
	
	@Test
	void testContainsObjects() {
		Functions_GUI f = new Functions_GUI();
		function f1 = new Polynom("5x");
		f.add(f1);
		if (!f.contains(f1)) {
			fail("Error: Functions_GUI should contain the Polynom.");
		}
	}
	
	@Test
	void testToArray() {
		Functions_GUI f = new Functions_GUI();
		function f1 = new Polynom("5x");
		f.add(f1);
		Object[] a = new Object[1];
		a = f.toArray();
		if (!f1.toString().equalsIgnoreCase((a[0].toString()))) {
			fail("Error: function sohuld be the same");
		}
	}
	
	@Test
	void testToArrayT(){
		function[] f = new Polynom[1];
		function f1 = new Polynom("5x");
		Functions_GUI fg = new Functions_GUI();
		fg.add(f1);
		fg.toArray(f);
		if (!f1.toString().equalsIgnoreCase((f[0].toString()))) {
			fail("Error: functions should be the same");
		}
	}
	
	@Test
	void testAddFunction() {
		Functions_GUI f = new Functions_GUI();
		function f1 = new Polynom("5x");
		if (!f.add(f1)) {
			fail("Error: didn't add.");
		}
		if (f.isEmpty()) {
			fail("Error:didn't add.");
		}
	}
	
	
	@Test
	void testRemoveFunction() {
		Functions_GUI f = new Functions_GUI();
		function f1 = new Polynom("5x");
		f.add(f1);
		if (!f.remove(f1)) {
			fail("Error: didn't remove.");
		}
		if (!f.isEmpty()) {
			fail("Error:didn't remove.");
		}
	}
	
	@Test
	void testContainsAll() {
		Collection<function> f = new ArrayList<function>();
		ArrayList<function> arr = new ArrayList<function>();
		ComplexFunction c= new ComplexFunction("plus(3.1+2.4x^2-x^4,5-1.2999999999999998x+0.1x^5)");
		arr.add(c);
		c= new ComplexFunction("plus(div(x+1,mul(mul(x+3,x-2),x-4)),2)");
		arr.add(c);
		c= new ComplexFunction("div(plus(3.1+2.4x^2-x^4,5-1.2999999999999998x+0.1x^5),3.1+2.4x^2-x^4)");
		arr.add(c);
		c= new ComplexFunction("3.1+2.4x^2-x^4");
		arr.add(c);
		c= new ComplexFunction("5-1.2999999999999998x+0.1x^5");
		arr.add(c);
		c= new ComplexFunction("max(max(max(max(plus"
				+ "(3.1+2.4x^2-x^4,5-1.2999999999999998x+0.1x^5),plus(div(x+1,mul(mul(x+3,x-2),x-4)),2)),"
				+ "div(plus(3.1+2.4x^2-x^4,5-1.2999999999999998x+0.1x^5)"
				+ ",3.1+2.4x^2-x^4)),3.1+2.4x^2-x^4),5-1.2999999999999998x+0.1x^5)");
		arr.add(c);
		c= new ComplexFunction("min(min(min(min(plus(3.1+2.4x^2-x^4,5-1.2999999999999998x+0.1x^5),"
				+ "plus(div(x+1,mul(mul(x+3,x-2),x-4)),2)),div(plus(3.1+2.4x^2-x^4,5-1.2999999999999998x+0.1x^5),"
				+ "3.1+2.4x^2-x^4)),3.1+2.4x^2-x^4),5-1.2999999999999998x+0.1x^5)");
		arr.add(c);
		Functions_GUI fg = new Functions_GUI(arr);
		c= new ComplexFunction("plus(div(x+1,mul(mul(x+3,x-2),x-4)),2)");
		f.add(c);
		c= new ComplexFunction("div(plus(3.1+2.4x^2-x^4,5-1.2999999999999998x+0.1x^5),3.1+2.4x^2-x^4)");
		f.add(c);
		c= new ComplexFunction("3.1+2.4x^2-x^4");
		f.add(c);
		if (!fg.containsAll(f)) {
			fail("Error: Functions_GUI should contain all elements of the colletion.");
		}
	}
	
	@Test
	void testAddAll() {
		Collection<function> arr = new ArrayList<function>();
		ComplexFunction c= new ComplexFunction("plus(3.1+2.4x^2-x^4,5-1.2999999999999998x+0.1x^5)");
		arr.add(c);
		c= new ComplexFunction("plus(div(x+1,mul(mul(x+3,x-2),x-4)),2)");
		arr.add(c);
		c= new ComplexFunction("div(plus(3.1+2.4x^2-x^4,5-1.2999999999999998x+0.1x^5),3.1+2.4x^2-x^4)");
		arr.add(c);
		c= new ComplexFunction("3.1+2.4x^2-x^4");
		arr.add(c);
		c= new ComplexFunction("5-1.2999999999999998x+0.1x^5");
		arr.add(c);
		c= new ComplexFunction("max(max(max(max(plus"
				+ "(3.1+2.4x^2-x^4,5-1.2999999999999998x+0.1x^5),plus(div(x+1,mul(mul(x+3,x-2),x-4)),2)),"
				+ "div(plus(3.1+2.4x^2-x^4,5-1.2999999999999998x+0.1x^5)"
				+ ",3.1+2.4x^2-x^4)),3.1+2.4x^2-x^4),5-1.2999999999999998x+0.1x^5)");
		arr.add(c);
		c= new ComplexFunction("min(min(min(min(plus(3.1+2.4x^2-x^4,5-1.2999999999999998x+0.1x^5),"
				+ "plus(div(x+1,mul(mul(x+3,x-2),x-4)),2)),div(plus(3.1+2.4x^2-x^4,5-1.2999999999999998x+0.1x^5),"
				+ "3.1+2.4x^2-x^4)),3.1+2.4x^2-x^4),5-1.2999999999999998x+0.1x^5)");
		arr.add(c);
		Functions_GUI fg = new Functions_GUI();
		if (!fg.addAll(arr)) {
			fail("Error: Functions_GUI should add all elements of the colletion.");
		}
		if (!fg.containsAll(arr)) {
			fail("Error: Functions_GUI should contain all elements of the colletion.");
		}
	}
	
	@Test
	void testRemoveAll() {
		Functions_GUI f = new Functions_GUI();
		Collection<function> arr = new ArrayList<function>();
		ComplexFunction c= new ComplexFunction("plus(3.1+2.4x^2-x^4,5-1.2999999999999998x+0.1x^5)");
		arr.add(c);
		f.add(c);
		c= new ComplexFunction("plus(div(x+1,mul(mul(x+3,x-2),x-4)),2)");
		arr.add(c);
		f.add(c);
		c= new ComplexFunction("div(plus(3.1+2.4x^2-x^4,5-1.2999999999999998x+0.1x^5),3.1+2.4x^2-x^4)");
		arr.add(c);
		f.add(c);
		c= new ComplexFunction("3.1+2.4x^2-x^4");
		arr.add(c);
		f.add(c);
		c= new ComplexFunction("5-1.2999999999999998x+0.1x^5");
		arr.add(c);
		f.add(c);
		c= new ComplexFunction("max(max(max(max(plus"
				+ "(3.1+2.4x^2-x^4,5-1.2999999999999998x+0.1x^5),plus(div(x+1,mul(mul(x+3,x-2),x-4)),2)),"
				+ "div(plus(3.1+2.4x^2-x^4,5-1.2999999999999998x+0.1x^5)"
				+ ",3.1+2.4x^2-x^4)),3.1+2.4x^2-x^4),5-1.2999999999999998x+0.1x^5)");
		arr.add(c);
		f.add(c);
		c= new ComplexFunction("min(min(min(min(plus(3.1+2.4x^2-x^4,5-1.2999999999999998x+0.1x^5),"
				+ "plus(div(x+1,mul(mul(x+3,x-2),x-4)),2)),div(plus(3.1+2.4x^2-x^4,5-1.2999999999999998x+0.1x^5),"
				+ "3.1+2.4x^2-x^4)),3.1+2.4x^2-x^4),5-1.2999999999999998x+0.1x^5)");
		arr.add(c);
		f.add(c);
		if (!f.removeAll(arr)) {
			fail("Error: should remove all element existing in arr from Functions_GUI.");
		}
		if (!f.isEmpty()) {
			fail("Error: should be empty");
		}
	}
	
	@Test
	void testRetainAll() {
		Functions_GUI f = new Functions_GUI();
		Collection<function> arr = new ArrayList<function>();
		ComplexFunction c= new ComplexFunction("plus(3.1+2.4x^2-x^4,5-1.2999999999999998x+0.1x^5)");
		arr.add(c);
		f.add(c);
		c= new ComplexFunction("plus(div(x+1,mul(mul(x+3,x-2),x-4)),2)");
		arr.add(c);
		f.add(c);
		c= new ComplexFunction("div(plus(3.1+2.4x^2-x^4,5-1.2999999999999998x+0.1x^5),3.1+2.4x^2-x^4)");
		arr.add(c);
		f.add(c);
		c= new ComplexFunction("3.1+2.4x^2-x^4");
		arr.add(c);
		f.add(c);
		c= new ComplexFunction("5-1.2999999999999998x+0.1x^5");
		arr.add(c);
		f.add(c);
		c= new ComplexFunction("max(max(max(max(plus"
				+ "(3.1+2.4x^2-x^4,5-1.2999999999999998x+0.1x^5),plus(div(x+1,mul(mul(x+3,x-2),x-4)),2)),"
				+ "div(plus(3.1+2.4x^2-x^4,5-1.2999999999999998x+0.1x^5)"
				+ ",3.1+2.4x^2-x^4)),3.1+2.4x^2-x^4),5-1.2999999999999998x+0.1x^5)");
		arr.add(c);
		f.add(c);
		c= new ComplexFunction("min(min(min(min(plus(3.1+2.4x^2-x^4,5-1.2999999999999998x+0.1x^5),"
				+ "plus(div(x+1,mul(mul(x+3,x-2),x-4)),2)),div(plus(3.1+2.4x^2-x^4,5-1.2999999999999998x+0.1x^5),"
				+ "3.1+2.4x^2-x^4)),3.1+2.4x^2-x^4),5-1.2999999999999998x+0.1x^5)");
		arr.add(c);
		f.add(c);
		if (!f.retainAll(arr)) {
			fail("Error: should retain all element existing in arr from Functions_GUI.");
		}
		if (!f.containsAll(arr) || !arr.containsAll(f)) {
			fail("Error: should be equals");
		}
	}
	
	@Test
	void testClear() {
		Functions_GUI f = new Functions_GUI();
		function f1 = new Polynom("5x");
		f.add(f1);
		f.clear();
		if (!f.isEmpty()) {
			fail("Error: Functions_GUI should be empty");
		}
	}
	
	@Test
	void testEquals() {
		Functions_GUI f1 = new Functions_GUI();
		Functions_GUI f2 = new Functions_GUI();
		if (!f1.equals(f2)) {
			fail("Error: should be equals.");
		}
		function f = new Polynom("342x");
		f1.add(f);
		f2.add(f);
		if (!f1.equals(f2)) {
			fail("Error: should be equals.");
		}
	}
	
}
