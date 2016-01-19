package boot;

import java.io.IOException;

import model.MyModel;
import presenter.MyPresenter;
import view.MyView;

public class RunCLI {

	public static void main(String[] args) throws ClassNotFoundException, IOException {
		
		MyView v = new MyView();
		MyModel m = new MyModel();
		MyPresenter p = new MyPresenter(v, m);
		v.addObserver(p);
		m.addObserver(p);
		v.start();

	}

}
