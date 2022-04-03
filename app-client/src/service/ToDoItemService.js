class ToDoItemService {
    async getToDoItems() {
        return [
            { name: 'one', complete: false, dueDate: new Date() },
            { name: 'two', complete: false, dueDate: new Date() },
            { name: 'three', complete: false, dueDate: new Date() },
        ];
    }
}

export default new ToDoItemService();
