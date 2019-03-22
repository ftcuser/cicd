
/**
 * Troubling Rank Bugs Sample.
 */
public class TroublingRankBugs {

	/**
	 * Empty synchronized.
	 */
	private void emptySynchronized() {
		synchronized (this) {
			// Forgot implementation
		}
	}

	/**
	 * Sleep in synchronized.
	 * 
	 * @throws InterruptedException
	 *             the interrupted exception
	 */
	private void sleepInSynchronized() throws InterruptedException {
		synchronized (this) {
			Thread.sleep(5000);
		}
	}

	/**
	 * The main method.
	 * 
	 * @param args
	 *            the args
	 * @throws InterruptedException
	 *             the interrupted exception
	 */
	public static void main(String[] args) throws InterruptedException {
		TroublingRankBugs instance = new TroublingRankBugs();
		instance.emptySynchronized();
		instance.sleepInSynchronized();
	}
}
