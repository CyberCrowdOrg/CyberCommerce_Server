package org.cybercrowd.mvp.dto;

import java.io.Serializable;
import java.util.List;

public class DaoViewNodeDto implements Serializable {

    private String id;
    private String text;
    private String styleClass = "";
    private String color = "#007CFF"; //默认色
    private List<DaoViewChildDto> children;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<DaoViewChildDto> getChildren() {
        return children;
    }

    public void setChildren(List<DaoViewChildDto> children) {
        this.children = children;
    }

    public String getStyleClass() {
        return styleClass;
    }

    public void setStyleClass(String styleClass) {
        this.styleClass = styleClass;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
