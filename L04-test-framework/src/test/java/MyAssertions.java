public class MyAssertions {
    public static void verifyEquality(int actual, int expected) {
        if (actual != expected) {
            throw new AssertionError("Expected " + actual + " to be equal to " + expected + " but wasn't");
        }
    }
}
