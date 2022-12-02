package aws.hands.on.ec2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.services.ec2.Ec2Client;
import software.amazon.awssdk.services.ec2.model.SecurityGroup;

import static aws.hands.on.credential.CredentioalsInfo.MY_REGION;
import static aws.hands.on.ec2.Ec2SecurityGroupHandler.*;

public class Ec2SecurityGroupHandlerTest {
    private Ec2Client ec2Client;

    @BeforeEach
    void init(){
        this.ec2Client = Ec2Client.builder()
            .region(MY_REGION)
            .credentialsProvider(ProfileCredentialsProvider.create())
            .build();
    }

    @AfterEach
    void teardown(){
        ec2Client.close();
    }
    @Test
    void testCreateDefaultBoundRules() {

    }

    @Test
    void testCreateEC2SecurityGroup() {
        String groupName = "groupName";
        String groupDesc = "groupDesc";

        String securityGroupId = createEC2SecurityGroup(ec2Client, groupName, groupDesc);
        
        assertEquals("", createEC2SecurityGroup(ec2Client, groupName, groupDesc));

        @SuppressWarnings("unused")
        Optional<List<SecurityGroup>> securityGroups = getEC2SecurityGroups(ec2Client);

        Optional<SecurityGroup> securityGroup = getEC2SecurityGroupById(ec2Client, securityGroupId);

        securityGroup.ifPresent(s -> {
            assertEquals(securityGroupId, s.groupId());
        });

        deleteEC2SecurityGroup(ec2Client, securityGroupId);

        assertTrue(getEC2SecurityGroupById(ec2Client, securityGroupId).isEmpty());
    }

    @Test
    void testDeleteEC2SecurityGroup() {

    }
}
