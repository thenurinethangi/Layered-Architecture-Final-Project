package com.example.test.controller;

import com.example.test.bo.BOFactory;
import com.example.test.bo.custom.LeaseAgreementBO;
import com.example.test.dto.TenantDTO;
import com.example.test.entity.Tenant;
import com.example.test.view.tdm.LeaseAgreementTM;
import com.example.test.view.tdm.PaymentTM;
import com.example.test.dao.custom.impl.LeaseAgreementDAOImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

public class MailSendFormInLeaseAgreementController {

    @FXML
    private ImageView exit;

    @FXML
    private ImageView clearBtn;

    @FXML
    private TextField receiverEmailAddress;

    @FXML
    private TextField subjectTxt;

    @FXML
    private TextArea messageArea;

    @FXML
    private Button sendWithAttachmentBtn;

    @FXML
    private Button send;

    private final LeaseAgreementBO leaseAgreementBO = (LeaseAgreementBO) BOFactory.getInstance().getBO(BOFactory.BOType.LEASEAGREEMENT);
    private String email;
    private PaymentTM payment;



    @FXML
    void clear(MouseEvent event) {
        messageArea.clear();
    }


    @FXML
    void exitOnMouseClicked(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }


    @FXML
    void sendBtnOnAction(ActionEvent event) {

        String recipientEmail = email;
        String subject = subjectTxt.getText();
        String msg = messageArea.getText();

        if (recipientEmail.isEmpty() || subject.isEmpty() || msg.isEmpty()) {
            notification("Please fill in all the fields before sending.");
            return;
        }


        new Thread(() -> sendMail(recipientEmail, subject, msg)).start();
        notification("Email Sent To The Tenant.");
    }



    public void sendMail(String recipientEmail, String subject, String msg) {

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        String myEmail = "thenurinathangi@gmail.com";
        String password = "hkzu guyn ahxq hlmh";

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myEmail, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(myEmail));
            message.setRecipients(Message.RecipientType.TO, new Address[]{
                    new InternetAddress(recipientEmail)
            });
            message.setSubject(subject);
            message.setText(msg);

            Transport.send(message);

        }
        catch (MessagingException e) {
            e.printStackTrace();
            System.err.println("Error while sending email to tenant: "+ e.getMessage());

        }
    }

    @FXML
    void sendWithAttachmentOnAction(ActionEvent event) {

        if (payment != null) {
            String recipientEmail = email;
            String subject = subjectTxt.getText();
            String msg = messageArea.getText();
            File invoiceFile = new File("path/to/invoice.pdf");

            if (recipientEmail.isEmpty() || subject.isEmpty() || msg.isEmpty() || !invoiceFile.exists()) {
                notification("Please ensure all fields are filled and the attachment exists.");
                return;
            }


            new Thread(() -> sendMailWithAttachment(recipientEmail, subject, msg, invoiceFile)).start();
            notification("Email Sent To The Tenant.");

        }
        else {
            notification("No payment details available for sending an email with an attachment.");
        }
    }


    public void sendMailWithAttachment(String recipientEmail, String subject, String msg, File attachment) {

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        String myEmail = "thenurinathangi@gmail.com";
        String password = "hkzu guyn ahxq hlmh";

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myEmail, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(myEmail));
            message.setRecipients(Message.RecipientType.TO, new Address[]{
                    new InternetAddress(recipientEmail)
            });
            message.setSubject(subject);


            MimeBodyPart textBodyPart = new MimeBodyPart();
            textBodyPart.setText(msg);


            MimeBodyPart attachmentBodyPart = new MimeBodyPart();
            attachmentBodyPart.attachFile(attachment);


            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(textBodyPart);
            multipart.addBodyPart(attachmentBodyPart);

            message.setContent(multipart);

            Transport.send(message);


        } catch (MessagingException | IOException e) {
            e.printStackTrace();
            System.err.println("Error while sending email to the tenant "+ e.getMessage());
        }
    }


    public void setSelectedTenantDetailsToSendMail(LeaseAgreementTM selectedLeaseAgreement, String sub, String message) {

        subjectTxt.setText(sub);
        messageArea.setText(message);

        try {
            TenantDTO tenant = leaseAgreementBO.getTenantEmail(selectedLeaseAgreement.getTenantId());
            email = tenant.getEmail();
            receiverEmailAddress.setText(tenant.getEmail());
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            System.err.println("Error while setting tenant details to send mail: " + e.getMessage());
            notification("An error occurred while setting the tenant details to send mail. Please try again or contact support.");
        }
        sendWithAttachmentBtn.setDisable(true);
    }



    public void setPaymentDetailsToSendMail(PaymentTM selectedPayment) {

        payment = selectedPayment;

        subjectTxt.setText("Invoice for Recent Payment Attached");
        messageArea.setText("I am writing to confirm that your recent " + selectedPayment.getPaymentType() + " has been received. \nFor your records, the corresponding invoice is attached.\n\n\nThe Grand View Residences,\nColombo 08");

        try {
            TenantDTO tenant = leaseAgreementBO.getTenantEmail(selectedPayment.getTenantId());
            email = tenant.getEmail();
            receiverEmailAddress.setText(tenant.getEmail());
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            System.err.println("Error while setting payment details to send mail: " + e.getMessage());
            notification("An error occurred while setting payment details to send mail. Please try again or contact support.");
        }
        sendWithAttachmentBtn.setDisable(true);
    }


    public void notification(String message) {
        Notifications notifications = Notifications.create();
        notifications.title("Notification");
        notifications.text(message);
        notifications.hideCloseButton();
        notifications.hideAfter(Duration.seconds(4));
        notifications.position(Pos.CENTER);
        notifications.darkStyle();
        notifications.showInformation();
    }
}
