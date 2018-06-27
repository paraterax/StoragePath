package com.paratera.sgri.pojo;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class PathPojo {
    private String project_name;
    private String real_path;
    private String remove_path;

    public String getProject_name() {
        return project_name;
    }

    public void setProject_name(String project_name) {
        this.project_name = project_name;
    }

    public String getReal_path() {
        return real_path;
    }

    public void setReal_path(String real_path) {
        this.real_path = real_path;
    }

    public String getRemove_path() {
        return remove_path;
    }

    public void setRemove_path(String remove_path) {
        this.remove_path = remove_path;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

}
