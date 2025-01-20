package com.example.aplikasipemesananmakanansa;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class PaymentSuccessActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_success);

        // Memanggil metode untuk mengirimkan email setelah pembayaran berhasil
        sendEmailProgrammatically();
    }

    private void sendEmailProgrammatically() {
        String host = "smtp.gmail.com"; // Ganti dengan SMTP Gmail
        String fromEmail = "bangalnibos000@gmail.com"; // Gantilah dengan email Anda
        String password = "123password"; // Gantilah dengan kata sandi aplikasi Anda
        String toEmail = "awaludinharbelubun98@gmail.com"; // Gantilah dengan email penerima

        // Menyiapkan properti untuk koneksi email
        Properties properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.socketFactory.port", "465");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        // Membuat sesi email
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        });

        try {
            // Membuat pesan email
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
            message.setSubject("Pembayaran Berhasil - Konfirmasi Pesanan");
            message.setText("Terima kasih atas pembayaran Anda! Pesanan Anda telah berhasil diproses.");

            // Mengirim email
            Transport.send(message);
            Toast.makeText(this, "Email Berhasil Dikirim!", Toast.LENGTH_SHORT).show();
        } catch (MessagingException e) {
            e.printStackTrace();
            Toast.makeText(this, "Pengiriman Email Gagal!", Toast.LENGTH_SHORT).show();
        }
    }
}
