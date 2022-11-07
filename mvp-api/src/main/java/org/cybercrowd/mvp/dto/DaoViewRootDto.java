package org.cybercrowd.mvp.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DaoViewRootDto implements Serializable {

    private String rootId;
    private List<DaoViewNodeDto> nodes;
    private List<Object> links = new ArrayList<>();

    public String getRootId() {
        return rootId;
    }

    public void setRootId(String rootId) {
        this.rootId = rootId;
    }

    public List<DaoViewNodeDto> getNodes() {
        return nodes;
    }

    public void setNodes(List<DaoViewNodeDto> nodes) {
        this.nodes = nodes;
    }

    public List<Object> getLinks() {
        return links;
    }

    public void setLinks(List<Object> links) {
        this.links = links;
    }
}
