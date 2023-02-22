import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.aws.assignment.S3ToSQS;
import org.junit.Test;
import org.mockito.Mockito;

public class TestS3ToSQS {
    @Test
    public void test() {
        S3ToSQS s3Lambda = Mockito.spy(S3ToSQS.class);
        S3Event event = Mockito.mock(S3Event.class);
        Context context = Mockito.mock(Context.class);
        Mockito.when(s3Lambda.handleRequest(event, context)).thenReturn("");
        s3Lambda.handleRequest(event,context);
    }
}
