create table TODO_Todo (
	todoId LONG not null primary key,
	text_ VARCHAR(75) null,
	completed BOOLEAN
);

create table TODO_TodoItem (
	todoId LONG not null primary key,
	text_ VARCHAR(75) null,
	completed BOOLEAN
);