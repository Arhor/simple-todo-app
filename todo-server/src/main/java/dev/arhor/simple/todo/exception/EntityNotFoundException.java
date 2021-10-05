package dev.arhor.simple.todo.exception;

public class EntityNotFoundException extends LocalizedException {

    private final String entityName;
    private final String propertyName;
    private final Object propertyValue;

    public EntityNotFoundException(String entityName, String propertyName, Object propertyValue) {
        super("Cannot found entity [%s] with [%s] = [%s]".formatted(entityName, propertyName, propertyValue));
        this.entityName = entityName;
        this.propertyName = propertyName;
        this.propertyValue = propertyValue;
    }

    public String getEntityName() {
        return entityName;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public Object getPropertyValue() {
        return propertyValue;
    }

    @Override
    public String getLabel() {
        return "error.entity.notfound";
    }

    @Override
    public Object[] getParams() {
        return new Object[]{entityName, propertyName, propertyValue};
    }
}
