package fr.lipn.tensor;

import cern.colt.matrix.DoubleMatrix1D;
import cern.colt.matrix.impl.SparseDoubleMatrix2D;
import fr.lipn.tensor.exceptions.WrongSizeException;

public class TwoWayTensor extends SparseDoubleMatrix2D {
	private static final long serialVersionUID = -5213910461657008537L;
	
	public TwoWayTensor(int rows, int columns) {
		super(rows, columns);
	}
	
	/**
	 * Returns the 3W Tensor resulting from the outer product of this 2W Tensor by v
	 * @param v
	 * @return
	 */
	public ThreeWayTensor outerProduct(BaseVector v){
		
		ThreeWayTensor t = new ThreeWayTensor(v.size(), this.rows(), this.columns());
		
		for(int i=0; i<this.rows; i++){
			for(int j=0; j <this.columns; j++){
				for(int k=0; k < v.size(); k++){
					t.set(k, i, j, v.getQuick(k)*this.getQuick(i, j));
				}
			}
		}
		
		return t;
	}
	
	/**
	 * Calculates the Khatri-Rao product (column-wise Kronecker product)
	 * @param t
	 * @return
	 * @throws WrongSizeException 
	 */
	public TwoWayTensor krProduct(TwoWayTensor B) throws WrongSizeException{
		TwoWayTensor t = new TwoWayTensor((this.rows*B.rows), this.columns);
		
		if(!(this.columns == B.columns)) throw new WrongSizeException();
		
		for(int j=0; j< this.columns; j++) {
			DoubleMatrix1D a = this.viewColumn(j);
			DoubleMatrix1D b = B.viewColumn(j);
			
			for(int i=0; i< a.size(); i++) {
				for(int ib=0; ib < b.size(); ib++) {
					double val=a.getQuick(i)*b.getQuick(ib);
					t.set(ib+(i*b.size()), j, val);
				}
			}
			
		}
		
		return t;
	}

}
