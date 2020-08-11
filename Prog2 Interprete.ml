						  
(*Variabili*)
type ide = string;; (*identificatore*)

(*Espressioni che l'interprete drovr� valutare*)

type exp =
  | CostInt of int
  | CostBool of bool
  | CostString of string
  | Den of ide  (*Serve per usare una variabile*)
  | Let of ide * exp * exp
  | Sum of exp * exp 
  | Sub of exp * exp
  | Prod of exp * exp
  | Div of exp * exp
  | Negative of exp 
  | Equal of exp * exp
  | IsZero of exp
  | Not of exp
  | Maggiore of exp * exp 
  | Minore of exp * exp
  | And of exp * exp
  | Or of exp * exp
  | IfThenElse of exp * exp * exp
  | Fun of ide * exp
  | FunCall of exp * exp
  | LetRec of ide * ide * exp * exp (*nome funzione*)(*parametro*)(*corpo body*)(*corpo in*)
  |	Dictionary of item (*Item � un nome*)
  | Insert of exp * ide * exp
  | Remove of exp * ide
  | Search of exp * ide
  | Clear of exp
  | ApplyOver of exp * exp
 and item = Unbound | Key_Value of ide * exp * item;;
  
	
	(* Risultati che ottengo dalla valutazione delle exp *)
type evT = 
  | Int of int 
  | Bool of bool
  | String of string
  | FunVal of ide * exp * env
  | RecFunVal of ide * ide * exp* env
  | Val_Dict of (ide * evT) list
  | Unbound
and env = (ide * evT) list;;



(* Crea un nuovo collegamento in un ambiente nuovo *)
(*Faccio un bind tra l'ide e il value cioe associo all'ide un valore es:x=5 e lo metto nell'ambiente*)
let bind (ambiente:env) (x:ide) (value:evT): env = (x,value)::ambiente;; 

(* Cerca la variabile nell'ambiente  *)	
(*match ls with x::xs ls e ambiente xs � ambiente 2*)
let rec lookup (ambiente:env) (x:ide) : evT = match ambiente with 
				|(y,value)::ambiente2 -> if (x=y) then value else lookup ambiente2 x 
				|[] -> failwith "not found";;
					
(* Controlla il tipo *)	
let typecheck (s:string) (x:evT) : bool = match s with 
			| "int" -> (match x with 
						|Int(_) -> true 
						| _ -> false)
			| "bool" -> (match x with 
						|Bool(_) -> true	
						| _ -> false)
			| _ -> failwith "Errore di tipo";;
			
(* Interprete *)
let rec eval (e:exp) (ambiente:env) : evT = match e with
  | CostInt(e1) -> Int(e1)
  | CostBool(e1) -> Bool(e1)
  | CostString(s) -> String(s)
  | Den(nome) -> lookup ambiente nome
  | Let(nome, e1, e2) -> let v1 = eval e1 ambiente in
                            eval e2 (bind ambiente nome v1)
									
	|Sum(e1, e2) -> let v1 = eval e1 ambiente in
                    let v2 = eval e2 ambiente in
                    (match typecheck "int" v1, typecheck "int" v2, v1, v2 with
                      | true, true, Int(a), Int(b) -> Int(a + b)
                      | _, _, _, _ -> failwith "Errore di tipo")
	
	|Sub(e1,e2) -> let v1 = eval e1 ambiente in
						let v2 = eval e2 ambiente in 
						(match typecheck "int" v1, typecheck "int" v2,v1,v2 with
						| true, true, Int(a), Int(b) -> Int(a - b)
						| _, _, _, _ -> failwith "Errore di tipo")
						
	|Prod(e1, e2) -> let v1 = eval e1 ambiente in
                      let v2 = eval e2 ambiente in
                        (match typecheck "int" v1, typecheck "int" v2, v1, v2 with
                          | true, true, Int(a), Int(b) -> Int(a * b)
                          | _, _, _, _ -> failwith "Errore di tipo")
						  
						  
	|Div(e1, e2) -> let v1 = eval e1 ambiente in
                      let v2 = eval e2 ambiente in
                        (match typecheck "int" v1, typecheck "int" v2, v1, v2 with
                          | true, true, Int(a), Int(b) -> Int(a / b)
                          | _, _, _, _ -> failwith "Errore di tipo")
	
	
	|Equal(e1, e2) -> let v1 = eval e1 ambiente in 
                      let v2 = eval e2 ambiente in
                        (match typecheck "int" v1, typecheck "int" v2, v1, v2 with
                          | true, true, Int(x), Int(y) -> Bool(x=y)
                          | _, _, _, _ -> failwith "Errore di tipo")
		
	|Negative(e1) -> let v1 = eval e1 ambiente in
                  (match typecheck "int" v1, v1 with
                    | true, Int(x) -> Int(-x)
                    | _, _ -> failwith "Errore di tipo")
	
	|IsZero(e1)  -> let v1 = eval e1 ambiente in
                    (match typecheck "int" v1, v1 with
                      | true, Int(x) -> Bool(x=0)
                      | _, _ -> failwith "Errore di tipo")
													
	|Not(e1) -> let v = eval e1 ambiente in
                        (match typecheck "bool" v, v with
                          | true, Bool(x) -> Bool(not x)
                          | _, _ -> failwith "Errore di tipo")
						 
    |Maggiore(e1,e2)  -> let v1 = eval e1 ambiente in
                            let v2 = eval e2 ambiente in
                              (match typecheck "int" v1, typecheck "int" v2, v1, v2 with
                                | true, true, Int(x), Int(y) -> Bool(x > y)
                                | _, _, _, _ -> failwith "Errore di tipo")
					
					
	|Minore(e1,e2) -> let v1 = eval e1 ambiente in		
								let v2 = eval e2 ambiente in		
								(match typecheck "int" v1, typecheck "int" v2,v1,v1 with
								|true,true, Int(a),Int(b) -> Bool(a < b)
								| _, _, _, _ -> failwith "Errore di tipo")	
				
					
	|And(e1,e2) -> let v1 = eval e1 ambiente in 
					let v2 = eval e2 ambiente in 
						(match typecheck "bool" v1, typecheck "bool" v2,v1,v2 with
						|true,true, Bool(a),Bool(b) -> Bool(a && b)
						| _, _, _, _ -> failwith "Errore di tipo")
						
	|Or(e1,e2) -> let v1 = eval e1 ambiente in 
					let v2 = eval e2 ambiente in 
						(match typecheck "bool" v1, typecheck "bool" v2,v1,v2 with	
						| true,true, Bool(a),Bool(b) -> Bool(a || b)
						| _, _, _, _ -> failwith "Errore di tipo")

	|IfThenElse(cond, e1, e2) -> let condizione = eval cond ambiente in
                               (match typecheck "bool" condizione, condizione with
                                  | true, Bool(x) -> if x then eval e1 ambiente else eval e2 ambiente
                                  | _, _ -> failwith "Guardia non booleana")


	| Fun(x, e1) -> FunVal(x, e1,ambiente)
	| LetRec((*nome funzione*)f, (*parametro*)par, (*corpo body*)fbody, (*corpo in*)flet) -> let ambiente2 = bind ambiente f (RecFunVal(f, par, fbody,ambiente)) in
                                      eval flet ambiente2
									  
	|FunCall(e1, x) -> let fclosure = eval e1 ambiente in
                       (match fclosure with
                        | FunVal(y, e2,ambd) ->(* x = par attuale *) let v1 = eval x ambiente in
                                                    let ambiente2 = bind ambd y v1 in
                                                       eval e2 ambiente2
												| RecFunVal(f, par, fbody, famb) -> let v = eval x ambiente in
																				(* Creo binding tra nome funzione e chiusura *)
																				let ambric = bind famb f fclosure in
																				(* Creo binding tra par formale e attuale *)
                                                                let ambiente2 = bind ambric par v in 
                                                                  eval fbody ambiente2
												| _ -> failwith("Non e' una funzione"))
								
	(*Qua creo il dizionario, gli passo un Item e tramite la funzione evalDict valuto i parametri che gli passo, in questo caso el che � un Item e l'ambiente*)
	|Dictionary(el) -> let a = evalDict el ambiente in 
							if(check a) then Val_Dict(a) else failwith "Chiavi ripetute"
							
	(*Cerco al'interno del dizionario un elemento specifico tramite l'utilizzo della funzione "Searching"*)						
	| Search(dict, indice) -> let a = eval dict ambiente in 
                      (match a with
					  (*Passo alla funzione l'indice dell'elemento da cercare*)
                        | Val_Dict(l) -> searching l indice
                        | _ -> failwith "Non � un dizionario")						
	
	(*Inserisco una nuova coppia di valori nel dizionario*)
	| Insert(dict, indice, elemento) -> let a = eval dict ambiente in
                              (match a with
							  (*Do come risultato un dizionario che conterra un nuovo elemento al suo interno aggiunto tramite la funzione add*)
                                | Val_Dict(l) -> Val_Dict(add l indice (eval elemento ambiente))
                                | _ -> failwith "Non � un dizionario")
								
	(*Rimuovo un elemento dal dizionario*)							
	| Remove(dict, indice) -> let a = eval dict ambiente in
                          (match a with
						    (*Do come risultato un dizionario che non conterr� l'elemento che ho appena eliminato tramite la funzione delete*)
                            | Val_Dict(l) -> Val_Dict(delete l indice)
                            | _ -> failwith "Non � un dizionario")
							
	(*Pulisco il dizionario restituendo un dizionario vuoto*)					
	| Clear(dict) -> Val_Dict([])
	
	
	(*Applica la funzione f a ogni elemento del dizionario*)
	| ApplyOver(f, dict) -> let d = eval dict ambiente in 
		(match d with
			| Val_Dict(l) -> Val_Dict(apply l f ambiente)
			| _ -> failwith "Non è un dizionario")                       
	and 
	(*Questa funzione non fa altro che valutare ci� che gli passo,cio� il mio Item e l'ambiente*)
		evalDict e ambiente = 
			match e with 
			(*Qua faccio il match di "e" se "e" � key_value valuto l'exp e mi ricorro sull'elemento successivo *)
			|Unbound -> []
			|Key_Value(i,e1,elsucc) -> let v1 = eval e1 ambiente in 
									(i,v1)::evalDict elsucc ambiente

			and
	
	(*Funzione per eliminare un'elemento dal dizionario*)
		delete a indice = 
			match a with
			(*Se la chiave � uguale all'indice, per elimiarlo restituisco il seguito della lista senza la coppia che aveva tale indice*)
			|  [] -> []
			| (key, value)::xs -> if (key = indice) then xs else (key, value)::delete xs indice

	and
	
	(*Funzione per inserire un elemento nel dizionario *)
		add a indice valore = 
			match a with
			| [] -> [(indice, valore)]
			(* Se si inserisce un elemento gi� ripetuto sovrascrivo tale valore nel dizionario altrimento ricorro sul resto della lista  *)
			| (key, value)::xs -> if (key=indice) then (key, valore)::xs else (key, value)::add xs indice valore 
	and
		apply dict f ambiente = match dict with
			|[] -> []
			|(k, v)::xs -> let fClouser = eval f ambiente in 
											(match fClouser with (* Creato nuovo ambiente a partire da quello vecchio *)
												| FunVal(i, body, enve) -> let amb2 = bind enve i v in 
																				let v1 = eval body amb2 in 
																					(k, v1)::(apply xs f ambiente)
												| RecFunVal(nome, parf, body, famb) -> let amb2 = bind famb parf v in
																								(*bindo il nome della funzione alla chiusura*)
																							let amb3 = bind amb2 nome fClouser in
																								let v1 = eval body amb3 in 
																									(k, v1)::(apply xs f ambiente)
												| _ -> failwith "Non una funzione")
	and
	
	(*Fuzione usata per cercare elementi nel dizionario*)
		searching a indice =
			match a with
			| [] -> Unbound
			(*Se la chiave � uguale all'indice, restituisco il valore attribuito a quell'indice, altrimento continuo la ricerca sul resto della lista*)
			| (key, value)::xs -> if (key=indice) then value else searching xs indice

				
	and
		(*La funzione check serve per controllare se quando creo il dizionario con elementi, mi passa chiavi ripetute*)
		check a = let rec isin i dict =
			(*valuto dict, cio� il resto della lista e controllo se ci soo chaivi con indice ripetuto*)
			match dict with	
				|[] -> false
				|(key,value)::xs -> if(key = i) then true else isin i xs in 
				(*Match di a guardo se cotiene elementi, se si parte l'if che passa a isin key e il resto della lista *)
				match a with 
				|[] -> true
				|(key,value)::xs -> if(isin key xs) then false else check xs;;
	
let emptyenv = [];;

(*Il dizionario vuoto*)
eval (Dictionary(Unbound)) emptyenv;;

(*Test con dizionario contenente degli elementi*)
let dict = Dictionary(Key_Value("Nome1",CostString("Luca"),
					   Key_Value("Cognome1" ,CostString("Cataldo"),
					   Key_Value("Matricola1",CostInt(546154),
					   Key_Value("Eta1",CostInt(21),
					   Key_Value("Nome2",CostString("Cecco"),
					   Key_Value("Cognome2",CostString("Beppe"),
					   Key_Value("Matricola2",CostInt(1111),
					   Key_Value("Eta2",CostInt(189),
					   Unbound)))))))));;
eval dict emptyenv;;

(*Test per accedere agli elementi che non sono presenti nel dizionario*)
eval (Search (dict,"Nome"))emptyenv;;
eval (Search(dict,"Cognome"))emptyenv;;
eval (Search(dict,"Eta"))emptyenv;;

(*Test per accedere a elementi presenti nel dizionario*)
eval (Search(dict,"Nome1"))emptyenv;;
eval (Search(dict,"Eta1"))emptyenv;;
eval (Search(dict,"Nome2"))emptyenv;;

(*Test con dizionario contente elementi aventi chiavi ripetute*)
let dict = Dictionary(Key_Value("Nome1",CostString("Luca"),
					   Key_Value("Cognome1" ,CostString("Cataldo"),
					   Key_Value("Matricola1",CostInt(546154),
					   Key_Value("Eta1",CostInt(21),
					   Key_Value("Nome1",CostString("Cecco"),
					   Key_Value("Cognome2",CostString("Beppe"),
					   Key_Value("Matricola2",CostInt(1111),
					   Key_Value("Eta1",CostInt(189),
					   Unbound)))))))));;
eval dict emptyenv;;

(*Test di inserimento degli elementi nel dizionario*)
eval (Insert(dict,"Nome3",CostString("Liuk")))emptyenv;;
eval (Insert(dict,"Eta3",CostInt(21)))emptyenv;;

(*Test di rimozione degli elmenti dal dizionario*)
eval (Remove(dict,"Nome1"))emptyenv;;
eval (Remove(dict,"Eta3"))emptyenv;;

(*Test di pulizia del dizionario*)
eval (Clear(dict))emptyenv;;

let dict = Dictionary(Key_Value("Matricola", CostInt(1000), 
                Key_Value("Anno Iscrizione", CostInt(2016),
                Unbound)));;
eval dict emptyenv;;

(* ApplyOver *)
eval (ApplyOver(Fun("x", Sum(Den("x"), CostInt(1))), dict)) emptyenv;;
eval (Let("y", CostInt(2),
      Let("somma", Fun("x", Sum(Den("x"), Den("y"))),
      Let("y", CostInt(5), ApplyOver(Den("somma"), dict))))) emptyenv;;





