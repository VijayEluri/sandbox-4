// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.

package org.sgo.rootest.client.managed.ui;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.text.client.DateTimeFormatRenderer;
import com.google.gwt.text.shared.AbstractRenderer;
import com.google.gwt.text.shared.AbstractSafeHtmlRenderer;
import com.google.gwt.text.shared.Renderer;
import com.google.gwt.text.shared.SafeHtmlRenderer;
import java.util.HashSet;
import java.util.Set;
import org.sgo.rootest.client.managed.request.IssueProxy;
import org.sgo.rootest.client.scaffold.ScaffoldMobileApp;
import org.sgo.rootest.client.scaffold.ui.MobileProxyListView;

public abstract class IssueMobileListView_Roo_Gwt extends MobileProxyListView<IssueProxy> {

    protected Set<String> paths = new HashSet<String>();

    public IssueMobileListView_Roo_Gwt(String buttonText, SafeHtmlRenderer<org.sgo.rootest.client.managed.request.IssueProxy> renderer) {
        super(buttonText, renderer);
    }

    public void init() {
        paths.add("summary");
        paths.add("id");
    }
}
