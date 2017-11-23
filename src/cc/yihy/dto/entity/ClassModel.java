package cc.yihy.dto.entity;

public class ClassModel {
    private ClassProperty model;
    private ClassProperty entity;

    public ClassModel(ClassProperty model, ClassProperty entity) {
        this.model = model;
        this.entity = entity;
    }

    public ClassProperty getModel() {
        return model;
    }

    public void setModel(ClassProperty model) {
        this.model = model;
    }

    public ClassProperty getEntity() {
        return entity;
    }

    public void setEntity(ClassProperty entity) {
        this.entity = entity;
    }
}
