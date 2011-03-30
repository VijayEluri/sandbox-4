// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.

package org.sgo.rootest.client.managed.request;

import com.google.gwt.requestfactory.shared.EntityProxy;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public abstract class ApplicationEntityTypesProcessor<T> {

    private final T defaultValue;

    private T result;

    public ApplicationEntityTypesProcessor() {
        defaultValue = null;
    }

    public ApplicationEntityTypesProcessor(T defaultValue) {
        this.defaultValue = defaultValue;
    }

    public static Set<java.lang.Class<? extends com.google.gwt.requestfactory.shared.EntityProxy>> getAll() {
        Set<Class<? extends EntityProxy>> rtn = new HashSet<Class<? extends EntityProxy>>();
        rtn.add(IssueProxy.class);
        return Collections.unmodifiableSet(rtn);
    }

    private static void process(org.sgo.rootest.client.managed.request.ApplicationEntityTypesProcessor<?> processor, Class<?> clazz) {
        if (IssueProxy.class.equals(clazz)) {
            processor.handleIssue((IssueProxy) null);
            return;
        }
        processor.handleNonProxy(null);
    }

    private static void process(org.sgo.rootest.client.managed.request.ApplicationEntityTypesProcessor<?> processor, Object proxy) {
        if (proxy instanceof IssueProxy) {
            processor.handleIssue((IssueProxy) proxy);
            return;
        }
        processor.handleNonProxy(proxy);
    }

    public void handleNonProxy(Object object) {
    }

    public abstract void handleIssue(IssueProxy proxy);

    public T process(Class<?> clazz) {
        setResult(defaultValue);
        ApplicationEntityTypesProcessor.process(this, clazz);
        return result;
    }

    public T process(Object proxy) {
        setResult(defaultValue);
        ApplicationEntityTypesProcessor.process(this, proxy);
        return result;
    }

    protected void setResult(T result) {
        this.result = result;
    }
}
