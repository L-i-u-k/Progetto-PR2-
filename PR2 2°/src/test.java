import java.util.Iterator;

public class test {
	private SecureDataContainerClass<Integer> datacontainer;
	
	public test() {
		datacontainer = new SecureDataContainerClass<Integer>();
	}
	
	public void createUser(String Id,String passw) {
		try {
			datacontainer.createUser(Id, passw);
			System.out.println("Utente" + Id + " e stato aggiuto");
		}catch(NullPointerException e ) {
			System.out.println("VALORI NULL");
		}catch(IllegalArgumentException e) {
			System.out.println("STRINGHE VUOTE");
		}catch(UserUnavailableException e) {
			System.out.println("utente gia inserito");
		}
		datacontainer.stampa();
	}
	
	
	public void put(String Owner,String passw,Integer data) {
		try {
			datacontainer.put(Owner, passw, data);
				System.out.println("OGGETTO" + data +"aggiunto a "+ Owner);
		}catch(NullPointerException e) {
			System.out.println("VALORE NULL");
		}catch(IllegalArgumentException e) {
			System.out.println("STRINGHE VUOTE");
		}catch(UserUnavailableException e) {
			System.out.println("UTENTE NON TROVATO");
		}catch(WrongPasswordException e) {
			System.out.println("PASSWORD NON TROVATA");
		}
		datacontainer.stampa();
		}
	public void removeUser(String Id, String passw) {
		try {
			datacontainer.removeUser(Id, passw);
			System.out.println("UTENTE"+ Id + "e STATO RIMOSSO");
		}catch (NullPointerException e) {
			System.out.println("VALORI NULL");
		}catch(IllegalArgumentException e) {
			System.out.println("VALORI VUOTI");
		}catch(UserUnavailableException e) {
			System.out.println("UTENTE NON PRESENTE");
		}catch(WrongPasswordException e) {
			System.out.println("PASSWORD NON TROVATA");
		}
		datacontainer.stampa();
	}
	public void getSize(String Owner, String passw) {
		try {
			System.out.println(datacontainer.getSize(Owner, passw));
		}catch (NullPointerException e) {
			System.out.println("VALORI NULL");
		}catch(IllegalArgumentException e) {
			System.out.println("VALORI VUOTI");
		}catch(UserUnavailableException e) {
			System.out.println("UTENTE NON PRESENTE");
		}catch(WrongPasswordException e) {
			System.out.println("PASSWORD NON TROVATA");
		}
		datacontainer.stampa();
	}
	public void get(String Owner, String passw, Integer data) {
		try {
			System.out.println(datacontainer.get(Owner, passw,data));
		}catch (NullPointerException e) {
			System.out.println("VALORI NULL");
		}catch(IllegalArgumentException e) {
			System.out.println("VALORI VUOTI");
		}catch(UserUnavailableException e) {
			System.out.println("UTENTE NON PRESENTE");
		}catch(WrongPasswordException e) {
			System.out.println("PASSWORD NON TROVATA");
		}catch(DateUnavailableException e) {
			System.out.println("DATO NON TROVATO");
		}
		datacontainer.stampa();
	}
	public void copy(String Owner, String passw, Integer data) {
		try {
			System.out.println("OGGETTO" + data +"COPIATO IN "+ Owner);
			datacontainer.copy(Owner, passw,data);
		}catch (NullPointerException e) {
			System.out.println("VALORI NULL");
		}catch(IllegalArgumentException e) {
			System.out.println("VALORI VUOTI");
		}catch(UserUnavailableException e) {
			System.out.println("UTENTE NON PRESENTE");
		}catch(WrongPasswordException e) {
			System.out.println("PASSWORD NON TROVATA");
		}catch(DateUnavailableException e) {
			System.out.println("DATO NON TROVATO");
		}
		datacontainer.stampa();
	}
	public void remove(String Owner, String passw, Integer data) {
		try {
			datacontainer.remove(Owner, passw,data);
		}catch (NullPointerException e) {
			System.out.println("VALORI NULL");
		}catch(IllegalArgumentException e) {
			System.out.println("VALORI VUOTI");
		}catch(UserUnavailableException e) {
			System.out.println("UTENTE NON PRESENTE");
		}catch(WrongPasswordException e) {
			System.out.println("PASSWORD NON TROVATA");
		}catch(DateUnavailableException e) {
			System.out.println("DATO NON TROVATO");
		}
		datacontainer.stampa();
		
	}
	public void getIterator(String Owner, String passw) {
		try {
			Iterator<Integer> luga;
			luga=datacontainer.getIterator(Owner, passw);
			while(luga.hasNext()){
				System.out.println(luga.next());
			}
		}catch (NullPointerException e) {
			System.out.println("VALORI NULL");
		}catch(IllegalArgumentException e) {
			System.out.println("VALORI VUOTI");
		}catch(UserUnavailableException e) {
			System.out.println("UTENTE NON PRESENTE");
		}catch(WrongPasswordException e) {
			System.out.println("PASSWORD NON TROVATA");
		}
		datacontainer.stampa();	
	}
	public void share(String Owner, String passw, String Other, Integer data) {
		try {
			datacontainer.share(Owner, passw,Other,data);
		}catch (NullPointerException e) {
			System.out.println("VALORI NULL");
		}catch(IllegalArgumentException e) {
			System.out.println("VALORI VUOTI");
		}catch(UserUnavailableException e) {
			System.out.println("UTENTE NON PRESENTE");
		}catch(WrongPasswordException e) {
			System.out.println("PASSWORD NON TROVATA");
		}catch(DateUnavailableException e) {
			System.out.println("DATO non presente");
		}catch(EqualUserExcpetion e) {
			System.out.println("OWNER E OTHER UGUALI");
		}
		datacontainer.stampa();
		
	}
}
