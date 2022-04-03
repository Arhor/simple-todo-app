import PropTypes from 'prop-types';

function ToDoItem({ name, dueDate, complete }) {
    return (
        <div>
            <div>{complete ? 'true' : 'false'}</div>
            <div>{name}</div>
            {/*{dueDate && (<Col>{dueDate}</Col>)}*/}
        </div>
    );
}

ToDoItem.propTypes = {
    name: PropTypes.string.isRequired,
    dueDate: PropTypes.instanceOf(Date),
    complete: PropTypes.bool.isRequired,
};

export default ToDoItem;
