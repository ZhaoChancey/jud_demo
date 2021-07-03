package cn.interview.juc;

/**
 * @author chancey
 * @create 2020-08-02 11:17
 */
public enum CountryEnum {
    ONE(1, "齐"), TWO(2, "楚"), THREE(3, "燕"), FOUR(4, "赵"), FIVE(5, "魏"), SIX(6, "韩");
    ;
    private Integer retCode;
    private String retMessage;

    public Integer getRetCode() {
        return retCode;
    }

    CountryEnum(Integer retCode, String retMessage) {
        this.retCode = retCode;
        this.retMessage = retMessage;
    }

    public String getRetMessage() {
        return retMessage;
    }

    public static CountryEnum for_each(int index) {
        CountryEnum[] enums = CountryEnum.values();
        for (CountryEnum anEnum : enums) {
            if (anEnum.getRetCode() == index) {
                return anEnum;
            }
        }
        return null;
    }
}
