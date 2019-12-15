package myMath;

import java.awt.Font;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Random;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import javax.json.JsonWriter;

public class Functions_GUI implements functions{

	private ArrayList<function> func;
	
	public Functions_GUI() {
		// TODO Auto-generated constructor stub
		this.func=new ArrayList<function>();
	}
	
	public Functions_GUI(ArrayList<function> func) {
		this.func=func;
	}
	
	
	/**
	 *this function take an existing object of Function_GUI
	 *and setting the attribute func to a new ArrayList with
	 *the function from file.
	 *@param file - name of text file in JSON format such that  
	 *the file will have a json object with the array called "functions" 
	 *and hold strings representing an object that implements the 
	 *interface function.
	 */
	@Override
	public void initFromFile(String file) throws IOException {
		// TODO Auto-generated method stub
		this.clear();
		File f = this.getFileFromString(file);
		JsonObject jsonObject = this.createJsonObject(file);
		JsonArray jsonArray = jsonObject.getJsonArray("functions");
		ArrayList<function> arr = new ArrayList<function>();
		for (int i=0;i<jsonArray.size();i++) {
			String function = jsonArray.getString(i);
			arr.add(new ComplexFunction(function));
		}
		this.func = arr;
	}

	
	/**
	 * this method takes a file and delete it
	 * and creating a new text file and Json object 
	 * that have an Array called "functions" that hold
	 * Strings representing the functions that the ArrayList
	 * func holds.
	 * @param file - name of text file.
	 */
	@Override
	public void saveToFile(String file) throws IOException {
		// TODO Auto-generated method stub
		File f = this.getFileFromString(file);
		if (f.exists() && f.isFile()) {
			f.delete();
		}else if (f.exists() && !f.isFile()) throw new IOException("Error: wrong file format.");
		f.createNewFile();
		JsonObjectBuilder Funciton_GUIBuilder = Json.createObjectBuilder();
		JsonArrayBuilder functionArray = Json.createArrayBuilder();
		Iterator<function> iter = this.iterator();
		while (iter.hasNext()) {
			functionArray.add(iter.next().toString());
		}
		JsonArray functions = functionArray.build();
		Funciton_GUIBuilder.add("functions", functions);
		JsonObject jsonObject = Funciton_GUIBuilder.build();
		OutputStream os = new FileOutputStream(f);
		JsonWriter jsonWriter = Json.createWriter(os);
		jsonWriter.writeObject(jsonObject);
		jsonWriter.close();
	}

	
	/**
	 * this method opens a window with a drawing of
	 * the functions stored in this object with the params:
	 * @param width: the width of the window.
	 * @param height: the height of the window.
	 * @param rx: the range of the x axle in the drawing.
	 * @param ry: the range of the y axle in the drawing.
	 * @param resolution: will define how accurate the
	 * drawing of the function will be, draw lines 
	 * "resolution" times from the min of rx to 
	 * max of rx when the steps are:
	 * length of rx over resolution.
	 */
	@Override
	public void drawFunctions(int width, int height, Range rx, Range ry, int resolution) {
		// TODO Auto-generated method stub
		createTemplate(width, height, rx, ry, resolution);
		Iterator<function> iter = this.iterator();
		while (iter.hasNext()) {
			function f = iter.next();
			int[] color = randomColor();
			System.out.println("color:["+color[0]+","+color[1]+","+color[2]+"] f(x) = "+f.toString());
			StdDraw.setPenColor(color[0], color[1], color[2]);
			double step = rx.length()/resolution;
			for (double i=rx.get_min();i<rx.get_max();i=i+step) {
				double fx = f.f(i);
				double fh = f.f(i+step);
				if (ry.isIn(fx) || ry.isIn(fh)) StdDraw.line(i, fx, i+step, fh);
			}
		}
	}

	/**
	 * this method draw the functions stored
	 * in this object and define the params
	 * from Json_file .
	 * @param json_file: Json format text file
	 * that hold json object with the next fields:
	 * Width, Height, RangeX_min, RangeX_max,
	 * RangeY_min, RangeY_max, Resolution.
	 */
	@Override
	public void drawFunctions(String json_file) {
		// TODO Auto-generated method stub
		JsonObject jsonObject =null;;
		int width,height,resolution;
		Range rx,ry;
		File f;
		try {
			f = getFileFromString(json_file);
			if (f.exists() && f.isFile()) {
				jsonObject = this.createJsonObject(json_file);
			}else jsonObject = this.createJsonObject("default_params.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}
		width = jsonObject.getInt("Width");
		height = jsonObject.getInt("Height");
		resolution = jsonObject.getInt("Resolution");
		rx = new Range(jsonObject.getInt("RangeX_min"),jsonObject.getInt("RangeX_max"));
		ry = new Range(jsonObject.getInt("RangeY_min"),jsonObject.getInt("RangeY_max"));
		drawFunctions(width, height, rx, ry, resolution);
	}

	/**
	 * @return the amount of functions stored in this object.
	 */
	@Override
	public int size() {
		// TODO Auto-generated method stub
		return func.size();
	}

	/**
	 * @return true if the amount of function stored in this object is zero.
	 */
	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		if (this.size()==0) return true;
		return false;
	}

	/**
	 * check if o is in this object.
	 * @param o: should be an Object that implement the interface function to work properly.
	 * @return true if o is a function and already existing in this object.
	 */
	@Override
	public boolean contains(Object o) {
		// TODO Auto-generated method stub
		Iterator<function> iter = this.iterator();
		while (iter.hasNext()) {
			function f = iter.next();
			try {
				if (f.equals(o)) return true;
			}catch (Exception e) {
				return false;
			}
		}
		return false;
	}

	/**
	 * @return an Iterator of the object.
	 */
	@Override
	public Iterator<function> iterator() {
		// TODO Auto-generated method stub
		Iterator<function> iter = func.iterator();
		return iter;
	}

	
	/**
	 * convert the attribute func from 
	 * ArrayList of function to Array of objects.
	 * @return Array of functions casted to Objects.
	 */
	@Override
	public Object[] toArray() {
		// TODO Auto-generated method stub
		Object[] arr = new Object[this.size()];
		for (int i=0;i<arr.length;i++) {
			arr[i] = this.func.get(i);
		}
		return arr;
	}

	
	/**
	 * change the attribute func to 
	 * a generic Array.
	 * @param a: Array of type T.
	 * @param T: type of Object.
	 * @return Array a of type T, will be empty if cannot be casted.
	 */
	@Override
	public <T> T[] toArray(T[] a) {
		// TODO Auto-generated method stub
		if (a.length < this.size()) a = (T[]) new Object[this.size()];
		for (int i=0;i<a.length;i++) {
			try {
				a[i] = (T) this.func.get(i);
			}catch (Exception e) {
				return a;
			}
		}
		return a;
	}

	/**
	 * add e to the attribute func.
	 * @param e: object that implement the interface function.
	 * @return true if added e successfully.
	 */
	@Override
	public boolean add(function e) {
		// TODO Auto-generated method stub
		try {
			this.func.add(e);
			return true;
		}catch (Exception ex) {
			return false;
		}
	}

	/**
	 * remove o from the attribute func.
	 * @param o: this object must be implemention of function
	 * @return true if removed o successfully.
	 */
	@Override
	public boolean remove(Object o) {
		// TODO Auto-generated method stub
		try {
			this.func.remove(o);
			return true;
		}catch (Exception ex) {
			return false;
		}
	}

	/**
	 * @param c: collection of unknown type.
	 * @return true if every object in c also can be find in the attribute func.
	 */
	@Override
	public boolean containsAll(Collection<?> c) {
		// TODO Auto-generated method stub
		Iterator<?> iter = c.iterator();
		while (iter.hasNext()) {
			if (!this.func.contains(iter.next())) return false;
		}
		return true;
	}

	
	/**
	 * @param c: collection of object that implements an interface extending the interface function.
	 * @return true if all function added successfully.
	 */
	@Override
	public boolean addAll(Collection<? extends function> c) {
		// TODO Auto-generated method stub
		Iterator<?> iter = c.iterator();
		while (iter.hasNext()) {
			if (!this.add((function) iter.next())) return false;
		}
		return true;
	}

	/**
	 * remove all the objects in c from this Object
	 * @param c collection of Objects.
	 * @return true if all object in c was removed from func.
	 */
	@Override
	public boolean removeAll(Collection<?> c) {
		// TODO Auto-generated method stub
		Iterator<?> iter = c.iterator();
		while (iter.hasNext()) {
			Object o = iter.next();
			if (this.func.contains(o)) {
				if (!this.remove(o)) return false;
			}
		}
		return true;
	}

	/**
	 * remove all functions from func if not existing in c.
	 * @param c: collection of functions.
	 * @return true if every function not existing in c was deleted.
	 */
	@Override
	public boolean retainAll(Collection<?> c) {
		// TODO Auto-generated method stub
		Iterator<function> iter = this.iterator();
		while (iter.hasNext()) {
			function f = iter.next();
			if (!c.contains(f)) {
				if (!this.remove(f)) return false;
			}
		}
		return true;
	}

	/**
	 * clear this Object from functions.
	 */
	@Override
	public void clear() {
		// TODO Auto-generated method stub
		func = new ArrayList<function>();
	}
	
	/**
	 * @param f Function_GUI object
	 * @return true if function are equals.
	 */
	public boolean equals(Functions_GUI f) {
		if (this.func.equals(f.func)) return true;
		return false;
	}
	
	// ***** private methods *****
	private int[] randomColor() {
		int[] arr = new int[3];
		Random ran = new Random();
		arr[0]=ran.nextInt(255)+1;
		arr[1]=ran.nextInt(255)+1;
		arr[2]=ran.nextInt(255)+1;
		if ((arr[0]>245 && arr[1]>245 && arr[0]>245) || (arr[0]<10 && arr[1]<10 && arr[2]<10)) {
			arr=randomColor();
		}
		return arr;
	}
	
	private void createTemplate(int width, int height, Range rx, Range ry, int resolution) {
		StdDraw.setCanvasSize(width, height);
		StdDraw.setXscale(rx.get_min(), rx.get_max());
		StdDraw.setYscale(ry.get_min(), ry.get_max());
		double tempx=Math.round(rx.get_min()),tempy=Math.round(ry.get_min());
		while (tempx<rx.get_max()) {
			tempx++;
			if (tempx==0) {
				StdDraw.setPenRadius(0.005);
				StdDraw.line(tempx, ry.get_min(), tempx, ry.get_max());
				StdDraw.setPenRadius();
			}else StdDraw.line(tempx, ry.get_min(), tempx, ry.get_max());
		}
		while (tempy<ry.get_max()) {
			tempy++;
			if (tempy==0) {
				StdDraw.setPenRadius(0.005);
				StdDraw.line(rx.get_min(), tempy, rx.get_max(), tempy);
				StdDraw.setPenRadius();
			}else StdDraw.line(rx.get_min(), tempy, rx.get_max(), tempy);
		}
		StdDraw.setPenRadius(0.01);
		StdDraw.setFont(new Font("Sans_Serif", Font.BOLD,16));
		for (int i=(int)rx.get_min();i<rx.get_max();i++) {
			for (int j=(int)ry.get_min();j<ry.get_max();j++) {
				if (i==0 && j==0) {
					StdDraw.text(i-0.15, j+0.05, "0");
				}else if (i==0 && j!=0) {
					StdDraw.text(i-0.15, j, j+"");
				}else if (i!=0 && j==0) {
					StdDraw.text(i, j-0.15, i+"");
				}else;
			}
		}
	}
	
	private File getFileFromString(String fileName) throws IOException {
		String s = System.getProperty("user.dir");
		Path path = FileSystems.getDefault().getPath(s, fileName);
		File f = path.toFile();
		return f;
	}
	
	private JsonObject createJsonObject(String s) throws IOException {
		File f = getFileFromString(s);
		InputStream fis = new FileInputStream(s);
		JsonReader jsonReader = Json.createReader(fis);
		JsonObject jsonObject = jsonReader.readObject();
		jsonReader.close();
		fis.close();
		return jsonObject;
	}
	
}
