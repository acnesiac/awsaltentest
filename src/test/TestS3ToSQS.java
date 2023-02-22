import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.s3.event.S3EventNotification;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.aws.assignment.S3ToSQS;
import org.junit.Test;
import org.mockito.Mockito;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import static org.junit.Assert.*;

import java.io.FileReader;
import java.io.IOException;

public class TestS3ToSQS {
    @Test
    public void test() throws IOException, ParseException {
        S3ToSQS s3Lambda = Mockito.spy(S3ToSQS.class);
        Context context = Mockito.mock(Context.class);
        S3EventNotification notification = S3EventNotification.parseJson("{\n" +
                "  \"Records\": [\n" +
                "    {\n" +
                "      \"eventVersion\": \"2.0\",\n" +
                "      \"eventSource\": \"aws:s3\",\n" +
                "      \"awsRegion\": \"eu-central-1\",\n" +
                "      \"eventTime\": \"1970-01-01T00:00:00.000Z\",\n" +
                "      \"eventName\": \"ObjectCreated:Put\",\n" +
                "      \"userIdentity\": {\n" +
                "        \"principalId\": \"EXAMPLE\"\n" +
                "      },\n" +
                "      \"requestParameters\": {\n" +
                "        \"sourceIPAddress\": \"127.0.0.1\"\n" +
                "      },\n" +
                "      \"responseElements\": {\n" +
                "        \"x-amz-request-id\": \"EXAMPLE123456789\",\n" +
                "        \"x-amz-id-2\": \"EXAMPLE123/5678abcdefghijklambdaisawesome/mnopqrstuvwxyzABCDEFGH\"\n" +
                "      },\n" +
                "      \"s3\": {\n" +
                "        \"s3SchemaVersion\": \"1.0\",\n" +
                "        \"configurationId\": \"testConfigRule\",\n" +
                "        \"bucket\": {\n" +
                "          \"name\": \"aws-s3-bucket-35\",\n" +
                "          \"ownerIdentity\": {\n" +
                "            \"principalId\": \"EXAMPLE\"\n" +
                "          },\n" +
                "          \"arn\": \"arn:aws:s3:::aws-s3-bucket-35\"\n" +
                "        },\n" +
                "        \"object\": {\n" +
                "          \"key\": \"vehicles.txt\",\n" +
                "          \"size\": 1024,\n" +
                "          \"eTag\": \"0123456789abcdef0123456789abcdef\",\n" +
                "          \"sequencer\": \"0A1B2C3D4E5F678901\"\n" +
                "        }\n" +
                "      }\n" +
                "    }\n" +
                "  ]\n" +
                "}");
        S3Event event = new S3Event(notification.getRecords());
        assertEquals(s3Lambda.handleRequest(event, context), "Error");
    }

}
