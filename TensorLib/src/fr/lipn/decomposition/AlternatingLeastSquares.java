package fr.lipn.decomposition;

import cern.colt.matrix.DoubleMatrix2D;
import cern.colt.matrix.linalg.Algebra;
import cern.jet.random.Uniform;
import cern.jet.random.engine.MersenneTwister;
import cern.jet.random.engine.RandomEngine;
import fr.lipn.tensor.Tensor;
import fr.lipn.tensor.ThreeWayTensor;
import fr.lipn.tensor.TwoWayTensor;
import fr.lipn.tensor.exceptions.UnsupportedModeException;

public class AlternatingLeastSquares {
	private int MAXITER=1000;
	private double eps=10e-3;
	private int r=100; //number of factors 
	
	private TwoWayTensor A, B, C;
	
	public AlternatingLeastSquares(int numberOfFactors){
		this.r=numberOfFactors;
	}
	
	public AlternatingLeastSquares(int numberOfFactors, int max_iterations){
		this.r=numberOfFactors;
		this.MAXITER= max_iterations;
	}
	
	public AlternatingLeastSquares(int numberOfFactors, int max_iterations, double eps){
		this.r=numberOfFactors;
		this.MAXITER= max_iterations;
		this.eps=eps;
	}
	
	/**
	 * Decomposes the tensor t into a sum of #factors bases
	 * @param t
	 */
	public void factorize(ThreeWayTensor t){
		Algebra alg = new Algebra();
		
		A = new TwoWayTensor(t.rows(), r);
		B = new TwoWayTensor(t.columns(), r);
		C = new TwoWayTensor(t.slices(), r);
		
		init(A); init(B); init(C);
		/*
		System.err.println(A);
		System.err.println(B);
		System.err.println(C);
		*/
		
		try {
			
			TwoWayTensor X1= t.flatten(Tensor.MODE1);
			//System.err.println(X1);
			
			TwoWayTensor H = C.krProduct(B);
			TwoWayTensor Ht = (TwoWayTensor) alg.transpose(H); //transposed H
			DoubleMatrix2D Obj = A.zMult(Ht, null); //objective
			
			TwoWayTensor diff = new TwoWayTensor(X1.rows(), X1.columns());
			
			for(int i=0; i< X1.rows(); i++) {
				for(int j=0; j<X1.columns(); j++) {
					diff.set(i, j, X1.getQuick(i, j)-Obj.getQuick(i, j));
				}
			}
			
			System.err.println("Objective: "+alg.normF(diff));
			
			//TODO: finish implementation: matrix update etc.
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Randomly initialize the three factor matrices
	 * by default, random values are in the range [0,1]
	 */
	private void init(TwoWayTensor T) {
		RandomEngine rengine = new MersenneTwister();
		Uniform randDist = new Uniform(0d, 1d, rengine);
		for(int i=0; i< T.rows(); i++){
			for(int j=0; j < T.columns(); j++){
				T.setQuick(i, j, randDist.nextDouble());
			}
		}
		
		
	}
	
	
}
