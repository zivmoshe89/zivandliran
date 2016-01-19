package boot;

import java.io.IOException;

import model.MyModel;
import presenter.MyPresenter;
import view.MazeWindow;

public class RunGUI {

	public static void main(String[] args) throws ClassNotFoundException, IOException {
		
		MazeWindow v = new MazeWindow("maze", 800, 500);
		
		MyModel m = new MyModel();
		MyPresenter p = new MyPresenter(v, m);
		v.addObserver(p);
		m.addObserver(p);
		v.run();
		
	}

}
