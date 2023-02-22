import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.s3.event.S3EventNotification;
import com.aws.assignment.S3ToSQS;
import org.junit.Test;
import org.mockito.Mockito;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;

public class TestS3ToSQS {
    @Test
    public void test() throws IOException, ParseException {
        S3ToSQS s3Lambda = Mockito.spy(S3ToSQS.class);
        Context context = Mockito.mock(Context.class);
        S3EventNotification.S3EventNotificationRecord s3EventNotificationRecord =  Mockito.mock(S3EventNotification.S3EventNotificationRecord.class);

        S3EventNotification notification = S3EventNotification.parseJson(loadJsonFromFile("s3-event.json"));
        S3Event event = new S3Event(notification.getRecords());

        Mockito.when(event.getRecords().get(0)).thenReturn(s3EventNotificationRecord);
        Mockito.when(s3Lambda.handleRequest(event, context)).thenReturn("");

        s3Lambda.handleRequest(event,context);
    }

    public String loadJsonFromFile(String file) throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(file));
        JSONObject jsonObject =  (JSONObject) obj;
        return jsonObject.toJSONString();
    }
}
