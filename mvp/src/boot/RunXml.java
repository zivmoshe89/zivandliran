package boot;

import java.beans.XMLEncoder;
import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import presenter.Properties;

public class RunXml {

	public static void main(String[] args) {

		
		Properties properties = new Properties("Liran",10,10,10,8, "simple", "a star(manhattan)");
		
		FileOutputStream fos;
		XMLEncoder xmlEncoder = null;
		try {
			fos = new FileOutputStream("propertiess.xml");
			BufferedOutputStream bos = new BufferedOutputStream(fos);
   	 	    xmlEncoder = new XMLEncoder(bos);
			xmlEncoder.writeObject(properties);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}finally{
			xmlEncoder.close();
		}
		
		
	}

}
