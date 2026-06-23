/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.example.todo.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link Todo}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see Todo
 * @generated
 */
public class TodoWrapper
	extends BaseModelWrapper<Todo> implements ModelWrapper<Todo>, Todo {

	public TodoWrapper(Todo todo) {
		super(todo);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("todoId", getTodoId());
		attributes.put("text", getText());
		attributes.put("completed", isCompleted());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long todoId = (Long)attributes.get("todoId");

		if (todoId != null) {
			setTodoId(todoId);
		}

		String text = (String)attributes.get("text");

		if (text != null) {
			setText(text);
		}

		Boolean completed = (Boolean)attributes.get("completed");

		if (completed != null) {
			setCompleted(completed);
		}
	}

	@Override
	public Todo cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the completed of this todo.
	 *
	 * @return the completed of this todo
	 */
	@Override
	public boolean getCompleted() {
		return model.getCompleted();
	}

	/**
	 * Returns the primary key of this todo.
	 *
	 * @return the primary key of this todo
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the text of this todo.
	 *
	 * @return the text of this todo
	 */
	@Override
	public String getText() {
		return model.getText();
	}

	/**
	 * Returns the todo ID of this todo.
	 *
	 * @return the todo ID of this todo
	 */
	@Override
	public long getTodoId() {
		return model.getTodoId();
	}

	/**
	 * Returns <code>true</code> if this todo is completed.
	 *
	 * @return <code>true</code> if this todo is completed; <code>false</code> otherwise
	 */
	@Override
	public boolean isCompleted() {
		return model.isCompleted();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets whether this todo is completed.
	 *
	 * @param completed the completed of this todo
	 */
	@Override
	public void setCompleted(boolean completed) {
		model.setCompleted(completed);
	}

	/**
	 * Sets the primary key of this todo.
	 *
	 * @param primaryKey the primary key of this todo
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the text of this todo.
	 *
	 * @param text the text of this todo
	 */
	@Override
	public void setText(String text) {
		model.setText(text);
	}

	/**
	 * Sets the todo ID of this todo.
	 *
	 * @param todoId the todo ID of this todo
	 */
	@Override
	public void setTodoId(long todoId) {
		model.setTodoId(todoId);
	}

	@Override
	public String toXmlString() {
		return model.toXmlString();
	}

	@Override
	protected TodoWrapper wrap(Todo todo) {
		return new TodoWrapper(todo);
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:-1510641204