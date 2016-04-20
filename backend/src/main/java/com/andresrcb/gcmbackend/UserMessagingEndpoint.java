package com.andresrcb.gcmbackend;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;

import org.json.simple.JSONObject;

import java.net.HttpURLConnection;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Logger;

import javax.inject.Named;

import static com.andresrcb.gcmbackend.OfyService.ofy;

@Api(
        name = "usermessaging",
        version = "v1",
        namespace = @ApiNamespace(
                ownerDomain = "gcmbackend.andresrcb.com",
                ownerName = "gcmbackend.andresrcb.com",
                packagePath=""
        )
)
public class UserMessagingEndpoint {
    private static final Logger log = Logger.getLogger(MessagingEndpoint.class.getName());

    /** Api Keys can be obtained from the google cloud console */
    private static final String API_KEY = System.getProperty("gcm.api.key");

    /**
     * Send to the first 10 devices (You can modify this to send to any number of devices or a specific device)
     *
     * @param message The message to send
     */
    @ApiMethod(name = "message", httpMethod = ApiMethod.HttpMethod.POST)
    public MessageRecord messageUser(MessageRecord message) throws IOException{
        String textMessage = message.getTextMessage();
        String toPhone = message.getToPhone();
        String fromPhone = message.getFromPhone();
        RegistrationRecord toUser = findRecord(toPhone);
        Sender sender = new Sender(API_KEY);
        Message msg = new Message.Builder().addData("message", textMessage).build();
        Result r = sender.send(msg, toUser.getRegId(), 5);
        log.info("Data sent with id: "+r.getMessageId());
        return null;
    }
    public MessageRecord messageNotification(MessageRecord message) throws IOException{
        String textMessage = message.getTextMessage();
        String toPhone = message.getToPhone();
        String fromPhone = message.getFromPhone();
        RegistrationRecord toUser = findRecord(toPhone);
        URL url = new URL("https://gcm-http.googleapis.com/gcm/send");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Authorization", "key="+API_KEY);
        conn.setDoOutput(true);
        
        return null;
    }
//    public void sendMessage(@Named("message") String message, @Named("phone") String phone) throws IOException {
//        if(message == null || message.trim().length() == 0) {
//            log.warning("Not sending message because it is empty");
//            return;
//        }
//        // crop longer messages
//        if (message.length() > 1000) {
//            message = message.substring(0, 1000) + "[...]";
//        }
//        Sender sender = new Sender(API_KEY);
//        Message msg = new Message.Builder().addData("message", message).build();
//        //List<RegistrationRecord> records = ofy().load().type(RegistrationRecord.class).limit(10).list();
////        String receiverId = "5512148288";
//
//
//        RegistrationRecord result = findRecord(phone);
//        Result r = sender.send(msg, result.getRegId(), 5);
//        log.info("Data sent with id: "+r.getMessageId());
//        /*for(RegistrationRecord record : records) {
//            Result result = sender.send(msg, record.getRegId(), 5);
//            if (result.getMessageId() != null) {
//                log.info("Message sent to " + record.getRegId());
//                String canonicalRegId = result.getCanonicalRegistrationId();
//                if (canonicalRegId != null) {
//                    // if the regId changed, we have to update the datastore
//                    log.info("Registration Id changed for " + record.getRegId() + " updating to " + canonicalRegId);
//                    record.setRegId(canonicalRegId);
//                    ofy().save().entity(record).now();
//                }
//            } else {
//                String error = result.getErrorCodeName();
//                if (error.equals(Constants.ERROR_NOT_REGISTERED)) {
//                    log.warning("Registration Id " + record.getRegId() + " no longer registered with GCM, removing from datastore");
//                    // if the device is no longer registered with Gcm, remove it from the datastore
//                    ofy().delete().entity(record).now();
//                }
//                else {
//                    log.warning("Error when sending message : " + error);
//                }
//            }
//        }*/
//
//    }
    private RegistrationRecord findRecord(String phone) {
        return ofy().load().type(RegistrationRecord.class).filter("phone", phone).first().now();
    }
}

