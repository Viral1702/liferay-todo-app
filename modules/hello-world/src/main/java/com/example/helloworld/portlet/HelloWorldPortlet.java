package com.example.helloworld.portlet;

import com.example.helloworld.constants.HelloWorldPortletKeys;
import com.example.todo.service.TodoLocalServiceUtil;
import com.example.todo.model.Todo;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.util.ParamUtil;

import java.io.IOException;
import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.Portlet;
import javax.portlet.PortletException;

import org.osgi.service.component.annotations.Component;

@Component(
    property = {
        "com.liferay.portlet.display-category=category.sample",
        "com.liferay.portlet.header-portlet-css=/css/main.css",
        "com.liferay.portlet.instanceable=true",
        "javax.portlet.display-name=hello-world",
        "javax.portlet.init-param.template-path=/",
        "javax.portlet.init-param.view-template=/view.jsp",
        "javax.portlet.name=" + HelloWorldPortletKeys.HELLOWORLD,
        "javax.portlet.resource-bundle=content.Language",
        "javax.portlet.security-role-ref=power-user,user"
    },
    service = Portlet.class
)
public class HelloWorldPortlet extends MVCPortlet {

    public void addTodo(ActionRequest actionRequest, ActionResponse actionResponse) 
            throws IOException, PortletException {

        // 1. Extract the text value from the input field element
        String todoText = ParamUtil.getString(actionRequest, "todoText");

        // 2. Instantiate a fresh model instance (0 tells framework to auto-increment ID)
        Todo newTodo = TodoLocalServiceUtil.createTodo(0);
        newTodo.setText(todoText);
        newTodo.setCompleted(false);

        // 3. Persist the data entry directly into your MySQL engine
        TodoLocalServiceUtil.addTodo(newTodo);
    }
}
