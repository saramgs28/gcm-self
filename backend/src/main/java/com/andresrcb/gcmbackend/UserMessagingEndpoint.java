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
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import javax.inject.Named;
import okhttp3.Authenticator;
import okhttp3.Credentials;

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
    @ApiMethod(name = "message", httpMethod = ApiMethod.HttpMethod.POST)
    public MessageRecord messageNotification(MessageRecord message) throws IOException{
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        String textMessage = message.getTextMessage();
        String toPhone = message.getToPhone();
        String fromPhone = message.getFromPhone();
        RegistrationRecord toUser = findRecord(toPhone);
        RegistrationRecord fromUser = findRecord(fromPhone);
        String dataBody = "{'notification':{'body':"+textMessage+", 'title': New message from "+fromUser.getUsername()+" }, 'to':"+toUser.getRegId()+"}";
        RequestBody reqBody = RequestBody.create(JSON, dataBody);
        Request req = new Request.Builder().url("https://gcm-http.googleapis.com/gcm/send").post(reqBody).addHeader("Authorization", "key="+API_KEY).build();
        Response response = client.newCall(req).execute();
        String res = response.body().string();
        return null;
    }
    private RegistrationRecord findRecord(String phone) {
        return ofy().load().type(RegistrationRecord.class).filter("phone", phone).first().now();
    }
}

