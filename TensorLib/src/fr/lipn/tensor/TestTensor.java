package fr.lipn.tensor;

import fr.lipn.decomposition.AlternatingLeastSquares;
import fr.lipn.tensor.exceptions.UnsupportedModeException;
import fr.lipn.tensor.exceptions.WrongSizeException;

public class TestTensor {

	public static void main(String[] args) throws UnsupportedModeException, WrongSizeException {
		ThreeWayTensor tensor = new ThreeWayTensor(5,4,6); //slice, row, column
		
		
		tensor.set(3, 2, 1, 2.0d);
		tensor.set(4, 3, 1, 3.0d);
		tensor.set(1, 1, 0, 1.5d);
		tensor.set(1, 3, 2, 2.5d);
		
		
		System.err.println(tensor);
		System.err.println("norm: "+tensor.norm());
		System.err.println("inner prod: "+tensor.innerProduct(tensor));
		System.err.println();
		
		double [] v1= {0.5, 0.5, 0.5};
		double [] v2= {2, 3, 4, 5, 6};
		double [] v3= {0.25, 2};
		
		BaseVector a = new BaseVector(v1);
		BaseVector b = new BaseVector(v2);
		BaseVector c = new BaseVector(v3);
		
		System.err.println("T1 = a xox b:");
		TwoWayTensor t1 = a.outerProduct(b);
		System.err.println(t1);
		System.err.println();
		
		System.err.println("T2 = T1 xox c:");
		ThreeWayTensor t2 = t1.outerProduct(c);
		System.err.println(t2);
		System.err.println();
		
		System.err.println("T2 flattened mode 1:");
		TwoWayTensor m1 = t2.flatten(Tensor.MODE1);
		System.err.println(m1);
		System.err.println();
		
		System.err.println("T2 flattened mode 2:");
		TwoWayTensor m2 = t2.flatten(Tensor.MODE2);
		System.err.println(m2);
		System.err.println();
		
		System.err.println("T2 flattened mode 3:");
		TwoWayTensor m3 = t2.flatten(Tensor.MODE3);
		System.err.println(m3);
		System.err.println();
		
		TwoWayTensor U = new TwoWayTensor(2,3);
		U.set(0, 0, 1); U.set(0, 1, 3); U.set(0, 2, 5);
		U.set(1, 0, 2); U.set(1, 1, 4); U.set(1, 2, 6);
		
		System.err.println("U:");
		System.err.println(U);
		System.err.println();
		
		System.err.println("T2 mode-1prod U:");
		System.err.println(t2.modeProduct(U, Tensor.MODE1));
		System.err.println();
		
		U = new TwoWayTensor(3,2);
		U.set(0, 0, 1); U.set(0, 1, 3); U.set(2, 0, 5);
		U.set(1, 0, 2); U.set(1, 1, 4); U.set(2, 1, 6);
		
		System.err.println("U:");
		System.err.println(U);
		System.err.println();
		
		System.err.println("T2 mode-2prod U:");
		System.err.println(t2.modeProduct(U, Tensor.MODE2));
		System.err.println();
		
		System.err.println("T2 mode-3prod U:");
		System.err.println(t2.modeProduct(U, Tensor.MODE3));
		System.err.println();
		
		TwoWayTensor A = new TwoWayTensor(3,2);
		A.set(0,0,1); A.set(0,1,2); A.set(2,0,3);
		A.set(1,0,4); A.set(1,1,5); A.set(2,1,6);
		
		TwoWayTensor B = new TwoWayTensor(2,2);
		B.set(0,0,1); B.set(0,1,4); 
		B.set(1,0,2); B.set(1,1,5);
		
		System.err.println("A:");
		System.err.println(A);
		System.err.println();
		
		System.err.println("B:");
		System.err.println(B);
		System.err.println();
		
		System.err.println("A krProd B:");
		System.err.println(A.krProduct(B));
		System.err.println();
		
		AlternatingLeastSquares als = new AlternatingLeastSquares(4);
		als.factorize(t2);
		
	}

}
