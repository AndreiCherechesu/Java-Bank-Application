package com.luxoft.bankapp.service;

import com.luxoft.bankapp.domain.Email;
import com.luxoft.bankapp.exceptions.BankException;
import com.luxoft.bankapp.utils.Queue;

public class EmailService implements Runnable {
    private Queue<Email> emailQueue = new Queue<>();
    private boolean isClosed = false;
    private Thread thread;
    private int sent = 0;

    public EmailService() {
        thread = new Thread(this);
        thread.start();
    }

    public int getSent() {
        return sent;
    }

    public void consumeEmail(Email email) {
        sent++;
        System.out.println("Sent [Email " + sent + "] successfully");
    }

    public void sendNotificationEmail(Email email) throws BankException {
        if (!isClosed) {
            emailQueue.add(email);
            synchronized (emailQueue) {
                emailQueue.notify();
            }
        } else {
            throw new BankException("ERROR: Cannot send email!");
        }
    }

    @Override
    public void run() {
        while (true) {
            if (isClosed) {
                return;
            }

            Email email = emailQueue.get();
            if (email != null) {
                consumeEmail(email);
            }
            synchronized (emailQueue) {
                try {
                    emailQueue.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void close() {
        isClosed = true;
        synchronized (emailQueue) {
            emailQueue.notify();
        }
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
