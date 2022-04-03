-- >>> table-create 'todo_items'
CREATE TABLE IF NOT EXISTS "todo_items"
(
    "id"                BIGSERIAL    NOT NULL PRIMARY KEY,
    "name"              VARCHAR(128) NOT NULL,
    "owner"             VARCHAR(512) NOT NULL,
    "due_date"          TIMESTAMP    NULL,
    "complete"          BOOLEAN      NOT NULL DEFAULT FALSE,
    "date_time_created" TIMESTAMP    NOT NULL,
    "date_time_updated" TIMESTAMP    NULL
) WITH (OIDS = FALSE);
-- <<< table-create 'todo_items'

-- >>> index-create 'idx_todo_items_owner'
CREATE INDEX IF NOT EXISTS "idx_todo_items_owner"
    ON "todo_items"
        USING "btree" ("owner");
-- <<< index-create 'idx_todo_items_owner'
