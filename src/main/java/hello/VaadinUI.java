package hello;

import com.vaadin.annotations.Theme;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Button;
import com.vaadin.ui.UI;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.viritin.fields.MTable;
import org.vaadin.viritin.fields.MTextField;
import org.vaadin.viritin.layouts.MHorizontalLayout;
import org.vaadin.viritin.layouts.MVerticalLayout;

@SpringUI
@Theme("valo")
public class VaadinUI extends UI {

    MTable<Customer> customerList = new MTable<>(Customer.class)
            .withProperties("firstName", "lastName");

    MTextField filter = new MTextField().withInputPrompt("filter by last name");

    @Autowired
    CustomerRepository cr;

    @Autowired
    CustomerViritinForm form;

    @Override
    protected void init(VaadinRequest request) {
        listBeans();

        Button addButton = new Button(FontAwesome.PLUS);
        addButton.addClickListener((Button.ClickEvent e) -> {
            form.setEntity(new Customer());
            form.openInModalPopup();
        });

        form.setSavedHandler(entity -> {
            cr.save(entity);
            form.getPopup().close();
            listBeans();
        });
        
        customerList.addRowClickListener(e -> {
            form.setEntity(e.getEntity());
            form.setDeleteHandler(entity -> {
                cr.delete(entity);
                form.getPopup().close();
                listBeans();
            });
            form.openInModalPopup();
        });

        filter.addTextChangeListener(e -> customerList.setBeans(cr.
                findByLastNameStartsWithIgnoreCase(e.getText())));

        setContent(
                new MVerticalLayout(new MHorizontalLayout(filter, addButton)).
                expand(customerList));
    }

    protected void listBeans() {
        customerList.setBeans(cr.findAll());
    }
}
