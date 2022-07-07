import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import java.sql.Timestamp;  
import java.time.Instant; 

public class AsynchronousClient {
	
	private static final Logger logger = Logger.getLogger(AsynchronousClient.class.getName());

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		
		CompletableFuture<Integer> sum_out = CompletableFuture.supplyAsync(() -> {
			logger.info("Adder function is currently running in thread " + Thread.currentThread().getName() + 
				    " | Timestamp is : " + Timestamp.from(Instant.now()));
		    try {
		        TimeUnit.SECONDS.sleep(10);
		    } catch (InterruptedException e) {
		       throw new IllegalStateException(e);
		    }
		    return ClientFactory.getInstance().callAdder(2, 3);
		});
		
		CompletableFuture<int[]> sort_out = CompletableFuture.supplyAsync(() -> {
			logger.info("Sorter function is currently running in thread " + Thread.currentThread().getName() + 
				    " | Timestamp is : " + Timestamp.from(Instant.now()));
		    try {
		        TimeUnit.SECONDS.sleep(10);
		    } catch (InterruptedException e) {
		       throw new IllegalStateException(e);
		    }
		    int[] arr = { 5, -2, 23, 7, 87, -42, 509 };
		    return ClientFactory.getInstance().callSorter(arr);
		});
		
//		System.out.println(hello_string.get());
		System.out.println(sum_out.get());
		System.out.println(Arrays.toString(sort_out.get()));
		
	}
}
