package com.nebula.service.strategy.helper;

import com.nebula.common.annotation.Strategy;
import com.nebula.service.strategy.CodeGenStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class CodeGenStrategyHelper {
    private static Map<String, CodeGenStrategy> codeGenStrategyMap;

    @Autowired
    public void setCodeGenStrategyMap(List<CodeGenStrategy> sasInvtBondedStrategyList) {
        codeGenStrategyMap = sasInvtBondedStrategyList.stream().collect(
                Collectors.toMap(
                        sasInvtBondedStrategy -> Objects.requireNonNull(AnnotationUtils.findAnnotation(sasInvtBondedStrategy.getClass(), Strategy.class)).value(),
                        sasInvtBondedStrategy -> sasInvtBondedStrategy,
                        (existing, replacement) -> existing
                )
        );
    }

    public static CodeGenStrategy getStrategy(String strategyType){
        return codeGenStrategyMap.get(strategyType);
    }
}
