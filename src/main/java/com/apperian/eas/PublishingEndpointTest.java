package com.apperian.eas;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class PublishingEndpointTest {

    private PublishingEndpoint api;

    @Before
    public void setUp() {
        api = new PublishingEndpoint("https://easesvc.apperian.com/ease.interface.php");
    }

    @Test
    @Ignore // for manual running only
    public void testError() throws Exception {
        AuthenticateUserResponse response = new AuthenticateUserRequest("test-easy-jenkins-user", "testpassword")
                .call(api);
        System.out.println(response);
    }
}
