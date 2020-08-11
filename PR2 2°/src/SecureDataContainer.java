import java.util.Iterator;

public interface SecureDataContainer<E> {
	/**
	 Overview: Tipo di dato modificabile, che rappresenta una collezione 
	 			SecureDataContainer<E>, che è un contenitore di oggetti di tipo E 
	 			generico. Tale collezione permette la memorizzazione e condivisione 
	 			dei dati. Garantisce un meccanismo di sicurezza dei dati, e un meccanismo 
	 			di gestione delle indentità degli utenti. In tale struttura solo alcuni utenti
	 			possono essere autorizzati dal proprietario ad accedere ai dati presenti nella
	 			propria collezione,i restanti non possono senza una adeguata autorizzazione.
	 		
	Typical Element: {<utene_0,password_0,[dato_0...dato_x-1]>...
	                 <utente_n-1,password_n-1,[dato_0...dato_y-1]>}
	                 
	        Dove: Ho un insieme che contiene triple,una singola tripla e formata cosi:
	        	  <utente_0,passwrd_0,[dati_0...dati_x-1]> dove utente e password sono
	        	  stringhe di caratteri, poi ho una collezione di dati, che riguardano i dati 
	        	  di ogni singolo utente che possono essere anche ripetuti, e sono di tipo generico E. 
	 */
	
	
	// Crea l’identità un nuovo utente della collezione.
	public void createUser(String Id, String passw) throws UserUnavailableException;
	/*
		REQUIRES: Id && passw != null && forall 0<=i<=n (utente_i!=Id).
		
		EFFECTS: Inserisco un nuovo "utente_i" nell'insieme, con l'appropiata "password_i".
		
		THROWS:Se ((Id==null) || (passw==null)) sollevo una eccezione NullPointerException.
			   Se (utenti_i==Id) sollevo l'eccezione UserUnavailableException 
			   Se ((Id=="") ||(passw=="")) sollevo l'eccezione IllegalArgumentException
	 */
	
	//Inserisce il valore del dato nella collezione se vengono rispettati i controlli di identità.
	public boolean put(String Owner, String passw, E data)throws UserUnavailableException,WrongPasswordException;
	/*
	 REQUIRES: Owner && passw && data != null && forall 0<=i<=n ((utente_i==Id) && (passw_i==password)).
	 
	 EFFECTS: Inserisco il "data" all'interno della collezione se i controlli d'identità sono rispettati,
	 		  cioè se "Id" e "passw" sono presenti all'interno della collezione. 
	 			
	 			
	 THROWS: Se ((Id==null) || (passw==null) || (data==null)) sollevo l'eccezione NullPointerException.
	 		 Se (utente_i!=Id) sollevo l'eccezione UserUnavailableException.
	 		 Se (passw!=password) sollevo  l'eccezione WrongPasswordException.
	 		 Se ((Id=="") ||(passw=="")) sollevo l'eccezione IllegalArgumentException.
	 
	 MODIFIES: This.
	 		 
	  */
	
	//Rimuove l’utente dalla collezione.
	public void removeUser(String Id, String passw) throws UserUnavailableException,WrongPasswordException;
	/*
	 REQUIRES: Id && passw !=null &&  forall 0<=i<=n ((utente_i==Id) && (passw_i==password)).
	 
	 EFFECTS Rimuovo dalla collezione l'utente in base ai parametri inseriti.
	 
	 THROWS: Se ((Id==null) || (passw==null)) sollevo l'eccezione NullPointerException.
	  		 Se (utente_i!=Id) sollevo l'eccezione UserUnavailableException.
	 		 Se (passw!=password) sollevo  l'eccezione WrongPasswordException.
	 		 Se ((Id=="") ||(passw=="")) sollevo l'eccezione IllegalArgumentException.
	 MODIFIES: This.
	 */
	
	// Restituisce il numero degli elementi di un utente presenti nella collezione.
	public int getSize(String Owner, String passw) throws UserUnavailableException,WrongPasswordException;
	/*
	 REQUIRES: Id && passw !=null && forall 0<=i<=n ((utente_i==Id) && (passw_i==password)).
	 
	 EFFECTS: Restituisco il numero degli elementi di un utente che sono presenti nella sua lista. 
	
	 THROWS: Se ((Id==null) || (passw==null)) sollevo l'eccezione NullPointerException.
	 		 Se (utente_i!=Id) sollevo l'eccezione UserUnavailableException.
	 		 Se (passw!=password) sollevo  l'eccezione WrongPasswordException.
	 		 Se ((Id=="") ||(passw=="")) sollevo l'eccezione IllegalArgumentException.
	*/
	
	// Ottiene una copia del valore del dato nella collezione se vengono rispettati i controlli di identità
	public E get(String Owner, String passw, E data) throws UserUnavailableException,WrongPasswordException,DateUnavailableException;
	/*
	 REQUIRES:  Owner && passw && data !=null && forall 0<=i<=n ((utente_i==Id) && (passw_i==password)).
	
	EFFECTS: Ottengo una copia del mio dato se "Id" e "passw" sono rispettati.
	
	THROWS:   	Se ((Id==null) || (passw==null) || (data==null)) sollevo l'eccezione NullPointerException.
	            Se (utente_i!=Id) sollevo l'eccezione UserUnavailableException.
	 		    Se (passw!=password) sollevo l'eccezione WrongPasswordException.
	 		    Se ((Id=="") ||(passw=="")) sollevo l'eccezione IllegalArgumentException.
			    Se "data" non è presente nella collezione dei dati,sollevo l'eccezione DateUnavailableException.
			  
	MODIFIES: This.
	 */
	
	//Rimuove il dato nella collezione se vengono rispettati i controlli di identità.
	public E remove(String Owner, String passw, E data)throws UserUnavailableException,WrongPasswordException,DateUnavailableException;
	/*
	 REQUIRES: Owner && passw && data !=null && forall 0<=i<=n ((utente_i==Id) && (passw_i==password)).
			   
	EFFECTS:   Rimuovo il dato dalla collezione se "Id" e "password" sono rispettati.
	
	THROWS:    Se ((Id==null) || (passw==null) || (data==null)) sollevo l'eccezione NullPointerException.
	 		   Se (utente_i!=Id) sollevo l'eccezione UserUnavailableException.
	 		   Se (passw!=password) sollevo l'eccezione WrongPasswordException.
	 		   Se ((Id=="") ||(passw=="")) sollevo l'eccezione IllegalArgumentException.
			   Se "data" non è presente nella collezione dei dati, sollevo l'eccezione DateUnavailableException.
			  
	MODIFIES: This.  
	 */
	
	// Crea una copia del dato nella collezione se vengono rispettati i controlli di identità
	public void copy(String Owner, String passw, E data) throws UserUnavailableException,DateUnavailableException,WrongPasswordException ;
	/*
	 REQUIRES: Owner && passw && data !=null && forall 0<=i<=n ((utente_i==Id) && (passw_i==password)).
	
	 EFFECTS: Creo una copia di "data" dentro la mia collezione se "Id" e "passw" sono rispettati.
	 
	 THROWS:   Se ((Id==null) || (passw==null) || (data==null)) sollevo l'eccezione NullPointerException.
	 		   Se (utente_i!=Id) sollevo l'eccezione UserUnavailableException.
	 		   Se (passw!=password) sollevo  l'eccezione WrongPasswordException.
	 		   Se ((Id=="") ||(passw=="")) sollevo l'eccezione IllegalArgumentException.
			   Se "data" non è presente nella collezione dei dati,sollevo l'eccezione DateUnavailableException.
	 
	 MODIES: This.
	 */

	// Condivide il dato nella collezione con un altro utente se vengono rispettati i controlli di identità
	public void share(String Owner, String passw, String Other, E data) throws UserUnavailableException,DateUnavailableException,WrongPasswordException,EqualUserExcpetion;
	/*
	 REQUIRES:Owner && passw && data && Other !=null && forall 0<=i<=n ((utente_i==Id) && (passw_i==password) && (utente_i==Other)).
	 
	 EFFECTS: Condivido il "data" dell'Owner con l'Other se i controlli d'indentità sono rispettati.
	  
	  THROWS:  Se ((Id==null) || (passw==null) || (data==null) || (Other==null)) sollevo l'eccezione NullPointerException.
	 		   Se (utente_i!=Id) sollevo l'eccezione UserUnavailableException.
	 		   Se (passw!=password) sollevo  l'eccezione WrongPasswordException.
	 		   Se (utente_i!=Other) sollevo l'eccezione UserUnavailableException.
	 		   Se ((Id=="") ||(passw=="") || (Other=="")) sollevo l'eccezione IllegalArgumentException.
			   Se il nome dell'Owner e uguale a quello dell'Other,  sollevo l'eccezione EqualUserExcpetion.
	
	MODIFIES: This.
	 */
	
	public Iterator<E> getIterator(String Owner, String passw) throws UserUnavailableException,WrongPasswordException;
	/*
	 REQUIRES:Owner && passw !=null && forall 0<=i<=n ((utente_i==Id) && (passw_i==password)
	 
	 EFFECTS:restituisce un iteratore (senza remove) che genera tutti i dati dell’utente in ordine arbitrario
			 se vengono rispettati i controlli di identità
		
	 THROWS: Se ((Id==null) || (passw==null)) sollevo l'eccezione NullPointerException. 
	 		 Se (utente_i!=Id) sollevo l'eccezione UserUnavailableException.
	 		 Se (passw!=password) sollevo  l'eccezione WrongPasswordException.
	 		 Se ((Id=="") ||(passw=="")) sollevo l'eccezione IllegalArgumentException.
	 */

}