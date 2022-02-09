import org.junit.runner.RunWith;

@RunWith(MyTestRunner.class)
public class ClassWithTests {
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
}
