import com.aws.assignment.S3Uploader;
import org.junit.Test;
import org.mockito.Mockito;

public class TestS3Uploader {
    @Test
    public void test(){
        S3Uploader sm = Mockito.mock(S3Uploader.class);
        Mockito.doNothing().when(sm).upload();
    }
}