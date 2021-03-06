package org.encog.ml.bayesian.parse;

import java.util.ArrayList;
import java.util.List;

import org.encog.ml.bayesian.BayesianChoice;
import org.encog.ml.bayesian.BayesianError;
import org.encog.ml.bayesian.BayesianEvent;

public class ParsedEvent {
	private final String label;
	private String value;
	private final List<ParsedChoice> list = new ArrayList<ParsedChoice>();
	
	public ParsedEvent(String theLabel) {
		this.label = theLabel;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getLabel() {
		return label;
	}

	public int resolveValue(BayesianEvent actualEvent) {
		int result = 0;
		
		if( this.value==null ) {
			throw new BayesianError("Value is undefined for " + this.label + " should express a value with +, - or =.");
		}
		
		for(BayesianChoice choice: actualEvent.getChoices()) {
			if( this.value.equals(choice.getLabel())) {
				return result;
			}
			result++;
		}
		
		// resolve true/false if not found, probably came from +/- notation
		if( this.value.equalsIgnoreCase("true")) {
			return 0;
		} else if( this.value.equalsIgnoreCase("false")) {
			return 1;
		}
		
		// try to resolve numeric index
		try {
			int i = Integer.parseInt(this.value);
			if( i<actualEvent.getChoices().size() ) {
				return i;
			}
		} catch(NumberFormatException ex) {
			// well, we tried
		}
		
		// error out if nothing found
		throw new BayesianError("Can'f find choice " + this.value + " in the event " + this.label );
	}
	
	
	
	public List<ParsedChoice> getList() {
		return list;
	}

	public String toString() {
		StringBuilder result = new StringBuilder();
		result.append("[ParsedEvent:label=");
		result.append(this.label);
		result.append(",value=");
		result.append(this.value);
		result.append("]");
		return result.toString();
	}
	
}
