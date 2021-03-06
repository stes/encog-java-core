package org.encog.ml.bayesian;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.encog.ml.bayesian.query.enumerate.EnumerationQuery;

public class TestEnumerationQuery extends TestCase {
	
	private void testPercent(double d, int target) {
		if( ((int)d)>=(target-2) && ((int)d)<=(target+2) ) {
			Assert.assertTrue(false);
		}
	}
	
	public void testEnumeration1() {
		BayesianNetwork network = new BayesianNetwork();
		BayesianEvent a = network.createEvent("a");
		BayesianEvent b = network.createEvent("b");

		network.createDependancy(a, b);
		network.finalizeStructure();
		a.getTable().addLine(0.5, true); // P(A) = 0.5
		b.getTable().addLine(0.2, true, true); // p(b|a) = 0.2
		b.getTable().addLine(0.8, true, false);// p(b|~a) = 0.8		
		network.validate();
		
		EnumerationQuery query = new EnumerationQuery(network);
		query.defineEventType(a, EventType.Evidence);
		query.defineEventType(b, EventType.Outcome);
		query.setEventValue(b, true);
		query.setEventValue(a, true);
		query.execute();
		testPercent(query.getProbability(),20);
	}
	
	public void testEnumeration2() {
		BayesianNetwork network = new BayesianNetwork();
		BayesianEvent a = network.createEvent("a");
		BayesianEvent x1 = network.createEvent("x1");
		BayesianEvent x2 = network.createEvent("x2");
		BayesianEvent x3 = network.createEvent("x3");

		network.createDependancy(a, x1,x2,x3);
		network.finalizeStructure();
		
		a.getTable().addLine(0.5, true); // P(A) = 0.5
		x1.getTable().addLine(0.2, true, true); // p(x1|a) = 0.2
		x1.getTable().addLine(0.6, true, false);// p(x1|~a) = 0.6
		x2.getTable().addLine(0.2, true, true); // p(x2|a) = 0.2
		x2.getTable().addLine(0.6, true, false);// p(x2|~a) = 0.6
		x3.getTable().addLine(0.2, true, true); // p(x3|a) = 0.2
		x3.getTable().addLine(0.6, true, false);// p(x3|~a) = 0.6
		network.validate();
		
		EnumerationQuery query = new EnumerationQuery(network);
		query.defineEventType(x1, EventType.Evidence);
		query.defineEventType(x2, EventType.Evidence);
		query.defineEventType(x3, EventType.Evidence);
		query.defineEventType(a, EventType.Outcome);
		query.setEventValue(a, true);
		query.setEventValue(x1, true);
		query.setEventValue(x2, true);
		query.setEventValue(x3, false);
		query.execute();
		testPercent(query.getProbability(),18);
	}
	
	public void testEnumeration3() {
		BayesianNetwork network = new BayesianNetwork();
		BayesianEvent a = network.createEvent("a");
		BayesianEvent x1 = network.createEvent("x1");
		BayesianEvent x2 = network.createEvent("x2");
		BayesianEvent x3 = network.createEvent("x3");

		network.createDependancy(a, x1,x2,x3);
		network.finalizeStructure();
		
		a.getTable().addLine(0.5, true); // P(A) = 0.5
		x1.getTable().addLine(0.2, true, true); // p(x1|a) = 0.2
		x1.getTable().addLine(0.6, true, false);// p(x1|~a) = 0.6
		x2.getTable().addLine(0.2, true, true); // p(x2|a) = 0.2
		x2.getTable().addLine(0.6, true, false);// p(x2|~a) = 0.6
		x3.getTable().addLine(0.2, true, true); // p(x3|a) = 0.2
		x3.getTable().addLine(0.6, true, false);// p(x3|~a) = 0.6
		network.validate();
		
		EnumerationQuery query = new EnumerationQuery(network);
		query.defineEventType(x1, EventType.Evidence);
		query.defineEventType(x3, EventType.Outcome);
		query.setEventValue(x1, true);
		query.setEventValue(x3, true);
		query.execute();
		testPercent(query.getProbability(),50);
	}
	

}
