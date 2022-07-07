import java.util.Arrays;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import java.sql.Timestamp;  
import java.time.Instant; 

public class SynchronousClient {
	
	private static final Logger logger = Logger.getLogger(SynchronousClient.class.getName());

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		
		logger.info("Adder function is currently running in thread " + Thread.currentThread().getName() + 
				    " | Timestamp is : " + Timestamp.from(Instant.now()));
		    try {
		        TimeUnit.SECONDS.sleep(10);
		    } catch (InterruptedException e) {
		       throw new IllegalStateException(e);
		    }
		    System.out.println(ClientFactory.getInstance().callAdder(2, 3));
		
		logger.info("Sorter function is currently running in thread " + Thread.currentThread().getName() + 
				    " | Timestamp is : " + Timestamp.from(Instant.now()));
		    try {
		        TimeUnit.SECONDS.sleep(10);
		    } catch (InterruptedException e) {
		       throw new IllegalStateException(e);
		    }
		    int[] arr = { 5, -2, 23, 7, 87, -42, 509 };
		    int[]sort_out = ClientFactory.getInstance().callSorter(arr);
		System.out.println(Arrays.toString(sort_out));
		
	}
}
