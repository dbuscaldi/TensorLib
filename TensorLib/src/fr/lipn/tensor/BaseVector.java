package fr.lipn.tensor;

import cern.colt.matrix.impl.SparseDoubleMatrix1D;

public class BaseVector extends SparseDoubleMatrix1D {
	private static final long serialVersionUID = 12L;
	
	public BaseVector(int size) {
		super(size);
	}
	
	public BaseVector(double [] array) {
		super(array);
	}
	
	/**
	 * Returns the matrix (2W Tensor) resulting from the outer product of this vector by v
	 * @param v
	 * @return
	 */
 	public TwoWayTensor outerProduct(BaseVector v){
 		TwoWayTensor t = new TwoWayTensor(this.size, v.size); //the new tensor has this.size lines and v.size columns
 		
 		for(int i=0; i< this.size; i++) {
 			for(int j=0; j < v.size; j++) {
 				t.setQuick(i, j, this.getQuick(i)*v.getQuick(j));
 			}
 		}
 		
 		return t;
 	}
    

}
