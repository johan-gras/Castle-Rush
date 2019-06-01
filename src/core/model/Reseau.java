package core.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Enumeration;

public class Reseau {
	
	private World world;
	
	private ServerSocket socketserver;
	private Socket socket;
	private BufferedReader in;
	private PrintWriter out;
	private boolean serveur= false;
	@SuppressWarnings("unused")
	private boolean client = false;

	public Reseau(int multijoueur, String ip){

		//Multijoueur == 1 on sera Serveur
		if(multijoueur == 1){
			try {
				serveur = true;
				socketserver = new ServerSocket(2009);
				socket = socketserver.accept();
				out = new PrintWriter(socket.getOutputStream());
				in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				System.out.println("Connection au serveur par le client.");
				
				System.out.println(in.readLine());

			}catch (IOException e) {
				e.printStackTrace();
			}
		}

		//Multijoueur == 2 on sera Client
		if(multijoueur == 2){
			try {
				client = true;
				socket = new Socket(InetAddress.getByName(ip), 2009);
				out = new PrintWriter(socket.getOutputStream());
				in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

				out.println("Client : Jme suis co jsuis un bgggg :p");
				out.flush();

			}catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void update(){
		try {
			if (in.ready()){
				String s = in.readLine();
				
				if (s.toCharArray()[0] == 'M')
					world.getSpawn().addMonster(world.getMonsters().get(Character.getNumericValue(s.toCharArray()[1])));
				
				else if (s.toCharArray()[0] == 'D')
					world.getInfoGame().lessIncome(world.getMonsters().get(Character.getNumericValue(s.toCharArray()[1])).getIncome());
				
				else if (s.toCharArray()[0] == 'L')
					world.getInfoGame().gameOver();
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void sendMonster(int num){
		send("M" + num);
	}
	
	public void deathMonster(int num){
		send("D" + num);
	}
	
	public void haveLost(){
		send("L");
	}
	
	private void send(String s){
		out.println(s);
		out.flush();
	}

	public void disconnect(){
		if (serveur){
			try {
				socketserver.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Deconnexion serveur.");
		} 
		else{
			try {
				socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Deconnexion client.");
		}
	}

	public void setWorld(World world) {
		this.world = world;
	}
	
	public static InetAddress getLocalHostLANAddress() {
	    try {
	        InetAddress candidateAddress = null;
	        // Iterate all NICs (network interface cards)...
	        for (Enumeration ifaces = NetworkInterface.getNetworkInterfaces(); ifaces.hasMoreElements();) {
	            NetworkInterface iface = (NetworkInterface) ifaces.nextElement();
	            // Iterate all IP addresses assigned to each card...
	            for (Enumeration inetAddrs = iface.getInetAddresses(); inetAddrs.hasMoreElements();) {
	                InetAddress inetAddr = (InetAddress) inetAddrs.nextElement();
	                if (!inetAddr.isLoopbackAddress()) {

	                    if (inetAddr.isSiteLocalAddress()) {
	                        // Found non-loopback site-local address. Return it immediately...
	                        return inetAddr;
	                    }
	                    else if (candidateAddress == null) {
	                        // Found non-loopback address, but not necessarily site-local.
	                        // Store it as a candidate to be returned if site-local address is not subsequently found...
	                        candidateAddress = inetAddr;
	                        // Note that we don't repeatedly assign non-loopback non-site-local addresses as candidates,
	                        // only the first. For subsequent iterations, candidate will be non-null.
	                    }
	                }
	            }
	        }
	        if (candidateAddress != null) {
	            // We did not find a site-local address, but we found some other non-loopback address.
	            // Server might have a non-site-local address assigned to its NIC (or it might be running
	            // IPv6 which deprecates the "site-local" concept).
	            // Return this non-loopback candidate address...
	            return candidateAddress;
	        }
	        // At this point, we did not find a non-loopback address.
	        // Fall back to returning whatever InetAddress.getLocalHost() returns...
	        InetAddress jdkSuppliedAddress = InetAddress.getLocalHost();
	        if (jdkSuppliedAddress == null) {
	            throw new UnknownHostException("The JDK InetAddress.getLocalHost() method unexpectedly returned null.");
	        }
	        return jdkSuppliedAddress;
	    }
	    catch (Exception e) {
	        UnknownHostException unknownHostException = new UnknownHostException("Failed to determine LAN address: " + e);
	        unknownHostException.initCause(e);
	    }
	    return null;
	}

}
