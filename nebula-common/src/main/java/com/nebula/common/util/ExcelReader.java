package com.nebula.common.util;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.ArrayList;
import java.util.List;

import static jdk.nashorn.internal.runtime.regexp.joni.Config.log;

public class ExcelReader<T> extends AnalysisEventListener<T> {
    List<T> list = new ArrayList<>();

    @Override
    public void invoke(T t, AnalysisContext analysisContext) {
        list.add(t);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
    }

    public List<T> getList() {
        return list;
    }
}
