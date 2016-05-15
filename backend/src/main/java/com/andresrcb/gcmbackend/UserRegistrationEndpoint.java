package com.andresrcb.gcmbackend;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiMethod.HttpMethod;
import com.google.api.server.spi.config.ApiNamespace;

import java.util.logging.Logger;

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

//Registration and check if the token or the phone already exist
public class UserRegistrationEndpoint {
    private static final Logger log = Logger.getLogger(UserRegistrationEndpoint.class.getName());
    @ApiMethod(name = "register", httpMethod = HttpMethod.POST)
    public RegistrationRecord registerDevice(RegistrationRecord record){
        if(findRecord(record.getRegId()) != null) {
            log.info("Device " + record.getRegId() + " already registered, skipping register");
            return null;
        }
        if(findRecordByPhone(record.getPhone()) !=  null){
            log.info("Device with phone: " + record.getPhone() + " already registered, skipping register");
            return null;
        }
        RegistrationRecord r = new RegistrationRecord();
        r.setRegId(record.getRegId());
        r.setUsername(record.getUsername());
        r.setPhone(record.getPhone());
        ofy().save().entity(r).now();
        return r;
    }
    private RegistrationRecord findRecord(String regId) {
        return ofy().load().type(RegistrationRecord.class).filter("regId", regId).first().now();
    }
    private RegistrationRecord findRecordByPhone(String phone){
        return ofy().load().type(RegistrationRecord.class).filter("phone", phone).first().now();
    }
}
