import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;


public class Helper {
	
	private static String client_filepath = "C:/CSE5306/Client Files/";

	public static void main(String[] args) throws IOException,
	InterruptedException {
		
		String[] files_list = ClientFactory.getInstance().callListFiles();
		System.out.println(files_list);

		Path faxFolder = Paths.get(client_filepath);
		WatchService watchService = FileSystems.getDefault().newWatchService();
		faxFolder.register(watchService, StandardWatchEventKinds.ENTRY_CREATE, 
				StandardWatchEventKinds.ENTRY_DELETE, 
				StandardWatchEventKinds.ENTRY_MODIFY);

		boolean valid = true;
		do {
			WatchKey watchKey = watchService.take();

			for (WatchEvent event : watchKey.pollEvents()) {
				WatchEvent.Kind kind = event.kind();
				if (StandardWatchEventKinds.ENTRY_CREATE.equals(event.kind())) {
					String fileName = event.context().toString();
					System.out.println("File Created:" + fileName);
					String out = ClientFactory.getInstance().callUploadFile(fileName);
					System.out.println("File uploaded:" + out);
				}
				if (StandardWatchEventKinds.ENTRY_DELETE.equals(event.kind())) {
					String fileName = event.context().toString();
					System.out.println("File Deleted:" + fileName);
					String out = ClientFactory.getInstance().callDeleteFile(fileName);
					System.out.println("File Deleted:" + out);
				}
				if (StandardWatchEventKinds.ENTRY_MODIFY.equals(event.kind())) {
					String fileName = event.context().toString();
					System.out.println("File Modified:" + fileName);
					String out = ClientFactory.getInstance().callUploadFile(fileName);
					System.out.println("File modified:" + out);
				}
			}
			valid = watchKey.reset();

		} while (valid);

	}
}