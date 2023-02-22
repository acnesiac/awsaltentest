import com.aws.assignment.S3Uploader;
import org.junit.Test;
import org.mockito.Mockito;

public class TestS3Uploaader{
    @Test
    public void test(){
        S3Uploader sm = Mockito.mock(S3Uploader.class);
        Mockito.doNothing().when(sm).upload();
    }
}