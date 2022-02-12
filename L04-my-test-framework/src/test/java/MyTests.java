import com.dfedorino.my_testing_framework.*;
import com.dfedorino.my_testing_framework.annotations.MyAfterEach;
import com.dfedorino.my_testing_framework.annotations.MyBeforeEach;
import com.dfedorino.my_testing_framework.annotations.MyTest;
import org.junit.runner.RunWith;

@RunWith(MyTestRunner.class)
public class MyTests {
    @MyBeforeEach
    public void setUp() {
        System.out.println(">> Setting up...");
    }

    @MyAfterEach
    public void tearDown() {
        System.out.println(">> Tearing down...");
    }

    @MyTest
    public void successful_test() {
        MyAssertions.verifyEquality(2, 2);
        System.out.println("hash -> " + this.hashCode());
    }

    @MyTest
    public void failing_test() {
        MyAssertions.verifyEquality(2, 3);
        System.out.println("hash -> " + this.hashCode());
    }

    @MyTest(expected = RuntimeException.class)
    public void test_with_expected_exception() {
        throw new RuntimeException("Expected exception");
    }

    @MyTest(expected = RuntimeException.class)
    public void test_with_unexpected_exception() throws Exception {
        throw new Exception("Unexpected exception");
    }
}
