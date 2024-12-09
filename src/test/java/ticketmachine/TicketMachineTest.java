package ticketmachine;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

class TicketMachineTest {
	private static final int PRICE = 50; // Une constante

	private TicketMachine machine; // l'objet à tester

	@BeforeEach
	public void setUp() {
		machine = new TicketMachine(PRICE); // On initialise l'objet à tester
	}

	@Test
	// On vérifie que le prix affiché correspond au paramètre passé lors de
	// l'initialisation
	// S1 : le prix affiché correspond à l’initialisation.
	void priceIsCorrectlyInitialized() {
		// Paramètres : valeur attendue, valeur effective, message si erreur
		assertEquals(PRICE, machine.getPrice(), "Initialisation incorrecte du prix");
	}

	@Test
	// S2 : la balance change quand on insère de l’argent
	void insertMoneyChangesBalance() {
		// GIVEN : une machine vierge (initialisée dans @BeforeEach)
		// WHEN On insère de l'argent
		machine.insertMoney(10);
		machine.insertMoney(20);
		// THEN La balance est mise à jour, les montants sont correctement additionnés
		assertEquals(10 + 20, machine.getBalance(), "La balance n'est pas correctement mise à jour");
	}
	
	@Test
	// S3 : on n'imprime pas le ticket si le montant inséré est insuffisant
	void dontPrintTicketIfBalanceIsntEnough() {
		// GIVEN : une machine vierge (initialisée dans @BeforeEach)
		// WHEN On insère de l'argent
		machine.insertMoney(PRICE - 20);
		assertFalse(machine.printTicket(), "Le ticket a tout de même été imprimé");
	}
	
	@Test
	// S4 : on imprime le ticket si le montant inséré est suffisant
	void printTicketIfBalanceIsEnough() {
		machine.insertMoney(PRICE + 20);
		assertTrue(machine.printTicket(), "Le ticket n'a pas été imprimé");
	}

	@Test
	// S5 : quand on imprime un ticket la balance est décrémentée du prix du ticket
	void whenTicketIsPrintedBalanceIsDecremented() {
		int balance = PRICE + 20;
		machine.insertMoney(balance);
		machine.printTicket();
		assertTrue(machine.getBalance() == balance - machine.getPrice());
	}
	
	@Test
	// S6 : le montant collecté est mis à jour quand on imprime un ticket (pas avant)
	void balanceIsUpdatedWhenATicketIsPrinted() {
		int balance = PRICE + 20;
		machine.insertMoney(balance);
		assertEquals(0, machine.getTotal());
		machine.printTicket();
		assertEquals(machine.getTotal(), machine.getPrice());
	}
	
	@Test
	// S7 : refund() rend correctement la monnaie
	void refundCorrectlyRefund() {
		int balance = PRICE + 20;
		machine.insertMoney(balance);
		assertEquals(machine.refund(), balance);
	}
	
	@Test
	// S8 : refund() remet la balance à zéro
	void refundSetsBalanceToZero() {
		int balance = PRICE + 20;
		machine.insertMoney(balance);
		machine.refund();
		assertEquals(machine.getBalance(), 0);
	}
	
	@Test
	// S9 : on ne peut pas insérer un montant négatif 
	void cannotInsertNegativeAmount() {
		int balance = -10;
		try {
			machine.insertMoney(balance);
			fail();
		}catch(IllegalArgumentException e) {
			
		}
	}
	
	@Test
	// S10 : on ne peut pas créer de machine qui délivre des tickets dont le prix est négatif
	void cannotCreateMacihneDeliveringNegativePriceTickets() {
		try {
			TicketMachine machine2 = new TicketMachine(-40);
			fail();
		}catch(IllegalArgumentException e) {
			
		}
	}
}
