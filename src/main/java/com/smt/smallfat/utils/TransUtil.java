package com.smt.smallfat.utils;

import com.csyy.common.UUIDProvider;
import com.csyy.constant.Constants;
import com.csyy.core.exception.BusinessException;
import com.csyy.core.obj.BasePo;
import com.csyy.core.utils.CommonBeanUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TransUtil {
    public static <T,V> List<V> transPoListToVoList(List<T> sourceList,Class<V> targetList){

        List list = new ArrayList<>();
        for (T bean:sourceList) {
            V targetBean = CommonBeanUtils.getBeanBySameProperty(targetList, bean);
            list.add(targetBean);
        }
        return list;
    }

    public static BasePo newInstance(BasePo clazz){
        clazz.setUuid(UUIDProvider.uuid());
        clazz.setCreateTime(new Date());
        clazz.setUpdateTime(new Date());
        clazz.setDisabled(Constants.DeleteStatus.ENABLED);
        return clazz;
    }

    public static void nullConverDefaultValue(Object obj) throws BusinessException {
        if (obj != null) {

            Class classz = obj.getClass();
            // 获取所有该对象的属性值
            Field fields[] = classz.getDeclaredFields();

            // 遍历属性值，取得所有属性为 null 值的
            for (Field field : fields) {
                try {
                    Type t = field.getType();
                    if (t != boolean.class) {
                        if (field.getName().equals("serialVersionUID") || field.getName().equals("tableName")){
                            continue;
                        }
                        if(Modifier.isStatic(field.getModifiers())){
                            continue;
                        }
                        Method m = classz.getMethod("get"
                                + change(field.getName()));
                        Object name = m.invoke(obj);// 调用该字段的get方法
                        if (name == null) {

                            if (t == Integer.class) {
                                Method mtd = classz.getMethod("set"
                                                + change(field.getName()),
                                        new Class[]{Integer.class});// 取得所需类的方法对象
                                mtd.invoke(obj, new Object[]{0});// 执行相应赋值方法
                            }else if (t == String.class){
                                Method mtd = classz.getMethod("set"
                                                + change(field.getName()),
                                        new Class[]{String.class});// 取得所需类的方法对象
                                mtd.invoke(obj, new Object[]{""});// 执行相应赋值方法
                            }else if(t == Date.class){
                                Method mtd = classz.getMethod("set"
                                                + change(field.getName()),
                                        new Class[]{Date.class});// 取得所需类的方法对象
                                mtd.invoke(obj, new Object[]{new Date()});
                            }
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    throw new BusinessException();
                }
            }
        }
    }

    /**
     * @param src 源字符串
     * @return 字符串，将src的第一个字母转换为大写，src为空时返回null
     */
    public static String change(String src) {
        if (src != null) {
            StringBuffer sb = new StringBuffer(src);
            sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
            return sb.toString();
        } else {
            return null;
        }
    }
}
