package lv.odylab.evemanage.client.rpc;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class CalculationExpression implements Serializable {
    private String blueprintTypeName;
    private Integer meLevel = 0;
    private Integer peLevel = 0;
    private Long quantity = 1L;
    private Map<String, Integer> blueprintPathToMeLevelMap = new HashMap<String, Integer>();
    private Map<String, Integer> blueprintPathToPeLevelMap = new HashMap<String, Integer>();
    private Map<Long, String> priceSetItemTypeIdToPriceMap = new HashMap<Long, String>();
    private Boolean valid = Boolean.FALSE;

    public CalculationExpression() {
    }

    public String getBlueprintTypeName() {
        return blueprintTypeName;
    }

    public void setBlueprintTypeName(String blueprintTypeName) {
        this.blueprintTypeName = blueprintTypeName;
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

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Map<String, Integer> getBlueprintPathToMeLevelMap() {
        return blueprintPathToMeLevelMap;
    }

    public Map<String, Integer> getBlueprintPathToPeLevelMap() {
        return blueprintPathToPeLevelMap;
    }

    public Map<Long, String> getPriceSetItemTypeIdToPriceMap() {
        return priceSetItemTypeIdToPriceMap;
    }

    public Boolean isValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    public static CalculationExpression parseExpression(String calculationExpressionString) {
        CalculationExpression calculationExpression = new CalculationExpression();
        String[] calculationExpressionTokens = calculationExpressionString.split("\\|");
        if (calculationExpressionTokens.length > 1) {
            calculationExpression.setValid(Boolean.TRUE);
            calculationExpression.setBlueprintTypeName(calculationExpressionTokens[1]);
            for (int i = 2; i < calculationExpressionTokens.length; i++) {
                String[] tokens = calculationExpressionTokens[i].split(":");
                String tokenName = tokens[0];
                String tokenFirstValue = tokens[1];
                if ("ME".equalsIgnoreCase(tokenName)) {
                    calculationExpression.setMeLevel(Integer.valueOf(tokenFirstValue));
                } else if ("PE".equalsIgnoreCase(tokenName)) {
                    calculationExpression.setPeLevel(Integer.valueOf(tokenFirstValue));
                } else if ("Q".equalsIgnoreCase(tokenName)) {
                    calculationExpression.setQuantity(Long.valueOf(tokenFirstValue));
                } else if ("B".equalsIgnoreCase(tokenName)) {
                    Integer blueprintMeLevel = Integer.valueOf(tokens[2]);
                    Integer blueprintPeLevel = Integer.valueOf(tokens[3]);
                    calculationExpression.getBlueprintPathToMeLevelMap().put(tokenFirstValue, blueprintMeLevel);
                    calculationExpression.getBlueprintPathToPeLevelMap().put(tokenFirstValue, blueprintPeLevel);
                } else if ("P".equalsIgnoreCase(tokenName)) {
                    Long priceItemTypeID = Long.valueOf(tokenFirstValue);
                    calculationExpression.getPriceSetItemTypeIdToPriceMap().put(priceItemTypeID, tokens[2]);
                }
            }
        } else {
            calculationExpression.setValid(Boolean.FALSE);
        }
        return calculationExpression;
    }

    public String getExpression() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("|").append(blueprintTypeName.replace(' ', '+'));
        if (meLevel != 0) {
            stringBuilder.append("|ME:").append(meLevel);
        }
        if (peLevel != 0) {
            stringBuilder.append("|PE:").append(peLevel);
        }
        if (quantity > 1) {
            stringBuilder.append("|Q:").append(quantity);
        }

        for (Map.Entry<String, Integer> entry : blueprintPathToMeLevelMap.entrySet()) {
            String blueprintPath = entry.getKey();
            Integer me = entry.getValue();
            Integer pe = blueprintPathToPeLevelMap.get(blueprintPath);
            stringBuilder.append("|B:").append(blueprintPath).append(":");
            if (me == null && pe == null) {
                stringBuilder.append(":");
            } else {
                stringBuilder.append(me).append(":").append(pe);
            }
        }

        for (Map.Entry<Long, String> entry : priceSetItemTypeIdToPriceMap.entrySet()) {
            Long typeID = entry.getKey();
            String price = entry.getValue();
            stringBuilder.append("|P:").append(typeID).append(":").append(price);
        }

        return stringBuilder.toString();
    }
}
