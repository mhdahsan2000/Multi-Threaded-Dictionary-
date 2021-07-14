package Functions;
import org.omg.CORBA.PUBLIC_MEMBER ; 
import org.omg.CORBA.* ; 


public class Functions {
	
	// The Command operations .
	
	public final static int QUERIES = 0 ;    // QUERY meaning of the existing word in the dictionary 
	public final static int ADDWORD = 1 ;    // ADD 
	public final static int REMOVEWORD = 2 ;  // REMOVE
	
	
	// The Command State operations. 
	
	public final static int SUCCESSSTATUS = 3 ;   // SUCCESS 
	public final static int FAILSTATUS = 4 ;       // FAIL 
	
	
	// The Networking error status.
	
	public final static int UNKNOWNHOST = 404 ; 
	public final static int CONNECTIONREFUSED = 404 ; 
	public final static int IOERROR = 404 ; 
	public final static int TIMEOUTERROR = 400 ; 

}
