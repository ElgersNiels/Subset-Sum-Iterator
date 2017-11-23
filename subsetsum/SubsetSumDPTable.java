package subsetsum;

import java.util.List;

import toInt.ToInt;

public class SubsetSumDPTable<T> {

	private boolean[][] table;
	
	public SubsetSumDPTable(int n, int sum) {
		this.table = new boolean[n][sum + 1];
	}
	
	public void populate(List<T> elements, int sum, List<? extends ToInt<T>> toInt) {
		if (elements.size() > table.length)
			this.table = new boolean[elements.size()][this.table[0].length];
		
		reset();
		
		for (int i = 0; i < elements.size(); i++)
			table[i][0] = true;
		
		if (toInt.get(0).toInt(elements.get(0)) <= sum)
			table[0][toInt.get(0).toInt(elements.get(0))] = true;

		for (int i = 1; i < elements.size(); i++)
	        for (int j = 0; j < sum + 1; j++)
	        	table[i][j] = toInt.get(i).toInt(elements.get(i)) <= j 
	            ? table[i - 1][j] || table[i - 1][j - toInt.get(i).toInt(elements.get(i))] : table[i - 1][j];
	}
	
	public boolean get(int i, int subsum) {
		return table[i][subsum];
	}
	
	private void reset() {
		for (int i = 0; i < table.length; i++)
			for (int j = 0; j < table[0].length; j++)
				table[i][j] = false;
	}
}
