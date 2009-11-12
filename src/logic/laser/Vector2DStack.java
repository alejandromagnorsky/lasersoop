package logic.laser;

import java.util.Stack;

import logic.Vector2D;

public class Vector2DStack {

	Stack<Vector2D> vectors;

	public Vector2DStack(){
		vectors = new Stack<Vector2D>();
	}
	
	public Vector2DStack(Vector2D v) {
		vectors = new Stack<Vector2D>();
		vectors.add(v);
	}
	
	public void add(Vector2DStack v){
		while(!v.isEmpty()){
			add(v.pop());
		}
	}
	
	public String toString(){
		return vectors.toString();
	}
	
	public Vector2D first(){
		return vectors.firstElement();
	}

	public Vector2D pop() {
		return vectors.pop();
	}

	public void add(Vector2D v) {
		vectors.add(v);
	}

	public boolean isEmpty() {
		return vectors.isEmpty();
	}

	public Vector2D peek() {
		return vectors.peek();
	}
}
