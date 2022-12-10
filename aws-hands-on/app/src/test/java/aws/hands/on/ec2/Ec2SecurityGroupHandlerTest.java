package aws.hands.on.ec2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import aws.hands.on.AppTest;

import software.amazon.awssdk.services.ec2.model.SecurityGroup;

import static aws.hands.on.ec2.Ec2SecurityGroupHandler.*;
import static aws.hands.on.credential.CredentialsInfo.*;

public class Ec2SecurityGroupHandlerTest extends AppTest{
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
        String groupId = createEC2SecurityGroup(ec2Client, DEFAULT_SECURITY_GROUP_NAME, DEFAULT_SECURITY_GROUP_NAME);
        groupId = getEC2SecurityGroupByName(ec2Client, DEFAULT_SECURITY_GROUP_NAME).get().groupId();
        deleteEC2SecurityGroup(ec2Client, groupId);
    }
}
