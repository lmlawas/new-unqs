public class IPCoverter{

	public static void main(String[] args){
		String ipAddr = longToIp(Long.parseLong(args[0]));
		System.out.println("IP = " + ipAddr);
	}

	public static String longToIp(long i) {
	// source: https://www.mkyong.com/java/java-convert-ip-address-to-decimal-number/
		return ((i >> 24) & 0xFF) +
                   "." + ((i >> 16) & 0xFF) +
                   "." + ((i >> 8) & 0xFF) +
                   "." + (i & 0xFF);

	}
}