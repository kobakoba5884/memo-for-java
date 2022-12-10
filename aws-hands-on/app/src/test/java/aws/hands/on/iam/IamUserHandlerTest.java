package aws.hands.on.iam;

import static aws.hands.on.iam.IamUserHandler.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class) // https://stackoverflow.com/questions/54947645/junits-testmethodorder-annotation-not-working
public class IamUserHandlerTest extends IamTest{
    private String userName = "testUser";
    private String newUserName = "newTestUser";

    @Test
    @Order(1)
    void testCreateIamUser() {
        assertEquals(userName, createIamUser(iamClient, userName));
    }

    @Test
    @Order(2)
    void testUpdateIamUserName() {
        updateIamUserName(iamClient, userName, newUserName);
    }

    @Test
    @Order(3)
    void testDeleteIamUser() {
        deleteIamUser(iamClient, newUserName);
    }
}
