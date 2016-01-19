package presenter;

import java.io.Serializable;

/**
 * The Class Properties.
 */
@SuppressWarnings("serial")
public class Properties implements Serializable {

	String name;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	/** The num of thred. */
	int numOfThred;
	int x,y,z;
	
	
	/**
	 * Gets the x.
	 *
	 * @return the x
	 */
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	/**
	 * Gets the y.
	 *
	 * @return the y
	 */
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	/**
	 * Gets the z.
	 *
	 * @return the z
	 */
	public int getZ() {
		return z;
	}
	public void setZ(int z) {
		this.z = z;
	}

	/** The algorithm solve. */
	String algorithmCreate = "", algorithmSolve = "";
	
	
	/**
	 * Instantiates a new properties.
	 */
	public Properties(){}
	/**
	 * Instantiates a new properties.
	 *
	 * @param numOfThred the num of thred
	 * @param algorithmCreate the algorithm create
	 * @param algorithmSolve the algorithm solve
	 */
	public Properties(String name,int x,int y,int z,int numOfThred,String algorithmCreate,String algorithmSolve) {
		this.name = name;
		this.x = x;
		this.y = y;
		this.z = z;
		this.numOfThred = numOfThred;
		this.algorithmCreate = algorithmCreate;
		this.algorithmSolve = algorithmSolve;
		
	}

	/**
	 * Gets the num of thred.
	 *
	 * @return the num of thred
	 */
	public int getNumOfThred() {
		return numOfThred;
	}

	/**
	 * Sets the num of thred.
	 *
	 * @param numOfThred the new num of thred
	 */
	public void setNumOfThred(int numOfThred) {
		this.numOfThred = numOfThred;
	}

	/**
	 * Gets the algorithm create.
	 *
	 * @return the algorithm create
	 */
	public String getAlgorithmCreate() {
		return algorithmCreate;
	}

	/**
	 * Sets the algorithm create.
	 *
	 * @param algorithmCreate the new algorithm create
	 */
	public void setAlgorithmCreate(String algorithmCreate) {
		this.algorithmCreate = algorithmCreate;
	}

	/**
	 * Gets the algorithm solve.
	 *
	 * @return the algorithm solve
	 */
	public String getAlgorithmSolve() {
		return algorithmSolve;
	}

	/**
	 * Sets the algorithm solve.
	 *
	 * @param algorithmSolve the new algorithm solve
	 */
	public void setAlgorithmSolve(String algorithmSolve) {
		this.algorithmSolve = algorithmSolve;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Properties other = (Properties) obj;
		if (algorithmCreate == null) {
			if (other.algorithmCreate != null)
				return false;
		} else if (!algorithmCreate.equals(other.algorithmCreate))
			return false;
		if (algorithmSolve == null) {
			if (other.algorithmSolve != null)
				return false;
		} else if (!algorithmSolve.equals(other.algorithmSolve))
			return false;
		if (numOfThred != other.numOfThred)
			return false;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		if (z != other.z)
			return false;
		return true;
	}
}
