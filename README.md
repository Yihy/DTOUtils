# DTOUtils-IDEA-Plugin


idea生成DTO转换类插件
基于 [intellij-converter](https://github.com/me10zyl/intellij-converter) 插件修改,感谢原作者 @me10zyl


----------------
效果如下
```java

public static CommonModel getCommonModel(Common source){
    CommonModel target = null;
    if (source != null) {
        target = new CommonModel();
        target.setId(source.getId());
        target.setKey(source.getKey());
        target.setValue(source.getValue());
    }
    return target;
}

public static List<CommonModel> getCommonModel(List<Common> list) {
    List<CommonModel> result = null;
        if (list != null && !list.isEmpty()) {
            result = new ArrayList<>(list.size());
            for (Common tmp : list) {
                result.add(getCommonModel(tmp));
            }
        }
    return result;
}

public static Common convertCommon(CommonModel source){
    Common target = null;
    if (source != null) {
        target = new Common();
        target.setId(source.getId());
        target.setKey(source.getKey());
        target.setValue(source.getValue());
    }
    return target;
}
public static List<Common> convertCommon(List<CommonModel> list) {
    List<Common> result = null;
    if (list != null && !list.isEmpty()) {
        result = new ArrayList<>(list.size());
        for (CommonModel tmp : list) {
            result.add(convertCommon(tmp));
        }
    }
    return result;
}


```
