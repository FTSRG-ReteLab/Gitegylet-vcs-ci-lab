package hu.bme.mit.train.tachograph;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

import java.util.Date;

public class TrainTachograph {
	private Table<Date, Integer, Integer> values;
	
	public TrainTachograph() {
		values = HashBasedTable.create();
	}
	
	public void recordValues(Date d, Integer joysticPosition, Integer referenceSpeed) {
		values.put(d, joysticPosition, referenceSpeed);
	}
	
	public int getNumberOfElements() {
		return values.size();
	}
}
