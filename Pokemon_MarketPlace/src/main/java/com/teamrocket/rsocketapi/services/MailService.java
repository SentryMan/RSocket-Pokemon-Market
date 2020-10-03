package com.teamrocket.rsocketapi.services;

import java.text.NumberFormat;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import com.teamrocket.rsocketapi.model.MailModel;
import com.teamrocket.rsocketapi.model.Sprite;
import reactor.core.publisher.Mono;

@Service
public class MailService {

  @Autowired private JavaMailSender sender;

  NumberFormat price = NumberFormat.getCurrencyInstance();

  public Mono<String> sendEmail(MailModel email) throws MessagingException {

    final MimeMessage message = sender.createMimeMessage();

    final MimeMessageHelper helper = new MimeMessageHelper(message, true);

    final String pokeRow =
        email
            .getPoke()
            .stream()
            .map(
                p -> {
                  final Sprite img = p.getSprite();
                  String pokeText = "<tr> <td>" + price.format(p.getPrice()) + "</td>";
                  pokeText = pokeText + "<td> <img src='" + img.getAnimated() + "'/> </td> ";
                  return pokeText + "<td>" + p.getName() + "</td></tr>";
                })
            .reduce("", (arg1, arg2) -> arg1 + arg2);

    final String itemRow =
        email
            .getItem()
            .stream()
            .map(
                i -> {
                  String itemText = "<tr> <td>" + price.format(i.getPrice()) + "</td>";
                  itemText = itemText + "<td> <img src='" + i.getImage() + "'/> </td>";
                  return itemText + "<td>" + i.getName() + "</td> </tr>";
                })
            .reduce("", (arg1, arg2) -> arg1 + arg2);

    helper.setTo(email.getEmailAddress());
    helper.setSubject("Thank you for your patronage!");
    helper.setText(
        "<html>"
            + "<div style=\"text-align: center\" class=\"container-fluid text-center\">"
            + "  <table class=\"table table-bordered\">"
            + "    <thead class=\"thead-dark\">"
            + "      <tr class=\"text-center\">"
            + "        <th scope=\"col\">Price</th>"
            + "        <th scope=\"col\">Image</th>"
            + "        <th scope=\"col\">Name</th>"
            + "      </tr>"
            + "    </thead>"
            + "    <tbody>"
            + pokeRow
            + itemRow
            + " </tbody> </table>"
            + "  </div> <p> Total: "
            + price.format(email.getTotal())
            + "</p> </html> Your order is on the way!",
        true);

    sender.send(message);

    return Mono.just("Email Sent");
  }
}
