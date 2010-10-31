package lv.odylab.evemanage.client.rpc;

import java.io.Serializable;

public class PathExpression implements Serializable {
    private Long[] pathNodes;
    private Boolean isMaterial = true;
    private Integer meLevel;
    private Integer peLevel;

    public PathExpression() {
    }

    public PathExpression(Long[] pathNodes, Integer meLevel, Integer peLevel) {
        this.pathNodes = pathNodes;
        this.meLevel = meLevel;
        this.peLevel = peLevel;
        this.isMaterial = true;
    }

    public PathExpression(Long[] pathNodes) {
        this.pathNodes = pathNodes;
        this.isMaterial = false;
    }

    public PathExpression(Long[] pathNodes, Integer meLevel, Integer peLevel, Long itemTypeID) {
        this.pathNodes = new Long[pathNodes.length + 1];
        System.arraycopy(pathNodes, 0, this.pathNodes, 0, pathNodes.length);
        this.pathNodes[pathNodes.length] = itemTypeID;
        this.meLevel = meLevel;
        this.peLevel = peLevel;
        this.isMaterial = true;
    }

    public PathExpression(Long[] pathNodes, Long itemTypeID) {
        this.pathNodes = new Long[pathNodes.length + 1];
        System.arraycopy(pathNodes, 0, this.pathNodes, 0, pathNodes.length);
        this.pathNodes[pathNodes.length] = itemTypeID;
        this.isMaterial = false;
    }

    public PathExpression(Long rootItemTypeID, Integer meLevel, Integer peLevel, Long itemTypeID) {
        this.pathNodes = new Long[]{rootItemTypeID, itemTypeID};
        this.meLevel = meLevel;
        this.peLevel = peLevel;
        this.isMaterial = true;
    }

    public PathExpression(Long rootItemTypeID, Long itemTypeID) {
        this.pathNodes = new Long[]{rootItemTypeID, itemTypeID};
        this.isMaterial = false;
    }

    public PathExpression(PathExpression rootPathExpression, Integer meLevel, Integer peLevel, Long itemTypeID) {
        Long[] rootPathNodes = rootPathExpression.getPathNodes();
        this.pathNodes = new Long[rootPathNodes.length + 1];
        System.arraycopy(rootPathNodes, 0, this.pathNodes, 0, rootPathNodes.length);
        this.pathNodes[rootPathNodes.length] = itemTypeID;
        this.meLevel = meLevel;
        this.peLevel = peLevel;
        this.isMaterial = true;
    }

    public PathExpression(PathExpression rootPathExpression, Long itemTypeID) {
        Long[] rootPathNodes = rootPathExpression.getPathNodes();
        this.pathNodes = new Long[rootPathNodes.length + 1];
        System.arraycopy(rootPathNodes, 0, this.pathNodes, 0, rootPathNodes.length);
        this.pathNodes[rootPathNodes.length] = itemTypeID;
        this.isMaterial = false;
    }

    public Integer getMeLevel() {
        return meLevel;
    }

    public void setMeLevel(Integer meLevel) {
        this.meLevel = meLevel;
    }

    public Integer getPeLevel() {
        return peLevel;
    }

    public void setPeLevel(Integer peLevel) {
        this.peLevel = peLevel;
    }

    public Boolean isMaterial() {
        return isMaterial;
    }

    public Boolean isRootNode() {
        return pathNodes.length == 2;
    }

    public Integer getLevel() {
        return pathNodes.length - 1;
    }

    public Long[] getPathNodes() {
        return pathNodes;
    }

    public String getPathNodesString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Long pathNode : pathNodes) {
            stringBuilder.append("/").append(pathNode);
        }
        return stringBuilder.toString();
    }

    public String getParentPath() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < pathNodes.length - 1; i++) {
            stringBuilder.append("/").append(pathNodes[i]);
        }
        return stringBuilder.toString();
    }

    public String getPath() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < pathNodes.length - 1; i++) {
            stringBuilder.append("/").append(pathNodes[i]);
        }
        if (isMaterial) {
            stringBuilder.append(":").append(meLevel).append(":").append(peLevel);
        } else {
            stringBuilder.append("::");
        }
        stringBuilder.append("/").append(pathNodes[pathNodes.length - 1]);
        return stringBuilder.toString();
    }

    public static PathExpression parsePath(String path) {
        String[] stringNodes = path.split("/");
        Long[] pathNodes = new Long[stringNodes.length - 1];
        boolean isMaterial = true;
        Integer meLevel = null;
        Integer peLevel = null;
        for (int i = 0; i < stringNodes.length - 1; i++) {
            String stringNode = stringNodes[i + 1];
            if (i == stringNodes.length - 3) {
                if (stringNode.contains("::")) {
                    isMaterial = false;
                } else {
                    isMaterial = true;
                    meLevel = Integer.valueOf(stringNode.substring(stringNode.indexOf(":") + 1, stringNode.lastIndexOf(":")));
                    peLevel = Integer.valueOf(stringNode.substring(stringNode.lastIndexOf(":") + 1));
                }
                stringNode = stringNode.substring(0, stringNode.indexOf(":"));
            }
            pathNodes[i] = Long.valueOf(stringNode);
        }
        if (isMaterial) {
            return new PathExpression(pathNodes, meLevel, peLevel);
        } else {
            return new PathExpression(pathNodes);
        }
    }

    public static String createPathNodesStringFromPathNodes(Long[] pathNodes) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Long pathNode : pathNodes) {
            stringBuilder.append("/").append(pathNode);
        }
        return stringBuilder.toString();
    }
}
