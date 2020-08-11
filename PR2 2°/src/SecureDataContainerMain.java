public class SecureDataContainerMain {

	public static void main(String[] args) {
		 test a = new  test();
		 
		
		 
		a.createUser("A","");
		a.createUser("","2");
		a.createUser("","");
		a.createUser("E",null);
		a.createUser(null,"6");
		a.createUser(null, null);
		a.createUser("A", "1");
		a.createUser("A", "1");
		a.createUser("B", "2");
		
		a.put("A","",null);
		a.put("","1",3);
		a.put("","",1);
		a.put(null,"",2);
		a.put("A",null,3);
		a.put("","",null);
		a.put("A","1",null);
		a.put("A","2",4);
		a.put("A","1",1);
		a.put("A","1",3);
		a.put("A","1",2);
		a.put("","",2);
		a.put("B","2",5); 
		
		a.removeUser("", "3");
		a.removeUser("A", "");
		a.removeUser("", "");
		a.removeUser(null, "3");
		a.removeUser("A", null);
		a.removeUser(null, null);
		a.removeUser("A", "1");
		a.removeUser("B", "2");
		
		
		a.getSize("", "1");
		a.getSize("S", "");
		a.getSize(null, "2");
		a.getSize("A", null);
		a.getSize(null, null);
		a.getSize("", "");
		a.getSize("A", "1");
		
		a.get("", "", null);
		a.get("A", "1", null);
		a.get("A", "1", 1);
		a.get("B", "2",2);
		
		a.copy("","",null);
		a.copy(null,"",4);
		a.copy("A","2",null);
		a.copy("B","2",null);
		a.copy("A","1",3);
		a.copy("B","2",5);
		
		a.remove("A", "1",3);
		a.remove("B", "2", 5);
		a.remove("","",null);
		a.remove(null,"4", null);
		a.remove("A","1", 8);
		a.remove("B","2",5);
		a.remove("A","2",3);
		
		a.share("A", "1", "B",2);
		a.share("A", "1", "A",2);
		a.share("","","",null);
		a.share("A","1","B",8);
		a.share("A","1","B",3);
		a.share("B","2","A",9);
		a.share("B","2","A",3);
		a.share("B","4","A",10);
		
		a.createUser("F", "5");
		a.put("F", "4", 6);
		a.put("F", "5", 5);
		a.copy("F", "7", 4);
		a.copy("F", "5", 4);
		a.copy("F", "5", 5);
		a.getSize("F", "6");
		a.getSize("F", "5");
		a.getIterator("F", "7");
		a.getIterator("F", "5");
	
		
	
		
		
			
	}
	

}