package pl.polishstation.polishstationbackend.domain.user.appuser;

import com.google.firebase.messaging.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.polishstation.polishstationbackend.domain.fuel.fuelprice.FuelPrice;

import java.util.List;

@Service
public class NotificationService {
    @Autowired
    FirebaseMessaging firebaseMessaging;

    public void notifyUsersAboutNewFuelPrice(List<String> tokens, FuelPrice fuelPrice) throws FirebaseMessagingException {
        if(!tokens.isEmpty()) {
            for (String token: tokens) {
                var message = Message.builder()
                        .setToken(token)
                        .setNotification(Notification.builder()
                                .setTitle("New Price added to " + fuelPrice.getPetrolStation().getName())
                                .setBody(fuelPrice.getPrice() + "ZŁ" + " " + fuelPrice.getFuelType().getName())
                                .build())
                        .build();
                try {
                    firebaseMessaging.send(message);
                } catch (FirebaseMessagingException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
