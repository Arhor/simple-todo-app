import React, { useEffect, useState } from 'react';
import { FormControl, InputLabel, Input, FormHelperText } from '@mui/material';
import ToDoItemList from '@/components/ToDoItemList';
import ToDoItemCreator from '@/components/ToDoItemCreator';
import ToDoItemService from '@/service/ToDoItemService';

function PageHome() {
    const [toDoItems, setToDoItems] = useState([]);

    useEffect(() => {
        async function fetchToDoItems() {
            try {
                const items = await ToDoItemService.getToDoItems();
                setToDoItems(items);
            } catch (e) {
                console.error('Cannot get list of user TO-DO items');
            }
        }
        fetchToDoItems();
    }, []);

    const onToDoItemCreated = (toDoItem) => {};

    return (
        <>
            <FormControl>
                <InputLabel htmlFor="my-input">Email address</InputLabel>
                <Input id="my-input" aria-describedby="my-helper-text" />
                <FormHelperText id="my-helper-text">We'll never share your email.</FormHelperText>
            </FormControl>

            <ToDoItemCreator onToDoItemCreated={onToDoItemCreated} />
            <ToDoItemList items={toDoItems} />
        </>
    );
}

export default PageHome;
