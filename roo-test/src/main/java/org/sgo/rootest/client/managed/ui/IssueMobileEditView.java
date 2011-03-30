package org.sgo.rootest.client.managed.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.editor.client.EditorError;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.requestfactory.client.RequestFactoryEditorDriver;
import com.google.gwt.requestfactory.shared.RequestFactory;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.text.shared.AbstractRenderer;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DoubleBox;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.IntegerBox;
import com.google.gwt.user.client.ui.LongBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.ValueListBox;
import com.google.gwt.user.datepicker.client.DateBox;
import java.util.Collection;
import java.util.List;
import org.sgo.rootest.client.managed.activity.IssueEditActivityWrapper;
import org.sgo.rootest.client.managed.request.IssueProxy;
import org.sgo.rootest.client.managed.ui.IssueMobileEditView.Binder;
import org.sgo.rootest.client.scaffold.place.ProxyEditView;
import org.sgo.rootest.client.scaffold.ui.*;

public class IssueMobileEditView extends IssueMobileEditView_Roo_Gwt {

    private static final Binder BINDER = GWT.create(Binder.class);

    private static org.sgo.rootest.client.managed.ui.IssueMobileEditView instance;

    @UiField
    Button save;

    @UiField
    DivElement errors;

    private Delegate delegate;

    public IssueMobileEditView() {
        initWidget(BINDER.createAndBindUi(this));
    }

    public static org.sgo.rootest.client.managed.ui.IssueMobileEditView instance() {
        if (instance == null) {
            instance = new IssueMobileEditView();
        }
        return instance;
    }

    @Override
    public RequestFactoryEditorDriver<org.sgo.rootest.client.managed.request.IssueProxy, org.sgo.rootest.client.managed.ui.IssueMobileEditView> createEditorDriver() {
        RequestFactoryEditorDriver<IssueProxy, IssueMobileEditView> driver = GWT.create(Driver.class);
        driver.initialize(this);
        return driver;
    }

    public void setCreating(boolean creating) {
    }

    public void setDelegate(Delegate delegate) {
        this.delegate = delegate;
    }

    public void setEnabled(boolean enabled) {
        save.setEnabled(enabled);
    }

    public void showErrors(List<EditorError> errors) {
        SafeHtmlBuilder b = new SafeHtmlBuilder();
        for (EditorError error : errors) {
            b.appendEscaped(error.getPath()).appendEscaped(": ");
            b.appendEscaped(error.getMessage()).appendHtmlConstant("<br>");
        }
        this.errors.setInnerHTML(b.toSafeHtml().asString());
    }

    @UiHandler("save")
    void onSave(ClickEvent event) {
        delegate.saveClicked();
    }

    interface Binder extends UiBinder<HTMLPanel, IssueMobileEditView> {
    }

    interface Driver extends RequestFactoryEditorDriver<IssueProxy, IssueMobileEditView> {
    }
}
