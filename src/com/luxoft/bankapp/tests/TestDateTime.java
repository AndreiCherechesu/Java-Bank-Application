package com.luxoft.bankapp.tests;

import com.luxoft.bankapp.domain.Bank;
import com.luxoft.bankapp.domain.Client;
import com.luxoft.bankapp.domain.Gender;
import com.luxoft.bankapp.exceptions.ClientExistsException;
import com.luxoft.bankapp.service.BankReportStreams;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class TestDateTime {
    private List<Client> clientList;
    private Bank bank;

    @Before
    public void setup() throws ClientExistsException {
        bank = new Bank();
        Client client1 = new Client(
                "Andrei C",
                Gender.MALE,
                "Toronto",
                LocalDate.of(1997, 3, 1));
        Client client2 = new Client(
                "Mihai C",
                Gender.MALE,
                "Focsani", LocalDate.of(1999, 4, 15));
        Client client3 = new Client(
                "Andreea T",
                Gender.FEMALE,
                "Dobroesti", LocalDate.of(1998, 10, 6));
        Client client4 = new Client(
                "Daniel M",
                Gender.MALE,
                "Golesti",
                LocalDate.of(1997, 8, 31));
        Client client5 = new Client(
                "Adrian C",
                Gender.MALE,
                "Focsani",
                LocalDate.of(1966, 7, 2));
        Client client6 = new Client(
                "Catalin C",
                Gender.MALE,
                "Braila",
                LocalDate.of(1967, 7, 21));
        Client client7 = new Client(
                "Codruta Z",
                Gender.FEMALE,
                "Bacau",
                LocalDate.of(1999, 12, 31));
        Client client8 = new Client(
                "Mikester McMike",
                Gender.MALE,
                "COVRIG 19",
                LocalDate.of(1997, 12, 10));
        Client client9 = new Client(
                "Hasbula K",
                Gender.MALE,
                "Oimyakon",
                LocalDate.of(2010, 11, 7));
        Client client10 = new Client(
                "Conor McG",
                Gender.FEMALE,
                "La Lupte",
                LocalDate.of(1975, 3, 4));

        bank.addClient(client1);
        bank.addClient(client2);
        bank.addClient(client3);
        bank.addClient(client4);
        bank.addClient(client5);
        bank.addClient(client6);
        bank.addClient(client7);
        bank.addClient(client8);
        bank.addClient(client9);
        bank.addClient(client10);
    }

    @Test
    public void testCheckNextMonthBirthdays() {
        LocalDate today = LocalDate.now();
        var clients = bank.getClients().stream().filter(client -> {
            LocalDate clientBirthday = client.getBirthday();
            LocalDate nextMonth = today.plusDays(30);
            LocalDate birthdayNowDate = LocalDate.of(
                    today.getYear(),
                    clientBirthday.getMonthValue(),
                    clientBirthday.getDayOfMonth()
            );
            return birthdayNowDate.isEqual(today)
                    || (birthdayNowDate.isAfter(today) && birthdayNowDate.isBefore(nextMonth))
                    || birthdayNowDate.isEqual(nextMonth);
        } ).collect(Collectors.toList());

        System.out.println("\n[TEST] Clients with birthdays within next 30 days:");
        System.out.println(clients + "\n");

//        Assert.assertTrue(clients.contains(bank.getClients().stream().toList().get(0)));
//        Assert.assertTrue(clients.contains(bank.getClients().stream().toList().get(3)));
//        Assert.assertTrue(clients.contains(bank.getClients().stream().toList().get(8)));
    }

    @Test
    public void testGetClientsByBirthdayMonth() {
        BankReportStreams bankReportStreams = new BankReportStreams();
        var clients = bankReportStreams.getClientsByBirthdayMonth(bank);
        System.out.println(clients);
        Assert.assertEquals("Mihai C", clients.get("APRIL").get(0).getName());
    }
}
