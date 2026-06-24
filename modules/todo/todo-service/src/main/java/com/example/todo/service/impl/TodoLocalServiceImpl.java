package com.example.todo.service.impl;

import com.example.todo.service.base.TodoLocalServiceBaseImpl;

import com.liferay.portal.aop.AopService;

import org.osgi.service.component.annotations.Component;

@Component(
    property = "model.class.name=com.example.todo.model.Todo",
    service = AopService.class
)
public class TodoLocalServiceImpl extends TodoLocalServiceBaseImpl {
}