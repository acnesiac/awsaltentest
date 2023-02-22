import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import com.aws.assignment.S3ToSQS;
import com.aws.assignment.S3Uploader;
import com.aws.assignment.SQSProcessor;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import static org.junit.Assert.*;

public class TestSQSProcessor {
    @Test
    public void test(){
        SQSProcessor sm = Mockito.spy(SQSProcessor.class);
        SQSEvent event = Mockito.mock(SQSEvent.class);
        Context ctx = Mockito.mock(Context.class);
        Void unused = sm.handleRequest(event, ctx);
        Assert.assertEquals(null, unused);
        String event = "{\n" +
                "  \"Records\": [\n" +
                "    {\n" +
                "      \"messageId\": \"19dd0b57-b21e-4ac1-bd88-01bbb068cb78\",\n" +
                "      \"receiptHandle\": \"MessageReceiptHandle\",\n" +
                "      \"body\": \"Hello from SQS!\",\n" +
                "      \"attributes\": {\n" +
                "        \"ApproximateReceiveCount\": \"1\",\n" +
                "        \"SentTimestamp\": \"1523232000000\",\n" +
                "        \"SenderId\": \"123456789012\",\n" +
                "        \"ApproximateFirstReceiveTimestamp\": \"1523232000001\"\n" +
                "      },\n" +
                "      \"messageAttributes\": {},\n" +
                "      \"md5OfBody\": \"{{{md5_of_body}}}\",\n" +
                "      \"eventSource\": \"aws:sqs\",\n" +
                "      \"eventSourceARN\": \"arn:aws:sqs:eu-central-1:774145483743:parse-document-topic.fifo\",\n" +
                "      \"awsRegion\": \"eu-central-1\"\n" +
                "    }\n" +
                "  ]\n" +
                "}";
    }
}