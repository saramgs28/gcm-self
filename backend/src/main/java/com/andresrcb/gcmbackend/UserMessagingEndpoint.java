package com.andresrcb.gcmbackend;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.appengine.api.urlfetch.HTTPHeader;
import com.google.appengine.api.urlfetch.HTTPMethod;
import com.google.appengine.api.urlfetch.HTTPRequest;
import com.google.appengine.api.urlfetch.HTTPResponse;
import com.google.appengine.api.urlfetch.URLFetchServiceFactory;

import org.json.simple.JSONObject;

import java.io.OutputStreamWriter;
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


//    public MessageRecord messageUser(MessageRecord message) throws IOException{
//        String textMessage = message.getTextMessage();
//        String toPhone = message.getToPhone();
//        String fromPhone = message.getFromPhone();
//        RegistrationRecord toUser = findRecord(toPhone);
//        Sender sender = new Sender(API_KEY);
//        Message msg = new Message.Builder().addData("message", textMessage).build();
//        Result r = sender.send(msg, toUser.getRegId(), 5);
//        log.info("Data sent with id: "+r.getMessageId());
//        return null;
//    }
    public void sendNotification(MessageRecord messageRecord){
        try{
            RegistrationRecord toUser = findRecord(messageRecord.getToPhone());
            RegistrationRecord fromUser = findRecord(messageRecord.getFromPhone());
            String fileUrl = messageRecord.getFileUrl();
            JSONObject requestPayload = new JSONObject();
            JSONObject dataObject = new JSONObject();
            JSONObject notificationObject = new JSONObject();
            requestPayload.put("to", toUser.getRegId());
            dataObject.put("fileUrl", fileUrl);
            dataObject.put("chatId", fromUser.id);
            notificationObject.put("body", "New GCM Message");
            notificationObject.put("title", "New MomentChat notification");
            requestPayload.put("notification", notificationObject);
            requestPayload.put("data", dataObject);
            URL url = new URL("https://gcm-http.googleapis.com/gcm/send");
            try{
                HTTPRequest request = new HTTPRequest(url, HTTPMethod.POST);
                request.addHeader(new HTTPHeader("Content-Type","application/json"));
                request.addHeader(new HTTPHeader("Authorization", "key=" + API_KEY));
                request.setPayload(requestPayload.toJSONString().getBytes("UTF-8"));
                HTTPResponse response = URLFetchServiceFactory.getURLFetchService().fetch(request);
            }catch(Exception e){
                e.printStackTrace();
            }
        }catch(Exception e){

        }
    }
    @ApiMethod(name = "message", httpMethod = ApiMethod.HttpMethod.POST)
    public MessageRecord messageNotification(MessageRecord message) throws IOException{
        String textMessage = message.getTextMessage();
        String toPhone = message.getToPhone();
        String fromPhone = message.getFromPhone();
        String fileUrl = message.getFileUrl();
        MessageRecord newMessage = new MessageRecord();
        newMessage.setFileUrl(fileUrl);
        newMessage.setFromPhone(fromPhone);
        newMessage.setToPhone(toPhone);
        newMessage.setTextMessage(textMessage);
        ofy().save().entity(newMessage).now();
        sendNotification(newMessage);
        return null;
    }
    private RegistrationRecord findRecord(String phone) {
        return ofy().load().type(RegistrationRecord.class).filter("phone", phone).first().now();
    }
}

