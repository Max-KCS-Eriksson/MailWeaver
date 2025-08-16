package com.maxeriksson.mailinator.ui.commandline;

import com.maxeriksson.mailinator.formatter.CompanyMailDetails;
import com.maxeriksson.mailinator.formatter.MailFormatter;
import com.maxeriksson.mailinator.formatter.ScanCompanyList;
import com.maxeriksson.mailinator.mail.MailService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
@Slf4j
public class MailinatorRunner implements CommandLineRunner {

    @Autowired private ScanCompanyList scanCompanyList;
    @Autowired private MailFormatter mailFormatter;
    @Autowired private MailService mailService;

    final Scanner SCANNER = new Scanner(System.in);

    @Override
    public void run(String... args) throws Exception {
        String subject = SCANNER.nextLine().trim();

        List<CompanyMailDetails> mailDetailsList = scanCompanyList.getCompanyMailDetailList();
        for (CompanyMailDetails mailDetails : mailDetailsList) {
            String recipient = mailDetails.getContactEmail();
            String mailText = mailFormatter.formatMailDraft(mailDetails);

            mailService.send(recipient, subject, mailText);
            log.info(
                    "\nSent email"
                            + "\n=========================================================="
                            + "\nTo: "
                            + recipient
                            + "\n----------------------------------------------------------"
                            + "\n"
                            + mailText);

            // Caution against throttling and raising spam email status
            if (mailDetails != mailDetailsList.getLast()) Thread.sleep(2_000);
        }
    }
}
