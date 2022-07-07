import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Hello extends Remote {
    String sayHello() throws RemoteException;
    int add(int x,int y)throws RemoteException;
    String uploadFile(byte[] bytes, String filename)throws RemoteException;
    int[] Sorter(int[] nums_list) throws RemoteException;
    String[] getFiles() throws RemoteException;
    byte[] downloadFile(String filename)throws RemoteException;
    String renameFile(String oldfilename, String newfilename) throws RemoteException;
    String deleteFile(String filename) throws RemoteException;
}