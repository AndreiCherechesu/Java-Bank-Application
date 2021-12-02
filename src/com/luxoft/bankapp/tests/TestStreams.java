package com.luxoft.bankapp.tests;

import com.luxoft.bankapp.domain.*;
import com.luxoft.bankapp.exceptions.ClientExistsException;
import com.luxoft.bankapp.service.BankReportStreams;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;

import static org.junit.Assert.assertEquals;

public class TestStreams {
    private final Bank bank = new Bank();
    private BankReportStreams bankReport;
    private Client client1;
    private Client client2;
    private Client client3;

    @Before
    public void initialize() throws ClientExistsException {
        client1 = new Client("Andrei", Gender.MALE, "Bucharest");
        Account account1 = new SavingAccount(1, 100);
        Account account2 = new CheckingAccount(2, 100, 20);
        client1.addAccount(account1);
        client1.addAccount(account2);

        client2 = new Client("Ionut", Gender.MALE, "Targoviste");
        Account account3 = new SavingAccount(3, 330);
        Account account4 = new CheckingAccount(4, 200, 20);
        account4.deposit(1000);
        client2.addAccount(account3);
        client2.addAccount(account4);

        client3 = new Client("Mihaela", Gender.FEMALE, "Focsani");
        Account account5 = new SavingAccount(5, 370);
        Account account6 = new CheckingAccount(6, 600, 80);
        client3.addAccount(account5);
        client3.addAccount(account6);

        bank.addClient(client1);
        bank.addClient(client2);
        bank.addClient(client3);
        bankReport = new BankReportStreams();
    }

    @Test
    public void testNumberOfClients() {
        assertEquals(3, bankReport.getNumberOfClients(bank));
    }


    @Test
    public void testNumberOfAccounts() {
        assertEquals(6, bankReport.getNumberOfAccounts(bank));
    }

    @Test
    public void testClientsSorted() {
        SortedSet<Client> clients = bankReport.getClientsSorted(bank);

        assertEquals(3, clients.size());
        assertEquals(clients.first(), client1);
        assertEquals(clients.last(), client3);
    }

    @Test
    public void testBankCreditSum() {
        assertEquals(0, bankReport.getBankCreditSum(bank), 0);
    }

    @Test
    public void testCustomerAccounts() {
        Map<Client, Collection<Account>> customerAccounts = bankReport.getCustomerAccounts(bank);
        assertEquals(3, customerAccounts.size());
    }

    @Test
    public void testClientsByCity() {
        Map<String, List<Client>> clientsByCity = bankReport.getClientsByCity(bank);
        assertEquals(1, clientsByCity.get("Bucharest").size());
        assertEquals(1, clientsByCity.get("Focsani").size());
    }
}