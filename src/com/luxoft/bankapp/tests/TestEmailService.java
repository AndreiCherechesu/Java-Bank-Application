package com.luxoft.bankapp.tests;

import com.luxoft.bankapp.domain.Client;
import com.luxoft.bankapp.domain.Email;
import com.luxoft.bankapp.domain.Gender;
import com.luxoft.bankapp.exceptions.BankException;
import com.luxoft.bankapp.service.EmailService;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TestEmailService {
    private static final int NO_EMAILS = 20;
    private Client client;
    private Client to;

    @Before
    public void setup() {
        client = new Client("Andrew", Gender.MALE, "Manchester", LocalDate.of(1997, 3, 1));
        to = new Client("Brian", Gender.MALE, "Birmingham", LocalDate.of(1997, 3, 1));
    }

    @Test
    public void testSendMail() throws InterruptedException {

        EmailService emailService = new EmailService();
        for (int i = 0; i < NO_EMAILS; i++) {
            try {
                emailService.sendNotificationEmail(
                        new Email(client, List.of(to), "Bank Service", "Welcome!")
                );
            } catch (BankException e) {
                e.printStackTrace();
            }
            Thread.sleep(100);
        }

        assertEquals(NO_EMAILS, emailService.getSent());

    }
}