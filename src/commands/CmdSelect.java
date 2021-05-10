package commands;


import modelViewController.ApplicationModel;
import shapes.Command;
import shapes.Shape;

public class CmdSelect implements Command{
	private ApplicationModel AppModel;
	private Shape shape;
	public CmdSelect(ApplicationModel appModel, Shape shape) {
		this.AppModel = appModel;
		this.shape = shape;
	}

	@Override 
	public void execute() {
		shape.setSelected(true);
		AppModel.addSelectedShape(shape);
	}

	@Override 
	public void unexecute() { 
		shape.setSelected(false);
		AppModel.getSelectedShapes().remove(shape);
	}
	
	@Override
	public String toString() {
		return "Selected->" + shape.toString();
	}

}
