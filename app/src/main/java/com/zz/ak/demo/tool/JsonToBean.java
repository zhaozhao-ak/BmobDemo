package com.zz.ak.demo.tool;//package com.zz.ak.demo.tool;
//
//import org.json.JSONArray;
//import org.json.JSONObject;
//
//import java.lang.reflect.Field;
//import java.lang.reflect.Method;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Created by Administrator on 2017/11/5.
// */
//
//public class JsonToBean {
//
//    /**
//     * jsonArray 转换成 javaBean list
//     *
//     * @param jsonStr json格式的String数据
//     * @param clazz 需要转成的bean的.class对象
//     * @param <T> 转化成的bean类型
//     * @return 集合
//     * @throws Exception
//     */
//    public static <T> List<T> parseJsonToBeanList(String jsonStr, Class<T> clazz) throws Exception {
//
//        List<T> list = null; //包含的实体列表
//
//        list = new ArrayList<T>();
//        JSONArray jArray = new JSONArray(jsonStr);
//
//        for (int i = 0; i < jArray.length(); i++) {
//            JSONObject jso = (JSONObject) jArray.opt(i);
//
//            Field[] fs = clazz.getDeclaredFields();
//
//            T t = clazz.newInstance();
//
//            for (Field field : fs) {
//                String fieldName = field.getName();
//                if ("_id".equals(fieldName)) {
//                    //_id根据自定义的bean做相应修改
//                    continue;
//                }
//                Method m = clazz.getDeclaredMethod(
//                        "set" + Tools.toUpperCaseFirstOne(fieldName),
//                        field.getType());
//                Object arg = jso.opt(fieldName);
//                if (m != null && m.getName() != null && arg != null) {
//                    if (!arg.toString().equals("null") && !arg.toString().equals("")) {
//
//                        if (field.getType().getName().equals("int")) {
//                            m.invoke(t, Integer.valueOf(arg.toString()));
//                        } else if (field.getType().getName().equals("long")) {
//                            m.invoke(t, Long.valueOf(arg.toString()));
//                        } else if (field.getType().getName().equals("short")) {
//                            m.invoke(t, Short.valueOf(arg.toString()));
//                        } else {
//                            m.invoke(t, arg);
//                        }
//                    }
//                }
//            }
//            list.add(t);
//        }
//        return list;
//    }
//}
