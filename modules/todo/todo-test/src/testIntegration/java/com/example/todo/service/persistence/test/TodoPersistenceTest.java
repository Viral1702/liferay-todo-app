/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.example.todo.service.persistence.test;

import com.example.todo.exception.NoSuchTodoException;
import com.example.todo.model.Todo;
import com.example.todo.service.TodoLocalServiceUtil;
import com.example.todo.service.persistence.TodoPersistence;
import com.example.todo.service.persistence.TodoUtil;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.util.IntegerWrapper;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PersistenceTestRule;
import com.liferay.portal.test.rule.TransactionalTestRule;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @generated
 */
@RunWith(Arquillian.class)
public class TodoPersistenceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED, "com.example.todo.service"));

	@Before
	public void setUp() {
		_persistence = TodoUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<Todo> iterator = _todos.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Todo todo = _persistence.create(pk);

		Assert.assertNotNull(todo);

		Assert.assertEquals(todo.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		Todo newTodo = addTodo();

		_persistence.remove(newTodo);

		Todo existingTodo = _persistence.fetchByPrimaryKey(
			newTodo.getPrimaryKey());

		Assert.assertNull(existingTodo);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addTodo();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Todo newTodo = _persistence.create(pk);

		newTodo.setText(RandomTestUtil.randomString());

		newTodo.setCompleted(RandomTestUtil.randomBoolean());

		_todos.add(_persistence.update(newTodo));

		Todo existingTodo = _persistence.findByPrimaryKey(
			newTodo.getPrimaryKey());

		Assert.assertEquals(existingTodo.getTodoId(), newTodo.getTodoId());
		Assert.assertEquals(existingTodo.getText(), newTodo.getText());
		Assert.assertEquals(existingTodo.isCompleted(), newTodo.isCompleted());
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		Todo newTodo = addTodo();

		Todo existingTodo = _persistence.findByPrimaryKey(
			newTodo.getPrimaryKey());

		Assert.assertEquals(existingTodo, newTodo);
	}

	@Test(expected = NoSuchTodoException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<Todo> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create(
			"TODO_Todo", "todoId", true, "text", true, "completed", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		Todo newTodo = addTodo();

		Todo existingTodo = _persistence.fetchByPrimaryKey(
			newTodo.getPrimaryKey());

		Assert.assertEquals(existingTodo, newTodo);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Todo missingTodo = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingTodo);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		Todo newTodo1 = addTodo();
		Todo newTodo2 = addTodo();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newTodo1.getPrimaryKey());
		primaryKeys.add(newTodo2.getPrimaryKey());

		Map<Serializable, Todo> todos = _persistence.fetchByPrimaryKeys(
			primaryKeys);

		Assert.assertEquals(2, todos.size());
		Assert.assertEquals(newTodo1, todos.get(newTodo1.getPrimaryKey()));
		Assert.assertEquals(newTodo2, todos.get(newTodo2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, Todo> todos = _persistence.fetchByPrimaryKeys(
			primaryKeys);

		Assert.assertTrue(todos.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		Todo newTodo = addTodo();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newTodo.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, Todo> todos = _persistence.fetchByPrimaryKeys(
			primaryKeys);

		Assert.assertEquals(1, todos.size());
		Assert.assertEquals(newTodo, todos.get(newTodo.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, Todo> todos = _persistence.fetchByPrimaryKeys(
			primaryKeys);

		Assert.assertTrue(todos.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		Todo newTodo = addTodo();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newTodo.getPrimaryKey());

		Map<Serializable, Todo> todos = _persistence.fetchByPrimaryKeys(
			primaryKeys);

		Assert.assertEquals(1, todos.size());
		Assert.assertEquals(newTodo, todos.get(newTodo.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			TodoLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod<Todo>() {

				@Override
				public void performAction(Todo todo) {
					Assert.assertNotNull(todo);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		Todo newTodo = addTodo();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			Todo.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq("todoId", newTodo.getTodoId()));

		List<Todo> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Todo existingTodo = result.get(0);

		Assert.assertEquals(existingTodo, newTodo);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			Todo.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq("todoId", RandomTestUtil.nextLong()));

		List<Todo> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		Todo newTodo = addTodo();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			Todo.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("todoId"));

		Object newTodoId = newTodo.getTodoId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in("todoId", new Object[] {newTodoId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingTodoId = result.get(0);

		Assert.assertEquals(existingTodoId, newTodoId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			Todo.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("todoId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"todoId", new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	protected Todo addTodo() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Todo todo = _persistence.create(pk);

		todo.setText(RandomTestUtil.randomString());

		todo.setCompleted(RandomTestUtil.randomBoolean());

		_todos.add(_persistence.update(todo));

		return todo;
	}

	private List<Todo> _todos = new ArrayList<Todo>();
	private TodoPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}
// LIFERAY-SERVICE-BUILDER-HASH:-373433176