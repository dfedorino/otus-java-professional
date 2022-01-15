import com.google.common.collect.Lists;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


/**
 *
 * To start the application:
 * ./gradlew build
 * java -jar ./L01-gradle/build/libs/gradleHelloWorld-0.1.jar
 *
 * To unzip the jar:
 * unzip -l L01-gradle.jar
 * unzip -l gradleHelloWorld-0.1.jar
 *
 */
public class App {
    public static void main(String... args) {
        List<Integer> example = IntStream.range(1, 10).boxed().collect(Collectors.toList());
        System.out.println(Lists.reverse(example));
    }
}
