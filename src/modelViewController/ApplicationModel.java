package modelViewController;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import adapters.HexagonAdapter;
import shapes.Circle;
import shapes.Command;
import shapes.Line;
import shapes.Point;
import shapes.Rectangle;
import shapes.Shape;

public class ApplicationModel {
	private List<Shape> shapes = new ArrayList<Shape>();
	private List<Shape> selectedShapes = new ArrayList<Shape>();
	private Stack<Command> undoStack = new Stack<>();
	private Stack<Command> redoStack = new Stack<>();
	public int undoCounter = 0;
	private Point startPoint;
	
	private PropertyChangeSupport propertyChangeSupport;
	
	public ApplicationModel() {
		propertyChangeSupport = new PropertyChangeSupport(this);
	}
	
	public void addPropertyChangeListener(PropertyChangeListener pcl) {
		propertyChangeSupport.addPropertyChangeListener(pcl);
	}
	
	public void removePropertyChangeListener(PropertyChangeListener pcl) {
		propertyChangeSupport.removePropertyChangeListener(pcl);
	}
	
	public Stack<Command> getRedoStack() {
		return redoStack;
	}
 
	public void setRedoStack(Stack<Command> redoStack) {
		this.redoStack = redoStack;
	}

	public Stack<Command> getUndoStack() {
		return undoStack;
	}

	public void setUndoStack(Stack<Command> undoStack) {
		this.undoStack = undoStack;
	}


	public List<Shape> getSelectedShapes() {
		return selectedShapes;
	}

	public void setSelectedShapes(List<Shape> selectedShapes) {
		this.selectedShapes = selectedShapes;
	}

	public Shape getByIndex(int index) {
		return shapes.get(index);
	}
	
	public Point getStartPoint() {
		return startPoint;
	}

	public void setStartPoint(Point startPoint) {
		this.startPoint = startPoint;
	}

	public List<Shape> getShapes() {
		return shapes;
	}

	public void setShapes(List<Shape> shapes) {
		this.shapes = shapes;
	}
	
	public void addShape(Shape toBeAdded)
	{
		shapes.add(toBeAdded);
	}
	
	public ArrayList<Shape> getAllShapes() {
		return (ArrayList<Shape>) shapes;
	}
	
	public void addMultipleShapes(ArrayList<Shape> shapes) {
		this.shapes.addAll(shapes);
	}
	
	public void removeShape(Shape toBeRemoved)
	{
		int selectedShapesSizeBefore = selectedShapes.size();
		if(shapes.remove(toBeRemoved) == false) {
			System.out.println("Shape does not exist in list of shapes!");
		}
		
		selectedShapes.remove(toBeRemoved);
		propertyChangeSupport.firePropertyChange("Deleted Shapes", selectedShapesSizeBefore, selectedShapes.size());
	}
	
	public void addSelectedShape(Shape toBeAdded) {
			int selectedShapesSizeBefore = selectedShapes.size();
			selectedShapes.add(toBeAdded);
			propertyChangeSupport.firePropertyChange("Selected Shapes", selectedShapesSizeBefore, selectedShapes.size());
	}
	
	public void pushToUndoStack(Command toBePushed) {
		undoCounter++;
		int undoStackSizeBefore = undoStack.size();
		this.undoStack.push(toBePushed);
		propertyChangeSupport.firePropertyChange("Undo Stack", undoStackSizeBefore, undoStack.size());
	}
	
	public void removeFromUndoStack() {
		undoCounter--;
		int undoStackSizeBefore = undoStack.size();
		if(undoStack.peek()!=null) {
			this.undoStack.pop().unexecute();
		}
		propertyChangeSupport.firePropertyChange("Undo Stack Remove", undoStackSizeBefore, undoStack.size());
	}
	
	public void pushToRedoStack(Command toBePushed) {
		int redoStackSizeBefore = redoStack.size();
		this.redoStack.push(toBePushed);
		propertyChangeSupport.firePropertyChange("Redo Stack", redoStackSizeBefore, redoStack.size());
	}
	
	public void removeFromRedoStack() {
		int redoStackSizeBefore = redoStack.size();
		if(redoStack.peek()!=null) {
			this.redoStack.pop().execute();
		}
		propertyChangeSupport.firePropertyChange("Redo Stack Remove", redoStackSizeBefore, redoStack.size());
	}
	
	public int getIndexOfShape(Shape s) {
		int listSize = shapes.size() - 1;
		
		for (int i = 0; i <= listSize; i++) {
			if (shapes.get(i).equals(s)) {
				
				return i;
			}
		}
		return -1;
	}
	
}
