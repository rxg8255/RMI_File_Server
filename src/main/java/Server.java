import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
        
public class Server implements Hello {
        
    public Server() {}
    
    private static String server_filepath = "C:/CSE5306/Server Files/";
    private static final Logger logger = Logger.getLogger(Server.class.getName());
    
    // Say Hello function
    public String sayHello() {
    	logger.info("SayHello function is currently running in thread " + Thread.currentThread().getName() + 
    		    " | Timestamp is : " + Timestamp.from(Instant.now()));
        return "Hello, world!";
    }
    
    // Adder function
    public int add(int x,int y) {
//    	try {
//			TimeUnit.SECONDS.sleep(20);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
    	logger.info("Adder function is currently running in thread " + Thread.currentThread().getName() + 
			    " | Timestamp is : " + Timestamp.from(Instant.now()));
    	return x+y;
    }
    
 // SORTER function
    public int[] Sorter(int[] nums_list) {
    	logger.info("Sorter function is currently running in thread " + Thread.currentThread().getName() + 
			    " | Timestamp is : " + Timestamp.from(Instant.now()));
    	Arrays.sort(nums_list);
    	return nums_list;
    }
    
   // File UPLOAD function
   public String uploadFile(byte[] bytes, String filename) {
	   logger.info("Upload function is currently running in thread " + Thread.currentThread().getName() + 
   		    " | Timestamp is : " + Timestamp.from(Instant.now()));
	   try {
		   String filepath = buildFilepath(filename);
		   FileOutputStream fos = new FileOutputStream(filepath);
		   fos.write(bytes);
           fos.close();
	   }
	   catch (Exception e) {
           e.printStackTrace();
       }
	   return "File created";
   }
   
   // LIST files function
   public String[] getFiles() {
	   logger.info("ListFiles on server function is currently running in thread " + Thread.currentThread().getName() + 
	   		    " | Timestamp is : " + Timestamp.from(Instant.now()));
	   String[] pathnames = null;
	   try {
		   File f = new File(server_filepath);
		   pathnames = f.list();
		   for (String pathname : pathnames) {
	            // Print the names of files and directories
	            System.out.println(pathname);
	        }
	   }
	   catch (Exception e) {
           e.printStackTrace();
           }
	   return pathnames;
	   }
   
   // DOWNLOAD file function
   public byte[] downloadFile(String filename) {
	   logger.info("Download file function is currently running in thread " + Thread.currentThread().getName() + 
	   		    " | Timestamp is : " + Timestamp.from(Instant.now()));
	   byte[] data = null;
	   try {
		   String filepath = buildFilepath(filename);
		   Path file = Paths.get(filepath);
		   data = Files.readAllBytes(file);
		   }
	   catch (Exception e) {
		   e.printStackTrace();
		   } 
	   return data;
	   }
   
   // RENAME file function
   public String renameFile(String oldfilename, String newfilename) {
	   logger.info("Rename file function is currently running in thread " + Thread.currentThread().getName() + 
	   		    " | Timestamp is : " + Timestamp.from(Instant.now()));
	   try {
		   File old_file = new File(buildFilepath(oldfilename));
		   File new_file= new File(buildFilepath(newfilename));
		   boolean flag = old_file.renameTo(new_file);
		   if(flag==false)
			   return "Rename failed";
		   }
	   catch (Exception e) {
		   e.printStackTrace();
		   }
	   return "File renamed";
   }
   
// DELETE file function
   public String deleteFile(String filename) {
	   logger.info("Delete file function is currently running in thread " + Thread.currentThread().getName() + 
	   		    " | Timestamp is : " + Timestamp.from(Instant.now()));
	   String res = null;
	   try {
		   File file_path = new File(buildFilepath(filename));
		   if (file_path.delete()) { 
			   res = "Deleted the file: " + file_path.getName();
			   } else {
			    	res = "Failed to delete the file.";
			    } 
		   }
	   catch (Exception e) {
		   e.printStackTrace();
		   }
	return res; 
	   }
	
	private String buildFilepath(String filename) {
		StringBuilder filepath = new StringBuilder(server_filepath);
		filepath.append(filename);
		return filepath.toString();
	}
        
    public static void main(String args[]) {
        
        try {
            Server obj = new Server();
            Hello stub = (Hello) UnicastRemoteObject.exportObject(obj, 0);

            // Bind the remote object's stub in the registry
            Registry registry = LocateRegistry.createRegistry(5001);
            registry.bind("Hello", stub);

            System.err.println("Server ready");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }
}