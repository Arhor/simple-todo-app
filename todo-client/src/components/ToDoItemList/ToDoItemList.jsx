import PropTypes from 'prop-types';
import ToDoItem from '@/components/ToDoItem/ToDoItem';

function ToDoItemList({ items }) {
    return (
        <>
            {items.map((item, i) => (
                <ToDoItem {...item} key={i} />
            ))}
        </>
    );
}

ToDoItemList.propTypes = {
    items: PropTypes.arrayOf(
        PropTypes.shape({
            name: PropTypes.string.isRequired,
            dueDate: PropTypes.instanceOf(Date),
            complete: PropTypes.bool.isRequired,
        }).isRequired,
    ).isRequired,
};

export default ToDoItemList;
