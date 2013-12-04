package fr.lipn.tensor;

import cern.colt.matrix.DoubleMatrix2D;
import cern.colt.matrix.impl.SparseDoubleMatrix3D;
import fr.lipn.tensor.exceptions.UnsupportedModeException;

public class ThreeWayTensor extends SparseDoubleMatrix3D {
	private static final long serialVersionUID = 1L;
	
	public ThreeWayTensor(int slices, int rows, int columns) {
		super(slices, rows, columns);
	}
	
	/**
	 * Calculates the Frobenius norm for this tensor
	 * @return
	 */
	public double norm() {
		double sum=0.0d;
		for(int i=0; i<this.rows; i++){
			for(int j=0; j <this.columns; j++){
				for(int k=0; k < this.slices; k++){
					sum+=Math.pow(this.getQuick(k, i, j), 2.0d);
				}
			}
		}
		
		return Math.sqrt(sum);
	}
	
	/**
	 * Calculates the inner product between the current tensor and the parameter tensor t
	 * @param t
	 * @return
	 */
	public double innerProduct(ThreeWayTensor t){
		double sum=0.0d;
		for(int i=0; i<this.rows; i++){
			for(int j=0; j <this.columns; j++){
				for(int k=0; k < this.slices; k++){
					sum+=(this.getQuick(k, i, j)*t.getQuick(k, i, j));
				}
			}
		}
		return sum;
	}
	
	/**
	 * Returns true if the tensor is a cubical one (rows=slices=columns)
	 * @return
	 */
	public boolean isCubical(){
		return (this.columns==this.rows) && (this.rows==this.slices);
	}
	
	/**
	 * Returns a 2W Tensor corresponding to the flattening mode: (1, 2 or 3 )
	 * @param mode
	 * @return
	 * @throws Exception 
	 */
	public TwoWayTensor flatten(int mode) throws UnsupportedModeException {
		int nr=0, nc=0;
		switch(mode){
			case Tensor.MODE1: nr=this.rows; nc=this.columns*this.slices; break;
			case Tensor.MODE2: nr=this.columns; nc=this.rows*this.slices; break;
			case Tensor.MODE3: nr=this.slices; nc=this.rows*this.columns; break;
			default: throw new UnsupportedModeException();
		}
		
		TwoWayTensor t = new TwoWayTensor(nr, nc);

		
		if(mode==Tensor.MODE1){
			for(int k=0; k< this.slices; k++){
				for(int i=0; i < this.rows; i++){
					for(int j=0; j < this.columns; j++){
						t.set(i, (k*this.columns+j), this.getQuick(k, i, j));
					}
				}
			}
		}
		
		if(mode==Tensor.MODE2){
			for(int i=0; i< this.rows; i++){
				for(int j=0; j < this.columns; j++){
					for(int k=0; k < this.slices; k++){
						t.set(j, i+(this.rows*k), this.getQuick(k,i, j));
					}
				}
			}
		}
		
		if(mode==Tensor.MODE3){
			for(int i=0; i< this.rows; i++){
				for(int j=0; j < this.columns; j++){
					for(int k=0; k < this.slices; k++){
						t.set(k, j+(i*this.columns), this.getQuick(k, i, j));
					}
				}
			}
		}
		return t;
	}
	
	/**
	 * Returns a 3W Tensor corresponding to the mode-product with matrix U
	 * @param mode
	 * @return
	 * @throws Exception 
	 */
	public ThreeWayTensor modeProduct(TwoWayTensor U, int mode) throws UnsupportedModeException {
		int ns =0;
		switch(mode) {
			case Tensor.MODE1: ns=this.slices; break;
			case Tensor.MODE2: ns=this.rows; break;
			case Tensor.MODE3: ns=this.columns; break;
			default: ns=0; throw new UnsupportedModeException();
		}
		ThreeWayTensor t = null; 
		
		DoubleMatrix2D [] mm = new DoubleMatrix2D[ns];
		
		for(int k=0; k<ns; k++) {
			DoubleMatrix2D X=null;
			switch(mode) {
				case Tensor.MODE1: X= this.viewSlice(k); break;
				case Tensor.MODE2: X= this.viewRow(k); break;
				case Tensor.MODE3: X= this.viewColumn(k); break;
			}
			DoubleMatrix2D res = U.zMult(X, null);
			mm[k]=res;
		}
		
		t = new ThreeWayTensor(ns, mm[0].rows(), mm[0].columns());
		
		for(int k=0; k< ns; k++) {
			DoubleMatrix2D res =mm[k];
			for(int i=0; i< res.rows(); i++) {
				for(int j=0; j< res.columns(); j++) {
					t.setQuick(k, i,j, res.getQuick(i, j));
				}
			}
		}	
		
		return t;

	}
	

}
