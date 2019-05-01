import java.util.EventObject;

public class VanishEvent extends EventObject {
	protected boolean control;
	public VanishEvent(Object source, boolean control) {
		super(source);
		this.control = control;
	}
}