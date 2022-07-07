import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ClientFactory {
	
	private static ClientFactory instance;
	private Hello stub;
	private static String client_filepath = "C:/CSE5306/Client Files/";
	
	private ClientFactory() {
		try {
			Registry registry = LocateRegistry.getRegistry(5001);
            stub = (Hello) registry.lookup("Hello");
		} catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
	}
	
	public static ClientFactory getInstance() {
        if (instance == null) {
        	instance = new ClientFactory();
        }

        return instance;
    }
	
	//call ADDER
	public int callAdder(int x, int y) {
		int out = 0;
		if(instance!=null) {
			try {
				out = stub.add(x, y);
//	            System.out.println("response: " + out);
	            return out;
			} catch (Exception e) {
	            System.err.println("Client exception: " + e.toString());
	            e.printStackTrace();
	        }
		}
		return out;
	}
	
	//call SORTER
		public int[] callSorter(int[] nums_list) {
			int[] sort_out = null;
			if(instance!=null) {
				try {
					sort_out = stub.Sorter(nums_list);
//		            System.out.println("response: " + out);
				} catch (Exception e) {
		            System.err.println("Client exception: " + e.toString());
		            e.printStackTrace();
		        }
			}
			return sort_out;
		}
	
	//Call sayHello
	public String callSayHello() {
		String response = null;
		if(instance!=null) {
			try {
				response = stub.sayHello();
//	            System.out.println("response: " + response);
	            return response;
			} catch (Exception e) {
	            System.err.println("Client exception: " + e.toString());
	            e.printStackTrace();
	        }
		}
		return response;
	}
	
	//Call UPLOAD file function
	public String callUploadFile(String filename) {
		String res = null;
		try {
			StringBuilder filepath = new StringBuilder(client_filepath);
			filepath.append(filename);
	        Path file = Paths.get(filepath.toString());
	        byte[] data = Files.readAllBytes(file);
	        res = stub.uploadFile(data, filename);
	        System.out.println("file response: " + res);
		} catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
		return res;
	}
	
	//Call LIST files function
	public String[] callListFiles() {
		String[] file_list = null;
		try {
			file_list = stub.getFiles();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return file_list;
        
	}
	
	//Call DOWNLOAD file function
	public String callDownloadFile(String filename) throws IOException {
        byte[] file_data = null;
		try {
			file_data = stub.downloadFile(filename);
			StringBuilder filepath = new StringBuilder(client_filepath);
	        filepath.append(filename);
	        FileOutputStream fos = new FileOutputStream(filepath.toString());
			   fos.write(file_data);
	        fos.close();
	        System.out.println("File downloaded");
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return "File downloaded: " + filename;
        
	}
	
	//Call DOWNLOAD file function
		public String callDeleteFile(String filename) {
			String delete_out = null;
			try {
				delete_out = stub.deleteFile(filename);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return delete_out;     
		}
	
	//Call RENAME file function
	public String callFileRename(String old_filename, String new_filename) {
    	String rename_out = null;
		try {
			rename_out = stub.renameFile(old_filename, new_filename);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return rename_out;
	}

}
