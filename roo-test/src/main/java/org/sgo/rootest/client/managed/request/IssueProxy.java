// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.

package org.sgo.rootest.client.managed.request;

import com.google.gwt.requestfactory.shared.EntityProxy;
import com.google.gwt.requestfactory.shared.ProxyForName;
import org.springframework.roo.addon.gwt.RooGwtMirroredFrom;

@RooGwtMirroredFrom("org.sgo.rootest.domain.Issue")
@ProxyForName("org.sgo.rootest.domain.Issue")
public interface IssueProxy extends EntityProxy {

    abstract String getSummary();

    abstract void setSummary(String summary);

    abstract Long getId();

    abstract void setId(Long id);

    abstract Integer getVersion();

    abstract void setVersion(Integer version);
}