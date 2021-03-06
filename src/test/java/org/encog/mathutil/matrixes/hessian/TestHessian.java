package org.encog.mathutil.matrixes.hessian;

import java.util.Arrays;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.encog.engine.network.activation.ActivationSigmoid;
import org.encog.mathutil.matrices.hessian.ComputeHessian;
import org.encog.mathutil.matrices.hessian.HessianCR;
import org.encog.mathutil.matrices.hessian.HessianFD;
import org.encog.mathutil.randomize.ConsistentRandomizer;
import org.encog.ml.data.MLDataSet;
import org.encog.ml.data.basic.BasicMLDataSet;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.XOR;
import org.encog.neural.networks.layers.BasicLayer;

public class TestHessian extends TestCase {
	
	private void dump(ComputeHessian hess, String name) {
		System.out.println(name);
		double[][] h = hess.getHessian();
		System.out.println("Gradients: " + Arrays.toString(hess.getGradients()));
		for(int i=0;i<h.length;i++) {
			System.out.println(Arrays.toString(h[i]));
		}
	}
	
	public void testSingleOutput() {
		
		BasicNetwork network = new BasicNetwork();
		network.addLayer(new BasicLayer(null,true,2));
		network.addLayer(new BasicLayer(new ActivationSigmoid(),true,2));
		network.addLayer(new BasicLayer(new ActivationSigmoid(),false,1));
		network.getStructure().finalizeStructure();
		
		(new ConsistentRandomizer(-1,1)).randomize(network);
		
		MLDataSet trainingData = new BasicMLDataSet(XOR.XOR_INPUT,XOR.XOR_IDEAL);		
		
		HessianFD testFD = new HessianFD(); 
		testFD.init(network, trainingData);
		testFD.compute();
				
		HessianCR testCR = new HessianCR(); 
		testCR.init(network, trainingData);
		testCR.compute();
		
		//dump(testFD, "FD");
		//dump(testCR, "CR");
		Assert.assertTrue(testCR.getHessianMatrix().equals(testFD.getHessianMatrix(), 4));
	}
	
	public void testDualOutput() {
		
		BasicNetwork network = new BasicNetwork();
		network.addLayer(new BasicLayer(null,true,2));
		network.addLayer(new BasicLayer(new ActivationSigmoid(),true,2));
		network.addLayer(new BasicLayer(new ActivationSigmoid(),false,2));
		network.getStructure().finalizeStructure();
		
		(new ConsistentRandomizer(-1,1)).randomize(network);
		
		MLDataSet trainingData = new BasicMLDataSet(XOR.XOR_INPUT,XOR.XOR_IDEAL2);		
		
		HessianFD testFD = new HessianFD(); 
		testFD.init(network, trainingData);
		testFD.compute();
		
		//dump(testFD, "FD");
				
		HessianCR testCR = new HessianCR(); 
		testCR.init(network, trainingData);
		testCR.compute();
		
		
		//dump(testCR, "CR");
		Assert.assertTrue(testCR.getHessianMatrix().equals(testFD.getHessianMatrix(), 4));
	}
}
