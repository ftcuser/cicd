
/**
 * Scary Rank Bugs Sample
 */
public class ScaryRankBugs {
	
	/** The check str value. */
	private String checkStrValue = null;

	/**
	 * The Constructor.
	 */
	public ScaryRankBugs() {
		this.checkStrValue = "SomeValue";
	}

	/**
	 * Execute some conditions.
	 */
	private void executeSomeConditions() {
		if ("SomeValue".equalsIgnoreCase(this.checkStrValue)) {
			// Condition 1
		} 
		else if ("SomeValue".equals(this.checkStrValue)) {
			// Duplicate condition
		}
	}

	/**
	 * Incorrect assignment in if condition.
	 */
	private static void incorrectAssignmentInIfCondition() {
		boolean value = false;
		if (value = true) {
			//do Something
		} else {
			//else Do Something
		}
	}

	/**
	 * The main method.
	 *
	 * @param args the args
	 */
	public static void main(String[] args) {
		ScaryRankBugs instance = new ScaryRankBugs();
		instance.executeSomeConditions();
	}
}
