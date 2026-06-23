/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.example.todo.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

/**
 * The table class for the &quot;TODO_Todo&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see Todo
 * @generated
 */
public class TodoTable extends BaseTable<TodoTable> {

	public static final TodoTable INSTANCE = new TodoTable();

	public final Column<TodoTable, Long> todoId = createColumn(
		"todoId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<TodoTable, String> text = createColumn(
		"text_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<TodoTable, Boolean> completed = createColumn(
		"completed", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);

	private TodoTable() {
		super("TODO_Todo", TodoTable::new);
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:891287888