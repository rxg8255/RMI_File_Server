import java.util.Arrays;

public class Client {

    private Client() {}
    
    public static void main(String[] args) {

        String action = (args.length < 1) ? "define function" : args[0];
    	
        try {
            
            //Call say Hello
            String response = ClientFactory.getInstance().callSayHello();
            System.out.println("response: " + response);
            
            //Call adder
            if(action.equals("adder")) {
            	int first_num = Integer.parseInt(args[1]);
            	int second_num = Integer.parseInt(args[2]);
            	System.out.println(ClientFactory.getInstance().callAdder(first_num, second_num));
            }
            
          //Call sorter
            else if(action.equals("sorter")) { 
            	int[] arr = new int[args[1].split(",").length];
            	int i = 0;
//            		{ 5, -2, 23, 7, 87, -42, 509 };
            	for (String token : args[1].split(","))
            		arr[i++] = Integer.parseInt(token);
            	System.out.println(Arrays.toString(arr));
    		    System.out.println(Arrays.toString(ClientFactory.getInstance().callSorter(arr)));
            }
            
            //Call file upload
            else if(action.equals("upload")) {
            	String filename = args[1];
                String res = ClientFactory.getInstance().callUploadFile(filename);
                System.out.println("file response: " + res);
            }
            
            //Call list files on server
            else if(action.equals("listfiles")) {
            	String[] file_list = ClientFactory.getInstance().callListFiles();
                System.out.println("Below are the list of files available for download:");
                for (String f : file_list) {
    	            System.out.println(f);
    	        }
            }
            
            //Call file download from server
            else if(action.equals("download")) {
            	String filename = args[1];
            	String res = ClientFactory.getInstance().callDownloadFile(filename);
                System.out.println(res);
            }
            
          //Call file delete from server
            else if(action.equals("delete")) {
            	String filename = args[1];
            	String res = ClientFactory.getInstance().callDeleteFile(filename);
                System.out.println(res);
            }
            
            //Call file rename
            else if(action.equals("rename")) {
            	String old_filename = args[1];
            	String new_filename = args[2];
            	String rename_out = ClientFactory.getInstance().callFileRename(old_filename, new_filename);
                System.out.println(rename_out);
            }  
            
            else {
            	throw new Exception("Invalid action, available actions are upload, download, rename, listfiles");
            }
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }
}