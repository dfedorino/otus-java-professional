import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;

import java.util.List;

public class MyTestRunner extends BlockJUnit4ClassRunner {

    public MyTestRunner(Class<?> testClass) throws InitializationError {
        super(testClass);
    }

    @Override
    protected List<FrameworkMethod> getChildren() {
        return getTestClass().getAnnotatedMethods(MyTest.class);
    }
}
