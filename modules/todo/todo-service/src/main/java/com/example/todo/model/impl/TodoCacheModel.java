/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.example.todo.model.impl;

import com.example.todo.model.Todo;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing Todo in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class TodoCacheModel implements CacheModel<Todo>, Externalizable {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof TodoCacheModel)) {
			return false;
		}

		TodoCacheModel todoCacheModel = (TodoCacheModel)object;

		if (todoId == todoCacheModel.todoId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, todoId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(7);

		sb.append("{todoId=");
		sb.append(todoId);
		sb.append(", text=");
		sb.append(text);
		sb.append(", completed=");
		sb.append(completed);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public Todo toEntityModel() {
		TodoImpl todoImpl = new TodoImpl();

		todoImpl.setTodoId(todoId);

		if (text == null) {
			todoImpl.setText("");
		}
		else {
			todoImpl.setText(text);
		}

		todoImpl.setCompleted(completed);

		todoImpl.resetOriginalValues();

		return todoImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		todoId = objectInput.readLong();
		text = objectInput.readUTF();

		completed = objectInput.readBoolean();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(todoId);

		if (text == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(text);
		}

		objectOutput.writeBoolean(completed);
	}

	public long todoId;
	public String text;
	public boolean completed;

}
// LIFERAY-SERVICE-BUILDER-HASH:-1624209088