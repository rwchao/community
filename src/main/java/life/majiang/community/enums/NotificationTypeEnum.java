package life.majiang.community.enums;

public enum  NotificationTypeEnum {
    REPLY_QUESTION(1,"回答了问题"),
    REPLY_COMMENT(2,"评论了回答")
    ;
    private Integer type;
    private String name;

    NotificationTypeEnum(int status, String name) {
        this.type = status;
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public static String nameOfType(Integer type){
        for (NotificationTypeEnum typeEnum : NotificationTypeEnum.values()) {
            if (typeEnum.getType() == type) {
                return typeEnum.getName();
            }
        }
        return "";
    }
}
