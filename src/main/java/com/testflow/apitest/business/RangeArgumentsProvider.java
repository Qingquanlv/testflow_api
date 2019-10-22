package com.testflow.apitest.business;

import com.testflow.apitest.stepdefinations.FileExecutor;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.support.AnnotationConsumer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class RangeArgumentsProvider implements ArgumentsProvider, AnnotationConsumer<TestFlowTest> {
    private String path;

    RangeArgumentsProvider() {
    }

    @Override
    public void accept(TestFlowTest t)
    {
        path = t.path();
    }

    @Override
    public Stream provideArguments(ExtensionContext context) {
        FileExecutor fileExecutor= new FileExecutor();
        List<Arguments> list = new ArrayList<>();
        try {
            List<Map<String, String>> listMap = fileExecutor.loadFile(path);
            for (Map<String, String> map : listMap) {
                list.add(Arguments.of(map));
            }
        }
        catch (Exception ex)
        {

        }
        return list.stream();
    }
}
