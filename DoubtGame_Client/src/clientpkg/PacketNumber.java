package clientpkg;

import java.net.Socket;

abstract public interface PacketNumber {
	static final int 	MAX_USER 		= 4;
	static final int 	totalCard 		= 52;
	static final int 	totalBuffer	= 10000;
	static final String hostName = "192.168.10.84";
	static final int 	portNumber = 6000;
	static final String Start 		= "1000";
	static final String Ready 		= "1001";
	static final String NoReady		= "1002";
	static final String Drop		= "1003";
	static final String Doubt 		= "1004";
	static final String GameEnd 	= "1005";
	static final String Connect 	= "1006";
	static final String DisConnect 	= "1007";
	static final String MSG 		= "1008";
	static final String CardNum 	= "1009";
	static final String Enter 		= "1010";
	static final String NotYourTurn = "1011";
	static final String DoubtGame	= "1012";
	static final String FailDoubt 	= "1013";
	static final String CardDeckSet = "1014";
	static final String EndGame 	= "1015";
	static final String Create		= "1016";
	static final String Edit		= "1017";
	static final String Reset		= "1018";
	Communicate		comm = new Communicate();
}
