import org.junit.Test;

public class JUnitClassWithTests {
    @Test
    public void test_1() {
        System.out.println("hello from test_1");
        MyAssertions.verifyEquality(2, 3);
        System.out.println("hash -> " + this.hashCode());
    }

    @Test
    public void test_2() {
        System.out.println("hello from test_2");
        System.out.println("hash -> " + this.hashCode());
    }
}
