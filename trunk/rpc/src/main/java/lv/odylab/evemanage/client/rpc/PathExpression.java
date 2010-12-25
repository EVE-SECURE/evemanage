package lv.odylab.evemanage.client.rpc;

import java.io.Serializable;

public class PathExpression implements Serializable {
    private Long[] pathNodes;
    private Integer meLevel;
    private Integer peLevel;

    public PathExpression() {
    }

    public PathExpression(Long[] pathNodes, Integer meLevel, Integer peLevel) {
        this.pathNodes = pathNodes;
        this.meLevel = meLevel;
        this.peLevel = peLevel;
    }

    public PathExpression(Long[] pathNodes) {
        this.pathNodes = pathNodes;
    }

    public PathExpression(Long[] pathNodes, Integer meLevel, Integer peLevel, Long itemTypeID) {
        this.pathNodes = new Long[pathNodes.length + 1];
        System.arraycopy(pathNodes, 0, this.pathNodes, 0, pathNodes.length);
        this.pathNodes[pathNodes.length] = itemTypeID;
        this.meLevel = meLevel;
        this.peLevel = peLevel;
    }

    public PathExpression(Long[] pathNodes, Long itemTypeID) {
        this.pathNodes = new Long[pathNodes.length + 1];
        System.arraycopy(pathNodes, 0, this.pathNodes, 0, pathNodes.length);
        this.pathNodes[pathNodes.length] = itemTypeID;
    }

    public PathExpression(Long rootItemTypeID, Integer meLevel, Integer peLevel, Long itemTypeID) {
        this.pathNodes = new Long[]{rootItemTypeID, itemTypeID};
        this.meLevel = meLevel;
        this.peLevel = peLevel;
    }

    public PathExpression(Long rootItemTypeID, Long itemTypeID) {
        this.pathNodes = new Long[]{rootItemTypeID, itemTypeID};
    }

    public PathExpression(PathExpression rootPathExpression, Integer meLevel, Integer peLevel, Long itemTypeID) {
        Long[] rootPathNodes = rootPathExpression.getPathNodes();
        this.pathNodes = new Long[rootPathNodes.length + 1];
        System.arraycopy(rootPathNodes, 0, this.pathNodes, 0, rootPathNodes.length);
        this.pathNodes[rootPathNodes.length] = itemTypeID;
        this.meLevel = meLevel;
        this.peLevel = peLevel;
    }

    public PathExpression(PathExpression rootPathExpression, Long itemTypeID) {
        Long[] rootPathNodes = rootPathExpression.getPathNodes();
        this.pathNodes = new Long[rootPathNodes.length + 1];
        System.arraycopy(rootPathNodes, 0, this.pathNodes, 0, rootPathNodes.length);
        this.pathNodes[rootPathNodes.length] = itemTypeID;
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

    public Boolean hasMeFactoring() {
        return meLevel != null;
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

    public String getExpression() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < pathNodes.length - 1; i++) {
            stringBuilder.append("/").append(pathNodes[i]);
        }
        if (hasMeFactoring()) {
            stringBuilder.append(":").append(meLevel).append(":").append(peLevel);
        }
        stringBuilder.append("/").append(pathNodes[pathNodes.length - 1]);
        return stringBuilder.toString();
    }

    public static PathExpression parseExpression(String expressionString) {
        String[] stringNodes = expressionString.split("/");
        Long[] pathNodes = new Long[stringNodes.length - 1];
        Boolean hasMeFactoring = true;
        Integer meLevel = null;
        Integer peLevel = null;
        for (int i = 0; i < stringNodes.length - 1; i++) {
            String stringNode = stringNodes[i + 1];
            if (i == stringNodes.length - 3) {
                if (!stringNode.contains(":")) {
                    hasMeFactoring = false;
                } else {
                    hasMeFactoring = true;
                    meLevel = Integer.valueOf(stringNode.substring(stringNode.indexOf(":") + 1, stringNode.lastIndexOf(":")));
                    peLevel = Integer.valueOf(stringNode.substring(stringNode.lastIndexOf(":") + 1));
                    stringNode = stringNode.substring(0, stringNode.indexOf(":"));
                }
            }
            pathNodes[i] = Long.valueOf(stringNode);
        }
        if (hasMeFactoring) {
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
