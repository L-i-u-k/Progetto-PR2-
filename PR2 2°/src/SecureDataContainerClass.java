import java.util.Iterator;
import java.util.Vector;


public class SecureDataContainerClass <E> implements SecureDataContainer<E>{
	/*
	 FUNZIONE DI ASTRAZIONE: <utente.get(0),password.get(0),[dato.get(0).get(0)...dato.get(0).get(dato.get(0).size()-1)]>
	 											,....,
	 						<utente.get(utente.size()-1),password.get(password.size()-1),[dato.get(0).get(0)...dato.get(0).get(dato.get(0).size()-1)]>
	 
	 INVARIANTE DI RAPPRESENTAZIONE: forall i. 0<=i<=n (utente.get(i) && password.get(i) !=null)
	  								 forall i.  0<=i<=n (utente.get(i) && password.get(i) !="") 
	 								 forall i. 0<=i<utente.size() (forall j. 0<=j<dato.get(i).size() dato.get(i).get(j)!= null )
	 								 forall i,j. 0<=i<=n i!=j ==> utente.get(i) != utente.get(j)
	 					
	 */
	
	
	private Vector<String> utente;
	private Vector<String> password;
	private Vector<Vector<E>> dato;
	
	public SecureDataContainerClass() {
		/*
		 EFFECTS: Istanzio le strutture vuote
		 */
		utente = new Vector<String>();
		password = new Vector<String>();
		dato = new Vector<Vector<E>>();
	}
	
	public void createUser(String Id, String passw) throws UserUnavailableException{
		/*
		 REQUIRES: Id && passw != null && forall 0<=i<=n (utente_i!=Id).
		 
		 EFFECTS: Creo e inserisco un utente "<Utente,Passwrod,[]>" nella mia collezione.
		 
		 THROWS: Se ((Id==null) || (passw==null)) throw new NullPointerException();
			     Se (utenti_i==Id) throw new UserUnavailableException();
			     Se ((Id=="") ||(passw=="")) throw new IllegalArgumentException();
		
		MODIFIES: This.
		 */
		if(Id.isEmpty() || passw.isEmpty()) { // Controllo se i parametri sono vuoti.
			throw new IllegalArgumentException();
		}
		this.NullControl(Id,passw); //Controllo i parametri se sono "null"
		this.IdContains(Id); // Controllo se "Id" e gia contenuto nella collezione

		
		utente.add(Id);// Inserisco "Id" 
		int pos=utente.indexOf(Id);//Contiene l'indice dell'utente
		
		password.add(pos,passw);// Inserisco "passw"
		dato.add(new Vector<E>());// Creo la struttura dei dati dell' utente medesimo
	}
	
	public boolean put(String Owner, String passw, E data)throws UserUnavailableException,WrongPasswordException{
		/*
		 REQUIRES: Owner && passw && data != null && forall 0<=i<=n ((utente_i==Id) && (passw_i==password)).
		 
		 EFFECTS: Se i controlli dell'identità sono rispettati inserisco il dato nella collezione: 
		 		  dato.get(utente.indexOf(Owner)).add(data); 
		 
		 THROWS: Se ((Id==null) || (passw==null) || (data==null)) throw new NullPointerException();
	 			 Se (utente_i!=Id) throw new UserUnavailableException();
	 		 	 Se (passw!=password) throw new WrongPasswordException();
	 		 	 Se ((Id=="") ||(passw=="")) throw new IllegalArgumentException();
	 		 	 
	 	MODIFIES: This.
		*/
		 
		if(Owner.isEmpty() || passw.isEmpty()) {// Controllo se i parametri non siano vuoti 
			throw new IllegalArgumentException();
		}
		this.NullControl(Owner,passw);// Controllo se i parametri sono "null"
		this.NullDato(data);// Controllo se il dato è "null"
		if(!utente.contains(Owner)) { // Controllo se "Owner" è presente all'interno della struttura
			throw new UserUnavailableException();
		}
		this.PasswComparison(Owner,passw);// Controllo se la "passw" è presente all'interno 

	
		
		dato.get(utente.indexOf(Owner)).add(data);// Inserisco il dato all'interno della struttura dei dati, nella stessa posizione 
												  // in cui si trova l'utente
		
		return true;
		
	}
	public void removeUser(String Id, String passw) throws UserUnavailableException,WrongPasswordException{
		/*
		REQUIRES: Id && passw !=null &&  forall 0<=i<=n ((utente_i==Id) && (passw_i==password)).
		
		EFFECTS: Se i controlli d'identità sono rispettati rimuovo l'utente e la sua lista dei dati
				 dalla collezione.
		
		THROWS: Se ((Id==null) || (passw==null)) throw new NullPointerException();
	  		 	Se (utente_i!=Id) throw new UserUnavailableException();
	 		    Se (passw!=password) throw new WrongPasswordException();
	 		    Se ((Id=="") ||(passw=="")) throw new IllegalArgumentException();
	 	
	 	MODIFIES: This.

		 		  
	    */
		if(Id.isEmpty() || passw.isEmpty()) { // Controllo se i parametri sono vuoti
			throw new IllegalArgumentException();
		}
		this.NullControl(Id, passw); // Controllo se i parametri sono "null"
		
		if(!utente.contains(Id)) {// Controllo se "Id" è presente
			throw new UserUnavailableException();
		}
		this.PasswComparison(Id,passw); // Controllo che la "passw" è presente
		
		
		dato.remove(utente.indexOf(Id));// Rimuovo il dato
		int pos=utente.indexOf(Id);// Contiene l'indice dell'utente
		utente.remove(Id);// Rimuovo "Id" dell'utente
		password.remove(pos);// Rimuovo la "passw" dell'utente
	}
	
	public int getSize(String Owner, String passw) throws UserUnavailableException,WrongPasswordException{
		/*
		REQUIRES: Owner && passw && forall 0<=i<=n ((utente_i==Id) && (passw_i==password)).
		
		EFFECTS: Restituisco il numero degli elementi di un utente che sono presenti nella sua lista.
		
		THROWS:Se ((Id==null) || (passw==null)) throw new NullPointerException();
               Se (utente_i!=Id) throw new UserUnavailableException();
 		       Se (passw!=password) throw new WrongPasswordException();
 		       Se ((Id=="") ||(passw==""))  throw new IllegalArgumentException();
	 */
		if(Owner.isEmpty() || passw.isEmpty()) {// Controllo se i parametri sono vuoti
			throw new IllegalArgumentException();
		}
		this.NullControl(Owner, passw);// Controllo se i parametri sono "null"
		if(!utente.contains(Owner)) {// Controllo se "Owner" è presente
			throw new UserUnavailableException();
		}
		this.PasswComparison(Owner,passw);// Controllo se la "passw" è presente
		
	
	
		return dato.get(utente.indexOf(Owner)).size(); // Ritorno il numero di elementi dei dati.
	}
	
	public E get(String Owner, String passw, E data) throws UserUnavailableException,WrongPasswordException,DateUnavailableException {
		/*
		REQUIRES: Owner && passw && data !=null && forall 0<=i<=n ((utente_i==Id) && (passw_i==password)).
		
		EFFECTS: Se i controlli sono rispettati creo una copia del dato.
		
		THROWS:  Se ((Id==null) || (passw==null) || (data==null)) throw new NullPointerException();
 		         Se (utente_i!=Id)  throw new UserUnavailableException();
 		         Se (passw!=password) throw new WrongPasswordException();
 		         Se ((Id=="") ||(passw=="")) throw new IllegalArgumentException();
		         Se "data" non è presente nella collezione dei dati:throw new DateUnavailableException();
		   
	 */
		
		if(Owner.isEmpty() || passw.isEmpty()) {// Controllo se i parametri non siano vuoti
			throw new IllegalArgumentException(); 
		}
		this.NullControl(Owner, passw);// Controllo sei i parametri sono "null"
		this.NullDato(data);// Controllo se il "data" è "null"
		if(!utente.contains(Owner)) {// Contrllo se "Owner" è presente
			throw new UserUnavailableException();
		}
		
		this.PasswComparison(Owner,passw);// Controllo se la "passw" è presente
	
	
		Vector<E> UtenteOwner= dato.get(utente.indexOf(Owner));// Creo un riferimento all'array dei "data"
		int pos=UtenteOwner.indexOf(data); // Associo a pos l'indice del "data"
		if(pos==-1) {// Controllo che il "data" sia presente
			throw new DateUnavailableException();
		}
		return UtenteOwner.get(UtenteOwner.indexOf(data));// Faccio la copia del "data" 
	}
	
	public E remove(String Owner, String passw, E data)throws UserUnavailableException,WrongPasswordException,DateUnavailableException{
		/*
		REQUIRES: Owner && passw && data !=null && forall 0<=i<=n ((utente_i==Id) && (passw_i==password)).
		
		EFFECTS: Se i controlli d'identità sono rispettati, rimuovo il dato nella collezione dell'utente.
		
		THROWS:  Se ((Id==null) || (passw==null) || (data==null)) throw new NullPointerException();
 	             Se (utente_i!=Id) throw new UserUnavailableException();
 		         Se (passw!=password)  throw new WrongPasswordException();
 		         Se ((Id=="") ||(passw=="")) throw new IllegalArgumentException();
		         Se "data" non è presente nella collezione dei dati:throw new DateUnavailableException();
		   
	   MODIFIES: This.  
	 */
		if(Owner.isEmpty() || passw.isEmpty()) {// Controllo se i parametri sono vuoti
			throw new IllegalArgumentException();
		}
		this.NullControl(Owner, passw);// Controllo se i parametri sono "null"
		this.NullDato(data);// Controllo se "data" è "null"
		if(!utente.contains(Owner)){// Controllo se "Onwer" è presente
			throw new UserUnavailableException();
		}
		
		this.PasswComparison(Owner,passw);// Controllo se la "passw" è presente
		
		
	
		Vector<E> UtenteOwner = dato.get(utente.indexOf(Owner));// Creo un riferimento al vettore dei "data"
		int pos=UtenteOwner.indexOf(data);// Associo a pos l'indice del dato 
		if(pos==-1) {// Controllo se il "data" è presente
			throw new DateUnavailableException();
		}else {
			return UtenteOwner.remove(pos);// Rimuovo il "data" alla posizione specificata sopra
			}
	}
	
	public void copy(String Owner, String passw, E data) throws UserUnavailableException,DateUnavailableException,WrongPasswordException{
		/*
		REQUIRES: Owner && passw && data !=null && forall 0<=i<=n ((utente_i==Id) && (passw_i==password)).
		
		EFFECTS: Se i controlli sono rispettati creo una copia del dato.
		
		THROWS:  Se ((Id==null) || (passw==null) || (data==null)) throw new NullPointerException();
 		         Se (utente_i!=Id)  throw new UserUnavailableException();
 		         Se (passw!=password) throw new WrongPasswordException();
 		         Se ((Id=="") ||(passw=="")) throw new IllegalArgumentException();
		         Se "data" non è presente nella collezione dei dati:throw new DateUnavailableException();
		   
	 */
		if(Owner.isEmpty() || passw.isEmpty()) {// Controllo se i parametri sono vuoti
			throw new IllegalArgumentException();
		}
		this.NullControl(Owner, passw);// Controllo se i parametri sono "null"
		this.NullDato(data);// Controllo se "data" è "null"
		if(!utente.contains(Owner)){// Controllo se "Owner" è presente
			throw new UserUnavailableException();
		}
		
		this.PasswComparison(Owner,passw);// Controllo se la "passw" è presente
		
	
	
		Vector<E> UtenteOwner = dato.get(utente.indexOf(Owner));// Creo un riferimento al vettore dei "data"
		int pos=UtenteOwner.indexOf(data);// Assegno a pos l'indice dell'utente
		if(pos==-1) {// Controllo se il "data" è presente
			throw new DateUnavailableException();
		}else{
			UtenteOwner.add(UtenteOwner.get(pos)); //Creo una copia del dato all'interno del vettore dei dati dell'utente, per esattezza tramite una "Shallow Copy"
		}
	}
	
	public void share(String Owner, String passw, String Other, E data) throws UserUnavailableException,DateUnavailableException,WrongPasswordException,EqualUserExcpetion{
		/*
		   REQUIRES:Owner && passw && data && Other !=null && forall 0<=i<=n ((utente_i==Id) && (passw_i==password) && (utente_i==Other)).
		   
		   EFFECTS: Se i controlli d'identità sono rispettati, condivido il dato dell'Owner con l'Other.
		   
		   THROWS:  Se ((Id==null) || (passw==null) || (data==null) || (Other==null)) throw new NullPointerException();
	 		        Se (utente_i!=Id) throw new UserUnavailableException();
	 		        Se (passw!=password) throw new WrongPasswordException();
	 		        Se (utente_i!=Other) throw new UserUnavailableException();
	 		        Se ((Id=="") ||(passw=="") || (Other=="")) throw new IllegalArgumentException();
			        Se Owner.equals(Other) throw new EqualUserExcpetion();
		 */
		
		if(Other==null) { // Controllo se Other è "null"
			throw new NullPointerException();
		}
		
		if(Owner.isEmpty() || passw.isEmpty() || Other.isEmpty()) {// Controllo se i parametri sono vuoti
			throw new DateUnavailableException();
		}
		this.NullControl(Owner, passw);// Controllo se i parametri sono "null"
		this.NullDato(data);// Controllo se il "data" è "null"
		
		
		if(!utente.contains(Owner)) {// Controllo se "Owner" è presente
			throw new  UserUnavailableException();
		}
		this.PasswComparison(Owner,passw);// Controllo se la "passw" è presente
		if(!utente.contains(Other)) {// Controllo se "other"è presente
			throw new UserUnavailableException();
		}
		if(Owner.equals(Other)) {// Controllo se "Onwer" e "Other" sono uguali
			throw new EqualUserExcpetion();
		}
		
		Vector<E> UtenteOwner = dato.get(utente.indexOf(Owner));// Creo un riferiento all'array dei "data"
		int pos=UtenteOwner.indexOf(data);// Associo a pos l'indice del "data"
		if(pos==-1) {// Controllo se "data" esiste
			throw new DateUnavailableException();
		}else {
			Vector<E> UtenteOther= dato.get(utente.indexOf(Other));// Creo un'altro riferimento sta volta all'array dei "data" dell'Other
			UtenteOther.add(UtenteOwner.get(pos));// Condivido il "data" all'Other,  creando direttamente una copia nella sua lista dei "data"
										
			
		}
	}
	public Iterator<E> getIterator(String Owner, String passw)throws UserUnavailableException,WrongPasswordException{
		/*
		 REQUIRES:Owner && passw !=null && forall 0<=i<=n ((utente_i==Id) && (passw_i==password)
		 
		 EFFECTS: Se i controlli d'identità sono rispettati, restituisco un iteratore (senza remove) 
		 		  che genera tutti i dati dell’utente in ordine arbitrario se vengono rispettati i controlli di identità
		
		 THROWS: Se ((Id==null) || (passw==null)) sollevo l'eccezione NullPointerException. 
		 		 Se (utente_i!=Id) throw new UserUnavailableException()
		 		 Se (passw!=password) throw new WrongPasswordException();
		         Se ((Id=="") ||(passw=="")) throw new IllegalArgumentException();
	 */	
		
		if(Owner.isEmpty() || passw.isEmpty()) {// Controllo se i parametri sono vuoti
			throw new IllegalArgumentException();
		}
		this.NullControl(Owner, passw);// Controllo se i parametri sono "null"
		if(!utente.contains(Owner)) {// Controllo se "Owner" è presente
			throw new UserUnavailableException();
		}
		this.PasswComparison(Owner,passw);// Controllo se la "passw" è presente
		
		Iterator<E> pippo = new Generatore(dato.get(utente.indexOf(Owner)));
		return pippo;
		
		
	}
	// Questa classe mi implementa l'interfaccia Iterator.
	private class Generatore implements Iterator<E>{
		private Vector<E> dati;
		int i;
		
		public Generatore(Vector<E> dato) {
			dati=dato;
			i=0;
		}
		public boolean hasNext() {
			return i!=dati.size();
		}
		public E next(){
			return dati.get(i++);
		}		
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}
	
	// Questi sono metodi che uso all'interno delle funzioni, sono metodi di controllo sui parametri in entrata
	
	private void NullControl(String Id,String passw) {
		/*
	 	REQUIRES: Id && passw != null
	 	EFFECTS: // Controlla se "Id" && "passw" sono "null"
	 	THROWS:  if(Id==null || passw ==null) throw new NullPointerException();
	    */
		if(Id==null || passw==null) {
			throw new NullPointerException();
		}
	}
	
	private void IdContains(String Id) throws UserUnavailableException {
		/*
	 	REQUIRES: Id!= null
	 	EFFECTS: // Controlla se "Id" è presente 
	 	THROWS:  if(utente.contains(Id)) throw new UserUnavailableException();
	    */
		if(utente.contains(Id)) {
			throw new UserUnavailableException();
		}
	}
	private void NullDato(E data) {
		/*
	 	REQUIRES: data!=null.
	 	EFFECTS: // Controlla se il "data" è "null"
	 	THROWS:  if(data==null) throw new NullPointerException();
	 */
		if(data==null) {
			throw new NullPointerException();
		}
	}
	private void PasswComparison(String Id,String passw) throws WrongPasswordException{
		/*
	 	REQUIRES: Id && passw != null
	 	EFFECTS: // Controlla se passw è presente 
	 	THROWS:  if(!password.contains(passw) || pos!=pos2  ) throw new WrongPasswordException();
	    */
		int pos=utente.indexOf(Id);
		int pos2=password.indexOf(passw);
				
		if(!password.contains(passw) || pos!=pos2  ) {
			throw new WrongPasswordException();
		}
		
	}
	public void stampa() {// Metodo che stampa i dati
		System.out.println(utente);
		System.out.println(password);
		System.out.println(dato);
	}

	
	
	
}