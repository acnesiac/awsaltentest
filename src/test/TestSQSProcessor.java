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
    }
}