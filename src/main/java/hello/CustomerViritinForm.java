package hello;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Component;
import com.vaadin.ui.DateField;
import com.vaadin.ui.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.viritin.fields.MTextField;
import org.vaadin.viritin.fields.TypedSelect;
import org.vaadin.viritin.form.AbstractForm;
import org.vaadin.viritin.layouts.MFormLayout;
import org.vaadin.viritin.layouts.MVerticalLayout;

@SpringComponent
@UIScope
public class CustomerViritinForm extends AbstractForm<Customer> {

    TextField firstName = new MTextField("firstName");

    TextField lastName = new MTextField("lastName");

    DateField birthDate = new DateField("birthDate");

    // Select to another entity, options must be populated!!
    TypedSelect<Company> company = new TypedSelect().
            withCaption("company");
    
    @Autowired CompanyRepository companyRepository;

    @Override
    protected Component createContent() {
        company.setOptions(companyRepository.findAll());
        company.setCaptionGenerator(c->c.getName());
        return new MVerticalLayout(
                new MFormLayout(
                        firstName,
                        lastName,
                        birthDate,
                        company
                ).withWidth(null),
                getToolbar()
        ).withWidth(null);
    }

}
