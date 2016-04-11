package com.andresrcb.gcmbackend;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiMethod.HttpMethod;
import com.google.api.server.spi.config.ApiNamespace;


import java.util.logging.Logger;

import javax.inject.Named;

import static com.andresrcb.gcmbackend.OfyService.ofy;

@Api(
        name = "register",
        version = "v1",
        namespace = @ApiNamespace(
                ownerDomain = "gcmbackend.andresrcb.com",
                ownerName = "gcmbackend.andresrcb.com",
                packagePath=""
        )

)


public class UserRegistrationEndpoint {
    private static final Logger log = Logger.getLogger(UserRegistrationEndpoint.class.getName());
    @ApiMethod(name = "register", httpMethod = HttpMethod.POST)
    public void registerDevice(@Named("regId") String regId, @Named("username") String username, @Named("phone") String phone) {
        if(findRecord(regId) != null) {
            log.info("Device " + regId + " already registered, skipping register");
            return;
        }
        RegistrationRecord record = new RegistrationRecord();
        record.setRegId(regId);
        record.setUsername(username);
        record.setPhone(phone);
        ofy().save().entity(record).now();
    }
    private RegistrationRecord findRecord(String regId) {
        return ofy().load().type(RegistrationRecord.class).filter("regId", regId).first().now();
    }
}
